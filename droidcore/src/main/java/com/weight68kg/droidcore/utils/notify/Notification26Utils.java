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
public class Notification26Utils extends NotifycationBase implements NotifycationOb {


    public Notification26Utils(Context base) {
        super(base);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public NotificationCompat.Builder converBuilder(NotificationUtils.Builder builder) {
        createNotificationChannel();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return mBuilder.setContentTitle(builder.title)// 设置通知栏标题
                .setContentText(builder.content)// 设置通知栏显示内容
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 设置该通知优先级
                .setSmallIcon(android.R.drawable.stat_notify_more)// 设置通知小ICON
                .setTicker(builder.ticker)
                .setSound(alarmSound)// 设置通知的提示音
                .setBadgeIconType(BADGE_ICON_SMALL)
                .setNumber(99)
                .setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
    }

    @Override
    public Notification getNotificationInstance(NotificationUtils.Builder builder) {
        converBuilder(builder);
        return getChannelNotification();
    }

    @Override
    public Notification getNormalMoreLine(NotificationUtils.Builder builder) {
        converBuilder(builder);
        return new NotificationCompat.BigTextStyle(mBuilder).bigText(builder.content).build();
    }

    @Override
    public Notification getMessageList(NotificationUtils.Builder builder) {
        converBuilder(builder);
//        Intent deleteIntent = new Intent(mContext, DeleteService.class);
//        int deleteCode = (int) SystemClock.uptimeMillis();
//        PendingIntent deletePendingIntent = PendingIntent.getService(mContext,
//                deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        cBuilder.setDeleteIntent(deletePendingIntent);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), builder.SmallIcon);
        mBuilder.setLargeIcon(bitmap);

        //cBuilder.setVibrate(new long[]{0, 100, 200, 300});// 设置自定义的振动
        // builder.setSound(Uri.parse("file:///sdcard/click.mp3"));

        // 设置通知样式为收件箱样式,在通知中心中两指往外拉动，就能出线更多内容，但是很少见
//        mBuilder.setNumber(builder.messageList.size());
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(builder.content);
        inboxStyle.setSummaryText(builder.title);
        for (String msg : builder.messageList) {
            inboxStyle.addLine(msg);
        }
        inboxStyle.setSummaryText("[" + builder.messageList.size() + "条]" + builder.title);
        mBuilder.setStyle(inboxStyle);
        return getChannelNotification();
    }

    @Override
    public Notification getBigIcon(NotificationUtils.Builder builder) {
        converBuilder(builder);
        return getChannelNotification();
    }

    @SuppressLint("WrongConstant")
    @Override
    public Notification getHeadsUp(NotificationUtils.Builder builder) {
        converBuilder(builder);
        if (builder.action.length > 0) {
            for (NotificationCompat.Action a : builder.action) {
                mBuilder.addAction(a);
            }
        }
        mBuilder.setFullScreenIntent(builder.contentIntent,true);
        Log.e("tag",builder.contentIntent+"");
        mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        return getChannelNotification();
    }

    @Override
    public Notification getButton(NotificationUtils.Builder builder) {
        converBuilder(builder);
        if (builder.action.length > 0) {
            for (NotificationCompat.Action a : builder.action) {
                mBuilder.addAction(a);
            }
        }
        return getChannelNotification();
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


    @TargetApi(Build.VERSION_CODES.O)
    public Notification getChannelNotification() {
        return mBuilder.build();
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
