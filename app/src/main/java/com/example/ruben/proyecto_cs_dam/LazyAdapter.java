package com.example.ruben.proyecto_cs_dam;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Pedido> data;
    private static  LayoutInflater inflate=null;
    public LazyAdapter(Activity context, ArrayList<Pedido> dataa) {

        activity = context;
        data = dataa;
        inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila=convertView;
        if(convertView==null){
            fila = inflate.inflate(R.layout.list_row, null);
        }

        TextView title =  fila.findViewById(R.id.title); // title
        TextView duration = fila.findViewById(R.id.duration); // duration
        TextView idPedido=fila.findViewById(R.id.idPedido);
        TextView pedido=fila.findViewById(R.id.pedido);
        TextView email=fila.findViewById(R.id.emailPedido);
        ImageView arrow = fila.findViewById(R.id.arrow);


        title.setText(data.get(position).getNombre());
        duration.setText(data.get(position).getDia());
        idPedido.setText(data.get(position).getId());
        pedido.setText(data.get(position).getPedido());
        email.setText(data.get(position).getEmail());

        //Log.i("NUEVO TAG",data.get(position).getNombre()+"   "+data.get(position).getDia());
        arrow.setImageAlpha(R.drawable.arrow);
        return fila;
    }


}