package com.bguneys.myapplication.drinkstoknow.settings;

import android.os.Bundle;
import android.widget.Toast;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.notification.NotificationWorker;

import java.util.concurrent.TimeUnit;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String WORKER_TAG = "notification_worker";
    private WorkManager mWorkManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        mWorkManager = WorkManager.getInstance(getActivity());

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        if (preference.getKey().equals(getString(R.string.notification_preference))) {

            if (((SwitchPreference) preference).isChecked()) {
                PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(NotificationWorker.class, 15, TimeUnit.MINUTES);
                workRequestBuilder.setInitialDelay(15, TimeUnit.MINUTES);
                PeriodicWorkRequest workRequest = workRequestBuilder.build();
                mWorkManager.enqueue(workRequest);

            } else {
                mWorkManager.cancelAllWork();
            }

        }

        return super.onPreferenceTreeClick(preference);
    }
}
