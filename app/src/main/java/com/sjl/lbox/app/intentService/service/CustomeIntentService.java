package com.sjl.lbox.app.intentService.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


/**
 * 自定义IntentService
 *
 * @author SJL
 * @date 2016/12/14 22:39
 */
public class CustomeIntentService extends IntentService {

    public static final String ACTION_RESULE = "com.sjl.result";
    public static final String RESULT = "result";
    public static final String TYPE = "type";
    public static final String UPLOAD = "upload";
    public static final String DOWNLOAD = "download";

    private LocalBroadcastManager localBroadcastManager;

    public CustomeIntentService() {
        super("CustomeIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Intent intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "service onCreate");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String type = intent.getStringExtra(TYPE);
            if (UPLOAD.equalsIgnoreCase(type)) {
                upload();
            } else if (DOWNLOAD.equalsIgnoreCase(type)) {
                download();
            }
        }
    }

    private void download() {
        int progress = 0;
        Intent intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "download start-------");
        localBroadcastManager.sendBroadcast(intent);
        while (progress < 100) {
            intent = new Intent(ACTION_RESULE);
            intent.putExtra(RESULT, String.format("download...%d%%", progress));
            localBroadcastManager.sendBroadcast(intent);
            progress += 20;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "download end-------");
        localBroadcastManager.sendBroadcast(intent);
    }

    private void upload() {
        int progress = 0;
        Intent intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "upload start-------");
        localBroadcastManager.sendBroadcast(intent);
        while (progress < 100) {
            intent = new Intent(ACTION_RESULE);
            intent.putExtra(RESULT, String.format("upload...%d%%", progress));
            localBroadcastManager.sendBroadcast(intent);
            progress += 20;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "upload end-------");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(ACTION_RESULE);
        intent.putExtra(RESULT, "service onDestroy");
        localBroadcastManager.sendBroadcast(intent);
        super.onDestroy();
    }
}
