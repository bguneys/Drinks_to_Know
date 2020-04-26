package com.bguneys.myapplication.drinkstoknow.details;

import android.content.Intent;
import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.settings.SettingsActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel mDetailsViewModel;
    DetailsViewModelFactory mDetailsViewModelFactory;
    DataRepository mRepository;

    TextView mItemNameTextView;
    TextView mItemDescriptionTextView;
    ImageView mItemImageView;

    private Item mCurrentItem;

    //private CollapsingToolbarLayout mCollapsingToolbar;

    static final String EXTRA_ITEM_ID = "com.bguneys.myapplication.EXTRA_ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Getting item Id from the Intent started from ListActivty or notification
        int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, 0);

        //mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        mItemNameTextView = findViewById(R.id.textView_itemName);
        mItemDescriptionTextView = findViewById(R.id.textView_itemDescription);
        mItemImageView = findViewById(R.id.imageView_itemImage);

        mRepository = DataRepository.getInstance(this);

        mDetailsViewModelFactory = new DetailsViewModelFactory(mRepository, itemId);
        mDetailsViewModel = new ViewModelProvider(this, mDetailsViewModelFactory).get(DetailsViewModel.class);

        mDetailsViewModel.getItem().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                mCurrentItem = item;
                mItemNameTextView.setText(mCurrentItem.getItemName());
                mItemDescriptionTextView.setText(mCurrentItem.getItemDescription());
                //mItemImageView.setImageResource(mCurrentItem.getItemImage());
                Glide.with(DetailsActivity.this).load(mCurrentItem.getItemImage()).into(mItemImageView);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        if (mCurrentItem.isItemFavourite()) {
            menu.getItem(0).setIcon(R.drawable.ic_action_heart_full_dark);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_action_heart_empty_dark);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_favourite:
                if (mCurrentItem.isItemFavourite()) {
                    mCurrentItem.setItemFavourite(false);
                    item.setIcon(R.drawable.ic_action_heart_empty_dark);

                } else {
                    mCurrentItem.setItemFavourite(true);
                    item.setIcon(R.drawable.ic_action_heart_full_dark);
                }

                mDetailsViewModel.setFavorite(mCurrentItem);

            default:

        }

        return super.onOptionsItemSelected(item);
    }

}
