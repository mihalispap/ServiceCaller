package com.foodakai.servicecaller.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class URLSourcerResponse {

    private String input="";

    private String output="";

    private byte[] binaryoutput;

    public URLSourcerResponse() {
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public byte[] getBinaryoutput() {
        return binaryoutput;
    }

    public void setBinaryoutput(byte[] binaryoutput) {
        this.binaryoutput = binaryoutput;
    }
}
