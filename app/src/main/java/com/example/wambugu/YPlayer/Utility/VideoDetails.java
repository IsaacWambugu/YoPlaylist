package com.example.wambugu.YPlayer.Utility;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


import java.net.URL;

/**
 * Created by wacuka on 07/11/2017.
 */

public class VideoDetails {

    URL embededURL;
    public String getVideoTitle(final String youtubeURL ){


         try {
             new Thread() {
                 @Override
                 public void run() {
                     try {
                         embededURL = new URL("http://www.youtube.com/oembed?url=" + youtubeURL + "&format=json");

                     } catch (Exception e) {
                         //String error = e.toString();

                     }
                 }
             }.start();

             return new JSONObject(IOUtils.toString(embededURL, "UTF-8")).getString("title");


         }catch (Exception e)
         {
             return e.toString();
         }
    }

}
