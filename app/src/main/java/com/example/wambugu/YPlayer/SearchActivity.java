package com.example.wambugu.YPlayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.example.wambugu.YPlayer.RecyclerView.SearchVideoResults;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.Utility.JSONExtractor;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;
import com.example.wambugu.YPlayer.RecyclerView.SearchVideoResultsAdapter;
import com.example.wambugu.YPlayer.Utility.StartCustomYPlayer;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wacuka on 17/11/2017.
 */

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText videoSearch;
    public ArrayList<SearchVideoResults> video_data;
    CatLoadingView mview = new CatLoadingView();
    PopupMenu popupMenu;
    ImageView addbutton;


    private DbHelper dbHelper  = new DbHelper(SearchActivity.this);


    SearchVideoResultsAdapter searchVideoResultsAdapter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


     //   getVideos();

        setUIConfigurations();

        //showRecyclerView();




    }
    public void setUIConfigurations(){



        //set up the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        try {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }catch(java.lang.NullPointerException e) {

            e.printStackTrace();
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        videoSearch = (EditText) findViewById(R.id.search_field);
        videoSearch.setFocusableInTouchMode(true);
        videoSearch.requestFocus();

        videoSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(getApplicationContext(), videoSearch.getText(), Toast.LENGTH_SHORT).show();
                    String unverified_search_text = videoSearch.getText().toString();
                    String verified_search_text = unverified_search_text.replaceAll(" ","%20");

                    new AsyncCaller(verified_search_text).execute();
                    return true;
                }
                return false;
            }
        });
/*
        videoSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)&&(keyCode== EditorInfo.IME_ACTION_DONE)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), videoSearch.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        */

        /*videoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(),charSequence.toString(),1000).show();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        */

       // videoSearch.setText("");


        //set the ic_action_search spinner


       final String[] categories  = {"Video","Playlist"};


        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,categories);


        Spinner categorySpinner = (Spinner) findViewById(R.id.search_spinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){



            //Performing action onItemSelected and onNothing selected
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
                Toast.makeText(getApplicationContext(), categories[position], Toast.LENGTH_LONG).show();
                toolbar.setTitle(categories[position]+" ic_action_search ");
                videoSearch.setHint("Search "+categories[position]+"...");

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }


        });

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);




    }
    public void setData(ArrayList<SearchVideoResults> sent_data){

        this.video_data =sent_data;

    }
    public void getVideos(){
        video_data = new ArrayList<>();

        video_data.add(new SearchVideoResults("dkjkjkjf","This is the title ","https://gifs.com/watch?v=JQb3ICATNTM","2M:40S","MY Channel","1000 views"));
        video_data.add(new SearchVideoResults("dkjkjkjf","This is the title 2 ","https://gifs.com/watch?v=JQb3ICATNTM","2M:40S","MY Channel","5000 views"));
    }
    public void showRecyclerView(){


        searchVideoResultsAdapter = new SearchVideoResultsAdapter(video_data);
        recyclerView = (RecyclerView) findViewById(R.id.search_result_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(searchVideoResultsAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){
                final SearchVideoResults searchVideoResults = video_data.get(position);

             //   Toast.makeText(getApplicationContext(),searchVideoResults.getVideoTitle(),Toast.LENGTH_LONG).show();

                ImageView imageView= (ImageView) view.findViewById(R.id.search_video_thumbnail);
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(),"Single click on text "+position,Toast.LENGTH_LONG).show();
                       new StartCustomYPlayer().startYouTubeVideoPlayer(SearchActivity.this, searchVideoResults.getVideoId());
                    }
                });



                addbutton = (ImageView) view.findViewById(R.id.addSearchVideoButton);










                addbutton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        showPopWindow(searchVideoResults.getVideoId(), searchVideoResults.getVideoTitle(), searchVideoResults.getThumbnailURL(), searchVideoResults.getVideoDuration(), searchVideoResults.getChannel());
                        //new StartCustomYPlayer().startYouTubeVideoPlayer(SearchActivity.this,searchVideoResults.getVideoId());
                    }
                });





            }
            @Override
            public void onLongClick(View view , int position){


            }





        }));
    }


//pop window when addbutton is clicked
    public void showPopWindow(final String id, final String title, final String thumbnail_url, final String duration, final String channel){
        popupMenu = new PopupMenu(SearchActivity.this,addbutton);
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


        dbHelper.addVideo(video_id,video_title,video_thumbnail_url,video_duration,video_channel);

    }








    //to handle UI threading when user searchesfor playlist/video
    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        //ProgressDialog pdLoading = new ProgressDialog(Main.this);

        ANResponse<JSONObject> response;
        JSONObject jsonObject;
        ArrayList<SearchVideoResults> video_data ;
        String search_text;



          private AsyncCaller(String search_text){
              this.search_text = search_text;
          }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mview.show(getSupportFragmentManager(),"");

            //this method will be running on UI thread
            // pdLoading.setMessage("\tLoading...");
            // pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

           ArrayList<String> video_ids = new ArrayList<>();
            ArrayList<String> video_info ;
            video_data = new ArrayList<>();


            String video_url  = "https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&key=AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo&id=";
            String search_url = "https://www.googleapis.com/youtube/v3/ic_action_search?part=snippet&type=video&key=AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo&id=rWOllhAi_lY&maxResults=20&q=";


            AndroidNetworking.initialize(getApplicationContext());

            ANRequest request = AndroidNetworking.get(search_url+search_text).setPriority(Priority.IMMEDIATE).build();
            response = request.executeForJSONObject();

            if (response.isSuccess()) {
                jsonObject = response.getResult();

                Log.e("SearchActivity",jsonObject.toString());


               video_ids =  new JSONExtractor().videoSearchResult(jsonObject);

                Log.e("VIDEOIDS",video_ids.toString());


            } else {
                ANError error = response.getError();

            }


            for(String videos:video_ids) {

                //get video details and statistics of each individual video
                request = AndroidNetworking.get(video_url+videos).setPriority(Priority.IMMEDIATE).build();
                response = request.executeForJSONObject();

                if (response.isSuccess()) {
                    jsonObject = response.getResult();

                    Log.e("GET INDIVIDUAL VIDEO DATA",jsonObject.toString());


                   // video_info = new JSONExtractor().individualVideoDetails(jsonObject);



              //      video_data.add(new SearchVideoResults(video_info.get(0),video_info.get(1),video_info.get(2),video_info.get(3),video_info.get(4),video_info.get(5)) );

                  // Log.e("CHECK THIS OUT!!!",video_info.get(5));

                    // Toast.makeText(getApplicationContext(),video_info.toString(),1000).show();
                } else {
                    ANError error = response.getError();
                   // Toast.makeText(getApplicationContext(), error.toString(), 1000).show();
                }

            }


            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //setData(video_data);
            showRecyclerView();

            mview.dismiss();

            //this method will be running on UI thread
           // Toast.makeText(getApplicationContext(),jsonObject.toString(),1000).show();

            //pdLoading.setMessage(jsonObject.toString());
            // pdLoading.show();
            // pdLoading.dismiss();

        }

    }


}
