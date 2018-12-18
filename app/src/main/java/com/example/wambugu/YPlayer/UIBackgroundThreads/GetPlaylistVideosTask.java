package com.example.wambugu.YPlayer.UIBackgroundThreads;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.Utility.JSONExtractor;
import com.example.wambugu.YPlayer.activities.ViewPlaylistVideos;
import com.example.wambugu.YPlayer.interfaces.GetPlaylistVideosResults;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;

/**
 * Created by wacuka on 06/12/2017.
 */

public class GetPlaylistVideosTask extends AsyncTask<Void ,Void ,Void > {

    private Context context;
    private View view ;
    private String playlistId = "";
    private NewtonCradleLoading newtonCradleLoading;
    private ArrayList<String> videoDetails;


    private GetPlaylistVideosResults getPlaylistVideosResults;






    public GetPlaylistVideosTask(Context context, View view, String playlistId, ViewPlaylistVideos viewPlaylistVideos){
        this.context = context;
        this.view = view ;
        this.playlistId = playlistId;


        getPlaylistVideosResults = viewPlaylistVideos;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        newtonCradleLoading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);


        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.colorPrimary);
        newtonCradleLoading.setVisibility(view.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

         videoDetails = new ArrayList<>();
       videoDetails =   new JSONExtractor().getPlaylistVideos(context,playlistId);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        getPlaylistVideosResults.getPlaylistVideosResult(videoDetails);
        newtonCradleLoading.setVisibility(view.INVISIBLE);
        newtonCradleLoading.stop();

    }
}
