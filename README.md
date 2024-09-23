# itinerary-prettifier

## Overview
`itinerary-prettifier` is a command-line tool that processes flight itineraries to make them customer-friendly. It reads a text-based itinerary (generated by an administrative system), converts the technical information into a more readable format, and writes the result to a new file. The tool can convert airport codes into airport names, city codes into city names, format dates and times and trim excessive white space.

## Features
- Converts IATA and ICAO airport codes into human-readable airport names.
- Supports city name conversion from IATA/ICAO codes.
- Formats dates and times according to customer-friendly standards.
- Removes excessive blank lines in the text output.
- Handles common errors like missing files or malformed data gracefully.

## Usage

```bash
$ java Prettifier.java <input_path> <output_path> <airport_lookup_path>
```
### Exaplle
```bash
$ java Prettifier.java ./input.txt ./output.txt ./airports_lookup.csv
```
## Help
### input
```bash
$ java Prettifier.java -h 
or
$ java Prettifier.java -help
```
### output
```bash
itinerary usage:
$ java Prettifier.java <input_path> <output_path> <airport_lookup_path>
```

## Input Format
The input text file contains the flight itinerary with the following elements:

- IATA codes: Encoded with a single `#` symbol followed by three letters `#LAX` for Los Angeles International Airport.
- ICAO codes: Encoded with double `##` symbols followed by four letters `##EGLL` for London Heathrow Airport.
- Dates: Encoded in ISO 8601 format and prefixed with `D` for dates  `D(2007-04-05T12:30−02:00)`.
- 12-hour Times: Prefixed with `T12` `T12(2007-04-05T12:30−02:00)`.
- 24-hour Times: Prefixed with `T24` `T24(2007-04-05T12:30−02:00)`.
- City Names: Prefixed with `*` `*#LHR` for "London".


## Output Format
The output file will include:

- Airport codes replaced with their corresponding airport names.
- City names, if encoded, replaced with actual city names.
- Dates formatted as `DD-Mmm-YYYY` `05 Apr 2007`.
- Times formatted as:
- 12-hour format: `HH:MMAM/PM (timezone offset)` `12:30PM (-02:00)`.
- 24-hour format: `HH:MM (timezone offset)` `12:30 (-02:00)`.
- Trimmed vertical whitespace with no more than one consecutive blank line.


## Airport Lookup
- The `airport_lookup.csv` file is a CSV that contains information for airport code lookups. The columns are:

- `name`: Airport name "Los Angeles International Airport"
- `iso_country`: Country ISO code
- `municipality`: City  "Los Angeles"
- `icao_code`: ICAO code
- `iata_code`: IATA code
- `coordinates`: Latitude and longitude

## Example CSV:
```csv
name,iso_country,municipality,icao_code,iata_code,coordinates
Los Angeles International Airport,US,Los Angeles,KLAX,LAX,"33.9425,-118.4081"
London Heathrow Airport,GB,London,EGLL,LHR,"51.4775,-0.461389"

```

## Extra Features
- Highlighting: If desired, you can configure the tool to highlight specific information like dates, times, offsets, airport names, and cities in the output for easy readability.
- City Names: When a city name is encoded using the * symbol followed by an airport code, the tool converts it to the corresponding city name *#LHR becomes "London".

## Requirements
Java 21 or higher

## License
This project is licensed under the MIT License.