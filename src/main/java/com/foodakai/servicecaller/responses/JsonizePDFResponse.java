package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize
public class JsonizePDFResponse {

    private String text;

    private String title;

    private String important_text="";

    private List<String> keywords;

    private List<String> authors;

    private List<String> subjects;

    private List<String> images;

    /*
    *   TODO:
    *       validate whether list of strings is the most appropriate
    *
    * */
    private List<String> tables;

    public JsonizePDFResponse() {
    }

    public String getImportant_text() {
        return important_text;
    }

    public void setImportant_text(String important_text) {
        this.important_text = important_text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
