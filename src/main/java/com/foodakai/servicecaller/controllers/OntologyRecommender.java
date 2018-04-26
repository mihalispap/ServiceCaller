package com.foodakai.servicecaller.controllers;


import com.foodakai.servicecaller.responses.OntologyRecommenderResponse;
import com.foodakai.servicecaller.responses.external.AgroPortalRecommenderEntity;
import com.foodakai.servicecaller.responses.external.AgroPortalRecommenderResponse;
import com.foodakai.servicecaller.responses.internal.OntologyRecommenderEntity;
import com.foodakai.servicecaller.utils.Utilities;
import com.foodakai.servicecaller.utils.config.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;

@Controller
@EnableAutoConfiguration
public class OntologyRecommender {

    @RequestMapping(value="/recommend", method={RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String recommend(
            @RequestParam("input") String input
    ){

        Configuration config = new Configuration();
        config.setMediatype("json");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        OntologyRecommenderResponse response = new OntologyRecommenderResponse();
        response.setInput(input);

        /*
        *   AgroPortalRecommender
        * */
        try {
            AgroPortalRecommenderEntity[] entities = new Gson().fromJson((
                    (StringBuffer) Utilities.sendGET("http://data.agroportal.lirmm.fr//recommender?apikey=" +
                                    "095bdee0-2d8d-4e49-b954-e91136289feb&input=" + URLEncoder.encode(input, "UTF-8"),
                            config)).toString(), AgroPortalRecommenderEntity[].class);

            for(AgroPortalRecommenderEntity entity : entities){
                OntologyRecommenderEntity oentity = new OntologyRecommenderEntity();

                oentity.setScore(entity.getEvaluationScore());
                oentity.setOntology_name(entity.getOntologies().get(0).getAcronym());

                response.getOntologies().add(oentity);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        /*
        *   GraphDB Geo Recommender
        * */
        try{
            String[] in_vars = input.split(",");

            OntologyRecommenderEntity oentity = new OntologyRecommenderEntity();
            oentity.setOntology_name("geonames");

            int count_pos=0;
            for(String s : in_vars){
                if(Utilities.graphDB_caller(s.trim()))
                    count_pos++;
            }
            oentity.setScore((double)count_pos/in_vars.length);

            response.getOntologies().add(oentity);

        }
        catch(Exception e){}

        return gson.toJson(response);
    }

}
















