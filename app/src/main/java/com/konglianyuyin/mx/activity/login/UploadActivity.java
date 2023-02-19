package com.konglianyuyin.mx.activity.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.MainActivity;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.Login;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.BaseUtils;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.PhotoWindow;
import com.konglianyuyin.mx.utils.SdcardTools;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.REGISTER;

/**
 * 完善资料
 */
public class UploadActivity extends MyBaseArmActivity {


    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.edt_login_name)
    EditText edtLoginName;
    @BindView(R.id.edt_invite_code)
    EditText edtInviteCode;
    @BindView(R.id.edt_code)
    TextView edtCode;
    @BindView(R.id.rl_data)
    RelativeLayout rlData;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;

    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img3)
    ImageView img3;

    String phone;
//    String password;
    String type;
    String uid;
    String tag;
    String coad;
    private String sex;
    String nowDate;
    String nickName;

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList = new ArrayList<>();
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
        return R.layout.activity_upload;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
//        password = getIntent().getStringExtra("password");
        type = getIntent().getStringExtra("type");
        uid = getIntent().getStringExtra("uid");
        tag = getIntent().getStringExtra("tag");
        coad = getIntent().getStringExtra("coad");
    }

    @OnClick({R.id.img, R.id.rl_data, R.id.btn_ok,R.id.img1, R.id.img3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img:
                showPop();
                hideKeyboard(img);
                break;
            case R.id.rl_data:
                hideKeyboard(img);
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UploadActivity.this, (date, v) -> {
                    nowDate = BaseUtils.getNowDate(date);
                    edtCode.setText(nowDate);
                }).build();
                pvTime.show();
                break;
            case R.id.btn_ok:
                nickName = edtLoginName.getText().toString().replace(" ", "").trim();
                if (TextUtils.isEmpty(nowDate)) {
                    showToast("请选择出生日期！");
                } else if (selImageList.size() == 0) {
                    showToast("请选择头像！");
                } else if (TextUtils.isEmpty(nickName)) {
                    showToast("请填写昵称！");
                } else if (nickName.length() > 6) {
                    showToast("用户名最多只有6位");
                } else {

                    LogUtils.debugInfo("sgm:" + BaseUtils.file2Base64(selImageList.get(0).path));
                    showDialogLoding();
                    RxUtils.loading(commonModel.register(phone, sex, nowDate, nickName,
                            "data:image/png;base64," + BaseUtils.file2Base64(selImageList.get(0).path), "android",
                            Api.CHANNEL,coad,edtInviteCode.getText().toString().trim()), this)
                            .subscribe(new ErrorHandleSubscriber<Login>(mErrorHandler) {
                                @Override
                                public void onNext(Login codeBean) {
                                        disDialogLoding();
                                        if (codeBean.getCode() == 1) {
                                            showMessage(codeBean.getMessage());
//                                            loginGO();
                                            loginGO2(codeBean);
                                        } else {
                                            showMessage(codeBean.getMessage());
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
            case R.id.img1:
                new MaterialDialog.Builder(this)
                        .title("")
                        .content("性别一经确认无法修改哦~")
                        .positiveText("确认")
                        .negativeText("我再想想")
                        .onPositive((dialog, which) -> {
                            sex="1";

                        })
                        .show();
                if (img2.getVisibility() == View.VISIBLE) {

                } else {
                    img2.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.GONE);
                }
                break;
            case R.id.img3:
                new MaterialDialog.Builder(this)
                        .title("")
                        .content("性别一经确认无法修改哦~")
                        .positiveText("确认")
                        .negativeText("我再想想")
                        .onPositive((dialog, which) -> {
                            sex="2";
                        })
                        .show();
                if (img4.getVisibility() == View.VISIBLE) {

                } else {
                    img2.setVisibility(View.GONE);
                    img4.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void loginGO(){
        showDialogLoding();
        RxUtils.loading(
                commonModel.login(phone, ""), this)
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
                        EventBus.getDefault().post(new FirstEvent("指定发送", REGISTER));
                        finish();
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

    private void loginGO2(Login userInfo){
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
        EventBus.getDefault().post(new FirstEvent("指定发送", REGISTER));
        finish();
        ArmsUtils.startActivity(MainActivity.class);
        finish();
    }


    private void showPop() {
        PhotoWindow photoWindow = new PhotoWindow(this);
        photoWindow.showAtLocation(img, Gravity.BOTTOM, 0, 0);
        photoWindow.getTake_photo().setOnClickListener(v -> {
            photoWindow.dismiss();
            if (SdcardTools.sdcard()) {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                //相机
                                Intent intent = new Intent(UploadActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                            }
                        });
            } else {
                Toast.makeText(this, "sd卡不可用", Toast.LENGTH_SHORT).show();
            }
        });
        photoWindow.getChose_pic().setOnClickListener(v -> {
            photoWindow.dismiss();
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            // 跳转到相册
                            ImagePicker.getInstance().setSelectLimit(1);
                            ImagePicker.getInstance().setMultiMode(false);
                            Intent intent = new Intent(UploadActivity.this, ImageGridActivity.class);
                            //显示选中的图片
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                        }
                    });
        });
        photoWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 1f;
            getWindow().setAttributes(params);
        });
    }


    /*-----------------------图片选择回调------------------------------*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ArrayList<ImageItem> tempList;
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (requestCode == REQUEST_CODE_SELECT) {
                tempList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (tempList == null) {
                    return;
                }
                selImageList.clear();
                selImageList.addAll(tempList);
                if (selImageList.size() > 0) {
                    GlideArms.with(UploadActivity.this)
                            .load(selImageList.get(0).path)
                            .placeholder(R.drawable.ic_default_image)
                            .error(R.drawable.ic_default_image)
                            .circleCrop()
                            .into(img);
                }
            }
        }
    }


}
