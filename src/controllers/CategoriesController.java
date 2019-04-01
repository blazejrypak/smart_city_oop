package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.GeneralCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private static AnchorPane holderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label street_light_count;


    @FXML
    private Label potholes_count;


    @FXML
    private Label bajk_count;


    @FXML
    private Label graffiti_count;

    public void setHolderPane(AnchorPane holderPane) {
        CategoriesController.holderPane = holderPane;
    }

    @FXML
    void getEvent(MouseEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<GeneralCategory> generalCategories = dataStorage.getAllCategories();
        System.out.println("List of categories");
        for (GeneralCategory gc: generalCategories) {
            System.out.println(gc.getType());
        }
    }
}
