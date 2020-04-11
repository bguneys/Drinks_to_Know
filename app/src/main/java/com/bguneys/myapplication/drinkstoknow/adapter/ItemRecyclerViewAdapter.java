package com.bguneys.myapplication.drinkstoknow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItemList;
    static ItemViewHolder.ClickListener clickListener;

    public ItemRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        if (mItemList != null) {
            Item currentWord = mItemList.get(position);
            holder.bind(currentWord);
        }

    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size();
        } else {
            return 0;
        }
    }

    /**
     * Populating adapter with the LiveData drink list taken from the Database through Viewmodel
     * @param itemList The list of drinks will be taken from ViewModel inside MainActivity
     */
    public void populateList(List<Item> itemList) {
        mItemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * Getter method for all the drinks inside cached drink list in adapter
     * @return The list of cachd drinks inside adapter
     */
    public List<Item> getItemList() {
        return mItemList;
    }

    public void setOnWordItemClickListener(ItemViewHolder.ClickListener clickListener) {
        ItemRecyclerViewAdapter.clickListener = clickListener;
    }
}
