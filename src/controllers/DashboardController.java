package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane holderPane;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnEvents;

    @FXML
    private JFXButton btnProfile;

    private AnchorPane home, profiles, events, add_category;
    @FXML
    private JFXButton btnControls;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load all fxmls in a cache
        try {
            home = FXMLLoader.load(getClass().getResource("/views/dashboard/Home.fxml"));
            profiles = FXMLLoader.load(getClass().getResource("/views/dashboard/Profiles.fxml"));
            events = FXMLLoader.load(getClass().getResource("/views/dashboard/Categories.fxml"));
            add_category = FXMLLoader.load(getClass().getResource("/views/dashboard/AddCategory.fxml"));
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
}
