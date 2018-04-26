package com.foodakai.servicecaller.controllers;

import com.foodakai.servicecaller.responses.AnnotateResponse;
import com.foodakai.servicecaller.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class Annotate {

    @RequestMapping(value="/annotate-geonames", method={RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String annotate_geonames(
            @RequestBody String input
    ){

        List<AnnotateResponse> response = new ArrayList<>();
        ArrayList<String> ngram_values = new ArrayList<String>();
        for (int n = 1; n <= 3; n++) {
            ngram_values.addAll(Utilities.ngrams(n, input));
        }

        for(String v : ngram_values){
            try{
                if(Utilities.graphDB_caller(v)) {
                    AnnotateResponse aentity = new AnnotateResponse();
                    aentity.setAnnotation(Utilities.toCamelCase(v));
                    aentity.setScore(1.0f);

                    response.add(aentity);
                }
            }
            catch(Exception e){}
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        return gson.toJson(response);
    }

}
