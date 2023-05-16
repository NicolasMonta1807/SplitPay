package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageEvents implements Initializable {
    @FXML
    private TableView<Member> groupTable;
    @FXML
    private TableColumn<Member, String> groupName;
    @FXML
    private TableColumn<Member, Integer> groupPersonalDebt;
    @FXML
    private Button groupViewButton;
    @FXML
    private Button newGroupButton;
    @FXML
    private Button newBillButton;
    @FXML
    private Label errorLabel;

    private User loggedUser = UsersController.getLoggedUser();
    private ObservableList<Member> userGroups = FXCollections.observableArrayList(MembersController.getGroupsOfUser(loggedUser.getUsername()));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setting table columns values based on current members
        this.groupName.setCellValueFactory(new PropertyValueFactory<Member, String>("groupName"));
        this.groupPersonalDebt.setCellValueFactory(new PropertyValueFactory<Member, Integer>("collectiveDebt"));
        groupTable.setItems(userGroups);
    }

    @FXML
    public void handleViewGroup(ActionEvent event) throws IOException {
        Member selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup == null) {
            errorLabel.setText("No ha seleccionado grupos");
        } else {
            GroupsController.setSelectedGroup(selectedGroup.getGroupName());
            SceneController.goToViewGroup(event, selectedGroup.getGroupName());
        }
    }

    @FXML
    public void goToCreateGroup(ActionEvent event) throws IOException {
        SceneController.goToCreateGroup(event);
    }

    @FXML
    public void goToCreateBill(ActionEvent event) throws IOException {
        SceneController.goToCreateBill(event);
    }
}
