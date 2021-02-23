package com.ebs.hydrokleen.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.ebs.hydrokleen.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog() {
    }

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }


    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_progressbar,null));

        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
