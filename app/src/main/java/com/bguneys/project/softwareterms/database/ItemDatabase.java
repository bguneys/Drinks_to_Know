package com.bguneys.project.softwareterms.database;

import android.content.Context;
import android.widget.Toast;

import com.bguneys.project.softwareterms.R;

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
            InputStream inputStream = context.getResources().openRawResource(R.raw.items_json_file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line ="";

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            json = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject jsonItem = items.getJSONObject(i);

                int itemId = jsonItem.getInt("item_id");
                String itemName = jsonItem.getString("item_name");
                String itemSummary = jsonItem.getString("item_summary");
                String itemDescription = jsonItem.getString("item_description");
                int itemImage = context.getResources().getIdentifier(jsonItem.getString("item_image"), "drawable", context.getPackageName());
                boolean itemFavourite = jsonItem.getBoolean("item_favourite");
                String itemSourceText = jsonItem.getString("item_text_source");
                String itemSourceTextUrl = jsonItem.getString("item_text_source_url");
                String itemSourceImage = jsonItem.getString("item_image_source");
                String itemSourceImageUrl = jsonItem.getString("item_image_source_url");
                String itemGroup = jsonItem.getString("item_group");

                Item newItem = new Item(itemId,
                        itemName,
                        itemSummary,
                        itemDescription,
                        itemImage,
                        itemFavourite,
                        itemSourceText,
                        itemSourceTextUrl,
                        itemSourceImage,
                        itemSourceImageUrl,
                        itemGroup);

                dao.insert(newItem);
            }
        }
        catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), R.string.data_loading_error_message, Toast.LENGTH_SHORT).show();
        }
    }

}
