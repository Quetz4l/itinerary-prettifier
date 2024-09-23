package org.quetz4l.service.date;

import org.quetz4l.controller.AppState;
import org.quetz4l.service.text.Logger;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum TypeOfDate {
    T24, T12, D
}


public class Date {
    public static String replaceDateInString(String string) {
        HashMap<TypeOfDate, Pattern> patterns = new HashMap<>();
        String result = string;

        /* 04:08 (+13:00) */
        DateTimeFormatter formatT24 = DateTimeFormatter.ofPattern("HH:mm (xxx)", Locale.ENGLISH);
        patterns.put(TypeOfDate.T24, Pattern.compile("(T24)\\(([^)]+)\\)"));

        /* 07:18PM (-02:00) */
        DateTimeFormatter formatT12 = DateTimeFormatter.ofPattern("hh:mma (xxx)", Locale.ENGLISH);
        patterns.put(TypeOfDate.T12, Pattern.compile("(T12)\\(([^)]+)\\)"));

        /* 09 May 2022 */
        DateTimeFormatter formatDay = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        patterns.put(TypeOfDate.D, Pattern.compile("(D)\\(([^)]+)\\)"));

        for (TypeOfDate type : patterns.keySet()) {
            Matcher matcher = patterns.get(type).matcher(result);
            while (matcher.find()) {
                ZonedDateTime dateTime;
                try {
                    String dateTimeString = matcher.group(2);

                    if (dateTimeString.endsWith("Z")) {
                        dateTime = ZonedDateTime.parse(dateTimeString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    } else {
                        dateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toZonedDateTime();
                    }
                } catch (Exception e) {
                    Logger.printError("Is not a valid date: ", matcher.group(2));
                    AppState.closeApp();
                    continue;
                }

                result = switch (type) {
                    case T24 -> matcher.replaceFirst(dateTime.format(formatT24));
                    case T12 -> matcher.replaceFirst(dateTime.format(formatT12));
                    case D -> matcher.replaceFirst(dateTime.format(formatDay));
                };
            }
        }
        return result;
    }


}

