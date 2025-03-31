package net.java.jsonrpc;

import org.json.JSONObject;
import org.json.JSONException;

public class SignalCliEventParser {

    public void eventParser(String m) {

        if (m.startsWith("data:")) {
            //System.out.println(m.substring(5));
            try {
                JSONObject jo = new JSONObject(m.substring(5));
                CheckPhoneNumberInList check = new CheckPhoneNumberInList();

                if (jo.has("envelope")) {
                    jo = jo.getJSONObject("envelope");
                    if (check.check(jo) && jo.has("dataMessage") || jo.has("editMessage")) {
                        String sourceNumber = jo.getString("sourceNumber");
                        String message;
                        if (jo.has("dataMessage")) {
                            message = jo.getJSONObject("dataMessage").getString("message");
                        } else {
                            message = jo.getJSONObject("editMessage").getJSONObject("dataMessage").getString("message");
                        }
                        // System.out.println("sourceNumber =" + sourceNumber);
                        // System.out.println("message =" + message);
                        CommandLineParser.lineParser(sourceNumber, message);

                    } /*else {
                            System.out.println("Source number or message was not initialized.");
                        }*/
                }
            } catch (JSONException e) {
                System.out.println("Error json : " + e);
            }
        }
    }

}
