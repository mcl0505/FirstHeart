package com.konglianyuyin.mx.popup;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.RechargePopAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.GoodsListBean;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.LogUtils;
import com.konglianyuyin.mx.utils.ScreenDimenUtil;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.konglianyuyin.mx.view.FullyGridLayoutManager;

import java.util.List;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class RechargePopWindow extends PopupWindow implements View.OnClickListener {

    TextView tvBalance;
    ImageView ivDiamond;
    RecyclerView rvPrice;
    ImageView ivAliCheck;
    LinearLayout llAli;
    ImageView ivWxCheck;
    LinearLayout llWx;
    TextView tvRecharge;

    private AdminHomeActivity mContext;
    private CommonModel commonModel;
    private RechargePopAdapter adapter;
    private int checkedPosition = -1;
    private int payType = 1;
    private String mPrice;


    public RechargePopWindow(AdminHomeActivity context,CommonModel commonModel) {
        super(context);

        this.mContext = context;
        this.commonModel = commonModel;
        init();
    }

    private void init() {
        View mView = View.inflate(mContext, R.layout.layout_pop_recharge, null);

        tvBalance = mView.findViewById(R.id.tv_balance);
        rvPrice = mView.findViewById(R.id.rv_price);
        ivAliCheck = mView.findViewById(R.id.iv_ali_check);
        llAli = mView.findViewById(R.id.ll_ali);
        ivWxCheck = mView.findViewById(R.id.iv_wx_check);
        llWx = mView.findViewById(R.id.ll_wx);
        tvRecharge = mView.findViewById(R.id.tv_recharge);

        llAli.setOnClickListener(this);
        llWx.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);

        initData();

        setFocusable(true);
        // 导入布局
        this.setContentView(mView);
        setWidth(ScreenDimenUtil.getInstance().getScreenWdith());
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        setAlpha(0.7f);
    }

    private void initData() {
        adapter = new RechargePopAdapter();
        RxUtils.loading(commonModel.getGoodsList(UserManager.getUser().getNewtoken(),String.valueOf(UserManager.getUser().getUserId())), mContext)
                .subscribe(new ErrorHandleSubscriber<GoodsListBean>(mContext.mErrorHandler) {
                    @Override
                    public void onNext(GoodsListBean goodsList) {
                        List<GoodsListBean.DataBean.GoodslistBean> data = goodsList.getData().getGoodslist();
                        data.add(new GoodsListBean.DataBean.GoodslistBean());
                        tvBalance.setText(goodsList.getData().getCnylingshi());
                        rvPrice.setLayoutManager(new FullyGridLayoutManager(mContext,3));
                        rvPrice.setAdapter(adapter);
                        adapter.setNewData(data);
                    }
                });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<GoodsListBean.DataBean.GoodslistBean> data = adapter.getData();
                LogUtils.e("TAG","onItemClick"+data.get(position).isSelect());
                if(!data.get(position).isSelect()){
                    checkedPosition = position;
                    for(int i =0; i<data.size();i++){
                        data.get(i).setSelect(false);
                    }

                    data.get(position).setSelect(true);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        adapter.setInputPriceListener(new RechargePopAdapter.onInputPriceListener() {
            @Override
            public void afterTextChanged(String price) {
                List<GoodsListBean.DataBean.GoodslistBean> data = adapter.getData();
                mPrice = price;
                if(checkedPosition != data.size()-1){
                    if(checkedPosition != -1){
                        data.get(checkedPosition).setSelect(false);
                        adapter.notifyItemChanged(checkedPosition);
                    }
                    checkedPosition = data.size()-1;
                }
            }
        });
    }

    private void setAlpha(float alpha) {
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = alpha;
        mContext.getWindow().setAttributes(params);
    }

    @Override
    public void dismiss() {
        setAlpha(1);
        super.dismiss();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ali:
                if(payType != 1){
                    payType = 1;
                    ivAliCheck.setVisibility(View.VISIBLE);
                    ivWxCheck.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_wx:
                if(payType != 2){
                    payType = 2;
                    ivAliCheck.setVisibility(View.GONE);
                    ivWxCheck.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_recharge:
                if(checkedPosition == -1){
                    ToastUtil.showToast(mContext,"未选择充值金额");
                    return;
                }
                recharge();
                break;
        }
    }

    private void recharge() {
        String goodId = "";
        List<GoodsListBean.DataBean.GoodslistBean> data = adapter.getData();
        if(checkedPosition == data.size() -1){
            if(TextUtils.isEmpty(mPrice)){
                ToastUtil.showToast(mContext,"充值金额未填写");
                return;
            }else if(mPrice.contains(".")){
                ToastUtil.showToast(mContext,"充值金额必须为整数");
                return;
            }
        }else{
            mPrice = data.get(checkedPosition).getPrice();
            goodId = data.get(checkedPosition).getId();
        }
        LogUtils.e("TAG","inputPrice"+ mPrice);
        if(listener != null){
            listener.recharge(goodId,mPrice, payType);
            dismiss();
        }

    }

    private RechargePopWindowListener listener;

    public void setRechargePopWindowListener(RechargePopWindowListener listener){
        this.listener = listener;
    }

    public interface RechargePopWindowListener{
        void recharge(String goodId,String price,int payType);
    }
}
