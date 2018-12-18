package com.example.wambugu.YPlayer.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wambugu.YPlayer.Database.DbHelper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wacuka on 09/11/2017.
 */

public final class JSONPlaylistDetails {

    private Intent intent ;
    private final String API_KEY = "AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo";
    DbHelper dbHelper ;




    private String title,thumbnail_url,y_playlist_id;
    private int video_count ;



    public void getPlaylistDetails(final Context context, final String playlist_id,final Activity activity) {

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = ("https://www.googleapis.com/youtube/v3/playlists?id="+playlist_id+"&key="+API_KEY+"&part=contentDetails,snippet");

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response !=null){
                       // title   =  response.getString("title");


                       //get title
                        JSONArray itemsArray  = response.getJSONArray("items");
                        JSONObject firstArray = itemsArray.getJSONObject(0);
                        JSONObject snippetObject  =firstArray.getJSONObject("snippet");
                        title = snippetObject.getString("title");

                        //get thumbnail url

                        JSONObject thumbnailObject = snippetObject.getJSONObject("thumbnails");
                        JSONObject mediumObject = thumbnailObject.getJSONObject("high");

                        thumbnail_url = mediumObject.getString("url");

                        //get playlist id
                        y_playlist_id = firstArray.getString("id");

                        //get playlist video count
                        JSONObject contentDetailsObject  = firstArray.getJSONObject("contentDetails");
                        video_count = contentDetailsObject.getInt("itemCount");
                        String s_video_count  = String.valueOf(video_count);//convert integer to string



                        //add above details to database
                        dbHelper = new DbHelper(context);

                       // dbHelper.addPlaylist(title,y_playlist_id,thumbnail_url,s_video_count);

                        activity.recreate();






















                     Toast.makeText(context,title+" \n"+thumbnail_url +" \n"+video_count,Toast.LENGTH_LONG).show();



                      /*  if(new DbHelper(context).videoChecker(video_id)){ //check if video exists
                            new DbHelper(context).addVideo(title,video_id);

                            //new MainActivity.PlaceholderFragment().onCreate(Bundle );//refresh activty after adding video

                            //intent  = new Intent(context,com.example.wacuka.a254yplaylist.VideoTab.class);
                            //  startActivity(intent);

                            activity.recreate();




                        }else{


                            Toast.makeText(context,"Video already exists ",Toast.LENGTH_LONG).show();
                        }
                         */

                    }
                }catch(Exception e) {
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Enter a valid url3",Toast.LENGTH_LONG).show();

            }
        });

        queue.add(request);



    }

}
