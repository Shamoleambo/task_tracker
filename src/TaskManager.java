import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private final Path FILE_PATH = Path.of("/home/tidz/program/task_tracker/tasks.json");
    private List<Task> tasks;

    public TaskManager() {
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

    public void saveTasks() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < this.tasks.size(); i++) {
            sb.append(this.tasks.get(i).toJson());
            if (i < this.tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        String jsonContent = sb.toString();
        try {
            Files.writeString(FILE_PATH, jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        this.tasks.add(newTask);
        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    }

    public void updateTask(String id, String newDescription) {
        Task task = findTask(id);
        task.updateDescription(newDescription);
    }

    public void deleteTask(String id) {
        Task task = findTask(id);
        this.tasks.remove(task);
    }

    public void markInProgress(String id) {
        Task task = findTask(id);
        task.markInProgress();
    }

    public void markDone(String id) {
        Task task = findTask(id);
        task.markDone();
    }

    public void listTasks(String type) {
        for (Task task : tasks) {
            String status = task.getStatus().toString().strip();
            if (type.equals("All") || status.equals(type)) System.out.println(task.toString());
        }
    }

    public Task findTask(String id) throws IllegalArgumentException {
        for (Task task : tasks) {
            if (task.getId() == Integer.parseInt(id)) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task wit ID " + id + " not found");
    }
}
