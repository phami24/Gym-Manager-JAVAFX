package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import com.example.gymmanagement.model.service.EquipmentService;
import com.example.gymmanagement.model.service.impl.EquipmentServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {

    private final EquipmentRepository equipmentRepository = new EquipmentRepository();
    private final EquipmentService equipmentService = new EquipmentServiceImpl();

    private final StageManager stageManager = new StageManager();

    @FXML
    private TableColumn<Equipment, Integer> stt;
    @FXML
    private TableColumn<Equipment, Void> action;

    @FXML
    private TableColumn<Equipment, String> equipmentName;

    @FXML
    private TableColumn<Equipment, String> category;

    @FXML
    private TableColumn<Equipment, String> purchaseDate;

    @FXML
    private TableColumn<Equipment, BigDecimal> price;

    @FXML
    private TableColumn<Equipment, String> status;

    @FXML
    private TableColumn<Equipment, String> notes;

    @FXML
    private TableView<Equipment> equipment_tableView;

    @FXML
    private Button buttonAdd;

    private ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cài đặt cách dữ liệu của các cột sẽ được lấy từ đối tượng Equipment và gắn vào TableView
        stt.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(equipment_tableView.getItems().indexOf(cellData.getValue()) + 1));
        equipmentName.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        purchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        setupActionColumn();

        // Lấy dữ liệu từ cơ sở dữ liệu và đổ vào TableView
        List<Equipment> equipmentList = equipmentRepository.getAllEquipment();
        equipmentData.addAll(equipmentList);
        equipment_tableView.setItems(equipmentData);
    }

    private void setupActionColumn() {
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Equipment, Void> call(final TableColumn<Equipment, Void> param) {
                return new TableCell<>() {
                    private final ImageView deleteButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/trash-bin.png")));
                    private final ImageView showDetailButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/document.png")));

                    {
                        // Xử lý sự kiện khi nhấn nút "Delete"
                        deleteButton.setOnMouseClicked(event -> {
                            Equipment equipment = getTableView().getItems().get(getIndex());
                            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmDeleteAlert.setTitle("Confirm Delete");
                            confirmDeleteAlert.setHeaderText("Are you sure you want to delete this equipment?");
                            confirmDeleteAlert.setContentText("Equipment: " + equipment.getEquipmentName());

                            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                equipmentService.deleteEquipment(equipment.getEquipmentId());
                                equipmentData.remove(equipment);
                                equipment_tableView.refresh();
                            }
                        });

                        // Xử lý sự kiện khi nhấn nút "Update"
                        showDetailButton.setOnMouseClicked(event -> {
                            Equipment equipment = getTableView().getItems().get(getIndex());
                            int equipmentId = equipment.getEquipmentId();
                            EquipmentUpdateController equipmentUpdateFormController = new EquipmentUpdateController(equipmentId, equipment_tableView);
                            stageManager.loadEquipmentUpdateFormDialog(equipmentUpdateFormController);
                            equipment_tableView.refresh();
                        });

                        // Thiết lập chiều rộng và chiều cao cho các hình ảnh
                        deleteButton.setFitWidth(20);
                        deleteButton.setFitHeight(20);
                        Tooltip deleteTooltip = new Tooltip("Delete"); // Tạo Tooltip với nội dung "Delete"
                        Tooltip.install(deleteButton, deleteTooltip); // Gắn Tooltip vào nút deleteButton

                        showDetailButton.setFitWidth(20);
                        showDetailButton.setFitHeight(20);
                        Tooltip showDetailTooltip = new Tooltip("Show and Update"); // Tạo Tooltip với nội dung "Show and Update"
                        Tooltip.install(showDetailButton, showDetailTooltip); // Gắn Tooltip vào nút showDetailButtonImage
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(20); // Tạo HBox với khoảng cách 10 giữa các nút
                            buttons.setAlignment(Pos.CENTER); // Căn giữa các nút trong HBox
                            buttons.getChildren().addAll(deleteButton, showDetailButton);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });
    }

    public void addEquipment(MouseEvent event) {
        EquipmentAddFormController addFormController = new EquipmentAddFormController(equipment_tableView);
        stageManager.loadEquipmentAddFormDialog(addFormController);
    }
}
