package com.sjl.lbox.util;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * App常用工具
 *
 * @author SJL
 * @date 2016/11/21 21:33
 */
public class AppUtil {
    /**
     * 获取所有桌面应用
     * @param context
     * @return
     */
    public static List<String> getHomeList(Context context){
        List<String> list = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo:resolveInfoList){
            list.add(resolveInfo.activityInfo.packageName);
        }
        return list;
    }

    /**
     * 当前界面是否是桌面
     * 分5.0之前和之后版本
     * @param context
     * @return
     */
    public static boolean isHome(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packname = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0之后getRunningTasks废弃，引入usage
            //<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions" />
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long currentTime = System.currentTimeMillis();
            List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,currentTime-2000,currentTime);
            if(usageStatsList!=null||usageStatsList.isEmpty()){
                SortedMap<Long,UsageStats> usageStatsSortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats:usageStatsList){
                    usageStatsSortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                }
                if(!usageStatsSortedMap.isEmpty()){
                    packname = usageStatsSortedMap.get(usageStatsSortedMap.lastKey()).getPackageName();
                }
            }
        }else{
            //5.0之前可直接使用getRunningTasks
            //<uses-permission android:name="android.permission.GET_TASKS"/>
            List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(1);
            packname = runningTaskInfoList.get(0).topActivity.getPackageName();
        }
        return getHomeList(context).contains(packname);
    }

    /**
     * 获取屏幕宽高
     * @param context
     * @return
     */
    public static Point getScreen(Context context){
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }
}
