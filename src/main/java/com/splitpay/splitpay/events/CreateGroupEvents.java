package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;

public class CreateGroupEvents {
    @FXML
    private Button cancelButton;

    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        System.out.println("Going back");
        SceneController.goToMainPage(event);
    }
}
