package net.java.jsonrpc;

import java.util.List;
import java.util.ArrayList;

public class CommandLineParser {

    public static void lineParser(String sourceNumber, String message) {
      String[]  args = message.split("\s");
        // Variables to hold command line option values
        boolean showHelp = false;
        String addItem = null;
        String deleteItem = null;
        String searchItem = null;

        // Parse arguments manually
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    showHelp = true;
                    break;
                case "-a":
                case "--add":
                    if (i + 1 < args.length) {
                        addItem = args[++i];
                    } else {
                        SignalCliSend.SignalCliSend(sourceNumber,"Error: Missing argument for -a/--add");
                        break;
                    }
                    break;
                case "-d":
                case "--delete":
                    if (i + 1 < args.length) {
                        deleteItem = args[++i];
                    } else {
                        SignalCliSend.SignalCliSend(sourceNumber,"Error: Missing argument for -d/--delete");
                        break;
                    }
                    break;
                case "-c":
                    if (i + 1 < args.length) {
                        searchItem = args[++i];
                    } else {
                        SignalCliSend.SignalCliSend(sourceNumber,"Error: Missing arguments for -c");
                        break;
                    }
                    break;
                default:
                    findFilds(sourceNumber, args[i]);
            }
        }

        // Show help if requested or if no other options are used
        if (showHelp || args.length == 0) {
            SignalCliSend.SignalCliSend(sourceNumber, showHelp());

        }

        // Handle add operation
        if (addItem != null) {
            SignalCliSend.SignalCliSend(sourceNumber,"Adding item: " + addItem);
            // Perform add operation here
        }

        // Handle delete operation
        if (deleteItem != null) {
            SignalCliSend.SignalCliSend(sourceNumber,"Deleting item: " + deleteItem);
            // Perform delete operation here
        }

        // Handle change operation
        if (searchItem != null) {
            Integer result = SignalGetData.countSearchFields(searchItem);
            SignalCliSend.SignalCliSend(sourceNumber,"Count item: " + result);
            // Perform change operation here
        }
    }

    private static String showHelp() {
        return "Usage: java ScpCommandLineParser [options]\n" +
                "Options:\n" +
                "  -h, --help        Show help\n" +
                "  -c, Загальна кількість\n" +
                "   <пошук>";
    }

    private static void sendMessage(String sourceNumber, String message){
        SignalCliSend signalCliSend = new SignalCliSend();
        signalCliSend.SignalCliSend(sourceNumber, message);
    }

    //Try to find the item if no argument is given
    private static void findFilds(String sourceNumber, String arg){
        List<String> results = new ArrayList<>();
        results = SignalGetData.getData(arg);
        if (results.isEmpty()){
            SignalCliSend.SignalCliSend(sourceNumber, "Запису не знайдено");
        } else {
            for(String result : results){
                SignalCliSend.SignalCliSend(sourceNumber, result);
            }
        }
    }

    private static void countFilds(String sourceNumber, String arg){

    }
}