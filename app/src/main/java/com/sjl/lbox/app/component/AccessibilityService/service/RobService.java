package com.sjl.lbox.app.component.AccessibilityService.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * RobService
 *
 * @author SJL
 * @date 2017/1/18
 */

public class RobService extends AccessibilityService {
    //开红包Id
    public static String OPEN_PACKET_ID = "com.tencent.mm:id/be_";
    //开红包Name
    public static String OPEN_PACKET_NAME = "開";
    //退出红包Id
    public static String EXIT_PACKET_ID = "com.tencent.mm:id/gr";

    private List<AccessibilityNodeInfo> parents;
    private List<AccessibilityNodeInfo> lastParents;

    /**
     * 服务启动时调用
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        parents = new ArrayList<>();
        lastParents = new ArrayList<>();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String className;
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains("红包")) {
                            //模拟打开通知栏消息
                            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                className = event.getClassName().toString();
                if ("android.widget.ListView".equals(className)) {
                    //点击红包
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickPacket();
                        }
                    }, 200);
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                className = event.getClassName().toString();
                if ("com.tencent.mm.ui.LauncherUI".equals(className)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //点击红包
                            clickPacket();
                        }
                    }, 200);
                } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(className)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //开红包
                            openPacket();
                        }
                    }, 200);
                } else if ("com.tencent.mm.ui.base.p".equals(className) || "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(className)) {
                    //退出红包
                    exitPacket();
                }
                break;
        }
    }

    /**
     * 退出红包
     */
    private void exitPacket() {
        AccessibilityNodeInfo info = getRootInActiveWindow();
        if (info != null) {
            List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId(EXIT_PACKET_ID);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 打开红包
     */
    private void openPacket() {
        AccessibilityNodeInfo info = getRootInActiveWindow();
        if (info != null) {
            List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByViewId(OPEN_PACKET_ID);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            list = info.findAccessibilityNodeInfosByViewId(OPEN_PACKET_NAME);
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 点击最后一个红包
     */
    private void clickPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
        if (parents.size() > 0&&lastParents.size()!=parents.size()) {
            parents.get(parents.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            lastParents = parents;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                parents = new ArrayList<AccessibilityNodeInfo>();
            }
        },2000);
    }

    private void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
                if ("领取红包".equals(info.getText().toString())) {
                    if (info.isCheckable()) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    AccessibilityNodeInfo parent = info.getParent();
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parents.add(parent);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
