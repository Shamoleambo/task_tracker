public class Task {

    private int id;
    private static int lastId = 0; //private variable to keep track of the last ID assigned
    private String description;
    private Status status;

    public Task(String description) {
        this.id = lastId++;
        this.description = description;
        this.status = Status.TODO;
    }

    public int getId() {
        return this.id;
    }

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public String toJson() {
        return "{\"id\":\"" + this.id + "\", \"description:\"" + this.description.strip() + "\", \"status\":\"" + this.status.toString() + "\"}";
    }


    public static Task fromJson(String json) {
        String[] json1 = json.replace("{", "").replace("}", "").replace("\"", "").replace(":", ",").split(",");

        String id = json1[1].strip();
        String description = json1[3];
        Status status;

        String s = json1[5].strip();

        if (s.equals("Todo")) {
            status = Status.TODO;
        } else if (s.equals("In progress")) {
            status = Status.IN_PROGRESS;
        } else {
            status = Status.DONE;
        }

        Task task = new Task(description);
        task.id = Integer.parseInt(id);
        task.status = status;

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
                this.status.toString();
    }
}
