package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.RecommenRoomBean;

/**
 * Created by cxf on 2018/9/26.
 */

public class HomeRecomGirlAdapter extends RefreshAdapter<RecommenRoomBean.DataBean> {
    private Context context;
    private View.OnClickListener mOnClickListener;

    public HomeRecomGirlAdapter(Context context) {
        super(context);
        this.context = context;

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canClick()) {
                    return;
                }
                Object tag = v.getTag();
                if (tag != null) {
                    int position = (int) tag;
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mList.get(position), position);
                    }
                }
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_main_home_recom_girl, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        ((Vh) vh).setData(mList.get(position), position);
    }

    class Vh extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_id;
        TextView tv_tip;
        TextView tv_nick;

        public Vh(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.avatar);
            tv_tip = (TextView) itemView.findViewById(R.id.into);
            tv_id = (TextView) itemView.findViewById(R.id.name);
            tv_nick = (TextView) itemView.findViewById(R.id.name2);
            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(RecommenRoomBean.DataBean bean, int position) {
            itemView.setTag(position);
            ArmsUtils.obtainAppComponentFromContext(context)
                    .imageLoader()
                    .loadImage(context, ImageConfigImpl
                            .builder()
                            .url(bean.getRoom_cover())
                            .placeholder(R.mipmap.no_tu)
                            .imageView(iv_img)
                            .errorPic(R.mipmap.no_tu)
                            .build());
            tv_id.setText("ID："+bean.getUid());
            tv_nick.setText(bean.getRoom_name());

        }
    }

}
