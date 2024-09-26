/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */

/**
 *
 * @author kunle
 */
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.bskhq.tasktrackercli.Task;

public class Tasktest {
    //tests the task class

    private Task task;
    private Random random;

    public Tasktest() {
        this.random = new Random();
        int randomInt = random.nextInt(100);

        this.task = new Task("Sample task", randomInt);
    }

    @Test
    void updatingTaskDescriptionWorks() {
        this.task.update("newTaskDescription");
        assertEquals("newTaskDescription", this.task.description());
    }

    @Test
    void theNewUpdateTimeWasRecorded() {
        this.task.update("newTaskDescription");
        assertNotEquals(this.task.getTimeCreatedAt(), this.task.getLastUpdatedTime());
    }

}
