package com.example.wambugu.YPlayer.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wambugu.YPlayer.R;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by wacuka on 28/10/2017.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    private ArrayList<DataModel> privatearray;
    Context mcontext;


    private static class ViewHolder{
        TextView txtName ;
        TextView txtVideo_id;
        ImageView thumbnail;



    }
    public CustomAdapter(ArrayList<DataModel> data , Context context){
        super(context,R.layout.row_layout,data);
        this.dataSet = data;
        this.mcontext = context;
        privatearray = new ArrayList<>();
        privatearray.addAll(data);




    }
    @Override
    public void onClick(View v){
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel =(DataModel)object;


        switch(v.getId()){
            case(R.id.video_name):
                Toast.makeText(getContext(), "Video detail" +dataModel.getVideoId(), Toast.LENGTH_LONG).show();

        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position ,View convertView, ViewGroup parent ){

        DataModel dataModel  = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if(convertView  == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater  = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.row_layout,parent,false) ;

            viewHolder.txtName  = (TextView) convertView.findViewById(R.id.video_name);
            viewHolder.thumbnail = (ImageView)  convertView.findViewById(R.id.thumbnail);
            viewHolder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            result = convertView;
           convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;

        }

        viewHolder.txtName.setText(dataModel.getName());

        String thumbnail_url  = "https://img.youtube.com/vi/"+dataModel.getVideoId()+"/0.jpg";




        viewHolder.txtName.setOnClickListener(this);

        Glide.with(getContext()).load(thumbnail_url).fitCenter().into( viewHolder.thumbnail);


        return convertView;


    }
    public void filter(String charText){
        charText = charText.toLowerCase();
        dataSet.clear();
        if(charText.length()==0 || charText == ""|| charText==" "){
            dataSet.addAll(privatearray);

        }else{
            for(DataModel dm :privatearray){
                if(dm.getName().toLowerCase().contains(charText)){
                    dataSet.add(dm);
                    

                }

            }
        }

    }




}
