package models;

import org.json.simple.JSONObject;

public class User {
    private static int incrementId = 0;
    private int id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private ContactDetails contactDetails = new ContactDetails();
    private String role;


    public User() {
        this.id = ++incrementId;
    }

    public static int getIncrementId() {
        return incrementId;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public static void setIncrementId(int incrementId) {
        User.incrementId = incrementId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + '\n';
    }

    public JSONObject getJSONObject() {
        JSONObject user = new JSONObject();
        user.put("id", this.id);
        user.put("username", this.username);
        user.put("password", this.password);
        user.put("first_name", this.first_name);
        user.put("last_name", this.last_name);
        if (this.contactDetails != null) {
            user.put("contactDetails", this.contactDetails.getJSONObject());
        }
        user.put("role", this.role);
        return user;
    }
}
