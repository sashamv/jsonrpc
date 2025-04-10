package net.java.jsonrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class CommandLineParser {
    private static final Logger log = LoggerFactory.getLogger(CommandLineParser.class);

    public static void lineParser(String sourceNumber, String message) {
        String trimmedMessage = message.trim();
        String[] parts = trimmedMessage.split("\\s+", 2);
        String command = parts[0].toLowerCase();

        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command){
            case "help":
            case "h":
                handleHelp(sourceNumber);
                break;
            case "update":
            case "u":
                handleUpdate(sourceNumber, arguments);
                break;
            case "delete":
            case "d":
                handleDelete(sourceNumber, arguments);
                break;
            default:
                hadnleSearch(sourceNumber, trimmedMessage);
                break;
        }
    }

    private static void handleHelp(String recipient){
        String helpText = """
                Available commands:
                <serial_number> - Search for device
                help - Show this help message
                update <serial_number> - <field=value> [field2=value2]... - Update device info
                delete <serial_number> - Delete device""";
        sendResponse(recipient, helpText);
    }

    private static void hadnleSearch(String recipient, String serialNumbere){
        log.info("Handling search for s/n: {}", serialNumbere);
        List<String> results = SignalGetData.getData(serialNumbere);
        //results =
        if (results.isEmpty()){
            sendResponse(recipient, "Запису не знайдено");
        } else {
            for(String result : results){
                sendResponse(recipient, result);
            }
        }
    }

    private static void handleDelete(String recipient, String args){
        String serialNumbers = args.trim();
        if (serialNumbers.isEmpty()){
            sendResponse(recipient, "Помилка: Укажіть серійний номер для видалення. Використання: delete <serial_number>");
            return;
        }
        log.info("Handling delete for s/n: {}", serialNumbers);
        //String result =
        sendResponse(recipient, "Функція знаходться в розробці");
    }

    private static void handleUpdate(String recipient, String args){
        String[] updateParts = args.trim().split("\\s+", 2); // Split S/N from the restv
        if (updateParts.length < 2){
            sendResponse(recipient, "Помилка: Недійсний формат оновлення. Використання: update <serial_number> <field=value> ...");
            return;
        }
        String serialNumber = updateParts[0];
        String updateDataString = updateParts[1];
        log.info("Handling update for S/N: {}, Data: {}", serialNumber, updateDataString);
        //String result =
        sendResponse(recipient, "Функція знаходться в розробці");

    }

    private static void sendResponse(String recipient, String message){
        log.info("Sending response to {}: {}", recipient, message);
        SignalCliSend.signalCliSend(recipient, message);
    }
}