package models.users;

import helpers.DataStorage;
import models.CategoryEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AdminUser is just specific role of user, AdminUser extends User class
 */
public class AdminUser extends User {
    private DataStorage dataStorage = DataStorage.getInstance();

    public AdminUser() {
        super();
    }

    private void deleteFromDataStorage(int id) {
        List<User> userList = dataStorage.getAllUsers(User.class, User.class.getSimpleName());
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                userList.remove(userList.get(i));
            }
        }
    }

    public void deleteUser(User user) {
        deleteFromDataStorage(user.getId());
    }

    /**
     * This method add notification to user
     * @param object object sent by NotificationManager
     */
    @Override
    public void update(Object object) {
        CategoryEvent categoryEvent = (CategoryEvent) object;
        ArrayList<User> users = dataStorage.getAllUsers(User.class, User.class.getSimpleName());
        for (User user : users) {
            if (user.getRole().equals(ArrayList.class.getSimpleName()) && this.getId() == user.getId()) {
                System.out.println("adding notification to user " + user.getUsername());
                user.addNotification(categoryEvent.getTitle() + " " + categoryEvent.getMessage());
                if (user.getId() == dataStorage.getLoggedInUser().getId()) {
                    dataStorage.setLoggedInUser(user); // update loggedInUser with notifications
                }
            }
        }
        dataStorage.updateUsersData(users);
    }


}
