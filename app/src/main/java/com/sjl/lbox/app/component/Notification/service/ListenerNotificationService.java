package com.sjl.lbox.app.component.Notification.service;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

public class ListenerNotificationService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);
        String pkg = sbn.getPackageName();
        CharSequence tickerText = sbn.getNotification().tickerText;
        sendBroadcast(String.format("pkg:%s\ntickerText:%s",pkg, TextUtils.isEmpty(tickerText)?"null":tickerText));
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
//        super.onNotificationRemoved(sbn);
        String pkg = sbn.getPackageName();
        CharSequence tickerText = sbn.getNotification().tickerText;
        sendBroadcast(String.format("pkg:%s\ntickerText:%s",pkg, TextUtils.isEmpty(tickerText)?"null":tickerText));
    }
    private void sendBroadcast(String msg){
        Intent intent = new Intent(getPackageName());
        intent.putExtra("text",msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
