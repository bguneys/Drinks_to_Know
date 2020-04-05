package com.bguneys.myapplication.drinkstoknow.database;

import android.app.Application;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class DataRepository {

    //fields
    private DrinkDao mDrinkDao;
    private LiveData<List<Drink>> mDrinkList;
    private static volatile DataRepository sInstance = null;

    public static DataRepository getInstance(Context context) {

        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(context);
                }
            }
        }

        return sInstance;
    }

    private DataRepository(Context context) {
        DrinkDatabase wordDatabase = DrinkDatabase.getInstance(context);
        mDrinkDao = wordDatabase.getDrinkDao();
        mDrinkList = mDrinkDao.getDrinkList();
    }

    public LiveData<List<Drink>> getDrinkList() {
        return mDrinkList;
    }

    public LiveData<Drink> getDrinkWithId(int id) {
        return mDrinkDao.getDrinkWithId(id);
    }

    public void insert (final Drink drink) {
        DrinkDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDrinkDao.insert(drink);
            }
        });
    }

    public void delete (final Drink drink) {
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
                    return mDrinkDao.getRandomDrink();
                }
            }).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
