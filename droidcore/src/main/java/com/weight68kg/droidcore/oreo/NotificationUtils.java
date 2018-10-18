package com.weight68kg.droidcore.oreo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.weight68kg.droidcore.R;
import com.weight68kg.droidcore.common.Notification25Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by weight68kg on 2018/9/10.
 */

public class NotificationUtils extends Notification25Utils {


    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    private Notification notification;
    private NotificationCompat.Builder builder;

    public NotificationUtils(Context context) {
        super(context);
        // 获取系统服务来初始化对象
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)// 设置通知栏标题
                .setContentText(content)// 设置通知栏显示内容
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
                .setSmallIcon(android.R.drawable.stat_notify_more)// 设置通知小ICON
                .setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content, PendingIntent pendingIntent) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)// 设置通知栏标题
                .setContentText(content)// 设置通知栏显示内容
                .setContentIntent(pendingIntent)// 跳转意图
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
                .setSmallIcon(android.R.drawable.stat_notify_more)// 设置通知小ICON
                .setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
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

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public void sendNotification(String title, String content, PendingIntent pendingIntent, int notifyId) {
        Notification notification = getNotification(title, content);
        getManager().notify(notifyId, notification);
    }

    private Notification getNotification(String title, String content) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification.Builder channelNotification = getChannelNotification(title, content);
            notification = channelNotification.build();
        } else {
            NotificationCompat.Builder notification_25 = getNotification_25(title, content);
            notification = notification_25.build();
        }
        return notification;
    }


    public void informShow(Context context, String title, String content,
                           PendingIntent pendingIntent, int notifyId) {
        // 获取状态通知栏管理
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 实例化通知栏构造器NotificationCompat.Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle(title)// 设置通知栏标题
                .setContentText(content)// 设置通知栏显示内容
//                .setContentIntent(pendingIntent)// 跳转意图
                // //设置通知栏点击意图
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
                .setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
                .setSmallIcon(R.mipmap.shanzhi);// 设置通知小ICON

        Notification notify = mBuilder.build();
        mNotificationManager.notify(notifyId + 1000, notify);
    }


    /**
     * 普通单行通知
     */
    public void normalSingleLine(String title, String content, int notifyId) {
        normalSingleLine(title, content,null,notifyId);
    }

    public void normalSingleLine(String title, String content, PendingIntent pendingIntent, int notifyId) {
        sendNotification(title, content, pendingIntent, notifyId);
    }

    /**
     * 多行通知
     */
    public void normalMoreLine() {

    }

    /**
     * 消息列表通知
     */
    public void messageList() {

    }

    /**
     * 大图通知
     */
    public void bigIcon() {

    }

    /**
     * 自定义通知
     */
    public void customView() {

    }

    /**
     * 折叠双按钮通知
     */
    public void cordButton() {

    }

    /**
     * 进度条通知
     */
    public void progress() {

    }

    /**
     * 悬浮通知
     */
    public void suspension() {

    }


    //检查系统是否关闭app应用的通知权限
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
     /* Context.APP_OPS_MANAGER */
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsClass = Class.forName(AppOpsManager.class.getName());

                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                        Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

                int value = (Integer) opPostNotificationValue.get(Integer.class);

                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                        AppOpsManager.MODE_ALLOWED);
            }
            return true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void goToSet(Activity context) {
        Intent intent = new Intent()
                .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package",
                        context.getApplicationContext().getPackageName(), null));
        context.startActivity(intent);
    }

}
