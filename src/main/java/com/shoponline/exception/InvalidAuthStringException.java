package com.shoponline.exception;

public class InvalidAuthStringException extends Exception{

	private String exceptionMessage = "The Auth String is found invalid";

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return exceptionMessage;
	}
}
