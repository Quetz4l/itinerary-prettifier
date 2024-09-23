package org.quetz4l.service.file;


import java.util.ArrayList;
import java.util.List;


public class CSV {
    public static List<String> convertToStrings(String line) {
        List<String> result = new ArrayList<>();
        char[] symbols = line.toCharArray();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (char sym : symbols) {
            if (sym == '"') inQuotes = !inQuotes;

            if (!inQuotes && sym == ',') {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(sym);
            }
        }
        result.add(field.toString());

        return result;
    }
}
