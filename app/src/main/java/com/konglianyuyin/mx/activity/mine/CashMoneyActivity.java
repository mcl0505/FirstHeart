package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.view.ShapeTextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 提现
 */
public class CashMoneyActivity extends MyBaseArmActivity {

    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textZhanghu)
    TextView textZhanghu;
    @BindView(R.id.textAll)
    TextView textAll;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.editMoney)
    EditText editMoney;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;

    @Inject
    CommonModel commonModel;

    private float withdrawRatio;

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
        return R.layout.activity_cash_money;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loadData();
        editMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int money = Integer.valueOf(s.toString());
                    tvMoney.setText((money*withdrawRatio)+"");
                }catch (NumberFormatException e){
                    tvMoney.setText("");
                }
            }
        });
    }

    private void loadData() {
        RxUtils.loading(commonModel.my_store(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        if (!TextUtils.isEmpty(moneyBean.getData().get(0).getAli_nick_name())) {
                            textName.setText(moneyBean.getData().get(0).getAli_nick_name() + "");
                        } else {
                            textName.setText("");
                        }
                        textZhanghu.setText(moneyBean.getData().get(0).getAli_user_id());
                        textAll.setText(moneyBean.getData().get(0).getMibi());

                        try{
                            // txbl为""
                            if(TextUtils.isEmpty(moneyBean.getData().get(0).getTxbl())) {
                                withdrawRatio = 0;
                            } else {
                                withdrawRatio = Float.valueOf(moneyBean.getData().get(0).getTxbl());
                            }
                        }catch (NumberFormatException e){
                            toast("数据错误");
                            finish();
                        }
                    }
                });
    }

    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                String str = tvMoney.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    toast("请输入提现钻石数");
                    return;
                }

                if(str.contains(".")){
                    String[] ss = str.split("\\.");
                    long floatStr = Long.valueOf(ss[1]);
                    if(floatStr>0){
                        toast("实际到账金额必须为整数");
                        return;
                    }
                }

                RxUtils.loading(commonModel.tixian(String.valueOf(UserManager.getUser().getUserId()), editMoney.getText().toString()), this)
                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                            @Override
                            public void onNext(BaseBean moneyBean) {
                                toast("提现成功");
                                EventBus.getDefault().post(new FirstEvent("指定发送", Constant.CHONGZHISHUAXIN));
                                loadData();
                            }
                        });
                break;
        }
    }

}
