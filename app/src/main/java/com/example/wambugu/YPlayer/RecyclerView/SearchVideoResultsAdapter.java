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
 * Created by wacuka on 17/11/2017.
 */

public class SearchVideoResultsAdapter extends RecyclerView.Adapter<SearchVideoResultsAdapter.ViewHolder>{

    ArrayList<SearchVideoResults> video_list ;
    Context context;



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView video_title,video_duration,video_channel,video_views;
        ImageView video_thumbnail;
        public ViewHolder(View v){
            super(v);

            video_title = (TextView) v.findViewById(R.id.search_video_title);
            video_duration = (TextView) v.findViewById(R.id.search_video_duration);
            video_channel = (TextView) v.findViewById(R.id.search_video_channel);
            video_views  = (TextView) v.findViewById(R.id.search_video_views);


            video_thumbnail = (ImageView) v.findViewById(R.id.search_video_thumbnail);




        }
    }
    public SearchVideoResultsAdapter(ArrayList<SearchVideoResults> video_list)
    {

        this.video_list = new ArrayList<>();
        this.video_list  = video_list;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchvideo_row,parent,false);

        this.context = parent.getContext();

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchVideoResults searchVideoResults = video_list.get(position);

        holder.video_title.setText(searchVideoResults.getVideoTitle());
        holder.video_duration.setText(searchVideoResults.getVideoDuration());
        holder.video_channel.setText(searchVideoResults.getChannel());
        holder.video_views.setText(searchVideoResults.getViews());

       Glide.with(context).load(searchVideoResults.getThumbnailURL()).fitCenter().into(holder.video_thumbnail);

    }
    @Override
    public int getItemCount() {
        return video_list.size();
    }
}
