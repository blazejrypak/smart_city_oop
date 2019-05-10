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
import models.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ListOfAllUsersController implements Initializable {

    @FXML
    private JFXTreeTableView<User> table;

    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private ComboBox<String> id_combo_options;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<String> list = new HashSet<String>();
        for (User user: dataStorage.getAllUsers(User.class, User.class.getSimpleName())) {
            list.add(user.getRole());
        }
        List<String> options = new ArrayList<>();
        options.addAll(list);
        ObservableList obList = FXCollections.observableList(options);
        id_combo_options.getItems().clear();
        id_combo_options.setItems(obList);
        table.setVisible(false);
    }


    @FXML
    private void showUser(ActionEvent actionEvent) {
        table.setPrefWidth(900);
        table.setVisible(true);
        if (dataStorage.getAllUsers(User.class, User.class.getSimpleName()) != null) {

            JFXTreeTableColumn<User,String> username = new JFXTreeTableColumn("Username");
            username.setPrefWidth(600);
            username.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getUsername()));

            JFXTreeTableColumn<User,String> role = new JFXTreeTableColumn("Role");
            role.setPrefWidth(300);
            role.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getRole()));

            ObservableList<User> userObservableList = FXCollections.observableArrayList();
            userObservableList.addAll(dataStorage.getAllUsers(User.class, id_combo_options.getValue()));

            final TreeItem<User> root = new RecursiveTreeItem<User>(userObservableList, RecursiveTreeObject::getChildren);
            table.getColumns().setAll(username, role);
            table.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleClickEvent(table.getSelectionModel().getSelectedItem().getValue(), event);
                }
            });
            table.setRoot(root);
            table.setShowRoot(false);
        }
    }

    public void handleClickEvent(User user, MouseEvent event){
        UserSettingDetail(user.getId(), event);
    }

    private void UserSettingDetail(int ID, MouseEvent event) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard/UserSettingsDetails.fxml"));
            AnchorPane anchorPane = loader.load();
            UserSettingsDetailsController userSettingsDetailsController = loader.getController();
            userSettingsDetailsController.setUID(ID);
            root = FXMLLoader.load(getClass().getResource("/views/dashboard/UserSettingsDetails.fxml"));
            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}