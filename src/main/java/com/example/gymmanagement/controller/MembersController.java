package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
import com.example.gymmanagement.model.service.MembersService;
import com.example.gymmanagement.model.service.impl.MemberServiceImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MembersController implements Initializable {

    private final MembersRepository membersRepository = new MembersRepository();
    private final MembershipStatusRepository membershipStatusRepository = new MembershipStatusRepository();
    private final MembershipTypesRepository membershipTypesRepository = new MembershipTypesRepository();
    private final InstructorRepository instructorRepository = new InstructorRepository();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final StageManager stageManager = new StageManager();

    @FXML
    private TableColumn<Members, Void> action;

    @FXML
    private TableColumn<Members, String> email;

    @FXML
    private TableColumn<Members, String> fullName;

    @FXML
    private TableColumn<Members, String> gender;

    @FXML
    private TableColumn<Members, Integer> memberId;

    @FXML
    private TableView<Members> member_tableView;

    @FXML
    private TableColumn<Members, String> phone;

    @FXML
    private TableColumn<Members, String> status;

    @FXML
    private TableColumn<Members, String> type;

    @FXML
    private TableColumn<Members, Integer> stt;
    @FXML
    private TableColumn<Members, String> instructor;
    @FXML
    private Button buttonAdd;

    private MembersService membersService = new MemberServiceImpl();
    private ObservableList<Members> membersData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cài đặt cách dữ liệu của các cột sẽ được lấy từ đối tượng Members và gắn vào TableView
        stt.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(member_tableView.getItems().indexOf(cellData.getValue()) + 1));
        memberId.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        // Hiển thị status, type và instructor dựa trên ID
        status.setCellValueFactory(cellData -> new SimpleStringProperty(membershipStatusRepository.getStatusNameById(cellData.getValue().getMembership_status_id())));
        type.setCellValueFactory(cellData -> new SimpleStringProperty(membershipTypesRepository.getTypeNameById(cellData.getValue().getMembership_type_id())));
        instructor.setCellValueFactory(cellData -> new SimpleStringProperty(instructorRepository.getInstructorNameById(cellData.getValue().getInstructorId())));

        setupActionColumn();

        // Lấy dữ liệu từ cơ sở dữ liệu và đổ vào TableView
        List<Members> membersList = membersRepository.getAllMembers();
        membersData.addAll(membersList);
        member_tableView.setItems(membersData);
    }
    @FXML
    public void close() {
       stage.close();
    }
    @FXML
    void homepage(MouseEvent event) {
        stageManager.loadHomeStage();
    }
    private void setupActionColumn() {
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Members, Void> call(final TableColumn<Members, Void> param) {
                return new TableCell<>() {
                    private final ImageView deleteButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/trash-bin.png")));
                    private final ImageView showDetailButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/document.png")));

                    {
                        // Xử lý sự kiện khi nhấn nút "Delete"
                        deleteButton.setOnMouseClicked(event -> {
                            Members member = getTableView().getItems().get(getIndex());
                            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmDeleteAlert.setTitle("Confirm Delete");
                            confirmDeleteAlert.setHeaderText("Are you sure you want to delete this member?");
                            confirmDeleteAlert.setContentText("Member: " + member.getFirst_name() + " " + member.getLast_name());

                            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                membersService.deleteMember(member.getMember_id());
                                membersData.remove(member);
                                member_tableView.refresh();
                            }
                        });

                        // Xử lý sự kiện khi nhấn nút "Update"
                        showDetailButton.setOnMouseClicked(event -> {

                            Members member = getTableView().getItems().get(getIndex());
                            int memberId = member.getMember_id();
                            MemberUpdateFormController memberUpdateFormController = new MemberUpdateFormController(memberId, member_tableView);
                            stageManager.loadMemberUpdateFormDialog(memberUpdateFormController);
                            member_tableView.refresh();

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

    @FXML
    void addMember(MouseEvent event) {
        MemberAddFormController addFormController = new MemberAddFormController(member_tableView);
        stageManager.loadMemberAddFormDialog(addFormController);
    }

    public static void exportToExcel(List<Members> membersList) {
        String[] columns = {"ID", "First Name", "Last Name", "DOB", "Gender", "Email", "Phone", "Address", "Join date", "End Date"};
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return; // User cancelled the save dialog
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Members");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            int rowIndex = 1;
            for (Members members : membersList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(members.getMember_id());
                row.createCell(1).setCellValue(members.getFirst_name());
                row.createCell(2).setCellValue(members.getLast_name());
                row.createCell(3).setCellValue(members.getDob());
                row.createCell(4).setCellValue(members.getGender());
                row.createCell(5).setCellValue(members.getEmail());
                row.createCell(6).setCellValue(members.getPhone_number());
                row.createCell(7).setCellValue(members.getAddress());
                row.createCell(8).setCellValue(members.getJoin_date());
                row.createCell(9).setCellValue(members.getEnd_date());
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
    private void handleExportMemberButtonAction() {
        List<Members> membersList = member_tableView.getItems(); // Get data from TableView
        exportToExcel(membersList); // Call the exportToExcel() method of ExcelExporter class

    }
}
