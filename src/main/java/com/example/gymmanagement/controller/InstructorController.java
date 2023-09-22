package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.impl.InstructorsServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Pagination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InstructorController implements Initializable {

    private final InstructorRepository instructorRepository = new InstructorRepository();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final StageManager stageManager = new StageManager();
    @FXML
    private Button exportedInstructor_btn;
    @FXML
    private TableColumn<Instructors, Void> action;
    @FXML
    private TableColumn<Members, Integer> stt;
    @FXML
    private TableColumn<Instructors, Integer> instructorId;
    @FXML
    private TableColumn<Instructors, String> fullName;

    @FXML
    private TableColumn<Instructors, String> gender;

    @FXML
    private TableColumn<Instructors, String> hireDate;

    @FXML
    private TableColumn<Instructors, String> specialization;

    @FXML
    private TableColumn<Instructors, Integer> experienceYears;

    @FXML
    private TableColumn<Instructors, Double> baseSalary;

    @FXML
    private TableColumn<Instructors, Double> salary;

    @FXML
    private TableView<Instructors> tableView;
    @FXML
    private TextField searchInstructor;

    private InstructorsService instructorsService = new InstructorsServiceImpl();

    private ObservableList<Instructors> instructorsData = FXCollections.observableArrayList();
    private FilteredList<Instructors> filteredInstructorList;

    private int currentPage = 1;
    private int pageSize = 10;

    private int totalPage = (int) Math.ceil((double)instructorRepository.getTotalInstructor() / pageSize);
    @FXML
    private Pagination pagination;
    @FXML
    private Button dashboard;
    @FXML
    private Button homePage;
    @FXML
    private Button logout;

    @FXML
    private ImageView searchBtn;

    @FXML
    private Label closeSearchBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stt.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1));
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        hireDate.setCellValueFactory(cellData -> {
            Instructors instructor = cellData.getValue();
            String hireDateString = instructor.getHireDate(); // Đổi thành phương thức lấy ngày tháng tương ứng
            return new SimpleStringProperty(hireDateString);
        });
        experienceYears.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        baseSalary.setCellValueFactory(param -> {
            Instructors instructor = param.getValue();
            BigDecimal baseSalaryValue = instructor.getBaseSalary();
            return new SimpleObjectProperty<>(Double.parseDouble(baseSalaryValue.toString()));
        });
        salary.setCellValueFactory(param -> {
            Instructors instructor = param.getValue();
            int instructorId = instructor.getInstructor_id();
            BigDecimal calculatedSalary = instructorsService.calculateSalary(instructorId);
            return new SimpleObjectProperty<>(Double.parseDouble(calculatedSalary.toString()));
        });
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


    private void setupPagination() {
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(currentPage - 1);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            currentPage = newIndex.intValue() + 1;
            loadInstructorsData();
        });
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
        List<Instructors> instructorList = instructorRepository.getInstructorByPage(currentPage, pageSize);
        instructorsData.clear();
        instructorsData.addAll(instructorList);
        tableView.setItems(instructorsData);
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
        action.setCellFactory(column -> new TableCell<Instructors, Void>() {
            private final Button deleteButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/removed.png"))));
            private final Button showDetailButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/refresh.png"))));

            {
                deleteButton.setOnAction(event -> {
                    Instructors member = getTableView().getItems().get(getIndex());
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDeleteAlert.setTitle("Confirm Delete");
                    confirmDeleteAlert.setHeaderText("Are you sure you want to delete this member?");
                    confirmDeleteAlert.setContentText("Member: " + member.getFirst_name() + " " + member.getLast_name());

                    Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        instructorsService.deleteInstructor(member.getInstructor_id());
                        loadInstructorsData();
                    }
                });

                showDetailButton.setOnAction(event -> {
                    Instructors member = getTableView().getItems().get(getIndex());
                    int memberId = member.getInstructor_id();
                    InstructorUpdateFormController memberUpdateFormController = new InstructorUpdateFormController(memberId, tableView);
                    stageManager.loadInstructorUpdateFormDialog(memberUpdateFormController);
                    loadInstructorsData();
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
    void addInstructor(MouseEvent event) {
        InstructorAddFormController addFormController = new InstructorAddFormController(tableView);
        stageManager.loadInstructorAddFormDialog(addFormController);
    }

    public static void exportToExcel(List<Instructors> instructorsList) {
        String[] columns = {"ID", "First Name", "Last Name", "DOB", "Gender", "Email", "Phone", "Address", "Hire Date", "Experience Years"};
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        LocalDate currentDate = LocalDate.now();
        Year currentYear = Year.of(currentDate.getYear());
        fileChooser.setInitialFileName("list_instructor_" + currentYear + ".xlsx");
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return; // User cancelled the save dialog
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Instructors");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowIndex = 1;
            for (Instructors instructor : instructorsList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(instructor.getInstructor_id());
                row.createCell(1).setCellValue(instructor.getFirst_name());
                row.createCell(2).setCellValue(instructor.getLast_name());
                row.createCell(3).setCellValue(instructor.getDob());
                row.createCell(4).setCellValue(instructor.getGender());
                row.createCell(5).setCellValue(instructor.getEmail());
                row.createCell(6).setCellValue(instructor.getPhone_number());
                row.createCell(7).setCellValue(instructor.getAddress());
                row.createCell(8).setCellValue(instructor.getHireDate());
                row.createCell(9).setCellValue(instructor.getExperienceYears());
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
        List<Instructors> instructorsList = instructorsService.getAllInstructors(); // Get data from TableView
        exportToExcel(instructorsList); // Call the exportToExcel() method of ExcelExporter class

    }

    @FXML
    private Button minimizeButton;

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    private void searchInstructors() {
        String searchTerm = searchInstructor.getText().trim();
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
            List<Instructors> searchResults = instructorRepository.getInstructorByName(searchTerm);
            // Hiển thị kết quả lên TableView
            tableView.getItems().clear();
            tableView.getItems().addAll(searchResults);
            searchBtn.setVisible(false);
            pagination.setVisible(false);
            closeSearchBtn.setVisible(true);
        }

    }

    @FXML
    private void closeSearchForm() {
        searchInstructor.clear();
        closeSearchBtn.setVisible(false);
        searchBtn.setVisible(true);
        pagination.setVisible(true);
        loadInstructorsData();
    }
}
