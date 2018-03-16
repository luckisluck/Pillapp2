package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 16/03/2018.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class popupZ_del extends AppCompatDialogFragment {

    private EditText delname;
    MyDBHandler  dbHandler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.del_popup, null);
        final Context context = inflater.getContext();
        dbHandler = new MyDBHandler(context, null, null, 1);

        builder.setView(view).setTitle("Delete Alarm").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String name = delname.getText().toString();
                        if (dbHandler.deleteSchedulez(name)) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "didnt del from DB", Toast.LENGTH_LONG).show();
                        }


                    }

                });
        delname = view.findViewById(R.id.editText5);

        return builder.create();
    }

}
