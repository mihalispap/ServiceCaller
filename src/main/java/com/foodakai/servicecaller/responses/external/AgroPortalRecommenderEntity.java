package com.foodakai.servicecaller.responses.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgroPortalRecommenderEntity {

    private double evaluationScore;

    private List<AgroPortalOntologyEntity> ontologies;

    public AgroPortalRecommenderEntity() {
    }

    public double getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(double evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public List<AgroPortalOntologyEntity> getOntologies() {
        return ontologies;
    }

    public void setOntologies(List<AgroPortalOntologyEntity> ontologies) {
        this.ontologies = ontologies;
    }
}
