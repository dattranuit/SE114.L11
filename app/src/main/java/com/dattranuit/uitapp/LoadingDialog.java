package com.dattranuit.uitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity activity;
    private Dialog dialog;
    LoadingDialog(Activity activity){
        this.activity = activity;
    }

    void StartLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public void DismissDialog(){
        dialog.dismiss();
    }
}
