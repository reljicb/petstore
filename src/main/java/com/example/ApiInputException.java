package com.example;

import static com.google.common.base.Preconditions.*;

import org.springframework.http.HttpStatus;

public class ApiInputException extends Exception
{
	private static final long serialVersionUID = -7732116908517085647L;

	private HttpStatus status;

	public ApiInputException(HttpStatus status, String message)
	{
		super(message);
		this.status = checkNotNull(status);
	}

	public HttpStatus getStatus()
	{
		return status;
	}
}
