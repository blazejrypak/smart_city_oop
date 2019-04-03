package models;

import helpers.NotificationListeners;

import java.util.ArrayList;

public class OfficeUser extends User implements NotificationListeners {
    ArrayList<CategoryEvent> notifications = new ArrayList<CategoryEvent>();

    @Override
    public void update(Object object) {
        System.out.println(((CategoryEvent) object).getTitle());
        notifications.add((CategoryEvent) object);
    }
}
