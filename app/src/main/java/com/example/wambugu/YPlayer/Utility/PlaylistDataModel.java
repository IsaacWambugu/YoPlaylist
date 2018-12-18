package com.example.wambugu.YPlayer.Utility;

/**
 * Created by wacuka on 09/11/2017.
 */

public class PlaylistDataModel {

    String playlist_title;
    String playlist_id;
    String video_count;
    String thumbnail_url;




    public PlaylistDataModel(String playlist_id ,String playlist_title ,String thumbnail_url,String video_count){

        this.playlist_title = playlist_title;
        this.playlist_id = playlist_id;
        this.video_count = video_count;
        this.thumbnail_url = thumbnail_url;




    }
    public String getPlaylistTitle(){
        return playlist_title;

    }
    public String getPlaylistId(){
        return playlist_id;

    }
    public String getVideoCount(){
        return video_count;

    }
    public String getThumbnail_url(){
        return thumbnail_url;

    }
}
