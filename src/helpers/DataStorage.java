package helpers;

import models.CategoryEvent;
import models.GeneralCategory;
import models.users.AdminUser;
import models.users.ClientUser;
import models.users.OfficeUser;
import models.users.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class save and load files to JSON
 */
public class DataStorage {
    private static DataStorage instance;
    private JSONParser parser = new JSONParser();
    private JSONArray jsonArrayUsers;
    private JSONArray jsonArrayCategories;
    private ArrayList<GeneralCategory> generalCategories = new ArrayList<>();
    private String USERS = "/home/bubko/IdeaProjects/SmartCityFinal/src/helpers/users";
    private String CATEGORIES = "/home/bubko/IdeaProjects/SmartCityFinal/src/helpers/categories";

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    private User loggedInUser;

    private DataStorage() {
        try {
            loadUsers();
        } catch (IncorrectFilePathException e) {
            e.printStackTrace();
        }
        try {
            loadCategories();
        } catch (IncorrectFilePathException e) {
            e.printStackTrace();
        }
    }

    public void addCategory(GeneralCategory generalCategory) {
        this.generalCategories.add(generalCategory);
        saveCategories();
    }

    /**
     * @param users is Array list of users
     */
    public void updateUsersData(ArrayList<User> users) {
        this.jsonArrayUsers.clear();
        for (User user : users) {
            this.jsonArrayUsers.add(user.getJSONObject());
        }
    }

    /**
     * @param c    class of user
     * @param role user role
     * @param <T>  generic type
     * @return Array of specific class of users
     */
    public <T> ArrayList<T> getAllUsers(Class<T> c, String role) {
        boolean allUsers = false;
        if (role.equals(User.class.getSimpleName())) {
            allUsers = true;
        }
        ArrayList<T> list = new ArrayList<T>();
        for (Object jsonArrayUser : this.jsonArrayUsers) {
            JSONObject user = (JSONObject) jsonArrayUser;
            if ((user.get("role")).equals(role) || allUsers) {
                if (user.get("role").equals(AdminUser.class.getSimpleName())) {
                    AdminUser adminUser = new AdminUser();
                    adminUser.populate(user);
                    T t = c.cast(adminUser);
                    list.add(t);
                } else if (user.get("role").equals(ClientUser.class.getSimpleName())) {
                    ClientUser clientUser = new ClientUser();
                    clientUser.populate(user);
                    T t = c.cast(clientUser);
                    list.add(t);
                } else if (user.get("role").equals(OfficeUser.class.getSimpleName())) {
                    OfficeUser officeUser = new OfficeUser();
                    officeUser.populate(user);
                    T t = c.cast(officeUser);
                    list.add(t);
                } else {
                    User superUser = new User();
                    superUser.populate(user);
                    T t = c.cast(superUser);
                    list.add(t);
                }
            }
        }
        return list;
    }

    public ArrayList<GeneralCategory> getGeneralCategories() {
        this.generalCategories = getAllCategories();
        return this.generalCategories;
    }

    /**
     * This method update all categories
     * @param generalCategoryArrayList array list of categories to update with
     */
    public void updateCategories(ArrayList<GeneralCategory> generalCategoryArrayList) {
        this.generalCategories = generalCategoryArrayList;
        saveCategories();
    }

    /**
     * This method update specific category
     *
     * @param generalCategory is class of general category
     */
    public void updateCategories(GeneralCategory generalCategory) {
        for (GeneralCategory category : this.generalCategories) {
            if (category.getId() == generalCategory.getId()) {
                this.generalCategories.remove(category);
                this.generalCategories.add(generalCategory);
                break;
            }
        }
        saveCategories();
    }

    /**
     * @return Array List of all categories
     */
    private ArrayList<GeneralCategory> getAllCategories() {
        ArrayList<GeneralCategory> categories = new ArrayList<GeneralCategory>();
        int category_increment_id = 0;
        for (Object jsonArrayCategory : this.jsonArrayCategories) {
            int category_event_increment_id = 0;
            JSONObject category = (JSONObject) jsonArrayCategory;
            GeneralCategory generalCategory = new GeneralCategory();
            generalCategory.setId(((Number) category.get("id")).intValue());
            generalCategory.setType((String) category.get("type"));
            generalCategory.setTitle((String) category.get("title"));
            ArrayList<CategoryEvent> categoryEvents = new ArrayList<>();
            for (Object event : (JSONArray) category.get("category_events")) {
                JSONObject json_event = (JSONObject) event;
                CategoryEvent categoryEvent = new CategoryEvent();
                categoryEvent.populate(json_event);
                if (json_event.get("subscribers") != null) {
                    for (Object o : (JSONArray) json_event.get("subscribers")) {
                        for (User user : getAllUsers(User.class, User.class.getSimpleName())) {
                            if (user.getId() == ((Number) o).intValue()) {
                                categoryEvent.addSubscriber(user.getId(), "new_state", user);
                            }
                        }
                    }
                }
                categoryEvents.add(categoryEvent);
                if (((Number) json_event.get("id")).intValue() > category_event_increment_id) {
                    category_event_increment_id = ((Number) json_event.get("id")).intValue();
                }
                CategoryEvent.setId_increment(category_event_increment_id);
            }

            generalCategory.setCategoryEvents(categoryEvents);
            categories.add(generalCategory);

            if (((Number) category.get("id")).intValue() > category_increment_id) {
                category_increment_id = ((Number) category.get("id")).intValue();
            }
            GeneralCategory.setId_increment(category_increment_id);
        }
        return categories;
    }

    /**
     * This method save all categories to JSON file
     */
    private void saveCategories() {
        this.jsonArrayCategories.clear();
        for (GeneralCategory generalCategory : this.generalCategories) {
            JSONObject general_category_json_object = new JSONObject();
            if (generalCategory.getCategoryEvents() != null) {
                JSONArray category_events = new JSONArray();
                for (CategoryEvent event : generalCategory.getCategoryEvents()) {
                    category_events.add(event.getJSONObject());
                }
                general_category_json_object = generalCategory.getJSONObject();
                general_category_json_object.put("category_events", category_events);
            }
            this.jsonArrayCategories.add(general_category_json_object);
        }
        try (FileWriter file = new FileWriter(CATEGORIES)) {

            file.write(this.jsonArrayCategories.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        saveUsers();
        saveCategories();
    }

    /**
     * This method save all users to JSON file.
     */
    private void saveUsers() {
        if (loggedInUser != null) {
            for (Object obj : this.jsonArrayUsers) {
                JSONObject usr = (JSONObject) obj;
                if ((((Number) usr.get("id")).intValue()) == loggedInUser.getId()) {
                    this.jsonArrayUsers.remove(usr);
                    break;
                }
            }
            this.jsonArrayUsers.add(loggedInUser.getJSONObject());
        }
        try (FileWriter file = new FileWriter(USERS)) {

            file.write(this.jsonArrayUsers.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method add user to json list of users and save it to JSON file.
     *
     * @param user class of User
     */
    public void addUser(User user) {
        this.jsonArrayUsers.add(user.getJSONObject());

        try (FileWriter file = new FileWriter(USERS)) {

            file.write(this.jsonArrayUsers.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method load all user from JSON file to json user list
     */
    private void loadUsers() throws IncorrectFilePathException {
        try {

            Object obj = parser.parse(new FileReader(USERS));

            this.jsonArrayUsers = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            throw new IncorrectFilePathException("Incorrect path to filename " + USERS, e);
        }
    }

    /**
     * This method load all user from JSON file to json category list
     */
    private void loadCategories() throws IncorrectFilePathException {
        try {

            Object obj = parser.parse(new FileReader(CATEGORIES));

            this.jsonArrayCategories = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            throw new IncorrectFilePathException("Incorrect path to filename " + CATEGORIES, e);
        }
    }

    /**
     * This methot always return just one instance, here is used Singleton pattern
     *
     * @return instance of this class
     */
    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
}
