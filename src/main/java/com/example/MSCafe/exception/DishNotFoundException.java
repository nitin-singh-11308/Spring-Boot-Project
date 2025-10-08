package com.example.MSCafe.exception;

public class DishNotFoundException extends RuntimeException{
    public DishNotFoundException(){
        super("Dish Doesn't Found");
    }

    public DishNotFoundException(String m){
        super(m);
    }
}
