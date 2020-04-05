package com.bguneys.myapplication.drinkstoknow.details;

import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.R;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel mDetailsViewModel;
    DetailsViewModelFactory mDetailsViewModelFactory;
    DataRepository mRepository;

    TextView mDrinkHeaderTextView;
    TextView mDrinkDescriptionTextView;
    ImageView mDrinkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting drink Id from the Intent started from ListActivty
        int drinkId = getIntent().getIntExtra("DRINK_ID", 0);

        mDrinkHeaderTextView = findViewById(R.id.textView_drinkHeader);
        mDrinkDescriptionTextView = findViewById(R.id.textView_drinkDescription);
        mDrinkImageView = findViewById(R.id.imageView_drinkImage);

        mRepository = DataRepository.getInstance(this);

        mDetailsViewModelFactory = new DetailsViewModelFactory(mRepository, drinkId);
        mDetailsViewModel = new ViewModelProvider(this, mDetailsViewModelFactory).get(DetailsViewModel.class);

        mDetailsViewModel.getDrink().observe(this, new Observer<Drink>() {
            @Override
            public void onChanged(Drink drink) {
                mDrinkHeaderTextView.setText(drink.getItemName());
                mDrinkDescriptionTextView.setText(drink.getItemDescription());
                mDrinkImageView.setImageResource(drink.getItemImage());
            }
        });

    }

}
