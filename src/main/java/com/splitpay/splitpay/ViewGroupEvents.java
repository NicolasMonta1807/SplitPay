package com.splitpay.splitpay;

import com.splitpay.splitpay.controllers.GroupsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewGroupEvents implements Initializable {

    @FXML
    private TableView<Member> membersTable;
    @FXML
    private TableColumn<Member, String> memberName;
    @FXML
    private TableColumn<Member, Double> memberDebt;

    private Group selectedGroup = GroupsController.getSelectedGroup();
    private ObservableList<Member> groupMembers = FXCollections.observableArrayList(MembersController.getMembersOfGroup(selectedGroup.getName()));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setting table columns based on selected group's members
        this.memberName.setCellValueFactory(new PropertyValueFactory<Member, String>("userName"));
        this.memberDebt.setCellValueFactory(new PropertyValueFactory<Member, Double>("collectiveDebt"));
        membersTable.setItems(groupMembers);
    }
}
