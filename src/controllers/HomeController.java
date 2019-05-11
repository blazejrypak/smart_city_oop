package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.CategoryEvent;
import models.GeneralCategory;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private Label sent_suggestions;

    @FXML
    private Label resolved_suggestions;

    @FXML
    private Label new_notifications;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int sent = 0;
        int resolved = 0;
        int notifications = dataStorage.getLoggedInUser().getAllNotifications().size();
        for (GeneralCategory generalCategory: dataStorage.getGeneralCategories()) {
            for (CategoryEvent categoryEvent: generalCategory.getCategoryEvents()) {
                sent++;
                if (categoryEvent.getState() == CategoryEvent.STATES.DONE) {
                    resolved++;
                }
            }
        }
        sent_suggestions.setText(String.valueOf(sent));
        resolved_suggestions.setText(String.valueOf(resolved));
        new_notifications.setText(String.valueOf(notifications));
    }
}
