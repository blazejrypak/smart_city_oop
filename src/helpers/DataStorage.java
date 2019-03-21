package helpers;

import models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private JSONParser parser = new JSONParser();
    private JSONArray jsonArrayUsers;
    private JSONArray jsonArrayCurrentEvents;
    private String USERS = "/home/bubko/IdeaProjects/SmartCity/src/helpers/users.json";
    private String CURRENT_EVENTS = "/home/bubko/IdeaProjects/SmartCity/src/helpers/current_events.json";

    private Map<String, String> eventTypes = new HashMap<>();

    private DataStorage() {
        eventTypes.put("street_light", "street_light");
        eventTypes.put("pothole", "pothole");
        eventTypes.put("bajk", "bajk");
        eventTypes.put("graffiti", "graffiti");
        updateState();
    }

    public String getEventType(String key) {
        return this.eventTypes.get(key);
    }

    private void updateState() {
        loadUsers();
        loadCurrentEvents();
    }

    private void loadCurrentEvents() {
        try {

            Object obj = parser.parse(new FileReader(CURRENT_EVENTS));

            this.jsonArrayCurrentEvents = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getAllUsers() {
        return this.jsonArrayUsers;
    }

    public JSONArray getEventsOfType(String type) {
        JSONArray events = new JSONArray();
        for (Object jsonArrayCurrentEvent : jsonArrayCurrentEvents) {
            JSONObject obj = (JSONObject) jsonArrayCurrentEvent;
            if (obj.get("type").equals(getEventType(type))) {
                events.add(obj);
            }
        }
        return events;
    }

    public int getNumberOfEvents(String type) {
        int count = 0;
        for (Object jsonArrayCurrentEvent : jsonArrayCurrentEvents) {
            JSONObject item = new JSONObject();
            item = (JSONObject) jsonArrayCurrentEvent;
            if (item.get("type").equals(getEventType(type))) {
                count++;
            }
        }
        return count;
    }

    public void createEvent(String type) {
        JSONObject newEvent = new JSONObject();
        newEvent.put("type", getEventType(type));
        jsonArrayCurrentEvents.add(newEvent);
        try (FileWriter file = new FileWriter(CURRENT_EVENTS)) {

            file.write(jsonArrayCurrentEvents.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateState();
    }

    private void deleteEvent(int id) {
        JSONObject item_to_del = new JSONObject();
        for (int i = 0; i < jsonArrayCurrentEvents.size(); i++) {
            item_to_del = (JSONObject) jsonArrayCurrentEvents.get(i);
            if (item_to_del.get("id").equals(id)) {
                jsonArrayCurrentEvents.remove(item_to_del);
            }
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

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void registerUser(String username, String email, String password) {
        JSONObject newUser = new JSONObject();
        newUser.put("id", jsonArrayUsers.size() + 1);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("first_name", "");
        newUser.put("last_name", "");
        newUser.put("adress", "");
        newUser.put("email", email);
        newUser.put("gender", "");
        newUser.put("phone_number", "");
        newUser.put("role", "client");
        jsonArrayUsers.add(newUser);
        try (FileWriter file = new FileWriter(USERS)) {

            file.write(jsonArrayUsers.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateState();
    }

    public boolean existUsername(String username) {
        for (Object jsonArrayUser : this.jsonArrayUsers) {
            JSONObject user = (JSONObject) jsonArrayUser;
            if (user.get("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean existUser(String username, String password) {
        for (Object jsonArrayUser : this.jsonArrayUsers) {
            JSONObject user = (JSONObject) jsonArrayUser;
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
//                User usr = User.getInstance();
//                usr.setId(((Number) user.get("id")).intValue());
//                usr.setUsername((String) user.get("username"));
//                usr.setPassword((String) user.get("password"));
//                usr.setFirst_name((String) user.get("first_name"));
//                usr.setLast_name((String) user.get("last_name"));
//                usr.setAddress((String) user.get("adress"));
//                usr.setEmail((String) user.get("email"));
//                usr.setGender((String) user.get("gender"));
//                usr.setPhone_number((String) user.get("phone_number"));
//                usr.setRole((String) user.get("role"));
                return true;
            }
        }
        return false;
    }
}
