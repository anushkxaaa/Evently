package com.evently.exception;

public class OpenServiceNotFoundException extends RuntimeException{
    public OpenServiceNotFoundException(String message){
        super(message);
    }
}
