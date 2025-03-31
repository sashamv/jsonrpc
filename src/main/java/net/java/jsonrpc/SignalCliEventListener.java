package net.java.jsonrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SignalCliEventListener {
    private static String url = "http://" + GetPropety.get("RPC_HOST")
                                    + ":" + GetPropety.get("RPC_PORT")
                                        + "/api/v1/events";
    //private static final String SIGNAL_CLI_URL = "http://localhost:8080/api/v1/events";

    private static final String SIGNAL_CLI_URL = url;

    public static void main(String[] args) {
        while (true) {  // Infinite loop for reconnection attempts
            HttpURLConnection connection = null;
            //System.out.println("SIGNAL_CLI_URL = " + SIGNAL_CLI_URL);
            try {
                URL url = new URL(SIGNAL_CLI_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "text/event-stream");
                connection.setRequestProperty("Connection", "keep-alive");
                connection.setConnectTimeout(10000); // 10 seconds
                connection.setReadTimeout(0);     // Disable read timeout for persistent connection

                // Start reading the event stream
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    if (!inputLine.isBlank()) {
                        SignalCliEventParser signalCliEventParser = new SignalCliEventParser();
                        signalCliEventParser.eventParser(inputLine);
                    }
                }
                in.close();

            } catch (SocketException e) {
                System.out.println("Connection lost: " + e.getMessage());
            } catch (SocketTimeoutException e){
                System.out.println("Read timeout: No data received, retrying...");
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            // Wait before retrying to avoid rapid reconnection attempts
            try {
                Thread.sleep(2000); // 2-second delay before retrying
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}



