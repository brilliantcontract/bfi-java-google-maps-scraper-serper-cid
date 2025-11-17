package bc.bfi.maps_scraper_cid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Cids {

    private final List<String> values;

    public Cids(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath must not be null");
        this.values = loadFile(filePath);
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

    private List<String> loadFile(String filePath) throws IOException {
        List<String> loaded = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    loaded.add(trimmed);
                }
                line = reader.readLine();
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        assert loaded != null : "loaded list must not be null! Got: null";
        return loaded;
    }
}
