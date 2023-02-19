package com.konglianyuyin.mx.bean;

import java.util.List;

public class GoodsListBean {

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String balance;
        private List<GoodslistBean> goodslist;
        private String cnylingshi;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public String getCnylingshi() {
            return cnylingshi;
        }

        public void setCnylingshi(String cnylingshi) {
            this.cnylingshi = cnylingshi;
        }

        public static class GoodslistBean {
            private String price;
            private int mizuan;
            private String id;
            boolean isSelect;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getMizuan() {
                return mizuan;
            }

            public void setMizuan(int mizuan) {
                this.mizuan = mizuan;
            }
        }
    }
}
