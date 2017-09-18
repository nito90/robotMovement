package com.toulios.yotirobbot.exceptions;

/**
 * Representation of a validation exeption for yoti-robbot instruction
 * 
 *
 */
public class InstructionValidationException extends RuntimeException{

    private static final long serialVersionUID = 7032183996150981826L;

    public InstructionValidationException(String message){
        super(message);
    }
}
