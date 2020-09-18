package com.github.ambrossrake.service;

import com.github.ambrossrake.model.Agenda;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class TaskService {

    private final Logger LOG = LoggerFactory.getLogger(TaskService.class);
    private static TaskService instance;
    private Agenda agenda = new Agenda();
    private final Gson gson = new Gson();
    private final String url = getClass().getResource("/data/").getPath()+"agenda.json";

    private TaskService() {
        loadTasks();
    }

    public static TaskService getInstance(){
        if(instance == null) {
            instance = new TaskService();
        }

        return instance;
    }

    private void loadTasks(){
        LOG.debug("Loading tasks from " + url);
        File agendaFile = new File(url);
        if(!agendaFile.exists()) {
            LOG.warn("No agenda file found");
            return;
        }

        try {
            LOG.debug("Reading agenda JSON");
            BufferedReader reader = new BufferedReader(new FileReader(agendaFile));
            JsonReader jsonReader = new JsonReader(reader);
            agenda = gson.fromJson(jsonReader, Agenda.class);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(),e);
        }
    }

    public void saveTasks(){
        LOG.debug("Saving tasks to " + url);
        File agendaFile = new File(url);
        System.out.println(agendaFile.exists());
        if(!agendaFile.exists()){
            LOG.debug("Agenda file not found, creating");
            try {
                File taskFileDirectory = agendaFile.getParentFile();
                if(!taskFileDirectory.exists()){
                    LOG.debug("Agenda directory not found, creating");
                    taskFileDirectory.mkdirs();
                }
                agendaFile.createNewFile();
            } catch (IOException e) {
                LOG.error(e.getMessage(),e);
            }
        }

        try {
            LOG.debug("Writing agenda to file");
            BufferedWriter writer = new BufferedWriter(new FileWriter(agendaFile));
            writer.write(gson.toJson(agenda));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Agenda getAgenda() {
        return agenda;
    }
}
