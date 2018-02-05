package com.example.cole.subbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    private final ArrayList<Subscription> subscriptions;
    private RecyclerView recyclerView;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sub_name;
        TextView sub_details;

        ViewHolder (View itemView) {
            super(itemView);
            sub_name = itemView.findViewById(R.id.subName);
            sub_details = itemView.findViewById(R.id.subDetails);
        }

        @Override
        public void onClick(View view){

        }
    }

    SubAdapter (ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public SubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.context_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.edit_button){
                            editListItem(viewHolder.getAdapterPosition());
                        }
                        else if(item.getItemId() == R.id.delete_button){
                            deleteListItem(viewHolder.getAdapterPosition());
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
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

    private void editListItem(int position){
        MainActivity activity = (MainActivity) recyclerView.getContext();
        activity.showEditEntryDialog(subscriptions.get(position));
    }

    private void deleteListItem(int position){
        MainActivity activity = (MainActivity) recyclerView.getContext();
        activity.removeSubscription(subscriptions.get(position));
    }
}

