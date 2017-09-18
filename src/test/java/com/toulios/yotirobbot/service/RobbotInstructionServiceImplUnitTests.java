package com.toulios.yotirobbot.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.toulios.yotirobbot.exceptions.InstructionValidationException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.toulios.yotirobbot.model.Instruction;
import com.toulios.yotirobbot.model.Result;

public class RobbotInstructionServiceImplUnitTests {

	@Autowired
	RobotInstructionServiceImpl service = new RobotInstructionServiceImpl();

	private Instruction instruction = new Instruction();

	@Before
	public void setUp(){

		List<Integer> coords = new ArrayList<>();
		coords.add(1);
		coords.add(1);
		instruction.setCoords(coords);

		List<Integer> patch1 = new ArrayList<>();
		patch1.add(2);
		patch1.add(2);

		List<Integer> patch2 = new ArrayList<>();
		patch2.add(3);
		patch2.add(2);

		List<Integer> patch3 = new ArrayList<>();
		patch3.add(4);
		patch3.add(2);

		List<Integer> patch4 = new ArrayList<>();
		patch4.add(4);
		patch4.add(3);

		List<List<Integer>> patches = new ArrayList<>();
		patches.add(patch1);
		patches.add(patch2);
		patches.add(patch3);
		patches.add(patch4);

		List<Integer> room = new ArrayList<>();
		room.add(10);
		room.add(5);

		instruction.setCoords(coords);
		instruction.setPatches(patches);
		instruction.setRoomSize(room);
		instruction.setInstructions("ENEEN");
	}

	@Test(expected = InstructionValidationException.class)
	public void test_executeCommand_nullInstruction() {
		service.executeInstruction(null);
	}

	@Test(expected = InstructionValidationException.class)
	public void test_executeCommand_emptyRoomSize() {
		instruction.getRoomSize().clear();
		service.executeInstruction(null);
	}

	@Test(expected = InstructionValidationException.class)
	public void test_executeCommand_nullRoomSize() {
		instruction.setRoomSize(null);
		service.executeInstruction(null);
	}

	@Test(expected = InstructionValidationException.class)
	public void test_executeCommand_emptyCoordinates() {
		instruction.getCoords().clear();
		service.executeInstruction(null);
	}

	@Test(expected = InstructionValidationException.class)
	public void test_executeCommand_nullCoordinates() {
		instruction.setCoords(null);
		service.executeInstruction(null);
	}

	@Test
	public void test_executeCommand_success() {
		Result result = service.executeInstruction(instruction);
		assertEquals(4, result.getPatches().intValue());
		assertEquals(4, result.getCoords().get(0).intValue());
		assertEquals(3, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successEmptyPatchesList() {
		instruction.getPatches().clear();
		Result result = service.executeInstruction(instruction);
		assertEquals(0, result.getPatches().intValue());
		assertEquals(4, result.getCoords().get(0).intValue());
		assertEquals(3, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successNullPatchesList() {
		instruction.setPatches(null);
		Result result = service.executeInstruction(instruction);
		assertEquals(0, result.getPatches().intValue());
		assertEquals(4, result.getCoords().get(0).intValue());
		assertEquals(3, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successEmptyInstructions() {
		instruction.setInstructions("");
		Result result = service.executeInstruction(instruction);
		assertEquals(0, result.getPatches().intValue());
		assertEquals(1, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successNullInstructions() {
		instruction.setInstructions(null);
		Result result = service.executeInstruction(instruction);
		assertEquals(0, result.getPatches().intValue());
		assertEquals(1, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successWithNorthLimitation() {
		instruction.setInstructions("NENNNNNNN");
		Result result = service.executeInstruction(instruction);
		assertEquals(1, result.getPatches().intValue());
		assertEquals(2, result.getCoords().get(0).intValue());
		assertEquals(4, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successWithEastLimitation() {
		instruction.setInstructions("NEEEEEEEEEEEEEEEEEEEEEEEE");
		Result result = service.executeInstruction(instruction);
		assertEquals(3, result.getPatches().intValue());
		assertEquals(9, result.getCoords().get(0).intValue());
		assertEquals(2, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_executeCommand_successWithWestLimitation() {
		instruction.setInstructions("NEWWWWWWWWWWWWWWW");
		Result result = service.executeInstruction(instruction);
		assertEquals(1, result.getPatches().intValue());
		assertEquals(0, result.getCoords().get(0).intValue());
		assertEquals(2, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveRight_success(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveRight(room, result);

		assertEquals(2, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveLeft_success(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveLeft(room, result);

		assertEquals(0, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveUp_success(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveUp(room, result);

		assertEquals(1, result.getCoords().get(0).intValue());
		assertEquals(2, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveDown_success(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveDown(room, result);

		assertEquals(1, result.getCoords().get(0).intValue());
		assertEquals(0, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveRight_successMaximumLimit(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveRight(room, result);

		assertEquals(2, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveLeft_successMaximumLimit(){
		int[][] room = new int[5][10];
		Result result = new Result(0, instruction.getCoords());
		service.moveLeft(room, result);

		assertEquals(0, result.getCoords().get(0).intValue());
		assertEquals(1, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveUp_successMaximumLimit(){
		int[][] room = new int[5][10];

		instruction.getCoords().set(0, 4);
		instruction.getCoords().set(1, 4);

		Result result = new Result(0, instruction.getCoords());
		service.moveUp(room, result);

		assertEquals(4, result.getCoords().get(0).intValue());
		assertEquals(4, result.getCoords().get(1).intValue());
	}

	@Test
	public void test_moveDown_successMaximumLimit(){
		int[][] room = new int[5][10];

		instruction.getCoords().set(0, 4);
		instruction.getCoords().set(1, 0);

		Result result = new Result(0, instruction.getCoords());
		service.moveDown(room, result);

		assertEquals(4, result.getCoords().get(0).intValue());
		assertEquals(0, result.getCoords().get(1).intValue());
	}
}
