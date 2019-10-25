package com.example.sharedlib.Object;

import java.util.Calendar;
import java.util.TimeZone;

public class ObtainCurrentDate {
    private Calendar calendar;

    public static void main(String[] args) {
        ObtainCurrentDate obj = new ObtainCurrentDate();
        obj.getDateAndTime();
    }

    public String getDateAndTime() {
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+11:00"));

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String min = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        String date = format(day) + "/" + format(month) + "/" + year + " " + format(hour) + ":" + format(min) + ":" + format(second) + " ";
        return date;
    }

    public String format(String str) {
        String result = "";
        if (str.length() < 2) {
            result = "0" + str;
        } else
            result = str;
        return result;
    }
}
