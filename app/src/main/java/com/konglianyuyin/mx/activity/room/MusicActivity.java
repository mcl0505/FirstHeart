package com.konglianyuyin.mx.activity.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.RankPagerAdapter;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.fragment.MyMusicFragment;
import com.konglianyuyin.mx.fragment.NetMuscicFragment;
import com.konglianyuyin.mx.popup.MusicAddWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 音乐列表
 */
public class MusicActivity extends MyBaseArmActivity {


    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private RankPagerAdapter mAdapter;
    private int tag = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_music;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleList.add("我的音乐");
        titleList.add("音乐库");

        MyMusicFragment fragment1 = new MyMusicFragment();
        NetMuscicFragment fragment2 = new NetMuscicFragment();
        mFragments.add(fragment1);
        mFragments.add(fragment2);

        mAdapter = new RankPagerAdapter(getSupportFragmentManager(), mFragments, titleList);

        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setTextBold(tag);
        tabLayout.setCurrentTab(tag);
        tabLayout.setSnapOnTabClick(true);
        viewPager.setOffscreenPageLimit(mFragments.size());

        imgBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminHomeActivity.class));
            finish();
        });

        imgRight.setOnClickListener(v -> {
            MusicAddWindow musicAddWindow = new MusicAddWindow(this);
            musicAddWindow.showAsDropDown(imgRight);
            musicAddWindow.getLlShare().setOnClickListener(v1 -> {
                ArmsUtils.startActivity(MyMusciActivity.class);
                musicAddWindow.dismiss();
            });
            musicAddWindow.getLlClose().setOnClickListener(v1 -> {
                musicAddWindow.dismiss();
                toast("请在甜音官网进行音乐上传！");
            });
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, AdminHomeActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
