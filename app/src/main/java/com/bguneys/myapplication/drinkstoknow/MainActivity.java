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
import android.widget.ImageView;
import android.widget.TextView;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.list.ListActivity;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModel;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModelFactory;
import com.bguneys.myapplication.drinkstoknow.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    MainViewModel mMainViewModel;
    MainViewModelFactory mMainViewModelFactory;
    DataRepository mRepository;

    TextView mItemHeaderTextView;
    TextView mItemDescriptionTextView;
    ImageView mItemImageView;

    int mTempItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mItemHeaderTextView = findViewById(R.id.textView_itemHeader);
        mItemDescriptionTextView = findViewById(R.id.textView_itemDescription);
        mItemImageView = findViewById(R.id.imageView_itemImage);

        mRepository = DataRepository.getInstance(this);

        mMainViewModelFactory = new MainViewModelFactory(mRepository);
        mMainViewModel = new ViewModelProvider(this, mMainViewModelFactory).get(MainViewModel.class);

        mMainViewModel.getRandomItem();

        mMainViewModel.getItem().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {

                mItemHeaderTextView.setText(item.getItemName());
                mItemDescriptionTextView.setText(item.getItemDescription());
                mItemImageView.setImageResource(item.getItemImage());

                mTempItemId = item.getItemId();

            }
        });
    }

    public void startListActivity(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        //intent.putExtra("com.bguneys.myapplication.drinkstoknow.list.EXTRA_ITEM_ID", mTempDrinkId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

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
