package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.mine.fragment.MoneyLeftFragment;
import com.konglianyuyin.mx.activity.mine.fragment.MoneyRightFragment;
import com.konglianyuyin.mx.adapter.RankPagerAdapter;
import com.konglianyuyin.mx.base.MyBaseArmActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的钱包
 */
public class MoneyActivityNew extends MyBaseArmActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private RankPagerAdapter mAdapter;

    private int mCanZs = 0; // 0不能赠送，1能赠送

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_money_new;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mCanZs = getIntent().getIntExtra("CanZs", 0);
        titleList.add("灵石");
        titleList.add("钻石");
        MoneyLeftFragment moneyLeftFragment = MoneyLeftFragment.newInstance(mCanZs);
        MoneyRightFragment moneyRightFragment = new MoneyRightFragment();
        mFragments.add(moneyLeftFragment);
        mFragments.add(moneyRightFragment);
        mAdapter = new RankPagerAdapter(getSupportFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setCurrentTab(0);
        tabLayout.setSnapOnTabClick(true);
        viewPager.setOffscreenPageLimit(mFragments.size());
    }

    @Override
    protected void onResume() {
        super.onResume();

        setToolbarTitle("我的钱包",true);
    }
}
