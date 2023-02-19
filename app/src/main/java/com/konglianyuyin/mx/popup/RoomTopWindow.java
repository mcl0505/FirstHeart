package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.utils.SharedPreferencesUtils;


/**
 * 房间输入
 */
public class RoomTopWindow extends PopupWindow {


    private View mMenuView;
    private Context context;

    public LinearLayout getLlShare() {
        return llShare;
    }

    public LinearLayout getLlClose() {
        return llClose;
    }

    public LinearLayout getLlJubao() {
        return llJubao;
    }

    public LinearLayout getLlTeXiao() {
        return llTeXiao;
    }

    private LinearLayout llShare, llClose, llJubao, llTeXiao;

    private TextView tv_texiao;

    public RoomTopWindow(Activity context) {
        super(context);
        this.context = context;
        mMenuView = LayoutInflater.from(context).inflate(R.layout.dialog_room_top, null);
        llShare = mMenuView.findViewById(R.id.llShare);
        llClose = mMenuView.findViewById(R.id.llClose);
        llJubao = mMenuView.findViewById(R.id.llJubao);
        llTeXiao = mMenuView.findViewById(R.id.llTeXiao);
        tv_texiao = mMenuView.findViewById(R.id.tv_texiao);
        boolean closeGif = (boolean) SharedPreferencesUtils.getParam(context, "SHOWGIF", false);
        if (closeGif) {
            tv_texiao.setText("开启特效");
        } else {
            tv_texiao.setText("屏蔽特效");
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //设置透明
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        context.getWindow().setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = (MyBaseArmActivity) context;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
    }
}
