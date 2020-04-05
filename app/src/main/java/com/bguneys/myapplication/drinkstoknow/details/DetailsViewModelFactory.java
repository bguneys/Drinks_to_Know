package com.bguneys.myapplication.drinkstoknow.details;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;
    private final int mDrinkId;

    public DetailsViewModelFactory(DataRepository dataRepository, int drinkId) {
        mRepository = dataRepository;
        mDrinkId = drinkId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(mRepository, mDrinkId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
} 

