package com.cosmos.operatorlogin;


import com.cosmos.operatorlogin.netutils.JsonRequestResult;

public class JsonUserInfo implements JsonRequestResult {


    /**
     * ec : 0
     * em : success
     * timesec : 1574137352
     * data : {"code":"201","msg":"token已失效","mobile":"xxxxzxsxzzx"}
     */

    private int ec;
    private String em;
    private int timesec;
    private DataBean data;

    @Override
    public boolean success() {
        return ec == 0;
    }

    public int getEc() {
        return ec;
    }

    public void setEc(int ec) {
        this.ec = ec;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public int getTimesec() {
        return timesec;
    }

    public void setTimesec(int timesec) {
        this.timesec = timesec;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 201
         * msg : token已失效
         * mobile : xxxxzxsxzzx
         */

        private String code;
        private String msg;
        private String mobile;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JsonUserInfo{" +
                "ec=" + ec +
                ", em='" + em + '\'' +
                ", timesec=" + timesec +
                ", data=" + data +
                '}';
    }
}
