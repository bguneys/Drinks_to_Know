package com.bguneys.project.softwareterms.list;

import com.bguneys.project.softwareterms.database.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ListViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;

    public ListViewModelFactory(DataRepository dataRepository) {
        mRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel(mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
