package com.example.ruben.proyecto_cs_dam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private ArrayList <Pedido> pedidos;
    private ProgressDialog pDialog;
    private String select;
    static final String TAG_SUCCESS = "success";
    static String url_create_product = "https://app-dam2.000webhostapp.com/select_pedidos.php";

    TextView pedidosRealizados,pedidosSinRealizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        pedidosRealizados = findViewById(R.id.pedidos_realizados);
        pedidosSinRealizar = findViewById(R.id.pedidos_sin_realizar);

        pedidosRealizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            select="realizados";
            }
        });
        pedidosSinRealizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        select="sinRealizar";
                new CreateNewProduct().execute();
            }
        });
    }
    class CreateNewProduct extends AsyncTask<String, String, String>

    {
        JSONObject json;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Menu.this);
            pDialog.setMessage("Cargando Pedidos..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(select, select));
            json = jsonParser.makeHttpRequest(url_create_product, "POST", params);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            try {

                JSONArray jArray = json.getJSONArray("result");
                Intent i = new Intent(getApplicationContext(), VerPedido.class);
                i.putExtra("json", jArray.toString());
                startActivity(i);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}