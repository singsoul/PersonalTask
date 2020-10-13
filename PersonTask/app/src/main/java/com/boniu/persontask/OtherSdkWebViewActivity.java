package com.boniu.persontask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.boniu.persontask.utils.ApiHelper;
import com.boniu.persontask.utils.SPUtils;
import com.boniu.persontask.utils.TaskUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherSdkWebViewActivity extends AppCompatActivity {
    public static final String TAG = "OtherSdkWebViewActivity";
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    FrameLayout llTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.webview)
    WebView webview;

    private int time = 0;
    private boolean isPause = false;//是否暂停
    public static final String TASK_URL = "TASK_URL";
    private String url;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, OtherSdkWebViewActivity.class);
        intent.putExtra(TASK_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_sdk_web_view);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra(TASK_URL);
        Log.e(TAG, "onCreate: " + url);
        initWebView();

        timeHandler.sendEmptyMessage(0);
        pauseHandler.sendEmptyMessageDelayed(0, 5000);
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "onTouch: " + MotionEvent.ACTION_DOWN);
                        pauseHandler.removeMessages(0);
                        isPause = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "onTouch: " + MotionEvent.ACTION_MOVE);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "onTouch: " + MotionEvent.ACTION_UP);
                        pauseHandler.sendEmptyMessageDelayed(0, 5000);
                        break;
                }
                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView() {
        WebSettings settings = webview.getSettings();
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvTitle.setText(title);
            }
        };
        // 设置setWebChromeClient对象
        webview.setWebChromeClient(wvcc);
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.loadUrl(url);
    }


    private Handler pauseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isPause = true;
        }
    };


    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isPause) {
                time++;
                if (time > 10) {
                    String taskType = SPUtils.getInstance(OtherSdkWebViewActivity.this).getString(ApiHelper.TASK_TYPE);
                    String accountId = SPUtils.getInstance(OtherSdkWebViewActivity.this).getString(ApiHelper.ACCOUNT_ID);
                    Log.e("asd", "handleMessage: " + taskType + "::" + accountId);
                    if (TaskUtils.isH5(taskType, accountId)) {
                        String taskid = SPUtils.getInstance(OtherSdkWebViewActivity.this).getString(ApiHelper.TASK_ID);
                        TaskUtils.finishTask(OtherSdkWebViewActivity.this, taskid, accountId);
                        llTime.setVisibility(View.GONE);
                    }
                }
                int a = 300 - time;
                if (a > 0) {
                    String b = (a / 60) > 10 ? (a / 60) + "" : "0" + a / 60;
                    String c = (a % 60) > 10 ? (a % 60) + "" : "0" + a % 60;
                    tvTime.setText(b + ":" + c);
                } else {
                    tvTime.setText("00:00");
                }

            }
            timeHandler.sendEmptyMessageDelayed(0, 1000);

        }
    };

}
