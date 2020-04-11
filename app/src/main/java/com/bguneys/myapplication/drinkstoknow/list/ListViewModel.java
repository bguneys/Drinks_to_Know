package com.bguneys.myapplication.drinkstoknow.list;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final DataRepository mRepository;
    private LiveData<List<Item>> mItemList;

    public ListViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
        mItemList = mRepository.getItemList();
    }

    public LiveData<List<Item>> getItemList() {
        return mItemList;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }



}
