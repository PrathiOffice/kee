package com.kee.recycler_view;
import android.graphics.Color;

public class DateModel {
    private String date;
    private int backgroundColor;

    public DateModel(String date, int backgroundColor) {
        this.date = date;
        this.backgroundColor = backgroundColor;
    }

    public String getDate() {
        return date;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
