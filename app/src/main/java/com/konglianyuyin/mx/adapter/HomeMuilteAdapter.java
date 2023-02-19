package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.SearchHisActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BannerBean;
import com.konglianyuyin.mx.bean.HomeMultipleItem;
import com.konglianyuyin.mx.bean.RecommenRoomBean;
import com.konglianyuyin.mx.bean.RoomMultipleItem;
import com.konglianyuyin.mx.fragment.MainHomeNewFragment;
import com.konglianyuyin.mx.http.HttpCallback;
import com.konglianyuyin.mx.http.HttpUtil;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.JsonMananger;
import com.konglianyuyin.mx.view.GlideImageLoader;
import com.konglianyuyin.mx.view.ItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class HomeMuilteAdapter extends BaseMultiItemQuickAdapter<HomeMultipleItem, BaseViewHolder> {

    Context mContext;

    List<HomeMultipleItem> mDataList;
    MainHomeNewFragment mFragment;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeMuilteAdapter(Context context, List<HomeMultipleItem> data, MainHomeNewFragment fragment) {
        super(data);
        mContext = context;
        mDataList = data;
        mFragment = fragment;
        //必须绑定type和layout的关系
        addItemType(HomeMultipleItem.Layout_Top, R.layout.item_home_multi_top);
        addItemType(HomeMultipleItem.Layout_Banner, R.layout.item_home_multi_banner);
        addItemType(HomeMultipleItem.Layout_Boy, R.layout.item_home_multi_boy);
        addItemType(HomeMultipleItem.Layout_Girl, R.layout.item_home_multi_girl);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeMultipleItem item) {
        switch (helper.getItemViewType()){
            case HomeMultipleItem.Layout_Top:
                helper.getView(R.id.home_myhome).setOnClickListener(view -> {
                    mFragment.enterData(UserManager.getUser().getUserId()+"","",mFragment.commonModel,1,UserManager.getUser().getHeadimgurl());
                });
                helper.getView(R.id.sousuo).setOnClickListener(view -> {
                    ArmsUtils.startActivity(SearchHisActivity.class);
                });
                break;
            case HomeMultipleItem.Layout_Banner:
                Banner banner = helper.getView(R.id.banner);
                HttpUtil.getBanner("", new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, Object info) {
                        LogUtils.debugInfo(JSON.toJSONString(info));
                        List<BannerBean.DataBean> data = JsonMananger.jsonToList(JsonMananger.beanToJson(info),BannerBean.DataBean.class);
                        List<String> imgurls = new ArrayList<>();

                        for (BannerBean.DataBean list : data) {
                            imgurls.add(list.getImg());
                        }
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        banner.setImages(imgurls);
                        //设置指示器位置（当banner模式中有指示器时）
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        banner.setIndicatorGravity(BannerConfig.CENTER);
                        //设置自动轮播，默认为true
                        banner.isAutoPlay(true);
                        banner.setOnBannerListener(position -> {
                            Intent intent = new Intent(banner.getContext(), BaseWebActivity.class);
                            intent.putExtra("url", data.get(position).url);
                            intent.putExtra("name", "");
                            ArmsUtils.startActivity(intent);
                        });
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();
                    }
                });
                break;
            case HomeMultipleItem.Layout_Boy:

                RecyclerView mBoyRecyclerView = helper.getView(R.id.mRecyclerViewBoy);
                mBoyRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
                ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 8, 8);
                decoration.setOnlySetItemOffsetsButNoDraw(true);
                mBoyRecyclerView.addItemDecoration(decoration);
                HomeRecomBoyAdapter boyAdapter = new HomeRecomBoyAdapter(mContext);
                mBoyRecyclerView.setAdapter(boyAdapter);
                HttpUtil.getRoomList(item.getData(), "1", new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, Object info) {
                        List<RecommenRoomBean.DataBean> data = JsonMananger.jsonToList(JsonMananger.beanToJson(info),RecommenRoomBean.DataBean.class);
                        boyAdapter.refreshData(data);

                    }
                });

                boyAdapter.setOnItemClickListener((bean, position) -> {
                    mFragment.enterData(bean.getUid(),"",mFragment.commonModel,1,bean.getRoom_cover());
                });

                break;
            case HomeMultipleItem.Layout_Girl:
                RecyclerView mGirlRecyclerView = helper.getView(R.id.mRecyclerViewGirl);
                mGirlRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                HomeRecomGirlAdapter girlAdapter = new HomeRecomGirlAdapter(mContext);
                mGirlRecyclerView.setAdapter(girlAdapter);
                HttpUtil.getRoomList(item.getData(), "1", new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, Object info) {
                        List<RecommenRoomBean.DataBean> data = JsonMananger.jsonToList(JsonMananger.beanToJson(info),RecommenRoomBean.DataBean.class);
                        girlAdapter.refreshData(data);
                    }
                });
                girlAdapter.setOnItemClickListener((bean, position) -> {
                    mFragment.enterData(bean.getUid(),"",mFragment.commonModel,1,bean.getRoom_cover());
                });
                break;
        }
    }
}
