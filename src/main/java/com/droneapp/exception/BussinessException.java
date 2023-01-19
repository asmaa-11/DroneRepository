package com.droneapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BussinessException extends Exception {
	
	public BussinessException(String exception) {
		super(exception);
	}
}