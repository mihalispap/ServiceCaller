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

import javax.rmi.CORBA.Util;
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
                Utilities.save_file(filename, file);
            } catch (Exception e) {
                e.printStackTrace();
                return "{\"error\":\"message\"}";
            }
        }

        JsonizePDFResponse response = new JsonizePDFResponse();

        try {
            response.setText(Utilities.pdf2text(filename));

            for(String sentence : response.getText().split("\\.")){

                if(Utilities.is_important_sentence(sentence))
                    response.setImportant_text(response.getImportant_text()+"\n"+sentence+".");

            }

            response.setImportant_text(Utilities.remove_irrelevant_sections(response.getImportant_text()));
            response.setKeywords(Utilities.extract_keywords(response.getText()));

            response.setTitle(Utilities.pdf2title(filename));

            /*
            * cleansing part
            * */
            response.setImportant_text(Utilities.cleanse_string(response.getImportant_text()));
            response.setText(Utilities.cleanse_string(response.getText()));
            response.setTitle(Utilities.cleanse_string(response.getTitle()));
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























