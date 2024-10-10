/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bskhq.tasktrackercli.Manager;
import com.bskhq.tasktrackercli.Task;

/**
 *
 * @author kunle
 */
public class ManagerTest {

    private final static String FILE_NAME = "taskStore.json";

    private String[] testDescription;

    public ManagerTest() {

        testDescription = new String[4];
        testDescription[0] = "to do now";
        testDescription[1] = "4th of July Preparations!!";
        testDescription[2] = "to do later";
        testDescription[3] = "sOmething 2 do later";

    }

    @BeforeEach
    void InitializeFile() {
        try {
            Files.deleteIfExists(Path.of(FILE_NAME));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Manager.addTask((new Task(this.testDescription[0], 1)));
        Manager.addTask((new Task(this.testDescription[1], 2)));
        Manager.addTask((new Task(this.testDescription[2], 3)));
        Manager.addTask((new Task(this.testDescription[3], 4)));
    }

    @Test
    void addTaskTest() {
        String[] descriptionTests = {"other tasks", "4th of July Preparations again!!", "relaxx", "sOmething 4 me"};

        Manager.addTask((new Task(descriptionTests[0], 5)));
        Manager.addTask((new Task(descriptionTests[1], 6)));
        Manager.addTask((new Task(descriptionTests[2], 7)));
        Manager.addTask((new Task(descriptionTests[3], 8)));

        int taskId = 0;
        String taskDescription = "";

        for (int i = 0; i < descriptionTests.length; i++) {
            try (Scanner reader = new Scanner(Path.of(FILE_NAME))) {
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
            Assertions.assertEquals(i + 5, taskId);
        }
    }

    @Test
    void updateTaskTest() {
        Map<Integer, String> testMap = new HashMap<>();
        testMap.put(4, "to do LATER");
        testMap.put(1, "GO to the gym");
        testMap.put(2, "eat 5g of protein");
        testMap.put(3, "go on date with Julia");

        for (int i : testMap.keySet()) {
            Manager.updateTask(i, testMap.get(i));
        }

        for (int i : testMap.keySet()) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
                for (int k = 0; k < i; k++) {
                    reader.readLine();
                }
                String[] line = reader.readLine().split(",");
                Pattern descPattern = Pattern.compile("\"(description)\":\"(.+)\"");
                Matcher descMatcher = descPattern.matcher(line[1]);
                String taskDescription = "";
                if (descMatcher.find()) {
                    taskDescription = descMatcher.group(2);
                }
                Assertions.assertEquals(testMap.get(i), taskDescription);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Test
    void deleteTaskTest() {
        Manager.deleteTask(2);

        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
            for (int k = 0; k < 2; k++) {
                reader.readLine();
            }
            String[] line = reader.readLine().split(",");
            Pattern descPattern = Pattern.compile("\"(description)\":\"(.+)\"");
            Matcher descMatcher = descPattern.matcher(line[1]);

            String taskDescription = "";
            if (descMatcher.find()) {
                taskDescription = descMatcher.group(2);
            }

            if (taskDescription.equals("4th of July Preparations!!")) {
                Assertions.fail("The description should be \'to do later\'");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

   // @Test
    void listAllTasksTest() {
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Manager.listAllTasks();

        System.setOut(originalOut);

        String expectedOutput = "1 to do now" + "\n"
                + "2 4th of July Preparations!!" + "\n"
                + "3 to do later" + "\n"
                + "4 sOmething 2 do later" + "\n";
        Assertions.assertEquals(expectedOutput, outContent.toString());
    }
    /* 
    @Test
    void listDoneTasksTest() {

    }

    @Test
    void listTodoTasksTest() {

    }

    @Test
    void listInProgressTasksTest() {

    }

    @Test
    void markTaskAsInProgressTest() {

    }

    @Test
    void markTaskAsDoneTest() {

    }*/
}
