package com.weight68kg.droidcore.utils.notify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotifycationBase extends ContextWrapper {

    public final String name = "channel_name_1";
    public final String id = "channel_1";
    protected NotificationManager manager;
    NotificationCompat.Builder mBuilder;

    public NotifycationBase(Context base) {
        super(base);
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), id);
    }


    protected NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    NotificationCompat.Builder converBuilder(NotificationUtils.Builder build) {
        return mBuilder;
    }


}
