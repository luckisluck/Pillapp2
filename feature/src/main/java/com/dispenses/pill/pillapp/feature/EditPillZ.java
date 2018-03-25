package com.dispenses.pill.pillapp.feature;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Win10 on 25/03/2018.
 */

public class EditPillZ extends AppCompatDialogFragment {

    private EditText pillbottle;
    private EditText totalpill;
    MyDBHandler  dbHandler;
    private static final String PREFS_NAME = "set";
    String keyZ="nameBZ";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editpill, null);
        final Context context = inflater.getContext();

        builder.setView(view).setTitle("Edit Pill Box").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String name = pillbottle.getText().toString();
                        final String Tpill = totalpill.getText().toString();
                        setDefaults(keyZ,name,getActivity());

                    }

                });
        pillbottle = view.findViewById(R.id.editText6);
        totalpill = view.findViewById(R.id.editText7);

        return builder.create();
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
