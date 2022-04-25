package com.lab.data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Worker implements Collectionable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null,
    // Значение этого поля должно генерироваться автоматически
    private Long salary; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.ZonedDateTime endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле может быть null


    public ZonedDateTime getCreationDate() {
       return creationDate;
    }




    /**
     * constructor, just set fields
     * @param name
     * @param coordinates
     * @param salary
     * @param endDate
     * @param position
     * @param status
     * @param person
     */
    public Worker(String name, Coordinates coordinates, Long salary, ZonedDateTime endDate, Position position,
                  Status status, Person person){

        this.creationDate = ZonedDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.person = person;
        this.creationDate  = ZonedDateTime.now();
    }

    /**
     * @return int
     */
    public Long getId(){
        return id;
    }


    /**
     * sets ID, useful for replacing elements in collection
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getName(){
        return name;
    }


    /**
     * @return long
     */
    public Long getSalary(){
        return salary;
    }

    /**
     * @return ZonedDateTime
     */
    public ZonedDateTime getEndDate(){
        return endDate;
    }

    /**
     * @return String
     */
    @Override
    public String toString(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strCreationDate = dateFormatter.format(creationDate);
        String strEndDate = "";
        if (getEndDate()!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            strEndDate = getEndDate().format(formatter);
        }
        String s = "";
        s += "{\n";
        s += "  \"id\" : " + Long.toString(id) + ",\n";
        s += "  \"name\" : " + "\"" + name + "\"" + ",\n";
        s += "  \"coordinates\" : " + coordinates.toString() + ",\n";
        s += "  \"creationDate\" : " + "\"" + strCreationDate + "\"" + ",\n";
        if (salary!=null) s += "  \"salary\" : " + Long.toString(salary) + ",\n";
        if (endDate!=null) s += "  \"endDate\" : " +  "\"" + strEndDate + "\"" + ",\n";
        s += "  \"position\" : " + "\"" + position.toString() + "\"" + ",\n";
        s += "  \"status\" : " + "\"" + status.toString() + "\"" + ",\n";
        s += "  \"person\" : " + person.toString() + "\n";
        s += "}";
        return s;
    }

    /**
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass()!= obj.getClass()) return false;
        Worker another = (Worker)obj;
        return this.getId() == another.getId();
    }

    /**
     * @param worker
     * @return int
     */
    public int compareTo(Collectionable worker){
        if (worker.getSalary() == null)
            return -1;
        if (this.salary == null)
            return 1;
        int res = Long.compare(this.salary, worker.getSalary());
        return res;
    }

    @Override
    public boolean validate() {
        return (
                coordinates!=null && coordinates.validate() &&
                        (person==null ||(!person.equals("") && person.validate())) &&
                        (salary==null ||(!salary.equals("") && salary>0)) && (id>0) &&
                        name!=null && !name.equals("") &&
                        status!=null &&
                        creationDate!=null &&
                        position!=null
        );
    }
}
