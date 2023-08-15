module com.example.gymmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires javafx.web;
    requires fontawesomefx;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens com.example.gymmanagement to javafx.fxml;
    exports com.example.gymmanagement;
    exports com.example.gymmanagement.controller;
    opens com.example.gymmanagement.controller to javafx.fxml;
    opens com.example.gymmanagement.model.entity to javafx.base;
}