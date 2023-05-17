package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.TransactionsController;
import com.splitpay.splitpay.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransactionEvents implements Initializable {

    @FXML
    private Label transactionDescription;
    @FXML
    private TextField transactionAmount;
    @FXML
    private Button createTransaction;
    @FXML
    private Button backButton;
    @FXML
    private ToggleGroup transactionMethod;
    @FXML
    private RadioButton totalAmount;
    @FXML
    private RadioButton partialAmount;
    @FXML
    private Label errorLabel;
    @FXML
    private Label currentDebt;

    int debt;
    User fromUser;
    User toUser;

    public void initialize(URL url, ResourceBundle rb) {
        fromUser = TransactionsController.getFromUser();
        toUser = TransactionsController.getToUser();
        transactionDescription.setText("Desde " + fromUser.getUsername() + " hacia " + toUser.getUsername());
        debt = TransactionsController.getDebt(fromUser, toUser);
        currentDebt.setText("Actualmente debe: " + debt);
        transactionAmount.setPromptText(String.valueOf(debt));
    }

    private void handlePartialTransaction() {
        String amount = transactionAmount.getText();
        if (amount.matches("\\d{1,9}")) {
            TransactionsController.setAmount(Integer.parseInt(amount));
        } else {
            errorLabel.setText("No ha ingresado una cantidad valida");
        }
    }

    private void handleTotalTransaction() {
        TransactionsController.setAmount(debt);
    }

    @FXML
    public void createTransaction(ActionEvent event) throws IOException {
        if (partialAmount.isSelected()) {
            handlePartialTransaction();
        }
        handleTotalTransaction();
        if (sendConfirmation()) {
            TransactionsController.performTransaction();
            sendAlert();
            SceneController.goToViewGroup(event, GroupsController.getSelectedGroup().getName());
        }
    }

    private boolean sendConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transacción");
        alert.setHeaderText("Hacia " + toUser.getUsername());
        alert.setContentText("¿Desea transferir " + debt + " a " + toUser.getUsername() + "?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.get() == ButtonType.OK;
    }

    private void sendAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transacción");
        alert.setHeaderText("Transacción realizada");
        alert.setContentText("Ha enviado " + debt + " a " + toUser.getUsername());
        alert.showAndWait();
    }

    @FXML
    public void goToViewGroup(ActionEvent event) throws IOException {
        TransactionsController.setToUser(null);
        TransactionsController.setFromUser(null);
        SceneController.goToViewGroup(event, GroupsController.getSelectedGroup().getName());
    }
}
