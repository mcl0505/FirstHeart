package com.konglianyuyin.mx.activity.room;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.LotteryHistoryAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.LotteryHistoryBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class LotteryHistoryActivity extends MyBaseArmActivity {


    @Inject
    CommonModel commonModel;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.sml_history)
    SmartRefreshLayout smlHistory;

    private int page = 1;
    private LotteryHistoryAdapter adapter;
    private List<LotteryHistoryBean.DataBean> list;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_lottery_history;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        getLotteryHistoryData();
    }

    private void initRecyclerView() {
        adapter = new LotteryHistoryAdapter();
        list = new ArrayList<>();
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);
        adapter.setNewData(list);

        smlHistory.setEnableRefresh(false);
        smlHistory.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getLotteryHistoryData();
            }
        });
    }

    private void getLotteryHistoryData() {
        RxUtils.loading(commonModel.lotteryHistory(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", page), this)
                .subscribe(new ErrorHandleSubscriber<LotteryHistoryBean>(mErrorHandler) {
                    @Override
                    public void onNext(LotteryHistoryBean lotteryBean) {
                        if(lotteryBean.getData()!=null && lotteryBean.getData().size()>0){
                            list.addAll(lotteryBean.getData());
                            smlHistory.finishLoadMore();
                            adapter.notifyDataSetChanged();
                        }else{
                            smlHistory.finishLoadMoreWithNoMoreData();
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                        toast(t.getMessage());
                    }
                });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}