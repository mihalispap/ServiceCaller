package com.foodakai.servicecaller.utils.processors;

import com.foodakai.servicecaller.utils.Utilities;
import com.foodakai.servicecaller.utils.config.Configuration;
import com.foodakai.servicecaller.utils.config.Parameter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestProcessor {

    /*
        TODO:
            think if the return is a complex object
            is type useful?
     */
    public String run(Configuration config) throws Exception
    {
        String value = "";
        switch(config.getInput().getMethod()){
            case "GET":
                value = get_process(config);
                break;
            case "POST":
                value = post_process(config);
                break;
            default:
                break;
        }

        return value;
    }

    private String post_process(Configuration configuration){
        return "";
    }

    private String get_process(Configuration configuration) throws Exception{

        String url = configuration.getInput().getUrl_pattern();

        for(Map.Entry<String, Parameter> entry : configuration.getInput().getParams().entrySet()){
            url = url.replace("{"+entry.getKey()+"}", entry.getValue().getValue());
        }

        return ((StringBuffer)Utilities.sendGET(url, configuration)).toString();

//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();

        //print result
    }
}
