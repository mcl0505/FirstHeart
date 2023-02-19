package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.HomeRecomAdapter;
import com.konglianyuyin.mx.adapter.OnItemClickListener;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.HeaderViewPagerFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CategorRoomBean;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.PullRefreshBean;
import com.konglianyuyin.mx.bean.RecommenRoomBean;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.FANHUIZHUYE;
import static com.konglianyuyin.mx.utils.Constant.XUANFUYINCANG;

/**
 * 作者:sgm
 * 描述:首页分类
 */
public class RecommendHomeFragment extends HeaderViewPagerFragment {

    @Inject
    CommonModel commonModel;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private int id;
    private int index;
//    private HomeTopAdapter homeTopAdapter;
    private HomeRecomAdapter adapter;
    Unbinder unbinder;
    private CategorRoomBean categorRoomBean;

    List<RecommenRoomBean.DataBean> mDataList = new ArrayList<>();

    PullRefreshBean mPullRefreshBean = new PullRefreshBean();//下拉刷新帮助类
    private String old_id = "";

    //    View mRootView;
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
        View mRootView = null;
        if (mRootView == null) {
            mRootView = ArmsUtils.inflate(getActivity(), R.layout.fragment_recommend);
            unbinder = ButterKnife.bind(this, mRootView);
        }
        return mRootView;
    }

    @Override
    protected void visibleToLoadData() {
        super.visibleToLoadData();
        visibleToloadData();
    }

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        if(mRootView == null){
//            mRootView = view;
//        }
//        super.onViewCreated(view, savedInstanceState);
//        isPrepared = true;
//        onVisible();
//    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        id = getArguments().getInt("index");
        categorRoomBean = (CategorRoomBean) getArguments().getSerializable("categorRoomBean");

        mPullRefreshBean.setDisableLoadMore(true);

    }

    protected void visibleToloadData() {
        adapter = new HomeRecomAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        if (index == 0){
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        }else {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//
//        }

        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 8, 8);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        mRecyclerView.addItemDecoration(decoration);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false));


        mRecyclerView.setAdapter(adapter);


        if (id == 0) {
            loadRecommendData(null);
        } else {
            loadData(new SmartRefreshLayout(getActivity()));
        }
        adapter.setOnItemClickListener(new OnItemClickListener<RecommenRoomBean.DataBean>() {
            @Override
            public void onItemClick(RecommenRoomBean.DataBean bean, int position) {
                enterData(bean.getUid(), "", commonModel, 1, bean.getRoom_cover());
            }
        });
    }



    /**
     * 头部数据
     */
    private void loadData(SmartRefreshLayout refreshLayout) {
        RxUtils.loading(commonModel.getrecommendroom(id, mPullRefreshBean.pageIndex), this)
                .subscribe(new ErrorHandleSubscriber<RecommenRoomBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecommenRoomBean categorRoomBean) {


                        //设置数据，包括加载更多，下拉刷新，无数据的情况都处理过了
//                        new DealRefreshHelper<RecommenRoomBean.DataBean>().dealDataToUI(refreshLayout, homeRecommendAdapter, null, list, mDataList, mPullRefreshBean);


                        if(mPullRefreshBean.pageIndex==1){
                            adapter.getList().clear();
                            if(refreshLayout!=null){
                                refreshLayout.finishRefresh();
                            }
                        }else{
                            if(refreshLayout!=null){
                                refreshLayout.finishLoadMore();
                            }
                        }
                        adapter.getList().addAll(categorRoomBean.getData());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        //设置数据，包括加载更多，下拉刷新，无数据的情况都处理过了
//                        new DealRefreshHelper<RecommenRoomBean.DataBean>().dealDataToUI(refreshLayout, homeRecommendAdapter, null, new ArrayList<>(), mDataList, mPullRefreshBean);

                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        }
                    }
                });
    }

    /**
     * 推荐的数据单独走接口
     */
    private void loadRecommendData(SmartRefreshLayout refreshLayout) {
        RxUtils.loading(commonModel.is_top(""), this)
                .subscribe(new ErrorHandleSubscriber<RecommenRoomBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecommenRoomBean categorRoomBean) {
                        adapter.setList(categorRoomBean.getData());
                        adapter.notifyDataSetChanged();
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                    }
                });
    }



    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    public void onRefresh(final SmartRefreshLayout refreshLayout) {

        mPullRefreshBean.setRefresh(mPullRefreshBean, refreshLayout);//下拉刷新时的处理

        if (id == 0) {
            loadRecommendData(refreshLayout);
        } else {
            loadData(refreshLayout);
        }

    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     */
    public void onLoadMore(final SmartRefreshLayout refreshLayout) {

        mPullRefreshBean.setLoardMore(mPullRefreshBean, refreshLayout);//加载更多时的处理

        loadData(refreshLayout);

    }

    public void setDisableLoadMore(boolean disableLoadMore) {
        mPullRefreshBean.setDisableLoadMore(disableLoadMore);
    }

    public void setDisableRefresh(boolean disableRefresh) {
        mPullRefreshBean.setDisableRefresh(disableRefresh);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public static RecommendHomeFragment getInstance(int index,int tag, CategorRoomBean categorRoomBean) {
        RecommendHomeFragment fragment = new RecommendHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", tag);
        bundle.putInt("index", index);
        bundle.putSerializable("categorRoomBean", categorRoomBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }


    public void setCategory(CategorRoomBean categorRoomBean) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (FANHUIZHUYE.equals(tag)) {//显示悬浮窗
            EnterRoom enterRoom = event.getEnterRoom();
            old_id = String.valueOf(enterRoom.getRoom_info().get(0).getUid());
        } else if (XUANFUYINCANG.equals(tag)) {//显示悬浮窗
            old_id = "";
        }
    }
}
