package com.bguneys.myapplication.drinkstoknow.database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class DataRepository {

    //fields
    private DrinkDao mDrinkDao;
    private LiveData<List<Drink>> mDrinkList;

    //constructor
    public DataRepository(Application application) {
        DrinkDatabase wordDatabase = DrinkDatabase.getInstance(application);
        mDrinkDao = wordDatabase.getDrinkDao();
        mDrinkList = mDrinkDao.getDrinkList();
    }

    //methods
    public LiveData<List<Drink>> getDrinkList() {
        return mDrinkList;
    }

    public void insert (final Drink drink) {
        //Using ExecutorService instead of AsyncTask
        DrinkDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDrinkDao.insert(drink);
            }
        });
    }

    public void delete (final Drink drink) {
        //Using ExecutorService instead of AsyncTask
        DrinkDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDrinkDao.delete(drink);
            }
        });
    }

    @Nullable
    public Drink getRandomDrink() {
        try {
            return (Drink) DrinkDatabase.databaseExecutor.submit(new Callable() {
                public final Object call() {
                    //return TeaDao.this.getRandomTea();
                    return mDrinkDao.getRandomDrink();
                }
            }).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
