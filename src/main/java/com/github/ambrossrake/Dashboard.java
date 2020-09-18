package com.github.ambrossrake;

import com.github.ambrossrake.service.TaskService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Scene scene = new Scene(root, 1024, 512);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            TaskService.getInstance().saveTasks();
        });
    }

    public static void main(String[] args){
        launch();
    }
}
