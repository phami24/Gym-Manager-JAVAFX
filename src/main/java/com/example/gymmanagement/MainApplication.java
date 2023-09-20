package com.example.gymmanagement;

import com.example.gymmanagement.controller.StageManager;
import com.example.gymmanagement.model.repository.MembersRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApplication extends Application {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = new StageManager();
        stageManager.setCurrentStage(stage);
        stageManager.loadLoginStage();

        // Lên lịch thực thi tác vụ sau mỗi khoảng thời gian cụ thể
        scheduler.scheduleAtFixedRate(this::scheduledTask, 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void stop() {
        // Đảm bảo dừng scheduler khi ứng dụng kết thúc
        scheduler.shutdown();
    }

    public void scheduledTask() {
        // Thực hiện các tác vụ cần lên lịch ở đây
        Runnable task = () -> {
            new MembersRepository().updateMembershipStatusBasedOnEndDate();
        };

        // Chạy nhiệm vụ trong một luồng riêng biệt
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true); // Đặt luồng là daemon để cho phép ứng dụng thoát
        backgroundThread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
