package bc.bfi.maps_scraper_cid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class PlaceCsvWriter {

    public void write(String filePath, List<Place> places) throws IOException {
        Objects.requireNonNull(filePath, "filePath must not be null");
        Objects.requireNonNull(places, "places must not be null");

        CSVFormat format = CSVFormat.DEFAULT.withHeader(
                "Name",
                "Full address",
                "Latitude",
                "Longitude",
                "Permanently closed",
                "Phone",
                "Query",
                "Rate",
                "Rate counter",
                "Type",
                "CID",
                "Google Place ID",
                "Website",
                "Position");

        CSVPrinter printer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            printer = new CSVPrinter(writer, format);

            for (Place place : places) {
                if (place != null) {
                    printer.printRecord(
                            place.getName(),
                            place.getFullAddress(),
                            place.getLatitude(),
                            place.getLongitude(),
                            place.getPermanentlyClosed(),
                            place.getPhone(),
                            place.getQuery(),
                            place.getRate(),
                            place.getRateCounter(),
                            place.getType(),
                            place.getCid(),
                            place.getGooglePlaceId(),
                            place.getWebsite(),
                            place.getPosition());
                }
            }
        } finally {
            if (printer != null) {
                printer.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}
