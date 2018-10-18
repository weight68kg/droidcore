package com.weight68kg.droidcore.utils.notify;

import android.app.Notification;

public interface NotifycationOb {


    void converBuilder(NotificationUtils.Builder build);

    //单行
    Notification getNotificationInstance(NotificationUtils.Builder builder);

    //多行
    Notification getNormalMoreLine(NotificationUtils.Builder builder);

    //消息列表
    Notification getMessageList(NotificationUtils.Builder builder);

    //大图
    Notification getBigIcon(NotificationUtils.Builder builder);
}
