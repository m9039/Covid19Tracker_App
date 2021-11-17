package au.edu.unsw.infs3634.covid19tracker;

public class User {

    public String fullname, username, email;

    public User(){

    }

    public User(String fullname, String username, String email) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
    }
}
