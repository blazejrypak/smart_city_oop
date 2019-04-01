package authentification;

import helpers.DataStorage;
import models.User;

import java.util.ArrayList;

public class Login {
    private DataStorage dataStorage = DataStorage.getInstance();
    private ArrayList<User> userList = dataStorage.getAllUsers();

    public void login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                dataStorage.setLoggedInUser(user);
                System.out.println(username + " is logged in");
            }
        }
    }
}
