package models.authentification;

import helpers.DataStorage;

/**
 * Class Logout to logout user from app.
 */
public class Logout {
    private DataStorage dataStorage = DataStorage.getInstance();

    /**
     * This is method to logout user from app.
     */
    public void logout() {
        dataStorage.saveData();
        dataStorage.setLoggedInUser(null);
        System.out.println("Succesfuly logged out.");
    }
}
