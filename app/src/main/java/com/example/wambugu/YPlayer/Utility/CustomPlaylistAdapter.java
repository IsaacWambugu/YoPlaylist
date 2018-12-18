package com.example.wambugu.YPlayer.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wambugu.YPlayer.R;

import java.util.ArrayList;

/**
 * Created by wacuka on 09/11/2017.
 */

public class CustomPlaylistAdapter extends ArrayAdapter<PlaylistDataModel> implements View.OnClickListener {


    private ArrayList<PlaylistDataModel> dataSet;
    Context mcontext;

    private static class PlayListViewHolder{
        TextView playlist_title ;
        TextView playlist_id;
        TextView video_count ;

        ImageView playlist_thumbnail;



    }
    public CustomPlaylistAdapter(ArrayList<PlaylistDataModel> data , Context context){
        super(context,R.layout.playlistrow_layout,data);
        this.dataSet = data;
        this.mcontext = context;

    }
    @Override
    public void onClick(View v){
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        PlaylistDataModel playlistDataModel =(PlaylistDataModel)object;


        switch(v.getId()){
            case(R.id.playlist_title):
                Toast.makeText(getContext(), "Video detail" +playlistDataModel.getPlaylistTitle(), Toast.LENGTH_LONG).show();

        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position ,View convertView, ViewGroup parent ){

        PlaylistDataModel playlistDataModel  = getItem(position);
        CustomPlaylistAdapter.PlayListViewHolder playListViewHolder;

        final View result;
        if(convertView  == null){
            playListViewHolder = new CustomPlaylistAdapter.PlayListViewHolder();
            LayoutInflater inflater  = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.playlistrow_layout,parent,false) ;

            playListViewHolder.playlist_title  = (TextView) convertView.findViewById(R.id.playlist_title);
            playListViewHolder.video_count = (TextView) convertView.findViewById(R.id.video_count);

            playListViewHolder.playlist_thumbnail = (ImageView)  convertView.findViewById(R.id.playlist_thumbnail);
            playListViewHolder.playlist_thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            result = convertView;
            convertView.setTag(playListViewHolder);

        }else{

            playListViewHolder = (CustomPlaylistAdapter.PlayListViewHolder) convertView.getTag();
            result = convertView;

        }



        playListViewHolder.playlist_title.setText(playlistDataModel.getPlaylistTitle());
        playListViewHolder.video_count.setText(playlistDataModel.getVideoCount());

        String youtube_url  = playlistDataModel.getThumbnail_url();

        playListViewHolder.playlist_title.setOnClickListener(this);
        Glide.with(getContext()).load(youtube_url).fitCenter().into( playListViewHolder.playlist_thumbnail);

        //Toast.makeText(mcontext,playlistDataModel.getThumbnail_url(),Toast.LENGTH_LONG).show();

        return convertView;


    }
}
