package com.foodakai.servicecaller.utils;

import com.foodakai.servicecaller.utils.config.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Utilities {

    private Utilities(){}

    private static volatile Utilities instance = null;

    public static Utilities getInstance(){
        if (instance == null) {
            synchronized(Utilities.class) {
                if (instance == null) {
                    instance = new Utilities();
                }
            }
        }
        return instance;
    }

    public static Configuration loadConfiguration(String yaml_file){

        Configuration configuration = new Configuration();

        Yaml yaml = new Yaml();
        try( InputStream in = Files.newInputStream( Paths.get( yaml_file ) ) ) {
            configuration = yaml.loadAs( in, Configuration.class );
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return configuration;
    }
}
