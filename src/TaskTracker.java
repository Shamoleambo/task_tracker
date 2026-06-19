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
        return storedTasks;
    }
}
