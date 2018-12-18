package com.example.wambugu.YPlayer.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.wambugu.YPlayer.Utility.CustomAdapter;
import com.example.wambugu.YPlayer.Utility.DataModel;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.Utility.StartCustomYPlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;

/**
 * Created by wacuka on 28/10/2017.
 */

public class VideoTab extends Fragment {


    private Intent intent;
    private ImageView thumbnail;
    private String url;
    private View view;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<DataModel> dataModels;
    ListView listView;


    private static CustomAdapter adapter;
    YouTubePlayerFragment playerFragment ;
    FragmentManager fm;
    String tag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,container,false);



        listView = (ListView) view.findViewById(R.id.list);
        dataModels = new ArrayList<>();


        dataModels.add(new DataModel("Video1", "IPfJnp1guPc"));
        dataModels.add(new DataModel("Video2", "luxE7oEKiic"));
        dataModels.add(new DataModel("Video3", "MLh88opqy0E"));

        adapter = new CustomAdapter(dataModels,getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DataModel dataModel = dataModels.get(position);

                //start youtube player
                new StartCustomYPlayer().startYouTubePlaylistPlayer(getActivity(),dataModel.getVideoId());





            }

        });


        return view;
    }
}
