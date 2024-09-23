package org.quetz4l.service.text;


public class Logger {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";


    public static String setColor(String text, String color) {
        return color + text + RESET;
    }

    public static void printError(String text) {
        logMessage(setColor(text, RED));
    }

    public static void printError(String errorText, String text) {
        logMessage(setColor(errorText, RED) + setColor(text, CYAN));
    }

    public static void printMessage(String messageText) {
        logMessage(setColor(messageText, GREEN));
    }

    public static void printMessage(String messageText, String text) {
        logMessage(setColor(messageText, GREEN) + setColor(text, CYAN));
    }

    public static void logMessage(String text) {
        System.out.println(text);
    }


}
