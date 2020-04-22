package com.bguneys.myapplication.drinkstoknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.list.ListActivity;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModel;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModelFactory;
import com.bguneys.myapplication.drinkstoknow.settings.SettingsActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    MainViewModel mMainViewModel;
    MainViewModelFactory mMainViewModelFactory;
    DataRepository mRepository;

    TextView mItemHeaderTextView;
    TextView mItemDescriptionTextView;
    ImageView mItemImageView;
    ImageView mFavouriteImageView;
    Button mNextItemButton;

    private Item mCurrentItem;

    int mTempItemId;
    int mCurrentItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mItemHeaderTextView = findViewById(R.id.textView_itemHeader);
        mItemDescriptionTextView = findViewById(R.id.textView_itemDescription);
        mItemImageView = findViewById(R.id.imageView_itemImage);
        mFavouriteImageView = findViewById(R.id.imageView_favourite);
        mNextItemButton = findViewById(R.id.button_nextItem);

        mRepository = DataRepository.getInstance(this);

        mMainViewModelFactory = new MainViewModelFactory(mRepository);
        mMainViewModel = new ViewModelProvider(this, mMainViewModelFactory).get(MainViewModel.class);

        mMainViewModel.getRandomItem();

        mMainViewModel.getItem().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                mCurrentItem = item;
                mCurrentItemId = mCurrentItem.getItemId();

                mItemHeaderTextView.setText(mCurrentItem.getItemName());
                mItemDescriptionTextView.setText(mCurrentItem.getItemDescription());
                mItemImageView.setImageResource(mCurrentItem.getItemImage());
                Glide.with(MainActivity.this).load(mCurrentItem.getItemImage()).into(mItemImageView);

                mTempItemId = mCurrentItem.getItemId();

                //If the current item is favourite then image changes
                if (mCurrentItem.isItemFavourite()) {
                    mFavouriteImageView.setImageResource(R.drawable.ic_action_heart_full_dark);
                } else {
                    mFavouriteImageView.setImageResource(R.drawable.ic_action_heart_empty_dark);
                }

            }
        });

        //Clicking favourite button makes the current item favourite and changes the image
        mFavouriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentItem.isItemFavourite()) {
                    mCurrentItem.setItemFavourite(false);
                    Toast.makeText(MainActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    mFavouriteImageView.setImageResource(R.drawable.ic_action_heart_empty_dark);

                } else {
                    mCurrentItem.setItemFavourite(true);
                    Toast.makeText(MainActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    mFavouriteImageView.setImageResource(R.drawable.ic_action_heart_full_dark);
                }

                mMainViewModel.setFavorite(mCurrentItem);
            }
        });


        mNextItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If last item is shown then go back to the first item
                if (mCurrentItemId == 50) {
                    mCurrentItemId = 0;
                }

                mCurrentItemId++;

                mMainViewModel.getNextItemWithId(mCurrentItemId);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_list:
                Intent listIntent = new Intent(this, ListActivity.class);
                startActivity(listIntent);
                return true;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:

        }

        return super.onOptionsItemSelected(item);
    }
}
