package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Event;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.EventRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import com.example.gymmanagement.model.service.EmailService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventController {
    @FXML
    private TableView<Event> tableView;
    @FXML
    private TextField event_name;
    @FXML
    private TextField description;
    @FXML
    private TextField discount;
    @FXML
    private DatePicker start_date;
    @FXML
    private DatePicker end_date;
    @FXML
    private MenuButton status;
    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;

    private TableColumn<Event, String> eventNameColumn;
    private TableColumn<Event, String> descriptionColumn;
    private TableColumn<Event, String> discountColumn;
    private TableColumn<Event, LocalDate> startDateColumn;
    private TableColumn<Event, LocalDate> endDateColumn;
    private TableColumn<Event, String> statusColumn;
    private TableColumn<Event, Void> actionColumn;

    @FXML
    private void handleUpdateButton(ActionEvent event) {
        // Handle the update button click event
        // This method will be called when the update button is clicked
        // You can get values from text fields, date pickers, etc.
        // and perform necessary actions here
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        // Handle the add button click event
        // This method will be called when the add button is clicked
        // You can get values from text fields, date pickers, etc.
        // and perform necessary actions here
    }

    @FXML
    private void initialize() {
        // Initialize the controller
        // This method will be called when the FXML is loaded
        // You can set initial values, event listeners, etc. here

        initializeTableColumns();

        // Set up the TableView columns
        tableView.getColumns().addAll(eventNameColumn, descriptionColumn, discountColumn,
                startDateColumn, endDateColumn, statusColumn, actionColumn);

        // Example: Adding items to the status MenuButton
        MenuItem unexpiredMenuItem = new MenuItem("Unexpired");
        MenuItem expiredMenuItem = new MenuItem("Expired");

        unexpiredMenuItem.setOnAction(event -> {
            status.setText(unexpiredMenuItem.getText());
        });

        expiredMenuItem.setOnAction(event -> {
            status.setText(expiredMenuItem.getText());
        });

        status.getItems().addAll(unexpiredMenuItem, expiredMenuItem);

        // Example: Adding event handlers to buttons
        updateButton.setOnAction(this::handleUpdateButton);
        addButton.setOnAction(this::handleAddButton);
    }

    private void initializeTableColumns() {
        eventNameColumn = new TableColumn<>("Event Name");
        descriptionColumn = new TableColumn<>("Description");
        discountColumn = new TableColumn<>("Discount");
        startDateColumn = new TableColumn<>("Start Date");
        endDateColumn = new TableColumn<>("End Date");
        statusColumn = new TableColumn<>("Status");
        actionColumn = new TableColumn<>("Action");

        // Set up the PropertyValueFactory to map the TableColumn to the corresponding property in Event
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up the Action column
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button emailButton = new Button("Send Email");

            {
                deleteButton.setOnAction(event -> {
                    Event eventDelete = getTableView().getItems().get(getIndex());
                    handleDeleteButton(eventDelete);
                });

                emailButton.setOnAction(event -> {
                    Event eventEmail = getTableView().getItems().get(getIndex());
                    handleSendEmailButton(eventEmail);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(deleteButton, emailButton);
                    buttonsBox.setSpacing(10);
                    setGraphic(buttonsBox);
                }
            }
        });
    }

    @FXML
    private void handleDeleteButton(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete this event?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int eventId = event.getEvent_id();
            EventRepository eventRepository = new EventRepository();
            eventRepository.deleteEventByStatus(eventId);

            // You can perform additional actions here if needed, such as refreshing the TableView
            // or displaying a message to the user
            // For example, if tableView is the instance variable for your TableView:
            tableView.getItems().remove(event);
        }
    }


    private String loadHtmlTemplate(Event event) {
        String template = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f0f0f0; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }" +
                ".header { background-color: #007bff; color: #ffffff; padding: 10px 0; text-align: center; }" +
                ".content { padding: 20px; }" +
                ".event-details { margin-bottom: 20px; }" +
                "h2 { color: #333333; }" +
                "p { color: #666666; }" +
                ".button { display: inline-block; background-color: #007bff; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>New Event Notification</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Dear members,</h2>" +
                "<p>We are pleased to announce a new event: <strong>{{event_name}}</strong>. The event will take place from <strong>{{start_date}}</strong> to <strong>{{end_date}}</strong>. Don't miss it!</p>" +
                "<div class='event-details'>" +
                "<h2>Event Details</h2>" +
                "<p><strong>Event Name:</strong> {{event_name}}</p>" +
                "<p><strong>Event Description:</strong> {{event_description}}</p>" +
                "<p><strong>Start Date:</strong> {{start_date}}</p>" +
                "<p><strong>End Date:</strong> {{end_date}}</p>" +
                "</div>" +
                "<a class='button' href='#'>Learn More</a>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        return template
                .replace("{{event_name}}", event.getEvent_name())
                .replace("{{event_description}}", event.getDescription())
                .replace("{{start_date}}", event.getStart_date().toString())
                .replace("{{end_date}}", event.getEnd_date().toString());
    }

    @FXML
    private void handleSendEmailButton(Event event) {
        MembersRepository membersRepository = new MembersRepository();
        List<Members> members = membersRepository.getAllMembers();
        List<String> memberEmails = new ArrayList<>();
        for (Members member : members) {
            memberEmails.add(member.getEmail());
        }

        String content = loadHtmlTemplate(event);

        EmailService emailService = new EmailService();
        emailService.sendEmailToMembers(memberEmails, "New Event Notification", content);
    }


}
