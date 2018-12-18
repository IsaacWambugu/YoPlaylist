package com.example.wambugu.YPlayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by wacuka on 27/10/2017.
 */

    public class intro extends AppIntro{
    private Intent intent;
     String LOG = "com.example.wacuka.a254yplaylist.";






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());


        //add fragments to the carousal
        addSlide(new com.example.wambugu.YPlayer.Fragments.IntroSlide2());
        addSlide(new com.example.wambugu.YPlayer.Fragments.IntroSlide2());
        addSlide(new com.example.wambugu.YPlayer.Fragments.IntroSlide3());

        //remove status bar
       // getSupportActionBar().hide();



    }

    @Override
    public void onSkipPressed(Fragment currentFragment) { //when skip button is pressed
        super.onSkipPressed(currentFragment);

        intent  = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);

        Log.d(LOG,"User pressed SKIP");





    }
    @Override
    public void onDonePressed(Fragment currentFragment) { //when the done button is pressed
        super.onDonePressed(currentFragment);
        intent  = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);

    }
}
