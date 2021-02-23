package com.ebs.hydrokleen.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.ebs.hydrokleen.R;

public class CustomAlertDialog extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogRefreshClick(DialogFragment dialog);
        void onCancelListener(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.layout_no_data, null));

            builder.setMessage("Please Check your Internet Connection")
                    .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.onDialogRefreshClick(CustomAlertDialog.this);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    listener.onCancelListener(CustomAlertDialog.this);
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}

