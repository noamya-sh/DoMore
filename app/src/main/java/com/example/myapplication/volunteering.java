package com.example.myapplication;

import java.util.Date;

public class volunteering {

    String association, title, location;
    Date start_date,end_date;
    int number_vol, number_vol_left;

    public volunteering(String association, String title, String location, Date start_date, Date end_date, int number_vol, int number_vol_left) {
        this.association = association;
        this.title = title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.number_vol = number_vol;
        this.number_vol_left = number_vol_left;
    }
}
