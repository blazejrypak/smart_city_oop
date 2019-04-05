package models;

import helpers.DataStorage;
import helpers.NotificationListeners;

import java.util.ArrayList;

public class ClientUser extends User implements NotificationListeners {
    private DataStorage dataStorage = DataStorage.getInstance();
    ArrayList<CategoryEvent> notifications = new ArrayList<CategoryEvent>();

    @Override
    public void update(Object object) {
        CategoryEvent categoryEvent = (CategoryEvent) object;
        ArrayList<User> users = dataStorage.getAllUsers(User.class, "");
        for (User user: users) {
            if (user.getRole().equals("client") && this.getId() == user.getId()) {
                System.out.println("adding notification to user " + user.getUsername());
                if (categoryEvent.getState().equals("in_progress")) {
                    user.addNotification("Your suggestion is in a state of solution.");
                } else if (categoryEvent.getState().equals("done")) {
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