package com.example.cole.subbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    private ArrayList<Subscription> subscriptions;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView sub_name;
        TextView sub_details;

        ViewHolder (View itemView){
            super(itemView);
            sub_name = itemView.findViewById(R.id.subName);
            sub_details = itemView.findViewById(R.id.subDetails);
        }
    }

    SubAdapter (ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public SubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Subscription subscription = subscriptions.get(position);
        holder.sub_name.setText(subscription.getName());
        holder.sub_details.setText(subscription.getDetails());
    }

    @Override
    public int getItemCount() {
        if (subscriptions == null){
            return 0;
        }
        return subscriptions.size();
    }
}

