package models;

import helpers.DataStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Register {
    private DataStorage dataStorage = DataStorage.getInstance();
    private JSONArray users = dataStorage.getAllUsers();

    private boolean usernameNotExist(String username) {
        for (Object user_obj : users) {
            JSONObject user = (JSONObject) user_obj;
            if (user.get("username").equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean registerUser(String username, String email, String password) {
        if (usernameNotExist(username)) {
            JSONObject newUser = new JSONObject();
            newUser.put("id", users.size() + 1);
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("first_name", "");
            newUser.put("last_name", "");
            newUser.put("adress", "");
            newUser.put("email", email);
            newUser.put("gender", "");
            newUser.put("phone_number", "");
            newUser.put("role", "citizen");
            users.add(newUser);
            dataStorage.saveJsonArray(users, "users");
            return true;
        }
        return false;
    }
}
