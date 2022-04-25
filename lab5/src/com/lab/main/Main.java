package com.lab.main;

import com.lab.collection.CollectionManager;
import com.lab.collection.WorkerCollectionManager;
import com.lab.commands.CommandManager;
import com.lab.data.Worker;
import com.lab.file.FileManager;
import com.lab.io.ConsoleInputManager;
import com.lab.io.InputManager;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static com.lab.io.OutputManager.print;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        FileManager fileManager = new FileManager();
        CollectionManager<Worker> collectionManager = new WorkerCollectionManager();
        if (args.length!=0){
            fileManager.setPath(args[0]);
            collectionManager.deserializeCollection(fileManager.read());
        } else{
            print("no file passed by argument. you can load file using load command");
        }

        InputManager consoleManager = new ConsoleInputManager();
        CommandManager commandManager = new CommandManager(collectionManager,consoleManager,fileManager);
        commandManager.consoleMode();


    }


}
