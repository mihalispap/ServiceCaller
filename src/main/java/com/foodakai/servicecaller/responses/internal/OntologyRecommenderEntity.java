package com.foodakai.servicecaller.responses.internal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class OntologyRecommenderEntity {

    private double score;

    private String ontology_name;

    public OntologyRecommenderEntity() {
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getOntology_name() {
        return ontology_name;
    }

    public void setOntology_name(String ontology_name) {
        this.ontology_name = ontology_name;
    }
}
