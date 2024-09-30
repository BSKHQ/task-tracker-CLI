/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bskhq.tasktrackercli;

import java.time.LocalDateTime;

/**
 *
 * @author kunle
 */
public class Task {
    private Status status;
    private String description;
    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String description, int id){
        this.id = id;
        this.description = description;
        this.status = Status.NOTDONE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Task(String description, int id, Status status, LocalDateTime createdTime, LocalDateTime updatedTime){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdTime;
        this.updatedAt = updatedTime;
    }

/*********************SETTERS************************************* */
    public void update(String newDescription){
        this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(Status newStatus){
        this.status = newStatus;
        
    }

/*********************GETTERS************************************* */

    public int getId(){
        return this.id;
    }
    public Status getStatus(){
        return this.status;
    }

    public String description(){
        return this.description;
    }

    public LocalDateTime getTimeCreatedAt(){
        return this.createdAt;
    }

    public LocalDateTime getLastUpdatedTime(){
        return this.updatedAt;
    }

    public String toString(){
        return this.id + " " + this.description + " " + this.status + getTimeCreatedAt() + "  " + getLastUpdatedTime();
    }


}
