package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helpers.DataStorage;
import helpers.NotificationListeners;
import helpers.NotificationManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class CategoryEvent extends RecursiveTreeObject<CategoryEvent> {
    DataStorage dataStorage = DataStorage.getInstance();
    private static int incrementId = 0;
    private int id;

    public Set<Integer> getSubscribersUID() {
        return subscribersUID;
    }

    public void setSubscribersUID(Set<Integer> subscribersUID) {
        this.subscribersUID = subscribersUID;
    }

    private Set<Integer> subscribersUID = new HashSet<>(); // set only for followers to this event

    public enum STATES {
        TO_DO, IN_PROGRESS, DONE;

        public String getTitle() throws NullPointerException {
            STATES state = STATES.valueOf(super.toString());
            switch (state) {
                case TO_DO:
                    return "To do";
                case IN_PROGRESS:
                    return "In progress";
                case DONE:
                    return "Done";
            }
            throw new NullPointerException();
        }
    }

    private NotificationManager notificationManager = new NotificationManager();

    public void addSubscriber(int UID, String eventType, NotificationListeners listener) {
        notificationManager.subscribe(eventType, listener);
        if (eventType.equals("new_state")) { // followers to this event, listeners for new state
            this.subscribersUID.add(UID);
        }
    }

    public void removeSubscriber(int UID, String eventType, NotificationListeners listener) {
        notificationManager.unsubscribe(eventType, listener);
        if (eventType.equals("new_state")) {
            this.subscribersUID.add(UID);
        }
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
        JSONArray jsonArraySub = new JSONArray();
        for (int i : this.subscribersUID) {
            jsonArraySub.add(i);
        }
        categoryEvent.put("subscribers", jsonArraySub);
        return categoryEvent;
    }

    public void populate(JSONObject jsonObject) {
        this.setUID(((Number) jsonObject.get("UID")).intValue());
        this.setTitle((String) jsonObject.get("title"));
        this.setMessage((String) jsonObject.get("message"));
        this.setState(CategoryEvent.STATES.valueOf((String) jsonObject.get("state")));
        Address address = new Address();
        address.populate((JSONObject) jsonObject.get("address"));
        this.setAddress(address);
    }
}
