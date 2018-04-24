package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashSet;
import java.util.Set;

@JsonSerialize
public class UniqueValuesResponse {

    private int column_no;

    private Set<String> values = new HashSet<>();

    public UniqueValuesResponse() {
    }

    public int getColumn_no() {
        return column_no;
    }

    public void setColumn_no(int column_no) {
        this.column_no = column_no;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }
}
