package models;

import helpers.DataStorage;

import java.util.List;

public class Register {
    private DataStorage dataStorage = DataStorage.getInstance();
    private List<User> userList = dataStorage.getAllUsers();
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
