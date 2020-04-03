package com.bguneys.myapplication.drinkstoknow.list;

import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.adapter.DrinkRecyclerViewAdapter;
import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DrinkRecyclerViewAdapter mAdapter;
    ListViewModel mListViewModel;
    ListViewModelFactory mListViewModelFactory;
    DataRepository mRepository;

    ArrayList<Drink> mDummyItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDummyItemList = new ArrayList<Drink>();

        mRepository = DataRepository.getInstance(this);

        mListViewModelFactory = new ListViewModelFactory(mRepository);
        mListViewModel = new ViewModelProvider(this, mListViewModelFactory).get(ListViewModel.class);

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new DrinkRecyclerViewAdapter(this);

        mListViewModel.getDrinkList().observe(this, new Observer<List<Drink>>() {
            @Override
            public void onChanged(List<Drink> drinks) {
                mAdapter.populateList(drinks);
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
