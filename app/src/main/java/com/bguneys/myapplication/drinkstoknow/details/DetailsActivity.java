package com.bguneys.myapplication.drinkstoknow.details;

import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.R;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel mDetailsViewModel;
    DetailsViewModelFactory mDetailsViewModelFactory;
    DataRepository mRepository;

    TextView mItemHeaderTextView;
    TextView mItemDescriptionTextView;
    ImageView mItemImageView;

    static final String EXTRA_ITEM_ID = "com.bguneys.myapplication.drinkstoknow.list.EXTRA_ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting drink Id from the Intent started from ListActivty
        int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, 0);

        mItemHeaderTextView = findViewById(R.id.textView_itemHeader);
        mItemDescriptionTextView = findViewById(R.id.textView_itemDescription);
        mItemImageView = findViewById(R.id.imageView_itemImage);

        mRepository = DataRepository.getInstance(this);

        mDetailsViewModelFactory = new DetailsViewModelFactory(mRepository, itemId);
        mDetailsViewModel = new ViewModelProvider(this, mDetailsViewModelFactory).get(DetailsViewModel.class);

        mDetailsViewModel.getItem().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                mItemHeaderTextView.setText(item.getItemName());
                mItemDescriptionTextView.setText(item.getItemDescription());
                mItemImageView.setImageResource(item.getItemImage());
            }
        });

    }

}
