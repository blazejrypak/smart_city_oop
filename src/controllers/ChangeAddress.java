package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeAddress {
    DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private TextField id_country;

    @FXML
    private TextField id_city;

    @FXML
    private TextField id_postal_code;

    @FXML
    private TextField id_street_name;

    @FXML
    private TextField id_home_number;

    @FXML
    void submit(javafx.scene.input.MouseEvent event) {
        dataStorage.getLoggedInUser().getContactDetails().getAddress().setCountry(this.id_country.getText());
        dataStorage.getLoggedInUser().getContactDetails().getAddress().setCity(this.id_city.getText());
        dataStorage.getLoggedInUser().getContactDetails().getAddress().setPostalCode(this.id_postal_code.getText());
        dataStorage.getLoggedInUser().getContactDetails().getAddress().setStreetName(this.id_street_name.getText());
        dataStorage.getLoggedInUser().getContactDetails().getAddress().setHomeNumber(this.id_home_number.getText());

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));

        } catch (IOException exception){
            exception.printStackTrace();
        }

    }
}
