package com.example.wambugu.YPlayer.Networking;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.example.wambugu.YPlayer.Constants.Values;

import org.json.JSONObject;

/**
 * Created by wacuka on 05/12/2017.
 */

public class NetWorker {

    private String videoId = "";
    private String operation = "";
    private String Url = "";
    private String searchQuery = "";
    private String sortBy ="";
    private String maxResult = "";
    private Context context;
    private String playlistId  = "";
    private String safeSearch =  "";
    private String videoDuration = "";
    private String nextPageToken = "";
    private ANResponse<JSONObject>  anResponse;
    private JSONObject response = new JSONObject();










    public NetWorker(Context context,String searchQuery ,String sortBy ,String maxResult,String safeSearch,String videoDuration,String operation){

       this.context = context;
       this.operation = operation;
       this.searchQuery =searchQuery;
       this.sortBy = sortBy;
       this.maxResult = maxResult;
       this.safeSearch = safeSearch;
       this.videoDuration = videoDuration;



   }
   public NetWorker(Context context,String videoId,String operation){
       this.context = context;
       this.videoId =videoId;
       this.operation = operation;

   }
    public NetWorker(Context context,String searchQuery ,String sortBy ,String maxResult,String safeSearch,String operation){

        this.context = context;
        this.operation = operation;
        this.searchQuery =searchQuery;
        this.sortBy = sortBy;
        this.maxResult = maxResult;
        this.safeSearch = safeSearch;




    }
    public NetWorker(Context context,String playlistId ,String nextPageToken, String operation){
        this.context = context;
        this.operation =operation;
        this.nextPageToken = nextPageToken;

        this.playlistId = playlistId;



    }

   public JSONObject getApiResult(){



       AndroidNetworking.initialize(context);
      Log.e("NETWORKER",operation);
       Log.e("NETWORKER",searchQuery);
       Log.e("NETWORKER",maxResult);
       Log.e("NETWORKER",safeSearch);
       Log.e("NETWORKER",sortBy);

       //form the youtube api URL:

       switch(operation){

           case "search_playlist":
               Url = Values.SEARCH_PLAYLIST_URL+searchQuery+"&maxResults="+maxResult+"&order="+sortBy+"&safeSearch="+safeSearch;
               break;
           case "video_search":
               Url  = Values.SEARCH_VIDEO_URL+searchQuery+"&maxResults="+maxResult+"&sortBy="+sortBy+"&safeSearch="+safeSearch+"&videoDuration="+videoDuration;;
               break;
           case "video_details":
               Url = Values.GET_VIDEO_DETAILS+videoId;

               break;

           case "playlist_video_list":

               if(nextPageToken == "") {
                   Url = Values.GET_PLAYLIST_ITEMS_URL + playlistId;
               }else{
                   Url = Values.GET_PLAYLIST_ITEMS_URL + playlistId+"&pageToken="+nextPageToken;
               }
           break;


       }
       Log.e("NETWORKER_URL",Url);

       ANRequest anRequest = AndroidNetworking.get(Url).setPriority(Priority.HIGH).build();
       anResponse = anRequest.executeForJSONObject();

       if(anResponse.isSuccess()){

            response =  anResponse.getResult();

           Log.e("API RESPONSE",response.toString());

       }
       else{

           ANError error = anResponse.getError();
       }




       return response;
   }
}
