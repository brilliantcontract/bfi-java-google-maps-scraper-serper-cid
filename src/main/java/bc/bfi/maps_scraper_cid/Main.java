package bc.bfi.maps_scraper_cid;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        Parser parser = new Parser();
        PlaceCsvWriter csvWriter = new PlaceCsvWriter();
        Storage storage = new Storage();
        final String csvFilePath = "places.csv";
        File csvFile = new File(csvFilePath);

        verifyCsvFileIsNotExist(csvFile, csvFilePath);

        try {
            csvWriter.create(csvFilePath);
            List<InvalidPlace> invalidPlaces = storage.readCsvFileWithInvalidPlaces();
            for (InvalidPlace invalidPlace : invalidPlaces) {
                if (invalidPlace == null) {
                    continue;
                }

                String googlePlaceCode = invalidPlace.getGooglePlaceCode();
                System.out.println("Process place - " + googlePlaceCode);
                String response = downloader.download(invalidPlace);
                if (response != null && !response.isEmpty()) {
                    List<Place> places = parser.parse(response, invalidPlace.getLocation());
                    if (places != null) {
                        for (Place place : places) {
                            if (place != null) {
                                csvWriter.append(csvFilePath, place);
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void verifyCsvFileIsNotExist(File csvFile, final String csvFilePath) {
        if (csvFile.exists()) {
            System.err.println(csvFilePath + " already exists. Cannot proceed further.");
            System.exit(1);
        }
    }
}
