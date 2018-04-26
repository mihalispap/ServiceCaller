package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foodakai.servicecaller.responses.internal.OntologyRecommenderEntity;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize
public class OntologyRecommenderResponse {

    private String input;

    private List<OntologyRecommenderEntity> ontologies = new ArrayList<>();

    public OntologyRecommenderResponse() {
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<OntologyRecommenderEntity> getOntologies() {
        return ontologies;
    }

    public void setOntologies(List<OntologyRecommenderEntity> ontologies) {
        this.ontologies = ontologies;
    }
}
