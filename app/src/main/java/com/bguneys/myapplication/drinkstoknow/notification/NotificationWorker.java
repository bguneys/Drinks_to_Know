package com.bguneys.myapplication.drinkstoknow.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.database.DataRepository;
import com.bguneys.myapplication.drinkstoknow.database.Item;
import com.bguneys.myapplication.drinkstoknow.details.DetailsActivity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private static final String CHANNEL_ID = "notify-item";
    private static final int NOTIFICATION_ID = 1;
    public static final String EXTRA_ITEM_ID = "com.bguneys.myapplication.EXTRA_ITEM_ID";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    public Result doWork() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        DataRepository instance = DataRepository.getInstance(getApplicationContext());

        Item randomItem = instance.getRandomItem();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    getApplicationContext().getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(getApplicationContext().getResources().getString(R.string.notification_channel_description));
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, randomItem.getItemId());

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getApplicationContext().getResources().getString(R.string.notification_title))
                .setContentText(getApplicationContext().getResources().getString(R.string.notification_message, randomItem.getItemName()))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        return Result.success();
    }
}
