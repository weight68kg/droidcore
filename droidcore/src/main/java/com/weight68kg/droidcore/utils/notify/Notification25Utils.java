package com.weight68kg.droidcore.utils.notify;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import com.weight68kg.droidcore.R;

import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;

import static androidx.core.app.NotificationCompat.BADGE_ICON_SMALL;

/**
 * Created by weight68kg on 2018/9/10.
 */

public class Notification25Utils extends NotifycationBase implements NotifycationOb {


    public Notification25Utils(Context base) {
        super(base);
    }

    @Override
    public NotificationCompat.Builder converBuilder(NotificationUtils.Builder build) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        return mBuilder.setContentTitle(build.title)
                .setContentText(build.content)
                .setSmallIcon(build.SmallIcon != 0 ? build.SmallIcon : R.mipmap.shanzhi)
                .setWhen(build.when !=0 ?build.when:System.currentTimeMillis())
                .setContentIntent(build.contentIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), build.largeIcon))
                .setPriority(build.priority)//优先级
                .setTicker(build.ticker)//悬浮title
                .setBadgeIconType(BADGE_ICON_SMALL)//角标
                .setSound(alarmSound)// 设置通知的提示音
                .setNumber(99)//角标数
                .setAutoCancel(build.autoCancel);
    }

    @Override
    public Notification getSingleLine(NotificationUtils.Builder builder) {
        converBuilder(builder);
        return getNotification();
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
        return getNotification();
    }

    @Override
    public Notification getBigIcon(NotificationUtils.Builder builder) {
        return null;
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
        mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        return getNotification();
    }

    @Override
    public Notification getButton(NotificationUtils.Builder builder) {
        converBuilder(builder);
        if (builder.action.length > 0) {
            for (NotificationCompat.Action a : builder.action) {
                mBuilder.addAction(a);
            }
        }
        return getNotification();
    }


    public Notification getNotification() {
        return mBuilder.build();
    }

}
