package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.BoxExchangeBean;

import java.util.List;

public class BoxDuiHuanAdapter extends BaseQuickAdapter<BoxExchangeBean.DataBean, BaseViewHolder> {


    public BoxDuiHuanAdapter(int layoutResId, @Nullable List<BoxExchangeBean.DataBean> data) {
        super(layoutResId, data);
    }

    private int selectPos = 0;

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public int getSelectPos() {
        return selectPos;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BoxExchangeBean.DataBean item) {
        try {
            LinearLayout ll_main = helper.getView(R.id.ll_main);
            if (helper.getPosition() == selectPos) {
                ll_main.setBackgroundDrawable(mContext.getDrawable(R.drawable.shape_duihuan_bg));
            } else {
                ll_main.setBackgroundColor(mContext.getColor(R.color.translant));
            }
            helper.setText(R.id.tv_num, item.getScore() + "");
            helper.setText(R.id.tv_name, item.getName());
            Glide.with(mContext).load(item.getShow_img()).into((ImageView) helper.getView(R.id.iv_cover));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
