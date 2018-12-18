package com.example.wambugu.YPlayer.UIBackgroundThreads;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidnetworking.common.ANResponse;
import com.example.wambugu.YPlayer.Networking.NetWorker;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.Utility.JSONExtractor;
import com.example.wambugu.YPlayer.Utility.SharedPreferenceManager;
import com.example.wambugu.YPlayer.activities.SearchPlaylist;
import com.example.wambugu.YPlayer.interfaces.GetPlaylistResults;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wacuka on 30/11/2017.
 */

public class SearchPlaylistTask extends AsyncTask<String,Void,Void>{

    private Context context;
    private View view;
    private String query;
    private ArrayList<String> playlists =new ArrayList<>();
    private ANResponse<JSONObject> response;
     private Activity activity ;
     private GetPlaylistResults playlistResults;
    private RelativeLayout relativeLayout;






    NewtonCradleLoading newtonCradleLoading;




   public SearchPlaylistTask(Context context, View view, SearchPlaylist activity1, String query){
       this.context = context;
       this.view = view;
       this.query = query;
       this.activity = activity1;
       this.playlistResults = activity1;



   }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        newtonCradleLoading = (NewtonCradleLoading) view.findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.setLoadingColor(R.color.colorPrimary);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.search_playlist_loading_view);
        relativeLayout.setBackgroundColor(Color.parseColor("#99000000"));
        relativeLayout.setVisibility(View.VISIBLE);
        newtonCradleLoading.setVisibility(View.VISIBLE);
        newtonCradleLoading.start();

    }

    @Override
    protected Void doInBackground(String... strings) {



        JSONObject result =  new NetWorker(context,query,new SharedPreferenceManager(context).getPlaylistSortBy(),new SharedPreferenceManager(context).getPlaylistMaxResult(),new SharedPreferenceManager(context).getPlaylistSafeSearch(),"search_playlist").getApiResult();
        playlists =  new JSONExtractor().playlistSearchResults(result);




        return null;
    }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e("BEFORE INTERFACE ",playlists.toString());

            playlistResults.getPlaylistResults(playlists);
             newtonCradleLoading.stop();
            newtonCradleLoading.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);



    }
}
