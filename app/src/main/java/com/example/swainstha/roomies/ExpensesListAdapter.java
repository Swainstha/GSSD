package com.example.swainstha.roomies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by swainstha on 9/17/18.
 */

public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.ExpensesViewHolder> {

    class ExpensesViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private ExpensesViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Expenses> mExpenses; // Cached copy of words

    ExpensesListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ExpensesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ExpensesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpensesViewHolder holder, int position) {
        if (mExpenses != null) {
            Expenses current = mExpenses.get(position);
            holder.wordItemView.setText(current.getItems());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setExpenses(List<Expenses> expenses){
        mExpenses = expenses;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mExpenses != null)
            return mExpenses.size();
        else return 0;
    }
}