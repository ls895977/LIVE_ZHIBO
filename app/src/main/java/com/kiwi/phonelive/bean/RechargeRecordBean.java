package com.kiwi.phonelive.bean;

import java.util.List;

public class RechargeRecordBean {
    private int ret;
    private String msg;
    private List<DataBean> data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 0
         * create_time : 2019-05-15 15:03:49
         * voucher_img : http://qiniu.kiwiapp.vip/20190515150349_87dd757b20290cf014da2ae37737cb5e?imageView2/2/w/600/h/600
         */

        private long create_time;
        private String diamond_num;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getDiamond_num() {
            return diamond_num;
        }

        public void setDiamond_num(String diamond_num) {
            this.diamond_num = diamond_num;
        }
    }
}
