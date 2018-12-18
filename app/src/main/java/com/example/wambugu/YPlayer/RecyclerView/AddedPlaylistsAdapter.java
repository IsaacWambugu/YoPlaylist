package com.example.wambugu.YPlayer.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wambugu.YPlayer.R;

import java.util.ArrayList;

/**
 * Created by wacuka on 26/11/2017.
 */

public class AddedPlaylistsAdapter extends RecyclerView.Adapter<AddedPlaylistsAdapter.ViewHolder> {

    ArrayList<AddedPlaylists> playlist_list;
    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        TextView playlist_title,playlist_channel,playlist_videos;


        public ViewHolder(View v){

            super(v);

            thumbnail = (ImageView) v.findViewById(R.id.added_playlist_thumbnail);
            playlist_title = (TextView) v.findViewById(R.id.added_playlist_title);
            playlist_channel = (TextView) v.findViewById(R.id.added_playlist_channel);
          //  playlist_videos = (TextView) v.findViewById(R.id.added_playlist_videos);


        }
    }

    public AddedPlaylistsAdapter(ArrayList<AddedPlaylists> playlist_list1){

        playlist_list =new ArrayList<>();
        playlist_list = playlist_list1;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_playlist_row,parent,false);

        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AddedPlaylists addedPlaylists = playlist_list.get(position);

        holder.playlist_title.setText(addedPlaylists.getPlaylistTitle());
        holder.playlist_channel.setText(addedPlaylists.getChannel());
        //holder.playlist_videos.setText(addedPlaylists.getPlaylistVideoCount());

        Glide.with(context).load(addedPlaylists.getPlaylistThumbnailURL()).fitCenter().into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return playlist_list.size();
    }
}
