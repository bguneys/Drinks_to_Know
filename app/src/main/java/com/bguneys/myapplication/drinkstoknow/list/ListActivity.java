package com.bguneys.myapplication.drinkstoknow.list;

import android.app.SearchManager;
import android.content.Context;
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
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ItemRecyclerViewAdapter mAdapter;
    ListViewModel mListViewModel;
    ListViewModelFactory mListViewModelFactory;
    DataRepository mRepository;
    boolean isSearchFinished; //variable for checking if search submit button tapped

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

        mListViewModel.getListFilter().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                mListViewModel.getItemListByGroup(s).observe(ListActivity.this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {
                        mAdapter.populateList(items);
                    }
                });
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

        //Add necessary SearchView callback methods
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search..");
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                isSearchFinished = true; //assign boolean true to signal submit button tapped
                searchView.onActionViewCollapsed();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                //check if search submit button tapped
                if (!isSearchFinished) {

                    //Populating the list with items matching the string typed in search area on toolbar
                    String searchString = "%" + s + "%"; //wildcard characters added for search query

                    mListViewModel.getItemsWithName(searchString).observe(ListActivity.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(List<Item> items) {
                            mAdapter.populateList(items);
                        }
                    });
                }

                isSearchFinished = false; //reset boolean when starting a new search

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_favourite:
                Intent intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_filter_all:
                mListViewModel.setListFilter("%");
                return true;

            case R.id.action_filter_algorithms:
                mListViewModel.setListFilter("Algorithm");
                return true;

            case R.id.action_filter_data_structures:
                mListViewModel.setListFilter("Data Structure");
                return true;

            case R.id.action_filter_design_patterns:
                mListViewModel.setListFilter("Design Pattern");
                return true;

            case R.id.action_filter_programming_concepts:
                mListViewModel.setListFilter("Programming Concept");
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
