package com.weight68kg.droidcore.utils.notify;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.NotificationCompat;

/**
 * 创建 {@link Notification} 有两种方式：
 * 1. {@link Notification.Builder#build()}
 * 2. {@link NotificationCompat.Builder#build()}
 * <p>
 * 使用第二种方式创建是兼容低版本，
 * 当 {@link android.os.Build.VERSION_CODES#O} 大于26，
 * 会自动使用 第一种方法
 * <p>
 * 当使用第二种方法时 需要创建通知渠道 {@link android.app.NotificationChannel}
 */
public class NotificationUtils extends ContextWrapper {

    private NotificationCallBack notificationCallBack;
    private Notification notification;
    private int notifyId;

    public NotificationUtils(Context context, int notifyId) {
        super(context);
        this.notifyId = notifyId;
        if (checkPermission(context)) return;
        checkVersion(context);
    }

    private boolean checkPermission(Context context) {
        //判断是否开启通知权限
        if (!isNotificationEnabled(context)) {
            goToSet((Activity) context);
            return true;
        }
        return false;
    }

    private void checkVersion(Context context) {
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationCallBack = new Notification26Utils(context);
        } else {
            notificationCallBack = new Notification25Utils(context);
        }
    }


    /**
     * 获取 {@link android.app.Notification}
     *
     * @return
     */
    protected Notification getNotification(Builder builder) {
        return notificationCallBack.getSingleLine(builder);
    }


    public void sendNotification() {
        ((NotifycationBase) notificationCallBack)
                .getManager().notify(notifyId, notification);
    }


    /**
     * 普通单行通知
     */
    public void normalSingleLine(Builder builder) {
        notification = getNotification(builder);
        sendNotification();
    }


    /**
     * 多行通知
     */
    public void normalMoreLine(Builder builder) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            normalSingleLine(builder);
            Toast.makeText(this, "您的手机低于Android 4.1.2，不支持多行通知显示！！", Toast.LENGTH_SHORT).show();
        } else {
            notification = notificationCallBack.getNormalMoreLine(builder);
            sendNotification();
        }
    }

    /**
     * 消息列表通知
     */
    public void messageList(Builder builder) {
        notification = notificationCallBack.getMessageList(builder);
        sendNotification();
    }

    /**
     * 大图通知
     */
    public void bigIcon(Builder builder) {
        notification = notificationCallBack.getBigIcon(builder);
        sendNotification();
    }

    /**
     * 自定义通知
     */
    public void customView(Builder builder) {
        notification = notificationCallBack.getCustomView(builder);
        sendNotification();
    }

    /**
     * 折叠双按钮通知
     */
    public void cordButton(Builder builder) {
        notification = notificationCallBack.getButton(builder);
        sendNotification();
    }

    /**
     * 进度条通知
     */
    public void progress(final Builder builder) {

        /*
         * 因为进度条要实时更新通知栏也就说要不断的发送新的提示，所以这里不建议开启通知声音。
         * 这里是作为范例，给大家讲解下原理。所以发送通知后会听到多次的通知声音。
         */

        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for (incr = 0; incr <= 100; incr += 10) {
                    // 参数：1.最大进度， 2.当前进度， 3.是否有准确的进度显示
                    builder.setProgress(100, incr, false);
                    // cBuilder.setProgress(0, 0, true);
                    notification = notificationCallBack.getProgress(builder);
                    sendNotification();
                    builder.setPriority(NotificationCompat.PRIORITY_LOW);
                    try {
                        Thread.sleep(1 * 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 进度满了后，设置提示信息
                builder.setContent("下载完成").setProgress(0, 0, false);
                notification = notificationCallBack.getProgress(builder);
                sendNotification();
            }
        }).start();


    }

    /**
     * 悬浮通知
     */
    public void headsUp(Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = notificationCallBack.getHeadsUp(builder);
            sendNotification();
        } else {
            Toast.makeText(this, "版本低于Andriod5.0，无法体验HeadUp样式通知", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 检查系统是否关闭app应用的通知权限
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
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


    //去设置页开启通知
    public static void goToSet(Activity context) {
        Intent intent = new Intent()
                .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package",
                        context.getApplicationContext().getPackageName(), null));
        context.startActivity(intent);
    }


    public static class Builder {
        String title;// 设置通知中心的标题
        String content;// 设置通知中心中的内容
        PendingIntent contentIntent; //该通知要启动的Intent
        int largeIcon;//设置顶部状态栏的大图标
        int number;//角标数
        /*
         * 从Android4.1开始，可以通过以下方法，设置notification的优先级，
         * 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
         */
        int priority;//优先级
        Notification.Style style;//扩展
        long when;//时间
        int SmallIcon;// 设置顶部状态栏的小图标
        String ticker;// 在顶部状态栏中的提示信息
        boolean autoCancel;/* 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失, 不设置的话点击消息后也不清除，但可以滑动删除*/
        boolean ongoing;/*将Ongoing设为true 那么notification将不能滑动删除*/
        boolean sound;//声音
        boolean light;//提示灯
        boolean vibrate;//震动
        int progressMax;
        int progress;
        boolean progressIndeterminate;
        RemoteViews remoteViews;


        /*
         * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
         * Notification.DEFAULT_SOUND：系统默认铃声。
         * Notification.DEFAULT_VIBRATE：系统默认震动。
         * Notification.DEFAULT_LIGHTS：系统默认闪光。
         */
        int defaults;

        List<String> messageList;

        NotificationCompat.Action[] action;

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public long getWhen() {
            return when;
        }

        public Builder setWhen(long when) {
            this.when = when;
            return this;
        }

        public PendingIntent getContentIntent() {
            return contentIntent;
        }

        public Builder setContentIntent(PendingIntent contentIntent) {
            this.contentIntent = contentIntent;
            return this;
        }

        public int getSmallIcon() {
            return SmallIcon;
        }

        public Builder setSmallIcon(int smallIcon) {
            SmallIcon = smallIcon;
            return this;
        }

        public int getLargeIcon() {
            return largeIcon;
        }

        public Builder setLargeIcon(int largeIcon) {
            this.largeIcon = largeIcon;
            return this;
        }

        public String getTicker() {
            return ticker;
        }

        public Builder setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public boolean isAutoCancel() {
            return autoCancel;
        }

        public Builder setAutoCancel(boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }

        public boolean isOngoing() {
            return ongoing;
        }

        public Builder setOngoing(boolean ongoing) {
            this.ongoing = ongoing;
            return this;
        }

        public int getPriority() {
            return priority;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public int getDefaults() {
            return defaults;
        }

        public Builder setDefaults(int defaults) {
            this.defaults = defaults;
            return this;
        }

        public List<String> getMessageList() {
            return messageList;
        }

        public Builder setMessageList(List<String> messageList) {
            this.messageList = messageList;
            return this;
        }

        public int getNumber() {
            return number;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Notification.Style getStyle() {
            return style;
        }

        public Builder setStyle(Notification.Style style) {
            this.style = style;
            return this;
        }

        public NotificationCompat.Action[] getAction() {
            return action;
        }

        public Builder setAction(NotificationCompat.Action... action) {
            this.action = action;
            return this;
        }

        public boolean isSound() {
            return sound;
        }

        public Builder setSound(boolean sound) {
            this.sound = sound;
            return this;
        }

        public boolean isLight() {
            return light;
        }

        public Builder setLight(boolean light) {
            this.light = light;
            return this;
        }

        public boolean isVibrate() {
            return vibrate;
        }

        public Builder setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
            return this;
        }


        public Builder setProgress(int max, int progress, boolean indeterminate) {
            this.progressMax = max;
            this.progress = progress;
            this.progressIndeterminate = indeterminate;
            return this;
        }


        public Builder setRemoteViews(RemoteViews remoteViews) {
            this.remoteViews = remoteViews;
            return this;
        }
    }


}
