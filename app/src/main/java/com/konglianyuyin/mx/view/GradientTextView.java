package com.konglianyuyin.mx.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

public class GradientTextView extends android.support.v7.widget.AppCompatTextView {
    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint paint = getPaint();
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, h,
                Color.parseColor("#ffcf3e"), Color.parseColor("#ff823e"), Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradient);
    }

}