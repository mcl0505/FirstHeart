package com.konglianyuyin.mx.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.CodeBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.Register;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.SharedPreferencesUtils;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.konglianyuyin.mx.view.ShapeTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.REGISTER;

public class RegisterActivity extends MyBaseArmActivity {

    public static final String tag = "RegisterActivity";
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rightTitle)
    TextView rightTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.textSend)
    TextView textSend;
    @BindView(R.id.edt_login_name)
    EditText edtLoginName;
    @BindView(R.id.edt_login_pw)
    EditText edtLoginPw;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;
    @BindView(R.id.iv_agree)
    ImageView ivAgree;
    @BindView(R.id.tv_agree)
    TextView tvAgree;

    @Inject
    CommonModel commonModel;

    @BindView(R.id.one_line)
    View oneLine;
    @BindView(R.id.two_line)
    View twoLine;
    @BindView(R.id.three_line)
    View threeLine;

    private CountDownTimer timer;
    private boolean isAgree;

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
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SharedPreferencesUtils.setParam(this, "isFirstIn", false);
        setAgreementTextClick();
        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameStr = edtLoginName.getText().toString().replace(" ", "").trim();
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(nameStr)) {
                    btnOk.setEnabled(false);
                } else {
                    btnOk.setEnabled(true);
                }
                if (TextUtils.isEmpty(s.toString().replace(" ", ""))) {
                    twoLine.setBackgroundColor(getResources().getColor(R.color.line));
                } else {
                    twoLine.setBackgroundColor(getResources().getColor(R.color.app_bg));
                }
            }
        });
//        edtLoginPw.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String codaStr = edtCode.getText().toString().replace(" ", "").trim();
//                String nameStr = edtLoginName.getText().toString().replace(" ", "").trim();
//                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(codaStr) || TextUtils.isEmpty(nameStr)) {
//                    btnOk.setEnabled(false);
//                } else {
//                    btnOk.setEnabled(true);
//                }
//                if (TextUtils.isEmpty(s.toString().replace(" ", ""))) {
//                    threeLine.setBackgroundColor(getResources().getColor(R.color.line));
//                } else {
//                    threeLine.setBackgroundColor(getResources().getColor(R.color.font_ff3e6d));
//                }
//            }
//        });
        edtLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String codaStr = edtCode.getText().toString().replace(" ", "").trim();
//                String pwStr = edtLoginPw.getText().toString().replace(" ", "").trim();
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(codaStr)) {
                    btnOk.setEnabled(false);
                } else {
                    btnOk.setEnabled(true);
                }
                if (TextUtils.isEmpty(s.toString().replace(" ", ""))) {
                    oneLine.setBackgroundColor(getResources().getColor(R.color.line));
                } else {
                    oneLine.setBackgroundColor(getResources().getColor(R.color.app_bg));
                }
            }
        });
    }


    @OnClick({R.id.textSend, R.id.btn_ok,R.id.iv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textSend:
                hideKeyboard(btnOk);
                String phone1 = edtLoginName.getText().toString().replace(" ", "");
                if (TextUtils.isEmpty(phone1)) {
                    ArmsUtils.snackbarText("账号不能为空");
                } else {
                    if (TextUtils.equals(textSend.getText().toString(), "发送验证码")
                            || TextUtils.equals(textSend.getText().toString(), "重新发送")) {
                        showDialogLoding();
                        RxUtils.loading(commonModel.verification(phone1, "reg"), this)
                                .subscribe(new ErrorHandleSubscriber<CodeBean>(mErrorHandler) {

                                    @Override
                                    public void onNext(CodeBean codeBean) {
                                        disDialogLoding();
                                        showCode();
                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                        super.onError(t);
                                        disDialogLoding();
                                    }
                                });
                    }
                }
                break;
            case R.id.btn_ok:
                if(!isAgree){
                    ToastUtil.showToast(this,"请先同意用户协议和隐私政策");
                    return;
                }
                String phone = edtLoginName.getText().toString().replace(" ", "");
                String phoneCode = edtCode.getText().toString().replace(" ", "");
                if (TextUtils.isEmpty(phone)) {
                    ArmsUtils.makeText(this, "账号不能为空");
                } else if (TextUtils.isEmpty(phoneCode)) {
                    ArmsUtils.snackbarText("手机验证码不能为空");
                } else {
                    showDialogLoding();
                    RxUtils.loading(commonModel.is_verification(phone, phoneCode), this)
                            .subscribe(new ErrorHandleSubscriber<Register>(mErrorHandler) {
                                @Override
                                public void onNext(Register codeBean) {
                                    disDialogLoding();
                                    if (codeBean != null && codeBean.getCode() == 1) {
                                        Intent intent = new Intent(RegisterActivity.this, UploadActivity.class);
                                        intent.putExtra("phone", phone);
//                                            intent.putExtra("password", password);
                                        ArmsUtils.startActivity(RegisterActivity.this, intent);
                                    } else {
                                        ArmsUtils.snackbarText(codeBean.getMessage());
                                    }
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    disDialogLoding();
                                }
                            });
                }
                break;
            case R.id.iv_agree:
                if(isAgree){
                    isAgree = false;
                    ivAgree.setImageResource(R.mipmap.icon_check_normal);
                }else {
                    isAgree = true;
                    ivAgree.setImageResource(R.mipmap.icon_check_blue);
                }

                break;
        }
    }

    private void setAgreementTextClick() {
        String content = tvAgree.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        MyClickText myClickText = new MyClickText(this, 0);
        MyClickText myClickText2 = new MyClickText(this, 1);
        //传入一个MyClickText，并且继承ClickableSpan（必须这样写啊）
        spannableString.setSpan(myClickText, content.indexOf("《用户协议》"),
                content.indexOf("《用户协议》") + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(myClickText2, content.indexOf("《隐私协议》"), content.indexOf("《隐私协议》") + 6
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAgree.setText(spannableString);
//        tvAgree.setHighlightColor(getResources().getColor(R.color.transparent)); //设置点击后的颜色为透明
        tvAgree.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private class MyClickText extends ClickableSpan {
        private Context context;
        private int type = 0;

        public MyClickText(Activity mainActivity, int type) {
            this.context = mainActivity;
            this.type = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(context.getResources().getColor(R.color.app_bg));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Bundle bundle = new Bundle();
            if (type == 0) {
                //隐私协议
                Intent intent2 = new Intent(RegisterActivity.this, BaseWebActivity.class);
                intent2.putExtra("url", Api.APP_DOMAIN_NoApi+"/index/index/show_content?id=5");
                intent2.putExtra("title", "隐私协议");
                startActivity(intent2);
            } else {
                //用户协议
                Intent intent = new Intent(RegisterActivity.this, BaseWebActivity.class);
                intent.putExtra("url", Api.APP_DOMAIN_NoApi+"/index/index/show_content?id=3");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    public void showCode() {
        ArmsUtils.snackbarText("发送成功！");
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int i = (int) (millisUntilFinished / 1000);
                textSend.setText(i + "s");
            }

            @Override
            public void onFinish() {
                textSend.setText("重新发送");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (REGISTER.equals(tag)) {
            finish();
        }
    }
}
