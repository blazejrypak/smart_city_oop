package models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GeneralCategory implements Category {
    public GeneralCategory() {
        setId(++id_increment);
    }

    public static void setId_increment(int id_increment) {
        GeneralCategory.id_increment = id_increment;
    }

    private static int id_increment = 0;
    public ArrayList<CategoryEvent> getCategoryEvents() {
        return categoryEvents;
    }

    public void addCategoryEvent(CategoryEvent event) {
        this.categoryEvents.add(event);
    }

    public void setCategoryEvents(ArrayList<CategoryEvent> categoryEvents) {
        this.categoryEvents = categoryEvents;
    }

    private ArrayList<CategoryEvent> categoryEvents = new ArrayList<CategoryEvent>();
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getJSONObject() {
        JSONObject category = new JSONObject();
        category.put("id", this.id);
        category.put("type", this.type);
        category.put("title", this.title);
        return category;
    }
}
