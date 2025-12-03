package bc.bfi.maps_scraper_cid;

class InvalidPlace {

    private final String countryCode;
    private final String googlePlaceCode;
    private final String langugae;
    private final String location;

    public InvalidPlace(String countryCode, String googlePlaceCode, String langugae, String location) {
        this.countryCode = countryCode;
        this.googlePlaceCode = googlePlaceCode;
        this.langugae = langugae;
        this.location = location;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getGooglePlaceCode() {
        return googlePlaceCode;
    }

    public String getLangugae() {
        return langugae;
    }

    public String getLocation() {
        return location;
    }

}
