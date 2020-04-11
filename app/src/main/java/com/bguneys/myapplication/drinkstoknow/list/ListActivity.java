package com.bguneys.myapplication.drinkstoknow.list;

import android.content.Intent;
import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.adapter.DrinkRecyclerViewAdapter;
import com.bguneys.myapplication.drinkstoknow.adapter.DrinkViewHolder;
import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;
import com.bguneys.myapplication.drinkstoknow.details.DetailsActivity;
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

    static final String EXTRA_ITEM_ID = "com.bguneys.myapplication.drinkstoknow.list.EXTRA_ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        mAdapter.setOnWordItemClickListener(new DrinkViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Drink drink = mAdapter.getDrinkList().get(position);
                launchDetailsActivity(drink);
            }
        });

    }

    /**
     * Fetching the chosen item with click on RecyclerView and getting all necessary information
     * then sending them to DetailActivity with Extras
     * @param drink clicked item on RecyclerView according to adapter position
     */
    private void launchDetailsActivity(Drink drink) {
        Intent intent = new Intent(this, DetailsActivity.class);
        int drinkId = drink.getItemId();
        String drinkName = drink.getItemName();
        String drinkDescription = drink.getItemDescription();
        intent.putExtra(EXTRA_ITEM_ID, drinkId);
        startActivity(intent);
    }

}
