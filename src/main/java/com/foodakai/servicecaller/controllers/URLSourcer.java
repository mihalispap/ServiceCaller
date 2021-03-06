package com.foodakai.servicecaller.controllers;

import com.foodakai.servicecaller.responses.CallEndpointResponse;
import com.foodakai.servicecaller.responses.URLSourcerResponse;
import com.foodakai.servicecaller.utils.Utilities;
import com.foodakai.servicecaller.utils.config.Configuration;
import com.foodakai.servicecaller.utils.processors.RequestProcessor;
import com.foodakai.servicecaller.utils.processors.ResponseProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
public class URLSourcer {

    @RequestMapping(value="/source", method={RequestMethod.GET},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String call(
            @RequestParam("url") String url
    ){

        URLSourcerResponse response = new URLSourcerResponse();

        response.setInput(url);

        /*
         *   PDF url example:
         *       http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.782.4365&rep=rep1&type=pdf
         * */

        try {
            response.setBinaryoutput((byte[])Utilities.sendGET(url, null));

            Base64 codec = new Base64();
            byte[] encoded = codec.encode(response.getBinaryoutput());

            response.setOutput(new String(encoded));

            //FileUtils.writeByteArrayToFile(new File("file1.pdf"), response.getBinaryoutput());
        }
        catch(Exception e){
            response.setBinaryoutput(null);
            try {
                response.setOutput(((StringBuffer)Utilities.sendGET(url, null)).toString());
            }
            catch(Exception ex){
                response.setOutput("error");
            }
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        return gson.toJson(response);
    }

}













