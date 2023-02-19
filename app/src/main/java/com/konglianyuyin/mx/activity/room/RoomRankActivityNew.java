package com.konglianyuyin.mx.activity.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.fragment.RoomRankFragmentNew;
import com.konglianyuyin.mx.service.CommonModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomRankActivityNew extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;

    private static String[] AAA = new String[]{"贡献榜", "房间榜"}; //tab数据源
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.room_rank_tit)
    RelativeLayout roomRankTit;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private String imatgeurl;
    private int uid;
    private int statusBarHeight; //导航栏的高度
    private ArrayList<Fragment> fsRes = new ArrayList<>();                  //fragment集合
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合
    private boolean is_show_num;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_rank_new;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        uid = getIntent().getIntExtra("id", 0);
        is_show_num = getIntent().getBooleanExtra("is_show_num", false);
//        statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
//        MyUtil.setMargins(roomRankTit, 0, statusBarHeight, 0, 0);
//        QMUIStatusBarHelper.translucent(this);

        //标题资源
        titleRes.add("贡献榜");
        titleRes.add("房间榜");
        //fragment数据
        fsRes.add(RoomRankFragmentNew.getInstance(1, 1, String.valueOf(uid), is_show_num));
        fsRes.add(RoomRankFragmentNew.getInstance(2, 2, String.valueOf(uid), is_show_num));
        //设置数据
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fsRes));
        tabLayout.setViewPager(viewPager);
        initListener();

        ivBack.setOnClickListener(v -> {
//            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//            finish();
            startActivity(new Intent(this, AdminHomeActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, AdminHomeActivity.class));
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置默认第0个
        viewPager.setCurrentItem(0);
    }

}
