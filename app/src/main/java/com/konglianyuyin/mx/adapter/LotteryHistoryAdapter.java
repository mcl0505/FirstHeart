package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.LotteryHistoryBean;

public class LotteryHistoryAdapter extends BaseQuickAdapter<LotteryHistoryBean.DataBean, BaseViewHolder> {
    public LotteryHistoryAdapter() {
        super(R.layout.item_lottery_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LotteryHistoryBean.DataBean item) {
        helper.setText(R.id.tv_level,item.getTitle());
        helper.setText(R.id.tv_numbers,item.getSelectpk());
        helper.setText(R.id.tv_lottery_number,item.getWinpk());
        helper.setText(R.id.tv_is_lottery,item.getState() == 1 ? "中奖":"不中奖");
        helper.setText(R.id.tv_time,item.getAddtime());
    }
}
