package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.BoxOpenRecordAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.BoxOpenRecordBean;
import com.konglianyuyin.mx.bean.PullRefreshBean;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.DealRefreshHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
//开奖记录
public class BoxOpenRecordWindow extends PopupWindow {
    private AdminHomeActivity mContext;
    private RecyclerView recyclerView;
    private SmartRefreshLayout mSm;
    private CommonModel commonModel;
    private RxErrorHandler mRxErrorHandler;
    private BoxOpenRecordAdapter mAdapter;
    private List<BoxOpenRecordBean.DataBean> mDataList = new ArrayList<>();
    private PullRefreshBean mPullRefreshBean = new PullRefreshBean();

    public BoxOpenRecordWindow(Activity context, View view, CommonModel commonModel, RxErrorHandler rxErrorHandler) {
        super(context);
        this.mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mRxErrorHandler = rxErrorHandler;
        View view1 = LayoutInflater.from(context).inflate(R.layout.box_open_record_window, null);
        recyclerView = view1.findViewById(R.id.recyclerview);
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
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
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

        mAdapter = new BoxOpenRecordAdapter(R.layout.box_open_record_item, mDataList);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
        actionAwardExchange();
        mSm.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPullRefreshBean.setLoardMore(mPullRefreshBean, mSm);//加载更多时的处理
                actionAwardExchange();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPullRefreshBean.setLoardMore(mPullRefreshBean, mSm);//加载更多时的处理
                actionAwardExchange();
            }
        });
    }

    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = mContext;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
    }

    //获取纪录
    private void actionAwardExchange() {
        RxUtils.loading(commonModel.getAwardRecordList(mPullRefreshBean.pageIndex), mContext)
                .subscribe(new ErrorHandleSubscriber<BoxOpenRecordBean>(mContext.mErrorHandler) {
                    @Override
                    public void onNext(BoxOpenRecordBean boxOpenRecordBean) {
                        List<BoxOpenRecordBean.DataBean> data = boxOpenRecordBean.getData();
                        new DealRefreshHelper<BoxOpenRecordBean.DataBean>().dealDataToUI(mSm, mAdapter, null, data, mDataList, mPullRefreshBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        new DealRefreshHelper<BoxOpenRecordBean.DataBean>().dealDataToUI(mSm, mAdapter, null, new ArrayList<>(), mDataList, mPullRefreshBean);
                    }
                });
    }
}
