package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 19/02/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

import static com.dispenses.pill.pillapp.feature.MainActivity.CONNECTION_TIMEOUT;
import static com.dispenses.pill.pillapp.feature.MainActivity.READ_TIMEOUT;


public class popup_autoX extends AppCompatDialogFragment {

    MyDBHandler  dbHandler;
    private EditText editTextname;
    private EditText editTextpillamt;
    private EditText editTextstart;
    private EditText editTextend;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_autox, null);
        final Context context = inflater.getContext();
        dbHandler = new MyDBHandler(context, null, null, 1);

        builder.setView(view)
                .setTitle("Set Schedule")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String name = editTextname.getText().toString();
                        final String startTime = editTextstart.getText().toString();
                        final String endTime = editTextend.getText().toString();
                        final String pillAmt = editTextpillamt.getText().toString();
                        final String table ="dispenseauto_X";
                        int k= Integer.parseInt(pillAmt);
                        try {
                            bottlexGetSet p = new bottlexGetSet(name,startTime,endTime,k);
                            dbHandler.bottlexAdd(p);
                        } catch (Exception e) {
                            Toast.makeText(context, "didnt add to DB", Toast.LENGTH_LONG).show();
                        }
                        new AsyncaddRecordAutos().execute(name,startTime,endTime,pillAmt,table);

                    }
                });

        editTextname = view.findViewById(R.id.editText123);
        editTextpillamt = view.findViewById(R.id.editText126);
        editTextstart = view.findViewById(R.id.editText124);
        editTextend = view.findViewById(R.id.editText125);
        return builder.create();
    }


    private class AsyncaddRecordAutos extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://weighty-beach-183107.appspot.com/addRecordAuto.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("startTime", params[1])
                        .appendQueryParameter("endTime", params[2])
                        .appendQueryParameter("pillAmt", params[3])
                        .appendQueryParameter("table", params[4]);
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
                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }
        protected void onPostExecute(String result) {


        }

    }
}
