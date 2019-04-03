package controllers;

import helpers.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListOfCategoryEvents implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private VBox id_vbox_container;

    @FXML
    private ComboBox<String> id_combo_category;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list = new ArrayList<String>();
        for (GeneralCategory gc : dataStorage.getGeneralCategories()) {
            list.add(gc.getTitle());
        }
        ObservableList obList = FXCollections.observableList(list);
        id_combo_category.getItems().clear();
        id_combo_category.setItems(obList);
    }

    private VBox generateVBox(double prefHeight, double prefWidth, double spacing, String id_label, String label_value) {
        VBox vBox = new VBox();
        vBox.prefHeight(prefHeight);
        vBox.prefWidth(prefWidth);
        vBox.setSpacing(spacing);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label(id_label);
        label.setId(id_label);
        label.setText(label_value);
        label.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        return vBox;
    }

    private GeneralCategory getCategory(String title) {
        for (GeneralCategory genCat: dataStorage.getGeneralCategories()) {
            if (genCat.getTitle().equals(title)) {
                return genCat;
            }
        }
        return null;
    }

    private void changeState(ActionEvent actionEvent) {
        CategoryEvent categoryEvent = new CategoryEvent(); // treba este ziskat event z action_event
        for (User user: dataStorage.getAllUsers(ClientUser.class, "client")){
            categoryEvent.addSubscriber("new_state", (ClientUser) user);
        }
        categoryEvent.notify("new_state", categoryEvent);
    }

    @FXML
    private void showCategory(ActionEvent actionEvent) {
        double prefHeight = 600, prefWidth = 900, spacing = 50;
        if (getCategory(this.id_combo_category.getValue()) != null && getCategory(this.id_combo_category.getValue()).getCategoryEvents() != null) {
            System.out.println("showCategory");
            id_vbox_container.getChildren().clear();
            for (CategoryEvent categoryEvent: getCategory(this.id_combo_category.getValue()).getCategoryEvents()) {
                HBox category = new HBox();
                category.setAlignment(Pos.CENTER);
                category.setPrefWidth(900);
                category.getChildren().clear();
                category.getChildren().add(generateVBox(600, 200, spacing, "id_title", categoryEvent.getTitle()));
                category.getChildren().add(generateVBox(prefHeight, 400, spacing, "id_message", categoryEvent.getMessage()));
                category.getChildren().add(generateVBox(prefHeight, 300, spacing, "id_address", categoryEvent.getAddress().getStreetName()));
                id_vbox_container.getChildren().add(category);
            }
        }
    }
}