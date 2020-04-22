package com.bguneys.myapplication.drinkstoknow.main;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final MutableLiveData<Item> mDrink = new MutableLiveData<>();
    private LiveData<Item> mLiveDataDrink;

    public MainViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    public void getRandomItem() {
        mDrink.setValue(mRepository.getRandomItem());
        mLiveDataDrink = mDrink;
    }

    public LiveData<Item> getItem() {
        return mLiveDataDrink;
    }

    public void setFavorite(Item item) {
        mRepository.updateFavorite(item);
    }

    public void showNextItem(int itemId) {
        mDrink.setValue(mRepository.getItemWithId(itemId).getValue());
        mLiveDataDrink = mDrink;
    }

    public void getNextItemWithId(int id) {
        mDrink.setValue(mRepository.getNextItemWithId(id));
        mLiveDataDrink = mDrink;
    }
}
