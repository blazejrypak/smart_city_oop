package models.authentification;

import helpers.DataStorage;
import models.users.User;

import java.util.ArrayList;

/**
 * This is Login class for logging in user.
 */
public class Login {
    private DataStorage dataStorage = DataStorage.getInstance();
    private ArrayList<User> userList = dataStorage.getAllUsers(User.class, User.class.getSimpleName());

    /**
     * This is the method to login user by username and password
     *
     * @param username username of user
     * @param password password of user
     */
    public void login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                dataStorage.setLoggedInUser(user);
                System.out.println(username + " is logged in");
            }
        }
    }
}
