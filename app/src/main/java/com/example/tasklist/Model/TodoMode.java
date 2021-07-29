package com.example.tasklist.Model;


// this model define the structure of the individul task
public class TodoMode {

    private int id,status;  // id of the task in the database and the status of the task whether it is complete or not
    private String task;   // the actual task that we write

    // write all the getter and setter function for the structure
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
