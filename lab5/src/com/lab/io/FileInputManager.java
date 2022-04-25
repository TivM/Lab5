package com.lab.io;

import com.lab.file.FileManager;

import java.util.Scanner;

public class FileInputManager extends InputManagerImpl{
    public FileInputManager(String path){
        super(new Scanner(new FileManager(path).read()));
        getScanner().useDelimiter("\n");
    }
}
