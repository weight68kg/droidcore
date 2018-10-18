package com.weight68kg.droidcore.notify;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

public class NotifycationBase extends ContextWrapper {

    public final String name = "channel_name_1";
    public final String id = "channel_1";
    protected NotificationManager manager;

    public NotifycationBase(Context base) {
        super(base);
    }


    protected NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
}
