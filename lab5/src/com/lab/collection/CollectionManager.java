package com.lab.collection;

import java.util.TreeSet;

public interface CollectionManager<T> {
    /**
     * generates new unique ID for collection
     * @return
     */
    public Long generateNextId();

    //public void sort();

    public TreeSet<T> getCollection();

    public String getHelp();
    /**
     * adds new element
     * @param element
     */
    public void add(T element);

    /**
     * get last 8 commands
     */
    //public void historyCommand();
    /**
     * get information about collection
     * @return info
     */
    public String getInfo();

    /**
     * checks if collection contains element with particular id
     * @param id
     * @return
     */
    public boolean checkID(Long id);

    /**
     * removes element by id
     * @param id
     */
    public void removeByID(Long id);

    /**
     * updates element by id
     * @param id
     * @param newElement
     */
    public void updateID(Long id, T newElement);

    /**
     * get collection size
     * @return
     */
    public int getSize();

    /**
     *  clear collection
     */
    public void clear();

    /**
     * remove all items smaller than the specified one from the collection
     * @param element
     */
    public void removeLower(T element);

    /**
     * adds element if it is smaller than min
     * @param element
     */
    public void addIfMin(T element);

    /**
     * output elements whose salary field value is greater than the specified one
     * @param salary
     */
    public void printGreaterThanSalary(Long salary);

    /**
     * print the sum of the values of the salary field for all elements of the collection
     */
    public Long sumOfSalary();

    /**
     * print the average value of the salary field for all items in the collection
     */
    public Long averageOfSalary();

    /**
     * convert collection to json
     * @param json
     * @return true if success, false if not
     */
    public boolean deserializeCollection(String json);

    /**
     * parse collection from json
     * @return
     */
    public String serializeCollection();

}
