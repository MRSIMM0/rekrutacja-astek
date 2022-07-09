package src.Models.User;

public class User {

    private Role role;

    public User(Role role) {

        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
