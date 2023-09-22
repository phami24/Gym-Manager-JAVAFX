package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.entity.Event;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.EventRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.example.gymmanagement.model.service.EmailService;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class EventController implements Initializable {

    @FXML
    private Button minimizeButton;
    @FXML
    private TableView<Event> tableView;
    @FXML
    private TextField event_name;
    @FXML
    private TextArea description;
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
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> discountColumn;
    @FXML
    private TableColumn<Event, String> startDateColumn;
    @FXML
    private TableColumn<Event, String> endDateColumn;
    @FXML
    private TableColumn<Event, String> statusColumn;
    @FXML
    private TableColumn<Event, Void> actionColumn;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final StageManager stageManager = new StageManager();

    private EventRepository eventRepository = new EventRepository();
    private ObservableList<Event> eventData = FXCollections.observableArrayList();
    private MembersRepository membersRepository = new MembersRepository();
    private final StringConverter<LocalDate> localDateStringConverter = new LocalDateStringConverter();


    @FXML
    private Pagination pagination;

    public static String formatLocalDate(LocalDate date, String pattern) {
        // Check for null date
        if (date == null) {
            return "";
        }

        // Create a DateTimeFormatter with the specified pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Format the LocalDate to a string
        return date.format(formatter);
    }
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        // Handle the update button click event
        // This method will be called when the update button is clicked
        // You can get values from text fields, date pickers, etc.
        // and perform necessary actions here

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to update this Event?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            Event events = tableView.getSelectionModel().getSelectedItem();
            if (events != null) {
                String eventName = event_name.getText();
                String des = description.getText();
                String dis = discount.getText();
                String startDate = String.valueOf(start_date.getValue());
                String endDate = String.valueOf(end_date.getValue());
//                String statuss = statusColumn.getText();

                events.setEvent_name(eventName);
                events.setDescription(des);

                BigDecimal discountPercent = new BigDecimal(dis);
                events.setDiscount_percent(discountPercent);

                events.setStart_date(startDate);
                events.setEnd_date(endDate);

//                int statusValue = stringToInt(statuss);
//                events.setStatus(statusValue);

                eventRepository.updateEvent(events);
                initializeTableColumns();
                clearField();
                tableView.refresh();
                addButton.setDisable(false);
                updateButton.setDisable(true);
            }
        }
    }


    @FXML
    private void handleAddButton(ActionEvent event) {
        // Handle the add button click event
        // This method will be called when the add button is clicked
        // You can get values from text fields, date pickers, etc.
        // and perform necessary actions here

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to add this Event?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            try {
                String eventNameText = event_name.getText();
                String eventDes = description.getText();
                String discountText = discount.getText();
                LocalDate start = start_date.getValue();
                LocalDate endDate = end_date.getValue();
//                String statuss = status.getText();


                Event newEvent = new Event();
                newEvent.setEvent_name(eventNameText);

                newEvent.setStart_date(String.valueOf(start));

                newEvent.setEnd_date(String.valueOf(endDate));

                BigDecimal discountPercent = new BigDecimal(discountText);
                newEvent.setDiscount_percent(discountPercent);

                newEvent.setDescription(eventDes);

                Members m = new Members();
                int memberId = m.getMember_id(); // You need to implement this logic
                newEvent.setMember_id(memberId);

//                int statusValue = stringToInt(statuss);
//                newEvent.setStatus(statusValue);

                eventRepository.addEvent(newEvent);
                tableView.getItems().add(newEvent);
                initializeTableColumns();
                clearField();
                tableView.refresh();
                addButton.setDisable(false);
                updateButton.setDisable(true);

            } catch (NumberFormatException e) {
                // Xử lý lỗi khi chuyển đổi không thành công
                // Ví dụ: Hiển thị thông báo lỗi cho người dùng
                Alert alertB = new Alert(Alert.AlertType.ERROR);
                alertB.setTitle("Error");
                alertB.setHeaderText("Invalid Discount Format");
                alertB.setContentText("Please enter a valid discount value.");
                alertB.showAndWait();
            }
        }
    }
    private void statusMenu(){
        MenuItem unexpiredMenuItem = new MenuItem("Unexpired");
        MenuItem expiredMenuItem = new MenuItem("Expired");

        unexpiredMenuItem.setOnAction(event -> {
            status.setText(unexpiredMenuItem.getText());
        });

        expiredMenuItem.setOnAction(event -> {
            status.setText(expiredMenuItem.getText());
        });

        status.getItems().addAll(unexpiredMenuItem, expiredMenuItem);
    }
    public static String intToString(int status) {
        switch (status) {
            case 0:
                return "Unexpired";
            case 1:
                return "Expired";
            // Thêm các trường hợp khác nếu cần
            default:
                return "";
        }
    }
    public static int stringToInt(String status) {
        switch (status.toLowerCase()) {
            case "Unexpired":
                return 0;
            case "Expired":
                return 1;
            // Thêm các trường hợp khác nếu cần
            default:
                return 1; // Giá trị mặc định hoặc lỗi
        }
    }

    @FXML
    public void close(MouseEvent event) {
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


    private void showEventDetails(Event event) {
        event_name.setText(event.getEvent_name());
        description.setText(event.getDescription());
        discount.setText(String.valueOf(event.getDiscount_percent()));
        start_date.setValue(LocalDate.parse(event.getStart_date()));
        end_date.setValue(LocalDate.parse(event.getEnd_date()));
//        String statusString = intToString(event.getStatus());
//        status.setText(statusString);
        addButton.setDisable(true);
        updateButton.setDisable(false);

    }

    public String formatLocalDate(LocalDate date) {
        if (date == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    private void initializeTableColumns() {
//        eventNameColumn = new TableColumn<>("Event Name");
//        descriptionColumn = new TableColumn<>("Description");
//        discountColumn = new TableColumn<>("Discount");
//        startDateColumn = new TableColumn<>("Start Date");
//        endDateColumn = new TableColumn<>("End Date");
//        statusColumn = new TableColumn<>("Status");
//        actionColumn = new TableColumn<>("Action");

        // Set up the PropertyValueFactory to map the TableColumn to the corresponding property in Event
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount_percent"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end_date"));

        // Create a custom cell factory for the startDateTableColumn
        startDateColumn.setCellFactory(new Callback<TableColumn<Event, String>, TableCell<Event, String>>() {
            @Override
            public TableCell<Event, String> call(TableColumn<Event, String> column) {
                return new TableCell<Event, String>() {
                    private final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    private final DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            try {
                                Date date = inputFormat.parse(item);
                                setText(outputFormat.format(date));
                            } catch (ParseException e) {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        endDateColumn.setCellFactory(new Callback<TableColumn<Event, String>, TableCell<Event, String>>() {
            @Override
            public TableCell<Event, String> call(TableColumn<Event, String> column) {
                return new TableCell<Event, String>() {
                    private final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    private final DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            try {
                                Date date = inputFormat.parse(item);
                                setText(outputFormat.format(date));
                            } catch (ParseException e) {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });


//        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up the Action column

        actionColumn.setCellFactory(column -> new TableCell<Event, Void>() {
            private final Button deleteButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/removed.png"))));
            private final Button emailButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/com/example/gymmanagement/image/message.png"))));

            {
                deleteButton.setOnAction(event -> {
                    Event eventt = getTableView().getItems().get(getIndex());
                    Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDeleteAlert.setTitle("Confirm Delete");
                    confirmDeleteAlert.setHeaderText("Are you sure you want to delete this event?");
                    confirmDeleteAlert.setContentText("Event: " + eventt.getEvent_name());

                    Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        eventRepository.deleteEvent(eventt.getEvent_id());

                        // Remove the deleted item from the observable list
                        eventData.remove(eventt);


                        // Refresh the TableView to reflect the updated data
                        tableView.refresh();
                    }
                });


                emailButton.setOnAction(event -> {
                    Event eventEmail = getTableView().getItems().get(getIndex());
                    handleSendEmailButton(eventEmail);
                });

                // Thiết lập kích thước cho các nút
                deleteButton.setPrefSize(10, 10);
                emailButton.setPrefSize(10, 10);
                ImageView deleteImageView = (ImageView) deleteButton.getGraphic();
                deleteImageView.setFitWidth(20);
                deleteImageView.setFitHeight(20);

                ImageView showDetailImageView = (ImageView) emailButton.getGraphic();
                showDetailImageView.setFitWidth(20);
                showDetailImageView.setFitHeight(20);
                deleteButton.getStyleClass().add("transparent-button");
                emailButton.getStyleClass().add("transparent-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(deleteButton, emailButton);
                    setGraphic(buttons);
                }
            }
        });

//        List<Event> events = eventRepository.getAllEvents();
//        tableView.getItems().addAll(events);


//        actionColumn.setCellFactory(param -> new TableCell<>() {
//            private final Button deleteButton = new Button("Delete");
//            private final Button emailButton = new Button("Send Email");
//
//            {
//                deleteButton.setOnAction(event -> {
//                    Event eventDelete = getTableView().getItems().get(getIndex());
//                    handleDeleteButton(eventDelete);
//                });
//
//                emailButton.setOnAction(event -> {
//                    Event eventEmail = getTableView().getItems().get(getIndex());
//                    handleSendEmailButton(eventEmail);
//                });
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    HBox buttonsBox = new HBox(deleteButton, emailButton);
//                    buttonsBox.setSpacing(10);
//                    setGraphic(buttonsBox);
//                }
//            }
//        });
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

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);

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

    public void clearField(){
        event_name.clear();
        discount.clear();
        description.clear();
        start_date.setValue(null);
        end_date.setValue(null);
    }
    @FXML
    private Button dashboard;
    @FXML
    private Button homePage;
    @FXML
    private Button logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the controller
        // This method will be called when the FXML is loaded
        // You can set initial values, event listeners, etc. here

//        initializeTableColumns();
//
        // Set up the TableView columns
//        tableView.getColumns().addAll(eventNameColumn, descriptionColumn, discountColumn,
//                startDateColumn, endDateColumn, statusColumn, actionColumn);

        // Example: Adding items to the status MenuButton
//        MenuItem unexpiredMenuItem = new MenuItem("Unexpired");
//        MenuItem expiredMenuItem = new MenuItem("Expired");
//
//        unexpiredMenuItem.setOnAction(event -> {
//            status.setText(unexpiredMenuItem.getText());
//        });
//
//        expiredMenuItem.setOnAction(event -> {
//            status.setText(expiredMenuItem.getText());
//        });
//
//        status.getItems().addAll(unexpiredMenuItem, expiredMenuItem);

        statusMenu();
        // Example: Adding event handlers to buttons
        updateButton.setOnAction(this::handleUpdateButton);
        addButton.setOnAction(this::handleAddButton);

        List<Event> events = eventRepository.getAllEvents();
        tableView.getItems().addAll(events);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showEventDetails(newValue);
            }
        });
        initializeTableColumns();

        Tooltip tooltipH = new Tooltip("Home");
        tooltipH.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        homePage.setTooltip(tooltipH);
        Tooltip tooltipD = new Tooltip("Dashboard");
        tooltipD.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        dashboard.setTooltip(tooltipD);
        Tooltip tooltipL = new Tooltip("Logout");
        tooltipL.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: #fff;");
        logout.setTooltip(tooltipL);
        updateButton.setDisable(true);
    }
}
