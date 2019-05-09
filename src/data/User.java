package data;

public class User {

    private String nick;

    private int totalHints;
    public User(){

    }

    public boolean hasHint() {
        return totalHints > 0;
    }

    public void useHint() {
        totalHints--;
    }

    public void addHint() {
        totalHints += 10;
    }

    public int getHint() {
        return totalHints;
    }
    public void setHint(int totalHints) {
        this.totalHints = totalHints;
    }

    public String getNickName() {
        return nick;
    }

    public void setNickName(String nick) {
        this.nick = nick;
    }
}
