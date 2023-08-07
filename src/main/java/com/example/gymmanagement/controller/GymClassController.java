package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.repository.ClassesRepository;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
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
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GymClassController implements Initializable {

    private final ClassesRepository gymClassRepository = new ClassesRepository();
    private final InstructorRepository instructorRepository = new InstructorRepository();
    private final MembershipStatusRepository membershipStatusRepository = new MembershipStatusRepository();
    private final MembershipTypesRepository membershipTypesRepository = new MembershipTypesRepository();

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

    private ObservableList<Classes> classesData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stt.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(class_tableView.getItems().indexOf(cellData.getValue()) + 1));
        classId.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        className.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        instructorId.setCellValueFactory(cellData -> new SimpleStringProperty(instructorRepository.getInstructorNameById(cellData.getValue().getInstructor_id())));
        schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        setupActionColumn();

        List<Classes> classesList = gymClassRepository.getAllClasses();
        classesData.addAll(classesList);
        class_tableView.setItems(classesData);
    }

    private void setupActionColumn() {
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Classes, Void> call(final TableColumn<Classes, Void> param) {
                return new TableCell<>() {
                    private final ImageView deleteButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/trash-bin.png")));
                    private final ImageView showDetailButton = new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/document.png")));

                    {
                        deleteButton.setOnMouseClicked(event -> {
                            Classes gymClass = getTableView().getItems().get(getIndex());
                            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmDeleteAlert.setTitle("Confirm Delete");
                            confirmDeleteAlert.setHeaderText("Are you sure you want to delete this class?");
                            confirmDeleteAlert.setContentText("Class: " + gymClass.getClass_name());

                            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                gymClassRepository.deleteClass(gymClass.getClass_id());
                                classesData.remove(gymClass);
                                class_tableView.refresh();
                            }
                        });

                        showDetailButton.setOnMouseClicked(event -> {
                            Classes gymClass = getTableView().getItems().get(getIndex());
                            int classId = gymClass.getClass_id();
//                             Create an instance of GymClassUpdateFormController with classId
                             GymClassUpdateController classUpdateFormController = new GymClassUpdateController(classId, class_tableView);
                             stageManager.loadClassUpdateFormDialog(classUpdateFormController);
                            class_tableView.refresh();
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
    void addClass(MouseEvent event) {
        // Create and load the add class form dialog
         GymClassAddFormController addFormController = new GymClassAddFormController(class_tableView);
         stageManager.loadGymClassAddFormDialog(addFormController);
    }
}