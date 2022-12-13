package com.shoponline.exception;

public class JwtTokenExpiredException extends Exception{

	public JwtTokenExpiredException(String msg){
        super(msg);
    }
}
