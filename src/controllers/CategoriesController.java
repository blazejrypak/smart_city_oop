package controllers;

import helpers.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.GeneralCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private ListView list_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<GeneralCategory> generalCategories = dataStorage.getGeneralCategories();
        List<String> list = new ArrayList<String>();
        for (GeneralCategory generalCategory: generalCategories) {
            list.add(generalCategory.getTitle());
        }
        ObservableList obList = FXCollections.observableList(list);
        list_view.getItems().clear();
        list_view.setItems(obList);
    }
}
