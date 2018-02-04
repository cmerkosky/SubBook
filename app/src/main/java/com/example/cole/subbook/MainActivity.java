package com.example.cole.subbook;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

//public class MainActivity extends AppCompatActivity implements AddEntryDialogFragment.AddEntryDialogListener{
public class MainActivity extends AppCompatActivity {

    private RecyclerView subListView;
    private SubAdapter adapter;
    private ArrayList<Subscription> subList;
    private Subscription subToEdit = null;
    private TextView totalView;
    private Charge currentTotal;
    private Resources res;

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "On start called!");

        // TODO: Load from file
        subList = new ArrayList<>();

        res = getResources();
        currentTotal = new Charge(0,0);
        adapter = new SubAdapter(subList);
        subListView.setAdapter(adapter);

        addSubscription(new Subscription("Netflix", new Date(), new Charge(8, 99), "For dem flix."));
        addSubscription(new Subscription("Rugby", new Date(), new Charge(5, 65), ""));
        addSubscription(new Subscription("Water", new Date(), new Charge(5, 65), "For dat flush"));
        addSubscription(new Subscription("Electric", new Date(), new Charge(5, 65), "For dat zap"));
        addSubscription(new Subscription("Heating", new Date(), new Charge(5, 65), "For dat toast"));
        addSubscription(new Subscription("Rent", new Date(), new Charge(5, 65), "For dat roof"));
        addSubscription(new Subscription("Groceries", new Date(), new Charge(5, 65), "For dat food"));
        addSubscription(new Subscription("Phone", new Date(), new Charge(5, 65), "For dat ring"));
        addSubscription(new Subscription("Google Play", new Date(), new Charge(5, 65), "For dem tunes"));

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

        recalculateTotal();
        Log.i("Subscription", subscription.toString());
        Log.i("Subscription",  currentTotal.toString());
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));
        Log.i("Subscription", "Made it through");
        adapter.notifyDataSetChanged();
    }

    public void editSubscription(Subscription subscription, String name, String comment, Charge charge, Date date){
        subscription.setName(name);
        subscription.setComment(comment);
        subscription.setCharge(charge);
        subscription.setDate(date);

        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));
        adapter.notifyDataSetChanged();
    }

    public void removeSubscription(Subscription subscription) {
        subList.remove(subscription);
        recalculateTotal();
        totalView.setText(res.getString(R.string.total_string, currentTotal.toString()));
        adapter.notifyDataSetChanged();
    }

    public Subscription getSubToEdit(){ return this.subToEdit; }

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
}
