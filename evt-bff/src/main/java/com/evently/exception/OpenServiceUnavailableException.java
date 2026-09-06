package com.evently.exception;

public class OpenServiceUnavailableException extends RuntimeException{
    public OpenServiceUnavailableException(String message){
        super(message);
    }
}
