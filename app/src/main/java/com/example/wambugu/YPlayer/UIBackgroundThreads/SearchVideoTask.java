package com.example.wambugu.YPlayer.UIBackgroundThreads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.wambugu.YPlayer.Networking.NetWorker;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.Utility.JSONExtractor;
import com.example.wambugu.YPlayer.Utility.SharedPreferenceManager;
import com.example.wambugu.YPlayer.activities.SearchVideo;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wacuka on 27/12/2017.
 */

public class SearchVideoTask extends AsyncTask<Void,Void,Void> {
    private Context context;
    private View view ;
    private SearchVideo searchVideo;
    private String query;
    private NewtonCradleLoading newtonCradleLoading;
    private ArrayList<String> videoDetails = new ArrayList<>();
    private Pair<ArrayList<String> ,ArrayList<String>> pairVideoDetails;
    private RelativeLayout relativeLayout;







    public SearchVideoTask(Context context, View view, SearchVideo searchVideo, String query){
        this.context = context;
        this.view = view;
        this.searchVideo = searchVideo;
        this.query= query;


    }
    @Override
    protected void onPreExecute() {
        newtonCradleLoading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.colorPrimary);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.search_video_loading_view);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.setBackgroundColor(Color.parseColor("#99000000"));
        newtonCradleLoading.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        JSONObject jsonObjectResponse = new NetWorker(context, query,
                new SharedPreferenceManager(context).getVideoSortBy(),
                new SharedPreferenceManager(context).getVideoMaxResult(),
                new SharedPreferenceManager(context).getVideoSafeSearch(),
                new SharedPreferenceManager(context).getVideoDuration(),"video_search")
                .getApiResult();

        ArrayList<String> videoIds = new ArrayList<>();
        ArrayList<String> first5Ids =new ArrayList<>();
        ArrayList<String> first5VideoDetails  = new ArrayList<>();


        videoIds = new JSONExtractor().videoSearchResult(jsonObjectResponse);

        //load the first 5 elements
        Log.e("VideoIDs",videoIds.toString());




        if(videoIds.size()>= 5){
            first5Ids = new ArrayList<>(videoIds.subList(0,5));
            videoIds.subList(0,4).clear();
            Log.e("FIRST CONDITION","this");


        }else{
            first5Ids = new ArrayList<>(videoIds.subList(0,videoIds.size()));
            Log.e("SECOND CONDITION","this");

        }

        for(String ids:first5Ids){

            JSONObject jsonObjectResponse1 = new NetWorker(context,ids,"video_details").getApiResult();
            first5VideoDetails.add(new JSONExtractor().individualVideoDetails(jsonObjectResponse1));

        }
         pairVideoDetails = new Pair<>(first5VideoDetails,videoIds);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        searchVideo.getVideoResults(pairVideoDetails);
        newtonCradleLoading.stop();
        newtonCradleLoading.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
    }
}
