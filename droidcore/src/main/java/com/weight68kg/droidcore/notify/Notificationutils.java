package com.weight68kg.droidcore.notify;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.core.app.NotificationCompat;

public class Notificationutils extends ContextWrapper {


    private NotifycationOb notifycationOb;

    public Notificationutils(Context context) {
        super(context);
        //判断是否开启通知权限
        if (!Notificationutils.isNotificationEnabled(context)) {
            Notificationutils.goToSet((Activity) context);
            return;
        }
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifycationOb = new Notification26Utils(context);
        } else {
            notifycationOb = new Notification25Utils(context);
        }
    }


    /**
     * 获取 {@link android.app.Notification}
     *
     * @param title
     * @param content
     * @return
     */
    protected Notification getNotification(String title, String content) {
        return notifycationOb.getNotification(title, content);
    }

    public void sendNotification(String title, String content, PendingIntent pendingIntent, int notifyId) {
        Notification notification = getNotification(title, content);
        ((NotifycationBase) notifycationOb).getManager().notify(notifyId, notification);
    }

    /**
     * 普通单行通知
     */
    public void normalSingleLine(String title, String content, int notifyId) {
        normalSingleLine(title, content, null, notifyId);
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
}
