package com.example.sharedlib;

public class GroupInfo {
    private String key;
    private ComWithDatabase comment;

    public GroupInfo(String key, ComWithDatabase comment){
        this.key = key;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ComWithDatabase getComment() {
        return comment;
    }

    public void setComment(ComWithDatabase comment) {
        this.comment = comment;
    }
}
