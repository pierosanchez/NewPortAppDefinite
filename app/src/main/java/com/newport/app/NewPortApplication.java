package com.newport.app;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.newport.app.util.CustomTabsActivityLifecycleCallbacks;

import java.util.HashMap;

/**
 * Created by tohure on 11/11/17.
 */

public class NewPortApplication extends Application {

    private static Context context;

    private static final String PROPERTY_ID = "UA-109625996-1";
    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("general");
        registerActivityLifecycleCallbacks(new CustomTabsActivityLifecycleCallbacks());
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t =
                    (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID):
                            (trackerId == TrackerName.GLOBAL_TRACKER) ?
                                    analytics.newTracker(R.xml.global_tracker):
                                    analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
}