package models;

import helpers.DataStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Login {
    private DataStorage dataStorage = DataStorage.getInstance();
    private JSONArray users = dataStorage.getAllUsers();
    private static Object logged_in_user;

    public boolean login(String username, String password) {
        for (Object user_obj : users) {
            JSONObject user = (JSONObject) user_obj;
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
                if (user.get("role").equals("admin")) {
                    logged_in_user = new AdminUser();
                } else if (user.get("role").equals("citizen")) {
                    logged_in_user = new CitizenUser();
                } else {
                    logged_in_user = new OfficerUser();
                }
                return true;
            }
        }
        return false;
    }
}
