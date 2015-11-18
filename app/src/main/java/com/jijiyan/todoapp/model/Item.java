package com.jijiyan.todoapp.model;

/**
 * Created by yuandaxia on 2015/11/16.
 */
public class Item {
    private int id;
    private String name;
    private String note;
    private String description;

    public Item(String name, String note, String description) {
        this.name = name;
        this.note = note;
        this.description = description;
    }

    public Item() {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
