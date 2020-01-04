package model.exceptions;

import model.Task;
import org.junit.jupiter.api.Test;

public class TestNegativeInputException {

    @Test
    public void testNegativeInputException() {
        Task t = new Task("test task ## today");
        try {
            t.setEstimatedTimeToComplete(-1);
        } catch (NegativeInputException e) {
            // expected
        }
    }
}
