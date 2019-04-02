package models;

import org.json.simple.JSONObject;

public class ContactDetails {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getFax() {
        return fax;
    }

    public void setFax(int fax) {
        this.fax = fax;
    }

    private String email;
    private String gender;
    private Address address;
    private String phone_number;
    private int fax;

    public JSONObject getJSONObject() {
        JSONObject contactDetails = new JSONObject();
        contactDetails.put("email", this.email);
        contactDetails.put("gender", this.gender);
        if (this.address != null) {
            contactDetails.put("address", this.address.getJSONObject());
        }
        contactDetails.put("phone_number", this.phone_number);
        contactDetails.put("fax", this.fax);
        return contactDetails;
    }

}
