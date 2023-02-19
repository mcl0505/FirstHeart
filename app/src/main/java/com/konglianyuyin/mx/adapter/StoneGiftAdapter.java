package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.GlideArms;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.GiftListBean;
import com.konglianyuyin.mx.view.MiniCircleRecyclerView;

import java.util.List;

/**
 * 作者:sgm
 * 描述:礼物的列表
 */
public class StoneGiftAdapter extends RecyclerView.Adapter<StoneGiftAdapter.VH> {

    private Context context;
    private MiniCircleRecyclerView recyclerView;
    private List<GiftListBean.DataBean.BaoshiBean> data;

    public List<GiftListBean.DataBean.BaoshiBean> getData() {
        return data;
    }


    boolean mNeedLoop = true;

    public StoneGiftAdapter(Context context, MiniCircleRecyclerView recyclerView, List<GiftListBean.DataBean.BaoshiBean> data,boolean needLoop) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.data = data;
        this.mNeedLoop = needLoop;
    }

    public void setNeedLoop(boolean needLoop){
        this.mNeedLoop = needLoop;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH h = null;
        h = new VH(LayoutInflater.from(context)
                .inflate(R.layout.item_h, parent, false));
        return h;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
//        LogUtils.debugInfo("====刷新列表" + data.get(position % data.size()).getName());
        holder.tv.setText(data.get(position % data.size()).getName());
        holder.tvPrice.setText(String.valueOf(data.get(position % data.size()).getPrice()));

        GlideArms.with(context)
                .load(data.get(position % data.size()).getImg())
//                .circleCrop()
                .into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LogUtils.debugInfo("====点击position:" +(position % data.size()));
                recyclerView.smoothScrollToView(v);
            }
        });

        holder.itemView.setTag(R.string.item_position, position);
//        if (data.get(position % data.size()).isSelect) {
//            holder.img.setVisibility(View.VISIBLE);
//        }else {
//            holder.img.setVisibility(View.GONE);
//        }

//        View viewAtCenter = recyclerView.findViewAtCenter();
    }

    @Override
    public int getItemCount() {
        if(mNeedLoop){
            return Integer.MAX_VALUE;
        } else {
            return data==null?0:data.size();
        }
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView tv, tvPrice;
        ImageView iv;

        public VH(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_img);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }
}
