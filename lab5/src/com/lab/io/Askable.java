package com.lab.io;

import com.lab.exceptions.InvalidDataException;
@FunctionalInterface
/**
 *user input callback
 */
public interface Askable<T> {
    T ask() throws InvalidDataException;

}
