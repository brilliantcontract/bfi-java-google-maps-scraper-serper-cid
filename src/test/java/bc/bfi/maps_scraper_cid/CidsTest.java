package bc.bfi.maps_scraper_cid;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CidsTest {

    @Test
    void getValuesReadsNonEmptyLines() throws Exception {
        // Initialization.
        final Path tempFile = Files.createTempFile("cids", ".txt");
        final List<String> lines = List.of("12345", "", "67890 ", "  abc");
        Files.write(tempFile, lines);

        // Execution.
        Cids cids = new Cids(tempFile.toString());

        // Assertion.
        assertThat(cids.getValues()).containsExactly("12345", "67890", "abc");
        Files.deleteIfExists(tempFile);
    }
}
