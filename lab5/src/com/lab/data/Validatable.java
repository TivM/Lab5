package com.lab.data;

public interface Validatable {
    /**
     * validates all fields after json deserialization
     * @return
     */
    public boolean validate();
}
