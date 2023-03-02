package com.konglianyuyin.mx.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.ext.ExtConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间聊天
 */
@ActivityScope
public class RoomMessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
//    messageType     1：正常的 聊天消息  2：进入房间  3： 刷新麦序列表  4 ：礼物消息  5 ：表型消息
    String roomId;
    AdminHomeActivity mActivity;
    public RoomMessageAdapter(AdminHomeActivity context,String roomId) {
        super(R.layout.item_room_message, new ArrayList<>());
        mActivity = context;
        this.roomId = roomId;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        if (TextUtils.equals(item.getMessageType(), "1")) {
            //正常的消息
            helper.setTextColor(R.id.textName, Color.parseColor("#51C8FF"));
            helper.setGone(R.id.ll1, true)
                    .setGone(R.id.ll2, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.tv_gonggao, false)
                    //.setText(R.id.textName, item.getT_length() + item.getNickName())
                    .setText(R.id.textName, item.getNickName())
                    .addOnClickListener(R.id.textName)
                    .setText(R.id.textDec, item.getMessage());
//            helper
//                    .setGone(R.id.imgVip, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz, true);
//            } else {
//                helper.setGone(R.id.imgHz, false);
//            }

            ImageView imgTopLeft = helper.getView(R.id.img_top_left);

            ImageView imgRightBottom = helper.getView(R.id.img_bottom_right);

            LinearLayout layoutBg = helper.getView(R.id.layout_msg_ltk);

            if (mContext instanceof AdminHomeActivity) {

                if (!TextUtils.isEmpty(item.ltk_left)) {
                    helper.setGone(R.id.img_top_left, true);
                    ((AdminHomeActivity) mContext).loadImage(imgTopLeft,
                            item.ltk_left, R.mipmap.huizhang);
                } else {
                    helper.setGone(R.id.img_top_left, false);
                }

                if (!TextUtils.isEmpty(item.ltk_right)) {
                    helper.setGone(R.id.img_bottom_right, true);
                    ((AdminHomeActivity) mContext).loadImage(imgRightBottom,
                            item.ltk_right, R.mipmap.huizhang);
                } else {
                    helper.setGone(R.id.img_bottom_right, false);
                }

                if (!TextUtils.isEmpty(item.ltk)) {
                    Glide.with(mContext)
                            .asBitmap()
                            .load(item.ltk)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    layoutBg.setBackground(drawable);
                                }

                            });
                } else {
                    layoutBg.setBackground(null);
                }

            }
        }
        else if (TextUtils.equals(item.getMessageType(), "2")) {//进入房间
            helper.setTextColor(R.id.textName2, Color.parseColor("#51C8FF"));
            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .addOnClickListener(R.id.textName2)
                    .setGone(R.id.textName3, true)
                    .setText(R.id.textName2, item.getNickName())
                    .setText(R.id.textName3, "进入直播间");
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackground(mContext.getResources().getDrawable(R.drawable.bg_room_chatitem));
//            helper.setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }
//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }
        }
        else if (TextUtils.equals(item.getMessageType(), "5")) {//表情消息
            helper.setTextColor(R.id.textName2, Color.parseColor("#51C8FF"));
            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .addOnClickListener(R.id.textName2)
                    .setGone(R.id.textName3, true)
                    .setText(R.id.textName2, item.getNickName())
                    .setText(R.id.textName3, item.getMessage());

            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackground(mContext.getResources().getDrawable(R.drawable.bg_room_chatitem));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }
//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }
        }
        else if (TextUtils.equals(item.getMessageType(), "4")) {
            //礼物消息
            helper.setTextColor(R.id.textNameGift1, Color.parseColor("#51C8FF"));
            helper.setTextColor(R.id.textNameGift2, Color.parseColor("#51C8FF"));
            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, false)
                    .setGone(R.id.ll3, true)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .addOnClickListener(R.id.textNameGift1)
                    .addOnClickListener(R.id.textNameGift2)
                    .setText(R.id.textNum, "x" + item.giftNum)
                    .setText(R.id.textNameGift1, item.getNickName())
                    .setText(R.id.textNameGift2, item.userInfo.get(0).nickname);
            ImageView imageView = helper.getView(R.id.imgGift);
            GlideArms.with(mContext)
                    .load(item.show_img)
                    .placeholder(R.mipmap.gift_size)
                    .error(R.mipmap.gift_size)
                    .circleCrop()
                    .into(imageView);
        }
        else if (TextUtils.equals(item.getMessageType(), "10")) {
            //cp进入房间，发送点对点消息，类型10，自己的公屏显示守护XX在房间
            TextView textView = helper.getView(R.id.textName2);
            textView.setText("");
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));
            String str1 = "守护CP";
            String str2 = item.toNickName;
            String str3 = "在房间";
            String colorName = item.toNick_color;
            textView.append(new SpannableString(str1));
            SpannableString clickString = new SpannableString(str2);

            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setFirstNameClickNew(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(colorName));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString);

            textView.append(new SpannableString(str3));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);
//                    .addOnClickListener(R.id.textName2);
//                    .setText(R.id.textName2, "守护CP"+item.toNickName+"在房间");
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackgroundColor(mContext.getResources().getColor(R.color.translant));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }

        }
        else if (TextUtils.equals(item.getMessageType(), "9")) {
            //cp进入房间，收到点对点消息，类型9，自己的公屏显示守护CPXX进入房间
            TextView textView = helper.getView(R.id.textName2);
            textView.setText("");
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));

            String str1 = "守护CP";

            String str2 = item.getNickName();

            String str3 = "进入房间";

            String colorName = item.nick_color;

            textView.append(new SpannableString(str1));

            SpannableString clickString = new SpannableString(str2);

            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setFirstNameClick(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(colorName));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString);

            textView.append(new SpannableString(str3));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);
//                    .addOnClickListener(R.id.textName2);
//                    .setText(R.id.textName2, "守护"+item.toNickName+"进入房间");
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackgroundColor(mContext.getResources().getColor(R.color.translant));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }

        }
        else if (TextUtils.equals(item.getMessageType(), "8")) {
            //cp进入房间所有人公屏显示
            TextView textView = helper.getView(R.id.textName2);
            textView.setText("");
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));

            String str1 = "守护CP";

            String str2 = item.getNickName();

            String str3 = "和";

            String str4 = item.toNickName;

            String str5 = "同在房间";

            String colorName = item.nick_color;

            String toColorName = item.toNick_color;

            textView.append(new SpannableString(str1));

            SpannableString clickString = new SpannableString(str2);

            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setFirstNameClick(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(colorName));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString);

            textView.append(new SpannableString(str3));

            SpannableString clickString4 = new SpannableString(str4);

            clickString4.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setSecondNameClickNew(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(toColorName));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString4);

            textView.append(new SpannableString(str5));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);
//                    .addOnClickListener(R.id.textName2);
//                    .setText(R.id.textName2, "守护"+item.toNickName+"进入房间");
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackgroundColor(mContext.getResources().getColor(R.color.translant));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }

        }
        else if (TextUtils.equals(item.getMessageType(), "11")) {
            //同意结为CP，房间公屏显示
            TextView textView = helper.getView(R.id.textName2);

            textView.setText("");

            textView.setTextColor(mActivity.getResources().getColor(R.color.white));

            String name1 = item.getNickName();

            String str1 = "与";

            String name2 = item.toNickName;

            String str2 = "结为守护CP啦";

            String nickColor = item.nick_color;

            String toNickColor = item.toNick_color;

            LogUtils.debugInfo("nick_clolr==" + nickColor);
            LogUtils.debugInfo("toNick_color==" + toNickColor);

            SpannableString clickString = new SpannableString(name1);

            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setFirstNameClick(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(nickColor));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString);

            textView.append(new SpannableString(str1));

            SpannableString clickString2 = new SpannableString(name2);

            clickString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {////点击事件
                    mActivity.setSecondNameClickNew(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(toNickColor));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString2);

            textView.append(new SpannableString(str2));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);
//                    .addOnClickListener(R.id.textName2);
//                    .setText(R.id.textName2, str1);
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackgroundColor(mContext.getResources().getColor(R.color.translant));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }
        }
        else if (TextUtils.equals(item.getMessageType(), "12")) {
            //联手上麦
            TextView textView = helper.getView(R.id.textName2);

            textView.setText("");

            textView.setTextColor(mActivity.getResources().getColor(R.color.white));

            String name1 = item.getNickName();

            String str1 = "与";

            String name2 = item.toNickName;

            String str2 = "携手上麦";

            String nickColor = item.nick_color;

            String toNickColor = item.toNick_color;

            LogUtils.debugInfo("nick_clolr==" + nickColor);
            LogUtils.debugInfo("toNick_color==" + toNickColor);

            SpannableString clickString = new SpannableString(name1);

            clickString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    mActivity.setFirstNameClick(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(nickColor));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString);

            textView.append(new SpannableString(str1));

            SpannableString clickString2 = new SpannableString(name2);

            clickString2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {////点击事件
                    mActivity.setSecondNameClickNew(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor(toNickColor));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickString2);

            textView.append(new SpannableString(str2));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, true)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);
//                    .addOnClickListener(R.id.textName2);
//                    .setText(R.id.textName2, str1);
//                    .setText(R.id.textDec2,item.getMessage());
            LinearLayout ll2 = helper.getView(R.id.ll2);
            ll2.setBackgroundColor(mContext.getResources().getColor(R.color.translant));
//            helper
//                    .setGone(R.id.imgVip2, !TextUtils.isEmpty(item.vip_img));
//            if (mContext instanceof AdminHomeActivity && !TextUtils.isEmpty(item.vip_img)) {
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgVip2),
//                        item.vip_img, R.mipmap.huizhang);
//            }

//            if(mContext instanceof AdminHomeActivity  && !TextUtils.isEmpty(item.hz_img)){
//                ((AdminHomeActivity) mContext).loadImage(helper.getView(R.id.imgHz2),
//                        item.hz_img,R.mipmap.huizhang);
//                helper.setGone(R.id.imgHz2, true);
//            } else {
//                helper.setGone(R.id.imgHz2, false);
//            }

        }
        else if (TextUtils.equals(item.getMessageType(), "13") || TextUtils.equals(item.getMessageType(), "6666")) {
            //名字颜色
            int defaultTextColor = Color.parseColor("#ffffff");
            int nameColor = Color.parseColor("#ffde00");
            int giftColor = Color.parseColor("#ffde00");
            int defaultBg = R.drawable.bg_room_chatitem;
            //有人开宝箱
            TextView textView = helper.getView(R.id.tv_just_tip);
            textView.setText("");

            String oneStr = TextUtils.equals(item.getMessageType(), "6666") ? "大吉大利【全服】恭喜 " : "哇偶~";
            String twoStr = item.getNickName();
            if (twoStr == null) return;
            String totalStr = "在" + (item.box_class.equals("1") ? "黄金海岸" : item.box_class.equals("2")?"神秘海域":"深海遨游") + "开出了";
            LinearLayout ll4= helper.getView(R.id.ll4);
            if (item.box_class.equals("1")){//普通
                defaultBg = ExtConfig.gift_bg_1;
                defaultTextColor = Color.parseColor(ExtConfig.gift_default_text_color_1);
            }else if (item.box_class.equals("2")){//中级
                defaultBg = ExtConfig.gift_bg_2;
                    defaultTextColor = Color.parseColor(ExtConfig.gift_default_text_color_2);
            }else if (item.box_class.equals("3")){//高级
                defaultBg = ExtConfig.gift_bg_3;
               defaultTextColor = Color.parseColor(ExtConfig.gift_default_text_color_3);
            }
            ll4.setBackground(mContext.getResources().getDrawable(defaultBg));
            textView.setTextColor(defaultTextColor);

            textView.append(new SpannableString(oneStr));

            SpannableString clickStringOne = new SpannableString(twoStr);

            clickStringOne.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {//点击事件
                    LogUtils.debugInfo("点击了名字=======");

                    mActivity.setFirstNameClick(helper.getAdapterPosition() - 1);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(nameColor);//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
            }, 0, clickStringOne.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.append(clickStringOne);

            textView.append(new SpannableString(totalStr));

            if (!TextUtils.isEmpty(item.getMessage()) || (item.awardList != null && item.awardList.size() > 0)) {

                if (item.awardList != null && item.awardList.size() > 0) {

                    List<OpenBoxBean.DataBean.AwardListBean> awardList = item.awardList;

                    StringBuilder builder;

                    for (OpenBoxBean.DataBean.AwardListBean bean : awardList) {
                        builder = new StringBuilder();
                        String number = "x" + bean.getNum();
                        String name = bean.getName();
                        String price=bean.getPrice()+"钻";
                        builder.append(name);
                        builder.append(price);
                        builder.append(number);
                        String strs = builder.toString();
                        SpannableString clickString = new SpannableString(strs);
                        clickString.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {//点击事件
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(giftColor);//设置颜色
                                ds.setUnderlineText(false);//去掉下划线
                            }
                        }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView.append(clickString);
                        textView.append(new SpannableString(" "));
                    }


                } else {

                    String timps = item.getMessage();

                    if (!TextUtils.isEmpty(timps)) {

                        if (timps.endsWith(" ")) {
                            timps = timps.substring(0, timps.length() - 1);//去除最后一个空格
                        }


                        SpannableString clickString = new SpannableString(timps);

                        clickString.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {//点击事件

                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(giftColor);//设置颜色
                                ds.setUnderlineText(false);//去掉下划线
                            }
                        }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        textView.append(clickString);
//                        textView.append(new SpannableString(","));
                    } else {
                        textView.append(new SpannableString(""));
                    }

                }

            }

//            textView.append(new SpannableString("真是太优秀了！"));

            textView.setMovementMethod(LinkMovementMethod.getInstance());

            //设置超文本点击后的背景色为透明
            textView.setHighlightColor(textView.getResources().getColor(android.R.color.transparent));

            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, false)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, true)
                    .setGone(R.id.tv_gonggao, false)
                    .setGone(R.id.textName3, false);


        }
        else if (TextUtils.equals(item.getMessageType(), "7")) {
            // 公告变了
            helper.setGone(R.id.ll1, false)
                    .setGone(R.id.ll2, false)
                    .setGone(R.id.ll3, false)
                    .setGone(R.id.ll4, false)
                    .setGone(R.id.tv_gonggao, true)
                    .setGone(R.id.textName3, false);
            helper.setText(R.id.tv_gonggao, item.getRoom_intro());
        }
    }

}
