package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Notification extends RecursiveTreeObject<Notification> {
    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    private LocalDate localDate;
    private String message;
    private Boolean seen = false;

    public JSONObject getJSONObject() {
        JSONObject notification = new JSONObject();
        notification.put("localDate", this.localDate.toString());
        notification.put("message", this.message);
        notification.put("seen", this.seen);
        return notification;
    }

    public void populate(JSONObject notification){
        setLocalDate(LocalDate.parse((String) notification.get("localDate")));
        setMessage((String) notification.get("message"));
        setSeen((Boolean) notification.get("seen"));
    }
}
