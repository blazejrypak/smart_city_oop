package helpers;

import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStorage {
    private static DataStorage instance;
    private JSONParser parser = new JSONParser();
    private JSONArray jsonArrayUsers;
    private JSONArray jsonArrayCategories;
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
        loadUsers();
        loadCategories();
    }

    public void updateState() {
        saveUsers();
        loadUsers();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        for (Object jsonArrayUser : this.jsonArrayUsers) {
            JSONObject user = (JSONObject) jsonArrayUser;
            User usr = new User();
            usr.setId(((Number) user.get("id")).intValue());
            usr.setUsername((String) user.get("username"));
            usr.setPassword((String) user.get("password"));
            usr.setFirst_name((String) user.get("first_name"));
            usr.setLast_name((String) user.get("last_name"));
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setEmail((String) user.get("email"));
            contactDetails.setGender((String) user.get("gender"));
            contactDetails.setPhone_number((String) user.get("phone_number"));
            usr.setContactDetails(contactDetails);
            usr.setRole((String) user.get("role"));
            userList.add(usr);
        }
        return userList;
    }

    public ArrayList<GeneralCategory> getAllCategories() {
        ArrayList<GeneralCategory> categories = new ArrayList<GeneralCategory>();
        int category_increment_id = 0;
        for (Object jsonArrayCategory : this.jsonArrayCategories) {
            JSONObject category = (JSONObject) jsonArrayCategory;
            GeneralCategory cat = new GeneralCategory();
            cat.setId(((Number) category.get("id")).intValue());
            cat.setType((String) category.get("type"));
            cat.setTitle((String) category.get("tittle"));
            categories.add(cat);
            if (((Number) category.get("id")).intValue() > category_increment_id) {
                category_increment_id = ((Number) category.get("id")).intValue();
            }
            GeneralCategory.setId_increment(category_increment_id);
        }
        return categories;
    }

    public void saveCategories(ArrayList<GeneralCategory> generalCategoryArrayList) {
        this.jsonArrayCategories.clear();
        for (GeneralCategory generalCategory: generalCategoryArrayList) {

            JSONObject category = new JSONObject();
            category.put("id", generalCategory.getId());
            category.put("type", generalCategory.getType());
            category.put("tittle", generalCategory.getTitle());
            JSONArray category_events = new JSONArray();
            for (CategoryEvent event: generalCategory.getCategoryEvents()) {
                JSONObject categoryEvent = new JSONObject();
                categoryEvent.put("id", event.getId());
                categoryEvent.put("title", event.getTitle());
                categoryEvent.put("message", event.getMessage());
                category_events.add(categoryEvent);
            }
            category.put("category_events", category_events);
            this.jsonArrayCategories.add(category);
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
    }

    private void saveUsers() {
        if (loggedInUser != null) {
            JSONObject user = new JSONObject();
            user.put("id", loggedInUser.getId());
            user.put("username", loggedInUser.getUsername());
            user.put("password", loggedInUser.getPassword());
            user.put("first_name", loggedInUser.getFirst_name());
            user.put("last_name", loggedInUser.getLast_name());
            user.put("role", loggedInUser.getRole());
            for (Object obj: this.jsonArrayUsers) {
                JSONObject usr = (JSONObject) obj;
                if ((((Number) usr.get("id")).intValue()) == loggedInUser.getId()) {
                    this.jsonArrayUsers.remove(usr);
                    break;
                }
            }
            this.jsonArrayUsers.add(user);
        }
        try (FileWriter file = new FileWriter(USERS)) {

            file.write(this.jsonArrayUsers.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        JSONObject new_user = new JSONObject();
        new_user.put("id", user.getId());
        new_user.put("username", user.getUsername());
        new_user.put("password", user.getPassword());
        new_user.put("first_name", user.getFirst_name());
        new_user.put("last_name", user.getLast_name());
        new_user.put("role", user.getRole());
        this.jsonArrayUsers.add(new_user);

        try (FileWriter file = new FileWriter(USERS)) {

            file.write(this.jsonArrayUsers.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {

            Object obj = parser.parse(new FileReader(USERS));

            this.jsonArrayUsers = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        try {

            Object obj = parser.parse(new FileReader(CATEGORIES));

            this.jsonArrayCategories = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
}
