package com.splitpay.splitpay;

import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
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
    @FXML
    private Label errorLabel;


    private boolean checkLoginInfo() {
        return !userName.getText().isEmpty()
                && !userEmail.getText().isEmpty()
                && !userPhone.getText().isEmpty();
    }

    private boolean sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inicio de Sesión");
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.get() == ButtonType.OK;
    }

    private void goToMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SplitPay - Menú");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        if (!checkLoginInfo()) {
            errorLabel.setText("No ha ingresado todos los campos");
            return;
        }
        User userToLog = new User(userName.getText(), userEmail.getText(), userPhone.getText());
        if (UsersController.checkExistingUser(userToLog)) {
            if (sendAlert("Usuario " + userToLog.getUsername(), "¿Desea iniciar sesión?")) {
                UsersController.setLoggedUser(userToLog);
                goToMainPage(event);
            }
        } else {
            if (sendAlert("Usuario " + userToLog.getUsername(), "Su usuario aún no existe. ¿Desea crearlo?")) {
                UsersController.createAndSetLoggedUser(userToLog);
                goToMainPage(event);
            }
        }

    }
}
