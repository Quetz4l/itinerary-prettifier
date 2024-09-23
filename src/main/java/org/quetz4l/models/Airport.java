package org.quetz4l.models;


public class Airport {
    String name;
    String iso_country;
    String municipality;
    String icao_code;
    String iata_code;
    String coordinates;

    public Airport(String name, String iso_country, String municipality, String icao_code, String iata_code) {
        this.name = name;
        this.iso_country = iso_country;
        this.municipality = municipality;
        this.icao_code = icao_code;
        this.iata_code = iata_code;
    }

    public String getName() {
        return name;
    }

    public String getIso_country() {
        return iso_country;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getIcao_code() {
        return icao_code;
    }

    public String getIata_code() {
        return iata_code;
    }

    public String getCoordinates() {
        return coordinates;
    }

}
