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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.my.MyPersonalCenterActivity;
import com.konglianyuyin.mx.adapter.RankAdapterN;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.Rank;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.LogUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class RankFragmentN extends MyBaseArmFragment {
    @Inject
    CommonModel commonModel;
    Unbinder unbinder;

    @BindView(R.id.btn_top1)
    ImageView btn_top1;
    @BindView(R.id.btn_top2)
    ImageView btn_top2;
    @BindView(R.id.btn_top3)
    ImageView btn_top3;

    @BindView(R.id.iv_top_one)
    RoundedImageView iv_top_one;
    @BindView(R.id.nick_top_one)
    TextView nick_top_one;
    @BindView(R.id.num_top_one)
    TextView num_top_one;
    @BindView(R.id.iv_top_two)
    RoundedImageView iv_top_two;
    @BindView(R.id.nick_top_two)
    TextView nick_top_two;
    @BindView(R.id.num_top_two)
    TextView num_top_two;
    @BindView(R.id.iv_top_three)
    RoundedImageView iv_top_three;
    @BindView(R.id.nick_top_three)
    TextView nick_top_three;
    @BindView(R.id.num_top_three)
    TextView num_top_three;

    @BindView(R.id.rank_top3)
    RecyclerView rank_top3;
    @BindView(R.id.no_data)
    ImageView no_data;

    @BindView(R.id.tv_index)
    TextView tv_index;
    @BindView(R.id.ci_head)
    CircularImage ci_head;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_me_num)
    TextView tv_me_num;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;


    private int id = 2;   //  class：2贡献榜 1魅力榜
    private int mType = 1;   //  1日, 2周, 3总
    private RankAdapterN mRankAdapterN;


    public static RankFragmentN getInstance(int tag) {
        RankFragmentN fragment = new RankFragmentN();
        Bundle bundle = new Bundle();
        bundle.putInt("id", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        LogUtils.debugInfo(own + "onCreateView");
        //EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_rank_new);
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
        LogUtils.debugInfo(own + "initData");
        id = getArguments().getInt("id");

        mRankAdapterN = new RankAdapterN(mType, getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rank_top3.setLayoutManager(llm);
        mRankAdapterN.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
                if (mRankAdapterN.getData().get(position).getUser_id() == UserManager.getUser().getUserId()) {
                    intent.putExtra("sign", 0);
                    intent.putExtra("id", "");
                } else {
                    intent.putExtra("sign", 1);
                    intent.putExtra("id", mRankAdapterN.getData().get(position).getUser_id() + "");
                }
                ArmsUtils.startActivity(intent);
            }
        });
        rank_top3.setAdapter(mRankAdapterN);

        refreshData(mType);
        rl_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
                intent.putExtra("sign", 0);
                intent.putExtra("id", "");
                ArmsUtils.startActivity(intent);
            }
        });
        btn_top1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mType != 1) {
                    mType = 1;
                    changeState(mType);
                    refreshData(mType);
                }
            }
        });
        btn_top2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mType != 2) {
                    mType = 2;
                    changeState(mType);
                    refreshData(mType);
                }
            }
        });
        btn_top3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mType != 3) {
                    mType = 3;
                    changeState(mType);
                    refreshData(mType);
                }
            }
        });

        iv_top_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.debugInfo(own + "iv_top_one: " + mTopBeans.size());
                if(null == mTopBeans || mTopBeans.size() < 1) return;
                if(mTopBeans.get(0).getUser_id() == 0) return;
                Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
                if (mTopBeans.get(0).getUser_id() == UserManager.getUser().getUserId()) {
                    intent.putExtra("sign", 0);
                    intent.putExtra("id", "");
                } else {
                    intent.putExtra("sign", 1);
                    intent.putExtra("id", mTopBeans.get(0).getUser_id() + "");
                }
                ArmsUtils.startActivity(intent);
            }
        });
        iv_top_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.debugInfo(own + "iv_top_two: " + mTopBeans.size());
                if(null == mTopBeans || mTopBeans.size() < 2) return;
                if(mTopBeans.get(1).getUser_id() == 0) return;
                Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
                if (mTopBeans.get(1).getUser_id() == UserManager.getUser().getUserId()) {
                    intent.putExtra("sign", 0);
                    intent.putExtra("id", "");
                } else {
                    intent.putExtra("sign", 1);
                    intent.putExtra("id", mTopBeans.get(1).getUser_id() + "");
                }
                ArmsUtils.startActivity(intent);
            }
        });
        iv_top_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.debugInfo(own + "iv_top_three: " + mTopBeans.size());
                if(null == mTopBeans || mTopBeans.size() < 3) return;
                if(mTopBeans.get(2).getUser_id() == 0) return;
                Intent intent = new Intent(getContext(), MyPersonalCenterActivity.class);
                if (mTopBeans.get(2).getUser_id() == UserManager.getUser().getUserId()) {
                    intent.putExtra("sign", 0);
                    intent.putExtra("id", "");
                } else {
                    intent.putExtra("sign", 1);
                    intent.putExtra("id", mTopBeans.get(2).getUser_id() + "");
                }
                ArmsUtils.startActivity(intent);
            }
        });
    }

    private void changeState(int type) {
        switch (type) {
            case 1:
                btn_top1.setImageResource(R.drawable.list_day_sel);
                btn_top2.setImageResource(R.drawable.list_week_nol);
                btn_top3.setImageResource(R.drawable.list_zong_nol);
                break;
            case 2:
                btn_top1.setImageResource(R.drawable.list_day_nol);
                btn_top2.setImageResource(R.drawable.list_week_sel);
                btn_top3.setImageResource(R.drawable.list_zong_nol);
                break;
            case 3:
                btn_top1.setImageResource(R.drawable.list_day_nol);
                btn_top2.setImageResource(R.drawable.list_week_nol);
                btn_top3.setImageResource(R.drawable.list_zong_sel);
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(@Nullable Object data) {
        LogUtils.debugInfo(own + "setData");
    }

    private void refreshData(int type) {
        showDialogLoding();
        RxUtils.loading(commonModel.leaderboard(String.valueOf(id),
                String.valueOf(type), String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<Rank>(mErrorHandler) {
                    @Override
                    public void onNext(Rank rank) {
                        disDialogLoding();
                        try {
                            if (rank.getData().getTop().size() == 0) {
                                no_data.setVisibility(View.VISIBLE);
                                rank_top3.setVisibility(View.GONE);
                            } else {
                                no_data.setVisibility(View.GONE);
                                rank_top3.setVisibility(View.VISIBLE);
                            }
                            drawTop(rank.getData().getTop());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        drawOther(rank.getData().getOther());
                        // 绘制个人排名数据
                        drawMySelf(rank.getData().getUser().get(0));
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                    }
                });
    }

    private List<Rank.DataBean.TopBean> mTopBeans;
    private void drawTop(List<Rank.DataBean.TopBean> topBeans) {
        mTopBeans = topBeans;
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .url(topBeans.get(0).getHeadimgurl())
                        .placeholder(R.mipmap.no_tou)
                        .imageView(iv_top_one)
                        .errorPic(R.mipmap.no_tou)
                        .build());
        nick_top_one.setText(topBeans.get(0).getNickname());
        num_top_one.setText(String.valueOf(topBeans.get(0).getExp()));

        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .url(topBeans.get(1).getHeadimgurl())
                        .placeholder(R.mipmap.no_tou)
                        .imageView(iv_top_two)
                        .errorPic(R.mipmap.no_tou)
                        .build());
        nick_top_two.setText(topBeans.get(1).getNickname());
        num_top_two.setText(String.valueOf(topBeans.get(1).getExp()));

        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .url(topBeans.get(2).getHeadimgurl())
                        .placeholder(R.mipmap.no_tou)
                        .imageView(iv_top_three)
                        .errorPic(R.mipmap.no_tou)
                        .build());
        nick_top_three.setText(topBeans.get(2).getNickname());
        num_top_three.setText(String.valueOf(topBeans.get(2).getExp()));
    }

    private void drawOther(List<Rank.DataBean.OtherBean> otherBeans) {
        mRankAdapterN.setNewData(otherBeans);
    }

    private void drawMySelf(Rank.DataBean.UserBean userBean) {
        tv_index.setText(userBean.getSort());
        tv_nick.setText(userBean.getNickname());
        tv_me_num.setText(String.valueOf(userBean.getExp()));

        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .url(userBean.getHeadimgurl())
                        .placeholder(R.mipmap.no_tou)
                        .imageView(ci_head)
                        .errorPic(R.mipmap.no_tou)
                        .build());
    }

    // 切换大标签，数据刷新
    public void switchData(boolean isGxb) {
        if(isGxb) {
            id = 2;
        } else {
            id = 1;
        }
        refreshData(mType);
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void changeData(RankSwitchEvent event) {
        if(event.isGxb()) {
            id = 2;
        } else {
            id = 1;
        }
        refreshData(mType);
    }*/

}
