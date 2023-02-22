package org.nurullah.model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String email;
    private Date createdAt;

    public User() {
    }

    public User(String name, String email, Date createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "|UserId: " + getId() +
                "|UserName: " + getName() +
                "|UserEmail: " + getEmail() +
                "|CreatedAt: " + getCreatedAt() + "|";
    }
}
