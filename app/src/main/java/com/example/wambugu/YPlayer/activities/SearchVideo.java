package com.example.wambugu.YPlayer.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;
import com.example.wambugu.YPlayer.RecyclerView.SearchVideoResults;
import com.example.wambugu.YPlayer.Singletons.QueryCleanerSingleton;
import com.example.wambugu.YPlayer.UI.AppSnackBar;
import com.example.wambugu.YPlayer.UIBackgroundThreads.GetRemainingVideosTask;
import com.example.wambugu.YPlayer.UIBackgroundThreads.SearchVideoTask;
import com.example.wambugu.YPlayer.RecyclerView.SearchVideoResultsAdapter;
import com.example.wambugu.YPlayer.Utility.StartCustomYPlayer;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.interfaces.GetRemainingVideos;
import com.example.wambugu.YPlayer.interfaces.GetVideoResults;

import java.util.ArrayList;

/**
 * Created by wacuka on 26/12/2017.
 */

public class SearchVideo extends AppCompatActivity implements GetVideoResults,GetRemainingVideos {

    private Toolbar toolbar;
    private MenuItem mSearchAction ;
    private  boolean isSearchOpened  = false;
    private EditText editSearch;
    private TextView toolbarTitle;

    private SearchVideoResultsAdapter searchVideoResultsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<SearchVideoResults> videoData;
    private ImageView add_button;

    private PopupMenu popupMenu;
    private View view ;







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);

        setUIConfigurations();




    }
    private void setUIConfigurations(){


        //toolbar

       toolbar = (Toolbar) findViewById(R.id.search_video_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchVideo.this,Main.class);
                startActivity(intent);
            }
        });



        // getSupportActionBar().setDisplayShowHomeEnabled(true);









    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id  = item.getItemId();

        switch(id){
            case R.id.action_search:
                handleMenuSearch();
                return true;

            case R.id.action__settings:
                parametersPopUpWindow();

                return true;


        }
        return super.onOptionsItemSelected(item);
    }
    protected void handleMenuSearch(){

        ActionBar actionBar = getSupportActionBar();




            if (isSearchOpened) {

                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);


                toolbarTitle.setVisibility(View.VISIBLE);
                mSearchAction.setIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_action_search));
                isSearchOpened = false;


            } else {
               actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setCustomView(R.layout.search_bar);
                actionBar.setDisplayShowTitleEnabled(false);

                editSearch = (EditText) actionBar.getCustomView().findViewById(R.id.search_field);
                editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {

                           Toast.makeText(getApplicationContext(),textView.getText().toString(),1000).show();
                            beginSearchQuery(textView.getText().toString());
                            hideKeyBoard();

                            return true;
                        }
                        return false;
                    }
                });

                editSearch.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);


                toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                toolbarTitle.setVisibility(View.GONE);

                mSearchAction.setIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_action_close));
                isSearchOpened = true;

            }

    }
    private void parametersPopUpWindow(){

        LayoutInflater layoutInflater= this.getLayoutInflater();


        AlertDialog.Builder builder = new AlertDialog.Builder(SearchVideo.this,R.style.AlertDialogCustom);
        builder.setTitle("Search Parameters");

        builder.setIcon(R.drawable.ic_action_trash);
        builder.setView(layoutInflater.inflate(R.layout.popupwindow_video_search_parameters,null));
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button positive_button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negative_button  = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positive_button.getLayoutParams();
        layoutParams.weight=10;
        positive_button.setLayoutParams(layoutParams);
        negative_button.setLayoutParams(layoutParams);

    }


    public void beginSearchQuery(String query){
        view  = getWindow().findViewById(R.id.activity_search_video_view);
        String cleanQuery  = QueryCleanerSingleton.getInstance().cleanQuery(query);
        new SearchVideoTask(getApplicationContext(),view,SearchVideo.this,cleanQuery).execute();







    }
    @Override
    public void getVideoResults(Pair<ArrayList<String>,ArrayList<String>> pair){

        videoData = new ArrayList<>();


        Log.e("FIRST PAIR",pair.first.toString());
        Log.e("SECOND PAIR",pair.second.toString());

        for(String x:pair.first){

            String[] data = x.split("###");

            videoData.add(new SearchVideoResults(data[0]
                    ,data[1],data[2],data[3],data[4],data[5])
            );
        }
        showRecyclerView(videoData);
        getRemainingResults(pair.second);







    }
    public void getRemainingResults(ArrayList<String> videoIds){

        for(String x : videoIds){
           new GetRemainingVideosTask(getApplicationContext(),SearchVideo.this,x).execute();
        }

    }
    public void showRecyclerView(final ArrayList<SearchVideoResults> videoData){



        searchVideoResultsAdapter = new SearchVideoResultsAdapter(videoData);
        recyclerView = (RecyclerView) findViewById(R.id.search_video_result_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchVideoResultsAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){
                final SearchVideoResults searchVideoResults = videoData.get(position);

                //   Toast.makeText(getApplicationContext(),searchVideoResults.getVideoTitle(),Toast.LENGTH_LONG).show();

                ImageView imageView= (ImageView) view.findViewById(R.id.search_video_thumbnail);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(),"Single click on text "+position,Toast.LENGTH_LONG).show();
                        new StartCustomYPlayer().startYouTubeVideoPlayer(SearchVideo.this, searchVideoResults.getVideoId());
                    }
                });



               add_button = (ImageView) view.findViewById(R.id.addSearchVideoButton);










                add_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        showPopWindow(searchVideoResults.getVideoId(),searchVideoResults.getVideoTitle(),searchVideoResults.getThumbnailURL(),searchVideoResults.getVideoDuration(),searchVideoResults.getChannel());
                        //new StartCustomYPlayer().startYouTubeVideoPlayer(SearchActivity.this,searchVideoResults.getVideoId());
                    }
                });





            }
            @Override
            public void onLongClick(View view , int position){


            }





        }));
    }
    @Override
    public void getRemainingVideos(String response){
        String[] splitVideoData  = response.split("###");


        videoData.add(new SearchVideoResults(splitVideoData[0],splitVideoData[1],splitVideoData[2],splitVideoData[3],splitVideoData[4],splitVideoData[5]));
        searchVideoResultsAdapter.notifyItemInserted(videoData.size()-1);

    }

    public void showPopWindow(final String id, final String title, final String thumbnail_url, final String duration, final String channel){
        popupMenu = new PopupMenu(SearchVideo.this,add_button);
        popupMenu.getMenuInflater().inflate(R.menu.add_video_option,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getTitle().toString()){

                    case "Add video":
                        Log.e("ADD","Add video");

                        addVideoToDb(id,title,thumbnail_url,duration,channel);
                        break;
                    case "Show Stats":
                        Log.e("ADD","Stats");
                        break;


                }
                return true;
            }
        });

        popupMenu.show();
    }
    public void addVideoToDb(String video_id ,String video_title,String video_thumbnail_url,String video_duration,String video_channel){
        view  = getWindow().findViewById(R.id.activity_search_video_view);
        DbHelper dbHelper= new DbHelper(getApplicationContext());
        dbHelper.addVideo(video_id,video_title,video_thumbnail_url,video_duration,video_channel);
        new AppSnackBar(view,video_title+" ADDED").showSnackBar();


    }

    //hide keyboard
    public void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }



}
