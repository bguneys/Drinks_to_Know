package com.bguneys.myapplication.drinkstoknow.main;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final MutableLiveData<Item> mItem = new MutableLiveData<>();
    private LiveData<Item> mLiveDataItem;

    public MainViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    public void getRandomItem() {
        mItem.setValue(mRepository.getRandomItem());
        mLiveDataItem = mItem;
    }

    public LiveData<Item> getItem() {
        return mLiveDataItem;
    }

    public void setFavorite(Item item) {
        mRepository.updateFavorite(item);
    }

    public void getNextItemWithId(int id) {
        mItem.setValue(mRepository.getNextItemWithId(id));
        mLiveDataItem = mItem;
    }
}
