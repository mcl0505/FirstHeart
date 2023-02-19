package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.RoomRankBean;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.utils.NumberUtil;

import java.util.ArrayList;

import static com.konglianyuyin.mx.app.Api.APP_DOMAIN;
import static io.rong.imkit.utilities.RongUtils.dip2px;

/**
 * 排行榜
 */
@ActivityScope
public class RoomRankAdapterNewTop3 extends BaseQuickAdapter<RoomRankBean.DataBean.TopBean, BaseViewHolder> {
    private int type;
    private Context context;
    boolean mIsFirstTransion = true;
    BaseViewHolder mHelper;
    boolean is_show_num;

    public RoomRankAdapterNewTop3(int type,boolean is_show_num,  Context context) {
        super(R.layout.item_room_rank_new_top3, new ArrayList<>());
        this.type = type;
        this.context = context;
        this.is_show_num = is_show_num;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoomRankBean.DataBean.TopBean item) {
        mHelper = helper;
        helper.addOnClickListener(R.id.ci_head);
        TextView roomRankId1 = helper.getView(R.id.room_rank_id);
        ImageView image1 = helper.getView(R.id.image1);
        ImageView image11 = helper.getView(R.id.image11);
        ImageView sex = helper.getView(R.id.sex);
        if (item.getSex() == 1) {
            sex.setImageResource(R.mipmap.gender_boy);
        } else if (item.getSex() == 2) {
            sex.setImageResource(R.mipmap.gender_girl);
        }
        if (helper.getPosition() == 0) {
            image1.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_1));
            image11.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_11));
        } else if (helper.getPosition() == 1) {
            image1.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_2));
            image11.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_22));
        } else if (helper.getPosition() == 2) {
            image1.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_3));
            image11.setImageDrawable(context.getResources().getDrawable(R.mipmap.rank_new_top3_33));
        }
        roomRankId1.setText("ID:" + item.getId());
        if (!TextUtils.isEmpty(item.getBright_num())) {
            roomRankId1.setText("ID:" + item.getBright_num());
            roomRankId1.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            Drawable left = context.getResources().getDrawable(R.mipmap.jianhao);
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
            roomRankId1.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        } else {
            roomRankId1.setCompoundDrawables(null, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        }
//        if (type == 1) {
////            helper.setTextColor(R.id.textNum, mContext.getResources().getColor(R.color.color_FFBA1C));
//            helper.setText(R.id.tit, "贡献值");
//            if (!TextUtils.isEmpty(item.getGold_img())) {
//                ArmsUtils.obtainAppComponentFromContext(mContext)
//                        .imageLoader()
//                        .loadImage(mContext, ImageConfigImpl
//                                .builder()
//                                .url(item.getGold_img())
//                                .imageView(helper.getView(R.id.image_one))
//                                .build());
//            }
//        } else {
////            helper.setTextColor(R.id.textNum, mContext.getResources().getColor(R.color.font_ff3e6d));
//            helper.setText(R.id.tit, "魅力值");
//            if (!TextUtils.isEmpty(item.getStars_img())) {
//                ArmsUtils.obtainAppComponentFromContext(mContext)
//                        .imageLoader()
//                        .loadImage(mContext, ImageConfigImpl
//                                .builder()
//                                .url(item.getStars_img())
//                                .imageView(helper.getView(R.id.image_one))
//                                .build());
//            }
//        }

        String ml = "";
        if (is_show_num){
            ml = item.getMizuan();
        }else {
            if (ExtConfig.isOpenRoomRankLookMyself){
                if (UserManager.getUser().getUserId() == Integer.parseInt(item.getId())){
                    ml = item.getMizuan();
                }
            }else {
                ml = item.getMizuan();
            }
        }

        helper.setText(R.id.tv_title, item.getName()).setText(R.id.textNum,item.getMizuan())
                .setText(R.id.textNum, ml);

//        if (!TextUtils.isEmpty(item.getVip_img())) {
//            ArmsUtils.obtainAppComponentFromContext(mContext)
//                    .imageLoader()
//                    .loadImage(mContext, ImageConfigImpl
//                            .builder()
//                            .url(item.getVip_img())
//                            .imageView(helper.getView(R.id.image_two))
//                            .build());
//        }
        if (!TextUtils.isEmpty(item.getImg())) {
            String headUrl = item.getImg();
            if(!headUrl.contains("http")){
                headUrl = APP_DOMAIN+headUrl;
            }
            ArmsUtils.obtainAppComponentFromContext(mContext)
                    .imageLoader()
                    .loadImage(mContext, ImageConfigImpl
                            .builder()
                            .url(headUrl)
                            .placeholder(R.mipmap.no_tou)
                            .imageView(helper.getView(R.id.ci_head))
                            .errorPic(R.mipmap.no_tou)
                            .build());
        }
    }

    public void setFirstItemBg(boolean isTransion) {
        LogUtils.debugInfo("方法进来了==3==");
        if (mHelper != null) {
            if (mHelper.getPosition() == 1) {
                if (isTransion) {
                    if (mIsFirstTransion) {
                        mIsFirstTransion = false;
                        mHelper.setBackgroundRes(R.id.item_back, R.drawable.transion_corner_shape);
                        LogUtils.debugInfo("变成透明了");
                        notifyItemChanged(1);
                    }
                } else {
                    if (!mIsFirstTransion) {
                        mIsFirstTransion = true;
                        mHelper.setBackgroundRes(R.id.item_back, R.drawable.white_corner_shape);
                        LogUtils.debugInfo("变成白色了");
                        notifyItemChanged(1);
                    }
                }


            } else {
                mHelper.setBackgroundRes(R.id.item_back, R.drawable.white_corner_shape_two);
            }
        }
    }


//    public static class ViewHolder {
//        TextView tv_title, textNum, text1;
//        CircularImage ci_head;
//
//        public ViewHolder(View convertView) {
//
//            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//            textNum = (TextView) convertView.findViewById(R.id.textNum);
//            ci_head = convertView.findViewById(R.id.ci_head);
//        }
//    }

}