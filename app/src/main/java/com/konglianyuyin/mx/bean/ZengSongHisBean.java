package com.konglianyuyin.mx.bean;

import java.util.List;

public class ZengSongHisBean {

    /**
     * code : 1
     * message : [{"id":23,"user_id":13149,"from_uid":13156,"money":"100.00","get_type":"转赠白钻（送）","now_nums":"49599.00","addtime":1584960641,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":21,"user_id":13149,"from_uid":13156,"money":"100.00","get_type":"转赠白钻（送）","now_nums":"49699.00","addtime":1584960547,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":19,"user_id":13149,"from_uid":13156,"money":"100.00","get_type":"转赠白钻（送）","now_nums":"49799.00","addtime":1584960413,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":17,"user_id":13149,"from_uid":13156,"money":"100.00","get_type":"转赠白钻（送）","now_nums":"49899.00","addtime":1584960215,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":15,"user_id":13149,"from_uid":13156,"money":"1.00","get_type":"转赠白钻（送）","now_nums":"49999.00","addtime":1584958912,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":13,"user_id":13149,"from_uid":13156,"money":"111.00","get_type":"转赠白钻（送）","now_nums":"6195.00","addtime":1584958663,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":11,"user_id":13149,"from_uid":13156,"money":"2800.00","get_type":"转赠白钻（送）","now_nums":"6306.00","addtime":1584958645,"fromUserInfo":{"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}},{"id":10,"user_id":13149,"from_uid":13149,"money":"2000.00","get_type":"转赠白钻（收）","now_nums":"9106.00","addtime":1584958623,"fromUserInfo":{"headimgurl":"/upload/avatar/20200310/15838120128350.jpg","nickname":"小试身手","sex":1,"birthday":"2019-03-10","age":1}},{"id":9,"user_id":13149,"from_uid":13149,"money":"2000.00","get_type":"转赠白钻（送）","now_nums":"9106.00","addtime":1584958623,"fromUserInfo":{"headimgurl":"/upload/avatar/20200310/15838120128350.jpg","nickname":"小试身手","sex":1,"birthday":"2019-03-10","age":1}},{"id":8,"user_id":13149,"from_uid":13149,"money":"55.00","get_type":"转赠白钻（收）","now_nums":"9106.00","addtime":1584958589,"fromUserInfo":{"headimgurl":"/upload/avatar/20200310/15838120128350.jpg","nickname":"小试身手","sex":1,"birthday":"2019-03-10","age":1}}]
     * data : []
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 23
         * user_id : 13149
         * from_uid : 13156
         * money : 100.00
         * get_type : 转赠白钻（送）
         * now_nums : 49599.00
         * addtime : 1584960641
         * fromUserInfo : {"headimgurl":"/upload/avatar/20200315/15842507712990.png","nickname":"A","sex":1,"birthday":"2020-03-15","age":0}
         */

        private int id;
        private int user_id;
        private int from_uid;
        private String money;
        private String get_type;
        private String now_nums;
        private String addtime;
        private FromUserInfoBean fromUserInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getFrom_uid() {
            return from_uid;
        }

        public void setFrom_uid(int from_uid) {
            this.from_uid = from_uid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getGet_type() {
            return get_type;
        }

        public void setGet_type(String get_type) {
            this.get_type = get_type;
        }

        public String getNow_nums() {
            return now_nums;
        }

        public void setNow_nums(String now_nums) {
            this.now_nums = now_nums;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public FromUserInfoBean getFromUserInfo() {
            return fromUserInfo;
        }

        public void setFromUserInfo(FromUserInfoBean fromUserInfo) {
            this.fromUserInfo = fromUserInfo;
        }

        public static class FromUserInfoBean {
            /**
             * headimgurl : /upload/avatar/20200315/15842507712990.png
             * nickname : A
             * sex : 1
             * birthday : 2020-03-15
             * age : 0
             */

            private String headimgurl;
            private String nickname;
            private int sex;
            private String bright_num;
            private String birthday;
            private int age;

            public String getBright_num() {
                return bright_num;
            }

            public void setBright_num(String bright_num) {
                this.bright_num = bright_num;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }
    }
}
