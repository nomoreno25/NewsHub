package com.hailv.newshub.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "com.hailv.newshub";


    private static final String SAVE_OBJECT_USER = "objectUser";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setNews(News user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SAVE_OBJECT_USER,json);
        editor.commit();
    }

    public News getNews(){
        Gson gson = new Gson();
        String json = pref.getString(SAVE_OBJECT_USER, "");
        News obj = gson.fromJson(json, News.class);
        return obj;
    }

    public void saveBol(String key, boolean b){
        editor.putBoolean(key,b);
        editor.commit();
    }

    public boolean getBol(String key){
        return pref.getBoolean(key,false);
    }



}
