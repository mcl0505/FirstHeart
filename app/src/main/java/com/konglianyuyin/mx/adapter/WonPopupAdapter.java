package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.OpenBoxBean;

import java.util.List;

public class WonPopupAdapter extends BaseQuickAdapter<OpenBoxBean.DataBean.AwardListBean, BaseViewHolder> {


    public WonPopupAdapter(int layoutResId, @Nullable List<OpenBoxBean.DataBean.AwardListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OpenBoxBean.DataBean.AwardListBean item) {
        helper.setText(R.id.record, item.getName());
        helper.setText(R.id.num, "x" + item.getNum());
        helper.setText(R.id.price_tv, item.getPrice()+"钻");
        Glide.with(mContext).load(item.getShow_img()).into((ImageView) helper.getView(R.id.iv_img));
    }
}
