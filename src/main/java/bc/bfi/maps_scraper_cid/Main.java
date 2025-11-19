package bc.bfi.maps_scraper_cid;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        Parser parser = new Parser();
        PlaceCsvWriter csvWriter = new PlaceCsvWriter();
        final String csvFilePath = "places.csv";
        File csvFile = new File(csvFilePath);

        verifyCsvFileIsNotExist(csvFile, csvFilePath);

        try {
            csvWriter.create(csvFilePath);
            Cids cids = new Cids("cids.txt");
            for (String cid : cids.getValues()) {
                System.out.println("Process CID - " + cid);
                String response = downloader.download(cid);
                if (response != null && !response.isEmpty()) {
                    List<Place> places = parser.parse(response, cid);
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
