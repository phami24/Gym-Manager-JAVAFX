package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.MembersService;
import com.example.gymmanagement.model.service.impl.InstructorsServiceImpl;
import com.example.gymmanagement.model.service.impl.MemberServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
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

    private InstructorsService instructorsService = new InstructorsServiceImpl();

    private ObservableList<Instructors> instructorsData = FXCollections.observableArrayList();

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

        List<Instructors> instructorsList = instructorRepository.getAllInstructors();
        instructorsData.addAll(instructorsList);
        tableView.setItems(instructorsData);

        //exported to excel
//        exportedInstructor_btn.setOnAction(event -> exportToExcel());

    }

    @FXML
    public void close() {
        javafx.application.Platform.exit();
    }
    @FXML
    void homepage(MouseEvent event) {
        stageManager.loadHomeStage();
        stage.close();
    }

    private void setupActionColumn() {
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Instructors, Void> call(final TableColumn<Instructors, Void> param) {
                return new TableCell<>() {
                    private final ImageView deleteButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/trash-bin.png")));
                    private final ImageView showDetailButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/document.png")));

                    {
                        deleteButton.setOnMouseClicked(event -> {
                            Instructors instructor = getTableView().getItems().get(getIndex());
                            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmDeleteAlert.setTitle("Confirm Delete");
                            confirmDeleteAlert.setHeaderText("Are you sure you want to delete this instructor?");
                            confirmDeleteAlert.setContentText("Instructor: " + instructor.getFirst_name() + " " + instructor.getLast_name());

                            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                instructorRepository.deleteInstructor(instructor.getInstructor_id());
                                instructorsData.remove(instructor);
                                tableView.refresh();
                            }
                        });

                        showDetailButton.setOnMouseClicked(event -> {
                            Instructors instructor = getTableView().getItems().get(getIndex());
                            int instructorId = instructor.getInstructor_id();

                            InstructorUpdateFormController instructorUpdateFormController = new InstructorUpdateFormController(instructorId, tableView);
                            stageManager.loadInstructorUpdateFormDialog(instructorUpdateFormController);
                            tableView.refresh();
                        });

                        deleteButton.setFitWidth(20);
                        deleteButton.setFitHeight(20);
                        Tooltip deleteTooltip = new Tooltip("Delete");
                        Tooltip.install(deleteButton, deleteTooltip);

                        showDetailButton.setFitWidth(20);
                        showDetailButton.setFitHeight(20);
                        Tooltip showDetailTooltip = new Tooltip("Show and Update");
                        Tooltip.install(showDetailButton, showDetailTooltip);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(20);
                            buttons.setAlignment(Pos.CENTER);
                            buttons.getChildren().addAll(deleteButton, showDetailButton);
                            setGraphic(buttons);
                        }
                    }
                };
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files","*.xlsx"));
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
        List<Instructors> instructorsList = tableView.getItems(); // Get data from TableView
        exportToExcel(instructorsList); // Call the exportToExcel() method of ExcelExporter class

    }

}
