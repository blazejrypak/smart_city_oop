package controllers.authentification;

import models.authentification.Login;
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

public class LoginController implements Initializable {
    private Login login = new Login();
    private DataStorage dataStorage = DataStorage.getInstance();


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label alert;

    @FXML
    void login(MouseEvent event) throws IOException {
        String username, password;

        username = this.username.getText();
        password = this.password.getText();
        login.login(username, password);

        if (dataStorage.getLoggedInUser() != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } else {
            System.out.println("Wrong username or password");
        }
    }

    @FXML
    void signup(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/account/signup.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}