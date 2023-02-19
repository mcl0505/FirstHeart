package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.SetAdminActivity;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.AdminUser;

/**
 * 管理员
 */
@ActivityScope
public class AdminUserAdapter extends MyBaseAdapter<AdminUser.DataBean.AdminBean> {

    private Context context;

    public AdminUserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder VH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_admin, null);
            VH = new ViewHolder(convertView);
            convertView.setTag(VH);
        } else {
            VH = (ViewHolder) convertView.getTag();
        }
        VH.tv_title.setText(list_adapter.get(position).getNickname());
        VH.tv_userid.setText("ID：" + list_adapter.get(position).getId());
        if (!TextUtils.isEmpty(list_adapter.get(position).getHeadimgurl())) {
            GlideArms.with(context)
                    .load(list_adapter.get(position).getHeadimgurl())
                    .placeholder(R.mipmap.no_tu)
                    .error(R.mipmap.no_tu)
                    .circleCrop()
                    .into(VH.ci_head);
        }
        VH.textCount.setOnClickListener(v -> {//删除
            if(context instanceof SetAdminActivity){
                ((SetAdminActivity) context).remove_admin(String.valueOf(list_adapter.get(position).getId()),0);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView tv_title, tv_userid, textCount;
        ImageView ci_head;

        public ViewHolder(View convertView) {
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_userid = (TextView) convertView.findViewById(R.id.tv_userid);
            textCount = (TextView) convertView.findViewById(R.id.textCount);
            ci_head = convertView.findViewById(R.id.ci_head);
        }
    }

}