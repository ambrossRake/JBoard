package com.github.ambrossrake.model;

import com.github.ambrossrake.controller.TaskController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class Task implements Drawable{
    Logger LOG = LoggerFactory.getLogger(Task.class);
    private String name;
    private String description;


    @Override
    public Node draw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Task.fxml"));
            loader.setController(new TaskController(this));
            return loader.load();
        } catch (IOException e) {
            LOG.error("Could not draw task: " + name, e);
        }

        return null;
    }

    public enum Status {
        ToDo,
        InProgress,
        Complete
    }

    private Status currentStatus;

    public Task(){
        this("","");
    }
    public Task(String name, String description) {
        this(name, description, Status.ToDo);
    }

    public Task(String name, String description, Status status){
        this.name = name;
        this.description = description;
        this.currentStatus = status;
        //this.changeListener.stateChanged(new ChangeEvent(this));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentStatus(Status newStatus){
        currentStatus = newStatus;
    }

    public Status getCurrentStatus(){
        return currentStatus;
    };

//    public ChangeListener getChangeListener(){
//        return changeListener;
//    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
