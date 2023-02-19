package com.konglianyuyin.mx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeMultipleItem implements MultiItemEntity {
    public static final int Layout_Top = 1;
    public static final int Layout_Banner = 2;
    public static final int Layout_Boy = 3;
    public static final int Layout_Girl = 4;

    private int itemType;

    private String bean;

    public HomeMultipleItem(int itemType, String bean) {
        this.itemType = itemType;
        this.bean = bean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getData(){
        return bean;
    }
}
