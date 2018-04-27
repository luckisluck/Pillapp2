package com.dispenses.pill.pillapp.feature;

import android.app.Fragment;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




public class MainActivity extends AppCompatActivity {

    LinearLayout l1,l2;
    Button btn1,btn2;
    Animation uptodown,downtoup;
    String remember="pass";
    private EditText etEmail;
    int box = 0;
    int go =0;
    private EditText etPassword;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    CheckBox fee_checkbox1;
    ProgressDialog progressDialog;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Get Reference to variables
            etEmail = (EditText) findViewById(R.id.editText25);
            etPassword = (EditText) findViewById(R.id.editText26);
            fee_checkbox1 = (CheckBox)findViewById(R.id.checkBox4);


            btn1 = (Button)findViewById(R.id.button4);
            btn2 = (Button)findViewById(R.id.button5);
            l1 = (LinearLayout) findViewById(R.id.l1);
            l2 = (LinearLayout) findViewById(R.id.l2);
            uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
            downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
            l1.setAnimation(uptodown);
            l2.setAnimation(downtoup);


            String checkCond = getDefaults(remember,MainActivity.this);
            if(checkCond.equals("ok")) {
                Intent intent = new Intent(MainActivity.this,home.class);
                startActivity(intent);
                MainActivity.this.finish();
            }

            else
            {
                go = 3;
            }

        }




    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if(go == 3)
        {
            new AsyncLogin().execute(email, password);
        }
    }



    private class AsyncLogin extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://weighty-beach-183107.appspot.com/login.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(MainActivity.this,home.class);
                startActivity(intent);
                MainActivity.this.finish();

                if(box == 1) { savelogin(remember, "ok", MainActivity.this);
                }

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public static void savelogin(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void Save(View v)
    {

        if(fee_checkbox1.isChecked())
        {
            box=1;
        }

        else
        {
            box=0;
        }

    }

    public static String getDefaults(String key, Context context) {

        String Default = "Null";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (key) {
            case "pass":
                Default = preferences.getString(key, "0");
                break;

        }

        return Default;

    }




}










