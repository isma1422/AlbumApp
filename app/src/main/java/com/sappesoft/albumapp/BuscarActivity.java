package com.sappesoft.albumapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sappesoft.albumapp.adapters.MeFaltanAdapter;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    boolean[] album;
    ArrayList<Integer> listaMeFaltan;
    MeFaltanAdapter meFaltanAdapter;
    EditText etFigurita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gvBuscar = (GridView) findViewById(R.id.gvBuscar);
        listaMeFaltan = new ArrayList<Integer>();
        meFaltanAdapter = new MeFaltanAdapter(listaMeFaltan,this);
        gvBuscar.setAdapter(meFaltanAdapter);
        meFaltanAdapter.notifyDataSetChanged();
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        etFigurita = (EditText) findViewById(R.id.etFiguritas);

        //Para evitar que peguen cualquier cosa
        etFigurita.setLongClickable(false);


        //OnClick enter edit text
        etFigurita.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){

                    buscarFigurita();
                    return true;
                }
                return false;
            }
        });


        //OnClick Boton reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaMeFaltan.clear();
                meFaltanAdapter.notifyDataSetChanged();
            }
        });


        //OnClick Boton buscar
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarFigurita();
            }
        });



        //OnClick GridView
        gvBuscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtFigurita = (TextView) view;
                if(album[listaMeFaltan.get(position)]){
                    txtFigurita.setTextColor(Color.RED);
                    album[listaMeFaltan.get(position)] = false;
                }else{
                    txtFigurita.setTextColor(Color.GREEN);
                    album[listaMeFaltan.get(position)] = true;
                }

            }
        });


    }



    private void buscarFigurita() {
        String strNumero = etFigurita.getText().toString();
        if(!strNumero.equals("")){
            Integer numeroFigurita = Integer.valueOf(strNumero);
            if(numeroFigurita > 0 && numeroFigurita < album.length && !album[numeroFigurita]){
                if(!listaMeFaltan.contains(numeroFigurita)){
                    listaMeFaltan.add(numeroFigurita);
                    meFaltanAdapter.notifyDataSetChanged();
                }
                Toast.makeText(BuscarActivity.this, "Falti", Toast.LENGTH_SHORT).show();
            }else if(numeroFigurita >= album.length){
                Toast.makeText(BuscarActivity.this, "Esa no existe capo", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BuscarActivity.this, "Tengui", Toast.LENGTH_SHORT).show();
            }
        }
        etFigurita.setText("");
    }


    @Override
    protected void onResume() {
        super.onResume();
        album = Util.loadAlbum("Album",this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.storeAlbum(album,"Album",this);
    }
}
