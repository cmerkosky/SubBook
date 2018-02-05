package com.example.cole.subbook;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The main activity that this app will run.
 */
public class MainActivity extends AppCompatActivity {

    private final static String FILENAME = "subscriptions.sav";
    private RecyclerView subListView;
    private SubAdapter adapter;
    private ArrayList<Subscription> subList;
    private Subscription subToEdit = null;
    private TextView totalView;
    private Charge currentTotal;
    private Resources res;

    /**
     * Called when this activity is created
     * @param savedInstanceState A saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subListView = findViewById(R.id.sub_list_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        subListView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = findViewById(R.id.add_entry_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEntryDialog();
            }
        });

        totalView = findViewById(R.id.total_view);
    }

    /**
     * Called when this activity is started
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "On start called!");

        // TODO: Load from file
        loadFromFile();

        res = getResources();
        currentTotal = new Charge(0,0);

        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));

        adapter = new SubAdapter(subList);
        subListView.setAdapter(adapter);
    }

    /**
     * Displays the "Add Entry" dialog
     */
    void showAddEntryDialog() {
        FragmentManager fm = getFragmentManager();

        DialogFragment dialog = new AddEntryDialogFragment();
        dialog.show(fm, "showAddEntryDialog");
    }

    /**
     * Displays the "Edit Entry" dialog
     * @param subToEdit The subscription to be edited
     */
    void showEditEntryDialog(Subscription subToEdit) {
        FragmentManager fm = getFragmentManager();

        this.subToEdit = subToEdit;

        DialogFragment dialog = new EditEntryDialogFragment();
        dialog.show(fm, "showEditEntryDialog");
    }

    /**
     * Adds a subscription to the list
     * @param subscription The subscription to add
     */
    public void addSubscription(Subscription subscription) {
        subList.add(subscription);

        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));

        saveInFile();
        adapter.notifyDataSetChanged();
    }

    /**
     * Edits a subscription currently in the list
     * @param subscription The subscription to edit
     * @param name The name to update the subscription with.
     * @param comment The comment to update the subscription with.
     * @param charge The charge to update the subscription with.
     * @param date The date to update the subscription with
     */
    public void editSubscription(Subscription subscription, String name, String comment, Charge charge, Date date){
        subscription.setName(name);
        subscription.setComment(comment);
        subscription.setCharge(charge);
        subscription.setDate(date);

        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));

        saveInFile();
        adapter.notifyDataSetChanged();
    }

    /**
     * Removes a subscription from the list
     * @param subscription The subscription to remove
     */
    public void removeSubscription(Subscription subscription) {
        subList.remove(subscription);
        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));

        saveInFile();
        adapter.notifyDataSetChanged();
    }

    /**
     * Recalculates the current total monthly charge
     */
    private void recalculateTotal(){
        int cents = 0;
        int dollars = 0;
        for(int i = 0; i < subList.size(); i++){
            Subscription nextSub = subList.get(i);
            cents += nextSub.getCharge().getCents();
            if (cents >= 100){
                dollars += 1;
                cents -= 100;
            }
            dollars += nextSub.getCharge().getDollars();
        }
        currentTotal = new Charge(dollars, cents);
    }

    /**
     * Returns the currently specified subscription to be edited
     * @return currently held subscription to be edited
     */
    public Subscription getSubToEdit(){ return this.subToEdit; }

    /**
     * Saves the subscription list to a GSON file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

            fos.close();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Loads the subscription list from a GSON file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();

            subList = gson.fromJson(in, listType);

            fis.close();

        } catch (FileNotFoundException e) {
            subList = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
