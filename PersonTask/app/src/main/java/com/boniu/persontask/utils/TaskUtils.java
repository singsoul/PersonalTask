package com.boniu.persontask.utils;


import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.boniu.persontask.bean.FinishTaskBean;
import com.boniu.persontask.dialog.FinishTaskDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//任务中心工具类
public class TaskUtils {
    public static final String TAG = "TaskUtils";
    public final static String[] taskList = {
            "YIJIANHUANTOU",
            "CHOUHUATUPIAN",
            "MOBAN",
            "RENLIANCESHI",
            "DONGXIAOBIAOQING"
    };

    public final static String[] h5List = {
            "KANXIAOSHUO",
            "KANZIXUN",
            "KANLINGSHENG"
    };
    public static boolean isZhitu(String type, String accountid){
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(accountid)){
            return false;
        }
        for (int i = 0; i < taskList.length; i++) {
            if (taskList[i].equals(type)){
                return true;
            }
        }
        return false;
    }
    public static boolean isH5(String type, String accountid){
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(accountid)){
            return false;
        }
        for (int i = 0; i < h5List.length; i++) {
            if (h5List[i].equals(type)){
                return true;
            }
        }
        return false;
    }

    public static void finishTask(Activity activity, String taskid, String accountId){
        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //设备信息参数
        Map<String, String> map = new HashMap<>();
        map.put("deviceType", "ANDROID");
        map.put("version", "1.0.0");
        map.put("deviceModel", android.os.Build.MODEL);
        map.put("brand", android.os.Build.BRAND);
        map.put("uuid", "uuid");
        map.put("channel", "mkbd");
        map.put("appName", ApiHelper.APP_NAME);
        String headerJson = new Gson().toJson(map);
        String newHeaderJson = AESUtil.encrypt(headerJson, ApiHelper.HEADER_KEY);
        //请求参数
        Map<String, String> map1 = new HashMap<>();
        map1.put("accountId", accountId + "");
        map1.put("missionId",taskid+"");
        map1.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        String requestJson = new Gson().toJson(map1);
        String newRequestJson = AESUtil.encrypt(requestJson, ApiHelper.REQUEST_KEY);
        Map<String, String> map2 = new HashMap<>();
        map2.put("vs", newRequestJson);
        String vsJson = new Gson().toJson(map2);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), vsJson);
        Request request = new Request.Builder()
                .url(ApiHelper.FINISH_TAKS)
                .post(requestBody)
                .addHeader("n", ApiHelper.APP_NAME)
                .addHeader("va", newHeaderJson)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "asdgetPersonalInfo->onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonbean = response.body().string();
                Log.e(TAG, "asdgetPersonalInfo->" + jsonbean);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        FinishTaskBean finishTaskBean = gson.fromJson(jsonbean, FinishTaskBean.class);
                        if (finishTaskBean.isSuccess()){
                            FinishTaskDialog finishTaskDialog =  new FinishTaskDialog(activity,finishTaskBean.getResult().getAwardScore()+"");
                            finishTaskDialog.show();
                            SPUtils.getInstance(activity).put(ApiHelper.TASK_ID, "");
                            SPUtils.getInstance(activity).put(ApiHelper.TASK_TYPE, "");
                        }
                    }
                });

            }
        });
    }


}
