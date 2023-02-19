package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.GlideArms;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.GiftListBeanNew;
import com.konglianyuyin.mx.view.MyRadioButton;

import java.util.List;

/**
 * 作者:sgm
 * 描述:礼物的列表
 */
public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.Vh> {

    private Context context;
    private List<GiftListBeanNew.DataBean.GiftsBean> mList;
    private LayoutInflater mInflater;
    private String mCoinName;
    private View.OnClickListener mOnClickListener;
    private ActionListener mActionListener;
    private int mCheckedPosition = -1;
    private ScaleAnimation mAnimation;
    private View mAnimView;


    public List<GiftListBeanNew.DataBean.GiftsBean> getData() {
        return mList;
    }


    public GiftAdapter(Context context, LayoutInflater inflater, List<GiftListBeanNew.DataBean.GiftsBean> list) {
        this.context = context;
        mInflater = inflater;
        mList = list;
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null) {
                    int position = (int) tag;
                    GiftListBeanNew.DataBean.GiftsBean bean = mList.get(position);
                    if (!bean.isChecked()) {
                        if (!cancelChecked()) {
                            if (mActionListener != null) {
                                mActionListener.onCancel();
                            }
                        }
                        bean.setChecked(true);
                        notifyItemChanged(position, "payload");
                        View view = bean.getView();
                        if (view != null) {
                            view.startAnimation(mAnimation);
                            mAnimView = view;
                        }
                        mCheckedPosition = position;
                        if (mActionListener != null) {
                            mActionListener.onItemChecked(bean, mCheckedPosition);
                        }
                    }
                }
            }
        };
        mAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.setDuration(400);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setRepeatCount(-1);
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_g, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        vh.setData(mList.get(position), position, payload);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 取消选中
     */
    public boolean cancelChecked() {
        if (mCheckedPosition >= 0 && mCheckedPosition < mList.size()) {
            GiftListBeanNew.DataBean.GiftsBean bean = mList.get(mCheckedPosition);
            if (bean.isChecked()) {
                View view = bean.getView();
                if (mAnimView == view) {
                    mAnimView.clearAnimation();
                } else {
                    if (view != null) {
                        view.clearAnimation();
                    }
                }
                mAnimView = null;
                bean.setChecked(false);
                notifyItemChanged(mCheckedPosition, "payload");
            }
            mCheckedPosition = -1;
            return true;
        }
        return false;
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public void release() {
        if (mAnimView != null) {
            mAnimView.clearAnimation();
        }
        if (mList != null) {
            mList.clear();
        }
        mOnClickListener = null;
        mActionListener = null;
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mStone;
        TextView mShuliang;
        ImageView mIcon;
        TextView mName;
        TextView mPrice;
        MyRadioButton mRadioButton;

        public Vh(View itemView) {
            super(itemView);
            mShuliang = (TextView) itemView.findViewById(R.id.shuliang);
            mIcon = (ImageView) itemView.findViewById(R.id.item_img);
            mName = (TextView) itemView.findViewById(R.id.tv);
            mPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            mStone = (ImageView) itemView.findViewById(R.id.stone);
            mRadioButton = (MyRadioButton) itemView.findViewById(R.id.radioButton);
            mRadioButton.setOnClickListener(mOnClickListener);
        }

        void setData(GiftListBeanNew.DataBean.GiftsBean bean, int position, Object payload) {
            if (payload == null) {
                GlideArms.with(context)
                        .load(bean.getImg())
                        .into(mIcon);
                bean.setView(mIcon);
                mName.setText(bean.getName());
                mPrice.setText(String.valueOf(bean.getPrice_004()));
            }
            mRadioButton.setTag(position);
            mRadioButton.doChecked(bean.isChecked());
        }
    }

    public interface ActionListener {
        void onCancel();

        void onItemChecked(GiftListBeanNew.DataBean.GiftsBean bean, int postion);
    }
}
