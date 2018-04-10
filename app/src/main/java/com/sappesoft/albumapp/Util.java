package com.sappesoft.albumapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ismael on 07/04/2018.
 */
public class Util {

    public static boolean[] loadAlbum(String albumName, Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(albumName,0);
        int size = sharedPreferences.getInt(albumName + "_size", 0);
        boolean[] album = null;
        if(size > 0){
            album = new boolean[size];
            for(int i=0; i<size; i++){
                album[i] = sharedPreferences.getBoolean(albumName + "_" + i,false);
            }
        }
        return album;
    }

    public static boolean storeAlbum(boolean[] album, String albumName, Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(albumName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(albumName + "_size", album.length);
        for(int i=0; i<album.length; i++){
            editor.putBoolean(albumName + "_" + i, album[i]);
        }

        return editor.commit();
    }

}
