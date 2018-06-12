package com.example.ruben.proyecto_cs_dam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Index extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "https://app-dam2.000webhostapp.com/login_user.php";
    EditText email, password;
    TextView crearCuenta,falloConexion;
    Button aceptar;
    ConstraintLayout loginLayout;

    final private int CREAR_USUARIO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        falloConexion=findViewById(R.id.fallo_conexion);
        password = (EditText) findViewById(R.id.etPassword);
        email = (EditText) findViewById(R.id.etEmail);
        crearCuenta = (TextView) findViewById(R.id.crear_cuenta);
        aceptar = (Button) findViewById(R.id.btnAcept);
        loginLayout=findViewById(R.id.loginLayout);

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falloConexion.setVisibility(View.GONE);
            }
        });
        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CrearUsuario.class);
                startActivityForResult(i,CREAR_USUARIO);

            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Index.this,Menu.class);
                startActivity(intent);
                finish();
                if(!email.getText().toString().equals("")&&!password.getText().toString().equals("")){
                    new LoginUser().execute();
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Index.RESULT_OK){
                email.setText(data.getStringExtra("email"));
                password.setText(data.getStringExtra("password"));
            }
            if (resultCode == Index.RESULT_CANCELED) {

            }
        }
    }
  class LoginUser extends AsyncTask<String, String, String> {
      JSONObject json=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Index.this);
            pDialog.setMessage("Verificando datos");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            byte dataBytes[] = password.getText().toString().getBytes();

            md.update(dataBytes);
            byte resumen[] = md.digest();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("email",email.getText().toString()));
            params.add(new BasicNameValuePair("password",new String(resumen)));

            json = jsonParser.makeHttpRequest(url_create_product, "POST", params);
            return null;
        }


        protected void onPostExecute(String file_url) {
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent intent=new Intent(Index.this,Menu.class);
                    startActivity(intent);
                    finish();
                    //     Toast.makeText(getApplicationContext(),"Se ha creado un usuario", Toast.LENGTH_SHORT).show();
                } else {

                    falloConexion.setVisibility(View.VISIBLE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
        }

    }
}

