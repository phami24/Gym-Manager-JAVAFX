package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Equipment;
import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.EquipmentRepository;
import com.example.gymmanagement.model.service.EquipmentService;
import com.example.gymmanagement.model.service.impl.EquipmentServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {

    private final EquipmentRepository equipmentRepository = new EquipmentRepository();
    private final EquipmentService equipmentService = new EquipmentServiceImpl();

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private StageManager stageManager = new StageManager();

    @FXML
    private Button exportedEquipment_btn;
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
    private int currentPage = 1;
    private int pageSize = 10;

    private int totalPage = equipmentRepository.getTotalEquipment() / pageSize;

    @FXML
    private Pagination pagination;


    private ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();
    @FXML
    public void close() {
        stageManager.loadHomeStage();
        stage.close();
    }
    @FXML
    void homepage(MouseEvent event) {
        stageManager.loadHomeStage();
        stage.close();
    }
    @FXML
    void dashboard(MouseEvent event) {
        stageManager.loadDashBoard();
        stage.close();
    }
    @FXML
    void logOut(MouseEvent event) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure you want to log out?");
        confirmationDialog.setContentText("Press OK to log out or Cancel to stay logged in.");

        // Show the dialog and wait for a result
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks OK, proceed with the logout
        if (result == ButtonType.OK) {
            // Close the current stage
            stage.close();

            // Create a new login stage
            Stage loginStage = new Stage();

            // Set the current stage in the stageManager
            stageManager.setCurrentStage(loginStage);

            // Load and display the login stage
            stageManager.loadLoginStage();
        }
    }
    @FXML
    private TextField searchQuipment;
    private FilteredList<Equipment> filteredMembersList;
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

        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(currentPage - 1);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            currentPage = newIndex.intValue() + 1;
            loadEquipmentData();
        });
        loadEquipmentData();


        //search
        equipmentData = FXCollections.observableArrayList(equipmentService.getAllEquipment());

        // Sử dụng FilteredList để lọc danh sách thành viên dựa trên tên
        filteredMembersList = new FilteredList<>(equipmentData, p -> true);

        // Liên kết TableView với FilteredList để hiển thị danh sách đã lọc
        equipment_tableView.setItems(filteredMembersList);

        // Bắt sự kiện khi người dùng nhập tên vào TextField
        searchQuipment.textProperty().addListener((observable, oldValue, newValue) -> {
            // Lọc danh sách thành viên dựa trên tên mới
            filteredMembersList.setPredicate(member -> {
                // Nếu không có tên nào được nhập, hiển thị tất cả các thành viên
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Chuyển đổi tên thành viên và tên mới sang chữ thường để so sánh không phân biệt chữ hoa/thường
                String lowerCaseFilter = newValue.toLowerCase();

                // Kiểm tra nếu tên thành viên chứa tên mới
                if (member.getEquipmentName().toLowerCase().contains(lowerCaseFilter) ) {
                    return true; // Thành viên thỏa mãn điều kiện lọc
                }

                return false; // Không tìm thấy tên trong thành viên
            });
        });
    }

    @FXML
    private void previousPage() {
        if (currentPage > 1) {
            pagination.setCurrentPageIndex(currentPage - 2); // Đặt trang trước đó
            loadEquipmentData();
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPage) {
            pagination.setCurrentPageIndex(currentPage); // Đặt trang tiếp theo
            loadEquipmentData();
        }
    }

    private void loadEquipmentData() {
        List<Equipment> equipmentList = equipmentRepository.getEquipmentByPage(currentPage, pageSize);
        equipmentData.clear();
        equipmentData.addAll(equipmentList);
        equipment_tableView.setItems(equipmentData);
    }

    private void setupActionColumn() {
        action.setCellFactory(column -> new TableCell<Equipment, Void>() {
            private final Button deleteButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/removed.png"))));
            private final Button showDetailButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/refresh.png"))));

            {
                deleteButton.setOnAction(event -> {
                    Equipment member = getTableView().getItems().get(getIndex());
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDeleteAlert.setTitle("Confirm Delete");
                    confirmDeleteAlert.setHeaderText("Are you sure you want to delete this member?");
                    confirmDeleteAlert.setContentText("Member: " + member.getEquipmentName());

                    Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        equipmentService.deleteEquipment(member.getEquipmentId());
                        equipmentData.remove(member);
                        equipment_tableView.refresh();
                    }
                });

                showDetailButton.setOnAction(event -> {
                    Equipment member = getTableView().getItems().get(getIndex());
                    int memberId = member.getEquipmentId();
                    EquipmentUpdateController memberUpdateFormController = new EquipmentUpdateController(memberId, equipment_tableView);
                    stageManager.loadEquipmentUpdateFormDialog(memberUpdateFormController);
                    equipment_tableView.refresh();
                });

                // Thiết lập kích thước cho các nút
                deleteButton.setPrefSize(10, 10);
                showDetailButton.setPrefSize(10, 10);
                ImageView deleteImageView = (ImageView) deleteButton.getGraphic();
                deleteImageView.setFitWidth(20);
                deleteImageView.setFitHeight(20);

                ImageView showDetailImageView = (ImageView) showDetailButton.getGraphic();
                showDetailImageView.setFitWidth(20);
                showDetailImageView.setFitHeight(20);
                deleteButton.getStyleClass().add("transparent-button");
                showDetailButton.getStyleClass().add("transparent-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(deleteButton, showDetailButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    public void addEquipment(MouseEvent event) {
        EquipmentAddFormController addFormController = new EquipmentAddFormController(equipment_tableView);
        stageManager.loadEquipmentAddFormDialog(addFormController);
    }
    public static void exportToExcel(List<Equipment> equipmentList) {
        String[] columns = {"ID", "Equipment Name", "Category", "Purchase date", "Price", "Notes"};
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return; // User cancelled the save dialog
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Equipment");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowIndex = 1;
            for (Equipment equipment : equipmentList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(equipment.getEquipmentId());
                row.createCell(1).setCellValue(equipment.getEquipmentName());
                row.createCell(2).setCellValue(equipment.getCategory());
                row.createCell(3).setCellValue(equipment.getPurchaseDate());
                row.createCell(4).setCellValue(String.valueOf(equipment.getPrice()));
                row.createCell(5).setCellValue(equipment.getNotes());

            }

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }

            System.out.println("Data exported to Excel successfully!");
            Alert informationExporter = new Alert(Alert.AlertType.INFORMATION);
            informationExporter.setTitle("Information exporter");
            informationExporter.setHeaderText("");
            informationExporter.setContentText("Export successful!!");
            informationExporter.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleExportButtonAction() {
        List<Equipment> equipmentList = equipment_tableView.getItems(); // Get data from TableView
        exportToExcel(equipmentList); // Call the exportToExcel() method of ExcelExporter class

    }

}
