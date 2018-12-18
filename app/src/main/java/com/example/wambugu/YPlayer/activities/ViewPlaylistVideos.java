package com.example.wambugu.YPlayer.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.RecyclerView.PlaylistVideos;
import com.example.wambugu.YPlayer.RecyclerView.PlaylistVideosAdapter;
import com.example.wambugu.YPlayer.UIBackgroundThreads.GetPlaylistVideosTask;
import com.example.wambugu.YPlayer.interfaces.GetPlaylistVideosResults;

import java.util.ArrayList;


/*
#######################################################################################
####################view videos individual playlist ic_action_search results#####################
 ######################################################################################
 */

public class ViewPlaylistVideos extends AppCompatActivity implements GetPlaylistVideosResults {
    private String tag = "ViewPlaylistVideos";
    private String playlistId  = "";
    private String playlistTitle = "";
    private String thumbnailUrl = "";
    private String playlistChannel  = "";

    private ArrayList<PlaylistVideos> videos;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist_videos);
        setToolBar();
        setBottomNavigation();
        getPassedVariables();
        retrieveVideos();





    }
    public void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_playlist_videos_toolbar);
        toolbar.setTitle(playlistTitle);

        setSupportActionBar(toolbar);
    }

    public void getPassedVariables(){


        Bundle bundle = getIntent().getExtras();

        playlistId = bundle.getString("Passed_Playlist_Id");
        playlistTitle = bundle.getString("Passed_Playlist_Title");
        playlistChannel = bundle.getString("passed_playlist_channel");
        thumbnailUrl = bundle.getString("passed_playlist_thumbnail_url");


        Log.e(tag +"playlistId :",playlistId);
        Log.e(tag +":playlistTitle :",playlistTitle);




    }
    public void setBottomNavigation(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_one:

                       addPlaylistToDb();
                        break;
                    case R.id.action_two:

                        Toast.makeText(getApplicationContext(),"Action two ",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_three:


                        Toast.makeText(getApplicationContext(),"Action three ",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void getPlaylistVideosResult(ArrayList<String> response) {

        videos = new ArrayList<>();


        for(String x:response){

            String[] videoData  = x.split("##");

            videos.add(new PlaylistVideos(videoData[0],videoData[1],videoData[2],videoData[3],videoData[4]));

        }

        setRecyclerView(videos);

    }
    public void setRecyclerView(ArrayList<PlaylistVideos> videoData){
        RecyclerView recyclerView;
        PlaylistVideosAdapter playlistVideosAdapter = new PlaylistVideosAdapter(videoData);



        recyclerView = (RecyclerView) findViewById(R.id.playlist_videos_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(playlistVideosAdapter);

    }
    public void retrieveVideos(){

        View view  = getWindow().findViewById(R.id.view_playlist_videos);

        new GetPlaylistVideosTask(getApplicationContext(),view,playlistId,ViewPlaylistVideos.this).execute();

    }
    public void addPlaylistToDb(){

        DbHelper dbHelper = new DbHelper(getApplicationContext());
        dbHelper.addPlaylist(playlistTitle,playlistId,thumbnailUrl,playlistChannel);

    }

}
