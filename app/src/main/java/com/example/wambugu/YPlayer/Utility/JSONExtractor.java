package com.example.wambugu.YPlayer.Utility;

import android.content.Context;
import android.util.Log;

import com.example.wambugu.YPlayer.Networking.NetWorker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wacuka on 18/11/2017.
 */

public class JSONExtractor {

    private String tag  = "JSONExtractor";



    public ArrayList<String> videoSearchResult(JSONObject search_result) {

        ArrayList<String> video_ids = new ArrayList<>();
       try {

           JSONArray searchJSONArray = search_result.getJSONArray("items");
           int itemsArraySize =searchJSONArray.length();


           for (int i = 0; i < itemsArraySize; i++) {
               JSONObject searchJSONID = searchJSONArray.getJSONObject(i);
               JSONObject videoID = searchJSONID.getJSONObject("id");

               video_ids.add(videoID.getString("videoId"));


           }
       }catch (JSONException e){
           e.printStackTrace();
       }

        return video_ids;

    }
    public String individualVideoDetails(JSONObject json_video_details){

        String video_id;
        String duration;
        String viewCount;
        String title;
        String channel_title;
        String thumbnail_url;
        String videoDetails = "";
        int count;



       try {

           JSONArray itemsJSONArray = json_video_details.getJSONArray("items");
           JSONObject arrayItem1 = itemsJSONArray.getJSONObject(0);
           video_id = arrayItem1.getString("id");

           Log.e("id",video_id);

           JSONObject contentDetails = arrayItem1.getJSONObject("contentDetails");
           duration = contentDetails.getString("duration");

           Log.e("duration",duration);

           JSONObject snippet = arrayItem1.getJSONObject("snippet");
           channel_title = snippet.getString("channelTitle");
           title = snippet.getString("title");


           Log.e("title",title);


           JSONObject thumbnail = snippet.getJSONObject("thumbnails");
           JSONObject high = thumbnail.getJSONObject("high");
           thumbnail_url = high.getString("url");

           Log.e("url",thumbnail_url);


           contentDetails = arrayItem1.getJSONObject("statistics");
           count = ((contentDetails.getInt("viewCount")));
           viewCount = Integer.toString(count);

           videoDetails = video_id +"###" +title+"###"+thumbnail_url+"###"+duration+"###"+channel_title+"###"+viewCount;

       }catch (JSONException e){
           e.printStackTrace();
       }
        return videoDetails;








    }
    public ArrayList<String> playlistSearchResults(JSONObject result){


        ArrayList<String> playlistDetails = new ArrayList<>();

        Log.e("!!!!!!!!!!ITS HERE",result.toString());

         int i =0;

        try {

            for (i = 0; i < 50;i++){

                String[] playlistChannel = new String[50];
                String[] playlistId = new String[50];
                String[] playlistThumbnail = new String[50];
                String[] playlistTitle = new String[50] ;




                playlistId[i]= result.getJSONArray("items").getJSONObject(i).getJSONObject("id").getString("playlistId");
                Log.e("PlaylistId",playlistId[i]);
                playlistTitle[i] = result.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getString("title");
                Log.e("PlaylistTitle",playlistTitle[i]);
                playlistChannel[i] = result.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getString("channelTitle");
                Log.e("PlaylistChannel",playlistChannel[i]);
                playlistThumbnail[i] =result.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails")
                        .getJSONObject("high").getString("url");
                Log.e("PlaylistThumbnail",playlistThumbnail[i]);
                playlistDetails.add(playlistId[i]+"##"+playlistTitle[i]+"##"+playlistThumbnail[i]+"##"+playlistChannel[i]);

                Log.e("Playlist Extractor",playlistDetails.toString());



            }
        }catch(JSONException jsonE){
            Log.e("MAKOSA",jsonE.toString());
        }

        return playlistDetails;
    }

    public ArrayList<String> getPlaylistVideos(Context context, String playlistId){

        Log.e(tag,"RETRIEVING PLAYLIST VIDEOS");

        String nextPageToken  = "";
        JSONObject jsonResult;
        String videoPosition,videoId,videoTitle,videoThumbnail,videoChannel;
        ArrayList<String> playlist_videos = new ArrayList<>();
        String data;




        do{

            jsonResult  = new NetWorker(context,playlistId,nextPageToken,"playlist_video_list").getApiResult();

            Log.e(tag+"Jsondata",jsonResult.toString());

            //extracting json data
            try {

                try {

                    nextPageToken = jsonResult.getString("nextPageToken");

                     }catch (JSONException jsonE){

                         nextPageToken = "";
                     }

                int jsonArrayCount = jsonResult.getJSONArray("items").length();

                Log.e(tag,nextPageToken);
                Log.e(tag,Integer.toString(jsonArrayCount));


                int i;
                for(i = 0;i<jsonArrayCount;i++){

                   videoPosition = Integer.toString(jsonResult.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getInt("position"));
                    videoId = jsonResult.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                    videoTitle = jsonResult.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getString("title");
                    videoThumbnail = jsonResult.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
                    videoChannel = jsonResult.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").getString("channelTitle");

                     data  = videoPosition + "##"+videoId+"##"+videoTitle+"##"+videoThumbnail+"##"+videoChannel;

                    Log.e(tag,data);

                    playlist_videos.add(data);

                }


            }catch(JSONException jsonE){

                Log.e(tag+" CATCH:",jsonE.getMessage());
                nextPageToken ="";
            }


        }while(nextPageToken !="");

            //nextPageToken

        return playlist_videos;


    }

}
