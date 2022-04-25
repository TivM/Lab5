package com.lab.exceptions;
/**
 * thrown when user input wrong command
 */
public class InvalidCommandArgumentException extends CommandException{
    public InvalidCommandArgumentException(String s) {
        super(s);
    }
}
