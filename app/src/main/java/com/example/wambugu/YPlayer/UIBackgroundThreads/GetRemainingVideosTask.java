package com.example.wambugu.YPlayer.UIBackgroundThreads;

import android.content.Context;
import android.os.AsyncTask;

import com.example.wambugu.YPlayer.Networking.NetWorker;
import com.example.wambugu.YPlayer.Utility.JSONExtractor;
import com.example.wambugu.YPlayer.activities.SearchVideo;

import org.json.JSONObject;

/**
 * Created by wacuka on 28/12/2017.
 */

public class GetRemainingVideosTask extends AsyncTask<Void,Void,Void> {

    private String videoId;
    private SearchVideo searchVideo;
    private Context context;
    private String videoDetails;





    public GetRemainingVideosTask(Context context, SearchVideo searchVideo , String videoId){

       this.context = context;
       this.videoId = videoId;
       this.searchVideo = searchVideo;


   }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {


        JSONObject jsonObjectResponse = new NetWorker(context,videoId,"video_details").getApiResult();
        videoDetails = new JSONExtractor().individualVideoDetails(jsonObjectResponse);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

      searchVideo.getRemainingVideos(videoDetails);
        super.onPostExecute(aVoid);
    }
}
