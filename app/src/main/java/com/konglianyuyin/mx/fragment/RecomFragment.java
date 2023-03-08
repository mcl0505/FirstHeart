package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.HomeBoyAdapter;
import com.konglianyuyin.mx.adapter.HomeRecommendAdapter;
import com.konglianyuyin.mx.adapter.HomeTopAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.HeaderViewPagerFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CategorRoomBean;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.PullRefreshBean;
import com.konglianyuyin.mx.bean.RecommenRoomBean;
import com.konglianyuyin.mx.bean.StartLoftBean;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.MyGridView;
import com.konglianyuyin.mx.view.MyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.FANHUIZHUYE;
import static com.konglianyuyin.mx.utils.Constant.XUANFUYINCANG;

/**
 * 作者:sgm
 * 描述:首页分类
 */
public class RecomFragment extends HeaderViewPagerFragment {


    //    @BindView(R.id.recyclerView)
//    public XRecyclerView recyclerView;
//    private RecomAdapter adapter;
    @Inject
    CommonModel commonModel;
    @BindView(R.id.myList1)
    MyListView myList1;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.myList2)
    MyListView myList2;
    @BindView(R.id.myList3)
    MyListView myList3;
    //    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.myGrid)
    MyGridView myGrid;
    Unbinder unbinder;
    @BindView(R.id.textGirl)
    TextView textGirl;
    @BindView(R.id.textBoy)
    TextView textBoy;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.imgFresh)
    ImageView imgFresh;

    private int id;
    private HomeTopAdapter homeTopAdapter;
    private HomeRecommendAdapter homeRecommendAdapter;
    private HomeRecommendAdapter homeRecommendAdapter3;
    private HomeBoyAdapter homeBoyAdapter;

    private StartLoftBean girlData;
    private StartLoftBean boyData;
    private CategorRoomBean categorRoomBean;
    private String old_id = "";

//    /**
//     * 是否为可见状态
//     */
//    protected boolean isVisible;
//    /**
//     * 是否初始视图完成
//     */
//    private boolean isPrepared;

    List<RecommenRoomBean.DataBean> mDataList = new ArrayList<>();

    PullRefreshBean mPullRefreshBean = new PullRefreshBean();//下拉刷新帮助类

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
            mRootView = ArmsUtils.inflate(getActivity(), R.layout.fragment_recom);
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
        textGirl.setSelected(true);
        id = getArguments().getInt("id");
        categorRoomBean = (CategorRoomBean) getArguments().getSerializable("categorRoomBean");

//        if (id == 0) {
//            refreshLayout.setEnableLoadmore(false);
//        } else {
//            refreshLayout.setEnableLoadmore(true);
//            refreshLayout.setOnLoadmoreListener(refreshlayout -> {
//                page++;
//                loadData();
//            });
//        }
//        refreshLayout.setOnRefreshListener(refreshlayout -> {
//            page = 1;
//            if (id == 0) {
//                loadRecommendData();
//            } else {
//                loadData();
//            }
//        });

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
////            onInvisible();
//        }
//    }

    /**
     * 可见做懒加载
     */
//    private void onVisible() {
//        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
//        if (isVisible && isPrepared) {
//            visibleToloadData();
//            //数据加载完毕,恢复标记,防止重复加载
//            isVisible = false;
//            isPrepared = false;
//        }
//    }
    protected void visibleToloadData() {
        homeTopAdapter = new HomeTopAdapter(getActivity());
        myList1.setAdapter(homeTopAdapter);
        homeRecommendAdapter = new HomeRecommendAdapter(getActivity());
//        myList1.setAdapter(homeRecommendAdapter);
//        mDataList = homeRecommendAdapter.getList_adapter();
//        myList2.setAdapter(homeRecommendAdapter);
//        homeRecommendAdapter3 = new HomeRecommendAdapter(getActivity());
//        myList3.setAdapter(homeRecommendAdapter3);
        homeBoyAdapter = new HomeBoyAdapter(getActivity(), categorRoomBean);
        //隐藏C位推荐
//        myGrid.setAdapter(homeBoyAdapter);
        if (id == 0) {
//            ll1.setVisibility(View.VISIBLE);
//            ll2.setVisibility(View.VISIBLE);
            //隐藏C位推荐
//            ll3.setVisibility(View.VISIBLE);
            loadRecommendData(null);
            loadGirlData();
        } else {
//            ll1.setVisibility(View.GONE);
//            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            loadData(new SmartRefreshLayout(getActivity()));
        }
        myList1.setOnItemClickListener((parent, view, position, id) -> {
            RxUtils.loading(commonModel.get_user_info(String.valueOf(UserManager.getUser().getUserId())), this)
                    .subscribe(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                        @Override
                        public void onNext(UserBean userBean) {
//                            if (ExtConfig.isOpenRoomNeedRealName){
//
//                            }
                        }
                    });

        });
//        myList2.setOnItemClickListener((parent, view, position, id) -> {
//            enterData(homeRecommendAdapter.getList_adapter().get(position).getUid(), "", commonModel, 1, homeRecommendAdapter.getList_adapter().get(position).getRoom_cover());
//        });
//        myList3.setOnItemClickListener((parent, view, position, id) -> {
//            enterData(homeRecommendAdapter3.getList_adapter().get(position).getUid(), "", commonModel, 1, homeRecommendAdapter3.getList_adapter().get(position).getRoom_cover());
//        });
        myGrid.setOnItemClickListener((parent, view, position, id) -> {
            enterData(String.valueOf(homeBoyAdapter.getList_adapter().get(position).getUid()), "", commonModel, 1, homeBoyAdapter.getList_adapter().get(position).getRoom_cover());
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
                        if(mPullRefreshBean.pageIndex==1){
                            homeTopAdapter.getList_adapter().clear();
                            if(refreshLayout!=null){
                                refreshLayout.finishRefresh();
                            }
                        }else{
                            if(refreshLayout!=null){
                                refreshLayout.finishLoadMore();
                            }
                        }
                        homeTopAdapter.getList_adapter().addAll(categorRoomBean.getData());
                        homeTopAdapter.notifyDataSetChanged();

                        //List<RecommenRoomBean.DataBean> list = categorRoomBean.getData();

                        //设置数据，包括加载更多，下拉刷新，无数据的情况都处理过了
                        //new DealRefreshHelper<RecommenRoomBean.DataBean>().dealDataToUI(refreshLayout, homeRecommendAdapter, null, list, mDataList, mPullRefreshBean);

//                        if (page == 0) {
//                            if (refreshLayout != null) {
//                                refreshLayout.finishRefresh();
//                            }
//                            homeRecommendAdapter.getList_adapter().clear();
//                            homeRecommendAdapter.getList_adapter().addAll(categorRoomBean.getData());
//                            homeRecommendAdapter.notifyDataSetChanged();
//                        } else {
//                            if (refreshLayout != null) {
//                                refreshLayout.finishLoadMore();
//                            }
//                            homeRecommendAdapter.getList_adapter().addAll(categorRoomBean.getData());
//                            homeRecommendAdapter.notifyDataSetChanged();
//                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        }
                        //设置数据，包括加载更多，下拉刷新，无数据的情况都处理过了
                        //new DealRefreshHelper<RecommenRoomBean.DataBean>().dealDataToUI(refreshLayout, homeRecommendAdapter, null, new ArrayList<>(), mDataList, mPullRefreshBean);
//                        if (refreshLayout != null) {
//                            refreshLayout.finishRefresh();
//                            refreshLayout.finishLoadMore();
//                        }
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
                        homeTopAdapter.getList_adapter().clear();
                        homeTopAdapter.getList_adapter().addAll(categorRoomBean.getData());
                        homeTopAdapter.notifyDataSetChanged();
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

    public static RecomFragment getInstance(int tag, CategorRoomBean categorRoomBean) {
        RecomFragment fragment = new RecomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", tag);
        bundle.putSerializable("categorRoomBean", categorRoomBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }

    //推荐
    public void setPopularData(RecommenRoomBean categorRoomBean) {
        if (homeRecommendAdapter != null) {
            homeRecommendAdapter.getList_adapter().clear();
            homeRecommendAdapter.getList_adapter().addAll(categorRoomBean.getData());
            homeRecommendAdapter.notifyDataSetChanged();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (homeRecommendAdapter != null) {
                        homeRecommendAdapter.getList_adapter().clear();
                        homeRecommendAdapter.getList_adapter().addAll(categorRoomBean.getData());
                        homeRecommendAdapter.notifyDataSetChanged();
                    }
                }
            }, 1000);
        }
    }

    //密聊
    public void setSecretData(RecommenRoomBean categorRoomBean) {
        if (homeRecommendAdapter3 != null) {
            homeRecommendAdapter3.getList_adapter().clear();
            homeRecommendAdapter3.getList_adapter().addAll(categorRoomBean.getData());
            homeRecommendAdapter3.notifyDataSetChanged();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (homeRecommendAdapter3 != null) {
                        homeRecommendAdapter3.getList_adapter().clear();
                        homeRecommendAdapter3.getList_adapter().addAll(categorRoomBean.getData());
                        homeRecommendAdapter3.notifyDataSetChanged();
                    }
                }
            }, 1000);
        }
    }


    public void loadBoyData() {
        //男生数据
        RxUtils.loading(commonModel.star_loft(1), this)
                .subscribe(new ErrorHandleSubscriber<StartLoftBean>(mErrorHandler) {
                    @Override
                    public void onNext(StartLoftBean categorRoomBean) {
                        homeBoyAdapter.getList_adapter().clear();
                        homeBoyAdapter.getList_adapter().addAll(categorRoomBean.getData());
                        homeBoyAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void loadGirlData() {
        //女生数据
        RxUtils.loading(commonModel.star_loft(2), this)
                .subscribe(new ErrorHandleSubscriber<StartLoftBean>(mErrorHandler) {
                    @Override
                    public void onNext(StartLoftBean categorRoomBean) {
                        homeBoyAdapter.getList_adapter().clear();
                        homeBoyAdapter.getList_adapter().addAll(categorRoomBean.getData());
                        homeBoyAdapter.notifyDataSetChanged();
                    }
                });
    }


    @OnClick({R.id.textGirl, R.id.textBoy, R.id.imgFresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textGirl:
                textBoy.setSelected(false);
                textGirl.setSelected(true);
                loadGirlData();
                break;
            case R.id.textBoy:
                textBoy.setSelected(true);
                textGirl.setSelected(false);
                loadBoyData();
                break;
            case R.id.imgFresh:
                if (textBoy.isSelected()) {
                    loadBoyData();
                } else {
                    loadGirlData();
                }
                break;
            default:
        }
    }

    public void setCategory(CategorRoomBean categorRoomBean) {

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
