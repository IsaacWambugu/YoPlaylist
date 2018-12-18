package com.example.wambugu.YPlayer.activities;


import com.example.wambugu.YPlayer.Fragments.PlaylistFragment;
import com.example.wambugu.YPlayer.Fragments.VideoFragment;
import com.example.wambugu.YPlayer.R;
import com.example.wambugu.YPlayer.SearchActivity;
import com.example.wambugu.YPlayer.Utility.JSONPlaylistDetails;
import com.example.wambugu.YPlayer.Utility.Test;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;



//floating button
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends AppCompatActivity {

    private FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3;
    private FloatingActionMenu materialDesignFAM;
    private PopupWindow popupWindow;
    protected Dialog dialog;
    private Button add_button,cancel_button;



     EditText youtube_url;








    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUIConfigurations();

      //  new VideoDetails().getVideoTitle("fjjf");

       //Toast.makeText(getApplicationContext(),new Test().getVideoTitle("https://m.youtube.com/watch?v=i1Be8bORaAs",getApplicationContext())
       //        ,Toast.LENGTH_LONG).show();





        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //floating button onclicklistener
        floatingActionButton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                closeFAB();
                String option  ="video";
                showAddOptions(option);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                closeFAB();
                String option  = "playlist";
                showAddOptions( option);



            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                closeFAB();
                createPlaylistDialog();

            }
        });














    }

    public void showAddOptions(final String option){

        AlertDialog.Builder build  = new AlertDialog.Builder(Main.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        build.setIcon(android.R.drawable.ic_input_add);

        build.setTitle("ADD "+ option.toUpperCase());
        build.setMessage("How do you wish to add your "+option+" ?");
        build.setCancelable(true);



        build.setPositiveButton(option.toUpperCase()+" URL",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch(option){
                    case "video":
                        addVideoDialog();
                        break;
                    case "playlist":
                        addPlaylistDialog();
                        break;
                }


            }
        });

        build.setNegativeButton("SEARCH "+option.toUpperCase(),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (option) {
                    case "video":

                        Intent intent = new Intent(Main.this, SearchVideo.class);
                        startActivity(intent);
                        break;
                    case "playlist":

                      intent  =  new Intent(Main.this,SearchPlaylist.class);
                        startActivity(intent);
                        break;
                }

            }
        });

        AlertDialog addDialog = build.create();
        addDialog.show();



    }


    public void addVideoDialog(){

        dialog  = new Dialog(Main.this,android.R.style.Theme_Holo_Dialog );
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Add Youtube Video");
        dialog.setContentView(R.layout.popup_addvideo);
        dialog.setCanceledOnTouchOutside(false);


        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        youtube_url = (EditText) dialog.findViewById(R.id.video_link);
        add_button = (Button) dialog.findViewById(R.id.add_video_button);
        cancel_button = (Button) dialog.findViewById(R.id.cancel_video_button);

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                String url = youtube_url.getText().toString();


                //validate youtube link
                String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.


                if (matcher.find()) {



                   //pass youtube url for checking and add to database
                    new Test().getVideoTitle(getApplicationContext(),url,matcher.group(),Main.this);


                }
                else

                    Toast.makeText(getApplication(),"Enter a valid url",1000).show();



            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void showAddVideoPopWindow(){
        CoordinatorLayout mainLayout  = (CoordinatorLayout) findViewById(R.id.main_content);

        //inflate layout of the popup
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView  = inflater.inflate(R.layout.popup_addvideo,null);

        //create popup window
        int width  = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height  = RelativeLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;//taps outside dismisses it
        popupWindow =new PopupWindow(popupView,width,height,true);
        popupWindow.showAtLocation(mainLayout, Gravity.CENTER,0,0);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);



        popupView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
    }
    public void addPlaylistDialog(){

        dialog  = new Dialog(Main.this,android.R.style.Theme_Holo_Dialog );

        dialog.setTitle("ADD YOUTUBE PLAYLIST");

        dialog.setContentView(R.layout.popup_addplaylist);
        dialog.setCanceledOnTouchOutside(false);


        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        youtube_url = (EditText) dialog.findViewById(R.id.playlist_link);
        add_button = (Button) dialog.findViewById(R.id.add_playlist_button);
        cancel_button = (Button) dialog.findViewById(R.id.cancel_playlist_button);

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                String url = youtube_url.getText().toString();

                new JSONPlaylistDetails().getPlaylistDetails(getApplicationContext(),url,Main.this);


                //validate youtube link
               /* String regex = "/(?:youtube\\.com.*(?:\\?|&)(?:list)=)((?!videoseries)[a-zA-Z0-9_]*)/g";
                Pattern compiledPattern = Pattern.compile(regex);
                Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.




                if (matcher.find()) {



                    //pass youtube url for checking and add to database
                   // new Test().getVideoTitle(getApplicationContext(),url,matcher.group(),Main.this);
                    new JSONPlaylistDetails().getPlaylistDetails(getApplicationContext(),matcher.group(1));

                }
                else

                    Toast.makeText(getApplication(),"Enter a valid playlist url",1000).show();
                    */



            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }
    public void setUIConfigurations(){
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);


       materialDesignFAM.setClosedOnTouchOutside(true);

    }
    public void createPlaylistDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setTitle("Create Playlist");
        builder.setView(R.layout.dialog_create_playlist);
        builder.setPositiveButton("CREATE",new  DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog  = builder.create();
        alertDialog.show();


        //R.style.AlertDialogCustom



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);



    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position){
                case 0:
                    VideoFragment tab1 = new VideoFragment();
                    return tab1;
                case 1:
                    PlaylistFragment tab2  = new PlaylistFragment();
                    return tab2;
                case 2:
                    VideoFragment tab3 =new VideoFragment();
                    return tab3;




            }
            return null;


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "VIDEOS";
                case 1:
                    return "PLAYLISTS";
                case 2:
                    return "MY PLAYLISTS";

            }
            return null;
        }
    }
    public void closeFAB(){
        materialDesignFAM.close(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        return super.onTouchEvent(event);
    }


}
