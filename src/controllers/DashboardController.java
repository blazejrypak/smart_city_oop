package controllers;

import com.jfoenix.controls.JFXButton;
import helpers.DataStorage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.users.AdminUser;
import models.users.OfficeUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private JFXButton btnNotifications;

    @FXML
    private JFXButton btnListOfCategoryEvents;

    @FXML
    private JFXButton btnAddCategory;

    @FXML
    private JFXButton btnAddCategoryEvent;

    @FXML
    private AnchorPane holderPane;

    @FXML
    private VBox vertical_menu;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnEvents;

    @FXML
    private JFXButton btnProfile;

    private AnchorPane home, profiles, events, add_category, add_category_event, list_of_category_events, notifications;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (dataStorage.getLoggedInUser().getRole().equals(AdminUser.class.getSimpleName())) {
            vertical_menu.getChildren().remove(btnAddCategory);
            vertical_menu.getChildren().remove(btnAddCategoryEvent);
            vertical_menu.getChildren().remove(btnListOfCategoryEvents);
            vertical_menu.getChildren().remove(btnEvents);
        } else if (dataStorage.getLoggedInUser().getRole().equals(OfficeUser.class.getSimpleName())) {
            vertical_menu.getChildren().remove(btnAddCategoryEvent);
        } else {
            vertical_menu.getChildren().remove(btnAddCategory);
            vertical_menu.getChildren().remove(btnNotifications);
            vertical_menu.getChildren().remove(btnListOfCategoryEvents);
        }
        //Load all fxmls in a cache
        try {
            home = FXMLLoader.load(getClass().getResource("/views/dashboard/Home.fxml"));
            profiles = FXMLLoader.load(getClass().getResource("/views/dashboard/Profiles.fxml"));
            events = FXMLLoader.load(getClass().getResource("/views/dashboard/Categories.fxml"));
            add_category = FXMLLoader.load(getClass().getResource("/views/dashboard/AddCategory.fxml"));
            add_category_event = FXMLLoader.load(getClass().getResource("/views/dashboard/AddCategoryEvent.fxml"));
            list_of_category_events = FXMLLoader.load(getClass().getResource("/views/dashboard/ListOfCategoryEvents.fxml"));
            notifications = FXMLLoader.load(getClass().getResource("/views/dashboard/Notifications.fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void switchHome(ActionEvent event) {
        setNode(home);

    }

    @FXML
    private void switchEvents(ActionEvent event) {
        setNode(events);

    }

    @FXML
    private void switchProfile(ActionEvent event) {
        setNode(profiles);
    }

    @FXML
    private void switchAddCategory(ActionEvent event) {
        setNode(add_category);
    }

    @FXML
    private void switchAddCategoryEvent(ActionEvent event) {
        setNode(add_category_event);
    }

    @FXML
    private void switchListOfCategoryEvents(ActionEvent event) {
        setNode(list_of_category_events);
    }

    @FXML
    private void switchNotifications(ActionEvent event) {
        setNode(notifications);
    }

    @FXML
    private void logout(ActionEvent event) {
        dataStorage.saveData();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/account/login.fxml"));

            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
