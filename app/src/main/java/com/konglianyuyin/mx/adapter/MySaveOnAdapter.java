package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.CollectionRoomListBean;

/**
 * Created by cxf on 2018/9/26.
 */

public class MySaveOnAdapter extends RefreshAdapter<CollectionRoomListBean.DataBean.OnBean> {
    private Context context;
    private View.OnClickListener mOnClickListener;

    public MySaveOnAdapter(Context context) {
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
        return new Vh(mInflater.inflate(R.layout.item_my_save, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        ((Vh) vh).setData(mList.get(position), position);
    }

    class Vh extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_id;
        ImageView iv_tip_bg;
        TextView tv_tip;
        TextView tv_nick;

        public Vh(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            iv_tip_bg = (ImageView) itemView.findViewById(R.id.iv_tip_bg);
            tv_tip = (TextView) itemView.findViewById(R.id.tv_tip);
            tv_nick = (TextView) itemView.findViewById(R.id.tv_nick);
            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(CollectionRoomListBean.DataBean.OnBean bean, int position) {
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
            tv_id.setText("ID " + bean.getUid());
            tv_nick.setText(bean.getRoom_name());

            int sex = bean.getSex();
            if (sex == 1) {
                iv_tip_bg.setImageResource(R.drawable.boy_img);
                tv_tip.setText("男友厅");
            } else {
                iv_tip_bg.setImageResource(R.drawable.gril_img1);
                tv_tip.setText("女友厅");
            }

            /*String roomType = bean.getRoom_type();
            if (!TextUtils.isEmpty(roomType)) {
                iv_tip_bg.setVisibility(View.VISIBLE);
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText(bean.getName());
                switch (roomType) {
                    case "4":
                        tv_tip.setText("男友厅");
                        iv_tip_bg.setImageResource(R.drawable.boy_img);
                        break;
                    case "5":
                        tv_tip.setText("女友厅");
                        iv_tip_bg.setImageResource(R.drawable.gril_img1);
                        break;
                    case "6":
                        tv_tip.setText("交友厅");
                        iv_tip_bg.setImageResource(R.drawable.friend_img);
                        break;
                }
            } else {
                iv_tip_bg.setVisibility(View.GONE);
                tv_tip.setVisibility(View.GONE);
                iv_tip_bg.setImageResource(0);
                tv_tip.setText(null);
            }*/
        }
    }

}
