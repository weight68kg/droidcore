package com.weight68kg.droidcore.utils.notify;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;

/**
 * Created by weight68kg on 2018/9/10.
 */

public class Notification25Utils extends NotifycationBase implements NotifycationOb {


    public Notification25Utils(Context base) {
        super(base);
    }

    @Override
    public void converBuilder(NotificationUtils.Builder build) {

    }

    @Override
    public Notification getNotificationInstance(NotificationUtils.Builder builder) {
        return getNotification_25(builder).build();
    }

    @Override
    public Notification getNormalMoreLine(NotificationUtils.Builder builder) {
        return null;
    }

    @Override
    public Notification getMessageList(NotificationUtils.Builder builder) {
        return null;
    }

    @Override
    public Notification getBigIcon(NotificationUtils.Builder builder) {
        return null;
    }


    public NotificationCompat.Builder getNotification_25(NotificationUtils.Builder builder) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(builder.title)
                .setContentText(builder.content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(builder.autoCancel);

    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }


}
