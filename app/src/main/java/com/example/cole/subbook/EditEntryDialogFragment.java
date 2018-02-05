package com.example.cole.subbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * The DialogFragment for the "Edit Entry" dialog - as per the Android spec, it is better to use
 * DialogFragments rather than dialogs themselves.
 */
public class EditEntryDialogFragment extends DialogFragment {

    /**
     * Initializes the dialog fragment and specifies the layout
     * @param savedInstanceState Saved instance of the calling function
     * @return a Dialog to display
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("Edit an entry...")
                .setView(View.inflate(getContext(), R.layout.edit_entry_dialog, null))
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}})
                .create();
    }

    /**
     * Sets the button actions - ensures proper input
     */
    @Override
    public void onStart(){
        super.onStart();
        final AlertDialog dialog = (AlertDialog) this.getDialog();

        final MainActivity activity = (MainActivity) getActivity();
        Subscription subscription = ((MainActivity) getActivity()).getSubToEdit();

        final EditText name = dialog.findViewById(R.id.edit_name);
        final EditText comment = dialog.findViewById(R.id.edit_comment);
        final EditText cost = dialog.findViewById(R.id.edit_cost);
        final DatePicker date = dialog.findViewById(R.id.edit_date);

        name.setText(subscription.getName());
        comment.setText(subscription.getComment());
        cost.setText(subscription.getChargeString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(subscription.getDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.updateDate(year, month, day);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameString = name.getText().toString();
                String commentString = comment.getText().toString();
                String costString = cost.getText().toString();

                boolean validSub = true;

                if (nameString.length() < 1 || nameString.length() > 20) {
                    name.setError("Name must be between 1 and 20 characters!");
                    validSub = false;
                }
                if (commentString.length() > 30) {
                    comment.setError("Comment must be less than 30 characters!");
                    validSub = false;
                }
                if (!costString.matches("[0-9]+[.][0-9]{2}")) {
                    cost.setError("Enter a valid price with two decimal places");
                    validSub = false;
                }

                if (validSub) {
                    int dollars = Integer.parseInt(costString.substring(0, costString.lastIndexOf(".")));
                    int cents = Integer.parseInt(costString.substring(costString.lastIndexOf(".") + 1));

                    int day = date.getDayOfMonth();
                    int month = date.getMonth();
                    int year = date.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    activity.editSubscription(activity.getSubToEdit(), nameString, commentString,
                            new Charge(dollars, cents), calendar.getTime());

                    dialog.dismiss();
                }
            }
        });
    }
}
