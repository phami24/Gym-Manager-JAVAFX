package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.ClassesRepository;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
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
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GymClassController implements Initializable {

    private final ClassesRepository gymClassRepository = new ClassesRepository();
    private final InstructorRepository instructorRepository = new InstructorRepository();
    private final MembershipStatusRepository membershipStatusRepository = new MembershipStatusRepository();
    private final MembershipTypesRepository membershipTypesRepository = new MembershipTypesRepository();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private final StageManager stageManager = new StageManager();

    @FXML
    private TableColumn<Classes, Void> action;

    @FXML
    private TableColumn<Classes, String> className;

    @FXML
    private TableColumn<Classes, Integer> classId;

    @FXML
    private TableView<Classes> class_tableView;

    @FXML
    private TableColumn<Classes, String> instructorId;

    @FXML
    private TableColumn<Classes, String> schedule;

    @FXML
    private TableColumn<Classes, Integer> capacity;

    @FXML
    private TableColumn<Classes, Integer> stt;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button dashboard;
    @FXML
    private Button homePage;
    @FXML
    private Button logout;


    @FXML
    private TextField searchClass;

    private ObservableList<Classes> classesData = FXCollections.observableArrayList();
    private FilteredList<Classes> filteredMembersList;
    private int currentPage = 1;
    private int pageSize = 10;

    int totalPage = (int) Math.ceil((double) gymClassRepository.getTotalClasses() / pageSize);
    @FXML
    private Pagination pagination;

    @FXML
    private ImageView searchBtn;

    @FXML
    private Label closeSearchBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stt.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(class_tableView.getItems().indexOf(cellData.getValue()) + 1));
        classId.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        className.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        instructorId.setCellValueFactory(cellData -> new SimpleStringProperty(instructorRepository.getInstructorNameById(cellData.getValue().getInstructor_id())));
        schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        setupActionColumn();


        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(currentPage - 1);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            currentPage = newIndex.intValue() + 1;
            loadInstructorsData();
        });
        loadInstructorsData();


        Tooltip tooltipH = new Tooltip("Home");
        tooltipH.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        homePage.setTooltip(tooltipH);
        Tooltip tooltipD = new Tooltip("Dashboard");
        tooltipD.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        dashboard.setTooltip(tooltipD);
        Tooltip tooltipL = new Tooltip("Logout");
        tooltipL.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        logout.setTooltip(tooltipL);
    }
    int rowIndex = 0;

    @FXML
    private void previousPage() {
        if (currentPage > 1) {
            pagination.setCurrentPageIndex(currentPage - 2); // Đặt trang trước đó
            loadInstructorsData();
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPage) {
            pagination.setCurrentPageIndex(currentPage); // Đặt trang tiếp theo
            loadInstructorsData();
        }
    }

    private void loadInstructorsData() {
        List<Classes>  classesList = gymClassRepository.getClassByPage(currentPage, pageSize);
        classesData.clear();
        classesData.addAll(classesList);
        class_tableView.setItems(classesData);
    }
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
    private void setupActionColumn() {
        action.setCellFactory(column -> new TableCell<Classes, Void>() {
            private final Button deleteButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/removed.png"))));
            private final Button showDetailButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/refresh.png"))));

            {
                deleteButton.setOnAction(event -> {
                    Classes member = getTableView().getItems().get(getIndex());
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDeleteAlert.setTitle("Confirm Delete");
                    confirmDeleteAlert.setHeaderText("Are you sure you want to delete this member?");
                    confirmDeleteAlert.setContentText("Member: " + member.getClass_name());

                    Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        gymClassRepository.deleteClass(member.getClass_id());
                        classesData.remove(member);
                        class_tableView.refresh();
                    }
                });

                showDetailButton.setOnAction(event -> {
                    Classes member = getTableView().getItems().get(getIndex());
                    int memberId = member.getClass_id();
                    GymClassUpdateController memberUpdateFormController = new GymClassUpdateController(memberId, class_tableView);
                    stageManager.loadClassUpdateFormDialog(memberUpdateFormController);
                    class_tableView.refresh();
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

    @FXML
    void addClass(MouseEvent event) {
        // Create and load the add class form dialog
         GymClassAddFormController addFormController = new GymClassAddFormController(class_tableView);
         stageManager.loadGymClassAddFormDialog(addFormController);
    }
    public static void exportToExcel(List<Classes> classesList) {
        String[] columns = {"ID", "Class Name", "Instructor", "Schedule", "Capacity"};
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));
        LocalDate currentDate = LocalDate.now();
        Year currentYear = Year.of(currentDate.getYear());
        fileChooser.setInitialFileName("list_class_"  + currentYear + ".xlsx");
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return; // User cancelled the save dialog
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Classes");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowIndex = 1;
            for (Classes classes : classesList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(classes.getClass_id());
                row.createCell(1).setCellValue(classes.getClass_name());
                row.createCell(2).setCellValue(classes.getInstructor_id());
                row.createCell(3).setCellValue(classes.getSchedule());
                row.createCell(4).setCellValue(classes.getCapacity());

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
    private void handleExportClassButtonAction() {
        List<Classes> classesList = gymClassRepository.getAllClasses(); // Get data from TableView
        exportToExcel(classesList); // Call the exportToExcel() method of ExcelExporter class

    }
    @FXML
    private Button minimizeButton;
    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    private void searchClass() {
        String searchTerm = searchClass.getText().trim();
        // Kiểm tra xem người dùng đã nhập tên cần tìm kiếm hay chưa
        if (searchTerm.isEmpty()) {
            // Hiển thị thông báo lỗi nếu người dùng chưa nhập tên
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("Please enter member name to search.");
            alert.showAndWait();
        } else {
            // Thực hiện tìm kiếm thành viên trong cơ sở dữ liệu
            List<Classes> searchResults = gymClassRepository.getClassByName(searchTerm);
            // Hiển thị kết quả lên TableView
            class_tableView.getItems().clear();
            class_tableView.getItems().addAll(searchResults);
            searchBtn.setVisible(false);
            pagination.setVisible(false);
            closeSearchBtn.setVisible(true);
        }

    }

    @FXML
    private void closeSearchForm() {
        searchClass.clear();
        closeSearchBtn.setVisible(false);
        searchBtn.setVisible(true);
        pagination.setVisible(true);
        loadInstructorsData();
    }
}
