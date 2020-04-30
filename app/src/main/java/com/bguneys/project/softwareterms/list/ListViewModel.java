package com.bguneys.project.softwareterms.list;

import com.bguneys.project.softwareterms.database.DataRepository;
import com.bguneys.project.softwareterms.database.Item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final DataRepository mRepository;
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
