package model.exceptions;

import model.Task;
import org.junit.jupiter.api.Test;

public class TestInvalidProgressException {

    @Test
    public void testInvalidProgressException() {
        Task t = new Task("test task ## today");
        try {
            t.setProgress(1000);
        } catch (InvalidProgressException e) {
            //expected
        }
    }
}
