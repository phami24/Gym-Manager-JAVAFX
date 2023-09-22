package com.example.gymmanagement;

import com.example.gymmanagement.controller.StageManager;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.service.EmailService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApplication extends Application {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final MembersRepository membersRepository = new MembersRepository();
    private final EmailService emailService = new EmailService();

    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = new StageManager();
        stageManager.setCurrentStage(stage);
        stageManager.loadLoginStage();
    }


    public static void main(String[] args) {
        launch();
    }
}
