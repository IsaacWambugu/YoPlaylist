package com.example.wambugu.YPlayer.RecyclerView;

/**
 * Created by wacuka on 08/12/2017.
 */

public class PlaylistVideos {

    String videoPosition,videoId,videoTitle,videoThumbnail,videoChannel;


    public PlaylistVideos(String videoPosition,String videoId,String videoTitle,String videoThumbnail,String videoChannel){

        this.videoPosition = videoPosition;
        this.videoId = videoId;
        this.videoThumbnail = videoThumbnail;
        this.videoChannel = videoChannel;
        this.videoTitle = videoTitle;



    }
    public String getVideoPosition(){

        return videoPosition;

    }
    public String getVideoId(){

        return videoId;

    }
    public String getVideoTitle(){

        return videoTitle;

    }
    public String getVideoThumbnail(){

        return videoThumbnail;

    }
    public String getVideoChannel(){

        return videoChannel;

    }


}
