package com.splitpay.splitpay.events;

import com.splitpay.splitpay.SceneController;
import com.splitpay.splitpay.controllers.BillsController;
import com.splitpay.splitpay.controllers.MembersController;
import com.splitpay.splitpay.controllers.UsersController;
import com.splitpay.splitpay.entities.Member;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ReportEvents implements Initializable {

    @FXML
    private TableView<Map.Entry<String, Map<String, Integer>>> reportTable;
    @FXML
    private Button backButton;

    private List<Member> groupsOfUser;
    Map<String, Map<String, Integer>> map;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupsOfUser = MembersController.getGroupsOfUser(UsersController.getLoggedUser().getUsername());

        try {
            map = BillsController.getReport();
        } catch (SQLException e) {
            sendAlert("Error", "No se pudo conectar con la bases de datos");
            Platform.exit();
        }

        if(map == null) {
            sendAlert("Error", "Aún no hay facturas");
            return;
        }

        TableColumn<Map.Entry<String, Map<String, Integer>>, String> billDate = new TableColumn("Fecha de la factura");

        billDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        reportTable.getColumns().add(billDate);


        for (Member m : groupsOfUser) {
            TableColumn<Map.Entry<String, Map<String, Integer>>, String> groupColumn = new TableColumn(m.getGroupName());
            groupColumn.setCellValueFactory(cellData -> {
                Integer value = cellData.getValue().getValue().get(m.getGroupName());
                if (value == null) {
                    return new SimpleStringProperty("0");
                } else {
                    return new SimpleStringProperty(String.valueOf(value));
                }
            });
            reportTable.getColumns().add(groupColumn);
        }

        TableColumn<Map.Entry<String, Map<String, Integer>>, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().get("total"))));
        reportTable.getColumns().add(totalColumn);

        ObservableList<Map.Entry<String, Map<String, Integer>>> items = FXCollections.observableArrayList(map.entrySet());
        reportTable.setItems(items);
    }

    private void sendAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio de Sesión");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void goToMainPage(ActionEvent event) throws IOException {
        SceneController.goToMainPage(event);
    }
}
