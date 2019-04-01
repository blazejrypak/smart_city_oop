package models;

import java.util.ArrayList;

public interface Category {
    String getType();

    void setType(String type);

    int getId();

    void setId(int id);

    void setTitle(String tittle);

    String getTitle();

    <T> ArrayList<T> getCategoryEvents();
}
