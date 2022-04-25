package com.lab.collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lab.collection.CollectionManager;
import com.lab.data.Worker;
import com.lab.json.CollectionDeserializer;
import com.lab.json.LocalDateTimeDeserializer;
import com.lab.json.ZonedDateTimeDeserializer;
import com.lab.json.ZonedDateTimeSerializer;


import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import static com.lab.io.OutputManager.print;
import static com.lab.io.OutputManager.printErr;
//final class gTreeS{
//    public TreeSet<Worker> collection;
//
//    public gTreeS(TreeSet<Worker> collection) {
//        this.collection = collection;
//    }
//
//    public TreeSet<Worker> getCollection() {
//        return collection;
//    }
//}
public class WorkerCollectionManager implements CollectionManager<Worker> {
    private TreeSet<Worker> collection;
    private java.time.ZonedDateTime initDate;
    private HashSet<Long> uniqueIds;


    public String getHelp(){
        return "\r\nhelp : show help for available commands\r\n\r\n" +
                "info : Write to standard output information about the collection " + "(type,\r\n" +
                "initialization date, number of elements, etc.)\r\n\r\n" +
                "show : print to standard output all elements of the collection in\r\n" +
                "string representation\r\n\r\n" +
                "add {element} : add a new element to the collection\r\n\r\n" +
                "update id {element} : update the value of the collection element whose id\r\n" +
                "equal to given\r\n\r\n" +
                "remove_by_id id : remove an element from the collection by its id\r\n\r\n" +
                "clear : clear the collection\r\n\r\n" +
                "save (file_name - optional) : save the collection to a file\r\n\r\n" +
                "load (file_name - optional): load collection from file\r\n\r\n" +
                "execute_script file_name : read and execute script from specified file.\r\n" +
                "The script contains commands in the same form in which they are entered\r\n" +
                "user is interactive.\r\n\r\n" +
                "exit : exit the program (without saving to a file)\r\n\r\n" +
                "remove_lower : remove all items smaller than the specified one from the collection\r\n\r\n" +
                "add_if_min {element} : add a new element to the collection if it\r\n" +
                "the value is less than the smallest element of this collection\r\n\r\n" +
                "history : output the last 8 commands (without their arguments)\r\n\r\n" +
                "sum_of_salary : print the sum of the values of the salary field for all\r\n" +
                "items in the collection\r\n\r\n" +
                "average_of_salary : print the average value of the salary field for all\r\n" +
                "items in the collection\r\n\r\n" +
                "filter_greater_than_salary salary : output elements whose salary field value\r\n" +
                "is greater than the specified one\r\n";
    }


    /**
     * Constructor, set start values
     */
    public WorkerCollectionManager() {
        uniqueIds = new HashSet<>();
        collection = new TreeSet<>();
        initDate = java.time.ZonedDateTime.now();
    }


    public Long generateNextId() {

        if (collection.isEmpty())
            return 1L;
        else {
            Long id = collection.last().getId() + 1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id += 1;
            }
            uniqueIds.add(id);
            return id;
        }
    }

    /**
     * Return collection
     *
     * @return Collection
     */
    public TreeSet<Worker> getCollection() {
        return collection;
    }

    /**
     * Add element to collection
     *-
     * @param worker Element of collection
     */
    public void add(Worker worker) {

        worker.setId(generateNextId());
        collection.add(worker);
        print("Added element:");
        print(worker.toString());
    }

    /**
     * Get information about collection
     *
     * @return Information
     */
    public String getInfo() {

        return "TreeSet of Worker, size: " + Integer.toString(collection.size()) + ", initialization date: " + initDate.toString();
    }

    /**
     * Give info about is this ID used
     *
     * @param id ID
     * @return is it used or not
     */
    public boolean checkID(Long id) {
        for (Worker worker : collection) {
            if (worker.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete element by ID
     *
     * @param id ID
     */
    public void removeByID(Long id) {
        for (Worker worker : collection) {
            if (worker.getId() == id) {
                collection.remove(worker);
                uniqueIds.remove(id);
                print("element #" + Long.toString(id) + " successfully deleted");
                return;
            }
        }
    }

    /**
     * Update element by ID
     *
     * @param id ID
     */
    public void updateID(Long id, Worker newWorker) {
        for (Worker worker : collection) {
            if (worker.getId() == id) {
                newWorker.setId(id);
                collection.remove(worker);
                collection.add(newWorker);
                print("element #" + Long.toString(id) + " successfully updated");
                return;
            }
        }
    }

    /**
     * Get size of collection
     *
     * @return Size of collection
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * clear collection
     */
    public void clear() {

        collection.clear();
        uniqueIds.clear();
    }

    /**
     * remove all items smaller than the specified one from the collection
     * @param worker
     */
    @Override
    public void removeLower(Worker worker) {

        for (Worker nWorker : collection) {
            if (nWorker.compareTo(worker) == -1) {
                collection.remove(nWorker);
                return;
            }
            if (nWorker==null) {
                collection.remove(nWorker);
                return;
            }
        }

    }

    /**
     * Add if ID of element smaller than min in collection
     *
     * @param worker Element
     */
    public void addIfMin(Worker worker) {

        for (Worker nWorker : collection) {
            if (nWorker==null){
                add(worker);
                return;
            }
            if (worker==null){
                printErr("unable to add");
                return;
            }
            if (worker.compareTo(nWorker) == 1) {
                printErr("unable to add");
                return;
            }
        }
        add(worker);
    }

    /**
     * output elements whose salary field value is greater than the specified one
     * @param salary
     */
    @Override
    public void printGreaterThanSalary(Long salary) {

        for (Worker worker : collection) {
            if (worker.getSalary() > salary){
                print(worker.toString());
            }
            else if (worker.getSalary() == null){
                continue;
            }
            else
                print("no one has a bigger salary");
        }
    }

    /**
     * print the sum of the values of the salary field for all elements of the collection
     * @return
     */
    @Override
    public Long sumOfSalary() {

        Long sum = 0L;
        for (Worker worker : collection) {
            if (worker.getSalary()!=null)
                sum+=worker.getSalary();
            else
                continue;

        }
        return sum;
    }

    /**
     * print the average value of the salary field for all items in the collection
     * @return
     */
    @Override
    public Long averageOfSalary() {

        Long averageSalary = 0L;
        averageSalary = (long)sumOfSalary()/ (long)getSize();
        return averageSalary;
    }

    public boolean deserializeCollection(String json){
        boolean success = true;
        try {
            if (json == null || json.equals("")){
                collection =  new TreeSet<>();
            } else {
                Type collectionType = new TypeToken<TreeSet<Worker>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                        .registerTypeAdapter(collectionType, new CollectionDeserializer(uniqueIds))
                        .create();
                collection = gson.fromJson(json.trim(), collectionType);
            }
        } catch (Exception e){
            success = false;
            printErr("wrong json data");
            System.out.println(e.getMessage());;

        }
        return success;
    }

    public String serializeCollection(){
        if (collection == null || collection.isEmpty()) return "";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .setPrettyPrinting().create();
        String json = gson.toJson(collection);
        return json;
    }



}
