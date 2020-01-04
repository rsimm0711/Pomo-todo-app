package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTask {

    @Test
    public void testTaskConstructorEmptyStringDescription() {
        try {
            Task t = new Task("");
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testTaskConstructorNullDescription() {
        try {
            Task t = new Task(null);
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testTaskConstructor() {
        DueDate dd = new DueDate();
        Task t = new Task("test task ## todo; urgent; today");
        assertEquals("test task ", t.getDescription());
        assertEquals(new Priority(3), t.getPriority());
        assertEquals(dd, t.getDueDate());
        assertEquals(Status.TODO, t.getStatus());
        assertEquals(0, t.getProgress());
        assertEquals(0, t.getEstimatedTimeToComplete());
    }

    @Test
    public void testTaskEqualsNoTags() {
        Task t = new Task("test task ## todo; urgent; today");
        assertEquals(t, new Task("test task ## todo; urgent; today"));
    }

    @Test
    public void testTaskEqualsWithDifferentTags() {
        Task t = new Task("cpsc homework ## tomorrow; term project; up next; important");
        assertEquals(t, new Task("cpsc homework ## tomorrow; coding exercise; up next; important"));
    }

    @Test
    public void testTaskEqualsMissingDetails() {
        Task t = new Task("math homework ## team project; done");
        assertEquals(t, new Task("math homework ## team project; done"));
    }

    @Test
    public void testAddTagNullException() {
        Task t = new Task("test task ## today; fun");
        Tag tag = null;
        try {
            t.addTag(tag);
        } catch (NullArgumentException e) {
            // expected
        }

    }

    @Test
    public void testAddTagAlreadyThere() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("fun");
        t.addTag(tag);
        t.addTag(tag);
        t.removeTag(tag);
        assertFalse(t.containsTag(tag));
    }

    @Test
    public void testAddTagSuccessfully() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("fun");
        t.addTag(tag);
        assertTrue(t.containsTag(tag));
    }

    @Test
    public void testAddTagManyTags() {
        Task t = new Task("test task ## today; fun");
        Tag one = new Tag("fun");
        Tag two = new Tag("boring");
        Tag three = new Tag("okay");
        t.addTag(one);
        t.addTag(two);
        t.addTag(three);
        assertTrue(t.containsTag(one));
        assertTrue(t.containsTag(two));
        assertTrue(t.containsTag(three));
    }

    @Test
    public void testRemoveTagNullExceptionTAG() {
        Task t = new Task("test task ## today; fun");
        Tag tag = null;
        try {
            t.removeTag(tag);
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTagNotThereTAG() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("test tag");

        assertFalse(t.containsTag(tag));
        t.removeTag(tag);
        assertFalse(t.containsTag(tag));
        assertFalse(tag.containsTask(t));
    }

    @Test
    public void testRemoveTagSuccessfullyTAG() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("test tag");

        t.addTag(tag);
        assertTrue(t.containsTag(tag));
        assertTrue(tag.containsTask(t));
        t.removeTag(tag);
        assertFalse(t.containsTag(tag));
        assertFalse(tag.containsTask(t));

    }

    @Test
    public void testRemoveTagNotThereSTRING() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("not fun");
        t.removeTag("not fun");
        assertFalse(t.containsTag("not fun"));
    }

    @Test
    public void testRemoveTagSuccessfullySTRING() {
        Task t = new Task("test task ## today; fun");
        Tag tag = new Tag("not fun");
        t.addTag("not fun");
        assertTrue(t.containsTag("not fun"));
        t.removeTag("not fun");
        assertFalse(t.containsTag("not fun"));
    }

    @Test
    public void testRemoveTagManyTagsSTRING() {
        Task t = new Task("test task ## today; fun");
        Tag one = new Tag("fun");
        Tag two = new Tag("boring");
        Tag three = new Tag("okay");
        t.addTag("fun");
        t.addTag("boring");
        t.addTag("okay");
        assertTrue(t.containsTag("fun"));
        assertTrue(t.containsTag("boring"));
        assertTrue(t.containsTag("okay"));
        t.removeTag("fun");
        t.removeTag("boring");
        t.removeTag("okay");
        assertFalse(t.containsTag(one));
        assertFalse(t.containsTag(two));
        assertFalse(t.containsTag(three));
    }

    @Test
    public void testGetTags() {
        Task t = new Task("test task ## fun; boring");
        Tag fun = new Tag("fun");
        Tag boring = new Tag("boring");
        HashSet<Tag> tags = new HashSet<>();
        tags.add(fun);
        tags.add(boring);

        assertEquals(tags, t.getTags());
    }

    @Test
    public void testGetPriority() {
        Task t = new Task("test task ## important");

        assertEquals(new Priority(2), t.getPriority());
    }

    @Test
    public void testSetPriorityNull() {
        Task t = new Task("test task ## important");
        try {
            t.setPriority(null);
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetPrioritySuccessful() {
        Task t = new Task("test task ## important");
        try {
            t.setPriority(new Priority(4));
            assertEquals(new Priority(4), t.getPriority());
        } catch (NullArgumentException e) {
            fail("Threw exception when shouldn't have");
        }
    }

    @Test
    public void testSetStatusNull() {

        try {
            Task t = new Task("test task ## today");
            t.setStatus(null);
            fail("Should have thrown NullArgumentException");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testGetDescription() {
        Task t = new Task("test task ## today");

        assertEquals("test task ", t.getDescription());
    }

    @Test
    public void testSetDescriptionEmpty() {
        Task t = new Task("test task ## today");

        try {
            t.setDescription("");
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testSetDescriptionNull() {
        Task t = new Task("test task ## today");

        try {
            t.setDescription(null);
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testGetDueDate() {
        Task t = new Task("test task ## today");
        DueDate dd = new DueDate();

        assertEquals(dd, t.getDueDate());
    }

    @Test
    public void testSetDueDate() {
        Task t = new Task("test task ## tomorrow");
        DueDate tomorrow = new DueDate();
        tomorrow.postponeOneDay();

        assertEquals(tomorrow, t.getDueDate());
        t.setDueDate(new DueDate());
        assertEquals(new DueDate(), t.getDueDate());
    }

    @Test
    public void testContainsTagEmptySTRING() {
        Task t = new Task("test task ## tomorrow");

        try {
            t.containsTag("");
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testContainsTagNullSTRING() {
        Task t = new Task("test task ## tomorrow");

        try {
            t.containsTag((String) null);
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testContainsTagTrueSTRING() {
        Task t = new Task("test task ## fun");

        assertTrue(t.containsTag("fun"));
    }

    @Test
    public void testContainsTagFalseSTRING() {
        Task t = new Task("test task ## fun");

        assertFalse(t.containsTag("boring"));
    }

    @Test
    public void testContainsTagNullTAG() {
        Task t = new Task("test task ## tomorrow");

        try {
            t.containsTag((Tag) null);
            fail("Should have thrown NullArgumentException");
        } catch (NullArgumentException e) {
            //expected
        }
    }

    @Test
    public void testToString() {
        Task t = new Task("test task ## tomorrow; done; urgent; cpsc");

        System.out.println(t.toString());
    }


    @Test
    public void testEqualsDifferentClass() {
        Task t = new Task("test task ## fun; math; easy");
        Tag tag = new Tag("test");

        assertFalse(t.equals(tag));

    }

    @Test
    public void testEqualsSameDetails() {
        Task t1 = new Task("test task ## fun; math; easy");
        Task t2 = new Task("test task ## fun; math; easy");

        assertTrue(t1.equals(t2));
    }

    @Test
    public void testGetProgress() {
        Task t = new Task("test task ## fun; math; easy");
        assertEquals(0, t.getProgress());
    }

    @Test
    public void testSetProgress() {
        Task t = new Task("test task ## fun; math; easy");
        try {
            t.setProgress(50);
            assertEquals(50, t.getProgress());
        } catch (InvalidProgressException e) {
            fail("Threw InvalidProgressException when shouldn't have");
        }
    }

    @Test
    public void testSetProgressInvalidProgressExceptionUpperBound() {
        Task t = new Task("test task ## fun; math; easy");

        try {
            t.setProgress(101);
            fail("Should have thrown InvalidProgressException");
        } catch (InvalidProgressException e) {
            //expected
        }
    }

    @Test
    public void testSetProgressInvalidProgressExceptionLowerBound() {
        Task t = new Task("test task ## fun; math; easy");

        try {
            t.setProgress(-1);
            fail("Should have thrown InvalidProgressException");
        } catch (InvalidProgressException e) {
            //expected
        }
    }

    @Test
    public void testSetProgressInvalidProgressExceptionReallyHigh() {
        Task t = new Task("test task ## fun; math; easy");

        try {
            t.setProgress(1000);
            fail("Should have thrown InvalidProgressException");
        } catch (InvalidProgressException e) {
            //expected
        }
    }

    @Test
    public void testSetProgressInvalidProgressExceptionReallyLow() {
        Task t = new Task("test task ## fun; math; easy");

        try {
            t.setProgress(-1000);
            fail("Should have thrown InvalidProgressException");
        } catch (InvalidProgressException e) {
            //expected
        }
    }

    @Test
    public void testGetEstimatedTimeToComplete() {
        Task t = new Task("test task ## fun; math; easy");
        assertEquals(0, t.getEstimatedTimeToComplete());
    }

    @Test
    public void testSetEstimatedTimeToComplete() {
        Task t = new Task("test task ## fun; math; easy");
        try {
            t.setEstimatedTimeToComplete(5);
            assertEquals(5, t.getEstimatedTimeToComplete());
        } catch (NegativeInputException e) {
            fail("Threw NegativeInputException when shouldn't have");
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteLowerBound() {
        Task t = new Task("test task ## fun; math; easy");
        try {
            t.setEstimatedTimeToComplete(-1);
            fail("Should have thrown NegativeInputException");
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteReallyLow() {
        Task t = new Task("test task ## fun; math; easy");
        try {
            t.setEstimatedTimeToComplete(-1000);
            fail("Should have thrown NegativeInputException");
        } catch (NegativeInputException e) {
            // expected
        }
    }

}
