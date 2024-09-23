package org.quetz4l.service.data;

import org.quetz4l.models.Airport;

import java.util.List;

public interface IAirport {
    List<Airport> initAirports(List<String> opAirports);

    String getAirportNameByIcao(String code);

    String getAirportNameByIata(String code);

    String getMunicipalityNameByIcao(String code);

    String getMunicipalityNameByIata(String code);

    String replaceCodeToName(String string);
}
