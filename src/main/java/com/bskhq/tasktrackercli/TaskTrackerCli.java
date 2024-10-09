/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.bskhq.tasktrackercli;

/**
 *
 * @author kunle
 */
public class TaskTrackerCli {

    public static void main(String[] args) {

        String COMMAND = "";
        String COMMAND2 = "";
        String COMMAND3 = "";

        try {
            COMMAND = args[0].trim();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            noCommand();
            return;
        }

        try {
            COMMAND2 = args[1].trim();
            COMMAND3 = args[2].trim();
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            //silent
        }

        switch (COMMAND) {
            case "add" ->
                add(COMMAND2, args);
            case "update" ->
                update(COMMAND2, COMMAND3);
            case "delete" ->
                delete(COMMAND2);
            case "mark-in-progress", "mark-done" ->
                markTasks(COMMAND, COMMAND2);
            case "list" ->
                listTasks(COMMAND2);
            case "help" ->
                help();
            default -> {
                System.out.println("You need to give a valid command. Try \'tasktrackercli help\' for list of available commands");
            }
        }

    }

    private static void help() {
        System.out.println("Available Commands:");
        System.out.println("    add - add a new task");
        System.out.println("    update - update a task");
        System.out.println("    delete - delete a task");
        System.out.println("    mark-in-progress - mark a task as in progress");
        System.out.println("    mark-done - mark a task as done");
        System.out.println("    list - list all tasks");
        System.out.println("    list done - list all tasks that are done");
        System.out.println("    list todo - list all tasks that are not done");
        System.out.println("    list in-progress - list all tasks that are in progress");
    }

    private static void noCommand() {
        System.out.println("TaskTrackerCLI is a commandline tool for managing your daily tasks.\n");
        help();
        System.out.println("    help - list all commands");

    }

    private static void add(String COMMAND2, String[] args) {
        if (COMMAND2.isBlank()) {
            System.out.println("You did not give a description for your task");
            System.out.println("try \'add your-task-description\' ");
            return;
        }

        StringBuilder task = new StringBuilder();
        task.append(COMMAND2.trim());

        for (int i = 2; i < args.length; i++) {
            task.append(" ");
            task.append(args[i].trim());
        }
        Manager.add(task.toString());
    }

    private static void update(String COMMAND2, String COMMAND3) {
        if (taskIdValid(COMMAND2)) {
            if (COMMAND3.isBlank()) { //make sure the description is not blank
                System.out.println("INVALID TASK DESCRIPTION");
                return;
            }
            Manager.updateTask(Integer.parseInt(COMMAND2), COMMAND3);
        }
    }

    private static void delete(String COMMAND2) {
        if (taskIdValid(COMMAND2)) {
            Manager.deleteTask(Integer.parseInt(COMMAND2));
        }
    }

    private static void markTasks(String COMMAND, String COMMAND2) {
        switch (COMMAND) {
            case "mark-in-progress" ->
                markTaskInProgress(COMMAND2);
            case "mark-done" ->
                markTaskDone(COMMAND2);
            default ->
                System.out.println(COMMAND + " is not a valid command");
        }
    }

    private static void markTaskInProgress(String COMMAND2) {
        if (taskIdValid(COMMAND2)) {
            Manager.markTaskAsInProgress(Integer.parseInt(COMMAND2));
        }
    }

    private static void markTaskDone(String COMMAND2) {
        if (taskIdValid(COMMAND2)) {
            Manager.markTaskAsDone(Integer.parseInt(COMMAND2));
        }
    }

    private static void listTasks(String COMMAND2) {
        switch (COMMAND2) {
            case "done" ->
                Manager.listDoneTasks();
            case "todo" ->
                Manager.listToDoTasks();
            case "in-progress" ->
                Manager.listInProgressTasks();
            case "" ->
                Manager.listAllTasks();
            default ->
                System.out.println(COMMAND2 + " is not a valid command");

        }
    }

    private static boolean taskIdValid(String COMMAND2) {

        if (COMMAND2.isBlank()) {// make sure the task Id isn't blank
            System.out.println("task id cannot be blank");
            return false;
        }

        int taskId = 0;
        try {
            taskId = Integer.parseInt(COMMAND2); //make sure the task id is an integer
        } catch (NumberFormatException e) {
            System.out.println("please give a valid task id");
            return false;
        }
        //finally, check that the taskid actually exists
        return Manager.checkValidId(taskId);
    }

}
