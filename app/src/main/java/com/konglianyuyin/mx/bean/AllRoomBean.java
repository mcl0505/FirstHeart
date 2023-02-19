package com.konglianyuyin.mx.bean;

import java.util.List;

public class AllRoomBean {

    /**
     * code : 1
     * message : 成功
     * data : [{"id":184,"numid":"111119","uid":111119},{"id":185,"numid":"111120","uid":111120},{"id":186,"numid":"111122","uid":111122},{"id":187,"numid":"111124","uid":111124},{"id":188,"numid":"111125","uid":111125},{"id":189,"numid":"111121","uid":111121},{"id":190,"numid":"111123","uid":111123},{"id":191,"numid":"111129","uid":111129},{"id":192,"numid":"111128","uid":111128},{"id":193,"numid":"111132","uid":111132},{"id":195,"numid":"111137","uid":111137}]
     */

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
        /**
         * id : 184
         * numid : 111119
         * uid : 111119
         */

        private int id;
        private String numid;
        private String uid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumid() {
            return numid;
        }

        public void setNumid(String numid) {
            this.numid = numid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
