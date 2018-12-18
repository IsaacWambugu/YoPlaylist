package com.example.wambugu.YPlayer.RecyclerView;

/**
 * Created by wacuka on 26/11/2017.
 */

public class AddedPlaylists {

    private String playlistId,playlistTitle,playlistThumbnailURL,channel,playlistVideoCount="";



    public AddedPlaylists(String playlistId,String playlistTitle,String playlistThumbnailURL,String channel){


        this.playlistId = playlistId;
        this.playlistTitle=  playlistTitle;
        this.playlistThumbnailURL = playlistThumbnailURL;
        this.channel = channel;
       // this.playlistVideoCount = playlistVideoCount;

    }
    public String getPlaylistId(){
        return playlistId;
    }
    public String getPlaylistTitle(){
        return playlistTitle;
    }
    public String getPlaylistThumbnailURL(){
        return playlistThumbnailURL;
    }
    public String getChannel(){
        return channel;
    }
  /*  public String getPlaylistVideoCount(){
        return playlistVideoCount;
    }
    */


}
