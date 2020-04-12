package com.bguneys.myapplication.drinkstoknow.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items_table ORDER BY item_name COLLATE NOCASE ASC") //capital letter no case-sensitive order
    LiveData<List<Item>> getItemList();

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM Items_table LIMIT 1")
    Item[] getAnyItem();

    @Query("SELECT * FROM Items_table ORDER BY RANDOM() LIMIT 1")
    Item getRandomItem();

    @Query("DELETE FROM Items_table")
    void deleteAll();

    @Query("SELECT * FROM Items_table WHERE item_id LIKE:itemId")
    LiveData<Item> getItemWithId(int itemId);

    @Update
    void updateFavorite(Item item);
}
