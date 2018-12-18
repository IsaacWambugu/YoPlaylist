package com.example.wambugu.YPlayer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.RecyclerView.AddedPlaylists;
import com.example.wambugu.YPlayer.RecyclerView.AddedPlaylistsAdapter;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by wacuka on 26/11/2017.
 */

public class PlaylistFragment extends Fragment {

    private TextView videoSearch;
    AddedPlaylistsAdapter addedPlaylistsAdapter;
    RecyclerView recyclerView;
    ArrayList<AddedPlaylists> added_playlist_data;




    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_playlist,container,false);

        getPlaylistsFromDb();
        setUIConfigurations();


        return view;
    }
    public void setUIConfigurations(){


        videoSearch = (EditText) view.findViewById(R.id.playlist_search_field);
      videoSearch.setText("");





        addedPlaylistsAdapter = new AddedPlaylistsAdapter(added_playlist_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.added_playlist_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addedPlaylistsAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){


                AddedPlaylists addedPlaylists = added_playlist_data.get(position);

                Toast.makeText(getActivity(),addedPlaylists.getPlaylistTitle(),Toast.LENGTH_LONG).show();

                TextView imageView= (TextView) view.findViewById(R.id.video_title);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Single click on text "+position,Toast.LENGTH_LONG).show();
                    }
                });



            }
            @Override
            public void onLongClick(View view , int position){


            }




        }));
    }
    public void recyclerViewFilter(){

        //ic_action_search in recycler view
        videoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // playlistVid.setFilter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    public void getPlaylistsFromDb(){
        ArrayList<String> playlist_from_db ;


        DbHelper dbHelper = new DbHelper(getActivity());
        playlist_from_db = dbHelper.getPlayLists();
        added_playlist_data = new ArrayList<>();




        for(String videos:playlist_from_db){

            String[]  playlistDetails = videos.split("##");

            Log.e("PLAYLIST FROM DB",videos);


            Log.e("PLAYLIST DETAILS",playlistDetails[0] +"  "+playlistDetails[1] +"  "+playlistDetails[2] +"  "+playlistDetails[3]);

             added_playlist_data.add(new AddedPlaylists(playlistDetails[0],playlistDetails[1],playlistDetails[2],playlistDetails[3]));
             playlistDetails = null;

        }
    }
}

