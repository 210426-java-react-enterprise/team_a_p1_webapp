package com.revature.ATeamWebApp.models;

import com.revature.ATeamORM.util.annotations.Column;
import com.revature.ATeamORM.util.annotations.Entity;
import com.revature.ATeamORM.util.annotations.Id;


/*
    Classes must be named the exact same as the file itself!

    Class names should (for best practice) be in PascalCase
        - not to be confused with camelCase

    POJO = Plain Ol' Java Object
        - Does not (usually) contain any methods beyond simple getters and setters
            + maybe the occasional convenience method
 */
@Entity(name="appuser")
public class AppUser {
    
    @Id
    @Column(name="user_id")
    private int id;
    @Column(name="username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="first_name")
    private String firstName; // variables should be in camelCase
    @Column(name="last_name")
    private String lastName;
    @Column(name ="age")
    private int age;

    public AppUser() {
        super();
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}