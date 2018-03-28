package com.sappesoft.albumapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sappesoft.albumapp.R;

/**
 * Created by Ismael on 22/03/2018.
 */
public class AlbumAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private boolean mostrarRepetidas;

    public AlbumAdapter(Context context){
        mContext = context;
        mostrarRepetidas = false;
        for(int i=0; i<album.length; i++){
            album[i] = false;
        }
    }

    @Override
    public int getCount() {
        return album.length;
    }

    @Override
    public Object getItem(int position) {
        return album[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView txtFigurita;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            txtFigurita = (TextView) inflater.inflate(R.layout.figurita_layout,parent,false);
        }else{
            txtFigurita = (TextView) convertView;
        }

        boolean[] albumAMostrar;
        if(mostrarRepetidas)
            albumAMostrar = repetidas;
        else
            albumAMostrar = album;

        txtFigurita.setText(Integer.toString(position));
        if(albumAMostrar[position]){
            txtFigurita.setTextColor(Color.GREEN);
        }else{
            txtFigurita.setTextColor(Color.RED);
        }

        return txtFigurita;
    }


    private boolean[] album = new boolean[670];
    private boolean[] repetidas = new boolean[670];

    public boolean[] getAlbum(){
        return album;
    }
    public void setAlbum(boolean[] pAlbum){
        album = pAlbum;
    }


    public boolean[] getRepetidas() {
        return repetidas;
    }

    public void setRepetidas(boolean[] repetidas) {
        this.repetidas = repetidas;
    }

    public void setMostrarRepetidas(boolean mostrarRepetidas) {
        this.mostrarRepetidas = mostrarRepetidas;
    }

    public boolean isMostrarRepetidas() {
        return mostrarRepetidas;
    }

}
