package com.konglianyuyin.mx.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.OfficeListAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.OffiMessageBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class MessageOfficeActivity extends MyBaseArmFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;

    @Inject
    CommonModel commonModel;
    private int page = 1;

    private OfficeListAdapter officeListAdapter;

//    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerCommonComponent.builder()
//                .appComponent(appComponent)
//                .commonModule(new CommonModule())
//                .build()
//                .inject(this);
//    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        officeListAdapter = new OfficeListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(officeListAdapter);

        loadData();

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            loadData();
        });

//        setToolbarRightText("清空消息", v -> {
//            if(officeListAdapter.getData() == null || officeListAdapter.getData().size()==0){
//                showToast("暂无消息");
//                return;
//            }
//            RxUtils.loading(commonModel.clear_message(String.valueOf(UserManager.getUser().getUserId())), this)
//                    .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
//                        @Override
//                        public void onNext(BaseBean userFriend) {
//                            loadData();
//                        }
//                    });
//        },R.color.textcolor);

        officeListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!TextUtils.isEmpty(officeListAdapter.getData().get(position).getUrl())) {
                Intent intent = new Intent(getActivity(), BaseWebActivity.class);
                intent.putExtra("url", officeListAdapter.getData().get(position).getUrl());
                intent.putExtra("name", "");
                ArmsUtils.startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void loadData() {
        RxUtils.loading(commonModel.official_message(UserManager.getUser().getUserId()+"", page + ""), this)
                .subscribe(new ErrorHandleSubscriber<OffiMessageBean>(mErrorHandler) {
                    @Override
                    public void onNext(OffiMessageBean userFriend) {
                        if (page == 1) {
                            officeListAdapter.setNewData(userFriend.getData());
                        }else {
                            officeListAdapter.getData().addAll(userFriend.getData());
                            officeListAdapter.notifyDataSetChanged();
                        }
                        try {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent();
                        intent.setAction("has_read_office");
                        getActivity().sendBroadcast(intent);
//                        EventBus.getDefault().post(new FirstEvent("指定发送", Constant.SHUAXINGUANFANGXIAOXI));
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.activity_message_office);
    }

}
