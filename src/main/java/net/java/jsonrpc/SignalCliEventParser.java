package net.java.jsonrpc;

import org.json.JSONObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalCliEventParser {
    private static final Logger log = LoggerFactory.getLogger(SignalCliEventParser.class);
    private final CheckPhoneNumberInList checkPhoneNumberInList = new CheckPhoneNumberInList();

    public void eventParser(String m) {

        if (m.startsWith("data:")) {
            //System.out.println(m.substring(5));
            try {
                JSONObject jo = new JSONObject(m.substring(5));

                if (jo.has("envelope")) {
                    jo = jo.getJSONObject("envelope");
                    if (checkPhoneNumberInList.check(jo) && jo.has("dataMessage") || jo.has("editMessage")) {
                        String sourceNumber = jo.getString("sourceNumber");
                        String message;
                        if (jo.has("dataMessage")) {
                            message = jo.getJSONObject("dataMessage").getString("message");
                        } else {
                            message = jo.getJSONObject("editMessage").getJSONObject("dataMessage").getString("message");
                        }
                        log.debug("sourceNumber = {}", sourceNumber);
                        log.debug("message = {}", message);
                        CommandLineParser.lineParser(sourceNumber, message);

                    }
                }
            } catch (JSONException e) {
                log.error("Error parsing JSON data: {}", e.toString());
            }
        }
    }

}
