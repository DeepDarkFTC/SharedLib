package com.example.sharedlib;

public class SearchSeatsComment {

    private String id;
    private String user;
    private String comment;
    private String date;


    public SearchSeatsComment() {
    }

    public SearchSeatsComment(String user, String comment, String date) {
        this.user = user;
        this.comment = comment;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDate() {
        return date;
    }

}
