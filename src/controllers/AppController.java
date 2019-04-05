package controllers;

import helpers.DataStorage;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppController extends Application {
    private DataStorage dataStorage = DataStorage.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/account/login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    private <T extends Event> void closeWindowEvent(T t) {
        System.out.println("Saving... data");
        dataStorage.saveData();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
