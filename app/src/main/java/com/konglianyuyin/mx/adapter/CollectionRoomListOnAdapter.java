package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.CollectionRoomListBean;

public class CollectionRoomListOnAdapter extends MyBaseAdapter<CollectionRoomListBean.DataBean.OnBean> {
    private Context context;

    public CollectionRoomListOnAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OnViewHolder OVH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_save, null);
            OVH = new OnViewHolder(convertView);
            convertView.setTag(OVH);
        } else {
            OVH = (OnViewHolder) convertView.getTag();
        }
        OVH.tv_nick.setText(list_adapter.get(position).getRoom_name());
        OVH.tv_id.setText("ID " + list_adapter.get(position).getUid());


        int sex = list_adapter.get(position).getSex();
        if (sex == 1) {
            OVH.iv_tip_bg.setImageResource(R.drawable.boy_img);
            OVH.tv_tip.setText("男友厅");
        } else {
            OVH.iv_tip_bg.setImageResource(R.drawable.gril_img1);
            OVH.tv_tip.setText("女友厅");
        }
        if (!TextUtils.isEmpty(list_adapter.get(position).getRoom_cover())) {
            ArmsUtils.obtainAppComponentFromContext(context)
                    .imageLoader()
                    .loadImage(context, ImageConfigImpl
                            .builder()
                            .url(list_adapter.get(position).getRoom_cover())
                            .placeholder(R.mipmap.no_tou)
                            .imageView(OVH.iv_img)
                            .errorPic(R.mipmap.no_tou)
                            .build());
        }

        return convertView;
    }

    public static class OnViewHolder {
        ImageView iv_img;
        TextView tv_id;

        ImageView iv_tip_bg;
        TextView tv_tip;

        TextView tv_nick;

        /*TextView tv_title, tv_userid, textCount;
        ImageView imgBg;
        CircularImage ci_head;
        RelativeLayout relativeLayout;*/

        public OnViewHolder(View convertView) {
            /*tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_userid = (TextView) convertView.findViewById(R.id.tv_userid);
            textCount = (TextView) convertView.findViewById(R.id.textCount);
            imgBg = convertView.findViewById(R.id.imgBg);
            ci_head = convertView.findViewById(R.id.ci_head);
            relativeLayout = convertView.findViewById(R.id.relative);*/

            iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            iv_tip_bg = (ImageView) convertView.findViewById(R.id.iv_tip_bg);
            tv_tip = (TextView) convertView.findViewById(R.id.tv_tip);
            tv_nick = (TextView) convertView.findViewById(R.id.tv_nick);
        }
    }
}
