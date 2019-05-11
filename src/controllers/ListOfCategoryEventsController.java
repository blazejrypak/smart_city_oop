package controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helpers.DataStorage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.CategoryEvent;
import models.GeneralCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListOfCategoryEventsController implements Initializable {

    @FXML
    private JFXTreeTableView<CategoryEvent> table;

    private DataStorage dataStorage = DataStorage.getInstance();
    private GeneralCategory currentGeneralCategory;

    @FXML
    private ComboBox<String> id_combo_category;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list = new ArrayList<String>();
        for (GeneralCategory gc : dataStorage.getGeneralCategories()) {
            list.add(gc.getTitle());
        }
        ObservableList obList = FXCollections.observableList(list);
        id_combo_category.getItems().clear();
        id_combo_category.setItems(obList);
        table.setVisible(false);
    }

    private GeneralCategory getCategory(String title) {
        for (GeneralCategory generalCategory : dataStorage.getGeneralCategories()) {
            if (generalCategory.getTitle().equals(title)) {
                this.currentGeneralCategory = generalCategory;
                return generalCategory;
            }
        }
        return null;
    }

    @FXML
    private void showCategory(ActionEvent actionEvent) {
        table.setPrefWidth(900);
        table.setVisible(true);
        if (getCategory(this.id_combo_category.getValue()) != null && getCategory(this.id_combo_category.getValue()).getCategoryEvents() != null) {

            JFXTreeTableColumn<CategoryEvent, String> title = new JFXTreeTableColumn("Type of suggestion");
            title.setPrefWidth(200);
            title.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getTitle()));

            JFXTreeTableColumn<CategoryEvent, String> address = new JFXTreeTableColumn("Address");
            address.setPrefWidth(300);
            address.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getAddress().getStreetName()));

            JFXTreeTableColumn<CategoryEvent, String> message = new JFXTreeTableColumn("Message");
            message.setPrefWidth(500);
            message.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getMessage()));

            JFXTreeTableColumn<CategoryEvent, String> state = new JFXTreeTableColumn("State");
            state.setPrefWidth(100);
            state.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getState().getTitle()));

            ObservableList<CategoryEvent> category_event_list = FXCollections.observableArrayList();
            category_event_list.addAll(getCategory(this.id_combo_category.getValue()).getCategoryEvents());

            final TreeItem<CategoryEvent> root = new RecursiveTreeItem<CategoryEvent>(category_event_list, RecursiveTreeObject::getChildren);
            table.getColumns().setAll(title, address, message, state);
            table.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleClickEvent(currentGeneralCategory, table.getSelectionModel().getSelectedItem().getValue(), event);
                }
            });
            table.setRoot(root);
            table.setShowRoot(false);
        }
    }

    public void handleClickEvent(GeneralCategory generalCategory, CategoryEvent categoryEvent, MouseEvent event) {
        CategoryEventDetails(categoryEvent.getId(), event);
    }

    private void CategoryEventDetails(int ID, MouseEvent event) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard/CategoryEventDetails.fxml"));
            AnchorPane anchorPane = loader.load();
            CategoryEventDetailsController categoryEventDetailsController = loader.getController();
            categoryEventDetailsController.setGeneralCategory(this.currentGeneralCategory);
            categoryEventDetailsController.setCategoryEvent(this.currentGeneralCategory.getCategoryEventById(ID));
            root = FXMLLoader.load(getClass().getResource("/views/dashboard/CategoryEventDetails.fxml"));
            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}