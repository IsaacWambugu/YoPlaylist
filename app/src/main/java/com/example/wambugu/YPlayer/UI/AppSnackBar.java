package com.example.wambugu.YPlayer.UI;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by wacuka on 24/12/2017.
 */

public class AppSnackBar {
    private View view;
    private String message;
    private Snackbar snackbar ;


    public AppSnackBar(View view, String message){
        this.message  = message;
        this.view = view;


    }
    public void showSnackBar(){
        snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.YELLOW);


        snackbar.show();

    }
}
