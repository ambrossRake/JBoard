package com.github.ambrossrake.controller;

import com.github.ambrossrake.model.Agenda;
import com.github.ambrossrake.model.Task;
import com.github.ambrossrake.service.TaskService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

public class DashboardController{

    private DropDownButtonController toDoDropDownController;
    public Button toDoDropDown;
    public Label welcomeMessage;
    public VBox completeTaskView;
    public VBox inProgressTaskView;
    public VBox toDoTaskView;
    public Label updateMessage;

    private final TaskService taskService = TaskService.getInstance();
    private final Properties dashboardConfig = new Properties();
    private Agenda agenda;

    @FXML
    public void initialize(){
        try {
            FileReader reader = new FileReader("src/config.properties");
            dashboardConfig.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        welcomeMessage.setText("Welcome back, " + dashboardConfig.getProperty("first-name"));
        updateMessage.setText("Last updated: "+ Calendar.getInstance().getTime());
        agenda = taskService.getAgenda();
        toDoDropDownController = (DropDownButtonController) toDoDropDown.getUserData();
        toDoDropDownController.setView(toDoTaskView);
    }

    public void addTask(MouseEvent mouseEvent) {
        Task toDoTask = new Task();
        agenda.addTask(toDoTask);
        toDoDropDownController.getDropDownItems().add(toDoTask);
    }

}
