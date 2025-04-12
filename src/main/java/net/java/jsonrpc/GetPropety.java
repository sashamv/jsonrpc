package net.java.jsonrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropety {
    private static final Logger log = LoggerFactory.getLogger(GetPropety.class);
    private static final Properties prop = new Properties();
    private static final String CONFIG_PATH = "/opt/jsonrpc/config.properties";

    // Static initializer block to load properties once
    static {
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            prop.load(input);
            log.info("Configuration loaded successfully from {}", CONFIG_PATH);
        } catch (IOException ex) {
            // Log fatal error and throw exception instead of System.exit
            log.error("FATAL: Could not load configuration file: {}", CONFIG_PATH, ex);
            throw new IllegalStateException("Failed to load configuration: " + CONFIG_PATH, ex);
        }
    }

    static String get(String s){
        String value = prop.getProperty(s);
        if (value == null) {
            log.error("FATAL: Required configuration property '{}' not found in {}", s, CONFIG_PATH);
            throw new IllegalStateException("Missing required configuration property: " + s);
        }
        return value;
    }
}
