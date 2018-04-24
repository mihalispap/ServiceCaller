package com.foodakai.servicecaller.controllers;

import com.foodakai.servicecaller.responses.UniqueValuesResponse;
import com.foodakai.servicecaller.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@EnableAutoConfiguration
public class CSVParser {

    @RequestMapping(value="/unique-values", method={RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String unique_values(
            @RequestParam("file") MultipartFile file,
            @RequestParam("column_no") int column_no,
            @RequestParam("delim") char delim,
            @RequestParam("header") boolean header
            ){

        String filename = "csv."+System.currentTimeMillis() + ".upld."+file.getSize()+".csv";
        if (!file.isEmpty()) {
            try {
                Utilities.save_file(filename, file);
            } catch (Exception e) {
                e.printStackTrace();
                return "{\"error\":\"message\"}";
            }
        }

        UniqueValuesResponse response = new UniqueValuesResponse();
        response.setColumn_no(column_no);

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filename), delim);
            String[] line;
            int no_lines=0;
            while ((line = reader.readNext()) != null) {
                if(header && no_lines==0){
                    no_lines++;
                    continue;
                }
                response.getValues().add(line[column_no].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
