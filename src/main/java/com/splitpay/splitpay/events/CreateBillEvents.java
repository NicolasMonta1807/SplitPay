package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.BillsController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateBillEvents implements Initializable {
    @FXML
    private TableView<Debt> membersTable;
    @FXML
    private TableColumn<Debt, Integer> debtCost;
    @FXML
    private TableColumn<Debt, String> debtUserName;
    @FXML
    private TableColumn<Debt, String> debtUserMail;
    @FXML
    private TextField costInsert;
    @FXML
    private Button removeMember;
    @FXML
    private ComboBox<Member> availableMembers;
    @FXML
    private Button addMember;
    @FXML
    private Button createButton;
    @FXML
    private Button backButton;

    private Optional<String> billName;
    private final User loggedUser = UsersController.getLoggedUser();
    private final Group selectedGroup = GroupsController.getSelectedGroup();
    private final ObservableList<Member> membersOfGroup = FXCollections.observableArrayList(MembersController.getMembersOfGroup(GroupsController.getSelectedGroup().getName()));
    private final ObservableList<Debt> debtsOfBill = FXCollections.observableArrayList(new ArrayList<>());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        membersOfGroup.removeIf(m -> (m.getUsername().equals(loggedUser.getUsername())));
        availableMembers.setItems(membersOfGroup);
        debtCost.setCellValueFactory(new PropertyValueFactory<Debt, Integer>("debtCost"));
        debtUserName.setCellValueFactory(new PropertyValueFactory<Debt, String>("owingName"));
        debtUserMail.setCellValueFactory(new PropertyValueFactory<Debt, String>("owingEmail"));
        membersTable.setItems(debtsOfBill);
    }

    private void promptBillName() {
        TextInputDialog prompt = new TextInputDialog("Ingrese el nombre de la factura: ");
        prompt.setHeaderText("Factura");
        prompt.setContentText("Ingrese el nombre de la factura: ");
        billName = prompt.showAndWait();
    }

    @FXML
    public void removeMemberFromBill(ActionEvent event) {
        Debt selectedDebt = membersTable.getSelectionModel().getSelectedItem();
        if (selectedDebt == null) {
            sendAlert("Error", "No ha seleccionado una deuda para eliminar");
            return;
        }
        debtsOfBill.remove(selectedDebt);
        membersOfGroup.add(selectedDebt.getOwing());
    }

    private Member getMemberToAdd() {
        return availableMembers.getSelectionModel().getSelectedItem();
    }

    private boolean checkAddValues() {
        if (costInsert.getText().isEmpty()) {
            sendAlert("Error", "No ingresó un costo válido");
            return false;
        }

        if (!(costInsert.getText().strip().matches("\\d{1,9}"))) {
            sendAlert("Error", "No ingresó un costo válido");
            return false;
        }

        if (getMemberToAdd() == null) {
            sendAlert("Error", "No ha seleccionado un usuario");
            return false;
        }

        return true;
    }

    @FXML
    public void addMemberToBill(ActionEvent event) {
        if (checkAddValues()) {
            Member selectedMember = getMemberToAdd();
            debtsOfBill.add(new Debt(selectedMember, new Member(loggedUser, selectedGroup), Integer.parseInt(costInsert.getText())));
            membersOfGroup.removeIf(m -> (m.getUsername().equals(selectedMember.getUsername())));
            availableMembers.getSelectionModel().clearSelection();
            costInsert.setText("");
        }
    }

    @FXML
    public void createBill(ActionEvent event) throws IOException {

        if(debtsOfBill.isEmpty()) {
            sendAlert("Error", "No ha añadido deudas a la factura");
            return;
        }

        if(sendConfirmation("¿Está seguro de crear la factura?")) {
            promptBillName();
            if(billName.isEmpty()) {
                return;
            }
            BillsController.createBill(new Bill(billName.get(), debtsOfBill, new Date()));
            sendAlert("Factura " + billName.get(), "Factura creada exitosamente");
            SceneController.goToMainPage(event);
        }
    }

    private boolean sendConfirmation(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Factura");
        alert.setHeaderText("Creación de factura para " + selectedGroup.getName());
        alert.setContentText(content);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.get() == ButtonType.OK;
    }

    private void sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Factura");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        SceneController.goToMainPage(event);
    }
}
