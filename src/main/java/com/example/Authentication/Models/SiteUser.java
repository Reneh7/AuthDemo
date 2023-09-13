package com.example.Authentication.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Posts> posts;


    public SiteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public SiteUser() {

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

    public Long getId() {
        return id;
    }

    public List<Posts> getPosts() {
        return posts;
    }
}

