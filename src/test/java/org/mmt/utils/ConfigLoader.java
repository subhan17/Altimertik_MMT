package org.mmt.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/java/org/mmt/resources/config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("property baseUrl is not specified in the stg_config.properties file");
    }
    public String getData(String data){
        String prop = "";
        switch (data.toLowerCase()){
            case "url":
                prop = properties.getProperty("url");
                break;
            case "fromcity1":
                prop = properties.getProperty("fromcity1");
                break;
            case "tocity1":
                prop = properties.getProperty("tocity1");
                break;
            case "tocity2":
                prop = properties.getProperty("tocity2");
                break;
            case "sort":
                prop = properties.getProperty("sort");
                break;
            case "popular":
                prop = properties.getProperty("popular");
                break;

        }
        if(prop != null) return prop;
        else throw new RuntimeException("data property is not specified in the config.properties file");
    }

}
