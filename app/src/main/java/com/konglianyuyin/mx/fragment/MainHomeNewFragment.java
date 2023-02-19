package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.HomeMuilteAdapter;
import com.konglianyuyin.mx.adapter.MyPagerAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.bean.CategorRoomBean;
import com.konglianyuyin.mx.bean.HomeMultipleItem;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class MainHomeNewFragment  extends MyBaseArmFragment implements ImmersionOwner {

    @Inject
    public CommonModel commonModel;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private HomeMuilteAdapter mAdapter;

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_home_new);
    }

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
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
           loadCategory();
        });
        refreshLayout.setEnableLoadMore(false);
        loadCategory();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    /**
     * 类别
     */
    private void loadCategory() {
        RxUtils.loading(commonModel.room_categories(), this)
                .subscribe(new ErrorHandleSubscriber<CategorRoomBean>(mErrorHandler) {
                    @Override
                    public void onNext(CategorRoomBean categorRoomBean) {
                        List<CategorRoomBean.DataBean> data = categorRoomBean.getData();
                        List<HomeMultipleItem> items = new ArrayList<>();
                        items.add(new HomeMultipleItem(HomeMultipleItem.Layout_Top,""));
                        items.add(new HomeMultipleItem(HomeMultipleItem.Layout_Banner,""));
                        items.add(new HomeMultipleItem(HomeMultipleItem.Layout_Boy,data.get(0).getId()+""));
                        items.add(new HomeMultipleItem(HomeMultipleItem.Layout_Girl,data.get(1).getId()+""));
                        mAdapter = new HomeMuilteAdapter(mContext,items,MainHomeNewFragment.this);
                        mRecyclerView.setAdapter(mAdapter);
                        refreshLayout.finishRefresh();
                    }
                });
    }
}
