package com.konglianyuyin.mx.fragment;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainHomeRankFragmentNew extends MyBaseArmFragment implements ImmersionOwner {
    @BindView(R.id.tv_gxb)
    TextView tv_gxb;
    @BindView(R.id.tv_mlb)
    TextView tv_mlb;

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.room_rank_tit)
    RelativeLayout roomRankTit;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    private RankFragmentN instance1, instance2;

    private boolean tabDefault = true;

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_home_rank_new);
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

    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public boolean immersionBarEnabled() {//是否用沉浸式
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    private ArrayList<Fragment> fsRes = new ArrayList<>();                  //fragment集合
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //标题资源
        titleRes.add("贡献榜");
        titleRes.add("魅力榜");
        //fragment数据
        //fsRes.add(RankFragmentNew.getInstance(2, 1));
        //fsRes.add(RankFragmentNew.getInstance(1, 2));
        fsRes.add(instance1 = RankFragmentN.getInstance(2));
        fsRes.add(instance2 = RankFragmentN.getInstance(1));

        //设置数据
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fsRes));
        //viewPager.setOffscreenPageLimit(0);
        tabLayout.setViewPager(viewPager);

        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fs;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fs) {
            super(fm);
            this.fs = fs;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleRes.get(position);
        }

        @Override
        public Fragment getItem(int i) {
            return fs.get(i);
        }

        @Override
        public int getCount() {
            return fs.size();
        }
    }

    private void changeTabState(boolean first) {
        if(first) {
            tv_gxb.setTextColor(getResources().getColor(R.color.color_white));
            tv_gxb.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_size_20));
            tv_gxb.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_mlb.setTextColor(getResources().getColor(R.color.color_222222));
            tv_mlb.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_size_16));
            tv_mlb.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabDefault = true;
            instance1.switchData(first);
        } else {
            tv_mlb.setTextColor(getResources().getColor(R.color.color_white));
            tv_mlb.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_size_20));
            tv_mlb.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_gxb.setTextColor(getResources().getColor(R.color.color_222222));
            tv_gxb.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.txt_size_16));
            tv_gxb.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabDefault = false;
            instance2.switchData(first);
        }
        // EventBus.getDefault().post(new RankSwitchEvent(first));
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        tv_gxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tabDefault) {
                    changeTabState(true);
                    viewPager.setCurrentItem(0);
                }
            }
        });
        tv_mlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabDefault) {
                    changeTabState(false);
                    viewPager.setCurrentItem(1);
                }
            }
        });
        //TabLayout监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //显示相应的item界面
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //ViewPager监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //设置相应选中图标和颜色
                tabLayout.setCurrentTab(i);
                changeTabState(i == 0);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置默认第0个
        viewPager.setCurrentItem(0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

}
