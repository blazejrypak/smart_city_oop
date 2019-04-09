package models.authentification;
import helpers.DataStorage;
import models.users.AdminUser;
import models.users.User;

import java.util.ArrayList;

public class Registration {
    private DataStorage dataStorage = DataStorage.getInstance();
    private ArrayList<User> userList = dataStorage.getAllUsers(User.class, User.class.getSimpleName());

    private boolean usernameNotExist(String username) {
        if (username.equals("bubo")) {
            return false;
        }
        return true;
    }

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