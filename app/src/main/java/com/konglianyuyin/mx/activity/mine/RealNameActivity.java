package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.IDCard;
import com.konglianyuyin.mx.view.ShapeTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class RealNameActivity extends MyBaseArmActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editNumber)
    EditText editNumber;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;

    @Inject
    CommonModel commonModel;

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
        return R.layout.activity_real_name;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String name = editName.getText().toString();
                String number = editNumber.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    toast("请输入姓名！");
                } else if (TextUtils.isEmpty(number)) {
                    toast("请输入身份证号！");
                } else if (!IDCard.IDCardValidate(number)) {
                    toast("身份证格式不正确！");
                } else {
//                    RxUtils.loading(commonModel.sfrz_start(String.valueOf(UserManager.getUser().getUserId()),
//                            name, number), this)
                    RxUtils.loading(commonModel.bindIdentity(
                            name, number), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
//                                    Intent intent = new Intent(RealNameActivity.this, WebRealNameActivity.class);
//                                    intent.putExtra("url",baseBean.getData());
//                                    ArmsUtils.startActivity(intent);
//                                    try {
//                                        String intentFullUrl = baseBean.getData();
//                                        Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
//                                        startActivity(intent);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }

//                                    Intent action = new Intent(Intent.ACTION_VIEW);
//                                    StringBuilder builder = new StringBuilder();
//                                    // 这里使用固定appid 20000067
//                                    builder.append("alipays://platformapi/startapp?appId=20000067&url=");
//                                    builder.append(URLEncoder.encode(baseBean.getData()));
//                                    action.setData(Uri.parse(builder.toString()));
//                                    try {
//                                        startActivity(action);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        showToast("暂未找到支付宝");
//                                    }
                                    EventBus.getDefault().post(new FirstEvent("指定发送", Constant.RENZHENGCHENGGONG));
                                    finish();
                                }
                            });
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (Constant.RENZHENGCHENGGONG.equals(tag)) {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        RxUtils.loading(commonModel.sfrz_query(String.valueOf(UserManager.getUser().getUserId())), this)
//                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseBean baseBean) {
//                        finish();
//                        EventBus.getDefault().post(new FirstEvent("指定发送", Constant.RENZHENGCHENGGONG));
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                    }
//                });
    }
}
