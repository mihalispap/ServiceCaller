package com.foodakai.servicecaller.controllers;

import com.foodakai.servicecaller.responses.JsonizePDFResponse;
import com.foodakai.servicecaller.utils.Utilities;
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
public class JsonizePDF {

    @RequestMapping(value="/jsonize", method={RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String call(
            @RequestParam("file") MultipartFile file
    ){


        String filename = "pdf."+System.currentTimeMillis() + ".upld."+file.getSize()+".pdf";
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

        JsonizePDFResponse response = new JsonizePDFResponse();

        try {
            response.setText(Utilities.pdf2text(filename));
        }
        catch(Exception e){
            response.setText("unable to extract");
        }

        try{
            Files.delete(Paths.get(filename));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        return gson.toJson(response);
    }
}























