package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.repository.ClassesRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {

    private final ClassesRepository classesRepository = new ClassesRepository();

    @FXML
    private TableColumn<Classes, Integer> classId;

    @FXML
    private TableColumn<Classes, String> className;

    @FXML
    private TableColumn<Classes, String> instructorName;

    @FXML
    private TableColumn<Classes, String> schedule;

    @FXML
    private TableColumn<Classes, Integer> capacity;

    @FXML
    private TableColumn<Classes, String> status;

    @FXML
    private TableColumn<Classes, Void> action;

    @FXML
    private TableView<Classes> classTableView;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchTextField;

    private ObservableList<Classes> classesData = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        classId.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        className.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        instructorName.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        // status.setCellValueFactory(); // Set your status property here


        setupActionColumn();

        List<Classes> classesList = classesRepository.getAllClasses();
        classesData.addAll(classesList);
        classTableView.setItems(classesData);
    }

    private void setupActionColumn() {
        action.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Classes selectedClass = getTableView().getItems().get(getIndex());
                    // Handle edit action here
                });

                deleteButton.setOnAction(event -> {
                    Classes selectedClass = getTableView().getItems().get(getIndex());
                    classesRepository.deleteClass(selectedClass.getClass_id());
                    classesData.remove(selectedClass);
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
