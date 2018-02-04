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

//public class MainActivity extends AppCompatActivity implements AddEntryDialogFragment.AddEntryDialogListener{
public class MainActivity extends AppCompatActivity {

    private RecyclerView subListView;
    private SubAdapter adapter;
    private ArrayList<Subscription> subList;
    private Subscription subToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subListView = findViewById(R.id.subList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        subListView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = findViewById(R.id.addEntryFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEntryDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: Load from file
        subList = new ArrayList<>();

        subList.add(new Subscription("Netflix", new Date(), new Charge(8, 99), "For dem flix."));

        adapter = new SubAdapter(subList);
        subListView.setAdapter(adapter);

        Log.i("MainActivity", "Adapter set!");
    }

    void showAddEntryDialog() {
        FragmentManager fm = getFragmentManager();

        DialogFragment dialog = new AddEntryDialogFragment();
        dialog.show(fm, "showAddEntryDialog");
    }

    void showEditEntryDialog(Subscription subToEdit) {
        FragmentManager fm = getFragmentManager();

        this.subToEdit = subToEdit;

        DialogFragment dialog = new EditEntryDialogFragment();
        dialog.show(fm, "showEditEntryDialog");
    }

    public void addSubscription(Subscription subscription) {
        subList.add(subscription);
        adapter.notifyDataSetChanged();
    }

    public void editSubscription(Subscription subscription, String name, String comment, Charge charge, Date date){
        subscription.setName(name);
        subscription.setComment(comment);
        subscription.setCharge(charge);
        subscription.setDate(date);

        adapter.notifyDataSetChanged();
    }

    public void removeSubscription(Subscription subscription) {
        subList.remove(subscription);
        adapter.notifyDataSetChanged();
    }

    public Subscription getSubToEdit(){ return this.subToEdit; }
}
