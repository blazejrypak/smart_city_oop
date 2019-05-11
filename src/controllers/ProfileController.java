package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private Label id_country;

    @FXML
    private Label id_city;

    @FXML
    private Label id_street_name;


    @FXML
    private Label id_postal_code;

    @FXML
    private Label id_home_number;

    @FXML
    private Label id_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_country.setText(dataStorage.getLoggedInUser().getContactDetails().getAddress().getCountry());
        id_city.setText(dataStorage.getLoggedInUser().getContactDetails().getAddress().getCity());
        id_street_name.setText(dataStorage.getLoggedInUser().getContactDetails().getAddress().getStreetName());
        id_postal_code.setText(dataStorage.getLoggedInUser().getContactDetails().getAddress().getPostalCode());
        id_home_number.setText(dataStorage.getLoggedInUser().getContactDetails().getAddress().getHomeNumber());
        id_username.setText(dataStorage.getLoggedInUser().getUsername());
    }

}
