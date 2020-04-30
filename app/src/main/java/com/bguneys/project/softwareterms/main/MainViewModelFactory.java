package com.bguneys.project.softwareterms.main;

import com.bguneys.project.softwareterms.database.DataRepository;

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
