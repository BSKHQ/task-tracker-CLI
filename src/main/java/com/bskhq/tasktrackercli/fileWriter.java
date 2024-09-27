/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bskhq.tasktrackercli;

/**
 *
 * @author kunle
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class fileWriter {

    /*this class handles reading and writing from and to the JSON file */
 /*Since Java doesn't have any internal libraries for handling JSON and we aren't allowed to 
     * import any external libraries, we'll just have to implement one :)
     */
    public fileWriter() throws IOException {
        try {
            Files.createFile(Path.of("taskStore.json"));
        } catch (FileAlreadyExistsException e) {
        }
    }

    public void writeToFile(Task task) {
        //1.Writes a task to file, //2.unless it already exists in which case it is updated
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("taskStore.json"), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {

            if (Files.size(Path.of("taskStore.json")) == 0) {
                writer.write("[]");
            }

            RandomAccessFile file = new RandomAccessFile("taskStore.json", "rw");
            
            file.seek(file.length()-1);
            String toBeWritten = "\n" + "{" + "\"id\":" + "\"" + task.getId() + "\"," +
                                    "\"description\":" + "\"" + task.description() + "\"," +
                                    "\"status\":" + "\"" + task.getStatus().toString() + "\"," +
                                    "\"createdAt\":" + "\"" + task.getTimeCreatedAt().toString() + "\","+
                                    "\"updatedAt\":" + "\"" + task.getLastUpdatedTime().toString() + "\"" + "}";

            writer.newLine();
            writer.write("{");
            writer.write("\"id\":" + "\"" + task.getId() + "\",");
            writer.write("\"description\":" + "\"" + task.description() + "\",");
            writer.write("\"status\":" + "\"" + task.getStatus().toString() + "\",");
            writer.write("\"createdAt\":" + "\"" + task.getTimeCreatedAt().toString() + "\",");
            writer.write("\"updatedAt\":" + "\"" + task.getLastUpdatedTime().toString() + "\"");
            writer.write("}");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Task getTaskFromFile(int id) {
        return null;
    }
}
