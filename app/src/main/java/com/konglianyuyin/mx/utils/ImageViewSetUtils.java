package com.konglianyuyin.mx.utils;

import android.widget.ImageView;

import com.konglianyuyin.mx.R;

public class ImageViewSetUtils {

    private static ImageViewSetUtils instance;

    private ImageViewSetUtils(){

    }

    public static ImageViewSetUtils getInstance(){
        if(instance == null){
            synchronized (ImageViewSetUtils.class){
                if(instance == null){
                    instance = new ImageViewSetUtils();
                }
            }
        }
        return instance;
    }

    public void setStarImage(ImageView image,int level){
        int[] starImages = new int[]{R.mipmap.xingrui_0,R.mipmap.xingrui_1,R.mipmap.xingrui_2,R.mipmap.xingrui_3,R.mipmap.xingrui_4,R.mipmap.xingrui_5,
                R.mipmap.xingrui_6,R.mipmap.xingrui_7,R.mipmap.xingrui_8,R.mipmap.xingrui_9,R.mipmap.xingrui_10,R.mipmap.xingrui_11,R.mipmap.xingrui_12,
                R.mipmap.xingrui_13,R.mipmap.xingrui_14,R.mipmap.xingrui_15,R.mipmap.xingrui_16,R.mipmap.xingrui_17,R.mipmap.xingrui_18,
                R.mipmap.xingrui_19, R.mipmap.xingrui_20};

        image.setImageResource(starImages[level]);
    }

    public void setGoldrImage(ImageView image,int level){
        int[] goldImages = new int[]{R.mipmap.jinrui_0,R.mipmap.jinrui_one,R.mipmap.jinrui_two,R.mipmap.jinrui_three,R.mipmap.jinrui_four,
                R.mipmap.jinrui_five, R.mipmap.jinrui_six,R.mipmap.jinrui_seven,R.mipmap.jinrui_eight,R.mipmap.jinrui_nine,R.mipmap.jinrui_ten,
                R.mipmap.jinrui_eleven,R.mipmap.jinrui_twelve, R.mipmap.jinrui_thirteen,R.mipmap.jinrui_fourteen,R.mipmap.jinrui_fifteen,
                R.mipmap.jinrui_sixteen,R.mipmap.jinrui_seveteen,R.mipmap.jinrui_eighteen,R.mipmap.jinrui_nineteen, R.mipmap.jinrui_twenty};

        image.setImageResource(goldImages[level]);
    }
}
