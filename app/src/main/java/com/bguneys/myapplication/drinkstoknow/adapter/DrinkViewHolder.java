package com.bguneys.myapplication.drinkstoknow.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.database.Drink;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrinkViewHolder extends RecyclerView.ViewHolder {

    public TextView mDrinkNameTextView;
    public TextView mDrinkDescriptionTextView;
    public ImageView mDrinkImageImageView;

    public DrinkViewHolder(@NonNull View itemView) {
        super(itemView);

        mDrinkNameTextView = itemView.findViewById(R.id.itemName_textView);
        mDrinkDescriptionTextView = itemView.findViewById(R.id.itemDescription_textView);
        mDrinkImageImageView = itemView.findViewById(R.id.itemImage_ImageView);
    }

    //Binding method to be used inside onBindViewHolder. So all view related code is in ViewHolder.
    public void bind (Drink currentDrink) {
        mDrinkNameTextView.setText(currentDrink.getItemName());
        mDrinkDescriptionTextView.setText(currentDrink.getItemDescription());

        //TODO: Check image visibility
        //mDrinkImageImageView.setImageResource(currentDrink.getItemImage());
    }
}