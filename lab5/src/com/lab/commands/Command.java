package com.lab.commands;

import com.lab.exceptions.CommandException;
import com.lab.exceptions.InvalidDataException;
@FunctionalInterface
/**
 * Command callback interface
 */
public interface Command {
    public void run(String arg) throws CommandException, InvalidDataException;
}

