package com.example.wambugu.YPlayer.Singletons;

/**
 * Created by wacuka on 27/12/2017.
 */

public class QueryCleanerSingleton {


    private static QueryCleanerSingleton queryCleanerSingleton = new QueryCleanerSingleton();
    private QueryCleanerSingleton(){}

    public static QueryCleanerSingleton getInstance(){
        return queryCleanerSingleton;
    }
    public  String cleanQuery(String query){

       query  = query.replaceAll(" ","%20");

        return query;


    }

}
