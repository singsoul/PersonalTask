package com.boniu.persontask.utils;


import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * 应用判断处理
 */
public class AppPackageUtils {

    public static final String TAG  = "AppPackageUtils";

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    public static boolean checkPackInfo(Context context, String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 启动app
     * @param context
     * @param packname
     * 启动app时记录时间，根据long值差来判断时间
     */
    public static void startApp(Context context, String packname, String schemeStr){
        if (TextUtils.isEmpty(schemeStr)){
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packname);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }else{
            boolean b = schemeValid(context, schemeStr);
            Log.e(TAG, "startApp: " +b );
            if (b){
                String url = schemeStr;
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                context.startActivity(intent);
            }else{
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(packname);
                if (intent != null) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        }




    }




    /**
     * 暂时不用
     *   通过判断手机里的所有进程是否有这个App的进程
     * //从而判断该App是否有打开
     *
     * time 与系统时间的间隔来判断应用是否开启
     * @param context
     * @param packageName
     * @return
     */

    public static boolean shouldInit(Context context, String packageName) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager m=(UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis()-10*1000;
            List<UsageStats> list=m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
            for (int i = 0; i < list.size(); i++) {
                UsageStats usageStats = list.get(i);
                String packageName1 = usageStats.getPackageName();
                if (packageName1.equals(packageName)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static void isCallable(String packname, Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();

        Log.e(TAG, "isCallable: " + processInfos.toString() );
    }


    //判断是否前台后台应用
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.d("asd", "后台"+ appProcess.processName);
                    return true;
                } else {
                    Log.d("asd", "前台"+ appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean schemeValid(Context context, String schemeStr) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(schemeStr));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }


    public static void setClipboard(Context context, String content){
//获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

}
