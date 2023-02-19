package com.konglianyuyin.mx.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.MainActivity;
import com.konglianyuyin.mx.activity.message.LiaoBaExtensionModule;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CodeBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.Login;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.OtherBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.SharedPreferencesUtils;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.konglianyuyin.mx.utils.VerificationUtils;
import com.konglianyuyin.mx.view.ClearEditText;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.kongzue.dialog.v3.MessageDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.REGISTER;


public class LoginActivity extends MyBaseArmActivity {

    public static final String tag = "LoginActivity";
    @Inject
    CommonModel commonModel;
    @BindView(R.id.edt_login_name)
    EditText edtLoginName;
    @BindView(R.id.textSend)
    TextView textSend;
    @BindView(R.id.edt_login_pass)
    ClearEditText edtLoginPass;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;
    @BindView(R.id.textForget)
    TextView textForget;
    @BindView(R.id.textRegister)
    TextView textRegister;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.oneline)
    View oneline;
    @BindView(R.id.twoline)
    View twoline;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.iv_agree)
    ImageView ivAgree;
    @BindView(R.id.tv_agree)
    TextView tvAgree;


    private String openid, nackName, iconurl, type;
    private boolean isAgree;
    private boolean mUShareInit;
    private CountDownTimer timer;

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
        return R.layout.activity_login;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        if (getIntent().getIntExtra("sign", 0) == 1) {
            edtLoginName.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MessageDialog.show(LoginActivity.this, "下线通知", "您的账号在别的设备登录，您已被迫下线", "确定");
                }
            }, 2000);
        }
        SharedPreferencesUtils.setParam(this, "isFirstIn", false);
        setAgreementTextClick();
        //读取本地用户信息
        UserManager.initData();
        if (UserManager.IS_LOGIN) {
            ArmsUtils.startActivity(MainActivity.class);
            finish();
        }
        try {
            edtLoginName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String pasStr = edtLoginPass.getText().toString();
                    if (TextUtils.isEmpty(s.toString().replace(" ", "").trim()) || TextUtils.isEmpty(pasStr)) {
                        btnOk.setEnabled(false);
                    } else {
                        btnOk.setEnabled(true);
                    }
                    if (TextUtils.isEmpty(s.toString().replace(" ", ""))) {
                        oneline.setBackgroundColor(getResources().getColor(R.color.line));
                    } else {
                        oneline.setBackgroundColor(getResources().getColor(R.color.app_bg));
                    }
                }
            });
            edtLoginPass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String nameStr = edtLoginName.getText().toString();
                    if (TextUtils.isEmpty(s.toString().replace(" ", "").trim()) || TextUtils.isEmpty(nameStr)) {
                        btnOk.setEnabled(false);
                    } else {
                        btnOk.setEnabled(true);
                    }
                    if (TextUtils.isEmpty(s.toString().replace(" ", ""))) {
                        twoline.setBackgroundColor(getResources().getColor(R.color.line));
                    } else {
                        twoline.setBackgroundColor(getResources().getColor(R.color.app_bg));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            btnOk.setEnabled(true);
            twoline.setBackgroundColor(getResources().getColor(R.color.app_bg));
            oneline.setBackgroundColor(getResources().getColor(R.color.app_bg));
        }

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


    @OnClick({R.id.btn_ok, R.id.textForget, R.id.textRegister, R.id.img1, R.id.img2, R.id.img3,R.id.iv_agree,R.id.textSend})
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
                        RxUtils.loading(commonModel.verification(phone1, ""), this)
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
                String code = edtLoginPass.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showMessage("账号不能为空");
                }  else if (!VerificationUtils.VildateMobile(phone)) {
                    showMessage("账号输入不合法");
                } else {
                    showDialogLoding();
                    RxUtils.loading(
                            commonModel.login(phone, code), this)
                            .subscribe(new ErrorHandleSubscriber<Login>(mErrorHandler) {
                                @Override
                                public void onNext(Login userInfo) {
                                    disDialogLoding();
                                    //设置融云用户信息
//                                    initRooIm(userInfo);

//                                    Uri mediaUriFromPath = BaseUtils.getMediaUriFromPath(LoginActivity.this,
//                                            userInfo.getData().getHeadimgurl());

//                                    Uri mediaUriFromPath = Uri.parse(userInfo.getData().getHeadimgurl());
//
//                                    UserInfo user = new UserInfo(String.valueOf(userInfo.getData().getId()),
//                                            userInfo.getData().getNickname(),mediaUriFromPath);
//                                    RongIM.getInstance().setCurrentUserInfo(user);


                                    showToast("登录成功");
                                    //用户信息存入数据库
                                    LoginData loginData = new LoginData();
                                    loginData.setBirthday(userInfo.getData().getBirthday());
                                    loginData.setCity(userInfo.getData().getCity());
                                    loginData.setHeadimgurl(userInfo.getData().getHeadimgurl());
                                    loginData.setIntroduction(userInfo.getData().getIntroduction());
                                    loginData.setIs_room(userInfo.getData().getIs_room());
                                    loginData.setLevel(userInfo.getData().getLevel());
                                    loginData.setMizuan(userInfo.getData().getMizuan());
                                    loginData.setNickname(userInfo.getData().getNickname());
                                    loginData.setOpenid(userInfo.getData().getOpenid());
                                    loginData.setUserId(userInfo.getData().getId());
                                    loginData.setPass(userInfo.getData().getPass());
                                    loginData.setPhone(userInfo.getData().getPhone());
                                    loginData.setSex(userInfo.getData().getSex());
                                    loginData.setRy_token(userInfo.getData().getRy_token());
                                    loginData.setToken(userInfo.getData().getToken());
                                    loginData.setNewtoken(userInfo.getData().getNewtoken());
//                                    if(TextUtils.isEmpty(userInfo.getData().getProvince())){
//                                        loginData.setPr(userInfo.getData().getProvince());
//                                    }
//                                    if(TextUtils.isEmpty(userInfo.getData().getCountry())){
//                                        loginData.set(userInfo.getData().getCountry());
//                                    }
                                    LitePal.deleteAll(LoginData.class);
                                    loginData.save();//litepal数据库，不能随便改LoginData数据
                                    UserManager.initData();//存储完，初始化
                                    EventBus.getDefault().post(new FirstEvent("指定发送", Constant.LOGIN));
                                    ArmsUtils.startActivity(MainActivity.class);
                                    finish();
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    disDialogLoding();
                                }
                            });
                }
                break;
            case R.id.textForget:
                ArmsUtils.startActivity(ForgetPsActivity.class);
                break;
            case R.id.textRegister:
                ArmsUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.img1:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.img2:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.img3:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, authListener);
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

            default:
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
                //用户协议
                Intent intent = new Intent(LoginActivity.this, BaseWebActivity.class);
                intent.putExtra("url", Api.APP_DOMAIN_NoApi+"/index/index/show_content?id=3");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            } else {
                //隐私协议
                Intent intent2 = new Intent(LoginActivity.this, BaseWebActivity.class);
                intent2.putExtra("url", Api.APP_DOMAIN_NoApi+"/index/index/show_content?id=5");
                intent2.putExtra("title", "隐私协议");
                startActivity(intent2);

            }
        }
    }

    /**
     * 初始化融云
     *
     * @param userInfo
     */
    private void initRooIm(Login userInfo) {
        RongIM.connect(userInfo.getData().getRy_token(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtils.debugInfo("TAG", "====token错误");
            }

            @Override
            public void onSuccess(String userid) {
                LogUtils.debugInfo("TAG", "--onSuccess" + userid);

                Uri mediaUriFromPath = Uri.parse(userInfo.getData().getHeadimgurl());

                UserInfo user = new UserInfo(String.valueOf(userInfo.getData().getId()),
                        userInfo.getData().getNickname(), mediaUriFromPath);

                RongIM.getInstance().setCurrentUserInfo(user);

//                UserInfo user = new UserInfo(userid,
//                        UserManager.getUser().getNickname(), Uri.parse(UserManager.getUser().getHeadimgurl()));
//                RongIM.getInstance().setCurrentUserInfo(user);
//                RongIM.getInstance().setMessageAttachedUserInfo(true);
                registerExtensionPlugin();
                RongIM.registerMessageTemplate(new MyTextMessageItemProvider());
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.debugInfo("TAG", "--Error" + errorCode);
            }
        });

    }

    /**
     * 删除扩展区域
     */
    private void registerExtensionPlugin() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new LiaoBaExtensionModule());
            }
        }
    }

    /**
     * 聊天字体颜色
     */
    @ProviderTag(messageContent = TextMessage.class, showReadState = true)
    public class MyTextMessageItemProvider extends TextMessageItemProvider {

        @Override
        public void bindView(View v, int position, TextMessage content, UIMessage data) {
            super.bindView(v, position, content, data);
            TextView textView = (TextView) v;
            if (data.getMessageDirection() == Message.MessageDirection.SEND) {
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setTextColor(Color.BLACK);
            }
        }
    }

    private UMAuthListener authListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA platform) {
            LogUtils.debugInfo("onStart" + platform);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            int a = 0;

            if (data == null) {
                return;
            }
            if (SHARE_MEDIA.QQ == platform) {
                if (data.get("gender").equals("男")) {
                    logOther(data.get("uid"), "qq", data.get("name"), data.get("iconurl"), "1");
                } else {
                    logOther(data.get("uid"), "qq", data.get("name"), data.get("iconurl"), "2");
                }

            } else if (SHARE_MEDIA.WEIXIN == platform) {
                if (data.get("gender").equals("男")) {
                    logOther(data.get("uid"), "wx", data.get("name"), data.get("iconurl"), "1");
                } else {
                    logOther(data.get("uid"), "wx", data.get("name"), data.get("iconurl"), "2");
                }
            } else if (SHARE_MEDIA.SINA == platform) {
                if (data.get("gender").equals("男")) {
                    logOther(data.get("uid"), "wb", data.get("name"), data.get("iconurl"), "1");
                } else {
                    logOther(data.get("uid"), "wb", data.get("name"), data.get("iconurl"), "2");
                }

            } else {
                Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtils.debugInfo("onError" + t.getMessage());
            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            LogUtils.debugInfo("onCancel" + "取消了");
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void logOther(String uid, String typa, String name, String iconurl, String gender) {
        RxUtils.loading(commonModel.logOther(uid, typa), this)
                .subscribe(new ErrorHandleSubscriber<OtherBean>(mErrorHandler) {
                    @Override
                    public void onNext(OtherBean login) {
                        LogUtils.debugInfo("====错了吗", login.getMessage());
                        if (login.getCode() == 1) {
                            LoginData loginData = new LoginData();
                            loginData.setHeadimgurl(login.getData().getHeadimgurl());
                            loginData.setNickname(login.getData().getNickname());
                            loginData.setUserId(login.getData().getId());
                            loginData.setPhone(login.getData().getPhone());
                            loginData.setRy_token(login.getData().getRy_token());
                            loginData.setToken(login.getData().getToken());
                            loginData.setNewtoken(login.getData().getNewtoken());
                            LitePal.deleteAll(LoginData.class);
                            loginData.save();//litepal数据库，不能随便改LoginData数据
                            UserManager.initData();//存储完，初始化
                            EventBus.getDefault().post(new FirstEvent("指定发送", Constant.LOGIN));
                            ArmsUtils.startActivity(MainActivity.class);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Intent intent = new Intent(LoginActivity.this, BingPhoneActivity.class);
                        intent.putExtra("openid", uid);
                        intent.putExtra("type", typa);
                        intent.putExtra("gender", gender);
                        intent.putExtra("nackName", name);
                        intent.putExtra("iconurl", iconurl);
                        ArmsUtils.startActivity(intent);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (REGISTER.equals(tag)) {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setVisibility(View.GONE);
        toolbarBack.setVisibility(View.GONE);
        if (TextUtils.isEmpty(edtLoginName.getText().toString().replace(" ", "")) || TextUtils.isEmpty(edtLoginPass.getText().toString())) {
            btnOk.setEnabled(false);
        } else {
            btnOk.setEnabled(true);
        }
    }
}
