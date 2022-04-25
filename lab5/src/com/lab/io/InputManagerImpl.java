package com.lab.io;
import com.lab.commands.CommandWrapper;
import com.lab.data.*;
import com.lab.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Scanner;

import static com.lab.utils.DateConverter.*;

public class InputManagerImpl implements InputManager{
    private Scanner scanner;

    public InputManagerImpl(Scanner sc){
        scanner = sc;
        scanner.useDelimiter("\n");
    }

    public Scanner getScanner(){
        return scanner;
    }

    public void setScanner(Scanner sc){
        scanner = sc;
    }
    public String readName() throws EmptyStringException {
        String s = scanner.nextLine().trim();
        if (s.equals("")){
            throw new EmptyStringException();
        }
        return s;
    }

    public Integer readWeight() throws InvalidNumberException{
        String w = scanner.nextLine().trim();
        Integer p = 0;
        if(w.equals("")){
            return null;
        }
        else try{
            p = Integer.parseInt(w);
        }

        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if (p<=0) throw new InvalidNumberException("must be greater than 0");
        return p;
    }

    public LocalDateTime readBirthday() throws InvalidDateFormatException {
        String buf = scanner.nextLine().trim();
        if(buf.equals("")){
            return null;
        }
        else{
            return parseLocalDateTime(buf);
        }
    }

    public double readXCoord() throws InvalidNumberException {
        double x;
        try{
            x = Double.parseDouble(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if (Double.isInfinite(x) || Double.isNaN(x) || (Double)x>850)
            throw new InvalidNumberException("invalid double value, must be less than 850");
        return x;
    }

    public Integer readYCoord() throws InvalidNumberException{
        Integer y;
        try{
            y = Integer.parseInt(scanner.nextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if (y==null) throw new InvalidNumberException("can't be null");
        return y;
    }

    public Coordinates readCoords() throws InvalidNumberException{
        double x = readXCoord();
        Integer y = readYCoord();
        Coordinates coord = new Coordinates(x,y);
        return coord;
    }

    public Long readSalary() throws InvalidNumberException{
        String w = scanner.nextLine().trim();
        Long s = 0L;
        if(w.equals("")){
            return null;
        }
        try{
            s = Long.parseLong(w);
        }

        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        if(s<=0) throw new InvalidNumberException("must be greater than 0");

        return s;
    }

    public ZonedDateTime readEndDate() throws InvalidDateFormatException {
        String buf = scanner.nextLine().trim();
        if(buf.equals("")){
            return null;
        }
        else{
            return parseZonedDateTime(buf);
        }
    }

    public Position readPosition() throws InvalidEnumException {
        String s = scanner.nextLine().trim();

        try{
            return Position.valueOf(s);
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }

    }

    public Status readStatus() throws InvalidEnumException{
        String s = scanner.nextLine().trim();
        try{
            return Status.valueOf(s);
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }
    }

    public Color readColor() throws InvalidEnumException{
        String s = scanner.nextLine().trim();
        if(s.equals("")){
            return null;
        }
        try{
            return Color.valueOf(s);
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }
    }

    public Person readPerson() throws InvalidDataException{
        Integer weight = readWeight();
        LocalDateTime birthday = readBirthday();
        Color eyeColor = readColor();
        return new Person(birthday, weight, eyeColor);
    }

    public Worker readWorker() throws InvalidDataException {
        Worker worker = null;
        String name = readName();
        Coordinates coords = readCoords();
        Long salary = readSalary();
        ZonedDateTime date = readEndDate();
        Position pos = readPosition();
        Status stat = readStatus();
        Person person = readPerson();
        worker = new Worker(name, coords, salary, date, pos, stat, person);

        return worker;

    }

    public CommandWrapper readCommand(){
        String cmd = scanner.nextLine();
        if (cmd.contains(" ")){ //if command has argument
            String arr [] = cmd.split(" ",2);
            cmd = arr[0];
            String arg = arr[1];
            return new CommandWrapper(cmd,arg);
        } else {
            return new CommandWrapper(cmd);
        }
    }
}
