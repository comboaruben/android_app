package com.example.ruben.proyecto_cs_dam;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearUsuario extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static String url_create_product = "https://app-dam2.000webhostapp.com/create_user.php";
    Calendar myCalendar = Calendar.getInstance();
    EditText birthday,email, nombre ,apellidos, password;
    Button aceptarCrearUsuario;
    ConstraintLayout crearUsuarioLayout;
    TextView emailFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        emailFormat=(TextView)findViewById(R.id.emailFormat);
        birthday=(EditText)findViewById(R.id.birthday);
        email=(EditText)findViewById(R.id.email);
        nombre=(EditText)findViewById(R.id.nombre);
        apellidos=(EditText)findViewById(R.id.apellidos);
        password=(EditText)findViewById(R.id.password);
        crearUsuarioLayout=findViewById(R.id.crearUsuarioLayout);
        aceptarCrearUsuario=(Button)findViewById(R.id.aceptarCrearUsuario);
        //Button crear user
        aceptarCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (birthday.getText().toString().equals("") || emailFormat.getVisibility() == View.VISIBLE || nombre.getText().toString().equals("")
                         || apellidos.getText().toString().equals("") || password.getText().toString().equals("")) {

                    Toast.makeText(CrearUsuario.this, R.string.rellenas_datos, Toast.LENGTH_SHORT).show();
                } else {
                    new CreateNewProduct().execute();

                }
            }
        });

        crearUsuarioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



            //Date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CrearUsuario.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //------------------------------------------------------------------
        //Email

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(isEmailValid(email.getText().toString())){
                    emailFormat.setVisibility(View.GONE);

                }else{
                    emailFormat.setVisibility(View.VISIBLE);

                }

            }
        });



    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthday.setText(sdf.format(myCalendar.getTime()));
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {
        JSONObject json;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CrearUsuario.this);
            pDialog.setMessage("Creating User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        /*String name = "hello";//user.getText().toString();
        String last_name = "hello";//user.getText().toString();
        String birthday = "1997-12-08";//user.getText().toString();
        String email = "hello";//user.getText().toString();
        String password = "5154";//user.getText().toString();*/



        //        String price = inputPrice.getText().toString();
        // nh  String description = inputDesc.getText().toString();

            // Building Parameters
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
            params.add(new BasicNameValuePair("name",nombre.getText().toString()));
            params.add(new BasicNameValuePair("last_name", apellidos.getText().toString()));
            params.add(new BasicNameValuePair("birthday",  birthday.getText().toString()));
            params.add(new BasicNameValuePair("email",email.getText().toString()));
            params.add(new BasicNameValuePair("password",new String(resumen)));

            json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("email",email.getText().toString());
                    returnIntent.putExtra("password",password.getText().toString());
                    setResult(CrearUsuario.RESULT_OK,returnIntent);
                    finish();
                    //     Toast.makeText(getApplicationContext(),"Se ha creado un usuario", Toast.LENGTH_SHORT).show();
                } else {
                    Intent returnIntent = new Intent();
                    setResult(CrearUsuario.RESULT_CANCELED,returnIntent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}


