package bc.bfi.maps_scraper_cid;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlaceCsvWriterTest {

    @Test
    void writeStoresHeaderAndRows() throws Exception {
        // Initialization.
        final Place place = new Place();
        place.setName("Test Name");
        place.setFullAddress("123 Main St");
        place.setLatitude("12.34");
        place.setLongitude("56.78");
        place.setPermanentlyClosed("no");
        place.setPhone("123-456-7890");
        place.setQuery("cid-query");
        place.setRate("4.0");
        place.setRateCounter("10");
        place.setType("Store");
        place.setCid("cid-1");
        place.setGooglePlaceId("place-1");
        place.setWebsite("https://example.com");
        place.setPosition("1");
        final List<Place> places = new ArrayList<>();
        places.add(place);
        final Path csvFile = Files.createTempFile("places", ".csv");
        final PlaceCsvWriter writer = new PlaceCsvWriter();

        // Execution.
        writer.write(csvFile.toString(), places);

        // Assertion.
        List<String> lines = Files.readAllLines(csvFile);
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).isEqualTo("Name,Full address,Latitude,Longitude,Permanently closed,Phone,Query,Rate,Rate counter,Type,CID,Google Place ID,Website,Position");
        assertThat(lines.get(1)).isEqualTo("Test Name,123 Main St,12.34,56.78,no,123-456-7890,cid-query,4.0,10,Store,cid-1,place-1,https://example.com,1");
        Files.deleteIfExists(csvFile);
    }
}
