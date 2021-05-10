package com.schoolproject.MokedOded;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.Calendar;


class Singleton {
    private static Singleton instance = null;
    public int location;
    public int issue;
    public String description;
    public Date date;
    public String picURL;

    private Singleton(){
        location = 0;
        issue = 0;
        description = "";
        date = null;
        picURL = "";
    }

    public static Singleton getInstance(){
        if(instance==null){
            instance = new Singleton();
        }
        return instance;
    }
}
