package com.boniu.persontask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.boniu.persontask.bean.MethodBean;
import com.boniu.persontask.bean.TaskInfoBean;
import com.boniu.persontask.bean.YaoqingTxtBean;
import com.boniu.persontask.dialog.FuzhiDialog;
import com.boniu.persontask.utils.AESUtil;
import com.boniu.persontask.utils.ApiHelper;
import com.boniu.persontask.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

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


public class SignWebViewActivity extends AppCompatActivity {

    private WebView webView;

    private String url;
    private ImageView imgxx, imgback;
    private TextView tvTitle;
    public static final String OLD_URL = "OLD_URL";
    public static final String COMMON_PARAM = "COMMON_PARAM";
    private String oldurl = "";
    private String commonParam;

    private Gson gson = new Gson();
    private JsonObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_web_voew);

        webView = findViewById(R.id.webView);
        imgxx = findViewById(R.id.img_xx);
        imgback = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_title);
        oldurl = getIntent().getStringExtra(OLD_URL);
        commonParam = getIntent().getStringExtra(COMMON_PARAM);

        if (oldurl.contains("{params}")) {
            url = oldurl.replace("{params}", commonParam);
        }else{
            url = oldurl;
        }
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setBlockNetworkImage(false);
        webView.setWebViewClient(new WebViewClient() {
            //h5调用支付
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);

            }
        });
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebMethod(), "android");
        webView.loadUrl(url);
        if (!webView.canGoBack()) {
            imgxx.setVisibility(View.GONE);
        } else {
            imgxx.setVisibility(View.VISIBLE);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!webView.canGoBack()) {
                    imgxx.setVisibility(View.GONE);
                    finish();
                } else {
                    webView.goBack();
                    imgxx.setVisibility(View.VISIBLE);
                }
            }
        });
        imgxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTaskList();

    }

    //本地任务列表
    private void setTaskList() {
        jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("YIJIANHUANTOU");
        jsonArray.add("CHOUHUATUPIAN");
        jsonArray.add("MOBAN");
        jsonArray.add("RENLIANCESHI");
        jsonArray.add("DONGXIAOBIAOQING");
        jsonArray.add("SHOUCANG");
        jsonArray.add("SIGN");
        jsonArray.add("INVITE");
        jsonArray.add("KANXIAOSHUO");
        jsonArray.add("KANZIXUN");
        jsonArray.add("KANLINGSHENG");
        jsonArray.add(jsonArray);

    }

    private class WebMethod {

        /**
         * 关闭webview
         */
        @JavascriptInterface
        public void closeWebView() {
            finish();
        }

        /**
         * 观看激励视频
         * @param readyData
         */
        @JavascriptInterface
        public void watchVideoByAd(String readyData) {

            Toast.makeText(SignWebViewActivity.this, "观看激励视频", Toast.LENGTH_SHORT).show();
        }

        /**
         * 登陆失效
         * @param readyData
         */
        @JavascriptInterface
        public void h5ResCode(String readyData) {
            if (TextUtils.isEmpty(readyData)) return;
            if (readyData.equals("9990") || readyData.equals("9991")) {
                getMainToken();
            }
        }

        /**
         * 跳转其他h5页面
         * @param url
         */
        @JavascriptInterface
        public void startNewActivity(String url){
            Intent intent = new Intent(SignWebViewActivity.this, SignWebViewActivity.class);
            intent.putExtra(SignWebViewActivity.OLD_URL,url);
            intent.putExtra(SignWebViewActivity.COMMON_PARAM,commonParam+"");
            startActivity(intent);
        }


        //进行任务
        @JavascriptInterface
        public void acceptH5TaskParams(String bean) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TaskInfoBean taskInfoBean = gson.fromJson(bean, TaskInfoBean.class);

                    SPUtils.getInstance(SignWebViewActivity.this).put(ApiHelper.TASK_ID, taskInfoBean.getMissionId() + "");
                    SPUtils.getInstance(SignWebViewActivity.this).put(ApiHelper.TASK_TYPE, taskInfoBean.getJumpType() + "");

                    switch (taskInfoBean.getJumpType() + "") {
                        case "QIANDAO":
                            Intent intent2 = new Intent(SignWebViewActivity.this, SignWebViewActivity.class);
                            intent2.putExtra(SignWebViewActivity.OLD_URL, taskInfoBean.getJumpUrl() + "");
                            intent2.putExtra(SignWebViewActivity.COMMON_PARAM, commonParam + "");
                            startActivity(intent2);
                            break;
                        case "YAOQINGHAOYOU":
                            yaoqing();
                            break;
                        case "KANXIAOSHUO":
                            OtherSdkWebViewActivity.actionStart(SignWebViewActivity.this,taskInfoBean.getJumpUrl());
                            break;
                        case "KANZIXUN":
                            OtherSdkWebViewActivity.actionStart(SignWebViewActivity.this,taskInfoBean.getJumpUrl());
                            break;
                        case "KANLINGSHENG":
                            OtherSdkWebViewActivity.actionStart(SignWebViewActivity.this,taskInfoBean.getJumpUrl());
                            break;

                    }

                }
            });


        }

        /**
         * accoundid为空。重新登陆
         * @param money
         */
        @JavascriptInterface
        public void newUserLogin(String money) {
            //1。先判断是否登陆

            //2。是否绑定手机号

            //3。刷新h5
//            if (是否登陆) {
//            } else {
//                //未绑定手机号
//                if (是否绑定手机号) {
//                    Intent intent = new Intent(SignWebViewActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    return;
//                } else {
//                    if (TextUtils.isEmpty(SPUtils.getInstance(SignWebViewActivity.this).getString(ApiHelper.ACCOUNT_ID) + "")) {
//                        getMainToken();
//                    } else {
//                        commonParam = "?aid=" + SPUtils.getInstance(SignWebViewActivity.this).getString(ApiHelper.ACCOUNT_ID) + "&appName=" + "PTUDASHEN_ANDROID_BONIU";
//                        if (oldurl.contains("{params}")) {
//                            url = oldurl.replace("{params}", commonParam);
//                        }
//                        webView.loadUrl(url);
//                    }
//                }
//            }
        }


        //获取本地任务列表
        @JavascriptInterface
        public void getJumpTypeList(String method){
            if (jsonObject != null){
                MethodBean methodBean = gson.fromJson(method, MethodBean.class);
                httpCallback(methodBean.getMethod(),jsonObject.toString());
            }
        }


    }

    //统一网页回调方法
    private void httpCallback(final String methodName, final String jsonStr){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String js = "javascript:" + methodName + "('" + jsonStr + "')";
                webView.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }
        });
    }

    private void getMainToken() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Map<String, String> map = new HashMap<>();
        map.put("appName", ApiHelper.APP_NAME);
        map.put("deviceType", "ANDROID");
        map.put("version", "1.0.0");
        map.put("deviceModel", Build.MODEL);
        map.put("brand", Build.BRAND);
        map.put("mobile", "18395996666");
        map.put("uuid", "uuid");//唯一标示
        String requestJson = new Gson().toJson(map);
        Log.e("myFragmentgetMainToken", requestJson);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), requestJson);
        Request request = new Request.Builder()
                .url(ApiHelper.COMMEN_GET_MAIN_TOKEN)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("myFragmentgetMainToken", "getMainToken->onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonbean = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("myFragment", "getMainToken->isNoSuccess" + jsonbean);
                            JSONObject object = new JSONObject(jsonbean);
                            JSONObject result = object.getJSONObject("result");
                            String accountId = result.getString("accountId");
                            SPUtils.getInstance(SignWebViewActivity.this).put(ApiHelper.ACCOUNT_ID, accountId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    //邀请好友
    private void yaoqing(){
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
        map1.put("accountId", SPUtils.getInstance(this).getString(ApiHelper.ACCOUNT_ID)  + "");
        map1.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        String requestJson = new Gson().toJson(map1);
        String newRequestJson = AESUtil.encrypt(requestJson, ApiHelper.REQUEST_KEY);
        Map<String, String> map2 = new HashMap<>();
        map2.put("vs", newRequestJson);
        String vsJson = new Gson().toJson(map2);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), vsJson);
        Request request = new Request.Builder()
                .url(ApiHelper.INVITE_FRIEND)
                .post(requestBody)
                .addHeader("n", ApiHelper.APP_NAME)
                .addHeader("va", newHeaderJson)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonbean = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        YaoqingTxtBean yaoqingTxtBean = gson.fromJson(jsonbean, YaoqingTxtBean.class);
                        if (yaoqingTxtBean.isSuccess()){
                            FuzhiDialog fuzhiDialog = new FuzhiDialog(SignWebViewActivity.this,yaoqingTxtBean.getResult()+"");
                            fuzhiDialog.show();
                        }

                    }
                });

            }
        });
    }

}
