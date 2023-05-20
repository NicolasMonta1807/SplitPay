package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.controllers.TransactionsController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewGroupEvents implements Initializable {

    @FXML
    private TableView<Member> membersTable;
    @FXML
    private TableColumn<Member, String> memberName;
    @FXML
    private TableColumn<Member, Double> memberDebt;
    @FXML
    private Button newTransactionButton;
    @FXML
    private Button backButton;
    @FXML
    private Button newBillButton;

    private Group selectedGroup = GroupsController.getSelectedGroup();
    private ObservableList<Member> groupMembers = FXCollections.observableArrayList(MembersController.getMembersOfGroup(selectedGroup.getName()));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.memberName.setCellValueFactory(new PropertyValueFactory<Member, String>("username"));
        this.memberDebt.setCellValueFactory(new PropertyValueFactory<Member, Double>("collectiveDebt"));
        membersTable.setItems(groupMembers);
    }

    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        SceneController.goToMainPage(event);
    }

    @FXML
    public void goToCreateBill(ActionEvent event) throws IOException {
        SceneController.goToCreateBill(event);
    }

    @FXML
    public void goToTransaction(ActionEvent event) throws IOException {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            sendAlert("Error", "No ha seleccionado un usuario");
            return;
        }
        if (selectedMember.getUsername().equals(UsersController.getLoggedUser().getUsername())) {
            sendAlert("Error", "No puede tranferir a sí mismo");
            return;
        }
        TransactionsController.setToMember(selectedMember);

        for(Member member: groupMembers) {
            if (member.getUsername().equals(UsersController.getLoggedUser().getUsername())) {
                TransactionsController.setFromMember(member);
            }
        }

        SceneController.goToTransaction(event);
    }

    private void sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Grupo");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
