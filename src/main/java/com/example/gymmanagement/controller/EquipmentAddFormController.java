package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.gymmanagement.validation.ValidateEquipment;

public class EquipmentAddFormController implements Initializable {

    @FXML
    private TextField equipmentNameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField purchaseDateField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField notesField;

    @FXML
    private Button buttonAdd;

    private EquipmentRepository equipmentRepository = new EquipmentRepository();
    private TableView<Equipment> equipmentTableView;

    // Constructor
    public EquipmentAddFormController(TableView<Equipment> equipmentTableView) {
        this.equipmentTableView = equipmentTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBoxes();
        buttonAdd.setOnAction(event -> {
            validateAndAddEquipment();
        });
    }

    // Xóa dữ liệu khỏi các trường nhập liệu
    private void clearFields() {
        equipmentNameField.clear();
        categoryField.clear();
        purchaseDateField.clear();
        priceField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        notesField.clear();
    }

    private void populateComboBoxes() {
        // Populate Status ComboBox
        // Replace this with your actual status retrieval logic
        List<String> statuses = new ArrayList<>();
        statuses.add("Available");
        statuses.add("In Use");
        statuses.add("Under Maintenance");
        statusComboBox.setItems(FXCollections.observableArrayList(statuses));
    }

    private void addEquipment() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Add");
        confirmationAlert.setHeaderText("Add Equipment");
        confirmationAlert.setContentText("Do you want to add this equipment?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String equipmentName = equipmentNameField.getText();
                String category = categoryField.getText();
                String purchaseDate = purchaseDateField.getText();
                BigDecimal price = new BigDecimal(priceField.getText());
                String status = statusComboBox.getValue();
                String notes = notesField.getText();

                Equipment newEquipment = new Equipment();
                newEquipment.setEquipmentName(equipmentName);
                newEquipment.setCategory(category);
                newEquipment.setPurchaseDate(purchaseDate);
                newEquipment.setPrice(price);
                newEquipment.setStatus(status);
                newEquipment.setNotes(notes);

                equipmentRepository.addEquipment(newEquipment);

                Platform.runLater(() -> {
                    ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();
                    List<Equipment> equipmentList = equipmentRepository.getAllEquipment();
                    equipmentData.addAll(equipmentList);
                    equipmentTableView.setItems(equipmentData);
                    equipmentTableView.refresh();
                });

                showSuccessAlert();
                clearFields();
            }
        });
    }

    private boolean validateAndAddEquipment() {
        List<String> errors = new ArrayList<>();

        String equipmentName = equipmentNameField.getText();
        String category = categoryField.getText();
        String purchaseDate = purchaseDateField.getText();
        String priceStr = priceField.getText();
        String status = statusComboBox.getValue();
        String notes = notesField.getText();

        if (!ValidateEquipment.isValidEquipmentName(equipmentName)) {
            errors.add("Equipment Name is required.");
        }

        if (!ValidateEquipment.isValidCategory(category)) {
            errors.add("Category is required.");
        }

        if (!ValidateEquipment.isValidPurchaseDate(purchaseDate)) {
            errors.add("Invalid Purchase Date.");
        }

        if (!ValidateEquipment.isValidPrice(priceStr)) {
            errors.add("Invalid Price.");
        }

        if (!ValidateEquipment.isValidStatus(status)) {
            errors.add("Status is required.");
        }

        addEquipment();
        return true;
    }


    private void showSuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Equipment Added");
        successAlert.setContentText("Equipment has been added successfully.");
        successAlert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void close(MouseEvent event) {
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
