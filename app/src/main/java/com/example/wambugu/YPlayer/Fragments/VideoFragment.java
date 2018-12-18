package com.example.wambugu.YPlayer.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.RecyclerView.AddedVideos;
import com.example.wambugu.YPlayer.RecyclerView.AddedVideosAdapter;
import com.example.wambugu.YPlayer.Singletons.VideoIdHolderSingleton;
import com.example.wambugu.YPlayer.UI.AppSnackBar;
import com.example.wambugu.YPlayer.interfaces.ClickListener;
import com.example.wambugu.YPlayer.Database.DbHelper;
import com.example.wambugu.YPlayer.RecyclerView.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by wacuka on 22/11/2017.
 */

public class VideoFragment extends android.support.v4.app.Fragment{



    private ListView listView;
    private RecyclerView recyclerView;
    private ImageView added_video_options;
    private EditText videoSearch;
    private View view ;


    private AddedVideosAdapter addedVideosAdapter;
    private ArrayList<String> videos_from_db;

    ArrayList<AddedVideos> added_video_data;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view  = inflater.inflate(R.layout.fragment_video,container,false);


        getVideosFromDb();
        setUIConfigurations();

        return view;




    }
    public void setUIConfigurations(){


        videoSearch = (EditText) view.findViewById(R.id.video_search_field);
        videoSearch.setText("");





        addedVideosAdapter = new AddedVideosAdapter(added_video_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.added_videos_list);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addedVideosAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView,new ClickListener(){
            @Override
            public void onClick(View view , final int position){
                final AddedVideos addedVideos = added_video_data.get(position);



                added_video_options  =  (ImageView) view.findViewById(R.id.added_video_options);
                added_video_options.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        VideoIdHolderSingleton.getInstance().setVideoId(addedVideos.getVideoId());
                        VideoIdHolderSingleton.getInstance().setPosition(position);

                        showPopUpMenu();
                    }
                });



               // ImageView pop_menu  = (ImageView) view.findViewById(R)


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
               // playlistVid.setFilter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    public void getVideosFromDb(){

        DbHelper dbHelper = new DbHelper(getActivity());
        videos_from_db = dbHelper.getVideos();
       // videodetials = new String[5];

        added_video_data = new ArrayList<>();




        for(String videos:videos_from_db){

                   String[]  videodetials = videos.split("##");
//                    Log.e("FETCH FROM DB",videodetials[3]);
                    added_video_data.add(new AddedVideos(videodetials[0],videodetials[1],videodetials[2],videodetials[3],videodetials[4]));
                    videodetials = null;
                    // playlistVideosAdapter.notifyDataSetChanged();
                    }
    }
    public void showPopUpMenu(){

        PopupMenu popUpMenu = new PopupMenu(getActivity(),added_video_options);
        popUpMenu.getMenuInflater().inflate(R.menu.added_video_menu,popUpMenu.getMenu());

        popUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch(item.getTitle().toString()){

                    case ("Video Details"):

                        Toast.makeText(getActivity().getApplicationContext(),"Details", Toast.LENGTH_SHORT).show();
                        videoDetailsPopUpWindow();
                        break;
                    case ("Delete Video"):
                        Toast.makeText(getActivity().getApplicationContext(),"Delete", Toast.LENGTH_SHORT).show();

                        deleteConfirmationDialog();

                        break;



                }

                return true;
            }
        });
        popUpMenu.show();


    }

    public void videoDetailsPopUpWindow(){

        View popUpView  = getLayoutInflater().inflate(R.layout.video_details_pop_up,null);

        PopupWindow popupWindow = new PopupWindow(popUpView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popUpView, Gravity.CENTER,0,0);


    }
    public void deleteConfirmationDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Dialog_NoActionBar);
        builder.setTitle("DELETE VIDEO");
        builder.setMessage("Are you sure you want to delete this video?");
        builder.setIcon(R.drawable.ic_action_trash);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity().getApplicationContext(),"Deleted", Toast.LENGTH_SHORT).show();
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.added_video_view);

                addedVideosAdapter.deleteItem(VideoIdHolderSingleton.getInstance().getPosition());

                new AppSnackBar(relativeLayout,"VIDEO"+VideoIdHolderSingleton.getInstance().getVideoId()+" DELETED").showSnackBar();
                new DbHelper(getActivity()).removeAddedVideo(VideoIdHolderSingleton.getInstance().getVideoId());
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getActivity().getApplicationContext(),"Canceled to Delete", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
