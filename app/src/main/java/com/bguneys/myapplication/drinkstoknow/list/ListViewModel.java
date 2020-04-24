package com.bguneys.myapplication.drinkstoknow.list;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final MutableLiveData<Boolean> mIsListFavourite = new MutableLiveData<>(false);
    private MutableLiveData<String> mListFilter = new MutableLiveData<>("%");

    public ListViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    public LiveData<List<Item>> getFavouriteItemList() {
        return mRepository.getFavouriteItemList();
    }

    public LiveData<List<Item>> getItemListByGroup(String group) {
        return mRepository.getItemListByGroup(group);
    }

    public LiveData<String> getListFilter() {
        return mListFilter;
    }

    public void setListFilter(String group) {
        mListFilter.setValue(group);
    }

    public LiveData<List<Item>> getItemsWithName(String name) {
        return mRepository.getItemsWithName(name);
    }

}
