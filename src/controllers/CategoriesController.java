package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.GeneralCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<GeneralCategory> generalCategories = dataStorage.getGeneralCategories();
        System.out.println("List of categories");
        for (GeneralCategory gc: generalCategories) {
            System.out.println(gc.getType());
        }
    }
}
