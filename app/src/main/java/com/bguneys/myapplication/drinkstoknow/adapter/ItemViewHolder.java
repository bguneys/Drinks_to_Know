package com.bguneys.myapplication.drinkstoknow.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.database.Item;

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
        mItemImageImageView = itemView.findViewById(R.id.itemImage_ImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemRecyclerViewAdapter.clickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }

    //Binding method to be used inside onBindViewHolder. So all view related code is in ViewHolder.
    public void bind (Item currentItem) {
        mItemNameTextView.setText(currentItem.getItemName());
        mItemSummaryTextView.setText(currentItem.getItemSummary());
        mItemImageImageView.setImageResource(currentItem.getItemImage());
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}
