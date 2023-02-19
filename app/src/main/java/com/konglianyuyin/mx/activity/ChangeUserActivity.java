package com.konglianyuyin.mx.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.MyBaseArmActivity;

/**
 * 修改个人资料
 */
public class ChangeUserActivity extends MyBaseArmActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_user;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

}
