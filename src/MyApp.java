public class MyApp {
    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTracker();
        taskTracker.listTasks("In progress");
        taskTracker.quit();
    }
}
