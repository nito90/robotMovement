package com.toulios.yotirobbot.model;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of instruction execution result
 */
public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2668381895049730489L;

	private Integer patches;
	private List<Integer> coords;
	
	public Result() {
		super();
	}
	
	public Result(Integer patches, List<Integer> coords) {
		this.patches = patches;
		this.coords = coords;
	}
	public Integer getPatches() {
		return patches;
	}
	public void setPatches(Integer patches) {
		this.patches = patches;
	}
	public List<Integer> getCoords() {
		return coords;
	}
	public void setCoords(List<Integer> coords) {
		this.coords = coords;
	}
	
	
}
