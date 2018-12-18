package com.example.wambugu.YPlayer.RecyclerView;
/**
 * Created by wacuka on 18/11/2017.
 */

public class SearchPlaylist {


    private String playlist_id, playlist_title, thumbnail_url, channel;


    public SearchPlaylist(String playlist_id, String playlist_title, String thumbnail_url, String channel) {

        this.playlist_id = playlist_id;
        this.playlist_title = playlist_title;
        this.thumbnail_url = thumbnail_url;

        this.channel = channel;


    }

    public String getPlaylistId() {
        return playlist_id;

    }

    public String getPlaylistTitle() {
        return playlist_title;

    }

    public String getThumbnailURL() {
        return thumbnail_url;

    }
    public String getChannel() {
        return channel;

    }
}

