package com.lab.exceptions;
/**
 * thrown when command argument is invalid
 */
public class InvalidDateFormatException extends InvalidDataException{
    public InvalidDateFormatException() {
        super("date format must be yyyy-MM-dd HH:mm:ss");
    }
}

