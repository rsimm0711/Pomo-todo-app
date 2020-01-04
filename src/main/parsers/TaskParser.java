package parsers;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    List<Task> tasks = new ArrayList<>();

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {


        JSONArray j = new JSONArray(input);
        for (Object o : j) {
            JSONObject obj = new JSONObject(o.toString());
            Task t = new Task(obj.get("description").toString());
            t.setStatus(Status.valueOf(obj.getString("status")));
            t.setPriority(parsePriority(obj));

            if (obj.optJSONObject("due-date") == null) {
                t.setDueDate(null);
            } else {
                t.setDueDate(parseDueDate(obj));
            }

            for (Tag tag : parseTags(obj)) {
                t.addTag(tag);
            }

            tasks.add(t);
        }
        return tasks;
    }

    public List<Tag> parseTags(JSONObject j) {
        List<Tag> tags = new ArrayList<>();
        for (Object obj : j.getJSONArray("tags")) {
            JSONObject json = new JSONObject(obj.toString());
            Tag t = new Tag(json.getString("name"));
            tags.add(t);
        }

        return tags;
    }

    public DueDate parseDueDate(JSONObject j) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, j.optJSONObject("due-date").getInt("year"));
        c.set(Calendar.MONTH, j.optJSONObject("due-date").getInt("month"));
        c.set(Calendar.DAY_OF_MONTH,
                j.optJSONObject("due-date").getInt("day"));
        c.set(Calendar.HOUR, j.optJSONObject("due-date").getInt("hour"));
        c.set(Calendar.MINUTE, j.optJSONObject("due-date").getInt("minute"));

        DueDate dd = new DueDate();
        dd.setDueDate(c.getTime());
        return dd;
    }


    public Priority parsePriority(JSONObject j) {
        Priority p = new Priority();
        p.setImportant(j.getJSONObject("priority").getBoolean("important"));
        p.setUrgent(j.getJSONObject("priority").getBoolean("urgent"));

        return p;
    }


}
