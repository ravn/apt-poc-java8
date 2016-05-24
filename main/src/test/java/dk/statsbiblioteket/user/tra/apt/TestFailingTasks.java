package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Task;
import org.junit.Test;

public class TestFailingTasks {

    Task crashingTask = i -> {
        throw new RuntimeException("Deliberately!");
    };

    @Test
    public void testSingleSuccessfulTask() {

    }
}
