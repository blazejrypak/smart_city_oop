package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label alert;

    @FXML
    void login(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/views/account/login.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }

    @FXML
    void signup(MouseEvent event) {
        DataStorage db = DataStorage.getInstance();
        if (db.existUsername(this.username.getText())) {
            alert.setText("Username is already used");
        } else {
            db.registerUser(this.username.getText(), this.email.getText(), this.password.getText());
            alert.setText("");
            try {
                login(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
