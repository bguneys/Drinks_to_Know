package com.bguneys.myapplication.drinkstoknow.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DrinkDao {

    @Query("SELECT * FROM drinks_table ORDER BY item_name COLLATE NOCASE ASC") //capital letter no case-sensitive order
    LiveData<List<Drink>> getDrinkList();

    @Insert
    void insert(Drink drink);

    @Delete
    void delete(Drink drink);

    @Query("SELECT * FROM drinks_table LIMIT 1")
    Drink[] getAnyDrink();
}
