package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.controllers.TransactionsController;
import com.splitpay.splitpay.entities.Member;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Label currentDebt;

    int totalDebt;
    int currentAmount;
    Member fromMember;
    Member toMember;

    public void initialize(URL url, ResourceBundle rb) {
        fromMember = TransactionsController.getFromMember();
        toMember = TransactionsController.getToMember();
        transactionDescription.setText("Desde " + fromMember.getUsername() + " hacia " + toMember.getUsername());
        try {
            totalDebt = TransactionsController.getDebt(fromMember, toMember);
        } catch (SQLException e) {
            sendAlert("Error", "No se pudo obtener la deuda");
            Platform.exit();
        }
        currentDebt.setText("Actualmente debe: " + totalDebt);
        transactionAmount.setPromptText(String.valueOf(totalDebt));
    }

    private void handlePartialTransaction() {
        TransactionsController.setAmount(currentAmount);
    }

    private boolean checkValue() {
        String amount = transactionAmount.getText();
        if (amount.matches("\\d{1,9}")) {
            return true;
        } else {
            sendAlert("Error", "No ha ingresado una cantidad válida");
            return false;
        }
    }

    private void handleTotalTransaction() {
        currentAmount = totalDebt;
        TransactionsController.setAmount(totalDebt);
    }

    @FXML
    public void createTransaction(ActionEvent event) throws IOException {
        if (partialAmount.isSelected()) {
            if (checkValue()) {
                currentAmount = Integer.parseInt(transactionAmount.getText());
                handlePartialTransaction();
            } else {
                return;
            }
        } else {
            handleTotalTransaction();
        }
        if (sendConfirmation()) {
            try {
                TransactionsController.performTransaction();
            } catch (SQLException e) {
                sendAlert("Error", "No se pudo realizar la transaccíon");
                return;
            }
            sendAlert("Transacción realizada", "Ha enviado " + currentAmount + " a " + toMember.getUsername());
            try {
                MembersController.reloadMembers();
            } catch (SQLException e) {
                sendAlert("Error", "No se puede conectar con la base de datos");
                return;
            }
            SceneController.goToViewGroup(event, GroupsController.getSelectedGroup().getName());
        }
    }

    private boolean sendConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transacción");
        alert.setHeaderText("Hacia " + toMember.getUsername());
        alert.setContentText("¿Desea transferir " + currentAmount + " a " + toMember.getUsername() + "?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.get() == ButtonType.OK;
    }

    private void sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transacción");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void goToViewGroup(ActionEvent event) throws IOException {
        TransactionsController.setToMember(null);
        TransactionsController.setFromMember(null);
        SceneController.goToViewGroup(event, GroupsController.getSelectedGroup().getName());
    }
}
