package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.components.ImmersionOwner;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.message.MessageOfficeActivity;
import com.konglianyuyin.mx.base.MyBaseArmFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeMessageFragment extends MyBaseArmFragment implements ImmersionOwner {


    @BindView(R.id.message_tab_layout)
    SlidingTabLayout messageTabLayout;
    @BindView(R.id.room_message)
    RelativeLayout roomMessage;
    @BindView(R.id.message_view_pager)
    ViewPager messageViewPager;
    Unbinder unbinder;

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_home_message);
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

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    private ArrayList<Fragment> fsRes = new ArrayList<>();                  //fragment集合
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //标题资源
        titleRes.add("好友消息");
        titleRes.add("官方消息");
        //fragment数据
        fsRes.add(new MainMessage2Fragment());
        fsRes.add(new MessageOfficeActivity());
        //设置数据
        messageViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fsRes));
        messageTabLayout.setViewPager(messageViewPager);
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

    /**
     * 初始化监听
     */
    private void initListener() {
        //TabLayout监听
        messageTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //显示相应的item界面
                messageViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //ViewPager监听
        messageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //设置相应选中图标和颜色
                messageTabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置默认第0个
        messageViewPager.setCurrentItem(0);
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
