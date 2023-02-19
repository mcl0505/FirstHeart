package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.Rank;

import java.util.ArrayList;

/**
 * 排行榜
 */
@ActivityScope
public class RankAdapterN extends BaseQuickAdapter<Rank.DataBean.OtherBean, BaseViewHolder> {
    private int type;
    private Context context;

    public RankAdapterN(int type, Context context) {
        super(R.layout.item_rank_n, new ArrayList<>());
        this.type = type;
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Rank.DataBean.OtherBean item) {
        /*if (type == 1) {
            if (!TextUtils.isEmpty(item.getGold_img())) {
                ArmsUtils.obtainAppComponentFromContext(mContext)
                        .imageLoader()
                        .loadImage(mContext, ImageConfigImpl
                                .builder()
                                .url(item.getGold_img())
                                .imageView(helper.getView(R.id.ci_head))
                                .build());
            }
        } else {
            if (!TextUtils.isEmpty(item.getStars_img())) {
                ArmsUtils.obtainAppComponentFromContext(mContext)
                        .imageLoader()
                        .loadImage(mContext, ImageConfigImpl
                                .builder()
                                .url(item.getStars_img())
                                .imageView(helper.getView(R.id.ci_head))
                                .build());
            }
        }*/
        helper.setText(R.id.tv_index, String.valueOf(helper.getPosition() + 4))
                .setText(R.id.tv_nick, item.getNickname())
                .setText(R.id.tv_num,item.getExp()+"");

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

}