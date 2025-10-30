package com.example.MSCafe.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException (String m) {
        super(m);
    }
}
