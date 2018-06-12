package com.example.ruben.proyecto_cs_dam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerPedido extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private ArrayList <Pedido> pedidos;

    private ProgressDialog pDialog;
    static final String TAG_SUCCESS = "success";
    static String url_create_product = "https://app-dam2.000webhostapp.com/select_pedidos.php";
    static final String KEY_NOMBRE = "nombre"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_EMAIL = "email";
    static final String KEY_DIA = "dia";
    ListView list;
    LazyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);

        try {
            Intent inte=getIntent();
            pedidos = new ArrayList<>();

            JSONArray jArray=new JSONArray(inte.getStringExtra("json"));



            for (int i = 0; i < jArray.length(); i++) {

                JSONObject json_data = jArray.getJSONObject(i);
                Pedido ped = new Pedido(json_data.getString("id"), json_data.getString("nombre"), json_data.getString("email"), json_data.getString("dia"),json_data.getString("pedido"));
                pedidos.add(ped);
          /*  Log.i("log_tag", "id" + json_data.getString("id") +
                    ", nombre" + json_data.getString("nombre") +
                    ", dia" + json_data.getString("dia") +
                    ", email" + json_data.getString("email"));*/
                Log.i("as","asdsadsadada");

            }

            list=findViewById (R.id.list);
            adapter=new LazyAdapter(this, pedidos);
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }






}

