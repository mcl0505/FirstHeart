package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.CashHisAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CashHis;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 作者:sgm
 * 描述:粉丝，关注
 */
public class CashHisFragment extends MyBaseArmFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smart)
    SmartRefreshLayout refreshLayout;

    @Inject
    CommonModel commonModel;
    private int page = 1;

    private CashHisAdapter friendAdapter;

    private int id;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_item_fans);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        friendAdapter = new CashHisAdapter(id);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(friendAdapter);

        if (id == 0) {
            loadDuihuan();
        } else {
            loadCash();
        }

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            if (id == 0) {
                loadDuihuan();
            } else {
                loadCash();
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {

            page++;
            if (id == 0) {
                loadDuihuan();
            } else {
                loadCash();
            }
        });

    }

    /**
     * 兑换记录
     */
    private void loadDuihuan() {
        RxUtils.loading(commonModel.exchange_log(String.valueOf(UserManager.getUser().getUserId()),
                page + ""), this)
                .subscribe(new ErrorHandleSubscriber<CashHis>(mErrorHandler) {
                    @Override
                    public void onNext(CashHis userFriend) {
                        if (page == 1) {
                            friendAdapter.setNewData(userFriend.getData());
                            refreshLayout.finishRefresh();
                        } else {
                            friendAdapter.addData(userFriend.getData());
                            refreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * 提现记录
     */

    private void loadCash() {
        RxUtils.loading(commonModel.tixian_log(String.valueOf(UserManager.getUser().getUserId()), page + ""), this)
                .subscribe(new ErrorHandleSubscriber<CashHis>(mErrorHandler) {
                    @Override
                    public void onNext(CashHis userFriend) {
                        if (page == 1) {
                            friendAdapter.setNewData(userFriend.getData());
                        } else {
                            friendAdapter.addData(userFriend.getData());
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
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
    public void setData(@Nullable Object data) {

    }

    public static CashHisFragment getInstance(int tag) {
        CashHisFragment fragment = new CashHisFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

}
