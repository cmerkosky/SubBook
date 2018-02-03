package com.example.cole.subbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class AddEntryDialogFragment extends DialogFragment {

    public interface AddEntryDialogListener{
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    AddEntryDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        LayoutInflater inflater = getActivity().getLayoutInflater();

        return new AlertDialog.Builder(getActivity()).setTitle("Add an entry...")
                .setView(View.inflate(getContext(), R.layout.add_entry_dialog, null))
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(AddEntryDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(AddEntryDialogFragment.this);
                    }
                 })
                .create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mListener = (AddEntryDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement AddEntryDialogListener!");
        }
    }
}
