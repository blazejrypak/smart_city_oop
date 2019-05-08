package models.users;

import helpers.NotificationListeners;
import models.ContactDetails;
import models.Notification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class User implements NotificationListeners {
    private static int incrementId = 0;
    private int id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private ContactDetails contactDetails = new ContactDetails();
    private String role;

    private ArrayList<Notification> notifications = new ArrayList<>();

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

    private JSONArray getNotificationsJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Notification notification: this.notifications) {
            jsonArray.add(notification.getJSONObject());
        }
        return jsonArray;
    }

    /**
     * @return JSONObject user
     */
    public JSONObject getJSONObject() {
        JSONObject user = new JSONObject();
        user.put("id", this.id);
        user.put("username", this.username);
        user.put("password", this.password);
        user.put("first_name", this.first_name);
        user.put("last_name", this.last_name);
        user.put("notifications", getNotificationsJsonArray());
        if (this.contactDetails != null) {
            user.put("contactDetails", this.contactDetails.getJSONObject());
        }
        user.put("role", this.role);
        return user;
    }

    /**
     * This method populate fields from JSONObject
     * @param user JSONObject user
     */
    public void populate(JSONObject user) {
        setId(((Number) user.get("id")).intValue());
        setUsername((String) user.get("username"));
        setPassword((String) user.get("password"));
        setFirst_name((String) user.get("first_name"));
        setLast_name((String) user.get("last_name"));
        JSONObject jsonObjectContactDetails = (JSONObject) user.get("contactDetails");
        contactDetails.setEmail((String) jsonObjectContactDetails.get("email"));
        contactDetails.setGender((String) jsonObjectContactDetails.get("gender"));
        contactDetails.setPhone_number((String) jsonObjectContactDetails.get("phone_number"));
        JSONObject jsonObjectAddress = (JSONObject) jsonObjectContactDetails.get("address");
        contactDetails.getAddress().populate(jsonObjectAddress);
        setRole((String) user.get("role"));
        JSONArray jsonArrayNotifications = (JSONArray) user.get("notifications");
        for (Object object : jsonArrayNotifications) {
            Notification new_notification = new Notification();
            new_notification.populate((JSONObject) object);
            this.notifications.add(new_notification);
        }
    }

    public ArrayList<Notification> getAllNotifications() {
        return notifications;
    }

    public void addNotification(String message) {
        Notification new_notification = new Notification();
        new_notification.setLocalDate(LocalDate.now());
        new_notification.setMessage(message);
        this.notifications.add(new_notification);
    }

    @Override
    public void update(Object object) {

    }
}
