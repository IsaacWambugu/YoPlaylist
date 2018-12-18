package com.example.wambugu.YPlayer.Utility;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by wacuka on 30/10/2017.
 */

public class TimeStamp{

    public String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-ddd HH:mm:ss", Locale.getDefault());
        return ft.format(date);
    }

}
