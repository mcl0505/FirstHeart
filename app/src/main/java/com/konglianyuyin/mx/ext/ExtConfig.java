package com.konglianyuyin.mx.ext;

import com.konglianyuyin.mx.R;

/**
 * 扩展配置
 *  可根据相关字段修改对于功能
 */
public class ExtConfig {
    //是否开启点击头像显示转赠按钮  附带转赠是发送私聊消息   true=显示转赠   false=不显示转赠
    public static boolean isTransfer = true;
    //上麦是否需要申请  true=申请上麦  false=直接上麦
    public static boolean isMicrophoneRequest = false;
    //是否显示卡罗牌 true=显示 false=不显示
    public static boolean isShowLottery = false;
    //是否需要将发送礼物添加到私聊
    public static boolean isSendGiftNeedAddToMessage = true;
    //是否可以自己赠送礼物给自己
    public static boolean isSendGiftToMyself = true;
    //是否可以清楚魅力值
    public static boolean isCleanMeiLi = true;
    //是否添加全部赠送按钮
    public static boolean isSendAllGift = true;
    //全部赠送  是否展示特效
    public static boolean isSendAllGiftShow = false;
    //是否开启房间图片修改功能
    public static boolean isOpenRoomChangeImage = true;
    //开房间是否需要实名认证
    public static boolean isOpenRoomNeedRealName = false;
    //贡献值需要大于等于这个数据才能显示
    public static int showEggMoney = 50;
    //宝箱显示的图片
    public static int baoxiangImg = R.mipmap.icon_baoxiang;
    //是否开启排行榜只看自己的   管理员与房主能查看全部
    public static boolean isOpenRoomRankLookMyself = true;
    //是否注册自定义转赠消息
    public static boolean isRegisterMsg = true;
    //礼物相关
    public static int gift_bg_1 = R.drawable.bg_room_chatitem_2;//普通礼物背景
    public static String gift_default_text_color_1 = "#61DFBD";//普通礼物字体默认颜色
    public static int gift_bg_2 = R.drawable.bg_room_chatitem_3;//中级礼物背景
    public static String gift_default_text_color_2 = "#51C8FF";//中级礼物字体默认颜色
    public static int gift_bg_3 = R.drawable.bg_room_chatitem_4;//高级礼物背景
    public static String gift_default_text_color_3 = "#FBA8DC";//高级礼物字体默认颜色
}
