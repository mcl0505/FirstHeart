package com.konglianyuyin.mx.http;

/**
 * Created by cxf on 2018/9/17.
 */

public class HttpUtil {

    private static final String SALT = "76576076c1f5f657b634e966c8836a06";
    private static final String DEVICE = "android";
    private static final String VIDEO_SALT = "#2hgfk85cm23mk58vncsark";

    /**
     * 初始化
     */
    public static void init() {
        HttpClient.getInstance().init();
    }

    /**
     * 取消网络请求
     */
    public static void cancel(String tag) {
        HttpClient.getInstance().cancel(tag);
    }

    /**
     * 获取礼物列表
     */
    public static void getGiftList(String uid, HttpCallback callback) {
        HttpClient.getInstance().get("gift_list", HttpConsts.GET_GIFT)
                .params("uid", uid)
                .execute(callback);
    }

    /**
     * 获取轮播
     */
    public static void getBanner(String xx, HttpCallback callback) {
        HttpClient.getInstance().get("carousel", HttpConsts.GET_GIFT)
                .params("xx", xx)
                .execute(callback);
    }

    public static void getRoomList(String id,String page,HttpCallback callback){
        HttpClient.getInstance().post("room_recommend_room", HttpConsts.GET_GIFT)
                .params("categories", id)
                .params("page", page)
                .execute(callback);
    }

}




