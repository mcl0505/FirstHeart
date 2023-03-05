package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.TreasureBoxAdapter;
import com.konglianyuyin.mx.adapter.WonPopupAdapter;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.bean.PullRefreshBean;
import com.konglianyuyin.mx.service.CommonModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

public class WonPopupWindow extends PopupWindow {
    private AdminHomeActivity mContext;
    private RecyclerView recyclerView;
    private SmartRefreshLayout mSm;
    private CommonModel commonModel;
    private RxErrorHandler mRxErrorHandler;
    private TreasureBoxAdapter mAdapter;
    private PullRefreshBean mPullRefreshBean = new PullRefreshBean();

    public WonPopupWindow(Activity context, View view, CommonModel commonModel, RxErrorHandler rxErrorHandler, List<OpenBoxBean.DataBean.AwardListBean> list) {
        super(context);
        this.mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mRxErrorHandler = rxErrorHandler;
        View view1 = LayoutInflater.from(context).inflate(R.layout.won_window, null);
        recyclerView = view1.findViewById(R.id.recyclerview);
        TextView price = view1.findViewById(R.id.price);
        ImageView iv_close = view1.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        mSm = view1.findViewById(R.id.sm);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view1);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //设置透明
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        this.setWidth(view.getMeasuredWidth());
//        this.setHeight(view.getMeasuredHeight() - 100);
        context.getWindow().setAttributes(params);
        mAdapter = new TreasureBoxAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(list);
        int allPrice = 0;
        for (OpenBoxBean.DataBean.AwardListBean bean:list){
            int p = bean.getNum()*Integer.parseInt(bean.getPrice());
            allPrice = p + allPrice;
        }

        price.setText(allPrice+"");
    }

    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = mContext;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
    }
}
