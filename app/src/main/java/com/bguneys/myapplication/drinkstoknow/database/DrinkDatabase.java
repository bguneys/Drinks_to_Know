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

@Database(entities = {Drink.class}, version = 1, exportSchema = false)
public abstract class DrinkDatabase extends RoomDatabase {

    private static volatile DrinkDatabase sInstance = null;

    public abstract DrinkDao getDrinkDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static WeakReference<Context> sContext;

    @NonNull
    public static synchronized DrinkDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DrinkDatabase.class) {
                if (sInstance == null) {
                    sInstance = (DrinkDatabase) Room.databaseBuilder(context.getApplicationContext(),
                            DrinkDatabase.class, Constants.DATABASE_NAME)
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
                    DrinkDao dao = getInstance(sContext.get()).getDrinkDao();
                    //dao.deleteAll();

                    if (dao.getAnyDrink().length < 1) {
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
        DrinkDao dao = getInstance(context).getDrinkDao();
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
            JSONArray words = jsonObject.getJSONArray("drinks");

            for (int i = 0; i < words.length(); i++) {
                JSONObject jsonDrink = words.getJSONObject(i);
                Drink newDrink = new Drink(jsonDrink.getString("name"), jsonDrink.getString("description"), jsonDrink.getInt("image"), jsonDrink.getBoolean("favourite"));
                dao.insert(newDrink);
            }
        }
        catch (Exception e) {
            //TODO: Delete Log when building proudction version
            Log.e("Error", "JSON Error");
        }
    }

}
