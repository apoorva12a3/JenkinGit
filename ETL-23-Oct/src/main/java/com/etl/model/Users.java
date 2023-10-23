package com.etl.model;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // other fields, getters and setters

    public Users(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password; // Store the password as it is set in the SQL workbench
    }

    public Users() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
    }

}