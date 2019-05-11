package controllers;

import helpers.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserSettingsDetailsController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    private int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        UserSettingsDetailsController.UID = UID;
    }

    private static int UID;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    @FXML
    private Label id_state;

    @FXML
    private ComboBox combo_box;

    @FXML
    private Button id_submit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<String> list = new HashSet<String>();
        for (User user : dataStorage.getAllUsers(User.class, User.class.getSimpleName())) {
            list.add(user.getRole());
        }
        List<String> options = new ArrayList<>();
        options.addAll(list);
        ObservableList obList = FXCollections.observableList(options);
        for (User u : dataStorage.getAllUsers(User.class, User.class.getSimpleName())) {
            if (u.getId() == UID && UID != 0) {
                this.user = u;
                break;
            }
        }
        if (this.user != null) {
            id_state.setText(this.user.getUsername());
            combo_box.getItems().clear();
            combo_box.setItems(obList);
        }
    }


    @FXML
    private void submit(ActionEvent event) {
        ArrayList<User> userArrayList = dataStorage.getAllUsers(User.class, User.class.getSimpleName());
        // get all types of user roles
        for (User user : userArrayList) {
            if (user.getId() == UID) {
                user.setRole(String.valueOf(this.combo_box.getValue()));
                break;
            }
        }
        dataStorage.updateUsersData(userArrayList);
        backToDashboard(event);
    }

    @FXML
    private void backToDashboard(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
