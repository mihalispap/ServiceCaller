package com.foodakai.servicecaller.utils.config;

import java.util.Map;

public class Output {

    private String mediatype;

    private String schema;

    private Map<String, String> values;

    public Output() {
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
