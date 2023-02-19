package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.GoodsListBean;

/**
 * 充值
 */
@ActivityScope
public class ChargeAdapter extends MyBaseAdapter<GoodsListBean.DataBean.GoodslistBean> {

    private Context context;
    public ChargeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder VH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chager, null);
            VH = new ViewHolder(convertView);
            convertView.setTag(VH);
        } else {
            VH = (ViewHolder) convertView.getTag();
        }
        VH.textNum1.setText(list_adapter.get(position).getMizuan() + "灵石");
        VH.textNum3.setText(list_adapter.get(position).getPrice() + "元");

        if(list_adapter.get(position).isSelect()){
            VH.rlBg.setSelected(true);
            VH.textNum1.setSelected(true);
            VH.textNum3.setSelected(true);
        }else {
            VH.rlBg.setSelected(false);
            VH.textNum1.setSelected(false);
            VH.textNum3.setSelected(false);
        }

        return convertView;
    }


    public static class ViewHolder {
        RelativeLayout rlBg;
        TextView textNum1,textNum2,textNum3,textJia,textRight;

        public ViewHolder(View convertView) {
            rlBg = (RelativeLayout) convertView.findViewById(R.id.rlBg);
            textNum1 = (TextView) convertView.findViewById(R.id.textNum1);
            textNum3 = (TextView) convertView.findViewById(R.id.textNum3);
        }
    }

}