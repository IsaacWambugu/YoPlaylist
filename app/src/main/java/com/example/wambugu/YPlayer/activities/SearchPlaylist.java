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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.RecyclerView.SearchPlaylistAdapter;
import com.example.wambugu.YPlayer.Singletons.QueryCleanerSingleton;
import com.example.wambugu.YPlayer.UIBackgroundThreads.SearchPlaylistTask;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;
import com.example.wambugu.YPlayer.interfaces.GetPlaylistResults;

import java.util.ArrayList;

/**
 * Created by wacuka on 29/11/2017.
 */

public class SearchPlaylist extends AppCompatActivity implements GetPlaylistResults {

    private TextView search_playlist;
    private RecyclerView recyclerView;
    private ArrayList<com.example.wambugu.YPlayer.RecyclerView.SearchPlaylist> playlist_data =new ArrayList<>();;
    private ImageView add_button ;
    private Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened  = false;
    private TextView editSearch;
    private TextView toolbarTitle;
    private View view;






    private SearchPlaylistAdapter searchPlaylistAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_playlist);
       //  setToolbar();
        //setGuillotineMenu();
        setUIConfigurations();


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
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);




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
   /* public void setGuillotineMenu(){

        ButterKnife.bind(this);

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine_search_playlist, null);
        root.addView(guillotineMenu);

       GuillotineAnimation guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        


        //set ic_action_search parameters
        //sort by
        final String[] sortBy  = {"Date","Rating","Relevance","Title","No of Videos","Views"};

        //safe ic_action_search
        final String[] safeSearch  = {"None","Moderate","Strict"};

        //No of results
        final String[] maxResults  = {"5","10","20","30","40","50"};


        //configure spinners
        //sort by spinner
        ArrayAdapter sortByAdapter  = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,sortBy);

        Spinner  sortBySpinner = (Spinner) guillotineMenu.findViewById(R.id.playlist_sort_spinner);
        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //update shared preferences
                new SharedPreferenceManager(getApplicationContext()).setPlaylistSortBy(sortBy[i]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(sortByAdapter);




        //safeSearch Spinner
        ArrayAdapter safeSearchAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,safeSearch);
        Spinner safeSearchSpinner  = (Spinner) guillotineMenu.findViewById(R.id.playlist_safe_spinner);
        safeSearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                new SharedPreferenceManager(getApplicationContext()).setPlaylistSafeSearch(safeSearch[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        safeSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        safeSearchSpinner.setAdapter(safeSearchAdapter);


        //maxresults spinner
        ArrayAdapter maxResultsAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,maxResults);
        Spinner maxResultSpinner  = (Spinner) guillotineMenu.findViewById(R.id.playlist_max_spinner);
        maxResultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                new SharedPreferenceManager(getApplicationContext()).setPlaylistMaxResult(maxResults[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        maxResultsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxResultSpinner.setAdapter(maxResultsAdapter);




    }
    public SearchPlaylist(){




    }
*/

    public void setUIConfigurations(){





        toolbar = (Toolbar) findViewById(R.id.search_playlist_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchPlaylist.this,Main.class);
                startActivity(intent);
            }
        });



        //   search_playlist = (EditText) findViewById(R.id.internet_playlist_search_field);
     //   search_playlist.setFocusableInTouchMode(true);
       // search_playlist.requestFocus();

        /*search_playlist.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(getApplicationContext(), search_playlist.getText(), Toast.LENGTH_SHORT).show();
                    String unverified_search_text = search_playlist.getText().toString();
                    String verified_search_text = unverified_search_text.replaceAll(" ","%20");

                   // new SearchPlaylist().SearchPlaylistTask(getApplicationContext());

                    SearchPlaylist spa = new SearchPlaylist();

                    new SearchPlaylistTask(getApplicationContext(),view,SearchPlaylist.this,verified_search_text).execute("sdsd");
                    return true;
                }
                return false;
            }
        });*/

    }

    @Override
    public void getPlaylistResults(ArrayList<String> result){



        Log.e("Almost Showing recycler view",result.toString());

        playlist_data.clear();
         for(String x :result){

             String[] separatedPlaylistData  = x.split("##");

            Log.e("PASSING TO ADAPTER",separatedPlaylistData[0]);
             Log.e("PASSING TO ADAPTER",separatedPlaylistData[1]);
             Log.e("PASSING TO ADAPTER",separatedPlaylistData[2]);
             Log.e("PASSING TO ADAPTER",separatedPlaylistData[3]);



            playlist_data.add(new com.example.wambugu.YPlayer.RecyclerView.SearchPlaylist(separatedPlaylistData[0],separatedPlaylistData[1],separatedPlaylistData[2],separatedPlaylistData[3]));
         }



        showRecyclerView();
    }

    public void showRecyclerView(){



        searchPlaylistAdapter = new SearchPlaylistAdapter(playlist_data);
        recyclerView = (RecyclerView) findViewById(R.id.search_playlist_list);

        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchPlaylistAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){
                final com.example.wambugu.YPlayer.RecyclerView.SearchPlaylist searchPlaylist = playlist_data.get(position);

                //   Toast.makeText(getApplicationContext(),searchVideo.getVideoTitle(),Toast.LENGTH_LONG).show();

                ImageView imageView= (ImageView) view.findViewById(R.id.search_playlist_thumbnail);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Single click on text : "+position,Toast.LENGTH_LONG).show();

                        Intent intent  =  new Intent(SearchPlaylist.this,ViewPlaylistVideos.class);

                        Log.e("Passing...","Wait below:");
                        Log.e("Passing playlistId",searchPlaylist.getPlaylistId());
                        Log.e("Passing playlisttitle",searchPlaylist.getPlaylistTitle());



                        intent.putExtra("Passed_Playlist_Id",searchPlaylist.getPlaylistId());
                        intent.putExtra("Passed_Playlist_Title",searchPlaylist.getPlaylistTitle());
                        intent.putExtra("passed_playlist_thumbnail_url",searchPlaylist.getThumbnailURL());
                        intent.putExtra("passed_playlist_channel",searchPlaylist.getChannel());


                       startActivity(intent);

                        //new StartCustomYPlayer().startYouTubeVideoPlayer(SearchPlaylist.this,searchPlaylist.getPlaylistId());



                    }
                });
            }
            @Override
            public void onLongClick(View view , int position){


            }





        }));
    }
    private void parametersPopUpWindow(){

        LayoutInflater layoutInflater= this.getLayoutInflater();


        AlertDialog.Builder builder = new AlertDialog.Builder(SearchPlaylist.this,R.style.AlertDialogCustom);
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
        view  = getWindow().findViewById(R.id.activity_search_playlist_view);
        String cleanQuery  = QueryCleanerSingleton.getInstance().cleanQuery(query);
        new SearchPlaylistTask(getApplicationContext(),view,SearchPlaylist.this,cleanQuery).execute();







    }
    //hide keyboard
    public void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }




}


