package com.splitpay.splitpay;

import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML
    private Label errorLabel;

    private Group selectedGroup = GroupsController.getSelectedGroup();
    private ObservableList<Member> groupMembers = FXCollections.observableArrayList(MembersController.getMembersOfGroup(selectedGroup.getName()));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setting table columns based on selected group's members
        this.memberName.setCellValueFactory(new PropertyValueFactory<Member, String>("userName"));
        this.memberDebt.setCellValueFactory(new PropertyValueFactory<Member, Double>("collectiveDebt"));
        membersTable.setItems(groupMembers);
    }

    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SplitPay - Menú");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToCreateBill(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("createBill.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SplitPay - Menú");
        stage.setScene(scene);
        stage.show();
    }
}
