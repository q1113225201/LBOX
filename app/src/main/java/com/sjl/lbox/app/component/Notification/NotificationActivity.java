package com.sjl.lbox.app.component.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

/**
 * 多种通知样式
 * 配置文件
 * <service
 * android:name=".app.component.Notification.service.ListenerNotificationService"
 * android:label="@string/app_name"
 * android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
 * <intent-filter>
 * <action android:name="android.service.notification.NotificationListenerService" />
 * </intent-filter>
 * </service>
 *
 * @author SJL
 * @date 2016/8/29 23:03
 */
public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    private static final int NORMAL = 0;
    private Button btnNotificationNormal;
    private static final int PROGRESS = 1;
    private Button btnNotificationProgress;
    private static final int BIG_TEXT = 2;
    private Button btnNotificationBigText;
    private static final int IN_BOX = 3;
    private Button btnNotificationInBox;
    private static final int BIG_PICTURE = 4;
    private Button btnNotificationBigPicture;
    private static final int BANNER = 5;
    private Button btnNotificationBanner;
    private static final int MEDIA = 6;
    private Button btnNotificationMedia;

    private Button btnNotificationRemove;

    private Button btnAuthorizationUsage;
    private TextView tv;

    private NotificationManager manager;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == PROGRESS) {
                if (msg.arg1 < 100) {
                    progressNotification(msg.arg1 + 5);
                }
            }
        }
    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("text");
            tv.setText(TextUtils.isEmpty(str) ? "null" : str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
    }

    private void initView() {
        btnNotificationNormal = (Button) findViewById(R.id.btnNotificationNormal);
        btnNotificationNormal.setOnClickListener(this);
        btnNotificationProgress = (Button) findViewById(R.id.btnNotificationProgress);
        btnNotificationProgress.setOnClickListener(this);
        btnNotificationBigText = (Button) findViewById(R.id.btnNotificationBigText);
        btnNotificationBigText.setOnClickListener(this);
        btnNotificationRemove = (Button) findViewById(R.id.btnNotificationRemove);
        btnNotificationRemove.setOnClickListener(this);
        btnNotificationInBox = (Button) findViewById(R.id.btnNotificationInBox);
        btnNotificationInBox.setOnClickListener(this);
        btnNotificationBigPicture = (Button) findViewById(R.id.btnNotificationBigPicture);
        btnNotificationBigPicture.setOnClickListener(this);
        btnNotificationBanner = (Button) findViewById(R.id.btnNotificationBanner);
        btnNotificationBanner.setOnClickListener(this);
        btnNotificationMedia = (Button) findViewById(R.id.btnNotificationMedia);
        btnNotificationMedia.setOnClickListener(this);
        btnAuthorizationUsage = (Button) findViewById(R.id.btnAuthorizationUsage);
        btnAuthorizationUsage.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName());
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNotificationServiceEnable();
    }

    private void isNotificationServiceEnable() {
        if (NotificationManagerCompat.getEnabledListenerPackages(mContext).contains(getPackageName())) {
            btnAuthorizationUsage.setText("用户授权 已开启");
        } else {
            btnAuthorizationUsage.setText("用户授权 已关闭");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNotificationNormal:
                normalNotification();
                break;
            case R.id.btnNotificationProgress:
                progressNotification(0);
                break;
            case R.id.btnNotificationBigText:
                bigTextNotification();
                break;
            case R.id.btnNotificationInBox:
                inBoxNotification();
                break;
            case R.id.btnNotificationBigPicture:
                bigPictureNotification();
                break;
            case R.id.btnNotificationBanner:
                bannerNotification();
                break;
            case R.id.btnNotificationMedia:
                mediaNotification();
                break;
            case R.id.btnNotificationRemove:
                removeNotification();
                break;
            case R.id.btnAuthorizationUsage:
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                break;
        }
    }

    /**
     * 音乐播放器通知
     */
    private void mediaNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("MediaStyle");
        builder.setContentText("Song Title");
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent intent = new Intent(this, NotificationClickPictureActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.addAction(R.drawable.iv_previous_white, "", null);
        builder.addAction(R.drawable.iv_stop_white, "", null);
        builder.addAction(R.drawable.iv_play_white, "", pIntent);
        builder.addAction(R.drawable.iv_next_white, "", null);
        NotificationCompat.MediaStyle style = new NotificationCompat.MediaStyle();
        style.setMediaSession(new MediaSessionCompat(this, "MediaSession",
                new ComponentName(mContext, Intent.ACTION_MEDIA_BUTTON), null).getSessionToken());
        //CancelButton在5.0以下的机器有效
        style.setCancelButtonIntent(pIntent);
        style.setShowCancelButton(true);
        //设置要显示在通知右方的图标 最多三个
        style.setShowActionsInCompactView(2, 3);
        builder.setStyle(style);
        builder.setShowWhen(false);
        Notification notification = builder.build();
        manager.notify(MEDIA, notification);
    }

    /**
     * 清除所有通知
     */
    private void removeNotification() {
//        manager.cancel(PROGRESS);
        manager.cancelAll();
    }

    /**
     * 横幅通知
     */
    private void bannerNotification() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ToastUtil.showToast(mContext, "此类通知在Android 5.0以上版本才会有横幅有效！");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("横幅通知");
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        Intent intent = new Intent(this, NotificationClickPictureActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.setFullScreenIntent(pIntent, true);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        manager.notify(BANNER, notification);
    }

    /**
     * 大图片通知
     */
    private void bigPictureNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("BigPictureStyle");
        builder.setContentText("BigPicture演示示例");
        builder.setSmallIcon(R.drawable.logo);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        android.support.v4.app.NotificationCompat.BigPictureStyle style = new android.support.v4.app.NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("BigContentTitle");
        style.setSummaryText("SummaryText");
        style.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        builder.setStyle(style);
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, NotificationClickPictureActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        Notification notification = builder.build();
        manager.notify(BIG_PICTURE, notification);
    }

    /**
     * 最多显示五行 再多会有截断
     */
    private void inBoxNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("InboxStyle");
        builder.setContentText("InboxStyle演示示例");
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        android.support.v4.app.NotificationCompat.InboxStyle style = new android.support.v4.app.NotificationCompat.InboxStyle();
        style.setBigContentTitle("BigContentTitle")
                .addLine("第一行，第一行，第一行，第一行，第一行，第一行，第一行")
                .addLine("第二行")
                .addLine("第三行")
                .addLine("第四行")
                .addLine("第五行")
                .setSummaryText("SummaryText");
        builder.setStyle(style);
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, NotificationClickActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(IN_BOX, notification);
    }

    /**
     * 文本内容较多的通知
     */
    private void bigTextNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("BigTextStyle");
        builder.setContentText("BigTextStyle演示示例");
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("这里是点击通知后要显示的正文，可以换行可以显示很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长");
        style.setBigContentTitle("点击后的标题");
        style.setSummaryText("末尾只一行的文字内容");
        builder.setStyle(style);
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, NotificationClickActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(BIG_TEXT, notification);
    }

    /**
     * 进度条通知
     */
    private void progressNotification(int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        builder.setShowWhen(false);
        if (progress == 100) {
            builder.setContentTitle("下载完成");
            //禁止用户点击删除按钮删除
            builder.setAutoCancel(true);
            //禁止滑动删除
            builder.setOngoing(false);
        } else {
            builder.setContentTitle("下载中..." + progress + "%");
            //禁止用户点击删除按钮删除
            builder.setAutoCancel(false);
            //禁止滑动删除
            builder.setOngoing(true);
        }
        builder.setProgress(100, progress, false);
        //builder.setContentInfo(progress+"%");
        builder.setOngoing(true);
        builder.setShowWhen(false);
        Notification notification = builder.build();
        manager.notify(PROGRESS, notification);

        Message msg = new Message();
        msg.what = PROGRESS;
        msg.arg1 = progress;
        handler.sendMessageDelayed(msg, 500);
    }

    /**
     * 普通通知
     */
    private void normalNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker("普通通知");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题");
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        builder.setContentInfo("2");
        builder.setAutoCancel(true);
        builder.setNumber(2);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo));
        Intent intent = new Intent(mContext, NotificationClickActivity.class);
        //PendingIntent.getBroadcast(mContext,1,intent,0);
        //PendingIntent.getService(mContext,1,intent,0);
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 1, intent, 0);
        builder.setContentIntent(pIntent);
        //设置震动
        long[] vibrate = {100, 200, 100, 200};
        builder.setVibrate(vibrate);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(NORMAL, notification);
    }
}
