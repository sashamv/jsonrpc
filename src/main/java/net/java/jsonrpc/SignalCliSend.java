package net.java.jsonrpc;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SignalCliSend {
    private static final Logger log = LoggerFactory.getLogger(SignalCliSend.class);

    public static void signalCliSend(String recipient, String message) {
        try {
            String url = "http://" + GetPropety.get("RPC_HOST")
                    + ":" + GetPropety.get("RPC_PORT")
                    + "/api/v1/rpc";
            // Правильний URL JSON-RPC сервера
            log.debug("URL of JSON-RPC server: {}", url);

            URI uri = URI.create(url);
            URL serverURL = uri.toURL();

//            URL serverURL = new URL(url);

            // Створення сесії JSON-RPC
            JSONRPC2Session session = new JSONRPC2Session(serverURL);

            // Підготовка даних для відправки повідомлення
            Map<String, Object> params = new HashMap<>();
            params.put("recipient", recipient); // номер телефону одержувача
            params.put("message", message); // текст повідомлення

            // Створення запиту
            String method = "send";
            String requestID = "1";
            JSONRPC2Request request = new JSONRPC2Request(method, params, requestID);

            // Виконання запиту
            JSONRPC2Response response = session.send(request);

            // Перевірка відповіді
            if (response.indicatesSuccess()) {
                log.info("Результат: {}", response.getResult());
            } else {
                log.info("Помилка: {}", response.getError().getMessage());
            }

        } catch (JSONRPC2SessionException e) {
            if (e.getMessage().contains("Unexpected \"text/html\" content type")) {
                log.error("Сервер повернув HTML замість JSON. Перевірте конфігурацію сервера.");
            } else {
                log.error("Error occurred: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }
}



