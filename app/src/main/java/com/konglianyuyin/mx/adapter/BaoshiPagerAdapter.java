package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.GiftListBeanNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/7/11.
 */

public class BaoshiPagerAdapter extends PagerAdapter {

    private List<RecyclerView> mViewList;
    private static final int GIFT_COUNT = 8;//每页10个礼物
    private int mPage = -1;
    private ActionListener mActionListener;

    public BaoshiPagerAdapter(Context context, List<GiftListBeanNew.DataBean.BaoshiBean> giftList) {
        mViewList = new ArrayList<>();
        int fromIndex = 0;
        int size = giftList.size();
        int pageCount = size / GIFT_COUNT;
        if (size % GIFT_COUNT > 0) {
            pageCount++;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        BaoshiAdapter.ActionListener actionListener = new BaoshiAdapter.ActionListener() {
            @Override
            public void onCancel() {
                if (mPage >= 0 && mPage < mViewList.size()) {
                    BaoshiAdapter adapter = (BaoshiAdapter) mViewList.get(mPage).getAdapter();
                    if (adapter != null) {
                        adapter.cancelChecked();
                    }
                }
            }

            @Override
            public void onItemChecked(GiftListBeanNew.DataBean.BaoshiBean bean, int position) {
                mPage = bean.getPage();
                int newPosition = mPage * GIFT_COUNT + position;
                if (mActionListener != null) {
                    mActionListener.onItemChecked(bean, newPosition);
                }
            }
        };
        for (int i = 0; i < pageCount; i++) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.view_gift_page, null, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false));
            int endIndex = fromIndex + GIFT_COUNT;
            if (endIndex > size) {
                endIndex = size;
            }
            List<GiftListBeanNew.DataBean.BaoshiBean> list = new ArrayList<>();
            for (int j = fromIndex; j < endIndex; j++) {
                GiftListBeanNew.DataBean.BaoshiBean bean = giftList.get(j);
                bean.setPage(i);
                list.add(bean);
            }
            BaoshiAdapter adapter = new BaoshiAdapter(context, inflater, list);
            adapter.setActionListener(actionListener);
            recyclerView.setAdapter(adapter);
            mViewList.add(recyclerView);
            fromIndex = endIndex;
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    public void release() {
        if (mViewList != null) {
            for (RecyclerView recyclerView : mViewList) {
                BaoshiAdapter adapter = (BaoshiAdapter) recyclerView.getAdapter();
                if (adapter != null) {
                    adapter.release();
                }
            }
        }
    }

    public interface ActionListener {
        void onItemChecked(GiftListBeanNew.DataBean.BaoshiBean bean, int position);
    }


    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
}
