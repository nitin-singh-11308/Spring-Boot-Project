package com.example.MSCafe.exception;

public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException() {
        super("Chef Doesn't Found");
    }

    public ChefNotFoundException(String m) {
        super(m);
    }
}
