package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import helpers.DataStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.CategoryEvent;
import models.GeneralCategory;

import java.util.ArrayList;
import java.util.List;

public class EventsController extends ListOfCategoryEventsController implements Initializable {
    private DataStorage dataStorage = DataStorage.getInstance();
    @FXML
    private StackPane stackPane;

    @Override
    public void handleClickEvent(GeneralCategory generalCategory, CategoryEvent categoryEvent, MouseEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Do you want follow this event?"));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        List<JFXButton> jfxButtonList = new ArrayList<>();
        JFXButton yes_button = new JFXButton("Yes");
        yes_button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        yes_button.setPrefHeight(32);
        yes_button.setStyle("-fx-background-color: #505AFF");
        yes_button.setOnAction(event1 -> {
            dialog.close();
            generalCategory.getCategoryEventById(categoryEvent.getId()).addSubscriber(dataStorage.getLoggedInUser().getId(), "new_state", dataStorage.getLoggedInUser());
        });

        JFXButton cancel_button = new JFXButton("Cancel");
        cancel_button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        cancel_button.setPrefHeight(32);
        cancel_button.setStyle("-fx-background-color: #ffe6f0");
        cancel_button.setOnAction(event1 -> {
            dialog.close();
        });
        jfxButtonList.add(yes_button);
        jfxButtonList.add(cancel_button);
        content.setActions(jfxButtonList);
        dialog.show();
    }
}
