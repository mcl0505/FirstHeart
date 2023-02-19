package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ShapeTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 绑定成功
 * 老王
 */

public class BindAliPayNewActivity extends MyBaseArmActivity {

    @Inject
    CommonModel commonModel;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_zhanghu)
    EditText etZhanghu;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;


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
        return R.layout.activity_bind_alipay_new;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle("绑定支付宝", true);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))){
            etName.setText(getIntent().getStringExtra("name"));
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("zhanghu"))){
            etZhanghu.setText(getIntent().getStringExtra("zhanghu"));
        }
    }

    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String name = etName.getText().toString();
                String number = etZhanghu.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    toast("请输入您的真实姓名！");
                } else if (TextUtils.isEmpty(number)) {
                    toast("请输入您的支付宝账户！");
                } else {
//                    RxUtils.loading(commonModel.sfrz_start(String.valueOf(UserManager.getUser().getUserId()),
//                            name, number), this)
                    RxUtils.loading(commonModel.upalipay(
                            name, number), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    toast("绑定成功");
                                    finish();
                                }
                            });
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
