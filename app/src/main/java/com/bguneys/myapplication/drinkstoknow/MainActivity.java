package com.bguneys.myapplication.drinkstoknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;
import com.bguneys.myapplication.drinkstoknow.details.DetailsActivity;
import com.bguneys.myapplication.drinkstoknow.list.ListActivity;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModel;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    MainViewModel mMainViewModel;
    MainViewModelFactory mMainViewModelFactory;
    DataRepository mRepository;

    TextView mDrinkHeaderTextView;
    TextView mDrinkDescriptionTextView;
    ImageView mDrinkImageView;

    int mTempDrinkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrinkHeaderTextView = findViewById(R.id.textView_drinkHeader);
        mDrinkDescriptionTextView = findViewById(R.id.textView_drinkDescription);
        mDrinkImageView = findViewById(R.id.imageView_drinkImage);

        mRepository = DataRepository.getInstance(this);

        mMainViewModelFactory = new MainViewModelFactory(mRepository);
        mMainViewModel = new ViewModelProvider(this, mMainViewModelFactory).get(MainViewModel.class);

        mMainViewModel.getRandomDrink();

        mMainViewModel.getDrink().observe(this, new Observer<Drink>() {
            @Override
            public void onChanged(Drink drink) {

                mDrinkHeaderTextView.setText(drink.getItemName());
                mDrinkDescriptionTextView.setText(drink.getItemDescription());
                mDrinkImageView.setImageResource(drink.getItemImage());

                mTempDrinkId = drink.getItemId();

            }
        });
    }

    public void startListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        //intent.putExtra("com.bguneys.myapplication.drinkstoknow.list.EXTRA_ITEM_ID", mTempDrinkId);
        startActivity(intent);
    }
}
