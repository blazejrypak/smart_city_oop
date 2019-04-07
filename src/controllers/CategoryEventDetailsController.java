package controllers;

import helpers.DataStorage;
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
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Category;
import models.CategoryEvent;
import models.GeneralCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryEventDetailsController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    private static CategoryEvent categoryEvent;


    public GeneralCategory getGeneralCategory() {
        return generalCategory;
    }

    public void setGeneralCategory(GeneralCategory generalCategory) {
        CategoryEventDetailsController.generalCategory = generalCategory;
    }

    private static GeneralCategory generalCategory;

    public void setCategoryEvent(CategoryEvent categoryEvent) {
        this.categoryEvent = categoryEvent;
    }

    @FXML
    private Label id_type;

    @FXML
    private Label id_city;

    @FXML
    private Label id_street_name;

    @FXML
    private TextArea id_message;

    @FXML
    private Label id_state;

    @FXML
    private ComboBox combo_box;

    @FXML
    private Button id_submit;

    @FXML
    private Rectangle state_rectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (categoryEvent != null) {
            id_type.setText(categoryEvent.getTitle());
            id_city.setText(categoryEvent.getAddress().getCity());
            id_street_name.setText(categoryEvent.getAddress().getStreetName());
            id_message.setText(categoryEvent.getMessage());
            id_state.setText(categoryEvent.getState().toString());
            if (categoryEvent.getState() == CategoryEvent.STATES.TO_DO) {
                state_rectangle.setStyle("-fx-background-color: #FF3100;");
            } else if (categoryEvent.getState() == CategoryEvent.STATES.IN_PROGRESS) {
                state_rectangle.setStyle("-fx-background-color: #FFEB00;");
            } else {
                state_rectangle.setStyle("-fx-background-color: #5AFF12;");
            }
            combo_box.getItems().addAll(CategoryEvent.STATES.values());
        }
    }

    @FXML
    private void submit(ActionEvent event) {
        categoryEvent.setState(CategoryEvent.STATES.valueOf(String.valueOf(this.combo_box.getValue())));
        for (CategoryEvent e : this.getGeneralCategory().getCategoryEvents()) {
            if (e.getId() == categoryEvent.getId()) {
                e = categoryEvent;
                break;
            }
        }
        dataStorage.updateCategories(this.getGeneralCategory());

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
