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
import com.example.wambugu.YPlayer.Utility.PlaylistVideo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by wacuka on 08/12/2017.
 */

public class PlaylistVideosAdapter extends RecyclerView.Adapter<PlaylistVideosAdapter.ViewHolder>{

   ArrayList<PlaylistVideos> videoData = new ArrayList<>();
    Context context;



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        TextView video_title,video_channel;


        public ViewHolder(View v){
            super(v);

            thumbnail = (ImageView) v.findViewById(R.id.playlist_video_thumbnail);
            video_title = (TextView) v.findViewById(R.id.playlist_video_title);
            video_channel = (TextView) v.findViewById(R.id.playlist_video_channel);




        }
    }
    public PlaylistVideosAdapter(ArrayList<PlaylistVideos> videoData){

        this.videoData = videoData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_video_row,parent,false);
        context = parent.getContext();

        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlaylistVideos playlistVideos = videoData.get(position);


        holder.video_title.setText(playlistVideos.getVideoTitle());
        holder.video_channel.setText(playlistVideos.getVideoChannel());

        Glide.with(context).load(playlistVideos.getVideoThumbnail()).fitCenter().into(holder.thumbnail);



    }

    @Override
    public int getItemCount() {
        return videoData.size();
    }
}
