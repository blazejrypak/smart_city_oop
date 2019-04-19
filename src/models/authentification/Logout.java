package models.authentification;

import helpers.DataStorage;

public class Logout {
    private DataStorage dataStorage = DataStorage.getInstance();

    public void logout() {
        dataStorage.setLoggedInUser(null);
        System.out.println("Succesfuly logged out.");
    }
}
