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
 * Created by wacuka on 22/11/2017.
 */

public class AddedVideosAdapter extends RecyclerView.Adapter<AddedVideosAdapter.ViewHolder> {


   private static ArrayList<AddedVideos> video_list = new ArrayList<>();

    Context context;




    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView videoTitle,videoDuration,videoChannel;
        ImageView videoThumbnail ;







        public ViewHolder(View view){

            super(view);

            videoTitle  = (TextView) view.findViewById(R.id.added_video_title);
            videoChannel = (TextView) view.findViewById(R.id.added_video_channel);
            videoDuration = (TextView) view.findViewById(R.id.added_video_duration);

            videoThumbnail = (ImageView) view.findViewById(R.id.added_video_thumbnail);



        }




    }
    public AddedVideosAdapter(ArrayList<AddedVideos> video_list1){

        video_list = new ArrayList<>();
        video_list = video_list1;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_video_row,parent,false);
         context = parent.getContext();

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AddedVideos addedVideos = video_list.get(position);

        holder.videoTitle.setText(addedVideos.getVideoTitle());
        holder.videoDuration.setText(addedVideos.getVideoDuration());
        holder.videoChannel.setText(addedVideos.getVideoChannel());

        Glide.with(context).load(addedVideos.getThumbnailUrl()).fitCenter().into(holder.videoThumbnail);

    }


    public void deleteItem(int position){
        video_list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position,video_list.size());

    }
    @Override
    public int getItemCount() {
        return video_list.size();
    }
}
