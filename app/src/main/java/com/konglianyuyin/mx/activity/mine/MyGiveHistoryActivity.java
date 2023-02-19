package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.ZengSongHisAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.ZengSongHisBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class MyGiveHistoryActivity extends MyBaseArmActivity {
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rightTitle)
    TextView rightTitle;
    @BindView(R.id.rightConfirm)
    ShapeTextView rightConfirm;
    @BindView(R.id.toolbar_right)
    RelativeLayout toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.srl_view)
    SmartRefreshLayout srlView;
    @Inject
    CommonModel commonModel;

    ZengSongHisAdapter zengSongHisAdapter;

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
        return R.layout.activity_give_my_history;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbarTitle.setText("赠送记录");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyview();
        srlView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                loadData();
            }
        });
        loadData();
    }

    List<ZengSongHisBean.DataBean> dataList = new ArrayList<>();

    private void initRecyview() {
        if (zengSongHisAdapter == null)
            zengSongHisAdapter = new ZengSongHisAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        zengSongHisAdapter.setNewData(dataList);
        listView.setAdapter(zengSongHisAdapter);
    }

    private int page = 1;

    private void loadData() {
        RxUtils.loading(commonModel.zhengsonglog(page + ""))
                .subscribe(new ErrorHandleSubscriber<ZengSongHisBean>(mErrorHandler) {
                    @Override
                    public void onNext(ZengSongHisBean zengSongHisBean) {
                        try {
                            srlView.finishLoadMore();
                            srlView.finishRefresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (page == 1) {
                            dataList.clear();
                        }
                        dataList.addAll(zengSongHisBean.getData());
                        zengSongHisAdapter.setNewData(dataList);
                        zengSongHisAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
