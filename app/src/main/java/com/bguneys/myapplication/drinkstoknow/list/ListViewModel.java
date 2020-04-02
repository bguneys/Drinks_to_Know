package com.bguneys.myapplication.drinkstoknow.list;

import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Drink;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    private final DataRepository mRepository;
    private LiveData<List<Drink>> mDrinkList;

    public ListViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
        mDrinkList = mRepository.getDrinkList();
    }

    public LiveData<List<Drink>> getDrinkList() {
        return mDrinkList;
    }

    public void insert(Drink drink) {
        mRepository.insert(drink);
    }



}
