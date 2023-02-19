package com.konglianyuyin.mx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by cxf on 2018/9/26.
 */

public class HomeListItemLayout extends LinearLayout {

    public HomeListItemLayout(Context context) {
        super(context);
    }

    public HomeListItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * 188 / 160), MeasureSpec.EXACTLY);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
