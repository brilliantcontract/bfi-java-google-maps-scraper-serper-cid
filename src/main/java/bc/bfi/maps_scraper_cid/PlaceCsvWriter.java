package bc.bfi.maps_scraper_cid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class PlaceCsvWriter {

    private static final String[] HEADERS = new String[] {
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
        "Position"
    };

    public void create(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath must not be null");

        CSVFormat format = CSVFormat.DEFAULT.withHeader(HEADERS);
        CSVPrinter printer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, false);
            printer = new CSVPrinter(writer, format);
        } finally {
            if (printer != null) {
                printer.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void append(String filePath, Place place) throws IOException {
        Objects.requireNonNull(filePath, "filePath must not be null");
        Objects.requireNonNull(place, "place must not be null");

        CSVFormat format = CSVFormat.DEFAULT;
        CSVPrinter printer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, true);
            printer = new CSVPrinter(writer, format);

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
