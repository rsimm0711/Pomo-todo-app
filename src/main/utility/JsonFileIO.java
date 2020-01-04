package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //          returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {

        TaskParser parser = new TaskParser();

        try {
            FileReader fileReader = new FileReader(jsonDataFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String output = "";

            while ((line = bufferedReader.readLine()) != null) {

                output = output + "\r\n" + line;
            }

            fileReader.close();
            return parser.parse(output);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        } catch (IOException e) {
            System.out.println("Caught IO Exception.");
        }
        return null;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {

        TaskParser parser = new TaskParser();

        try {
            FileWriter fileWriter = new FileWriter(jsonDataFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            JSONArray array = Jsonifier.taskListToJson(tasks);
            bufferedWriter.write(array.toString());

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Caught IO Exception");
        }
    }
}
