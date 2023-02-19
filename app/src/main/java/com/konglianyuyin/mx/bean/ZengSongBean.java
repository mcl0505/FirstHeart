package com.konglianyuyin.mx.bean;

public class ZengSongBean {

    /**
     * code : 1
     * message : {"user_info":{"id":13149,"is_zengsong":1,"mizuan":"9106.00"}}
     * data : []
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

//    public MessageBean getMessage() {
//        return message;
//    }
//
//    public void setMessage(MessageBean message) {
//        this.message = message;
//    }
//

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * user_info : {"id":13149,"is_zengsong":1,"mizuan":"9106.00"}
         */

        private UserInfoBean user_info;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
            /**
             * id : 13149
             * is_zengsong : 1
             * mizuan : 9106.00
             */

            private int id;
            private int is_zengsong;
            private String mizuan;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_zengsong() {
                return is_zengsong;
            }

            public void setIs_zengsong(int is_zengsong) {
                this.is_zengsong = is_zengsong;
            }

            public String getMizuan() {
                return mizuan;
            }

            public void setMizuan(String mizuan) {
                this.mizuan = mizuan;
            }
        }
    }
}
