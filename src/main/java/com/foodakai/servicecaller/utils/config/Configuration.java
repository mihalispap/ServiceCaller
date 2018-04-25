package com.foodakai.servicecaller.utils.config;

import static java.lang.String.format;

public final class Configuration {

    private String endpoint;

    private String mediatype;

    private Input input;

    private Output output;

    public Configuration() {
    }

    public Output getOutput() {
        return output;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }
}
