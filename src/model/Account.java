package model;

public class Account {
    private String username;
    private  String password;
    private  String fullName;
    private int point;
    private int time;
    private int indexWord;
    private int level;

    public Account(String username, String password, String fullName, int point, int time, int indexWord, int level){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.point = point;
        this.time = time;
        this.indexWord = indexWord;
        this.level = level;
    }

    public int getIndexWord() {
        return indexWord;
    }

    public int getLevel() {
        return level;
    }

    public String getFullName() {

        return fullName;
    }

    public int getPoint() {
        return point;
    }

    public int getTime() {
        return time;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }

}
