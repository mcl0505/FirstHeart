package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.GoodsListBean;
import com.konglianyuyin.mx.utils.LogUtils;

public class RechargePopAdapter extends BaseQuickAdapter<GoodsListBean.DataBean.GoodslistBean, BaseViewHolder> {


    public RechargePopAdapter() {
        super(R.layout.item_recharge_pop);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GoodsListBean.DataBean.GoodslistBean item) {
        TextView tvMoney = helper.getView(R.id.tv_money);
        TextView tvPrice = helper.getView(R.id.tv_price);
        EditText edInputPrice = helper.getView(R.id.ed_input_price);
        LinearLayout llTop = helper.getView(R.id.ll_top);


        if(!TextUtils.isEmpty(item.getPrice())){
            edInputPrice.setVisibility(View.GONE);
            tvPrice.setVisibility(View.VISIBLE);
            llTop.setVisibility(View.VISIBLE);
            tvMoney.setText(item.getMizuan()+"");
            tvPrice.setText("￥"+item.getPrice());

            if(item.isSelect()){
                tvMoney.setTextColor(mContext.getResources().getColor(R.color.color_9d8cff));
                tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_9d8cff));
            }else{
                tvMoney.setTextColor(mContext.getResources().getColor(R.color.font_333333));
                tvPrice.setTextColor(mContext.getResources().getColor(R.color.font_333333));
            }
        }else{
            edInputPrice.setVisibility(View.VISIBLE);
            tvPrice.setVisibility(View.GONE);
            llTop.setVisibility(View.GONE);

            if(edInputPrice.getTag() != null && edInputPrice.getTag() instanceof TextWatcher){
                edInputPrice.removeTextChangedListener((TextWatcher)edInputPrice.getTag());
            }

            edInputPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e("TAG","input click");
                }
            });

             TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(listener != null){
                        listener.afterTextChanged(s.toString());
                    }
                }
            };

            edInputPrice.addTextChangedListener(textWatcher);
            edInputPrice.setTag(textWatcher);
        }
    }

    private onInputPriceListener listener;

    public void setInputPriceListener(onInputPriceListener listener){
        this.listener = listener;
    }

    public interface onInputPriceListener{
        void afterTextChanged(String price);
    }
}
