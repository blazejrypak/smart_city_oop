package models;

import helpers.DataStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AdminUser extends User {
    private static AdminUser adminUserInstance;
    private DataStorage dataStorage = DataStorage.getInstance();
    private JSONArray usersObjects = dataStorage.getAllUsers();

    public AdminUser() {
        super();
    }

    public AdminUser getAdminUserInstance() {
        if (adminUserInstance == null) {
            adminUserInstance = new AdminUser();
        }
        return adminUserInstance;
    }

    private void deleteFromDataStorage(int id) {
        for (Object obj: this.usersObjects) {
            JSONObject user = (JSONObject) obj;
            if (user.get("id").equals(id)) {
                this.usersObjects.remove(user);
            }
        }
        dataStorage.saveJsonArray(usersObjects, "users");
    }

    public void deleteUser(User user) {
        deleteFromDataStorage(user.getId());
    }
}
