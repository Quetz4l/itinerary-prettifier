package org.quetz4l.service.text;

import org.quetz4l.service.data.AirportImp;
import org.quetz4l.service.date.Date;

import java.util.List;

public class TextPrettifier {
    public static List<String> prettifier(List<String> txt, List<String> csv) {
        AirportImp airportImp = new AirportImp(csv);

        return txt.stream()
                .map(Date::replaceDateInString)
                .map(airportImp::replaceCodeToName)
                .toList();
    }
}
