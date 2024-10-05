/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bskhq.tasktrackercli.Status;
import com.bskhq.tasktrackercli.Task;
import com.bskhq.tasktrackercli.fileWriter;

/**
 *
 * @author kunle
 */
public class fileWriterTest {

    public fileWriterTest() {
    }

    @BeforeEach
    void deleteFile() {
        try {
            Files.deleteIfExists(Path.of("taskStore.json"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void writeToFileTest() {
        String[] testDescription = {"a new task 1", "another task", "THIRD task", "sOmething 2 do later"};

        fileWriter.writeToFile(new Task(testDescription[0], 1));
        fileWriter.writeToFile(new Task(testDescription[1], 2));
        fileWriter.writeToFile(new Task(testDescription[2], 3));
        fileWriter.writeToFile(new Task(testDescription[3], 4));

        int taskId = 0;
        String taskDescription = "";

        for (int i = 0; i < testDescription.length; i++) {
            try (Scanner reader = new Scanner(Path.of("taskStore.json"))) {
                String line = reader.nextLine();
                if (line.length() < 2) {
                    continue;
                }
                String[] sgs = line.split(",");

                Pattern idPattern = Pattern.compile("\\d+");
                Matcher idMatcher = idPattern.matcher(sgs[0]);

                if (idMatcher.find()) {
                    taskId = Integer.parseInt(idMatcher.group());
                }

                Pattern descPattern = Pattern.compile("\"(description)\":\"(.+)\"");
                Matcher descMatcher = descPattern.matcher(sgs[1]);

                if (descMatcher.find()) {
                    taskDescription = descMatcher.group(2);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Assertions.assertEquals(testDescription[i], taskDescription);
            Assertions.assertEquals(i + 1, taskId);
        }

    }

    @Test
    void JSONtoHashmapTest() {
        String[] testDescription = {"a new task 1", "another task", "THIRD task", "sOmething 2 do later"};

        fileWriter.writeToFile(new Task(testDescription[0], 1));
        fileWriter.writeToFile(new Task(testDescription[1], 2));
        fileWriter.writeToFile(new Task(testDescription[2], 3));
        fileWriter.writeToFile(new Task(testDescription[3], 4));

         

        Map<Integer, Task> taskMap = fileWriter.JSONtoHashmap();

        for (Task t: taskMap.values()){
            System.out.println(t);
        }

        Assertions.assertEquals(4, taskMap.size());

        
        if (taskMap.get(1) instanceof Task) {
            Assertions.assertEquals(testDescription[0], taskMap.get(1).description());
            Assertions.assertEquals(Status.NOTDONE, taskMap.get(1).getStatus());
            Assertions.assertEquals(1, taskMap.get(1).getId());
        } else {
            Assertions.fail("Something went wrong with converting the Json file to hashmap");
        }

    }

}
