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
import com.konglianyuyin.mx.bean.Rank;

import java.util.ArrayList;

import static io.rong.imkit.utilities.RongUtils.dip2px;

/**
 * 排行榜
 */
@ActivityScope
public class RankAdapterNew extends BaseQuickAdapter<Rank.DataBean.OtherBean, BaseViewHolder> {
    private int type;
    private Context context;
    boolean mIsFirstTransion = true;
    BaseViewHolder mHelper;

    public RankAdapterNew(int type, Context context) {
        super(R.layout.item_rank_new, new ArrayList<>());
        this.type = type;
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Rank.DataBean.OtherBean item) {
        mHelper = helper;
        ImageView sex = helper.getView(R.id.sex);
        if (item.getSex()!=null&&item.getSex().equals("1")) {
            sex.setImageResource(R.mipmap.gender_boy);
        } else if (item.getSex()!=null&&item.getSex().equals("2")) {
            sex.setImageResource(R.mipmap.gender_girl);
        }
        helper.addOnClickListener(R.id.ci_head);
        TextView roomRankId1 = helper.getView(R.id.room_rank_id);
//        roomRankId1.setText("ID:" + item.getUser_id());
        if (!TextUtils.isEmpty(item.getBright_num())) {
//            roomRankId1.setText("ID:" + item.getBright_num());
            Drawable left = context.getResources().getDrawable(R.mipmap.jianhao);
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
            roomRankId1.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        } else {
            roomRankId1.setCompoundDrawables(null, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        }
        if (type == 1) {
//            helper.setTextColor(R.id.textNum, mContext.getResources().getColor(R.color.color_FFBA1C));
            helper.setText(R.id.tit, "贡献值");
            if (!TextUtils.isEmpty(item.getGold_img())) {
                ArmsUtils.obtainAppComponentFromContext(mContext)
                        .imageLoader()
                        .loadImage(mContext, ImageConfigImpl
                                .builder()
                                .url(item.getGold_img())
                                .imageView(helper.getView(R.id.image_one))
                                .build());
            }
        } else {
//            helper.setTextColor(R.id.textNum, mContext.getResources().getColor(R.color.font_ff3e6d));
            helper.setText(R.id.tit, "魅力值");
            if (!TextUtils.isEmpty(item.getStars_img())) {
                ArmsUtils.obtainAppComponentFromContext(mContext)
                        .imageLoader()
                        .loadImage(mContext, ImageConfigImpl
                                .builder()
                                .url(item.getStars_img())
                                .imageView(helper.getView(R.id.image_one))
                                .build());
            }
        }
        helper.setText(R.id.text1, String.valueOf(helper.getPosition() + 4))
                .setText(R.id.tv_title, item.getNickname())
                .setText(R.id.textNum,item.getExp()+"");
//                .setText(R.id.textNum, NumberUtil.getBigDecimal(String.valueOf(item.getExp())));

        if (!TextUtils.isEmpty(item.getVip_img())) {
            ArmsUtils.obtainAppComponentFromContext(mContext)
                    .imageLoader()
                    .loadImage(mContext, ImageConfigImpl
                            .builder()
                            .url(item.getVip_img())
                            .imageView(helper.getView(R.id.image_two))
                            .build());
        }
        if (!TextUtils.isEmpty(item.getHeadimgurl())) {
            ArmsUtils.obtainAppComponentFromContext(mContext)
                    .imageLoader()
                    .loadImage(mContext, ImageConfigImpl
                            .builder()
                            .url(item.getHeadimgurl())
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