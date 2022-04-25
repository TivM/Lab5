package com.lab.commands;

import com.lab.collection.CollectionManager;
import com.lab.exceptions.NoSuchCommandException;
import com.lab.exceptions.RecursiveScriptExecuteException;
import com.lab.data.Worker;
import com.lab.exceptions.*;
import com.lab.file.ReaderWriter;
import com.lab.io.ConsoleInputManager;
import com.lab.io.FileInputManager;
import com.lab.io.InputManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import static com.lab.io.OutputManager.print;
import static com.lab.io.OutputManager.printErr;

public class CommandManager implements Commandable {
    private Map<String,Command> map;
    private CollectionManager<Worker> collectionManager;
    private InputManager inputManager;
    private ReaderWriter fileManager;
    private boolean isRunning;
    private String currentScriptFileName;
    private static final LinkedList<String> history = new LinkedList<>();
    private static Stack<String> callStack = new Stack<>();

    public void clearStack(){
        callStack.clear();
    }

    public CommandManager(CollectionManager<Worker> cManager, InputManager iManager, ReaderWriter fManager){

        isRunning = false;
        this.inputManager = iManager;
        this.collectionManager = cManager;
        this.fileManager = fManager;

        currentScriptFileName = "";
        map = new HashMap<String,Command>();


        addCommand("info", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("info");
            print(collectionManager.getInfo());
        });

        addCommand("help", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("help");
            print(collectionManager.getHelp());
        });

       // addCommand("help", (a)->print(getHelp()));


        addCommand("show", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("show");
            if (collectionManager.getCollection().isEmpty()) print("collection is empty");
            else print(collectionManager.serializeCollection());
        });

        addCommand("add", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("add");
            collectionManager.add(inputManager.readWorker());
        });

        addCommand("update", (arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("update");
            Long id = 0L;
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Long.parseLong(arg);
            } catch (NumberFormatException e){
                throw new InvalidCommandArgumentException("id must be Long");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (!collectionManager.checkID(id)) throw new InvalidCommandArgumentException("no such id");

            collectionManager.updateID(id, inputManager.readWorker());

        });

        addCommand("remove_by_id", (arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("remove_by_id");
            Long id = 0L;
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }
            try{
                id = Long.parseLong(arg);
            } catch (NumberFormatException e){
                throw new InvalidCommandArgumentException("id must be Long");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            if (!collectionManager.checkID(id)) throw new InvalidCommandArgumentException("no such id");

            collectionManager.removeByID(id);
        });

        addCommand("clear", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("clear");
            collectionManager.clear();
        });

        addCommand("save", (arg)->{
            if (history.size() == 8){
                history.removeLast();
            }
            history.addFirst("save "+arg);
            if (!(arg == null ||arg.equals(""))) fileManager.setPath(arg);
            if (collectionManager.getCollection().isEmpty()) print("collection is empty");
            if(!fileManager.write(collectionManager.serializeCollection())) throw new CommandException("cannot save collection");

        });

        addCommand("execute_script",(arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("execute_script");
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }

            if (callStack.contains(currentScriptFileName)) throw new RecursiveScriptExecuteException();

            callStack.push(currentScriptFileName);
            CommandManager process = new CommandManager(collectionManager, inputManager, fileManager);
            process.fileMode(arg);
            callStack.pop();
        });

        addCommand("exit", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("exit");
            isRunning=false;
        });

        addCommand("remove_lower", (arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("remove_lower");
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            collectionManager.removeLower(inputManager.readWorker());
        });

        addCommand("add_if_min", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("add_if_min");
            collectionManager.addIfMin(inputManager.readWorker());
        });

        addCommand("history", (a)-> {
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("history");
            print(historyCommand());
        });

        addCommand("average_of_salary", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("average_of_salary");
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            print(collectionManager.averageOfSalary());
        });

        addCommand("sum_of_salary", (a)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("sum_of_salary");
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            print(collectionManager.sumOfSalary());
        });

        addCommand("filter_greater_than_salary", (arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("filter_greater_then_salary");
            Long salary = 0L;
            if (arg == null || arg.equals("")){
                throw new MissedCommandArgumentException();
            }
            try{
                salary = Long.parseLong(arg);
            } catch (NumberFormatException e){
                throw new InvalidCommandArgumentException("id must be Long");
            }
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            collectionManager.printGreaterThanSalary(Long.parseLong(arg));
        });

        addCommand("load", (arg)->{
            if (history.size() == 8)
                history.removeLast();
            history.addFirst("load");
            if (!(arg == null ||arg.equals(""))) fileManager.setPath(arg);
            String data = fileManager.read();
            if(data.equals("")) throw new CommandException("cannot load, data not found");
            boolean success = collectionManager.deserializeCollection(data);
            if(success) print("collection successfully loaded");
        });

    }

    public void addCommand(String key, Command c){
        map.put(key, c);
    }

    public void runCommand(String s, String arg){
        try{
            if (! hasCommand(s)) throw new NoSuchCommandException();
            map.get(s).run(arg);
        }
        catch(CommandException|InvalidDataException e){
            printErr(e.getMessage());
        }
    }
    public void runCommand(String s){
        runCommand(s,null);
    }
    public boolean hasCommand(String s){
        return map.containsKey(s);
    }
    public void consoleMode(){
        inputManager = new ConsoleInputManager();
        isRunning = true;
        while(isRunning){
            System.out.print("enter command (help to get command list): ");
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
        }
    }
    public void fileMode(String path){
        currentScriptFileName = path;
        isRunning = true;
        inputManager = new FileInputManager(path);
        isRunning = true;
        while(isRunning && inputManager.getScanner().hasNextLine()){
            CommandWrapper pair = inputManager.readCommand();
            runCommand(pair.getCommand(), pair.getArg());
        }
    }
    public String historyCommand() {
        String a = "";
        int i = 0;
        for (String item : history) {
            i++;
            System.out.println(i + " - " + item + "\n");
        }
        return a;
    }



}










