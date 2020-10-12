package com.boniu.persontask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainToken();
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
                String commonParam = "?aid=" + SPUtils.getInstance(MainActivity.this).getString(ApiHelper.ACCOUNT_ID) + "&appName=" + "PTUDASHEN_ANDROID_BONIU";

                Intent intent = new Intent(MainActivity.this, SignWebViewActivity.class);
                intent.putExtra(SignWebViewActivity.OLD_URL, "");
                intent.putExtra(SignWebViewActivity.COMMON_PARAM, commonParam + "");
                startActivity(intent);
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
                            SPUtils.getInstance(MainActivity.this).put(ApiHelper.ACCOUNT_ID,accountId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }
}
