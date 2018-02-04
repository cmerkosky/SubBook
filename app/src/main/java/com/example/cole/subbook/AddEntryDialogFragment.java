package com.example.cole.subbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AddEntryDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity()).setTitle("Add an entry...")
                .setView(View.inflate(getContext(), R.layout.add_entry_dialog, null))
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}})
                .create();
    }

    @Override
    public void onStart(){
        super.onStart();
        final AlertDialog dialog = (AlertDialog) this.getDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = dialog.findViewById(R.id.dialog_name);
                EditText comment = dialog.findViewById(R.id.dialog_comment);
                EditText cost = dialog.findViewById(R.id.dialog_cost);
                DatePicker date = dialog.findViewById(R.id.dialog_date);

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

                    Subscription subscription = new Subscription(name.getText().toString(),
                            calendar.getTime(), new Charge(dollars, cents), comment.getText().toString());

                    MainActivity activity = (MainActivity) getActivity();
                    activity.addSubscription(subscription);
                    dialog.dismiss();
                }
            }
        });
    }
}
