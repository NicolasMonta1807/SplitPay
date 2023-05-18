package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class LoginEvents {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField userPhone;
    @FXML
    private Button loginButton;

    private boolean checkLoginInfo() {
        return !userName.getText().isEmpty()
                && !userEmail.getText().isEmpty()
                && !userPhone.getText().isEmpty();
    }

    private boolean sendConfirmation(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inicio de Sesión");
        alert.setHeaderText("Usuario " + userName.getText());
        alert.setContentText(content);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.get() == ButtonType.OK;
    }

    private void sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio de Sesión");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        if (!checkLoginInfo()) {
            sendAlert("Error","Todos los campos son obligatorios");
            return;
        }

        User userToLog = new User(userName.getText(), userEmail.getText(), userPhone.getText());
        if (UsersController.checkExistingUser(userToLog)) {
            if (sendConfirmation("¿Desea iniciar sesión?")) {
                UsersController.setLoggedUser(userToLog);
            }
        } else {
            if (sendConfirmation("Su usuario aún no existe. ¿Desea crearlo?")) {
                UsersController.createAndSetLoggedUser(userToLog);
            }
        }
        SceneController.goToMainPage(event);
    }
}
