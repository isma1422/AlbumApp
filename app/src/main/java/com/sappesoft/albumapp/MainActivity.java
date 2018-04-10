package com.sappesoft.albumapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sappesoft.albumapp.adapters.AlbumAdapter;

public class MainActivity extends AppCompatActivity {

    AlbumAdapter albumAdapter;
    TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gvAlbum = (GridView) findViewById(R.id.gvAlbum);


        albumAdapter = new AlbumAdapter(this);
        gvAlbum.setAdapter(albumAdapter);

        //Item click
        gvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean[] album;
                if(albumAdapter.isMostrarRepetidas()){
                    album = albumAdapter.getRepetidas();
                }else{
                    album = albumAdapter.getAlbum();
                }
                album[position] = !album[position];
                albumAdapter.notifyDataSetChanged();
            }
        });

    }

    private boolean storeAlbum(boolean[] album, String albumName, Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(albumName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(albumName + "_size", album.length);
        for(int i=0; i<album.length; i++){
            editor.putBoolean(albumName + "_" + i, album[i]);
        }

        return editor.commit();
    }

    private boolean[] loadAlbum(String albumName, Context mContext){
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
        if (id == R.id.action_cuantas_faltan) {
            Toast.makeText(MainActivity.this, "Te faltan " + calcularFaltantes(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.action_album){
            albumAdapter.setMostrarRepetidas(false);
            albumAdapter.notifyDataSetChanged();
            getSupportActionBar().setTitle("Album");
        }
        if(id == R.id.action_repetidas){
            albumAdapter.setMostrarRepetidas(true);
            albumAdapter.notifyDataSetChanged();
            getSupportActionBar().setTitle("Repetidas");
        }
        if(id == R.id.action_buscar){
            Intent buscarIntent = new Intent(this,BuscarActivity.class);
            startActivity(buscarIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(albumAdapter.isMostrarRepetidas()){
            getSupportActionBar().setTitle("Repetidas");

        }else{
            getSupportActionBar().setTitle("Album");
        }

        boolean[] savedAlbum = loadAlbum("Album",this);
        if(savedAlbum != null)
            albumAdapter.setAlbum(savedAlbum);

        boolean[] savedRepetidas = loadAlbum("Repetidas",this);
        if(savedRepetidas != null)
            albumAdapter.setRepetidas(savedRepetidas);

        albumAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeAlbum(albumAdapter.getAlbum(),"Album",this);
        storeAlbum(albumAdapter.getRepetidas(),"Repetidas",this);
    }

    private int calcularFaltantes(){
        int faltantes = 0;
        for(boolean figurita : albumAdapter.getAlbum()){
            if(!figurita){
                faltantes++;
            }
        }
        return faltantes;
    }
}
