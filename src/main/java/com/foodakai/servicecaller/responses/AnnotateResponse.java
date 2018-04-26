package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class AnnotateResponse {

    private String annotation;

    private double score;

    public AnnotateResponse() {
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
