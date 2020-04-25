package com.bguneys.myapplication.drinkstoknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModel;
import com.bguneys.myapplication.drinkstoknow.main.MainViewModelFactory;

import static com.bguneys.myapplication.drinkstoknow.database.Constants.NUMBER_OF_ITEMS;

public class LauncherActivity extends AppCompatActivity {

    DataRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //Instantiate Database through Repository so it can load data starting MainActivity
        mRepository = DataRepository.getInstance(this);

        Item item = null;
        int counter = 0;

        //Check if last database item is loaded
        while (item == null) {
            counter++;
            Log.i("While Loop Counter", String.valueOf(counter));
            //item = mRepository.getRandomItem();

            item = mRepository.getNextItemWithId(NUMBER_OF_ITEMS);
        }

        //If database is loaded then start MainActivity
        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
