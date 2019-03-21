package models;

import helpers.DataStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AdminUser extends User {
    private static AdminUser adminUserInstance;
    private DataStorage dataStorage = DataStorage.getInstance();
    private JSONArray usersObjects = dataStorage.getAllUsers();

    private AdminUser() {
        super();
    }

    public AdminUser getAdminUserInstance() {
        if (adminUserInstance == null) {
            adminUserInstance = new AdminUser();
        }
        return adminUserInstance;
    }

    private void deleteFromDatabase(int id) {
        for (Object obj: this.usersObjects) {
            JSONObject user = (JSONObject) obj;
            if (user.get("id").equals(id)) {
                this.usersObjects.remove(user);
            }
        }
    }

    public void deleteUser(User user) {
        deleteFromDatabase(user.getId());
    }
}
