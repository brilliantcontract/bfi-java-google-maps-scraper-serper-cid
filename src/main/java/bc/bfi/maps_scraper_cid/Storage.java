package bc.bfi.maps_scraper_cid;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

class Storage {

    List<InvalidPlace> readCsvFileWithInvalidPlaces() throws IOException {
        List<InvalidPlace> invalidPlaces = new ArrayList<>();
        CSVParser parser = null;
        FileReader reader = null;
        try {
            reader = new FileReader("google-places-to-fix.csv");
            CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
            parser = format.parse(reader);

            for (CSVRecord record : parser.getRecords()) {
                String countryCode = record.get("country_code");
                if (countryCode == null) {
                    countryCode = "";
                } else {
                    countryCode = countryCode.trim();
                }

                String googlePlaceCode = record.get("google_place_code");
                if (googlePlaceCode == null) {
                    googlePlaceCode = "";
                } else {
                    googlePlaceCode = googlePlaceCode.trim();
                }

                String language = record.get("language");
                if (language == null) {
                    language = "";
                } else {
                    language = language.trim();
                }

                String location = record.get("location");
                if (location == null) {
                    location = "";
                } else {
                    location = location.trim();
                }

                InvalidPlace place = new InvalidPlace(countryCode, googlePlaceCode, language, location);
                invalidPlaces.add(place);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
            if (reader != null) {
                reader.close();
            }
        }

        assert invalidPlaces != null : "invalidPlaces must not be null! Got: null";
        return invalidPlaces;
    }

}
