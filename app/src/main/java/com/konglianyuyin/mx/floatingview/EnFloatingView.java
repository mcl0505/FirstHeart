package com.konglianyuyin.mx.floatingview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.konglianyuyin.mx.R;


/**
 * @ClassName EnFloatingView
 * @Description 悬浮窗
 * @Author Yunpeng Li
 * @Creation 2018/3/15 下午5:04
 * @Mender Yunpeng Li
 * @Modification 2018/3/15 下午5:04
 */
public class EnFloatingView extends FloatingMagnetView {

//    private final ImageView mIcon;

    public EnFloatingView(@NonNull Context context) {
        super(context, null);
        inflate(context, R.layout.home_float, this);
//        mIcon = findViewById(R.id.icon);
    }

    public void setIconImage(@DrawableRes int resId){
//        mIcon.setImageResource(resId);
    }

}
