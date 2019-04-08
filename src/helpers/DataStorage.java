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

    public void addCategory(GeneralCategory generalCategory) {
        this.generalCategories.add(generalCategory);
    }

    public void updateUsersData(ArrayList<User> users) {
        this.jsonArrayUsers.clear();
        for (User user: users) {
            this.jsonArrayUsers.add(user.getJSONObject());
        }
    }

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
        return this.generalCategories;
    }

    public void updateCategories(ArrayList<GeneralCategory> generalCategoryArrayList){
        this.generalCategories = generalCategoryArrayList;
    }

    public void updateCategories(GeneralCategory generalCategory){
        for (GeneralCategory category: this.generalCategories) {
            if (category.getId() == generalCategory.getId()) {
                this.generalCategories.remove(category);
                this.generalCategories.add(generalCategory);
                break;
            }
        }
    }

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
            for (Object event: (JSONArray) category.get("category_events")) {
                JSONObject json_event = (JSONObject) event;
                CategoryEvent categoryEvent = new CategoryEvent();
                categoryEvent.setTitle((String) json_event.get("title"));
                categoryEvent.setMessage((String) json_event.get("message"));
                categoryEvent.setState(CategoryEvent.STATES.valueOf((String) json_event.get("state")));

                Localization localization = new Localization();
                JSONObject json_localization = (JSONObject) json_event.get("localization");
                localization.setLatitude(((Number) json_localization.get("latitude")).doubleValue());
                localization.setLatitude(((Number) json_localization.get("longitude")).doubleValue());

                Address address = new Address();
                JSONObject json_address = (JSONObject) json_event.get("address");
                address.setCountry((String) json_address.get("country"));
                address.setCity((String) json_address.get("city"));
                address.setPostalCode((String) json_address.get("postal_code"));
                address.setHomeNumber((String) json_address.get("homeNumber"));
                address.setStreetName((String) json_address.get("street_name"));

                categoryEvent.setLocalization(localization);
                categoryEvent.setAddress(address);

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

    private void saveCategories() {
        this.jsonArrayCategories.clear();
        for (GeneralCategory generalCategory: this.generalCategories) {
            JSONObject general_category_json_object = new JSONObject();
            if (generalCategory.getCategoryEvents() != null) {
                JSONArray category_events = new JSONArray();
                for (CategoryEvent event: generalCategory.getCategoryEvents()) {
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
