/*
 *  This file is licensed under the MIT License - see the LICENSE file for details
 */
package com.bskhq.tasktrackercli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Task(String description, int id) {
        this.id = id;
        this.description = description;
        this.status = Status.NOTDONE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Task(String description, int id, Status status, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdTime;
        this.updatedAt = updatedTime;
    }

    /**
     * *******************SETTERS*************************************
     */
    public void update(String newDescription) {
        this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;

    }

    public void changeID(int newId) {
        this.id = newId;
    }

    /**
     * *******************GETTERS*************************************
     */
    public int getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    public String description() {
        return this.description;
    }

    public LocalDateTime getTimeCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getLastUpdatedTime() {
        return this.updatedAt;
    }

    @Override
    public String toString() {
        String timeCreated = getTimeCreatedAt().format(DateTimeFormatter.ISO_LOCAL_TIME);
        String dateCreated = getTimeCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE);

        String timeUpdated = getLastUpdatedTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
        String dateUpdated = getLastUpdatedTime().format(DateTimeFormatter.ISO_LOCAL_DATE);

        //return this.id + " " + this.description + " " + this.status + " " + timeCreated + " @ " + dateCreated + " " + timeUpdated + " @ " + dateUpdated;
        return this.id + " " + this.description;
    }

}
