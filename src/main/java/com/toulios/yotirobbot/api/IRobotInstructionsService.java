package com.toulios.yotirobbot.api;

import com.toulios.yotirobbot.model.Instruction;
import com.toulios.yotirobbot.model.Result;

/**
 * Interface of the robot actions
 */
public interface IRobotInstructionsService {

	Integer INCREMENT = 1;
	Integer X_COORDINATE = 0;
	Integer Y_COORDINATE = 1;
	Integer IS_PATCHED = 1;
	Integer CLEANED = 0;
	Integer MINIMUM_LIMIT_WALL = 0;
	String HOVER = "#";
	String CLEAN_PLACE = ".";
	String PATCH = "@";

	/**
	 * Executes the command
	 * 
	 * @param instruction
	 * @return
	 */
	public Result executeInstruction(Instruction instruction);
	
	/**
	 * Handle the right movement and calculate the result after the move
	 * 
	 * @param room
	 * @param result
	 */
	public void moveRight(int[][] room, Result result);
	
	/**
	 * Handle the left movement and calculate the result after the move
	 * 
	 * @param room
	 * @param result
	 */
	public void moveLeft(int[][] room, Result result);
	
	/**
	 * Handle the up movement and calculate the result after the move
	 * 
	 * @param room
	 * @param result
	 */
	public void moveUp(int[][] room, Result result);
	
	/**
	 * Handle the down movement and calculate the result after the move
	 * 
	 * @param room
	 * @param result
	 */
	public void moveDown(int[][] room, Result result);
}
