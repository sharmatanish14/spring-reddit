package com.learning.springreddit.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message){
        super(message);
    }
}
