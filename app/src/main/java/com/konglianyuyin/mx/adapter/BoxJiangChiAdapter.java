package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.BoxJiangChiBean;

import java.util.List;

public class BoxJiangChiAdapter extends BaseQuickAdapter<BoxJiangChiBean.DataBean, BaseViewHolder> {


    public BoxJiangChiAdapter(int layoutResId, @Nullable List<BoxJiangChiBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BoxJiangChiBean.DataBean item) {
        try {
            helper.setText(R.id.tv_num, item.getPrice());
            helper.setText(R.id.tv_name, item.getName());
            Glide.with(mContext).load(item.getShow_img()).into((ImageView) helper.getView(R.id.iv_cover));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
