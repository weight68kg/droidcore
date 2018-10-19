package com.weight68kg.droidcore.utils.notify;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.weight68kg.droidcore.R;

import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;

/**
 * Created by weight68kg on 2018/9/10.
 */

public class Notification25Utils extends NotifycationBase implements NotifycationOb {


    public Notification25Utils(Context base) {
        super(base);
    }

    @Override
    public NotificationCompat.Builder converBuilder(NotificationUtils.Builder build) {
        return mBuilder.setContentTitle(build.title)
                .setContentText(build.content)
                .setSmallIcon(build.SmallIcon != 0 ? build.SmallIcon : R.mipmap.shanzhi)
                .setWhen(build.when)
                .setContentIntent(build.contentIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), build.largeIcon))
                .setNumber(build.number)
                .setPriority(build.priority)
                .setTicker(build.ticker)
                .setAutoCancel(build.autoCancel);
    }

    @Override
    public Notification getNotificationInstance(NotificationUtils.Builder builder) {
        converBuilder(builder);
        return getNotification_25();
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

    @Override
    public Notification getHeadsUp(NotificationUtils.Builder builder) {
        converBuilder(builder);
        if (builder.action.length > 0) {
            for (NotificationCompat.Action a : builder.action) {
                mBuilder.addAction(a);
            }
        }
        mBuilder.setFullScreenIntent(builder.contentIntent,true);
        return getNotification_25();
    }

    @Override
    public Notification getButton(NotificationUtils.Builder builder) {
        converBuilder(builder);
        if (builder.action.length > 0) {
            for (NotificationCompat.Action a : builder.action) {
                mBuilder.addAction(a);
            }
        }
        return getNotification_25();
    }


    public Notification getNotification_25() {
        return mBuilder.build();
    }

}
