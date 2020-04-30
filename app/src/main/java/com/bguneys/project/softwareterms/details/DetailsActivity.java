package com.bguneys.project.softwareterms.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bguneys.project.softwareterms.database.DataRepository;
import com.bguneys.project.softwareterms.database.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.project.softwareterms.R;
import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel mDetailsViewModel;
    DetailsViewModelFactory mDetailsViewModelFactory;
    DataRepository mRepository;

    TextView mItemNameTextView;
    TextView mItemDescriptionTextView;
    TextView mItemSourceText;
    TextView mItemSourceImage;
    ImageView mItemImageView;


    private Item mCurrentItem;

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
        mItemSourceText = findViewById(R.id.textView_itemSourceText);
        mItemSourceImage = findViewById(R.id.textView_itemSourceImage);
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
                Glide.with(DetailsActivity.this).load(mCurrentItem.getItemImage()).into(mItemImageView);

                //If there is no source then hide the text source TextView
                if (mCurrentItem.getItemSourceText().equals("none")) {
                    mItemSourceText.setVisibility(View.GONE);
                } else {
                    mItemSourceText.setText(getString(R.string.item_text_source, mCurrentItem.getItemSourceText()));
                    mItemSourceText.setVisibility(View.VISIBLE);
                }

                //If there is no source then hide the image source TextView
                if (mCurrentItem.getItemSourceImage().equals("none")) {
                    mItemSourceImage.setVisibility(View.GONE);
                } else {
                    mItemSourceImage.setText(getString(R.string.item_image_source, mCurrentItem.getItemSourceImage()));
                    mItemSourceImage.setVisibility(View.VISIBLE);
                }
            }
        });

        //Visit the text source website when clicked on source TextView
        mItemSourceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = mCurrentItem.getItemSourceTextUrl();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        //Visit the image source website when clicked on source TextView
        mItemSourceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = mCurrentItem.getItemSourceImageUrl();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
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
