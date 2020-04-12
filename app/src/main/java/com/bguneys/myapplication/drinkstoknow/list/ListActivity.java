package com.bguneys.myapplication.drinkstoknow.list;

import android.content.Intent;
import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.adapter.ItemRecyclerViewAdapter;
import com.bguneys.myapplication.drinkstoknow.adapter.ItemViewHolder;
import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.details.DetailsActivity;
import com.bguneys.myapplication.drinkstoknow.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ItemRecyclerViewAdapter mAdapter;
    ListViewModel mListViewModel;
    ListViewModelFactory mListViewModelFactory;
    DataRepository mRepository;

    static final String EXTRA_ITEM_ID = "com.bguneys.myapplication.EXTRA_ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRepository = DataRepository.getInstance(this);

        mListViewModelFactory = new ListViewModelFactory(mRepository);
        mListViewModel = new ViewModelProvider(this, mListViewModelFactory).get(ListViewModel.class);

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new ItemRecyclerViewAdapter(this);

        mListViewModel.getItemList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                mAdapter.populateList(items);
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnWordItemClickListener(new ItemViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item item = mAdapter.getItemList().get(position);
                launchDetailsActivity(item);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fetching the chosen item with click on RecyclerView and getting all necessary information
     * then sending them to DetailActivity with Extras
     * @param item clicked item on RecyclerView according to adapter position
     */
    private void launchDetailsActivity(Item item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        int drinkId = item.getItemId();
        String drinkName = item.getItemName();
        String drinkDescription = item.getItemDescription();
        intent.putExtra(EXTRA_ITEM_ID, drinkId);
        startActivity(intent);
    }

}
