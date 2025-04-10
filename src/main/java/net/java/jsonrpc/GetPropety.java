package net.java.jsonrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetPropety {
    private static final Logger log = LoggerFactory.getLogger(GetPropety.class);

    static String get(String s){
        try {
            Properties prop = new Properties();
            FileInputStream file = new FileInputStream("/opt/jsonrpc/config.properties");
            prop.load(file);
            file.close();
            if (prop.getProperty(s) == null) {
                log.info("The property {} is null", s);
                System.exit(1);
            }
            return prop.getProperty(s);
        } catch (FileNotFoundException e) {
            log.error("config.properties file not found");
            System.exit(1);
        } catch (IOException e) {
            log.error("Error reade property file: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
