package helpers;

import models.users.AdminUser;
import models.users.ClientUser;
import models.users.OfficeUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    private Map<String, List<NotificationListeners>> listeners = new HashMap<String, List<NotificationListeners>>();

    /**
     * This method creates new relation between eventType and listeners
     *
     * @param eventType eventType is specific notification type
     */
    private void initializeSubscriber(String eventType) {
        if (this.listeners.get(eventType) == null) {
            List<NotificationListeners> notificationListeners = new ArrayList<NotificationListeners>();
            this.listeners.put(eventType, notificationListeners);
        }
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void subscribe(String eventType, AdminUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void subscribe(String eventType, OfficeUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void subscribe(String eventType, ClientUser listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void unsubscribe(String eventType, AdminUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void unsubscribe(String eventType, OfficeUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void unsubscribe(String eventType, ClientUser listener) {
        this.listeners.get(eventType).remove(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param data is object which is send to listeners
     */
    public void notify(String eventType, Object data) {
        for (NotificationListeners notificationListeners : this.listeners.get(eventType)) {
            notificationListeners.update(data);
        }
    }
}
