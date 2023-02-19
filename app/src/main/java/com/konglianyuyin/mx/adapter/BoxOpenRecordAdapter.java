package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.BoxOpenRecordBean;

import java.util.List;

public class BoxOpenRecordAdapter extends BaseQuickAdapter<BoxOpenRecordBean.DataBean, BaseViewHolder> {
    public BoxOpenRecordAdapter(int layoutResId, @Nullable List<BoxOpenRecordBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BoxOpenRecordBean.DataBean item) {
        helper.setText(R.id.record, item.getName());
        helper.setText(R.id.num, "x" + item.getNum());
        helper.setText(R.id.time, item.getAddtime());
        Glide.with(mContext).load(item.getShow_img()).into((ImageView) helper.getView(R.id.iv_img));
    }
}
