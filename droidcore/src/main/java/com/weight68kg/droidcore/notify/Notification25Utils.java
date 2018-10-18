package com.weight68kg.droidcore.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

/**
 * Created by weight68kg on 2018/9/10.
 */

public class Notification25Utils extends NotifycationBase implements NotifycationOb {


    public Notification25Utils(Context base) {
        super(base);
    }


    @Override
    public Notification getNotification(String title, String content) {
        return getNotification_25(title, content).build();
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }


}
