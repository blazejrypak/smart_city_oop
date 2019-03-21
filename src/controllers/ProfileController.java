package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private User user;

    @FXML
    private Label username;

    @FXML
    private Label adress;

    @FXML
    private Label role;

    @FXML
    private Label email;

    public Label getUsername() {
        return username;
    }

    public Label getRole() {
        return role;
    }

    public Label getEmail() {
        return email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.user = User.getInstance();
//        username.setText(this.user.getUsername());
//        adress.setText(this.user.getAddress());
//        role.setText(this.user.getRole());
//        email.setText(this.user.getEmail());
    }

}
