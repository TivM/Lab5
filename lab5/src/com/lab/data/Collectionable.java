package com.lab.data;

public interface Collectionable extends Comparable<Collectionable>, Validatable {
    public Long getId();
    /**
     * sets id, useful for replacing object in collection
     * @param id
     */
    public void setId(Long id);

    public Long getSalary();

    public String getName();

    /**
     * compairs two objects
     */
    public int compareTo(Collectionable worker);


    public boolean validate();




}
