package com.example.wambugu.YPlayer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wambugu.YPlayer.Utility.TimeStamp;

import java.util.ArrayList;

/**
 * Created by wacuka on 29/10/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private SQLiteDatabase db;
    ArrayList<String> videos,playlists;

    private static final String DB_NAME  = "254YPlaylist.db";
    private static final int VERSION = 1;
    private static final String TABLE1 = "YoutubeVideos";
    private static final String TABLE2 = "YoutubePlayLists";
    private static final String TABLE3 = "CustomPlayLists";
    private static final String TABLE4  = "CustomVideos";
    private Static final String TABLE5  = "CustomPlayListsCustomVideos";





    public DbHelper(Context context){

        super(context,DB_NAME,null,VERSION);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                 db.execSQL("CREATE TABLE "+TABLE1+"("+

                                "VIDEO_ID TEXT PRIMARY KEY," +
                                "VIDEO_TITLE TEXT  ," +
                                "VIDEO_THUMBNAIL_URL TEXT ," +
                                "VIDEO_DURATION TEXT ," +
                                "VIDEO_CHANNEL TEXT ," +
                                "DATE_ADDED DATETIME DEFAULT CURRENT_TIMESTAMP)" );


                 db.execSQL("CREATE TABLE "+TABLE2+"("+
                         "PLAYLIST_ID TEXT PRIMARY KEY," +
                         "PLAYLIST_TITLE TEXT ,"+
                         "PLAYLIST_THUMBNAIL_URL TEXT ," +
                         "PLAYLIST_CHANNEL TEXT ,"+
                         "VIDEO_COUNT TEXT ," +
                         "DATE_ADDED DATETIME DEFAULT CURRENT_TIMESTAMP)");

                 db.execSQL(
                       "CREATE TABLE "+TABLE3+"("+
                               "PLAYLIST_ID INT PRIMARY KEY AUTOINCREMENT, "+
                               "PLAYLIST_TITLE TEXT," +
                               "DATE_ADDED DATETIME DEFAULT CURRENT_TIMESTAMP)"
                        );

                 db.execSQL("CREATE TABLE "+TABLE4+"("+
                      "VIDEO_ID TEXT PRIMARY KEY, "+
                      "VIDEO_TITLE TEXT, "+
                      "VIDEO_THUMBNAIL_URL TEXT, "+
                      "VIDEO_DURATION TEXT, "+
                      "VIDEO_CHANNEL TEXT) "
                      );
                 db.execSQL("CREATE TABLE "+TABLE5+"("+
                 "PLAYLIST_ID TEXT FOREIGN KEY REFERENCES "+TABLE3+"(PLAYLIST_ID) ,"+
                 "VIDEO_ID TEXT FOREIGN KEY REFERENCES "+TABLE4+"(VIDEO_ID)" +
                         "PRIMARY KEY(PLAYLIST_ID,VIDEO_ID) )");





    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
    public ArrayList<String> getCustomPlaylists()throws SQLiteException{

        ArrayList<String> playLists  = ArrayList<>();


        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT PLAYLIST_ID,PLAYLIST_TITLE FROM "+TABLE3,null);


        if(cursor.getCount()>0){

            for(int  i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                String details  = Integer.toString(cursor.getInt(0)) +"###"+ (cursor.getString(1)) ;
                playLists.add(details);
            }
        }
        cursor.close();

        return playLists;


    }
    public Integer getCustomPlaylistVideos(int playlistId){

        db= this.getReadableDatabase();

        String details;
        Cursor cursor  = db.rawQuery("SELECT " +
                "CustomPlayListsCustomVideos.VIDEO_ID," +
                "VIDEO_TITLE," +
                "VIDEO_THUMBNAIL," +
                "VIDEO_DURATION," +
                "VIDEO_CHANNEL " +
                "FROM CustomPlayListsCustomVideos " +
                "INNER JOIN CustomVideos ON CustomPlayListsCustomVideos.VIDEO_ID = CustomVideos.VIDEO_ID " +
                "WHERE playlist_videos.PLAYLIST_ID ="+playlistId+,null);

        if(cursor.getCount()>0){
            for(int  i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                details  = (cursor.getString(0)) +"##"+ (cursor.getString(1)) + "##" + (cursor.getString(2)) +"##"+ (cursor.getString(3))+"##"+ (cursor.getString(4)) ;

                videos.add(details);



            }
        }
        cursor.close();
        return videos;



            }
    public void addCustomPlayList(String playlistTitle){

        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PLAYLIST_TITLE",playlistTitle);
        contentValues.put("DATE_ADDED",new TimeStamp().getTimestamp());

        db.insert(TABLE3,null,contentValues);
        db.close();



    }
    public boolean renameCustomPlaylist(String id, String playListName){
        db = this.getReadableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE3 +
                    "SET PLAYLIST_TITLE =" + playListName + "WHERE PLAYLIST_ID = " + id);
            return  true;

        }catch (SQLiteException sqlE){

            return false;,
        }
    }
    public boolean deleteCustomPlaylist(String playlistId){

        db = this.getReadableDatabase();

        try {

            db.execSQL("DELETE FROM "+TABLE4+
                    "WHERE PLAYLIST_ID = "+playlistId);
            db.execSQL("DELETE FROM " + TABLE3 +
                    "WHERE PLAYLIST_ID = " + playlistId);
            return  true;

        }catch (SQLiteException sqlE){

            return false;
        }
    }
    public boolean deleteCustomPlaylistContents(String playlistId) throws SQLiteException {
       db = this.getReadableDatabase();
        db.execSQL("DELETE FROM ");
    }
    public void addVideoToCustomPlayList(String videoId,String videoTitle,String videoThumbnail,String videoDuration,String videoChannel){

        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("VIDEO_ID",videoId);
        contentValues.put("VIDEO_TITLE",videoTitle);
        contentValues.put("VIDEO_THUMBNAIL_URL",videoThumbnail);
        contentValues.put("VIDEO_DURATION",videoDuration);
        contentValues.put("VIDEO_CHANNEL",videoChannel);

        db.insert(T)

    }
    public void addPlaylist(String title,String playlist_id,String thumbnail_url,String playlist_channel){

        db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("PLAYLIST_ID",playlist_id);
        contentValues.put("PLAYLIST_TITLE",title);
        contentValues.put("PLAYLIST_THUMBNAIL_URL",thumbnail_url);
        contentValues.put("PLAYLIST_CHANNEL",playlist_channel);
        contentValues.put("DATE_ADDED",new TimeStamp().getTimestamp());

        db.insert(TABLE2,null,contentValues);
        db.close();

    }


    public void addVideo(String video_id,String video_title,String thumbnail_url,String video_duration,String video_channel ){
        db =this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("VIDEO_ID",video_id);
        contentValues.put("VIDEO_TITLE",video_title);
        contentValues.put("VIDEO_THUMBNAIL_URL",thumbnail_url);
        contentValues.put("VIDEO_DURATION",video_duration);
        contentValues.put("VIDEO_CHANNEL",video_channel);

        contentValues.put("DATE_ADDED",new TimeStamp().getTimestamp());

        db.insert(TABLE1,null,contentValues);
        db.close();

    }
    public boolean videoChecker(String video_id){
        db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE1 +" WHERE VIDEO_ID = \"" + video_id+"\";";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount()<= 0){
            cursor.close();
            db.close();
            return true;

        }

        cursor.close();
        db.close();
        return false;



    }
    public ArrayList<String> getVideos(){
        String details;


        db = this.getReadableDatabase();


        Cursor cursor  = db.rawQuery("SELECT VIDEO_ID, VIDEO_TITLE ,VIDEO_THUMBNAIL_URL ,VIDEO_DURATION ,VIDEO_CHANNEL FROM "+TABLE1,null);


        videos =new  ArrayList<>();

        if(cursor.getCount()>0){

            for(int  i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                details  = (cursor.getString(0)) +"##"+ (cursor.getString(1)) + "##" + (cursor.getString(2)) +"##"+ (cursor.getString(3))+"##"+ (cursor.getString(4)) ;

                videos.add(details);



            }
        }
        cursor.close();
        return videos;


    }
    public ArrayList<String> getPlayLists(){
        String playlist_details;


        db = this.getReadableDatabase();

        Cursor cursor  = db.rawQuery("SELECT PLAYLIST_ID , PLAYLIST_TITLE , PLAYLIST_THUMBNAIL_URL ,PLAYLIST_CHANNEL  FROM "+TABLE2,null);

        playlists =new  ArrayList<>();



        if(cursor.getCount()>0){

            for(int  i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                playlist_details = (cursor.getString(0)) +"##"+ (cursor.getString(1))+"##"+(cursor.getString(2))+"##"+(cursor.getString(3));
                playlists.add(playlist_details);



            }
        }

        cursor.close();
        return playlists;


    }
    public void removeAddedVideo(String videoId){

        db = this.getReadableDatabase();
        db.execSQL(
                "DELETE FROM "+ TABLE1 + " WHERE " +
                        "VIDEO_ID = "+ " '"+videoId +"'"




        );
        db.close();
    }
}
