import java.io.*;
import java.util.*;

public class CSVStorage {
    private static final String FILE_NAME = "tasks.csv";

    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String text = parts[0].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    tasks.add(new Task(text, completed));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
