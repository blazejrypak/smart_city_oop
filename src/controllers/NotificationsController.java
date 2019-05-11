package controllers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import helpers.DataStorage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import models.Notification;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationsController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();

    @FXML
    private JFXTreeTableView<Notification> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setPrefWidth(900);
        table.setVisible(true);
        JFXTreeTableColumn<Notification, String> message = new JFXTreeTableColumn("Message");
        message.setPrefWidth(700);
        message.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getMessage()));

        JFXTreeTableColumn<Notification, String> localDate = new JFXTreeTableColumn("Time");
        localDate.setPrefWidth(200);
        localDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getLocalDate().toString()));

        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(dataStorage.getLoggedInUser().getAllNotifications());

        final TreeItem<Notification> root = new RecursiveTreeItem<Notification>(notificationObservableList, RecursiveTreeObject::getChildren);
        table.getColumns().setAll(message, localDate);
        table.setRoot(root);
        table.setShowRoot(false);
    }
}
