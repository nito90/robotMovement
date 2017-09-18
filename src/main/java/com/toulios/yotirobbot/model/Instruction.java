package com.toulios.yotirobbot.model;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of robbot's instruction
 */
public class Instruction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5490603829726610744L;

	private List<Integer> roomSize;
	private List<Integer> coords;
	private List<List<Integer>> patches;
	private String instructions;
	
	public Instruction() {
		super();
	}
	
	public Instruction(List<Integer> roomSize, List<Integer> coords, List<List<Integer>> patches, String instructions) {
		this.roomSize = roomSize;
		this.coords = coords;
		this.patches = patches;
		this.instructions = instructions;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public List<Integer> getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(List<Integer> roomSize) {
		this.roomSize = roomSize;
	}
	public List<Integer> getCoords() {
		return coords;
	}
	public void setCoords(List<Integer> coords) {
		this.coords = coords;
	}
	public List<List<Integer>> getPatches() {
		return patches;
	}
	public void setPatches(List<List<Integer>> patches) {
		this.patches = patches;
	}
	
	
}
