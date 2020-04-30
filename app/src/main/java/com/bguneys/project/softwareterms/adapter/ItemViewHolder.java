package com.bguneys.project.softwareterms.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.project.softwareterms.R;
import com.bguneys.project.softwareterms.database.Item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView mItemNameTextView;
    public TextView mItemSummaryTextView;
    public ImageView mItemImageImageView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        mItemNameTextView = itemView.findViewById(R.id.itemName_textView);
        mItemSummaryTextView = itemView.findViewById(R.id.itemSummary_textView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemRecyclerViewAdapter.clickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }

    //Binding method to be used inside onBindViewHolder. So all view related code is in ViewHolder.
    public void bind (Item currentItem, Context context) {
        mItemNameTextView.setText(currentItem.getItemName());
        mItemSummaryTextView.setText(currentItem.getItemSummary());
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}
