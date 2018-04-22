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

public class addalarm_popup extends AppCompatDialogFragment {

    private EditText addname;
    private EditText addtime;
    MyDBHandler  dbHandler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addalarm, null);
        final Context context = inflater.getContext();
        dbHandler = new MyDBHandler(context, null, null, 1);

        builder.setView(view).setTitle("Add Alarm").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String name = addname.getText().toString();
                        if (dbHandler.deleteSchedule(name)) {

                        } else {
                            Toast.makeText(getActivity(), "didnt add from DB", Toast.LENGTH_LONG).show();
                        }


                    }

                });
        addname = view.findViewById(R.id.editText4);
        addtime = view.findViewById(R.id.editText9);

        return builder.create();
    }



}
