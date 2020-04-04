package com.bguneys.myapplication.drinkstoknow.main;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final MutableLiveData<Drink> mDrink = new MutableLiveData<>();
    private LiveData<Drink> mLiveDataDrink;

    public MainViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    public void getRandomDrink() {
        mDrink.setValue(mRepository.getRandomDrink());
        mLiveDataDrink = mDrink;
    }

    public LiveData<Drink> getDrink() {
        return mLiveDataDrink;
    }
}
