package com.example.wambugu.YPlayer.Utility;

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
 * Created by wacuka on 11/11/2017.
 */

public class PlaylistVideosAdapter extends RecyclerView.Adapter<PlaylistVideosAdapter.ViewHolder> {

    private ArrayList<PlaylistVideo> video_list;
    private Context context;
    private ArrayList<PlaylistVideo> dataSet;
    private ArrayList<PlaylistVideo> privatearray;




    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView video_time,video_title;
        public ImageView video_thumbnail;



        public  ViewHolder(View view ){
            super(view);
            video_title = (TextView) view.findViewById(R.id.video_title);
            video_thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);

        }
    }
    public PlaylistVideosAdapter(ArrayList<PlaylistVideo> video_list){

        video_list = new ArrayList<>();
        this.video_list = video_list;
        this.dataSet = video_list;
        privatearray = new ArrayList<>();
        privatearray.addAll(video_list);





    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row,parent,false);
        context = parent.getContext();

        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlaylistVideo playlistvideo =  video_list.get(position);
        holder.video_title.setText(playlistvideo.getVideoTitle());
        holder.video_thumbnail.setImageResource(R.drawable.intro_slide1);


        Glide.with(context).load(playlistvideo.getVideoThumbnail()).fitCenter().into(holder.video_thumbnail);

    }

    @Override
    public int getItemCount() {
        return video_list.size();
    }

    public void setFilter(String charText ){


        charText = charText.toLowerCase();
        dataSet.clear();
        if(charText.length()==0 || charText == ""|| charText==" "){
            dataSet.addAll(privatearray);
            notifyDataSetChanged();

        }else{
            for(PlaylistVideo dm :privatearray){
                if(dm.getVideoTitle().toLowerCase().contains(charText)){
                    dataSet.add(dm);
                    notifyDataSetChanged();


                }
                else{
                    dataSet.clear();

                    notifyDataSetChanged();

                }

            }
        }
    }
}
