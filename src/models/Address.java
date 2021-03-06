package models;

import org.json.simple.JSONObject;

public class Address {
    private String country = "";
    private String city = "";
    private String postalCode = "";
    private String StreetName = "";
    private String homeNumber = "";

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreetName() {
        return StreetName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    /**
     * @return JSONObject of address
     */
    public JSONObject getJSONObject() {
        JSONObject address = new JSONObject();
        address.put("country", this.country);
        address.put("city", this.city);
        address.put("postal_code", this.postalCode);
        address.put("street_name", this.StreetName);
        address.put("homeNumber", this.homeNumber);
        return address;
    }

    public void populate(JSONObject address) {
        this.setCountry((String) address.get("country"));
        this.setCity((String) address.get("city"));
        this.setStreetName((String) address.get("street_name"));
        this.setPostalCode((String) address.get("postal_code"));
        this.setHomeNumber((String) address.get("homeNumber"));
    }

}
