package com.example.todo.Modal;

public class Todo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String createdAt;
    String title;
    String description;

    public Todo() {
    }

    public Todo(String createdAt, String title, String description) {
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;

    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
