package org.quetz4l.service.data;

import org.quetz4l.controller.AppState;
import org.quetz4l.models.Airport;
import org.quetz4l.models.AirportFields;
import org.quetz4l.service.file.CSV;
import org.quetz4l.service.text.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum CodeType {
    airportIcao,
    airportIata,
    cityIcao,
    cityIata
}

public class AirportImp implements IAirport {

    public static List<Airport> AIRPORTS = new ArrayList<>();
    public static List<AirportFields> FIELDS_ORDER = new ArrayList<>();
    public static int FIELD_COUNT;


    public AirportImp(List<String> opAirports) {
        if (AIRPORTS.isEmpty()) {
            if (opAirports.size() > 1) {
                initFieldsOrder(opAirports.getFirst());
                opAirports.removeFirst();
                AIRPORTS = initAirports(opAirports);
            } else {
                Logger.printError("No airports found in csv file");
                AppState.closeApp();
            }
        }
    }

    @Override
    public List<Airport> initAirports(List<String> opAirports) {
        List<Airport> result = new ArrayList<>();
        List<String> fields;

        for (String line : opAirports) {
            fields = CSV.convertToStrings(line);
            if (fields.size() == FIELD_COUNT) {
                result.add(fillAirport(fields));
            } else {
                Logger.printError("Airport lookup malformed");
                AppState.closeApp();
                return result;
            }
        }

        return result;
    }

    private static Airport fillAirport(List<String> fields) {
        HashMap<AirportFields, String> map = new HashMap<>();

        for (int i = 0; i < FIELD_COUNT - 1; i++) {
            map.put(FIELDS_ORDER.get(i), fields.get(i));
        }

        return new Airport(
                map.get(AirportFields.name),
                map.get(AirportFields.iso_country),
                map.get(AirportFields.municipality),
                map.get(AirportFields.icao_code),
                map.get(AirportFields.iata_code)
        );


    }

    @Override
    public String getAirportNameByIcao(String code) {
        return AIRPORTS.stream().filter(a -> a.getIcao_code().equals(code)).findFirst().map(Airport::getName).orElse("Unknown");
    }

    @Override
    public String getAirportNameByIata(String code) {
        return AIRPORTS.stream().filter(a -> a.getIata_code().equals(code)).findFirst().map(Airport::getName).orElse("Unknown");
    }

    @Override
    public String getMunicipalityNameByIata(String code) {
        return AIRPORTS.stream().filter(a -> a.getIata_code().equals(code)).findFirst()
                .map(Airport::getMunicipality).orElse("Unknown");
    }

    @Override
    public String getMunicipalityNameByIcao(String code) {
        return AIRPORTS.stream().filter(a -> a.getIcao_code().equals(code)).findFirst()
                .map(Airport::getMunicipality).orElse("Unknown");
    }


    public String replaceCodeToName(String string) {
        HashMap<CodeType, Pattern> patterns = new HashMap<>();
        String result = string;

        patterns.put(CodeType.airportIcao, Pattern.compile("([^*])(##)([A-Z]{4})"));
        patterns.put(CodeType.airportIata, Pattern.compile("([^*#])(#)([A-Z]{3})"));

        patterns.put(CodeType.cityIcao, Pattern.compile("(.)(\\*##)([A-Z]{4})"));
        patterns.put(CodeType.cityIata, Pattern.compile("(.)(\\*#)([A-Z]{3})"));

        for (CodeType type : patterns.keySet()) {
            int matcherIndex = 0;
            Matcher matcher = patterns.get(type).matcher(result);
            String code, symBeforeCode;

            while (matcher.find(matcherIndex)) {
                matcherIndex = matcher.start() + 2;

                code = matcher.group(3);
                symBeforeCode = matcher.group(1);


                result = switch (type) {
                    case airportIata -> {
                        String name = symBeforeCode + getAirportNameByIata(code);
                        if (name.substring(1).equals("Unknown")) {
                            Logger.printError("Unknown iata code: ", code);
                            AppState.closeApp();
                            yield result;
                        } else yield matcher.replaceFirst(name);
                    }
                    case airportIcao -> {
                        String name = symBeforeCode + getAirportNameByIcao(code);
                        if (name.substring(1).equals("Unknown")) {
                            Logger.printError("Unknown icao code: ", code);
                            AppState.closeApp();
                            yield result;
                        } else yield matcher.replaceFirst(name);
                    }
                    case cityIata -> {
                        String name = symBeforeCode + getMunicipalityNameByIata(code);
                        if (name.substring(1).equals("Unknown")) {
                            Logger.printError("Unknown iata code: ", code);
                            AppState.closeApp();
                            yield result;
                        } else yield matcher.replaceFirst(name);
                    }
                    case cityIcao -> {
                        String name = symBeforeCode + getMunicipalityNameByIcao(code);
                        if (name.substring(1).equals("Unknown")) {
                            Logger.printError("Unknown icao code: ", code);
                            AppState.closeApp();
                            yield result;
                        } else yield matcher.replaceFirst(name);
                    }
                };
                matcher = patterns.get(type).matcher(result);
            }
        }
        return result;
    }

    private void initFieldsOrder(String str) {
        if (FIELDS_ORDER.isEmpty()) {
            String[] fieldNames = str.split(",");
            FIELD_COUNT = fieldNames.length;
            for (String field : fieldNames) {
                switch (field) {
                    case "name": {
                        FIELDS_ORDER.add(AirportFields.name);
                        break;
                    }
                    case "iso_country": {
                        FIELDS_ORDER.add(AirportFields.iso_country);
                        break;
                    }
                    case "municipality": {
                        FIELDS_ORDER.add(AirportFields.municipality);
                        break;
                    }
                    case "icao_code": {
                        FIELDS_ORDER.add(AirportFields.icao_code);
                        break;
                    }
                    case "iata_code": {
                        FIELDS_ORDER.add(AirportFields.iata_code);
                        break;
                    }
                }
            }
        }
    }

}
