package com.splitpay.splitpay;

import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    // TODO: Get loggedUser from LoginController and load its Member info
    private User loggedUser = new User("Nikoresu", "n_montanez@javeriana.edu.co", 0);
    private ObservableList<Member> userGroups = FXCollections.observableArrayList(MembersController.getGroupsOfUser(loggedUser.getUsername()));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setting table columns values based on current members
        this.groupName.setCellValueFactory(new PropertyValueFactory<Member, String>("groupName"));
        this.groupPersonalDebt.setCellValueFactory(new PropertyValueFactory<Member, Integer>("collectiveDebt"));
        groupTable.setItems(userGroups);
    }

    @FXML
    public void goToViewGroup(Event event) throws IOException {
        // Setting GroupsController selected group for changing scene
        Member selectedGroup = groupTable.getSelectionModel().getSelectedItem();
        if (selectedGroup == null) {
            errorLabel.setText("No ha seleccionado grupos");
        } else {
            // Changing scene to ViewGroup
            GroupsController.setSelectedGroup(selectedGroup.getGroupName());
            Parent root = FXMLLoader.load(getClass().getResource("viewGroup.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("SplitPay - Grupo " + selectedGroup.getGroupName());
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goToCreateGroup(ActionEvent event) throws IOException {
        // Changing scene to CreateGroup
        Parent root = FXMLLoader.load(getClass().getResource("createGroup.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SplitPay - Crear Grupo");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToCreateBill(Event event) throws IOException {
        // Changing scene to CreateBill
        Parent root = FXMLLoader.load(getClass().getResource("createBill.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SplitPay - Crear Factura");
        stage.setScene(scene);
        stage.show();
    }
}
