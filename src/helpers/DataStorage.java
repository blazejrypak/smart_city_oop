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
        loadUsers();
        loadCategories();
        generalCategories = getAllCategories();
    }

    public void updateState() {
        saveUsers();
        loadUsers();
    }

    public void addCategory(GeneralCategory generalCategory) {
        this.generalCategories.add(generalCategory);
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

    public ArrayList<GeneralCategory> getGeneralCategories() {
        return this.generalCategories;
    }

    private ArrayList<GeneralCategory> getAllCategories() {
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

    public void saveCategories() {
        this.jsonArrayCategories.clear();
        for (GeneralCategory generalCategory: this.generalCategories) {
            if (generalCategory.getCategoryEvents() != null) {
                JSONArray category_events = new JSONArray();
                for (CategoryEvent event: generalCategory.getCategoryEvents()) {
                    category_events.add(event.getJSONObject());
                }
                generalCategory.getJSONObject().put("category_events", category_events);
            }
            this.jsonArrayCategories.add(generalCategory.getJSONObject());
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

    private void saveUsers() {
        if (loggedInUser != null) {
            for (Object obj: this.jsonArrayUsers) {
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

    public void addUser(User user) {
        this.jsonArrayUsers.add(user.getJSONObject());

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
