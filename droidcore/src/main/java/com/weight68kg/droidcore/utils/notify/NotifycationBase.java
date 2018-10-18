package com.weight68kg.droidcore.utils.notify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

public class NotifycationBase extends ContextWrapper {

    public final String name = "channel_name_1";
    public final String id = "channel_1";
    protected NotificationManager manager;
    private NotificationUtils.Builder builder;

    public NotifycationBase(Context base) {
        super(base);
    }


    protected NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationUtils.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(NotificationUtils.Builder builder) {
        this.builder = builder;
    }
}
