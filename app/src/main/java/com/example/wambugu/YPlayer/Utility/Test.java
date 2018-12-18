package com.example.wambugu.YPlayer.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wambugu.YPlayer.Database.DbHelper;

import org.json.JSONObject;

/**
 * Created by wacuka on 07/11/2017.
 */

public class Test extends AppCompatActivity {

    private Intent intent ;


    private String title;


    public void getVideoTitle(final Context context, String youtubeURL, final String video_id, final Activity activity) {

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = ("http://www.youtube.com/oembed?url="+youtubeURL );

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                      if (response !=null){
                          title   =  response.getString("title");

                          if(new DbHelper(context).videoChecker(video_id)){ //check if video exists
                              //new DbHelper(context).addVideo(title,video_id);

                             //new MainActivity.PlaceholderFragment().onCreate(Bundle );//refresh activty after adding video

                             //intent  = new Intent(context,com.example.wacuka.a254yplaylist.VideoTab.class);
                            //  startActivity(intent);

                             activity.recreate();




                          }else{


                              Toast.makeText(context,"Video already exists ",Toast.LENGTH_LONG).show();
                          }


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
