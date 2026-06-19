import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskTracker {

    // what do I put as a variable in the Path.of(...)?
    private final Path FILE_PATH = Path.of("");
    private List<Task> tasks;

    public TaskTracker() {
        this.tasks = loadTasks();
    }

    private List<Task> loadTasks() {
        List<Task> storedTasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String jsonContent = Files.readString(FILE_PATH);
            String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");

            for (String taskJson : taskList) {
                if (!taskJson.endsWith("}")) {
                    taskJson = taskJson + "}";
                    storedTasks.add(Task.fromJson(taskJson));
                } else {
                    storedTasks.add(Task.fromJson(taskJson));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storedTasks;
    }
}
