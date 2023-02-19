package com.konglianyuyin.mx.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;


import com.konglianyuyin.mx.bean.EmojiBean;

import java.util.List;
import java.util.regex.Pattern;

public class SpannableStringUtils {

    private static SpannableStringUtils instance;
    private static Pattern pattern = Pattern.compile("\\[E[0-9]{3}]", Pattern.CASE_INSENSITIVE);
    private static List<EmojiBean> emojiBeans;

    private SpannableStringUtils(){

    }

    public static SpannableStringUtils getInstance(){
        if(instance == null){
            synchronized (SpannableStringUtils.class){
                if(instance == null){
                    instance = new SpannableStringUtils();
                }
            }
        }
        return instance;
    }

    public SpannableString getSizeChangePrice(String price){
        SpannableString sp = new SpannableString(price+"灵石");
        sp.setSpan(new AbsoluteSizeSpan(12,true), 0, sp.length()-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(8,true), sp.length()-2,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public SpannableString addCenterLine(String oldPrice){
        SpannableString sp = new SpannableString(oldPrice);
        StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
        sp.setSpan(stSpan, 0, oldPrice.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
