package com.toulios.yotirobbot.api;

import java.io.Serializable;

/**
 *
 * Representation of a  exception's response
 *
 */
public class ExceptionResult implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1324773389503578320L;

	/**
     * The http code of the result
     */
    private final int result;

    /**
     * The description of the result
     */
    private final String description;

    public ExceptionResult(int result, String description) {
        this.result = result;
        this.description = description;
    }

    public int getResult() {
        return this.result;
    }

    public String getDescription() {
        return this.description;
    }
}
