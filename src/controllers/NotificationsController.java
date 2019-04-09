package controllers;

import com.jfoenix.controls.JFXListView;
import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationsController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private ListView list_view;

    @FXML
    private Pane pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXListView<Label> list = new JFXListView<Label>();
        for (String notification : dataStorage.getLoggedInUser().getAllNotifications()) {
            list.getItems().add(new Label(notification));
        }
        list.getStyleClass().add("mylistview");
        list.setPrefWidth(1170);
        list.setPrefHeight(650);
        pane.getChildren().add(list);
    }
}
