package com.ebs.hydrokleen.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.networkutils.AlertHelper;

/**
 * Created by Amit on 16,April,2020
 * kundu.amit517@gmail.com
 */
public class AlertDialogHelper {

    public static void updateService(String type, Context context, String text1, String text2, String text3, final AlertHelper alertHelper){

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.layout_alert_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final TextView title = promptsView.findViewById(R.id.title);
        final TextView message = promptsView.findViewById(R.id.message);
        final TextView warning = promptsView.findViewById(R.id.warning);
        final EditText userInput = promptsView.findViewById(R.id.userInput);
        final Button cancel = promptsView.findViewById(R.id.cancel);
        final Button ok = promptsView.findViewById(R.id.ok);

        if (type.equals("adapter")){
            userInput.setVisibility(View.GONE);
        }

        title.setText(text1);
        message.setText(text2);
        warning.append(text3);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false);

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertHelper.alert(true,userInput.getText().toString());
            }
        });

        //return null;

    }

}

