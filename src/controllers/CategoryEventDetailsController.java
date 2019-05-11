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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
        CategoryEventDetailsController.categoryEvent = categoryEvent;
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

    @FXML
    private HBox combo_box_container;

    @FXML
    private HBox submit_container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (categoryEvent != null) {
            id_type.setText("Change suggestion state");
            id_state.setText(categoryEvent.getTitle());
            id_city.setText(categoryEvent.getAddress().getCity());
            id_street_name.setText(categoryEvent.getAddress().getStreetName());
            id_message.setText(categoryEvent.getMessage());
            id_state.setText(categoryEvent.getState().getTitle());
            // Handle state of rectangle background color
            if (categoryEvent.getState() == CategoryEvent.STATES.TO_DO) {
                state_rectangle.setFill(Color.RED);
            } else if (categoryEvent.getState() == CategoryEvent.STATES.IN_PROGRESS) {
                state_rectangle.setFill(Color.YELLOW);
            } else {
                state_rectangle.setFill(Color.GREEN);
            }
            // handle combo box view
            if (categoryEvent.getState() == CategoryEvent.STATES.IN_PROGRESS) {
                combo_box.getItems().add(CategoryEvent.STATES.IN_PROGRESS);
                combo_box.getItems().add(CategoryEvent.STATES.DONE);
            } else if (categoryEvent.getState() == CategoryEvent.STATES.DONE) {
                combo_box_container.getChildren().clear();
                submit_container.getChildren().clear();
            } else {
                combo_box.getItems().addAll(CategoryEvent.STATES.values());
            }
            combo_box.setOnAction(e -> {
                handleChangeIndex();
            });
        }
    }

    private void handleChangeIndex() {
        switch (CategoryEvent.STATES.valueOf(String.valueOf(this.combo_box.getValue()))) {
            case TO_DO:
                state_rectangle.setFill(Color.RED);
                id_state.setText(CategoryEvent.STATES.TO_DO.getTitle());
                break;
            case IN_PROGRESS:
                state_rectangle.setFill(Color.YELLOW);
                id_state.setText(CategoryEvent.STATES.IN_PROGRESS.getTitle());
                break;
            case DONE:
                state_rectangle.setFill(Color.GREEN);
                id_state.setText(CategoryEvent.STATES.DONE.getTitle());
                break;
        }
    }

    @FXML
    private void submit(ActionEvent event) {
        categoryEvent.setState(CategoryEvent.STATES.valueOf(String.valueOf(this.combo_box.getValue())));
        ArrayList<CategoryEvent> categoryEventArrayList = generalCategory.getCategoryEvents();
        for (CategoryEvent e : categoryEventArrayList) { // update event in array list
            if (e.getId() == categoryEvent.getId()) {
                e = categoryEvent;
                break;
            }
        }
        generalCategory.setCategoryEvents(categoryEventArrayList);
        dataStorage.updateCategories(generalCategory);
        categoryEvent.notify("new_state", categoryEvent);
        backToDashboard(event);
    }

    @FXML
    private void backToDashboard(ActionEvent event) {
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
