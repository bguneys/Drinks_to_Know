package com.bguneys.myapplication.drinkstoknow.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bguneys.myapplication.drinkstoknow.R;
import com.bguneys.myapplication.drinkstoknow.notification.NotificationWorker;

import java.util.concurrent.TimeUnit;

import androidx.core.app.ShareCompat;
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


        switch(preference.getKey()) {

            case "notification_preference":

                if (((SwitchPreference) preference).isChecked()) {
                    PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                            NotificationWorker.class, 1, TimeUnit.DAYS);
                    workRequestBuilder.setInitialDelay(1, TimeUnit.DAYS);
                    PeriodicWorkRequest workRequest = workRequestBuilder.build();
                    mWorkManager.enqueue(workRequest);

                } else {
                    mWorkManager.cancelAllWork();
                }

                break;

            case "disclaimer_preference":

                Intent disclaimerIntent = new Intent(getActivity(), DisclaimerActivity.class);
                startActivity(disclaimerIntent);

                break;

            case "about_preference":

                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);

                break;

            case "share_app_preference":

                ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle(R.string.share_this_app_with)
                        .setText("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                        .startChooser();

                break;

            case "rate_app_preference":

                String url = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                Uri uri = Uri.parse(url);
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(rateIntent);

                break;

            default:
                break;
        }

        return super.onPreferenceTreeClick(preference);
    }
}
