package bc.bfi.maps_scraper_cid;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        Parser parser = new Parser();
        PlaceCsvWriter csvWriter = new PlaceCsvWriter();
        List<Place> allPlaces = new ArrayList<>();
        
        try {
            Cids cids = new Cids("cids.txt");
            for (String cid : cids.getValues()) {
                System.out.println("Process CID - " + cid);
                String response = downloader.download(cid);
                if (response != null && !response.isEmpty()) {
                    List<Place> places = parser.parse(response, cid);
                    if (places != null) {
                        allPlaces.addAll(places);
                    }
                }
            }

            csvWriter.write("places.csv", allPlaces);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
