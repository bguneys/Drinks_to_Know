package com.bguneys.myapplication.drinkstoknow.main;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.list.ListViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;

    public MainViewModelFactory(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
