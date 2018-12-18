package com.example.wambugu.YPlayer.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.Utility.PlaylistVideo;
import com.example.wambugu.YPlayer.Utility.PlaylistVideosAdapter;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by wacuka on 11/11/2017.
 */
/*
##########################################################################
###########Activity showing videos of  individual playlists################
##########################################################################
 */


public class PlayListView extends AppCompatActivity {
    private ListView listView;
    private RecyclerView recyclerView;
    private PlaylistVideosAdapter playlistVideosAdapter;
    private ArrayList<String> videos_fromdb;
    private String[]  videodetials;
    private  ArrayList<PlaylistVideo> playlist_data;
    private EditText videoSearch;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistview);


        getVideosFromDb();
        setUIConfigurations();
        recyclerViewFilter();




    }
    public void setUIConfigurations(){


        videoSearch = (EditText) findViewById(R.id.playlist_search_field);
       // videoSearch.setText("");





        playlistVideosAdapter = new PlaylistVideosAdapter(playlist_data);
        recyclerView = (RecyclerView) findViewById(R.id.playlist_view_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(playlistVideosAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){
                PlaylistVideo playlistVideo = playlist_data.get(position);

                Toast.makeText(getApplicationContext(),playlistVideo.getVideoTitle(),Toast.LENGTH_LONG).show();

                TextView imageView= (TextView) view.findViewById(R.id.video_title);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Single click on text "+position,Toast.LENGTH_LONG).show();
                    }
                });



            }
            @Override
            public void onLongClick(View view , int position){


            }




    }));
    }
    public void recyclerViewFilter(){

        //search in recycler view
        videoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  playlistVideosAdapter.setFilter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    public void getVideosFromDb(){

        DbHelper dbHelper = new DbHelper(getApplicationContext());
        videos_fromdb = dbHelper.getVideos();
        videodetials = new String[ videos_fromdb.size()];

        playlist_data = new ArrayList<>();




        for(String videos:videos_fromdb){

            videodetials = videos.split("##");
            playlist_data.add(new PlaylistVideo("null",videodetials[0],videodetials[1],"https://i.ytimg.com/vi/F57P9C4SAW4/hqdefault.jpg"));
            videodetials = null;
           // playlistVideosAdapter.notifyDataSetChanged();
        }
    }
}
