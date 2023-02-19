package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.DialogBean;

/**
 * 首页头部
 */
@ActivityScope
public class RoomDiaAdapter extends MyBaseAdapter<DialogBean> {

    private Context context;

    public RoomDiaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder VH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room_dialog, null);
            VH = new ViewHolder(convertView);
            convertView.setTag(VH);
        } else {
            VH = (ViewHolder) convertView.getTag();
        }
        VH.text1.setText(list_adapter.get(position).name);

        GlideArms
                        .with(context)
                        .load(list_adapter.get(position).id)
                        .placeholder(R.mipmap.no_tu)
                        .error(R.mipmap.no_tu)
                        .into(VH.img1);
        return convertView;
    }


    public static class ViewHolder {
        TextView text1;
        ImageView img1;

        public ViewHolder(View convertView) {
            text1 = (TextView) convertView.findViewById(R.id.text1);
            img1 = (ImageView) convertView.findViewById(R.id.img1);
        }
    }

}