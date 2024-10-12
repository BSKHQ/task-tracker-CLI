/*
 *  This file is licensed under the MIT License - see the LICENSE file for details
 */
package com.bskhq.tasktrackercli;

import java.util.Map;

/**
 *
 * @author kunle
 */
public class Manager {

    /*manages Tasks */
    private Manager() {
    }

    public static void add(String task) {
        int currentId = fileWriter.JSONtoHashmap().size();
        addTask(new Task(task, currentId + 1));
    }

    public static void addTask(Task task) {
        fileWriter.writeToFile(task);
    }

    public static boolean checkValidId(int id) {
        //check that id is valid
        return !(id < 1 || id > fileWriter.JSONtoHashmap().size());
    }

    public static void updateTask(int id, String newDescription) {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();

        Task t = editMap.get(id);
        t.update(newDescription);

        fileWriter.HashMapToJson(editMap);
    }

    public static void deleteTask(int id) {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();
        int size = editMap.size();
        while (id < size) {
            editMap.get(id + 1).changeID(id);
            editMap.replace(id, editMap.get(id + 1));
            id++;
        }
        if (id == size) {
            editMap.remove(id);
        }
        fileWriter.HashMapToJson(editMap);
    }

    public static void listAllTasks() {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();
        editMap.values().forEach(task -> System.out.println(task));
    }

    public static void listDoneTasks() {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();
        editMap.values().stream()
                .filter(task -> task.getStatus() == Status.DONE)
                .forEach(task -> System.out.println(task));
    }

    public static void listToDoTasks() {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();
        editMap.values().stream()
                .filter(task -> task.getStatus() == Status.NOTDONE)
                .forEach(task -> System.out.println(task));
    }

    public static void listInProgressTasks() {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();
        editMap.values().stream()
                .filter(task -> task.getStatus() == Status.INPROGRESS)
                .forEach(task -> System.out.println(task));
    }

    public static void markTaskAsInProgress(int id) {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();

        Task t = editMap.get(id);
        t.setStatus(Status.INPROGRESS);

        fileWriter.HashMapToJson(editMap);
    }

    public static void markTaskAsDone(int id) {
        Map<Integer, Task> editMap = fileWriter.JSONtoHashmap();

        Task t = editMap.get(id);
        t.setStatus(Status.DONE);

        fileWriter.HashMapToJson(editMap);
    }

}
