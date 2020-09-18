package com.github.ambrossrake.controller;

import com.github.ambrossrake.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TaskController {

    Logger LOG = LoggerFactory.getLogger(TaskController.class);

    public HBox taskView;
    public TextField taskTitle;
    private boolean isNewTask = true;
    private boolean isFinalized = false;
    private final Task task;

    public TaskController(Task task) {
        this.task = task;
    }

    @FXML
    private void initialize(){
        if(!task.getName().isEmpty()){
            LOG.debug("Task loaded: " + task.getName());
            taskTitle.setText(task.getName());
            taskTitle.setEditable(false);
            isNewTask = false;
            isFinalized = true;
        }else{
            LOG.debug("Creating new task, waiting for name input");
            taskTitle.setText("Task Title");
            taskTitle.setStyle("-fx-text-fill:#ccc");
            taskTitle.requestFocus();
        }

        taskTitle.setOnKeyPressed(event -> {
            if(isNewTask){
                LOG.debug("Input  received, clearing default text");
                taskTitle.clear();
                taskTitle.setStyle("-fx-text-fill:#000");
                isNewTask = false;
            }
            if(event.getCode() == KeyCode.ENTER){
                finalizeTask();
            }
        });

        taskTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if(isNewTask){
                    // View/task should be deleted
                    taskTitle.setText("DELETED");
                }
                if(!taskTitle.getText().isEmpty()){
                    finalizeTask();
                }
            }
        });

        taskView.setOnMouseClicked(e -> {
            LOG.debug("Task " + task.getName() + " clicked.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskModal.fxml"));
                loader.setController(new TaskModalController(task));
                Parent root = loader.load();
                Stage dialog = new Stage();
                dialog.setScene(new Scene(root));
                dialog.setTitle(task.getName());
                dialog.setResizable(false);
                dialog.initOwner(((Node) e.getTarget()).getScene().getWindow());
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }

    private void finalizeTask(){
        if(isFinalized) return;
        isFinalized = true;
        task.setName(taskTitle.getText());
        taskTitle.setEditable(false);
        LOG.debug("Task title updated to: " + task.getName());
    }
}
