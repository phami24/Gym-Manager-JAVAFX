package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.repository.InstructorRepository;
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

public class InstructorController implements Initializable {

    private final InstructorRepository instructorsRepository = new InstructorRepository();

    @FXML
    private TableColumn<Instructors, Integer> id;

    @FXML
    private TableColumn<Instructors, String> full_name;

    @FXML
    private TableColumn<Instructors, String> gender;

    @FXML
    private TableColumn<Instructors, String> email;

    @FXML
    private TableColumn<Instructors, String> phone;

    @FXML
    private TableColumn<Instructors, String> hireDate;

    @FXML
    private TableColumn<Instructors, Integer> experience;

    @FXML
    private TableColumn<Instructors, String> status;

    @FXML
    private TableColumn<Instructors, Void> action;

    @FXML
    private TableView<Instructors> tableView;

    @FXML
    private TextField searchTextField;

    private ObservableList<Instructors> instructorsData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello World");
        id.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        full_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        hireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupActionColumn();

        List<Instructors> instructorsList = instructorsRepository.getAllInstructors();
        instructorsData.addAll(instructorsList);
        tableView.setItems(instructorsData);
    }

    private void setupActionColumn() {
        action.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Instructors selecttedInstructor = getTableView().getItems().get(getIndex());
                    // Handle edit action here
                });

                deleteButton.setOnAction(event -> {
                    Instructors selecttedInstructor = getTableView().getItems().get(getIndex());
                    instructorsRepository.deleteInstructor(selecttedInstructor.getInstructor_id());
                    instructorsData.remove(selecttedInstructor);
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
