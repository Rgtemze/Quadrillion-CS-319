package data;

public class User {

    private String nick;

    private int totalHints;

    private static User instance = new User();

    private User(){
        nick = "Rgtemze";
    }

    public static User getInstance(){
        return instance;
    }

    public boolean hasHint() {
        return totalHints > 0;
    }

    public void useHint() {
        totalHints--;
    }

    public void addHint() {
        totalHints++;
    }

    public int getHint() {
        return totalHints;
    }

    public String getNickName() {
        return nick;
    }
}
