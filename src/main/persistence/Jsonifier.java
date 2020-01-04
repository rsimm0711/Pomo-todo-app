package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();

        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject duedateJson = new JSONObject();

        if (dueDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(dueDate.getDate());
            duedateJson.put("year", c.get(Calendar.YEAR));
            duedateJson.put("month", c.get(Calendar.MONTH));
            duedateJson.put("day", c.get(Calendar.DATE));
            duedateJson.put("hour", c.get(Calendar.HOUR));
            duedateJson.put("minute", c.get(Calendar.MINUTE));
            return duedateJson;
        }
        return null;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();

        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagListToJson(task));
        taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus());

        return taskJson;
    }

    // EFFECTS: returns a JSONArray of a task's tags
    public static JSONArray tagListToJson(Task task) {
        JSONArray tagsJson = new JSONArray();

        for (Tag t : task.getTags()) {
            tagsJson.put(tagToJson(t));
        }

        return tagsJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksJson = new JSONArray();

        for (Task t : tasks) {
            tasksJson.put(taskToJson(t));
        }
        return tasksJson;
    }

}
