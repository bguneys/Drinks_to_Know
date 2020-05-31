package com.bguneys.myapplication.drinkstoknow;

import android.content.Context;

import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.database.ItemDao;
import com.bguneys.myapplication.drinkstoknow.database.ItemDatabase;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    private ItemDatabase itemDatabase;
    private ItemDao itemDao;

    @Before
    public void createDatabase() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        itemDatabase = Room.inMemoryDatabaseBuilder(appContext, ItemDatabase.class).allowMainThreadQueries().build();
        itemDao = itemDatabase.getItemDao();
    }

    @After
    public void closeDatabase() {
        try {
            itemDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test for inserting item into database
     */
    @Test
    public void insertItem() {
        try {
            Item item = new Item(
                    1,
                    "Test Item Name",
                    "Test Summary",
                    "Test Description",
                    1,
                    false,
                    "Test Source",
                    "Test Source Url",
                    "Test Image",
                    "Test Image Url",
                    "Test Group");

            itemDao.insert(item);

            Item[] testItem = itemDao.getAnyItem();
            assertEquals(testItem[0].getItemName(), "Test Item Name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
