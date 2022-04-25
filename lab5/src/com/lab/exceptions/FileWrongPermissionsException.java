package com.lab.exceptions;
/**
 * thrown when if unable to read file
 */
public class FileWrongPermissionsException extends FileException{
    public FileWrongPermissionsException(){
        super("cannot read file");
    }
}
