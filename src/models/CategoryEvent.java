package models;

import org.json.simple.JSONObject;

public class CategoryEvent {
    private static int incrementId = 0;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String message;
    private Address address;
    private Localization localization;

    public CategoryEvent() {
        super();
        this.id = ++incrementId;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public JSONObject getJSONObject() {
        JSONObject categoryEvent = new JSONObject();
        categoryEvent.put("id", this.id);
        categoryEvent.put("title", this.title);
        categoryEvent.put("message", this.message);
        categoryEvent.put("address", this.address.getJSONObject());
        categoryEvent.put("localization", this.localization.getJSONObject());
        return categoryEvent;
    }
}
