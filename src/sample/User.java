package sample;

public class User {
    private int totalHints;

    public boolean hasHint() {
        return totalHints > 0;
    }

    public void useHint() {
        totalHints--;
    }

    public void addHint() {
        totalHints++;
    }

}
