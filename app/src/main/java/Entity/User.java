package Entity;

public class User {
    String email;
    Boolean admin=false;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isAdmin() { return admin; }
}
