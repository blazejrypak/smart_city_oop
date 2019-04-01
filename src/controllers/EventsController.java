package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {
    private DataStorage db = DataStorage.getInstance();

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
        EventsController.holderPane = holderPane;
    }

    @FXML
    void getEvent(MouseEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        street_light_count.setText(Integer.toString(db.getNumberOfEvents(db.getEventType("street_light"))));
//        potholes_count.setText(Integer.toString(db.getNumberOfEvents(db.getEventType("pothole"))));
//        bajk_count.setText(Integer.toString(db.getNumberOfEvents(db.getEventType("bajk"))));
//        graffiti_count.setText(Integer.toString(db.getNumberOfEvents(db.getEventType("graffiti"))));
    }
}
