package net.java.jsonrpc;

import java.util.Arrays;
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
}
