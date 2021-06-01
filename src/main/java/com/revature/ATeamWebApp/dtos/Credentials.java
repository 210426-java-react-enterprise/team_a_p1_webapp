package com.revature.ATeamWebApp.dtos;

import com.revature.ATeamORM.annotations.Column;
import com.revature.ATeamORM.annotations.Entity;


@Entity(name ="appuser")
public class Credentials {
    
    @Column(name="username", unique = true)
    private String username;
    @Column(name="password")
    private String password;

    public Credentials() {
        super();
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

}
