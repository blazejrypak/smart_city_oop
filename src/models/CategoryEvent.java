package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helpers.NotificationManager;
import models.users.AdminUser;
import models.users.ClientUser;
import models.users.OfficeUser;
import org.json.simple.JSONObject;

public class CategoryEvent extends RecursiveTreeObject<CategoryEvent> {
    private static int incrementId = 0;
    private int id;

    public enum STATES {
        TO_DO, IN_PROGRESS, DONE;

        public String getTitle() {
            STATES state = STATES.valueOf(super.toString());
            switch (state) {
                case TO_DO:
                    return "To do";
                case IN_PROGRESS:
                    return "In progress";
                case DONE:
                    return "Done";
            }
            return "";
        }
    }

    private NotificationManager notificationManager = new NotificationManager();

    public void addSubscriber(String eventType, AdminUser listener) {
        notificationManager.subscribe(eventType, listener);
    }

    public void addSubscriber(String eventType, OfficeUser listener) {
        notificationManager.subscribe(eventType, listener);
    }

    public void addSubscriber(String eventType, ClientUser listener) {
        notificationManager.subscribe(eventType, listener);
    }

    public void removeSubscriber(Object eventType, AdminUser listener) {
        notificationManager.unsubscribe(eventType, listener);
    }

    public void removeSubscriber(Object eventType, OfficeUser listener) {
        notificationManager.unsubscribe(eventType, listener);
    }

    public void removeSubscriber(Object eventType, ClientUser listener) {
        notificationManager.unsubscribe(eventType, listener);
    }


    public void notify(String eventType, CategoryEvent categoryEvent) {
        notificationManager.notify(eventType, categoryEvent);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title = "";
    private String message = "";

    public STATES getState() {
        return state;
    }

    public void setState(STATES state) {
        this.state = state;
    }

    private STATES state = STATES.TO_DO;
    private Address address;
    private Localization localization;

    public CategoryEvent() {
        super();
        this.id = ++incrementId;
    }

    public static void setId_increment(int id_increment) {
        CategoryEvent.incrementId = id_increment;
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

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    private int UID = 0;

    /**
     * @return return JSONObject of CategoryEvent
     */
    public JSONObject getJSONObject() {
        JSONObject categoryEvent = new JSONObject();
        categoryEvent.put("id", this.id);
        categoryEvent.put("UID", this.UID);
        categoryEvent.put("title", this.title);
        categoryEvent.put("message", this.message);
        categoryEvent.put("state", this.state.toString());
        categoryEvent.put("address", this.address.getJSONObject());
        categoryEvent.put("localization", this.localization.getJSONObject());
        return categoryEvent;
    }
}
