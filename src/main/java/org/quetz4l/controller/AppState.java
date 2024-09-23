package org.quetz4l.controller;

import org.quetz4l.models.Config;
import org.quetz4l.service.text.Logger;

public class AppState {
    public static void saveArgs(String[] args) {
        for (String arg : args) {
            if (arg.equals("-h") || arg.equals("-help")) {
                printHelp();
                closeApp();
            }
        }

        if (args.length != 3) {
            Logger.printError("Wrong amount of arguments");
            printHelp();
            closeApp();
        }

        Config.inputFileName = args[0];
        Config.outputFileName = args[1];
        Config.csvFileName = args[2];
    }


    public static void closeApp() {
        System.exit(0);
    }

    private static void printHelp() {
        Logger.printMessage("Itinerary usage: ", "$ java Prettifier.java ./input.txt ./output.txt ./airport-lookup.csv");
    }

}
