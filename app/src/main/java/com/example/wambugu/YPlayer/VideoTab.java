package com.example.wambugu.YPlayer;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.wambugu.YPlayer.Database.DbHelper;


import com.example.wambugu.YPlayer.Utility.CustomAdapter;
import com.example.wambugu.YPlayer.Utility.DataModel;
import com.example.wambugu.YPlayer.activities.PlayListView;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;

/**
 * Created by wacuka on 28/10/2017.
 */

public class VideoTab extends android.support.v4.app.Fragment {


    private Intent intent;
    private ImageView thumbnail;
    private String url;
    private  String[] videodetials;

    private View view;
    private ArrayList<String> videos_fromdb;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private Intent intent;

    ArrayList<DataModel> dataModels;
    ListView listView;
    EditText video_search;


    CustomAdapter adapter;
    YouTubePlayerFragment playerFragment ;
    FragmentManager fm;
    String tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_tab,container,false);



        listView = (ListView) view.findViewById(R.id.list);
        dataModels = new ArrayList<>();
        videos_fromdb = new ArrayList<>();



          //fetch videos from database
        getVideosFromDb();

        adapter = new CustomAdapter(dataModels,getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DataModel dataModel = dataModels.get(position);

                //start youtube player
               // new StartCustomYPlayer().startYouTubeVideoPlayer(getActivity(),dataModel.getVideoId());

                intent  = new Intent(getActivity(),PlayListView.class);
                startActivity(intent);




            }

        });


        //ic_action_search test
        video_search = (EditText) view.findViewById(R.id.video_search);



        video_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //String ic_action_search  =video_search.getText().toString().toLowerCase(Locale.getDefault());
               // Toast.makeText(getActivity(),charSequence.toString(),Toast.LENGTH_LONG).show();
                //adapter.getFilter().filter();
                adapter.filter(charSequence.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return view;
    }

    public void getVideosFromDb(){

        DbHelper dbHelper = new DbHelper(getActivity());
        videos_fromdb = dbHelper.getVideos();
        videodetials = new String[ videos_fromdb.size()];

        for(String videos:videos_fromdb){

          ///  videodetials = videos.split("##");
//            dataModels.add(new DataModel(videodetials[0],videodetials[1]));
            videodetials = null;
        }
    }
}
