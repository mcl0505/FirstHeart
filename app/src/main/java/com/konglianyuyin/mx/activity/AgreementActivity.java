package com.konglianyuyin.mx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.AgrAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.AgreementBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.MyListView;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 平台协议页面
 */
import com.konglianyuyin.mx.base.BaseWebActivity;
public class AgreementActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.agr_list)
    MyListView agrList;
    private AgrAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_agreement;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAdapter = new AgrAdapter(this);
        agrList.setAdapter(mAdapter);
        getAgr();
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle("平台协议", true);
    }

    private void getAgr() {
        RxUtils.loading(commonModel.getAgreement(String.valueOf(3)), this)
                .subscribe(new ErrorHandleSubscriber<AgreementBean>(mErrorHandler) {
                    @Override
                    public void onNext(AgreementBean agreementBean) {
                        mAdapter.getList_adapter().addAll(agreementBean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });

        agrList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(AgreementActivity.this, BaseWebActivity.class);
            intent.putExtra("url", mAdapter.getList_adapter().get(position).getUrl());
            startActivity(intent);
        });
    }
}
