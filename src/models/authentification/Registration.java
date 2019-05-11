package models.authentification;

import helpers.DataStorage;
import models.users.AdminUser;
import models.users.User;

import java.util.ArrayList;

/**
 * Class Registration to register user
 */
public class Registration {
    private DataStorage dataStorage = DataStorage.getInstance();
    private ArrayList<User> userList = dataStorage.getAllUsers(User.class, User.class.getSimpleName());

    /**
     * This is method to find out, whether user does not exist.
     *
     * @param username username of user
     * @return boolean
     */
    private boolean usernameNotExist(String username) {
        for (User user: dataStorage.getAllUsers(User.class, User.class.getSimpleName())) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to register user with username and password
     *
     * @param username username of user
     * @param password password of user
     * @return boolean
     */
    public boolean register(String username, String password) {
        if (usernameNotExist(username)) {
            User new_user = new AdminUser();
            new_user.setUsername(username);
            new_user.setPassword(password);
            dataStorage.addUser(new_user);
            return true;
        }
        return false;
    }
}