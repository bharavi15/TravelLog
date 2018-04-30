package com.bharavi.travellog;

import java.io.Serializable;

/**
 * Created by bharavi on 28-12-2017.
 */

public class LogModel {
    int ID;
    String description;
    String date;
    String time;
    public LogModel(int i,String d,String t,String des)
    {
        ID=i;
        description=des;
        date=d;
        time=t;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
