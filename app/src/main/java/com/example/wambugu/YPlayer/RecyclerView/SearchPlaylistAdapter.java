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
 * Created by wacuka on 29/11/2017.
 */

public class SearchPlaylistAdapter extends RecyclerView.Adapter<SearchPlaylistAdapter.ViewHolder> {

    ArrayList<SearchPlaylist> playlist_result;
    Context context;




    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView playlist_title,playlist_channel,playlist_videos;
        ImageView playlist_thumbnail;


        public ViewHolder(View v){
            super(v);

            playlist_title = (TextView) v.findViewById(R.id.search_playlist_title);
            playlist_channel = (TextView) v.findViewById(R.id.search_playlist_channel);


            playlist_thumbnail = (ImageView) v.findViewById(R.id.search_playlist_thumbnail);

        }


    }
    public SearchPlaylistAdapter(ArrayList<SearchPlaylist> playlist){


        playlist_result = new ArrayList<>();
        playlist_result =playlist;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchplaylist_row,parent,false);


        context = parent.getContext();

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SearchPlaylist searchPlaylist = playlist_result.get(position);

        holder.playlist_title.setText(searchPlaylist.getPlaylistTitle());
        holder.playlist_channel.setText(searchPlaylist.getChannel());



        Glide.with(context).load(searchPlaylist.getThumbnailURL()).fitCenter().into(holder.playlist_thumbnail);
    }

    @Override
    public int getItemCount() {
        return playlist_result.size();
    }
}
