package com.konglianyuyin.mx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.konglianyuyin.mx.R;

import java.util.ArrayList;
import java.util.List;

public class PwdEditText extends AppCompatEditText {
    private Paint sidePaint, backPaint, textPaint;
    private Context mC;
    private String mText;
    private List<RectF> rectFS;
    private int StrokeWidth, spzceX, spzceY, textSize;
    private int checkedColor, defaultColor, backColor, textColor, waitInputColor;
    private int textLength;
    private int Circle, Round;
    private boolean isPwd, isWaitInput;

    public PwdEditText(Context context) {
        super(context);

        mC = context;
        setAttrs(null);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mC = context;
        setAttrs(attrs);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mC = context;
        setAttrs(attrs);
        init();
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray t = mC.obtainStyledAttributes(attrs, R.styleable.pwdEditText);
        if (t != null) {
            textLength = t.getInt(R.styleable.pwdEditText_textLength, 6);
            spzceX = t.getDimensionPixelSize(R.styleable.pwdEditText_space, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            spzceY = t.getDimensionPixelSize(R.styleable.pwdEditText_space, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            StrokeWidth = t.getDimensionPixelSize(R.styleable.pwdEditText_strokeWidths, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
            Round = t.getDimensionPixelSize(R.styleable.pwdEditText_round, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
            Circle = t.getDimensionPixelSize(R.styleable.pwdEditText_circle, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics()));
            textSize = t.getDimensionPixelSize(R.styleable.pwdEditText_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            checkedColor = t.getColor(R.styleable.pwdEditText_checkedColor, 0xff44ce61);
            defaultColor = t.getColor(R.styleable.pwdEditText_defaultColor, 0xffd0d0d0);
            backColor = t.getColor(R.styleable.pwdEditText_backColor, 0xfff1f1f1);
            textColor = t.getColor(R.styleable.pwdEditText_textColor, 0xFF444444);
            waitInputColor = t.getColor(R.styleable.pwdEditText_waitInputColor, 0xFF444444);
            isPwd = t.getBoolean(R.styleable.pwdEditText_isPwd, true);
            isWaitInput = t.getBoolean(R.styleable.pwdEditText_isWaitInput, false);
            t.recycle();
        }
    }

    private void init() {
        setTextColor(0X00ffffff); //???????????????????????????????????????
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        sidePaint = new Paint();
        backPaint = new Paint();
        textPaint = new Paint();

        rectFS = new ArrayList<>();
        mText = "";

        this.setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mText == null) {
            return;
        }
        //????????????????????????????????????????????????????????????????????????mText???
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (text.toString().length() <= textLength) {
            mText = text.toString();
        } else {
            setText(mText);
            setSelection(getText().toString().length());  //?????????????????????
            //??????setText(mText)???????????????????????????????????????????????????????????????
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        if (OnTextChangeListener != null) OnTextChangeListener.onTextChange(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                heightSize = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                heightSize = widthSize / textLength;
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //????????????
        sidePaint.setAntiAlias(true);//????????????
        sidePaint.setStrokeWidth(StrokeWidth);//?????????????????????
        sidePaint.setStyle(Paint.Style.STROKE);//??????????????????
        sidePaint.setColor(defaultColor);
        //???????????????
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(backColor);
        //???????????????
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);

        int Wide = Math.min(getMeasuredHeight(), getMeasuredWidth() / textLength);

        for (int i = 0; i < textLength; i++) {
            //??????????????????????????????????????????
            if (mText.length() > i) {
                sidePaint.setColor(checkedColor);
            } else {
                sidePaint.setColor(defaultColor);
            }

            //RectF?????????(left,  top,  right,  bottom); ????????????????????????????????????????????????????????????????????????????????????????????????????????????
            RectF rect = new RectF(i * Wide + spzceX, spzceY, i * Wide + Wide - spzceX, Wide - spzceY); //????????????????????????4?????????????????????????????????
//            canvas.drawRoundRect(rect,Round,Round,backPaint); //???????????????
//            canvas.drawRoundRect(rect,Round,Round,sidePaint); //???????????????
            canvas.drawLine(i * Wide + spzceX, Wide - spzceY, i * Wide + Wide, Wide - spzceY, sidePaint);
            rectFS.add(rect);

            if (isWaitInput && i == mText.length()) {  //?????????????????????
                Paint l = new Paint();
                l.setStrokeWidth(3);
                l.setStyle(Paint.Style.FILL);
                l.setColor(waitInputColor);
                canvas.drawLine(i * Wide + Wide / 2, Wide / 2 - Wide / 5, i * Wide + Wide / 2, Wide / 2 + Wide / 5, l);
            }
        }
        //???????????????
        for (int j = 0; j < mText.length(); j++) {
            if (isPwd) {
                canvas.drawCircle(j * Wide + Wide / 2 + spzceX / 2, rectFS.get(j).centerY() - spzceY / 2, Circle, textPaint);
            } else {
                canvas.drawText(mText.substring(j, j + 1), rectFS.get(j).centerX() - (textSize - spzceX) / 2, rectFS.get(j).centerY() + (textSize - spzceY) / 2, textPaint);
//                Rect textRect = new Rect();
//                textPaint.getTextBounds(mText.substring(j, j + 1), 0, 1, textRect);
//                canvas.drawText(mText.substring(j, j + 1), rectFS.get(j).left + (rectFS.get(j).right - rectFS.get(j).left) / 2 - textRect.width() / 2,
//                        rectFS.get(j).top + ((rectFS.get(j).bottom - rectFS.get(j).top) / 2) + textRect.height() / 2, textPaint);
            }
        }
    }

    private int dp2px(float dpValue) {
        float scale = mC.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ????????????
     */
    public interface OnTextChangeListener {
        void onTextChange(String pwd);
    }

    private OnTextChangeListener OnTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener OnTextChangeListener) {
        this.OnTextChangeListener = OnTextChangeListener;
    }

    /**
     * ???????????????????????????
     */
    public void clearText() {
        setText("");
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
    }

    /**
     * ?????????????????????
     */
    public void setSpace(int space) {
        spzceX = space;
        spzceY = space;
    }

    /**
     * ?????????????????????
     */
    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    /**
     * ?????????????????????
     */
    public int getTextLength() {
        return this.textLength;
    }

    /**
     * ??????????????????????????????
     */
    public void setcheckedColorColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    /**
     * ??????????????????????????????
     */
    public void setdefaultColorColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    /**
     * ????????????????????????
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    /**
     * ???????????????????????????
     */
    public void setPwdTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * ??????????????? ???????????????
     */
    public void setStrokeWidth(int width) {
        StrokeWidth = width;
    }

    /**
     * ?????????????????????
     */
    public void setCircle(int Circle) {
        this.Circle = Circle;
    }

    /**
     * ???????????????????????????
     */
    public void setRound(int Round) {
        this.Round = Round;
    }

    public int getStrokeWidth() {
        return StrokeWidth;
    }

    public int getSpzceX() {
        return spzceX;
    }

    public int getSpzceY() {
        return spzceY;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public int getBackColor() {
        return backColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getCircle() {
        return Circle;
    }

    public int getRound() {
        return Round;
    }

    public int gettextSize() {
        return textSize;
    }

    public void settextSize(int textSize) {
        this.textSize = textSize;
    }

    public boolean isPwd() {
        return isPwd;
    }

    /**
     * ??????????????????
     *
     * @param pwd
     */
    public void setPwd(boolean pwd) {
        isPwd = pwd;
    }

    public int getWaitInputColor() {
        return waitInputColor;
    }

    /**
     * \
     * ?????????????????????
     *
     * @param waitInputColor
     */
    public void setWaitInputColor(int waitInputColor) {
        this.waitInputColor = waitInputColor;
    }

    public boolean isWaitInput() {
        return isWaitInput;
    }

    /**
     * ???????????????????????????
     *
     * @param waitInput
     */
    public void setWaitInput(boolean waitInput) {
        isWaitInput = waitInput;
    }

}
