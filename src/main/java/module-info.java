module com.example.gymmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens com.example.gymmanagement to javafx.fxml;
    exports com.example.gymmanagement;
    exports com.example.gymmanagement.controller;
    opens com.example.gymmanagement.controller to javafx.fxml;
}