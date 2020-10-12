package com.boniu.persontask;


public class ApiHelper {

    /**
     * 这几个接口下用md。rhinox这个域名
     * /faceModule/sign
     * /personal
     *
     * 其他的用apps.rhinoxlab
     *
     *
     */

//    public static final String BASE_URI = "https://apps.rhinoxlab.com/";
//    public static final String BASE_URI2 = "https://md.rhinox.cn/";

    public static final String BASE_URI = "http://test99.jukmall.cn/";
    public static final String BASE_URI2 = "http://test99.jukmall.cn/";


//    public static final String BASE_URI = "http://192.168.10.61:7102/";
//    public static final String BASE_URI2 = "http://192.168.10.61:7102/";

    public static final String COMMEN_BASE = BASE_URI + "app/common/versionInfo";
    public static final String COMMEN_JUBAO = BASE_URI + "app/common/addReport";
    public static final String COMMEN_GET_MAIN_TOKEN = BASE_URI + "ptu/info/initAccount";
    public static final String COMMEN_PERSONNAL = BASE_URI2 + "personal/index";//用md域名下的
    public static final String FINISH_TAKS = BASE_URI2 + "module/mission/report";//完成任务

    public static final String INVITE_FRIEND = BASE_URI2 + "module/mission/goInvite";//邀请好友
    public static final String OUT_LOGIN = BASE_URI + "ptu/info/logout";//推出登陆

    //请求加密key
    public static final String REQUEST_KEY = "UkDfuBJZ3bQmjFhL";
    public static final String HEADER_KEY = "dBkOEzSlFrj1it8p";

    //任务类型  任务id
    public static final String TASK_TYPE = "TASK_TYPE";
    public static final String TASK_ID = "TASK_ID";


    public static final String VIP_TYPE = "VIP_TYPE";

    public static final String UUID_STR = "UUID_STR";
    public static final int LOGIN_RETURN = 1024;

    public static final String APP_NAME = "PTUDASHEN_ANDROID_BONIU";//app标示

    public static final String APP_VERSION = "APP_VERSION";
    public static final String VERSION_JSON = "VERSION_JSON";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";



}
