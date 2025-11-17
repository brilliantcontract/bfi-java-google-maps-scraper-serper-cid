package bc.bfi.maps_scraper_cid;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;

class Parser {

    List<Place> parse(String json, String query) {
        assert json != null : "json must not be null! Got: null";
        assert query != null : "query must not be null! Got: null";

        List<Place> places = new ArrayList<>();

        JsonObject jsonObject = parseJson(json);
        JsonArray placesArray = readArray("places", jsonObject);

        for (JsonElement element : placesArray) {
            if (!element.isJsonObject()) {
                continue;
            }

            JsonObject jsonPlace = element.getAsJsonObject();
            Place place = new Place();

            place.setName(readString("title", jsonPlace));
            place.setFullAddress(readString("address", jsonPlace));
            place.setLatitude(readNumber("latitude", jsonPlace));
            place.setPosition(readNumber("position", jsonPlace));
            place.setLongitude(readNumber("longitude", jsonPlace));
            place.setRate(readNumber("rating", jsonPlace));
            place.setRateCounter(readNumber("ratingCount", jsonPlace));
            place.setType(readString("type", jsonPlace));
            place.setWebsite(readString("website", jsonPlace));
            place.setCid(readString("cid", jsonPlace));
            place.setGooglePlaceId(readString("placeId", jsonPlace));
            place.setPhone(readString("phoneNumber", jsonPlace));
            place.setQuery(query);

            places.add(place);
        }

        return places;
    }

    private JsonObject parseJson(String json) {
        JsonElement parsed = JsonParser.parseString(json);
        if (parsed.isJsonObject()) {
            return parsed.getAsJsonObject();
        }

        return new JsonObject();
    }

    private JsonArray readArray(String key, JsonObject jsonObject) {
        if (jsonObject.has(key)) {
            JsonElement element = jsonObject.get(key);
            if (element.isJsonArray()) {
                return element.getAsJsonArray();
            }
        }

        return new JsonArray();
    }

    private String readString(String key, JsonObject jsonPlace) {
        if (jsonPlace.has(key)) {
            JsonElement element = jsonPlace.get(key);
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isString()) {
                    return primitive.getAsString();
                }

                return primitive.toString();
            }
        }

        return "";
    }

    private String readNumber(String key, JsonObject jsonPlace) {
        if (jsonPlace.has(key)) {
            JsonElement element = jsonPlace.get(key);
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    return primitive.getAsBigDecimal().toPlainString();
                }

                if (primitive.isString()) {
                    return primitive.getAsString();
                }
            }
        }

        return "";
    }
}
