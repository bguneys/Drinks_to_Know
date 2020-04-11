package com.bguneys.myapplication.drinkstoknow.database;

import android.content.Context;
import android.util.Log;

import com.bguneys.myapplication.drinkstoknow.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    private static volatile ItemDatabase sInstance = null;

    public abstract ItemDao getItemDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static WeakReference<Context> sContext;

    @NonNull
    public static synchronized ItemDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ItemDatabase.class) {
                if (sInstance == null) {
                    sInstance = (ItemDatabase) Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, Constants.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDataBaseCallBack)
                            .build();
                }
            }
        }

        sContext = new WeakReference<>(context);

        return sInstance;
    }

    private static RoomDatabase.Callback roomDataBaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull final SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ItemDao dao = getInstance(sContext.get()).getItemDao();
                    //dao.deleteAll();

                    if (dao.getAnyItem().length < 1) {
                       parseJson(sContext.get());
                    }

                }
            });
        }
    };

    /**
     * Custom method for getting JSON file and converting it into JSONObject then creating new
     * Word objects from the data inside JSON.
     * @param context
     */
    private static void parseJson(Context context) {
        ItemDao dao = getInstance(context).getItemDao();
        String json = "";

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.drinks_json_file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line ="";

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            json = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray drinks = jsonObject.getJSONArray("drinks");

            for (int i = 0; i < drinks.length(); i++) {
                JSONObject jsonDrink = drinks.getJSONObject(i);

                String drinkName = jsonDrink.getString("item_name");
                String drinkDescription = jsonDrink.getString("item_description");
                int drinkImage = context.getResources().getIdentifier(jsonDrink.getString("item_image"), "drawable", context.getPackageName());
                boolean drinkFavourite = jsonDrink.getBoolean("item_favourite");

                Item newItem = new Item(drinkName, drinkDescription, drinkImage, drinkFavourite);
                dao.insert(newItem);
            }
        }
        catch (Exception e) {
            //TODO: Delete Log when building proudction version
            Log.e("Error", "JSON Error");
        }
    }

}
