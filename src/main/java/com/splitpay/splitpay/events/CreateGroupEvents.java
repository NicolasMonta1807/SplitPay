package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateGroupEvents implements Initializable {


    @FXML
    private TableView<User> newGroupTable;
    @FXML
    private TableColumn<User, String> memberName;
    @FXML
    private TableColumn<User, String> memberMail;
    @FXML
    private TextField groupName;
    @FXML
    private ChoiceBox<User> leaderSelection;
    @FXML
    private Button backButton;
    @FXML
    private Button createButton;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<User> userSelection;

    private final ObservableList<User> groupMembers = FXCollections.observableArrayList(new ArrayList<>());
    private final ObservableList<User> allCurrentUsers = FXCollections.observableArrayList(UsersController.getAllCurrentUsers());
    private final User loggedUser = UsersController.getLoggedUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allCurrentUsers.remove(loggedUser);
        groupMembers.add(loggedUser);
        userSelection.setItems(allCurrentUsers);
        newGroupTable.setItems(groupMembers);
        leaderSelection.setItems(groupMembers);
        memberName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        memberMail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
    }

    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        SceneController.goToMainPage(event);
    }

    private boolean checkValues() {
        if (groupName.getText().isEmpty()) {
            sendAlert("Error", "No ha ingresado un nombre para el grupo");
            return false;
        }

        if(groupMembers.isEmpty() || groupMembers.size() == 1) {
            sendAlert("Error", "No ha agregado miembros al nuevo grupo");
            return false;
        }

        if(leaderSelection.getSelectionModel().getSelectedItem() == null) {
            sendAlert("Error", "No ha asignado el líder del grupo");
            return false;
        }

        return true;
    }

    private void sendAlert(String header, String  content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nuevo Grupo");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void createGroup(ActionEvent event) throws IOException {
        if(checkValues()) {
            String name = groupName.getText();
            User leader = leaderSelection.getSelectionModel().getSelectedItem();
            GroupsController.createGroup(name, leader, groupMembers);
            sendAlert("Grupo creado", "El grupo " + name + " ha sido creado satisfactoriamente");
            SceneController.goToMainPage(event);
        }
    }

    @FXML
    public void addMemberToGroup(ActionEvent event) {
        User userToAdd = userSelection.getSelectionModel().getSelectedItem();
        if(userToAdd == null) {
            sendAlert("Error", "No ha seleccionado un usuario para añadir");
            return;
        }
        userSelection.getSelectionModel().clearSelection();
        allCurrentUsers.remove(userToAdd);
        groupMembers.add(userToAdd);
    }
}
