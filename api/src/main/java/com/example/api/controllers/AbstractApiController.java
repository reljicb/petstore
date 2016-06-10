package com.example.api.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.ApiInputException;

@ControllerAdvice
public abstract class AbstractApiController
{
	@ExceptionHandler(ApiInputException.class)
	public ResponseEntity handleApiExceptions(ApiInputException e) throws IOException
	{

		if (e.getStatus() == HttpStatus.NOT_FOUND)
		{
			return ResponseEntity.notFound()
			      .header("error", e.getMessage())
			      .build();
		}

		if (e.getStatus() == HttpStatus.BAD_REQUEST)
		{
			return ResponseEntity.badRequest()
			      .header("error", e.getMessage())
			      .build();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
