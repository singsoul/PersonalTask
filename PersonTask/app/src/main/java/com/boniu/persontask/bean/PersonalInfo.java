package com.boniu.persontask.bean;

import java.util.List;

public class PersonalInfo {


    /**
     * success : true
     * returnCode : 200
     * result : {"headImg":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","mobile":"18236569369","personalScoreInfoVo":{"todayScore":0,"totalScore":0,"cashScore":0,"missionUrl":"https://www.baidu.com"},"signUrl":"http://192.168.10.62:8003","transferUrl":"https://www.baidu.com","personalFreeVos":[{"freeSort":2,"personalAreaVos":[{"title":"邀请好友\n免费领现金","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"BXM_ZCM"},{"title":"种植森林\n免费领树","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"BXM_YCJ"}]},{"freeSort":1,"personalAreaVos":[{"title":"","logo":"http://boniustory.oss-cn-hangzhou.aliyuncs.com/operate/20200330/1585540089644.jpg","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"},{"title":"","logo":"http://boniustory.oss-cn-hangzhou.aliyuncs.com/operate/20200410/1586515486629.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"}]}],"personalServiceVos":[{"title":"一览","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"YILAN"},{"title":"铃声","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"RINGTONE"},{"title":"变现猫","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"BXM_XJK"},{"title":"新闻赚钱","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"},{"title":"小说频道","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"http://three.zixunredian.com/mpage/newIndex?channel=","jumpType":"COMMON"}]}
     * timeOut : false
     */

    private boolean success;
    private String returnCode;
    private ResultBean result;
    private boolean timeOut;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }



    public static class ResultBean {
        /**
         * headImg : http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png
         * mobile : 18236569369
         * personalScoreInfoVo : {"todayScore":0,"totalScore":0,"cashScore":0,"missionUrl":"https://www.baidu.com"}
         * signUrl : http://192.168.10.62:8003
         * transferUrl : https://www.baidu.com
         * personalFreeVos : [{"freeSort":2,"personalAreaVos":[{"title":"邀请好友\n免费领现金","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"BXM_ZCM"},{"title":"种植森林\n免费领树","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"BXM_YCJ"}]},{"freeSort":1,"personalAreaVos":[{"title":"","logo":"http://boniustory.oss-cn-hangzhou.aliyuncs.com/operate/20200330/1585540089644.jpg","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"},{"title":"","logo":"http://boniustory.oss-cn-hangzhou.aliyuncs.com/operate/20200410/1586515486629.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"}]}]
         * personalServiceVos : [{"title":"一览","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"YILAN"},{"title":"铃声","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"RINGTONE"},{"title":"变现猫","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"BXM_XJK"},{"title":"新闻赚钱","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"COMMON"},{"title":"小说频道","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"http://three.zixunredian.com/mpage/newIndex?channel=","jumpType":"COMMON"}]
         */

        private String headImg;
        private String mobile;
        private PersonalScoreInfoVoBean personalScoreInfoVo;
        private String signUrl;
        private String transferUrl;
        private List<PersonalFreeVosBean> personalFreeVos;
        private List<PersonalServiceVosBean> personalServiceVos;


        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public PersonalScoreInfoVoBean getPersonalScoreInfoVo() {
            return personalScoreInfoVo;
        }

        public void setPersonalScoreInfoVo(PersonalScoreInfoVoBean personalScoreInfoVo) {
            this.personalScoreInfoVo = personalScoreInfoVo;
        }

        public String getSignUrl() {
            return signUrl;
        }

        public void setSignUrl(String signUrl) {
            this.signUrl = signUrl;
        }

        public String getTransferUrl() {
            return transferUrl;
        }

        public void setTransferUrl(String transferUrl) {
            this.transferUrl = transferUrl;
        }

        public List<PersonalFreeVosBean> getPersonalFreeVos() {
            return personalFreeVos;
        }

        public void setPersonalFreeVos(List<PersonalFreeVosBean> personalFreeVos) {
            this.personalFreeVos = personalFreeVos;
        }

        public List<PersonalServiceVosBean> getPersonalServiceVos() {
            return personalServiceVos;
        }

        public void setPersonalServiceVos(List<PersonalServiceVosBean> personalServiceVos) {
            this.personalServiceVos = personalServiceVos;
        }

        public static class PersonalScoreInfoVoBean {
            /**
             * todayScore : 0
             * totalScore : 0
             * cashScore : 0
             * missionUrl : https://www.baidu.com
             */

            private int todayScore;
            private int totalScore;
            private int cashScore;
            private String missionUrl;
            private String canWithDraw;

            public String getCanWithDraw() {
                return canWithDraw;
            }

            public void setCanWithDraw(String canWithDraw) {
                this.canWithDraw = canWithDraw;
            }

            public int getTodayScore() {
                return todayScore;
            }

            public void setTodayScore(int todayScore) {
                this.todayScore = todayScore;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }

            public int getCashScore() {
                return cashScore;
            }

            public void setCashScore(int cashScore) {
                this.cashScore = cashScore;
            }

            public String getMissionUrl() {
                return missionUrl;
            }

            public void setMissionUrl(String missionUrl) {
                this.missionUrl = missionUrl;
            }
        }

        public static class PersonalFreeVosBean {
            /**
             * freeSort : 2
             * personalAreaVos : [{"title":"邀请好友\n免费领现金","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"YES","jumpUrl":"https://www.baidu.com","jumpType":"BXM_ZCM"},{"title":"种植森林\n免费领树","logo":"http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png","loginFlag":"NO","jumpUrl":"https://www.baidu.com","jumpType":"BXM_YCJ"}]
             */

            private String layoutType;
            private List<PersonalAreaVosBean> personalAreaVos;
            public String getViewType() {
                return layoutType;
            }

            public void setViewType(String ViewType) {
                this.layoutType = ViewType;
            }

            public List<PersonalAreaVosBean> getPersonalAreaVos() {
                return personalAreaVos;
            }

            public void setPersonalAreaVos(List<PersonalAreaVosBean> personalAreaVos) {
                this.personalAreaVos = personalAreaVos;
            }

            public static class PersonalAreaVosBean {
                /**
                 * title : 邀请好友
                 * 免费领现金
                 * logo : http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png
                 * loginFlag : YES
                 * jumpUrl : https://www.baidu.com
                 * jumpType : BXM_ZCM
                 */

                private String title;
                private String logo;
                private String loginFlag;
                private String jumpUrl;
                private String jumpType;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public String getJumpUrl() {
                    return jumpUrl;
                }

                public void setJumpUrl(String jumpUrl) {
                    this.jumpUrl = jumpUrl;
                }

                public String getJumpType() {
                    return jumpType;
                }

                public void setJumpType(String jumpType) {
                    this.jumpType = jumpType;
                }
            }
        }

        public static class PersonalServiceVosBean {
            /**
             * title : 一览
             * logo : http://boniuapp.oss-cn-hangzhou.aliyuncs.com/common/head/1599546812784.png
             * loginFlag : YES
             * jumpUrl : https://www.baidu.com
             * jumpType : YILAN
             */

            private String title;
            private String logo;
            private String loginFlag;
            private String jumpUrl;
            private String jumpType;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(String jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public String getJumpType() {
                return jumpType;
            }

            public void setJumpType(String jumpType) {
                this.jumpType = jumpType;
            }
        }
    }


}
