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

    private void saveTasks() throws IOException {
        if (!Files.exists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        String jsonContent = sb.toString();
        Files.writeString(FILE_PATH, jsonContent);
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        this.tasks.add(newTask);
    }

    public void updateTask(String id, String newDescription) {
        Task task = findTask(id);
        task.updateDescription(newDescription);
    }

    public void deleteTask(String id) {
        Task task = findTask(id);
        tasks.remove(task);
    }

    public Task findTask(String id) throws IllegalArgumentException {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task wit ID " + id + " not found");
    }
}
