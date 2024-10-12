/*
 *  This file is licensed under the MIT License - see the LICENSE file for details
 */
package com.bskhq.tasktrackercli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class fileWriter {

    /* this class handles reading and writing from and to the JSON file */
    /*
     * Since Java doesn't have any internal libraries for handling JSON and we
     * aren't allowed to
     * import any external libraries, we'll just have to implement one :)
     */

    private final static String PATH = System.getProperty("user.home") + File.separator + "tasktrackercli";
    private final static String FILE_NAME = PATH + File.separator + "taskstore.json";

    private fileWriter() {
    }

    private static void mkDirIfNotExists() {
        File fileDir = new File(PATH);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }

    public static void writeToFile(Task task) {
        // Writes given task to file
        mkDirIfNotExists();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE)) {

            if (Files.size(Path.of(FILE_NAME)) == 0) {
                writer.write("[]");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {

            if (file.length() >= 3) {
                file.seek(file.length() - 3);
            }

            if (file.readByte() == '}') {
                file.write(",".getBytes(StandardCharsets.UTF_8));
            }

            file.write("\n".getBytes(StandardCharsets.UTF_8));

            String toBeWritten = "{" + "\"id\":" + "\"" + task.getId() + "\","
                    + "\"description\":" + "\"" + task.description() + "\","
                    + "\"status\":" + "\"" + task.getStatus().toString() + "\","
                    + "\"createdAt\":" + "\"" + task.getTimeCreatedAt().truncatedTo(ChronoUnit.SECONDS) + "\","
                    + "\"updatedAt\":" + "\"" + task.getLastUpdatedTime().truncatedTo(ChronoUnit.SECONDS) + "\"" + "}";

            file.write(toBeWritten.getBytes(StandardCharsets.UTF_8));
            file.write("\n".getBytes(StandardCharsets.UTF_8));
            file.write("]".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<Integer, Task> JSONtoHashmap() {
        mkDirIfNotExists();
        Map<Integer, Task> jsonMap = new HashMap<>();

        File fl = new File(FILE_NAME);
        if (!fl.exists()) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE)) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
            reader.lines().forEach(line -> {

                if (line.length() > 2) { // make sure we're not dealing with lines containing [ or ]
                    int taskId = 0;
                    String taskDescription = "";
                    Status taskStatus = null;
                    LocalDateTime taskCreatedAt = null;
                    LocalDateTime taskUpdatedAt = null;

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

                    Pattern statusPattern = Pattern.compile("[A-Z]+");
                    Matcher statusMatcher = statusPattern.matcher(sgs[2]);

                    if (statusMatcher.find()) {
                        taskStatus = Status.valueOf(statusMatcher.group());
                    }

                    Pattern timePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
                    Matcher timeMatcher = timePattern.matcher(sgs[3]);

                    if (timeMatcher.find()) {
                        taskCreatedAt = LocalDateTime.parse(timeMatcher.group(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }

                    timeMatcher = timePattern.matcher(sgs[4]);

                    if (timeMatcher.find()) {
                        taskUpdatedAt = LocalDateTime.parse(timeMatcher.group(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }
                    // finally, remake the task :)
                    Task remadeTask = new Task(taskDescription, taskId, taskStatus, taskCreatedAt, taskUpdatedAt);
                    jsonMap.put(taskId, remadeTask);

                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonMap;
    }

    public static void HashMapToJson(Map<Integer, Task> hashmap) {
        mkDirIfNotExists();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME), StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task t : hashmap.values()) {
                writeToFile(t);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
