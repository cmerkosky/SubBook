package com.example.cole.subbook;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AddEntryDialogFragment.AddEntryDialogListener{
//public class MainActivity extends AppCompatActivity {

    private RecyclerView subListView;
//    private RecyclerView.LayoutManager mLayoutManager;
    private SubAdapter adapter;

    private ArrayList<Subscription> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subListView = findViewById(R.id.subList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        subListView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = findViewById(R.id.addEntryFAB);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAddEntryDialog();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        // TODO: Load from file
        subList = new ArrayList<>();
        subList.add(new Subscription("Netflix", new Charge(7, 99), "For dose flix"));
        subList.add(new Subscription("Gym", new Charge(30, 85), "For dat buff"));
        subList.add(new Subscription("Xbox", new Charge(5, 0), "For dem games"));
        subList.add(new Subscription("Phone", new Charge(50, 0), "For dat phone"));
        subList.add(new Subscription("Rugby Dues", new Date(), new Charge(50, 0), "For dat scrum"));
        subList.add(new Subscription("Electric", new Charge(47, 78), "For dat zap"));
        subList.add(new Subscription("Water", new Charge(84, 90), "For dat flush"));
        subList.add(new Subscription("Rent", new Charge(500, 0), "For dat roof"));
        subList.add(new Subscription("Garbage", new Charge(70, 4), "For dat bag"));
        subList.add(new Subscription("Miscellaneous", new Charge(96, 7), "For dat everything"));

        adapter = new SubAdapter(subList);
        subListView.setAdapter(adapter);

        Log.i("MainActivity", "Adapter set!");
    }

    void showAddEntryDialog(){
        FragmentManager fm = getFragmentManager();

        DialogFragment dialog = new AddEntryDialogFragment();
        dialog.show(fm, "showAddEntryDialog");
    }

//    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        //TODO: Do something
        EditText name = dialog.getDialog().findViewById(R.id.dialogName);
        EditText comment = dialog.getDialog().findViewById(R.id.dialogComment);
        EditText cost = dialog.getDialog().findViewById(R.id.dialogCost);

        Log.i("Debug", "Name is " + name);
        Log.i("Debug", "Comment is " + comment);
        Log.i("Debug", "Cost is " + cost);

        String costString = cost.getText().toString();

        int dollars = Integer.parseInt(costString.substring(0, costString.lastIndexOf(".")));
        int cents = Integer.parseInt(costString.substring(costString.lastIndexOf(".") + 1));

        Subscription subscription = new Subscription(name.getText().toString(), new Charge(dollars, cents), comment.getText().toString());
        subList.add(subscription);

        adapter.notifyDataSetChanged();
    }

//    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        //TODO: Do something
    }
}
