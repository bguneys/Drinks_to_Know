package com.bguneys.myapplication.drinkstoknow.details;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DetailsViewModel extends ViewModel {

    private final DataRepository mRepository;
    private LiveData<Drink> mLiveDataDrink;

    public DetailsViewModel(DataRepository dataRepository, int drinkId) {
        mRepository = dataRepository;
        mLiveDataDrink = mRepository.getDrinkWithId(drinkId);
    }

    public LiveData<Drink> getDrink() {
        return mLiveDataDrink;
    }

}

