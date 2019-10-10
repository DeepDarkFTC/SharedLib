package com.example.sharedlib;

import java.util.ArrayList;

public class ComWithDatabase {

    private String id;
    private String user;
    private String comment;
    private String date;
    private String groupName;
    private String libraryName;
    private String libraryLevel;
    private String studyTopic;
    private String startTime;
    private String endTime;
    private String teamLeader;




    public ComWithDatabase() {
    }

    public ComWithDatabase(String user, String comment, String date) {
        this.user = user;
        this.comment = comment;
        this.date = date;

    }

    public ComWithDatabase(String comment, String date) {
        this.comment = comment;
        this.date = date;

    }

    public ComWithDatabase(String groupName, String libraryName,
                           String libraryLevel, String studyTopic,
                           String startTime, String endTime, String teamLeader){
        this.groupName = groupName;
        this.libraryName = libraryName;
        this.libraryLevel = libraryLevel;
        this.studyTopic = studyTopic;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teamLeader = teamLeader;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryLevel() {
        return libraryLevel;
    }

    public void setLibraryLevel(String libraryLevel) {
        this.libraryLevel = libraryLevel;
    }

    public String getStudyTopic() {
        return studyTopic;
    }

    public void setStudyTopic(String studyTopic) {
        this.studyTopic = studyTopic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
}
