package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.MembersRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MembersController implements Initializable {

    private final MembersRepository membersRepository = new MembersRepository();

    @FXML
    private TableColumn<Members, Void> action;

    @FXML
    private TableColumn<Members, String> email;

    @FXML
    private TableColumn<Members, String> endDate;

    @FXML
    private TableColumn<Members, String> fullName;

    @FXML
    private TableColumn<Members, String> gender;

    @FXML
    private TableColumn<Members, String> joinDate;

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

    private ObservableList<Members> membersData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cài đặt cách dữ liệu của các cột sẽ được lấy từ đối tượng Members và gắn vào TableView
        memberId.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        joinDate.setCellValueFactory(new PropertyValueFactory<>("join_date"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        status.setCellValueFactory(new PropertyValueFactory<>("membership_status_id"));
        type.setCellValueFactory(new PropertyValueFactory<>("membership_type_id"));

        setupActionColumn();

        // Lấy dữ liệu từ cơ sở dữ liệu và đổ vào TableView
        List<Members> membersList = membersRepository.getAllMembers();
        membersData.addAll(membersList);
        member_tableView.setItems(membersData);
    }

    private void setupActionColumn() {
        action.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Members, Void> call(final TableColumn<Members, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button updateButton = new Button("Update");
                    private final Button showDetailButton = new Button("Show Detail");

                    {
                        // Xử lý sự kiện khi nhấn nút "Delete"
                        deleteButton.setOnAction(event -> {
                            Members member = getTableView().getItems().get(getIndex());
                            membersRepository.deleteMember(member.getMember_id());
                            membersData.remove(member);
                            member_tableView.refresh();
                        });

                        // Xử lý sự kiện khi nhấn nút "Update"
                        updateButton.setOnAction(event -> {
                            // Show Update Form
                        });

                        // Xử lý sự kiện khi nhấn nút "Show Detail"
                        showDetailButton.setOnAction(event -> {
                            Members member = getTableView().getItems().get(getIndex());
                            // Show Update stage
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(deleteButton, updateButton, showDetailButton);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        });
    }
}
