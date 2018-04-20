package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class CallEndpointResponse {

    private String endpoint;
    private String input;

    public CallEndpointResponse(String endpoint, String input) {
        this.endpoint = endpoint;
        this.input = input;
    }

    public CallEndpointResponse() {
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
