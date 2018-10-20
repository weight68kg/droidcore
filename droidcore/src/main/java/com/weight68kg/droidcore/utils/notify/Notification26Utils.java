package com.weight68kg.droidcore.utils.notify;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.weight68kg.droidcore.R;

import static android.app.Notification.PRIORITY_HIGH;
import static android.app.Notification.VISIBILITY_SECRET;
import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.app.NotificationManager.IMPORTANCE_UNSPECIFIED;
import static androidx.core.app.NotificationCompat.BADGE_ICON_SMALL;

/**
 * Created by weight68kg on 2018/9/10.
 */
public class Notification26Utils extends Notification25Utils implements NotifycationOb {


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
        channel.setShowBadge(true);// 设置是否显示角标
        getManager().createNotificationChannel(channel);
    }




    /**
     * 设置在顶部通知栏中的各种信息
     *
     * @param title         标题
     * @param content       内容
     * @param pendingIntent 跳转意图
     * @param smallIcon     小图标
     * @param ticker        顶部状态栏中的提示信息
     * @param sound         声音
     * @param vibrate       震动
     * @param lights        提示灯
     * @return
     */
    @TargetApi(Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String title, String content, PendingIntent pendingIntent, int smallIcon, String ticker,
                                                        boolean sound, boolean vibrate, boolean lights) {
        //        // 如果当前Activity启动在前台，则不开启新的Activity。
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        // 当设置下面PendingIntent.FLAG_UPDATE_CURRENT这个参数的时候，常常使得点击通知栏没效果，你需要给notification设置一个独一无二的requestCode
//        // 将Intent封装进PendingIntent中，点击通知的消息后，就会启动对应的程序
//        PendingIntent pIntent = PendingIntent.getActivity(mContext,
//                requestCode, intent, FLAG);
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), id);
        builder.setContentIntent(pendingIntent);// 该通知要启动的Intent
        builder.setSmallIcon(smallIcon);// 设置顶部状态栏的小图标
        builder.setTicker(ticker);// 在顶部状态栏中的提示信息

        builder.setContentTitle(title);// 设置通知中心的标题
        builder.setContentText(content);// 设置通知中心中的内容
        builder.setWhen(System.currentTimeMillis());

        /*
         * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
         * 不设置的话点击消息后也不清除，但可以滑动删除
         */
        builder.setAutoCancel(true);
        // 将Ongoing设为true 那么notification将不能滑动删除
//        builder.setOngoing(true);
        /*
         * 从Android4.1开始，可以通过以下方法，设置notification的优先级，
         * 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
         */
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        /*
         * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
         * Notification.DEFAULT_SOUND：系统默认铃声。
         * Notification.DEFAULT_VIBRATE：系统默认震动。
         * Notification.DEFAULT_LIGHTS：系统默认闪光。
         * notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
         */
        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        builder.setDefaults(defaults);
        return builder;

    }

}
