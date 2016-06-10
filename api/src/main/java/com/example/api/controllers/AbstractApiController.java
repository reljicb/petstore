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
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorMessage> handleApiExceptions(Throwable t) throws IOException
	{
		if (t instanceof ApiInputException)
		{
			ApiInputException e = (ApiInputException) t;

			return ResponseEntity
			      .status(e.getStatus())
			      .body(new ErrorMessage(e.getMessage()));
		}

		return ResponseEntity
		      .status(HttpStatus.INTERNAL_SERVER_ERROR)
		      .body(new ErrorMessage(t.getMessage()));
	}

	private class ErrorMessage
	{
		public String error;

		public ErrorMessage(String error)
		{
			this.error = error;
		}
	}

}
