package com.weight68kg.droidcore.utils.notify;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

/**
 * Created by weight68kg on 2018/9/10.
 */
public class Notification26Utils extends Notification25Utils implements NotificationCallBack {


    public Notification26Utils(Context base) {
        super(base);
        createNotificationChannel();
    }

    /**
     * 创建通知渠道
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel
                = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
//        channel.setShowBadge(true);// 设置是否显示角标
        getManager().createNotificationChannel(channel);
    }
}
