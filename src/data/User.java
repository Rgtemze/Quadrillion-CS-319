package data;

public class User {

    private int userID;

    private int totalHints;

    private static User instance = new User();

    private User(){

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

}
