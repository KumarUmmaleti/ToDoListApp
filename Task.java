public class Task {
    private String text;
    private boolean completed;

    public Task(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    public Task(String text) {
        this(text, false);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toCSV() {
        return "\"" + text.replace("\"", "\"\"") + "\"," + completed;
    }

    @Override
    public String toString() {
        return text;
    }
}
