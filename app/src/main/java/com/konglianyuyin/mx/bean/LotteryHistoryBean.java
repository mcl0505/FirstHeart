package com.konglianyuyin.mx.bean;

import java.util.List;

public class LotteryHistoryBean {

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private String selectpk;
        private String winpk;
        private int state;
        private String addtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSelectpk() {
            return selectpk;
        }

        public void setSelectpk(String selectpk) {
            this.selectpk = selectpk;
        }

        public String getWinpk() {
            return winpk;
        }

        public void setWinpk(String winpk) {
            this.winpk = winpk;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
