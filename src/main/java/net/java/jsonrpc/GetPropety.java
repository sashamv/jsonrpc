package net.java.jsonrpc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetPropety {
    static String get(String s){
        try {
            Properties prop = new Properties();
            FileInputStream file = new FileInputStream("/opt/jsonrpc/config.properties");
            prop.load(file);
            file.close();
            if (prop.getProperty(s) == null) {
                System.out.println("The property " + s + " is null");
                System.exit(1);
            }
            return prop.getProperty(s);
        } catch (FileNotFoundException e) {
            System.out.println("config.properties file not found");
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
