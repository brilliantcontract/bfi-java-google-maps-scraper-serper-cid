package bc.bfi.maps_scraper_cid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.RequestBodyEntity;
import kong.unirest.UnirestInstance;
import org.junit.jupiter.api.Test;

class DownloaderTest {

    @Test
    void downloadUsesInvalidPlaceLanguageAndCid() {
        // Initialization.
        final InvalidPlace invalidPlace = new InvalidPlace("US", "cid-123", "ru", "New York");
        final String apiKey = "api-key";
        final String placesUrl = "https://example.com/maps";

        // Mocks.
        final UnirestInstance unirest = mock(UnirestInstance.class);
        final HttpRequestWithBody requestWithBody = mock(HttpRequestWithBody.class);
        final RequestBodyEntity requestBodyEntity = mock(RequestBodyEntity.class);
        final HttpResponse<String> response = mock(HttpResponse.class);
        when(unirest.post(placesUrl)).thenReturn(requestWithBody);
        when(requestWithBody.header("X-API-KEY", apiKey)).thenReturn(requestWithBody);
        when(requestWithBody.header("Content-Type", "application/json")).thenReturn(requestWithBody);
        when(requestWithBody.body("{\"hl\":\"ru\",\"cid\":\"cid-123\"}"))
                .thenReturn(requestBodyEntity);
        when(requestBodyEntity.asString()).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        when(response.getBody()).thenReturn("{\"result\":\"ok\"}");

        // Execution.
        Downloader downloader = new Downloader(unirest, apiKey, placesUrl);
        String body = downloader.download(invalidPlace);

        // Assertion.
        assertThat(body).isEqualTo("{\"result\":\"ok\"}");
        verify(unirest).post(placesUrl);
        verify(requestWithBody).header("X-API-KEY", apiKey);
        verify(requestWithBody).header("Content-Type", "application/json");
        verify(requestWithBody).body("{\"hl\":\"ru\",\"cid\":\"cid-123\"}");
        verify(requestBodyEntity).asString();
    }
}
