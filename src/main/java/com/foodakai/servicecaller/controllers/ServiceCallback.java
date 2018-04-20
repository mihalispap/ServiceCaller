package com.foodakai.servicecaller.controllers;

import com.foodakai.servicecaller.responses.CallEndpointResponse;
import com.foodakai.servicecaller.utils.config.Configuration;
import com.foodakai.servicecaller.utils.Utilities;
import com.foodakai.servicecaller.utils.processors.RequestProcessor;
import com.foodakai.servicecaller.utils.processors.ResponseProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@EnableAutoConfiguration
public class ServiceCallback {

    @RequestMapping(value="/call", method={RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String call(
            @RequestParam("file") MultipartFile file
            ){

        CallEndpointResponse response = new CallEndpointResponse();

        //response.setEndpoint(endpoint);
        //response.setInput(input);

        /*
        *   DOI service example:
        *       https://api.crossref.org/works/10.1371/journal.pntd.0004314
        * */

        String filename = "fl."+System.currentTimeMillis() + ".upld."+file.getSize();
        if (!file.isEmpty()) {
            try {

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(
                                new File(filename)));
                stream.write(bytes);
                stream.close();
                //return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        }

        Utilities util;
        Configuration config = Utilities.loadConfiguration(filename);

        String resp="";
        RequestProcessor reqproc = new RequestProcessor();
        ResponseProcessor respproc = new ResponseProcessor();
        try {

            resp = reqproc.run(config);
            resp = respproc.run(resp, config);

        } catch (Exception e) {
            e.printStackTrace();
            resp="{\"error\": \"some error occured\"}";
        }

        try{
            Files.delete(Paths.get(filename));
        }
        catch(Exception e){
            e.printStackTrace();
            resp="{\"error\": \"some error occured\"}";
        }

        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        return resp;
    }

}
























