package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private User user;
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private Label username;

    @FXML
    private Label adress;

    @FXML
    private Label role;

    @FXML
    private Label email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(dataStorage.getLoggedInUser().getUsername());
        role.setText(dataStorage.getLoggedInUser().getRole());
        email.setText(dataStorage.getLoggedInUser().getContactDetails().getEmail());
    }

}
