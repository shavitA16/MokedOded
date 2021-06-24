package com.schoolproject.MokedOded;

import java.util.Date;

public class Issue {
    public String path;
    public int location;
    public int issue;
    public String description;
    public String date;
    public String imgURL;
    public String status;
    public String userEmail;

    public Issue(String path, int issue, int location, String description, String date, String imgURL, String status, String userEmail){
        this.path = path;
        this.issue = issue;
        this.location = location;
        this.description = description;
        this.date = date;
        this.imgURL = imgURL;
        this.status = status;
        this.userEmail = userEmail;
    }
}
