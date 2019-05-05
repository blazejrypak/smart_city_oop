package models.users;

import helpers.DataStorage;
import helpers.NotificationListeners;
import models.CategoryEvent;

import java.util.ArrayList;

public class OfficeUser extends User implements NotificationListeners {
    private DataStorage dataStorage = DataStorage.getInstance();

    @Override
    public void update(Object object) {
        CategoryEvent categoryEvent = (CategoryEvent) object;
        ArrayList<User> users = dataStorage.getAllUsers(User.class, User.class.getSimpleName());
        for (User user : users) {
            if (user.getRole().equals(OfficeUser.class.getSimpleName()) && this.getId() == user.getId()) {
                user.addNotification("You have another new suggestion: " + categoryEvent.getTitle() + ":  " + categoryEvent.getMessage());
                if (user.getId() == dataStorage.getLoggedInUser().getId()) {
                    dataStorage.setLoggedInUser(user); // update loggedInUser with notifications
                }
            }
        }
        dataStorage.updateUsersData(users);
    }
}
