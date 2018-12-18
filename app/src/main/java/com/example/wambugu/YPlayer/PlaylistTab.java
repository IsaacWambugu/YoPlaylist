package com.example.wambugu.YPlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wambugu.YPlayer.Utility.CustomPlaylistAdapter;
import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.Utility.PlaylistDataModel;
import com.example.wambugu.YPlayer.Utility.StartCustomYPlayer;

import java.util.ArrayList;

/**
 * Created by wacuka on 09/11/2017.
 */

public class PlaylistTab extends Fragment {

    private ListView listView;
    private View view;
    private ArrayList<PlaylistDataModel> playlistDataModels;
    private ArrayList<String> fetched_playlists;
    private String split_playlist[];




    CustomPlaylistAdapter adapter;
    DbHelper dbHelper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist_tab, container, false);

        setUIConfigurations();
        fetchDataFromDb();
        configureAdapter();
        //new JSONPlaylistDetails().getPlaylistDetails(getActivity(), "PLFgquLnL59amN9tYr7o2a60yFUfzQO3sU");
        //setup custom list adapter
        return view;
    }
    public void setUIConfigurations(){

        listView = (ListView)view.findViewById(R.id.playlist);

    }
    public void fetchDataFromDb() {
        playlistDataModels = new ArrayList<>();

        dbHelper = new DbHelper(getActivity());
       fetched_playlists =  dbHelper.getPlayLists();

        for(String playlist:fetched_playlists){

            split_playlist = playlist.split("##");
            playlistDataModels.add(new PlaylistDataModel(split_playlist[0],split_playlist[1],split_playlist[2],split_playlist[3]));
            split_playlist =null;

        }

      // playlistDataModels.add(new PlaylistDataModel("Playlist Title","PLFgquLnL59amN9tYr7o2a60yFUfzQO3sU","100")) ;
    }


    public void configureAdapter(){

        adapter = new CustomPlaylistAdapter(playlistDataModels,getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PlaylistDataModel playlistDataModel = playlistDataModels.get(position);

                //start youtube player

                new StartCustomYPlayer().startYouTubePlaylistPlayer(getActivity(),playlistDataModel.getPlaylistId());
                // new StartCustomYPlayer().setYouTubeVideoId(getActivity(),playlistDataModel.getPlaylistId());


            }
        });
    }

}

