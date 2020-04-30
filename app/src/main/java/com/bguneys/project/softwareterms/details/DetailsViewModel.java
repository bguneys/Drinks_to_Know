package com.bguneys.project.softwareterms.details;

import com.bguneys.project.softwareterms.database.DataRepository;
import com.bguneys.project.softwareterms.database.Item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DetailsViewModel extends ViewModel {

    private final DataRepository mRepository;
    private LiveData<Item> mLiveDataItem;

    public DetailsViewModel(DataRepository dataRepository, int itemId) {
        mRepository = dataRepository;
        mLiveDataItem = mRepository.getItemWithId(itemId);
    }

    public LiveData<Item> getItem() {
        return mLiveDataItem;
    }

    public void setFavorite(Item item) {
        mRepository.updateFavorite(item);
    }

}

