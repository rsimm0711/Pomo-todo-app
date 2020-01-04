package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.Test;

import static model.Status.DONE;
import static model.Status.TODO;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestProject {


    @Test
    public void testProjectConstructorNoException() {

        try {
            Project p = new Project("test");
            assertEquals("test", p.getDescription());
            assertEquals(0, p.getProgress());
        } catch (EmptyStringException e) {
            fail("Threw EmptyStringException when shouldn't have");
        }
    }

    @Test
    public void testProjectConstructorEmptyStringException() {

        try {
            Project p = new Project("");
            assertEquals("", p.getDescription());
        } catch (EmptyStringException e) {
            //expected behavior
        }
    }

    @Test
    public void testProjectConstructorNullEmptyStringException() {

        try {
            Project p = new Project(null);
            assertEquals(null, p.getDescription());
        } catch (EmptyStringException e) {
            //expected behavior
        }
    }

    @Test
    public void testAddProjectToItself() {
        Project p1 = new Project("test");
        p1.add(p1);
        assertFalse(p1.contains(p1));
    }

    @Test
    public void testAddTaskNoException() {
        try {
            Project p = new Project("test");
            Task t = new Task("test your methods ## test");
            p.add(t);
            assertTrue(p.tasks.contains(t));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testAddTaskParsingException() {
        try {
            Project p = new Project("test");
            Task t = new Task("test your methods test");
            p.add(t);
            assertTrue(p.tasks.contains(t));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testAddTaskNullArgumentException() {

        try {
            Project p = new Project("test");
            p.add(null);
            assertTrue(p.tasks.contains(null));
        } catch (NullArgumentException e) {
            //expected behavior
        }
    }

    @Test
    public void testRemoveTaskNoException() {

        try {
            Project p = new Project("test");
            Task t = new Task("test your methods ## test");
            p.add(t);
            p.remove(t);
            assertFalse(p.tasks.contains(t));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testRemoveTaskParsingException() {
        try {
            Project p = new Project("test");
            Task t = new Task("test your methods test");
            p.remove(t);
            assertFalse(p.tasks.contains(t));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testRemoveTaskNullArgumentException() {

        try {
            Project p = new Project("test");
            p.remove(null);
            assertFalse(p.tasks.contains(null));
        } catch (NullArgumentException e) {
            //expected behavior
        }
    }

    @Test
    public void testGetDescription() {
        Project p = new Project("test");

        assertEquals("test", p.getDescription());
    }



    @Test
    public void testGetEstimatedTimeToCompleteNoTasks() {
        Project p = new Project("test");
        assertEquals(0, p.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetEstimatedTimeToCompleteMultipleTasks() {
        Project p = new Project("test");
        Task t1 = new Task("first task ## today");
        t1.setEstimatedTimeToComplete(10);
        Task t2 = new Task("second task ## today");
        t2.setEstimatedTimeToComplete(5);
        Task t3 = new Task("third task ## today");
        t3.setEstimatedTimeToComplete(7);

        p.add(t1);
        p.add(t2);
        p.add(t3);

        assertEquals(22, p.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetEstimatedTimeToCompleteTasksAndProjects() {
        Project p1 = new Project("test1");
        Project p2 = new Project("test2");
        Project p3 = new Project("test3");
        Task t1 = new Task("first task ## today");
        t1.setEstimatedTimeToComplete(10);
        Task t2 = new Task("second task ## today");
        t2.setEstimatedTimeToComplete(5);
        Task t3 = new Task("third task ## today");
        t3.setEstimatedTimeToComplete(2);
        Task t4 = new Task("fourth task ## today");
        t4.setEstimatedTimeToComplete(4);
        Task t5 = new Task("fifth task ## today");
        t5.setEstimatedTimeToComplete(1);

        p1.add(t1);
        p1.add(t2);
        p2.add(t3);
        p2.add(t4);
        p3.add(t5);
        p2.add(p3);
        p1.add(p2);

        assertEquals(22, p1.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetProgressProjectsAndTasks() {
        Project p1 = new Project("test1");
        Project p2 = new Project("test2");
        Project p3 = new Project("test3");
        Task t1 = new Task("first task ## today");
        t1.setProgress(100);
        Task t2 = new Task("second task ## today");
        t2.setProgress(50);
        Task t3 = new Task("third task ## today");
        t3.setProgress(25);
        Task t4 = new Task("fourth task ## today");
        t4.setProgress(33);
        Task t5 = new Task("fifth task ## today");
        t5.setProgress(66);

        p1.add(t1);
        p1.add(t2);
        p1.add(p2);
        p2.add(t3);
        p2.add(t4);
        p1.add(p3);
        p3.add(t5);

        assertEquals(61, p1.getProgress());
    }

    @Test
    public void testGetProgressSomeDone() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        t1.setProgress(100);
        Task t2 = new Task("implement your methods ## test");
        t2.setProgress(100);
        Task t3 = new Task("commit and push your code ## test");
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(66, p.getProgress());

    }

    @Test
    public void testGetProgressAllDone() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        t1.setProgress(100);
        Task t2 = new Task("implement your methods ## test");
        t2.setProgress(100);
        p.add(t1);
        p.add(t2);
        assertEquals(100, p.getProgress());

    }

    @Test
    public void testGetProgressVariedProgress() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        t1.setProgress(100);
        Task t2 = new Task("implement your methods ## test");
        t2.setProgress(50);
        Task t3 = new Task("debug your tests ## test");
        t3.setProgress(25);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(58, p.getProgress());
    }

    @Test
    public void testGetProgressNoneDone() {

        Project p = new Project("test ## test");
        Task t1 = new Task("test your methods ## test");
        Task t2 = new Task("implement your methods ## test");
        p.add(t1);
        p.add(t2);
        assertEquals(0, p.getProgress());

    }

    @Test
    public void testGetProgressParsingException() {
        Project p = new Project("test test");
        Task t1 = new Task("test your methods test");
        Task t2 = new Task("implement your methods test");
        p.add(t1);
        p.add(t2);
        assertEquals(0, p.getProgress());
    }

    @Test
    public void testGetProgressNoTasks() {
        Project p = new Project("test");

        assertEquals(0, p.getProgress());
    }


    @Test
    public void testGetNumberofTasks() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        Task t2 = new Task("implement your methods ## test");
        Task t3 = new Task("commit and push your code ## test");
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(3, p.getNumberOfTasks());
    }

    @Test
    public void testGetNumberofTasksParsingException() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods");
        Task t2 = new Task("implement your methods");
        Task t3 = new Task("commit and push your code");
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(3, p.getNumberOfTasks());

    }


    @Test
    public void testIsCompletedTrue() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        Task t2 = new Task("implement your methods ## test");
        p.add(t1);
        p.add(t2);
        t1.setProgress(100);
        t2.setProgress(100);
        assertTrue(p.isCompleted());

    }

    @Test
    public void testIsCompletedFalse() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods ## test");
        Task t2 = new Task("implement your methods ## test");
        p.add(t1);
        p.add(t2);
        t1.setProgress(100);
        t2.setProgress(50);
        assertFalse(p.isCompleted());
    }

    @Test
    public void testIsCompletedParsingException() {
        Project p = new Project("test");
        Task t1 = new Task("test your methods");
        Task t2 = new Task("implement your methods");
        p.add(t1);
        p.add(t2);
        t1.setStatus(DONE);
        t2.setStatus(TODO);
        assertFalse(p.isCompleted());
    }

    @Test
    public void testContainsTrueNoException() {

        try {
            Project p = new Project("test");
            Task t1 = new Task("test your methods ## test");
            Task t2 = new Task("implement your methods ## test");
            p.add(t1);
            p.add(t2);
            assertTrue(p.contains(t2));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testContainsFalseNoException() {

        try {
            Project p = new Project("test");
            Task t1 = new Task("test your methods ## test");
            Task t2 = new Task("implement your methods ## test");
            p.add(t1);
            assertFalse(p.contains(t2));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testContainsTrueNullArgumentException() {

        try {
            Project p = new Project("test");
            p.add(null);
            assertTrue(p.contains(null));
        } catch (NullArgumentException e) {
            // expected behavior
        }
    }

    @Test
    public void testContainsParsingException() {
        try {
            Project p = new Project("test");
            Task t = new Task("test test test");
            p.add(t);
            assertTrue(p.contains(t));
        } catch (NullArgumentException e) {
            fail("Threw NullArgumentException when shouldn't have");
        }
    }

    @Test
    public void testContainsFalseNullArgumentException() {

        try {
            Project p = new Project("test");
            assertFalse(p.contains(null));
        } catch (NullArgumentException e) {
            //expected behavior
        }
    }

    @Test
    public void testEqualsExactSameObject() {
        try {
            Project p = new Project("test project");
            assertTrue(p.equals(p));
        } catch (NullArgumentException e) {
            fail("Threw exception when shouldn't have");
        }

    }

    @Test
    public void testEqualsDifferentClasses() {
        try {
           Project p = new Project("test project");
           Tag t = new Tag("test project");
           assertFalse(p.equals(t));
        } catch (NullArgumentException e) {
            fail("Threw exception when shouldn't have");
        }

    }

    @Test
    public void testEqualsSameDescription() {
        try {
           Project p1 = new Project("test project");
           Project p2 = new Project("test project");
           assertTrue(p1.equals(p2));
        } catch (NullArgumentException e) {
            fail("Threw exception when shouldn't have");
        }
    }

    @Test
    public void testHashCode() {
        Project p1 = new Project("test project");
        Project p2 = new Project("test project");
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testPrintProjectPrioritiesManyTasks() {
        Project p1 = new Project("test1");
        p1.setPriority(new Priority(3));
        Project p2 = new Project("test2");
        p2.setPriority(new Priority(2));
        Project p3 = new Project("test3");
        p3.setPriority(new Priority(4));
        Task t1 = new Task("1. you can print multiple tasks of the same priority! ## today");
        t1.setPriority(new Priority(1));
        Task t2 = new Task("test task 2 ## today");
        t2.setPriority(new Priority(3));
        Task t3 = new Task("test task 3 ## today");
        t3.setPriority(new Priority(4));
        Task t5 = new Task("test task 5 ## today");
        t5.setPriority(new Priority(1));
        Task t4 = new Task("test task 4 ## today");
        t4.setPriority(new Priority(1));

        p1.add(p2);
        p1.add(p3);
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        p2.add(t4);
        p1.add(t5);

        p1.printProjectPriorities();
    }

    @Test
    public void testPrintProjectPrioritiesNoPriorityOneTasks() {
        Project p1 = new Project("test1");
        p1.setPriority(new Priority(3));
        Project p2 = new Project("test2");
        p2.setPriority(new Priority(2));
        Project p3 = new Project("test3");
        p3.setPriority(new Priority(4));

        p1.add(p2);
        p1.add(p3);

        p1.printProjectPriorities();
    }

    @Test
    public void testPrintProjectPrioritiesNoTasks() {
        Project p1 = new Project("test");

        p1.printProjectPriorities();
    }
}
