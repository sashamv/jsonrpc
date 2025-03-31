package net.java.jsonrpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.List;
import org.json.JSONObject;

public class CheckPhoneNumberInList {

    public Boolean check(JSONObject jo){
        //Properties p = readProperty("/root/config.properties");
        String phoneNumber = GetPropety.get("phoneNumber");
        List<String> phoneNumbrList = Arrays.asList(phoneNumber.split(","));

        if(!jo.isNull("sourceNumber") && jo.has("sourceNumber")){
            return phoneNumbrList.contains(jo.getString("sourceNumber"));
        }
        return false;
    }

    private Properties readProperty (String config) {
        File propFile = new File(config);
        Properties properties = new Properties();
        try {
            InputStream in = propFile.isFile() ? new FileInputStream(propFile) : this.getClass().getResourceAsStream(config);
            if (in == null) {
                throw new IllegalArgumentException("Cannot find property file: " + config);
            }
            properties.load((InputStream) in);
            if (in != null) {
                in.close();
            }
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
