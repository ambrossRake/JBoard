package com.github.ambrossrake.model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private final List<Task> taskList = new ArrayList<>();

    /**
     * Adds a task to the agenda
     * @param task The task to be added
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * Removes task from agenda
     * @param name The name of the task to be removed
     */
    public void removeTaskByName(String name){
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if(task.getName().equals(name)){
                taskList.remove(i);
                break;
            }
        }
    }

    public List<Task> getTasksByStatus(Task.Status status){
        List<Task> selectedTasks = new ArrayList<>();
        for (Task task: taskList) {
            if(task.getCurrentStatus() == status){
                selectedTasks.add(task);
            }
        }

        return selectedTasks;
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public List<Drawable> getTasksDrawable() {
        return new ArrayList<>(taskList);
    }
}
