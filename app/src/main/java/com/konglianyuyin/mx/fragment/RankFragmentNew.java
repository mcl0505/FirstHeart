package com.konglianyuyin.mx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.my.MyPersonalCenterActivity;
import com.konglianyuyin.mx.adapter.RankAdapterNew;
import com.konglianyuyin.mx.adapter.RankAdapterNewTop3;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.Rank;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class RankFragmentNew extends MyBaseArmFragment {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.tab_layout)
    SegmentTabLayout tabLayout;
    @BindView(R.id.rank_top3)
    RecyclerView rankTop3;
    @BindView(R.id.rank_list)
    RecyclerView recyclerView;
    @BindView(R.id.no_data)
    ImageView noData;
    Unbinder unbinder;
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合
    private List<String> dataTab = new ArrayList<>();                 //CommonTabLayout 所需数据集合
    private int id, type; //  ，1贡献榜2房间榜 type
    private String data = "1"; //1日榜2周榜3月榜
    private String uid; // 房间Id
    private RankAdapterNew rankAdapter; //适配器
    private RankAdapterNewTop3 rankAdapterNewTop3;
    private List<Rank.DataBean.TopBean> mList;

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_rank_sub_new);
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
        id = getArguments().getInt("id");
        type = getArguments().getInt("type");

        rankAdapterNewTop3 = new RankAdapterNewTop3(type, getActivity());
        LinearLayoutManager llm333 = new LinearLayoutManager(getActivity());
        llm333.setOrientation(LinearLayoutManager.VERTICAL);
        rankTop3.setLayoutManager(llm333);
        rankTop3.setAdapter(rankAdapterNewTop3);
        //点击除了前三的头像跳转个人主页
        rankAdapterNewTop3.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
            if (rankAdapterNewTop3.getData().get(position).getUser_id() == UserManager.getUser().getUserId()) {
                intent.putExtra("sign", 0);
                intent.putExtra("id", "");
            } else {
                intent.putExtra("sign", 1);
                intent.putExtra("id", rankAdapterNewTop3.getData().get(position).getUser_id() + "");
            }
            ArmsUtils.startActivity(intent);
        });

        //设置适配器
        rankAdapter = new RankAdapterNew(type, getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(rankAdapter);
        //点击除了前三的头像跳转个人主页
        rankAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
            if (rankAdapter.getData().get(position).getUser_id() == UserManager.getUser().getUserId()) {
                intent.putExtra("sign", 0);
                intent.putExtra("id", "");
            } else {
                intent.putExtra("sign", 1);
                intent.putExtra("id", rankAdapter.getData().get(position).getUser_id() + "");
            }
            ArmsUtils.startActivity(intent);
        });

        loadData(data);
        //标题资源
        titleRes.add("日榜");
        titleRes.add("周榜");
        titleRes.add("总榜");

        //设置数据
        tabLayout.setTabData(titleRes.toArray(new String[titleRes.size()]));
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                data = position + 1 + "";
                loadData(data);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public static RankFragmentNew getInstance(int tag, int type) {
        RankFragmentNew fragment = new RankFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putInt("id", tag);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadData(String date) {
        showDialogLoding();
        RxUtils.loading(commonModel.leaderboard(String.valueOf(id),
                date, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<Rank>(mErrorHandler) {
                    @Override
                    public void onNext(Rank rank) {
                        disDialogLoding();
                        try {
                            if (rank.getData().getTop().size() == 0) {
                                noData.setVisibility(View.VISIBLE);
                                rankTop3.setVisibility(View.GONE);
                            } else {
                                rankTop3.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (rank.getData().getOther().size() != 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                        }
                        rankAdapter.setNewData(rank.getData().getOther());
                        mList = rank.getData().getTop();
                        setTop(rank.getData().getTop());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                    }
                });
    }


    //设置前三的数据
    private void setTop(List<Rank.DataBean.TopBean> topBeanList) {
        if (topBeanList.get(2).getUser_id() == 0)
            topBeanList.remove(2);
        if (topBeanList.get(1).getUser_id() == 0)
            topBeanList.remove(1);
        if (topBeanList.get(0).getUser_id() == 0)
            topBeanList.remove(0);
        if (topBeanList.size() == 0) {
            noData.setVisibility(View.VISIBLE);
            rankTop3.setVisibility(View.GONE);
        } else {
            rankTop3.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
        rankAdapterNewTop3.setNewData(topBeanList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
