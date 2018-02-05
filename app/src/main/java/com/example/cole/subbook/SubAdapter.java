package com.example.cole.subbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An adapter for the subscriptions in the RecyclerView
 */
public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    private final ArrayList<Subscription> subscriptions;
    private RecyclerView recyclerView;

    /**
     * The ViewHolder for a specific subscription. Includes the name and details fields
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sub_name;
        private TextView sub_details;

        /**
         * Constructs the ViewHolder and sets the View fields
         * @param itemView the View to be used by the ViewHolder
         */
        ViewHolder (View itemView) {
            super(itemView);
            sub_name = itemView.findViewById(R.id.subName);
            sub_details = itemView.findViewById(R.id.subDetails);
        }
    }

    /**
     * Constructs an adapter for the RecyclerView given a list of subscriptions
     * @param subscriptions The list of subscriptions to hold
     */
    SubAdapter (ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new view
     * @return a new ViewHolder that holds a View of a given view type
     */
    @Override
    public SubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            // Specifies what happens when a list item is clicked
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.context_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    // Specifies what happens when a menu item is clicked
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

    /**
     * Called when a RecyclerView attaches to this adapter. Saves the recycler view to use this later
     * @param recyclerView The RecyclerView that bound to this adapter
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * Called by the RecyclerView to display the Subscription at the specified position
     * @param holder The ViewHolder to be updated to display the Subscription
     * @param position The position of the Subscription in the subscription list
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Subscription subscription = subscriptions.get(position);
        holder.sub_name.setText(subscription.getName());
        holder.sub_details.setText(subscription.getDetails());
    }

    /**
     * Returns the number of items in the current subscription list
     * @return The number of items in the current subscription list
     */
    @Override
    public int getItemCount() {
        if (subscriptions == null){
            return 0;
        }
        return subscriptions.size();
    }

    /**
     * Called when the "Edit" button is clicked on a certain list item
     * @param position The position of the subscription in the list
     */
    private void editListItem(int position){
        MainActivity activity = (MainActivity) recyclerView.getContext();
        activity.showEditEntryDialog(subscriptions.get(position));
    }

    /**
     * Called when the "Delete" button is click on a certain list item
     * @param position The position of the subscription in the list
     */
    private void deleteListItem(int position){
        MainActivity activity = (MainActivity) recyclerView.getContext();
        activity.removeSubscription(subscriptions.get(position));
    }
}

