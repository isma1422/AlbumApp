package com.sappesoft.albumapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sappesoft.albumapp.R;

import java.util.ArrayList;

/**
 * Created by Ismael on 08/04/2018.
 */
public class MeFaltanAdapter extends BaseAdapter{

    ArrayList<Integer> listaMeFaltan;
    private LayoutInflater inflater;
    Context mContext;

    public MeFaltanAdapter(ArrayList<Integer> listaMeFaltan,Context context){
        this.listaMeFaltan = listaMeFaltan;
        mContext = context;
    }


    @Override
    public int getCount() {
        return listaMeFaltan.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMeFaltan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaMeFaltan.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtFigurita;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            txtFigurita = (TextView) inflater.inflate(R.layout.figurita_layout,parent,false);
            txtFigurita.setTextColor(Color.RED);
        }else{
            txtFigurita = (TextView) convertView;
        }

        txtFigurita.setText(listaMeFaltan.get(position).toString());

        return txtFigurita;
    }
}
