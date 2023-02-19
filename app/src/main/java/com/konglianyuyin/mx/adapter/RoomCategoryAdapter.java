package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.RoomType;

/**
 * 房间设置类型
 */
@ActivityScope
public class RoomCategoryAdapter extends MyBaseAdapter<RoomType.DataBean> {

    private Context context;

    public RoomCategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder VH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room_category, null);
            VH = new ViewHolder(convertView);
            convertView.setTag(VH);
        } else {
            VH = (ViewHolder) convertView.getTag();
        }
        VH.textNick.setText(list_adapter.get(position).getName());
        if(list_adapter.get(position).isSelect){
            VH.textNick.setSelected(true);
        }else {
            VH.textNick.setSelected(false);
        }
        convertView.setOnClickListener(v -> {
            for(RoomType.DataBean list : list_adapter){
                list.isSelect = false;
            }
            list_adapter.get(position).isSelect = true;
            notifyDataSetChanged();
        });
        return convertView;
    }


    public static class ViewHolder {
        TextView textNick;


        public ViewHolder(View convertView) {
            textNick = (TextView) convertView.findViewById(R.id.textNick);
        }
    }

}