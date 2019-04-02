package controllers;

import helpers.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Address;
import models.CategoryEvent;
import models.GeneralCategory;
import models.Localization;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCategoryEventController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private TextArea id_message;

    @FXML
    private ChoiceBox id_choice_category;

    @FXML
    private TextField id_city;

    @FXML
    private TextField id_street_name;

    @FXML
    private TextField id_home_number;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list = new ArrayList<String>();
        for (GeneralCategory gc: dataStorage.getGeneralCategories()) {
            list.add(gc.getTitle());
        }
        ObservableList obList = FXCollections.observableList(list);
        id_choice_category.getItems().clear();
        id_choice_category.setItems(obList);
    }

    @FXML
    void submit(MouseEvent event) throws IOException {
        GeneralCategory generalCategory = new GeneralCategory();
        for (GeneralCategory genCat: dataStorage.getGeneralCategories()) {
            if (genCat.getTitle().equals(this.id_choice_category.getValue())) {
                generalCategory = genCat;
                break;
            }
        }
        CategoryEvent categoryEvent = new CategoryEvent();
        categoryEvent.setTitle(generalCategory.getTitle());
        Address address = new Address();
        address.setCountry("SK");
        address.setCity(this.id_city.getText());
        address.setStreetName(this.id_street_name.getText());
        address.setHomeNumber(this.id_home_number.getText());
        categoryEvent.setAddress(address);
        Localization localization = new Localization();
        localization.setLatitude(47.0001);
        localization.setLongitude(47.002);
        categoryEvent.setLocalization(localization);
        categoryEvent.setMessage(this.id_message.getText());
        generalCategory.addCategoryEvent(categoryEvent);
        dataStorage.updateCategories(generalCategory);
        for (CategoryEvent categoryEvent1 : generalCategory.getCategoryEvents()) {
            System.out.println(categoryEvent1.getTitle());
            System.out.println(categoryEvent1.getAddress().getCity());
            System.out.println(categoryEvent1.getAddress().getStreetName());
            System.out.println(categoryEvent1.getAddress().getHomeNumber());
            System.out.println(categoryEvent1.getMessage());
        }
        System.out.println("--------------");
        for (GeneralCategory generalCategory1 : dataStorage.getGeneralCategories()) {
            for (CategoryEvent categoryEvent1 : generalCategory1.getCategoryEvents()) {
                System.out.println(categoryEvent1.getTitle());
                System.out.println(categoryEvent1.getAddress().getCity());
                System.out.println(categoryEvent1.getAddress().getStreetName());
                System.out.println(categoryEvent1.getAddress().getHomeNumber());
                System.out.println(categoryEvent1.getMessage());
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));
    }
}
