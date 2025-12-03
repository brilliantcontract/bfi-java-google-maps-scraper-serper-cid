package bc.bfi.maps_scraper_cid;

import java.util.Objects;
import kong.unirest.HttpResponse;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public class Downloader {

    private final UnirestInstance unirest;
    private final String apiKey;
    private final String placesUrl;

    public Downloader() {
        this(Unirest.primaryInstance(), Config.getApiKey(), Config.getPlacesUrl());
    }

    public Downloader(UnirestInstance unirest, String apiKey, String placesUrl) {
        Objects.requireNonNull(unirest, "unirest must not be null");
        Objects.requireNonNull(apiKey, "apiKey must not be null");
        Objects.requireNonNull(placesUrl, "placesUrl must not be null");

        this.unirest = unirest;
        this.apiKey = apiKey;
        this.placesUrl = placesUrl;

        Unirest.config().connectTimeout(10_000);
    }

    public String download(String cid) {
        Objects.requireNonNull(cid, "cid must not be null");

        RequestBodyEntity request = unirest.post(placesUrl)
                .header("X-API-KEY", apiKey)
                .header("Content-Type", "application/json")
                .body("{\"cid\":\"" + cid + "\"}");

        HttpResponse<String> response = request.asString();
        assert response != null : "response must not be null! Got: null";

        int status = response.getStatus();
        assert status >= 200 && status < 300 : "Unexpected HTTP status! Got: " + status;

        return response.getBody();
    }

    public String download(final InvalidPlace invalidPlace) {
        Objects.requireNonNull(invalidPlace, "invalidPlace must not be null");

        final String googlePlaceCode = invalidPlace.getGooglePlaceCode();
        Objects.requireNonNull(googlePlaceCode, "googlePlaceCode must not be null");

        final String language = invalidPlace.getLangugae();
        Objects.requireNonNull(language, "language must not be null");

        HttpRequestWithBody postRequest = unirest.post(placesUrl)
                .header("X-API-KEY", apiKey)
                .header("Content-Type", "application/json");
        RequestBodyEntity request = postRequest.body("{\"hl\":\"" + language + "\",\"cid\":\""
                + googlePlaceCode + "\"}");

        HttpResponse<String> response = request.asString();
        assert response != null : "response must not be null! Got: null";

        int status = response.getStatus();
        assert status >= 200 && status < 300 : "Unexpected HTTP status! Got: " + status;

        return response.getBody();
    }
}
