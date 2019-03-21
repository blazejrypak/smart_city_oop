package models;

public class Address {
    private int postalCode;
    private String country;
    private String city;
    private String StreetName;
    private String homeNumber;

    public void setPostalCode(int postalCode) {
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

    public int getPostalCode() {
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
}
