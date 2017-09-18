package com.toulios.yotirobbot.web.mgt;

import com.toulios.yotirobbot.api.ExceptionResult;
import com.toulios.yotirobbot.exceptions.InstructionValidationException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.toulios.yotirobbot.model.Instruction;
import com.toulios.yotirobbot.model.Result;
import com.toulios.yotirobbot.service.RobotInstructionServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/instructions")
public class InstructionController {

	@Autowired
	private RobotInstructionServiceImpl service;

	@ApiResponses({
			@ApiResponse(code = 200, message = "Returns the current position of the hover and how many patches it cleaned."),
			@ApiResponse(code = 400, message = "Validation problem with the instruction info"),
	})
	@RequestMapping(value = "/execute", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Result executeCommand(@RequestBody Instruction instruction) {
		return service.executeInstruction(instruction);
	}

	@ExceptionHandler(InstructionValidationException.class)
	public ExceptionResult invalid(Exception e, HttpServletResponse response) throws IOException {
		return new ExceptionResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
}
