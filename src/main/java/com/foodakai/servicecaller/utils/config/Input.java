package com.foodakai.servicecaller.utils.config;

import java.util.Map;

public class Input {

    private String method;

    private String url_pattern;

    private Map<String, Parameter> params;

    public Input() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl_pattern() {
        return url_pattern;
    }

    public void setUrl_pattern(String url_pattern) {
        this.url_pattern = url_pattern;
    }

    public Map<String, Parameter> getParams() {
        return params;
    }

    public void setParams(Map<String, Parameter> params) {
        this.params = params;
    }
}
