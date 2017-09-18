package com.toulios.yotirobbot.service;

import java.util.List;
import java.util.Objects;

import com.toulios.yotirobbot.exceptions.InstructionValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.toulios.yotirobbot.api.IRobotInstructionsService;
import com.toulios.yotirobbot.model.Instruction;
import com.toulios.yotirobbot.model.Result;

@Service
public class RobotInstructionServiceImpl implements IRobotInstructionsService{

	@Override
	public Result executeInstruction(Instruction instruction) {
		try {
			validateInstruction(instruction);
			int[][] room = initializeRoom(instruction);
		
			Result result = new Result(0, instruction.getCoords());
			checkForPatches(room, result);
			
			printRoom(room, result);

			if(StringUtils.isNotEmpty(instruction.getInstructions())) {
				instruction.getInstructions().chars()
						.forEach(
								command
										-> executeCommand(command, instruction.getPatches(), room, result));
			}
			return result;
		}catch (InstructionValidationException e){
			throw e;
		}
	}

	private void validateInstruction(Instruction instruction) throws InstructionValidationException{
		if(Objects.isNull(instruction)){
			throw new InstructionValidationException("The instruction should not be null.");
		}
		if(CollectionUtils.isEmpty(instruction.getRoomSize())){
			throw new InstructionValidationException("The roomsize is mandatory.");
		}
		if(instruction.getRoomSize().size() != 2){
			throw new InstructionValidationException("There must be value roomsize[X,Y]");
		}
		if((instruction.getRoomSize().get(X_COORDINATE) < 0 ) ||  (instruction.getRoomSize().get(Y_COORDINATE) < 0 )){
			throw new InstructionValidationException("There must be value roomsize[X,Y] with both X,Y positives");
		}
		if(CollectionUtils.isEmpty(instruction.getCoords())){
			throw new InstructionValidationException("The hoover's coordinates should have values.");
		}
		
		if(instruction.getCoords().size() != 2){
			throw new InstructionValidationException("There must be value coordinates[X,Y]");
		}
		if((instruction.getCoords().get(X_COORDINATE) < 0 ) ||  (instruction.getCoords().get(Y_COORDINATE) < 0 )){
			throw new InstructionValidationException("There must be value coordinates[X,Y] with both X,Y positives");
		}
		if(CollectionUtils.isNotEmpty(instruction.getPatches())) {
			instruction.getPatches().forEach(patchVal -> {
				if(CollectionUtils.isEmpty(patchVal)) {
					throw new InstructionValidationException("The patches should be like patches[[x,y]]");
				}
			});
		}
		
		if((instruction.getRoomSize().get(X_COORDINATE) < instruction.getCoords().get(X_COORDINATE)) || (instruction.getRoomSize().get(Y_COORDINATE) < instruction.getCoords().get(Y_COORDINATE))) {
			throw new InstructionValidationException("The coordinates of the hover must be inside the room");
		}
	}

	/**
	 * Initializes the room
	 * 
	 * @param instruction
	 * @return
	 */
	private int[][] initializeRoom(Instruction instruction) {
		
		int sizeX = instruction.getRoomSize().get(X_COORDINATE);
		int sizeY = instruction.getRoomSize().get(Y_COORDINATE);
		int[][] room = new int[sizeY][sizeX];
		if(!CollectionUtils.isEmpty(instruction.getPatches())){
			initializePatches(instruction, room);
		}
		return room;
	}

	/**
	 * Iniatilizes the positions of the patches in the room
	 * 
	 * @param instruction
	 * @param room
	 */
	private void initializePatches(Instruction instruction, int[][] room) {
		instruction.getPatches().forEach(patch -> {
			if(patch.size() != 2) {
				throw new InstructionValidationException("Each patch should be in the form [x,y]");
			}
			
			if((patch.get(X_COORDINATE) < 0) || (patch.get(Y_COORDINATE) < 0)) {
				throw new InstructionValidationException("The patches points should be positive points");
			}
			
			int patchXpos = patch.get(X_COORDINATE);
			int patchYpos = patch.get(Y_COORDINATE);
			room[patchYpos][patchXpos] = 1;
		});
	}

	/**
	 * Execute the command and calculate the final position of the hovver and clean the patches
	 * for the given instructions.
	 * 
	 * @param command
	 * @param patchers
	 * @param room
	 * @param result
	 */
	private void executeCommand( 
			int command, 
			List<List<Integer>> patchers, 
			int[][] room, Result result) {
		
		switch (command) {
		case 'N':
			moveUp(room, result);
			printRoom(room, result);
			break;
		case 'S':
			moveDown(room, result);
			printRoom(room, result);
			break;
		case 'W':
			moveLeft(room, result);
			printRoom(room, result);
			break;
		case 'E':
			moveRight(room, result);
			printRoom(room, result);
			break;
		default:
			break;
		}
	}

	@Override
	public void moveRight(int[][] room, Result result) {
		int newPosX = result.getCoords().get(X_COORDINATE) + INCREMENT;
		
		if(newPosX < room[X_COORDINATE].length) {
			result.getCoords().set(X_COORDINATE, newPosX);
			checkForPatches(room, result);
		}
	}

	@Override
	public void moveLeft(int[][] room, Result result) {
		int newPosX = result.getCoords().get(X_COORDINATE) - INCREMENT;
		
		if(newPosX >= MINIMUM_LIMIT_WALL) {
			result.getCoords().set(X_COORDINATE, newPosX);
			checkForPatches(room, result);
		}
		
	}


	@Override
	public void moveUp(int[][] room, Result result) {
		int newPosY = result.getCoords().get(Y_COORDINATE) + INCREMENT;
		
		if(newPosY < room.length) {
			result.getCoords().set(Y_COORDINATE, newPosY);
			checkForPatches(room, result);
		}
		
	}

	@Override
	public void moveDown(int[][] room, Result result) {
		
		int newPosY = result.getCoords().get(Y_COORDINATE) - INCREMENT;
		
		if(newPosY >= MINIMUM_LIMIT_WALL) {
			result.getCoords().set(Y_COORDINATE, newPosY);
			checkForPatches(room, result);
		}
		
	}
	
	/**
	 * Check if in the current of the hover exists a patch and clean it
	 * 
	 * @param room
	 * @param result
	 */
	private void checkForPatches(int[][] room, Result result) {
		int currentPosX = result.getCoords().get(X_COORDINATE);
		int currentPosY = result.getCoords().get(Y_COORDINATE);
		
		if(room[currentPosY][currentPosX] == IS_PATCHED) {
			room[currentPosY][currentPosX] = CLEANED;
			result.setPatches(result.getPatches() + INCREMENT);
		}
	}
	
	/**
	 * Print in the console the state of the room. 
	 * 
	 * with dots the empty places, with 1s the patches and with hashTag the position of the hover
	 * 
	 * @param room
	 * @param result
	 */
	private void printRoom(int[][] room, Result result) {
		System.out.println();
		for(int i=0; i < room.length;i++) {
			for(int j = 0; j< room[0].length;j++) {
				if((j == result.getCoords().get(X_COORDINATE)) && (i == result.getCoords().get(Y_COORDINATE))) {
					System.out.print(HOVER);
				}
				else if(room[i][j] == CLEANED) {
					System.out.print(CLEAN_PLACE);
				}
				else {
					System.out.print(PATCH);
				}
			}
			System.out.println();
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("Number of cleaned patches: ")
		.append(result.getPatches()).append('\n')
		.append("Current position: ( ")
		.append(result.getCoords().get(X_COORDINATE))
		.append(" , ")
		.append(result.getCoords().get(Y_COORDINATE))
		.append(" )");
		
		System.out.println(builder.toString());
		
		
	}
}
