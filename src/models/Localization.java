package models;

import org.json.simple.JSONObject;

public class Localization {
    private double latitude = 0;
    private double longitude = 0;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public JSONObject getJSONObject() {
        JSONObject localization = new JSONObject();
        localization.put("latitude", this.latitude);
        localization.put("longitude", this.longitude);
        return localization;
    }
}
