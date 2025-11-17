package bc.bfi.maps_scraper_cid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parsePopulatesPlaceFieldsWithGson() {
        // Initialization.
        final String json = """
                {"places":[{"title":"Coffee Shop","address":"123 Main St","latitude":40.7128,"longitude":-74.0060,"rating":4.5,"ratingCount":120,"type":"Cafe","website":"https://example.com","cid":"cid-123","placeId":"place-123","phoneNumber":"123-456-7890","position":1}]}
                """;
        final String query = "coffee near me";
        final Parser parser = new Parser();

        // Execution.
        List<Place> places = parser.parse(json, query);

        // Assertion.
        assertThat(places).hasSize(1);
        Place place = places.get(0);
        assertThat(place.getName()).isEqualTo("Coffee Shop");
        assertThat(place.getFullAddress()).isEqualTo("123 Main St");
        assertThat(place.getLatitude()).isEqualTo("40.7128");
        assertThat(place.getLongitude()).isEqualTo("-74.0060");
        assertThat(place.getRate()).isEqualTo("4.5");
        assertThat(place.getRateCounter()).isEqualTo("120");
        assertThat(place.getType()).isEqualTo("Cafe");
        assertThat(place.getWebsite()).isEqualTo("https://example.com");
        assertThat(place.getCid()).isEqualTo("cid-123");
        assertThat(place.getGooglePlaceId()).isEqualTo("place-123");
        assertThat(place.getPhone()).isEqualTo("123-456-7890");
        assertThat(place.getPosition()).isEqualTo("1");
        assertThat(place.getQuery()).isEqualTo(query);
    }
}
