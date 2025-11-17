package bc.bfi.maps_scraper_cid;

public final class Config {

    private static final String API_KEY = "0881769d8ef5e996ba41abc92ddb186b00d1a9b1";
    private static final String PLACES_URL = "https://google.serper.dev/maps";

    private Config() {
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getPlacesUrl() {
        return PLACES_URL;
    }
}
