package com.easycarpool.easycarpoolapp;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ranjas on 6/9/2016.
 */
public class PropertyUtil {
    private static Properties properties = null;
    public PropertyUtil(){
        try {
            properties = new Properties();
            Environment.getRootDirectory().getAbsolutePath();
            String fileName = Environment.getRootDirectory().getAbsolutePath() + "/local.properties";
            properties.load(PropertyUtil.class.getClassLoader().getResourceAsStream(fileName));
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
    public static String getProperty(String name) {
        if (properties.containsKey(name)){
            try {
                String property = properties.getProperty(name);
                return property;
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return null;
    }
}
