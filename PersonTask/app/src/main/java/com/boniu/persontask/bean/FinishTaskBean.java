package com.boniu.persontask.bean;

public class FinishTaskBean {

    /**
     * success : true
     * returnCode : 200
     * result : {"awardScore":100}
     * timeOut : false
     */

    private boolean success;
    private String returnCode;
    private ResultBean result;
    private boolean timeOut;

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
         * awardScore : 100
         */

        private String awardScore;

        public String getAwardScore() {
            return awardScore;
        }

        public void setAwardScore(String awardScore) {
            this.awardScore = awardScore;
        }
    }
}
