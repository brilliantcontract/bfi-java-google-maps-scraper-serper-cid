package bc.bfi.maps_scraper_cid;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void readCsvFileWithInvalidPlacesLoadsAllRows() throws Exception {
        // Initialization.
        final Storage storage = new Storage();
        final Path csvPath = Path.of("google-places-to-fix.csv");
        final List<String> lines = Files.readAllLines(csvPath);
        int expectedCount = 0;
        int index = 1;
        while (index < lines.size()) {
            String line = lines.get(index);
            if (line != null && !line.trim().isEmpty()) {
                expectedCount++;
            }
            index++;
        }

        // Execution.
        List<InvalidPlace> invalidPlaces = storage.readCsvFileWithInvalidPlaces();

        // Assertion.
        assertThat(invalidPlaces).hasSize(expectedCount);
        InvalidPlace firstPlace = invalidPlaces.get(0);
        assertThat(firstPlace.getCountryCode()).isEqualTo("SE");
        assertThat(firstPlace.getGooglePlaceCode()).isEqualTo("10000183136918769203");
        assertThat(firstPlace.getLangugae()).isEqualTo("sv");
        assertThat(firstPlace.getLocation()).isEqualTo("Sweden");
    }
}
