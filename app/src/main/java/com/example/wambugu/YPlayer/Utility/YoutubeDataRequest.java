package com.example.wambugu.YPlayer.Utility;

/**
 * Created by wacuka on 18/11/2017.
 */

public class YoutubeDataRequest {

  /*  private String request_type;

    //private String result_count  = 25;//default

    private String api_key  = "AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo";

    private String video_search_url = "https://googleapis.com/youtube/v3/search?part=snippet&type=video&q=";

    private String full_search_url = "";

    Context context ;
    Activity activity;








    public YoutubeDataRequest(Context context, Activity activity){
        this.activity = activity;
        this.context = context;

    }
    public void videoSearch(Context context, final Activity activity , String user_search){


        RequestQueue queue = Volley.newRequestQueue(context);

        full_search_url = video_search_url +user_search+"&key="+api_key;



        //String url = ("https://www.googleapis.com/youtube/v3/playlists?id="+playlist_id+"&key="+API_KEY+"&part=contentDetails,snippet");

        JsonObjectRequest request = new JsonObjectRequest(full_search_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response !=null){

                         // new JSONExtractor().;
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
    */
}
