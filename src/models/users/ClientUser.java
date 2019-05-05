package models.users;

import helpers.DataStorage;
import helpers.NotificationListeners;
import models.CategoryEvent;

import java.util.ArrayList;

public class ClientUser extends User implements NotificationListeners {
    private DataStorage dataStorage = DataStorage.getInstance();
    ArrayList<CategoryEvent> notifications = new ArrayList<CategoryEvent>();

    @Override
    public void update(Object object) {
        CategoryEvent categoryEvent = (CategoryEvent) object;
        ArrayList<User> users = dataStorage.getAllUsers(User.class, User.class.getSimpleName());
        for (User user : users) {
            if (user.getRole().equals(ClientUser.class.getSimpleName()) && this.getId() == user.getId()) {
                System.out.println("adding notification to user " + user.getUsername());
                if (categoryEvent.getState() == CategoryEvent.STATES.IN_PROGRESS) {
                    user.addNotification("Your suggestion is in a state of solution.");
                } else if (categoryEvent.getState() == CategoryEvent.STATES.DONE) {
                    user.addNotification("Your suggestion has been made.");
                }
                if (user.getId() == dataStorage.getLoggedInUser().getId()) {
                    dataStorage.setLoggedInUser(user); // update loggedInUser with notifications
                }
            }
        }
        dataStorage.updateUsersData(users);
    }
}
