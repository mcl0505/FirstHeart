package com.konglianyuyin.mx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.utils.DpUtil;
import com.konglianyuyin.mx.utils.LogUtils;

public class LotteryView extends View {

    public LotteryView(Context context) {
        super(context);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(DpUtil.dp2px(10));
        mPaint.setColor(getContext().getColor(R.color.color_8ec3ff));

        int width = getWidth();
        int height = getHeight();

        LogUtils.e("TAG","-------------");

        //上面横线
        canvas.drawLine(DpUtil.dp2px(37),DpUtil.dp2px(22),
                width-DpUtil.dp2px(37),DpUtil.dp2px(22),mPaint);

        //左边纵线
        canvas.drawLine(DpUtil.dp2px(20),DpUtil.dp2px(55),
                DpUtil.dp2px(20),height-DpUtil.dp2px(55),mPaint);

        //右边纵线
        canvas.drawLine(width - DpUtil.dp2px(20),DpUtil.dp2px(55),
                width - DpUtil.dp2px(20),height-DpUtil.dp2px(55),mPaint);

        //下边横线
        canvas.drawLine(DpUtil.dp2px(37),height - DpUtil.dp2px(22),
                width-DpUtil.dp2px(37),height - DpUtil.dp2px(22),mPaint);

    }
}
