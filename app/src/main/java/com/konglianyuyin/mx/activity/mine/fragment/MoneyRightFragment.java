package com.konglianyuyin.mx.activity.mine.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.mine.BindAliPayNewActivity;
import com.konglianyuyin.mx.activity.mine.CashHisActivity;
import com.konglianyuyin.mx.activity.mine.CashMoneyActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.AliInfor;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.ChaMoneyBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.AuthResult;
import com.konglianyuyin.mx.utils.BaseUtils;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.view.ShapeTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.JIEBANGALI;

public class MoneyRightFragment extends MyBaseArmFragment {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.textBiNum)
    TextView textBiNum;
    @BindView(R.id.imgJilu)
    TextView imgJilu;
    @BindView(R.id.imgDuihuan)
    TextView imgDuihuan;
    @BindView(R.id.imgTixian)
    TextView imgTixian;
    @BindView(R.id.textAliName)
    TextView textAliName;
    @BindView(R.id.llBang)
    LinearLayout llBang;
    Unbinder unbinder;


    private MoneyBean mMoneyBean;
    private int allSign = 0;
//    private TextView uselessText;
//    private TextView zengText;
    private TextView zuanNum;
    private String moneyStr;

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_right, container, false);
        return view;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        loadData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    Handler moneyHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getMoney(moneyStr);
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (Constant.CHONGZHISHUAXIN.equals(tag)) {
            loadData();
        } else if (JIEBANGALI.equals(tag)) {
            loadData();
        }
    }

    private void getMoney(String money) {
        RxUtils.loading(commonModel.getMoney(money), this)
                .subscribe(new ErrorHandleSubscriber<ChaMoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(ChaMoneyBean chaMoneyBean) {
                        if (chaMoneyBean.getCode() == 1) {
//                            if (chaMoneyBean.getData().getGive() == 0) {
//                                uselessText.setVisibility(View.GONE);
//                                zengText.setVisibility(View.GONE);
//                            } else {
//                                uselessText.setVisibility(View.VISIBLE);
//                                zengText.setVisibility(View.VISIBLE);
//                            }
                            zuanNum.setText(String.valueOf(chaMoneyBean.getData().getMizuan()));
//                            zengText.setText(String.valueOf(chaMoneyBean.getData().getGive()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
//                        uselessText.setVisibility(View.GONE);
//                        zengText.setVisibility(View.GONE);
                    }
                });
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult payResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.debugInfo("sgm", "====result" + resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")
                            && TextUtils.equals(payResult.getResultCode(), "200")) {
                        loadInfor(payResult.getAuthCode());
                    } else {
                        // 失败。
                        showToast("授权失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadInfor(String code) {
        RxUtils.loading(commonModel.ali_oauth_token(code, UserManager.getUser().getUserId() + ""), this)
                .subscribe(new ErrorHandleSubscriber<AliInfor>(mErrorHandler) {
                    @Override
                    public void onNext(AliInfor aliInfor) {
                        loadData();
//                        aliInfor.getData().getAvatar();
                    }
                });
    }

    private void loadData() {
        RxUtils.loading(commonModel.my_store(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        mMoneyBean = moneyBean;
                        textBiNum.setText(moneyBean.getData().get(0).getMibi());
//                        textZuanNum.setText(moneyBean.getData().get(0).getMizuan());
                        if (!TextUtils.isEmpty(moneyBean.getData().get(0).getAli_nick_name())) {
                            textAliName.setText(moneyBean.getData().get(0).getAli_nick_name());
                        } else {
                            textAliName.setText("");
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.imgTixian, R.id.imgDuihuan, R.id.imgJilu, R.id.llBang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgTixian:
                if (mMoneyBean.getData().get(0).getIs_ali() == 1) {
                    ArmsUtils.startActivity(CashMoneyActivity.class);
                } else {
                    showToast("请先绑定支付宝！");
                }
                break;
            case R.id.imgDuihuan:
//                ToastUtil.showToast(mContext,"兑换功能未开启");
                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .customView(R.layout.dialog_duhuan, true)
                        .show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textId = (TextView) dialog.findViewById(R.id.textId);
                EditText textBiNum = (EditText) dialog.findViewById(R.id.textBiNum);
                zuanNum = (TextView) dialog.findViewById(R.id.textZuanNum);
                ShapeTextView btn_ok = (ShapeTextView) dialog.findViewById(R.id.btn_ok);
                TextView textAll = (TextView) dialog.findViewById(R.id.textAll);
                if (mMoneyBean != null) {
                    textId.setText("账户可兑换上限：" + mMoneyBean.getData().get(0).getMibi());
                    zuanNum.setText(0 + "");
                }
                textBiNum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        moneyStr = s.toString();
                        //改变完，计算
                        try {
                            if (!TextUtils.isEmpty(BaseUtils.getNumber(s.toString()))) {
                                moneyHandler.removeCallbacks(runnable);
                                moneyHandler.postDelayed(runnable, 300);
                            } else {
                                zuanNum.setText("0");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                btn_ok.setOnClickListener(v -> {

                    RxUtils.loading(commonModel.exchange(String.valueOf(UserManager.getUser().getUserId()),
                            textBiNum.getText().toString()), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean moneyBean) {
                                    dialog.dismiss();
                                    showToast(moneyBean.getMessage());
                                    EventBus.getDefault().post(new FirstEvent("指定发送", Constant.CHONGZHISHUAXIN));
                                }
                            });
                });
                textAll.setOnClickListener(v -> {
                    textBiNum.setText(String.valueOf(mMoneyBean.getData().get(0).getMibi()));
                });
                break;
            case R.id.imgJilu:
                ArmsUtils.startActivity(CashHisActivity.class);
                break;
            case R.id.llBang:
                Intent intent = new Intent(getActivity(), BindAliPayNewActivity.class);
                if (mMoneyBean.getData().get(0).getIs_ali() == 1) {
                    intent.putExtra("zhanghu", mMoneyBean.getData().get(0).getAli_user_id());
                    intent.putExtra("name", mMoneyBean.getData().get(0).getAli_nick_name());
                }
//                    intent.putExtra("phone",mMoneyBean.getData().get(0).get)
                ArmsUtils.startActivity(intent);

//                if (mMoneyBean.getData().get(0).getIs_ali() == 1) {
//                    Intent intent = new Intent(getActivity(), BindSuccessActivity.class);
//                    intent.putExtra("head", mMoneyBean.getData().get(0).getAli_avatar());
//                    intent.putExtra("name", mMoneyBean.getData().get(0).getAli_nick_name());
////                    intent.putExtra("phone",mMoneyBean.getData().get(0).get)
//                    ArmsUtils.startActivity(intent);
//                } else {
//                    RxUtils.loading(commonModel.ali_oauth_code(null), this)
//                            .subscribe(new ErrorHandleSubscriber<BindBean>(mErrorHandler) {
//                                @Override
//                                public void onNext(BindBean moneyBean) {
//                                    Runnable payRunnable = new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            AuthTask alipay = new AuthTask(getActivity());
//                                            Map<String, String> result = alipay.authV2(moneyBean.getData().getSign(), true);
//                                            Message msg = new Message();
//                                            msg.what = SDK_PAY_FLAG;
//                                            msg.obj = result;
//                                            mHandler.sendMessage(msg);
//                                        }
//                                    };
//                                    // 必须异步调用
//                                    Thread payThread = new Thread(payRunnable);
//                                    payThread.start();
//                                }
//                            });
//                }

                break;
        }
    }

}
