package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import models.CategoryEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryEventDetailsController implements Initializable {
    public int getCategoryEvent() {
        return setCategoryEventID;
    }

    public  void setCategoryEvent(int id) {
        this.setCategoryEventID = id;
    }

    private int setCategoryEventID;

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

    }
}
