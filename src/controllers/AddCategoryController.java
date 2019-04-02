package controllers;

import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.GeneralCategory;

import java.io.IOException;

public class AddCategoryController {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private TextField id_title;

    @FXML
    private TextField id_unique_type;

    @FXML
    void submit(MouseEvent event) throws IOException {
        String title, unique_type;

        title = this.id_title.getText();
        unique_type = this.id_unique_type.getText();
        GeneralCategory generalCategory = new GeneralCategory();
        generalCategory.setTitle(title);
        generalCategory.setType(unique_type);
        dataStorage.addCategory(generalCategory);

        Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));
    }
}
