package models;

import helpers.DataStorage;

import java.util.List;

public class AdminUser extends User {
    private static AdminUser adminUserInstance;
    private DataStorage dataStorage = DataStorage.getInstance();

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
        List<User> userList = dataStorage.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                userList.remove(userList.get(i));
            }
        }
    }

    public void deleteUser(User user) {
        deleteFromDataStorage(user.getId());
    }
}
