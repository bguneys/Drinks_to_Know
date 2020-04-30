package com.bguneys.project.softwareterms.details;

import com.bguneys.project.softwareterms.database.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;
    private final int mItemId;

    public DetailsViewModelFactory(DataRepository dataRepository, int itemId) {
        mRepository = dataRepository;
        mItemId = itemId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(mRepository, mItemId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
} 

