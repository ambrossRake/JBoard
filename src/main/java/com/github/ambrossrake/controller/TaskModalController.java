package com.github.ambrossrake.controller;

import com.github.ambrossrake.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskModalController {

    private final Logger LOG = LoggerFactory.getLogger(TaskModalController.class);
    public TextField taskTitleView;
    public TextArea taskDescriptionView;
    private final Task task;

    public TaskModalController(Task task) {
        this.task = task;
    }

    @FXML
    public void initialize() {
        LOG.debug("Task modal initialized for Task: " + task);
        taskTitleView.setText(task.getName());
        taskDescriptionView.setText(task.getDescription());

        taskDescriptionView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue){
                LOG.debug("Task description view lost focus, updating task description");
                task.setDescription(taskDescriptionView.getText());
            }
        });
        taskTitleView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue){
                LOG.debug("Task title view lost focus, updating task name");
                task.setName(taskTitleView.getText());
            }
        });
    }
}