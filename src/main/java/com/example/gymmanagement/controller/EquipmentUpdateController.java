package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import com.example.gymmanagement.validation.ValidateEquipment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EquipmentUpdateController implements Initializable {
    @FXML
    private TextField equipmentNameField;

    @FXML
    private TextField categoryField;

    @FXML
    private DatePicker purchaseDatePicker;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextArea notesField;

    private List<String> errors = new ArrayList<>();
    private EquipmentRepository equipmentRepository = new EquipmentRepository();
    private final int equipmentId;
    private TableView<Equipment> equipmentTableView;

    // Constructor
    public EquipmentUpdateController(int equipmentId, TableView<Equipment> equipmentTableView) {
        this.equipmentId = equipmentId;
        this.equipmentTableView = equipmentTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBoxes();
        equipmentTableView.refresh();
        showData(equipmentId);
    }

    private void showData(int equipmentId) {
        Equipment equipment = equipmentRepository.getEquipmentById(equipmentId);
        if (equipment == null) {
            showErrorAlert("Equipment Not Found", "The equipment with ID " + equipmentId + " does not exist.");
            return;
        }

        // Populate the fields with equipment data
        equipmentNameField.setText(equipment.getEquipmentName());
        categoryField.setText(equipment.getCategory());
        purchaseDatePicker.setValue(LocalDate.parse(equipment.getPurchaseDate()));
        priceField.setText(equipment.getPrice().toString());
        statusComboBox.setValue(equipment.getStatus());
        notesField.setText(equipment.getNotes());
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

    @FXML
    private void closeForm() {
        // Get the Stage for the update form and close it
        Stage currentStage = (Stage) equipmentNameField.getScene().getWindow();
        currentStage.close();
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Equipment Updated");
        successAlert.setContentText("Update successfully.");
        successAlert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Update method for Equipment Name
    private void updateEquipmentName(int equipmentId) {
        String newEquipmentName = equipmentNameField.getText();
        if (ValidateEquipment.isValidEquipmentName(newEquipmentName)) {
            equipmentRepository.updateEquipmentName(equipmentId, newEquipmentName);
        } else {
            errors.add("ERROR: Invalid equipment name");
        }
    }

    // Update method for Category
    private void updateCategory(int equipmentId) {
        String newCategory = categoryField.getText();
        if (ValidateEquipment.isValidCategory(newCategory)) {
            equipmentRepository.updateCategory(equipmentId, newCategory);
        } else {
            errors.add("ERROR: Invalid category");
        }
    }

    // Update method for Purchase Date
    private void updatePurchaseDate(int equipmentId) {
        String newPurchaseDate = purchaseDatePicker.getValue().toString();
        if (ValidateEquipment.isValidPurchaseDate(newPurchaseDate)) {
            equipmentRepository.updatePurchaseDate(equipmentId, newPurchaseDate);
        } else {
            errors.add("ERROR: Invalid purchase date");
        }
    }

    // Update method for Price
    private void updatePrice(int equipmentId) {
        String newPriceStr = priceField.getText();
        if (ValidateEquipment.isValidPrice(newPriceStr)) {
            BigDecimal newPrice = new BigDecimal(newPriceStr);
            equipmentRepository.updatePrice(equipmentId, newPrice);
        } else {
            errors.add("ERROR: Invalid price");
        }
    }

    // Update method for Status
    private void updateStatus(int equipmentId) {
        String newStatus = statusComboBox.getValue();
        if (ValidateEquipment.isValidStatus(newStatus)) {
            equipmentRepository.updateStatus(equipmentId, newStatus);
        } else {
            errors.add("ERROR: Invalid status");
        }
    }

    // Update method for Notes
    private void updateNotes(int equipmentId) {
        String newNotes = notesField.getText();
        equipmentRepository.updateNotes(equipmentId, newNotes);
    }

    @FXML
    public void saveUpdate() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Update");
        confirmationAlert.setHeaderText("Update Equipment");
        confirmationAlert.setContentText("Do you want to update this equipment?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                updateEquipmentName(equipmentId);
                updateCategory(equipmentId);
                updatePurchaseDate(equipmentId);
                updatePrice(equipmentId);
                updateStatus(equipmentId);
                updateNotes(equipmentId);

                Platform.runLater(() -> {
                    // Update data in TableView
                    ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();
                    List<Equipment> equipmentList = equipmentRepository.getAllEquipment();
                    equipmentData.addAll(equipmentList);
                    equipmentTableView.setItems(equipmentData);
                    equipmentTableView.refresh();
                });

                if (!errors.isEmpty()) {
                    showErrorAlert("Errors", String.join("\n", errors));
                    errors.clear();
                } else {
                    showSuccessAlert();
                }
            }
        });

    }
}
