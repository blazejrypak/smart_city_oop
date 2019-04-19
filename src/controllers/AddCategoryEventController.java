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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.*;
import models.users.OfficeUser;

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
        for (GeneralCategory gc : dataStorage.getGeneralCategories()) {
            list.add(gc.getTitle());
        }
        ObservableList obList = FXCollections.observableList(list);
        id_choice_category.getItems().clear();
        id_choice_category.setItems(obList);
    }

    @FXML
    void submit(MouseEvent event) throws IOException {
        GeneralCategory generalCategory = new GeneralCategory();
        for (GeneralCategory genCat : dataStorage.getGeneralCategories()) {
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
        categoryEvent.setState(CategoryEvent.STATES.TO_DO);
        categoryEvent.setUID(dataStorage.getLoggedInUser().getId());
        for (OfficeUser officeUser : dataStorage.getAllUsers(OfficeUser.class, OfficeUser.class.getSimpleName())) {
            categoryEvent.addSubscriber("new_category_event", officeUser);
        }
        generalCategory.addCategoryEvent(categoryEvent);
        categoryEvent.notify("new_category_event", categoryEvent);
        dataStorage.updateCategories(generalCategory);

        Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard/FXMLDocument.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));
    }
}
