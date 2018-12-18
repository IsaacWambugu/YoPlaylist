package com.example.wambugu.YPlayer.Constants;

/**
 * Created by wacuka on 30/11/2017.
 */

public  class Values {

    private static String apiKey = "AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo";


    //shared Preferences
    public static final String PREF_LOCATION  = "com.example.wambugu.YPlayer.AppPrefs";


   //youtube api urls
    public static final String SEARCH_PLAYLIST_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=playlist&key="+apiKey+"&q=" ;
    public static final String GET_PLAYLIST_ITEMS_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&key="+apiKey+"&maxResults=50&playlistId=";
    public static final String SEARCH_VIDEO_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key="+apiKey+"&q=";
    public static final String GET_VIDEO_DETAILS = "https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&key="+apiKey+"&id=";

}
