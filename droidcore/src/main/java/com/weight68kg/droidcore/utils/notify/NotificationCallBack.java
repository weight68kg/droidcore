package com.weight68kg.droidcore.utils.notify;

import android.app.Notification;

public interface NotificationCallBack {


    //单行
    Notification getSingleLine(NotificationUtils.Builder builder);

    //多行
    Notification getNormalMoreLine(NotificationUtils.Builder builder);

    //消息列表
    Notification getMessageList(NotificationUtils.Builder builder);

    //大图
    Notification getBigIcon(NotificationUtils.Builder builder);

    //悬浮
    Notification getHeadsUp(NotificationUtils.Builder builder);

    //进度条
    Notification getProgress(NotificationUtils.Builder builder);

    //带按钮的
    Notification getButton(NotificationUtils.Builder builder);
    //自定义
    Notification getCustomView(NotificationUtils.Builder builder);
}
