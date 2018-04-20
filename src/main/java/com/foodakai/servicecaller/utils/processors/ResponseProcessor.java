package com.foodakai.servicecaller.utils.processors;

import com.foodakai.servicecaller.utils.config.Configuration;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResponseProcessor {

    public String run(String input, Configuration config) throws Exception
    {
        String value = "";
        switch(config.getOutput().getMediatype()){
            case "json":
                value = json_process(input, config);
                break;
            default:
                break;
        }

        return value;
    }

    private String json_process(String input, Configuration config){

        Map<String, String> extracted = new HashMap<>();
        JSONObject obj = new JSONObject(input);

        for( Map.Entry<String, String> entry : config.getOutput().getValues().entrySet()){

            String[] keys = entry.getValue().split("\\.");

            JSONObject curr = obj;
            String jbody = "";
            for(int i=0; i<keys.length; i++){

                String key = keys[i];

                if(key.equals("ROOT")) continue;

                jbody = curr.get(key).toString();
                if(i!=keys.length-1)
                    curr = new JSONObject(jbody);
            }

            extracted.put(entry.getKey(), jbody);
        }

        return json_response_process(extracted, config);
    }

    private String json_response_process(Map<String, String> values, Configuration configuration){

        String resp = configuration.getOutput().getSchema();
        for(Map.Entry<String, String> entry : values.entrySet()){

            resp = resp.replace("___"+entry.getKey()+"___",
                    "\""+entry.getKey()+"\":"+entry.getValue());

        }

        return resp;
    }
}











