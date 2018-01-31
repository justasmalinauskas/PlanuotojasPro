package com.justas.planuotojaspro.global;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

import static com.justas.planuotojaspro.Main.main;

public class Settings {

    public static String language = "en";

    public static boolean getSettingsFromFile(){
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("config.properties"));
            Enumeration<Object> en = config.keys();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                if(key.equals("Language")){
                    language = (String)config.get(key);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static void setLanguage(String lang) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("config.properties");
            prop.setProperty("Language", lang);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
