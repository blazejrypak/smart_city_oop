package helpers;

import models.AdminUser;
import models.ClientUser;
import models.OfficeUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    private Map<String, List<NotificationListeners>> listeners = new HashMap<String, List<NotificationListeners>>();

    private void initializeSubscriber(String eventType) {
        if (this.listeners.get(eventType) == null) {
            List<NotificationListeners> notificationListeners = new ArrayList<NotificationListeners>();
            this.listeners.put(eventType, notificationListeners);
        }
    }

    public void subscribe(String eventType, AdminUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    public void subscribe(String eventType, OfficeUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    public void subscribe(String eventType, ClientUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    public void unsubscribe(Object eventType, AdminUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    public void unsubscribe(Object eventType, OfficeUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    public void unsubscribe(Object eventType, ClientUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    public void notify(String eventType, Object data) {
        for (NotificationListeners notificationListeners : this.listeners.get(eventType)) {
            notificationListeners.update(data);
        }
    }
}
