package com.example.sharedlib;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

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
    private String thumbNumber;



    public ComWithDatabase() {
    }

    public ComWithDatabase(String user, String comment, String date, String thumbNumber) {
        this.user = user;
        this.comment = comment;
        this.date = date;
        this.thumbNumber = thumbNumber;
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

    public ComWithDatabase(String id, String groupName, String libraryName,
                           String libraryLevel, String studyTopic,
                           String startTime, String endTime, String teamLeader){
        this.id = id;
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

    public String getThumbNumber() {
        return thumbNumber;
    }

    public void setThumbNumber(String thumbNumber) {
        this.thumbNumber = thumbNumber;
    }
}
