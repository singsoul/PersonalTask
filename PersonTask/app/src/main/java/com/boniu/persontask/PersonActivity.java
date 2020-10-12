package com.boniu.persontask;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.boniu.persontask.adapter.MoreServerAdapter;
import com.boniu.persontask.adapter.StationAdAdapter;
import com.boniu.persontask.adapter.ViewPagerAdapter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

public class PersonActivity extends AppCompatActivity {

    @BindView(R.id.rl_ad)
    FrameLayout rlAd;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    @BindView(R.id.tvTodayScore)
    TextView tvTodayScore;
    @BindView(R.id.tvTotalScore)
    TextView tvTotalScore;
    @BindView(R.id.tvTransferUrl)
    TextView tvTransferUrl;
    @BindView(R.id.withdrawal)
    LinearLayout withdrawal;
    @BindView(R.id.missionUrl)
    RelativeLayout missionUrl;
    @BindView(R.id.llMoney)
    LinearLayout llMoney;
    @BindView(R.id.rlv_tools)
    RecyclerView rlvTools;
    @BindView(R.id.ll_station)
    LinearLayout llStation;
    @BindView(R.id.rlv_more_server)
    RecyclerView rlvMoreServer;
    @BindView(R.id.llMoreService)
    LinearLayout llMoreService;
    @BindView(R.id.fl_ad_container)
    FrameLayout flAdContainer;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private List<PersonalInfo.ResultBean.PersonalServiceVosBean> serviceVos = new ArrayList<>();
    private MoreServerAdapter moreServerAdapter;
    //配置服务类型
    private List<String> jumpUrls = new ArrayList<>();

    private String commonParam = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        jumpUrls.add("YILAN");
        jumpUrls.add("RINGTONE");
        jumpUrls.add("BXM_XJK");
        jumpUrls.add("COMMON");
        jumpUrls.add("COMMON_PERSON");
        commonParam = "?aid=" + SPUtils.getInstance(this).getString(ApiHelper.ACCOUNT_ID) + "&appName=" + "PTUDASHEN_ANDROID_BONIU";
        getPersonalInfo();
        moreServerAdapter = new MoreServerAdapter(serviceVos);
        rlvMoreServer.setLayoutManager(new GridLayoutManager(PersonActivity.this, 4));
        rlvMoreServer.setAdapter(moreServerAdapter);

    }


    private void getPersonalInfo() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //设备信息参数
        Map<String, String> map = new HashMap<>();
        map.put("deviceType", "ANDROID");
        map.put("version", "1.0.0");
        map.put("deviceModel", Build.MODEL);
        map.put("brand", Build.BRAND);
        map.put("uuid", "uuid");//唯一标示
        map.put("channel", "mkbd");//渠道
        map.put("appName", ApiHelper.APP_NAME);
        String headerJson = new Gson().toJson(map);
        String newHeaderJson = AESUtil.encrypt(headerJson, "dBkOEzSlFrj1it8p");
        Log.e("myFragment", headerJson);
        //请求参数
        Map<String, String> map1 = new HashMap<>();
        map1.put("accountId", SPUtils.getInstance(this).getString(ApiHelper.ACCOUNT_ID) + "");
        map1.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        String requestJson = new Gson().toJson(map1);
        String newRequestJson = AESUtil.encrypt(requestJson, "UkDfuBJZ3bQmjFhL");
        Map<String, String> map2 = new HashMap<>();
        map2.put("vs", newRequestJson);
        Log.e("myFragment", requestJson);
        String vsJson = new Gson().toJson(map2);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), vsJson);
        Request request = new Request.Builder()
                .url(ApiHelper.COMMEN_PERSONNAL)
                .post(requestBody)
                .addHeader("n", "PTUDASHEN_ANDROID_BONIU")
                .addHeader("va", newHeaderJson)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("myFragment", "getPersonalInfo->onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonbean = response.body().string();
                Log.e("myFragment", "getPersonalInfo->" + jsonbean);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final PersonalInfo personalInfo = new Gson().fromJson(jsonbean, PersonalInfo.class);
                            if (personalInfo.isSuccess()) {
                                //更多服务信息列表
                                List<PersonalInfo.ResultBean.PersonalServiceVosBean> serviceVoss = personalInfo.getResult().getPersonalServiceVos();
                                if (serviceVoss.size() > 0) {
                                    serviceVos.clear();
                                    serviceVos.addAll(serviceVoss);
                                    moreServerAdapter.notifyDataSetChanged();
                                    llMoreService.setVisibility(View.VISIBLE);
                                    for (PersonalInfo.ResultBean.PersonalServiceVosBean beans : serviceVoss) {
                                        if (!jumpUrls.contains(beans.getJumpType())) {
                                            serviceVoss.remove(beans);
                                            moreServerAdapter.notifyDataSetChanged();
                                        }
                                    }
                                } else {
                                    llMoreService.setVisibility(View.GONE);
                                }

                                //提现展示
                                final PersonalInfo.ResultBean.PersonalScoreInfoVoBean scoreInfovo = personalInfo.getResult().getPersonalScoreInfoVo();
                                if (null != scoreInfovo) {

                                    llMoney.setVisibility(View.VISIBLE);
                                    tvTodayScore.setText(scoreInfovo.getTodayScore() + "");
                                    tvTotalScore.setText(scoreInfovo.getTotalScore() + "");

                                    tvTransferUrl.setText(scoreInfovo.getCanWithDraw() + "");

                                    if (!TextUtils.isEmpty(personalInfo.getResult().getTransferUrl())) {
                                        withdrawal.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                gotoMainWebView(personalInfo.getResult().getTransferUrl(), "提现中心");
                                            }
                                        });
                                    }
                                    if (!TextUtils.isEmpty(scoreInfovo.getMissionUrl())) {

                                        missionUrl.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                gotoMainWebView(scoreInfovo.getMissionUrl(), "任务中心");
                                            }
                                        });
                                    } else {
                                        missionUrl.setVisibility(View.GONE);
                                    }
                                } else {
                                    llMoney.setVisibility(View.GONE);
                                }
                                //签到
                                if (!TextUtils.isEmpty(personalInfo.getResult().getSignUrl())) {
                                    ivSign.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            gotoMainWebView(personalInfo.getResult().getSignUrl(), "签到");
                                        }
                                    });
                                    ivSign.setVisibility(View.VISIBLE);
                                } else {
                                    ivSign.setVisibility(View.INVISIBLE);
                                }

                                //占位广告id
                                List<PersonalInfo.ResultBean.PersonalFreeVosBean> areaVos = personalInfo.getResult().getPersonalFreeVos();
                                if (areaVos != null && areaVos.size() > 0) {
                                    llStation.removeAllViews();
                                    for (final PersonalInfo.ResultBean.PersonalFreeVosBean bean : areaVos) {
                                        switch (bean.getViewType()) {
                                            case "LIST_VERTICAL":
                                                //站位图广告
                                                GridLayoutManager layoutManager = new GridLayoutManager(PersonActivity.this, 2);
                                                RecyclerView rlvStation = new RecyclerView(PersonActivity.this);
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(0, 16, 0, 0);
                                                rlvStation.setLayoutParams(lp);
                                                //设置布局管理器
                                                rlvStation.setLayoutManager(layoutManager);

                                                StationAdAdapter stationAdapter = new StationAdAdapter(bean.getPersonalAreaVos());
                                                rlvStation.setAdapter(stationAdapter);
                                                //设置为垂直布局，这也是默认的
                                                stationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                        String url = bean.getPersonalAreaVos().get(position).getJumpUrl();
                                                        switch (bean.getPersonalAreaVos().get(position).getJumpType()) {
                                                            case "COMMON_PERSON":
                                                            case "COMMON":
                                                                gotoMainWebView(url, bean.getPersonalAreaVos().get(position).getTitle());
                                                                break;
                                                            case "BXM_ZCM"://变现猫
                                                                break;
                                                            case "BXM_YCJ"://变现猫2
                                                                break;
                                                        }
                                                    }
                                                });
                                                llStation.addView(rlvStation);
//                                                break;
                                            case "BANNER":
                                                List<ImageView> bannerList = new ArrayList<>();
                                                for (int i = 0; i < bean.getPersonalAreaVos().size(); i++) {
                                                    if (jumpUrls.contains(bean.getPersonalAreaVos().get(i).getJumpType())) {
                                                        ImageView imageView = new ImageView(PersonActivity.this);
                                                        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                        lp1.setMargins(0, 16, 0, 0);
                                                        imageView.setLayoutParams(lp1);
                                                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                                        imageView.setAdjustViewBounds(true);
                                                        Glide.with(PersonActivity.this).load(bean.getPersonalAreaVos().get(i).getLogo()).into(imageView);
                                                        final int index = i;
                                                        imageView.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String url = bean.getPersonalAreaVos().get(index).getJumpUrl();
                                                                switch (bean.getPersonalAreaVos().get(index).getJumpType()) {
                                                                    case "COMMON_PERSON":
                                                                    case "COMMON":
                                                                        gotoMainWebView(url, bean.getPersonalAreaVos().get(index).getTitle());
                                                                        break;
                                                                    case "BXM_ZCM"://变现猫
                                                                        break;
                                                                    case "BXM_YCJ"://变现猫2
                                                                        break;
                                                                }
                                                            }
                                                        });
                                                        bannerList.add(imageView);
                                                    }
                                                }

                                                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(bannerList);
                                                viewpager.setAdapter(viewPagerAdapter);
                                                viewpager.setVisibility(View.VISIBLE);
                                                llStation.addView(viewpager);
                                                break;
                                        }
                                    }
                                }
                            } else {
                                String errorCode = personalInfo.getErrorCode();
                                if (errorCode.contains("9990")) {
                                    getMainToken();
                                }
                                if (errorCode.contains("9991")) {
                                    getMainToken();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }

    private void gotoMainWebView(String url, String title) {

        Intent intent = new Intent(this, SignWebViewActivity.class);
        intent.putExtra(SignWebViewActivity.OLD_URL, url);
        intent.putExtra(SignWebViewActivity.COMMON_PARAM, commonParam + "");
        startActivity(intent);
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
                            SPUtils.getInstance(PersonActivity.this).put(ApiHelper.ACCOUNT_ID, accountId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

}
