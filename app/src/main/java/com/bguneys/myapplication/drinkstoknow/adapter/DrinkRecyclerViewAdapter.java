package com.bguneys.myapplication.drinkstoknow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.database.Drink;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    private final LayoutInflater mInflater;
    private List<Drink> mDrinkList;
    static DrinkViewHolder.ClickListener clickListener;

    public DrinkRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_item, parent, false);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {

        if (mDrinkList != null) {
            Drink currentWord = mDrinkList.get(position);
            holder.bind(currentWord);
        }

    }

    @Override
    public int getItemCount() {
        if (mDrinkList != null) {
            return mDrinkList.size();
        } else {
            return 0;
        }
    }

    /**
     * Populating adapter with the LiveData drink list taken from the Database through Viewmodel
     * @param drinkList The list of drinks will be taken from ViewModel inside MainActivity
     */
    public void populateList(List<Drink> drinkList) {
        mDrinkList = drinkList;
        notifyDataSetChanged();
    }

    /**
     * Getter method for all the drinks inside cached drink list in adapter
     * @return The list of cachd drinks inside adapter
     */
    public List<Drink> getDrinkList() {
        return mDrinkList;
    }

    public void setOnWordItemClickListener(DrinkViewHolder.ClickListener clickListener) {
        DrinkRecyclerViewAdapter.clickListener = clickListener;
    }
}
