package com.boniu.persontask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.boniu.persontask.utils.ApiHelper;
import com.boniu.persontask.utils.SPUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_task)
    TextView tvTask;
    public static final String APP_NAME= "PTUDASHEN_ANDROID_BONIU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tvPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PersonActivity.class));
            }
        });
        tvTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commonParam = "?aid=" + SPUtils.getInstance(MainActivity.this).getString(ApiHelper.ACCOUNT_ID) + "&appName=" + APP_NAME;

                Intent intent = new Intent(MainActivity.this, SignWebViewActivity.class);
                intent.putExtra(SignWebViewActivity.OLD_URL, "http://test99.jukmall.cn/html/personal/mission/index.html{params}");
                intent.putExtra(SignWebViewActivity.COMMON_PARAM, commonParam + "");
                startActivity(intent);
            }
        });



    }



}
