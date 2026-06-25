import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private static int lastId = 0; //private variable to keep track of the last ID assigned
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String description) {
        this.id = lastId++;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return this.id;
    }

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void markDone() {
        this.status = Status.DONE;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String toJson() {
        return "{\"id\":\"" + this.id + "\", \"description:\"" + this.description.strip() +
                "\", \"status\":\"" + this.status.toString() + "\", \"createdAt\":\"" +
                this.createdAt.format(formatter) + "\", \"updatedAt\":\"" + this.updatedAt.format(formatter) + "\"}";
    }


    public static Task fromJson(String json) {
        String[] json1 = json.replace("{", "").replace("}", "").replace("\"", "").replace(":", ",").split(",");

        String id = json1[1].strip();
        String description = json1[3];
        String s = json1[5].strip();
        Status status = Status.valueOf(s.toUpperCase().replace(" ", "_"));
        String createdAtStr = json1[7];
        String updatedAtStr = json1[9];


        Task task = new Task(description);
        task.id = Integer.parseInt(id);
        task.status = status;
        task.createdAt = LocalDateTime.parse(createdAtStr, formatter);
        task.updatedAt = LocalDateTime.parse(updatedAtStr, formatter);

        if (Integer.parseInt(id) > lastId) {
            lastId = Integer.parseInt(id);
        }

        return task;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "; description: " + this.description.strip() + "; status: " +
                this.status.toString() + ", createdAt: " + this.createdAt.format(formatter) +
                ", updatedAt: " + this.updatedAt.format(formatter);
    }
}
