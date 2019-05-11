package helpers;

import java.util.*;

public class NotificationManager {
    private Map<String, Set<NotificationListeners>> listeners = new HashMap<String, Set<NotificationListeners>>();

    /**
     * This method creates new relation between eventType and listeners
     *
     * @param eventType eventType is specific notification type
     */
    private void initializeSubscriber(String eventType) {
        if (this.listeners.get(eventType) == null) {
            Set<NotificationListeners> notificationListeners = new HashSet<>();
            this.listeners.put(eventType, notificationListeners);
        }
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void subscribe(String eventType, NotificationListeners listener) {
        initializeSubscriber(eventType);
        this.listeners.get(eventType).add(listener);
    }

    /**
     * @param eventType is specific type of notification
     * @param listener  is listener of specific notification type
     */
    public void unsubscribe(String eventType, NotificationListeners listener) {
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
