package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {

    private final EquipmentRepository equipmentRepository = new EquipmentRepository();

    @FXML
    private TableColumn<Equipment, Integer> equipment_id_col;

    @FXML
    private TableColumn<Equipment, String> equipment_name_col;

    @FXML
    private TableColumn<Equipment, String> category_col;

    @FXML
    private TableColumn<Equipment, BigDecimal> price_col;

    @FXML
    private TableColumn<Equipment, String> purchase_col;

    @FXML
    private TableColumn<Equipment, String> status_col;

    @FXML
    private TableColumn<Equipment, Void> action_col;

    @FXML
    private TableView<Equipment> equipment_tableView;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView searchImage;

    private ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("hello world");
        equipment_id_col.setCellValueFactory(new PropertyValueFactory<>("equipmentId"));
        equipment_name_col.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        purchase_col.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupActionColumn();

        List<Equipment> equipmentList = equipmentRepository.getAllEquipment();
        equipmentData.addAll(equipmentList);
        equipment_tableView.setItems(equipmentData);
    }

    private void setupActionColumn() {
        action_col.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Equipment selecttedEquipment = getTableView().getItems().get(getIndex());
                    // Handle edit action here
                });

                deleteButton.setOnAction(event -> {
                    Equipment selecttedEquipment = getTableView().getItems().get(getIndex());
                    equipmentRepository.deleteEquipment(selecttedEquipment.getEquipmentId());
                    equipmentData.remove(selecttedEquipment);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(editButton, deleteButton));
                }
            }
        });
    }
}
