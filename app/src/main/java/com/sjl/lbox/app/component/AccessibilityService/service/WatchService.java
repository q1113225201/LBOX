package com.sjl.lbox.app.component.AccessibilityService.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.sjl.lbox.app.component.AccessibilityService.FloatWindow.WatchWindowManager;

/**
 * WatchService
 *
 * @author SJL
 * @date 2017/7/20
 */

public class WatchService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(event.getEventType()==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            WatchWindowManager.getInstance(this).show(event.getPackageName()+"\n"+event.getClassName());
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
