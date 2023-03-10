package com.konglianyuyin.mx.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.login.LoginActivity;
import com.konglianyuyin.mx.activity.message.LiaoBaExtensionModule;
import com.konglianyuyin.mx.activity.message.RedPackageMessageItemProvider;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.DanMuViewHolder;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.PushBean;
import com.konglianyuyin.mx.bean.UpdateApkBean;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.floatingview.EnFloatingView;
import com.konglianyuyin.mx.floatingview.FloatingMagnetView;
import com.konglianyuyin.mx.floatingview.FloatingView;
import com.konglianyuyin.mx.floatingview.MagnetViewListener;
import com.konglianyuyin.mx.fragment.HomeMessageFragment;
import com.konglianyuyin.mx.fragment.MainCenterFragment;
import com.konglianyuyin.mx.fragment.MainFindFragment;
import com.konglianyuyin.mx.fragment.MainHomeNewFragment;
import com.konglianyuyin.mx.fragment.MainHomeRankFragmentNew;
import com.konglianyuyin.mx.http.HttpUtil;
import com.konglianyuyin.mx.activity.message.RedPackageMessage;
import com.konglianyuyin.mx.popup.WarningDialog;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.ActivityUtils;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.EncodeUtils;
import com.konglianyuyin.mx.utils.FastJsonUtils;
import com.konglianyuyin.mx.utils.SharedPreferencesUtils;
import com.orient.tea.barragephoto.adapter.AdapterListener;
import com.orient.tea.barragephoto.adapter.BarrageAdapter;
import com.orient.tea.barragephoto.ui.BarrageView;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.app.Api.APP_DOMAIN;
import static com.konglianyuyin.mx.utils.Constant.FANHUIZHUYE;
import static com.konglianyuyin.mx.utils.Constant.KBXTUISONG;
import static com.konglianyuyin.mx.utils.Constant.LOGOUT;
import static com.konglianyuyin.mx.utils.Constant.TUISONG;
import static com.konglianyuyin.mx.utils.Constant.XUANFUYINCANG;

public class MainActivity extends MyBaseArmActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.frameLayout_main)
    FrameLayout frameLayoutMain;
    @BindView(R.id.radio_home)
    RadioButton radioHome;
    @BindView(R.id.radio_finder)
    RadioButton radioFinder;
    @BindView(R.id.radio_shequ)
    RadioButton radioShequ;
    @BindView(R.id.radio_message)
    RadioButton radioMessage;
    @BindView(R.id.radio_center)
    RadioButton radioCenter;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.barrage)
    BarrageView barrageView;

    private CircularImage imgHeader;
    private ImageView img1, img2;
    @Inject
    CommonModel commonModel;
//    MainHomeFragment mainHomeFragment = new MainHomeFragment();
MainHomeNewFragment mainHomeFragment = new MainHomeNewFragment();
    MainFindFragment mainFindFragment = new MainFindFragment();
    //    MainCommunityFragment mainCommunityFragment = new MainCommunityFragment();
    MainHomeRankFragmentNew mainHomeRankFragmentNew = new MainHomeRankFragmentNew();
    HomeMessageFragment mainMessageFragment = new HomeMessageFragment();
    MainCenterFragment mainCenterFragment = new MainCenterFragment();

    private BarrageAdapter<PushBean> mAdapter;
    private UserManager userManager;

    List<PushBean> mPushBeanList = new Vector<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        //?????????Http
        HttpUtil.init();

        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return true;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initShare(getApplication());

        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.radio_home);

        //???????????????
        initDanmu();

        LoginData loginData = UserManager.getUser();
        LogUtils.debugInfo(JSON.toJSONString(loginData));

        if (!TextUtils.isEmpty(loginData.getRy_token())) {

            LogUtils.debugInfo(TAG + "ry_token: " + loginData.getRy_token());
            RongIMClient.setRCLogInfoListener(s -> System.out.println("--------" + s));
            RongIM.connect(loginData.getRy_token(), new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    LogUtils.debugInfo(TAG + "onTokenIncorrect");
                    LogUtils.debugInfo("0");
                }

                @Override
                public void onSuccess(String userid) {
                    LogUtils.debugInfo(TAG + "onSuccess???" + userid);
                    LogUtils.debugInfo("TAG", "--onSuccess" + userid);
                    //??????????????????
//                    UserInfo user = new UserInfo(userid,
//                            loginData.getNickname(), Uri.parse(loginData.getHeadimgurl()));
//                    RongIM.getInstance().setCurrentUserInfo(user);
//                    RongIM.getInstance().setMessageAttachedUserInfo(true);

                    registerExtensionPlugin();

                    RongIM.registerMessageTemplate(new MyTextMessageItemProvider());

                    RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                        @Override
                        public boolean onReceived(Message message, int i) {
                            MessageContent content = message.getContent();
                            LogUtils.debugInfo("MainActivity",message.toString());
                            return false;
                        }
                    });

                    if (ExtConfig.isRegisterMsg){
                        //?????????????????????
                        RongIM.registerMessageType(RedPackageMessage.class);
                        RongIM.registerMessageTemplate(new RedPackageMessageItemProvider());
                    }


                    //        //????????????????????????????????????
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {
                            //            LogUtils.debugInfo("sgm","====??????????????????" + userId);
                            LogUtils.debugInfo("====??????????????????", s);
                            return findUserId(s);//?????? userId ?????????????????????????????????????????????????????????????????? SDK???
                        }
                    }, true);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.debugInfo("TAG", "--onError" + errorCode);
                    LogUtils.debugInfo(TAG + "onError???" + errorCode.getMessage() + " _ " + errorCode.getValue());
                    LogUtils.debugInfo("TAG", "--onError" + errorCode);
                }
            });
        }

        //????????????????????????
        if (UserManager.IS_LOGIN && TextUtils.isEmpty(UserManager.getUser().getToken())) {//???????????????token?????????????????????
            UserManager.layout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void initShare(Application application) {
        UMConfigure.init(application, "6077a0f22dfb8509d34e6522"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "27bc03222dc7b466fff52fc8a04ca913");
        //??????????????????????????????
        PushAgent mPushAgent = PushAgent.getInstance(application);
        //?????????????????????????????????register???????????????????????????
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //?????????????????????deviceToken deviceToken??????????????????????????????
                LogUtils.debugInfo("sgm", "??????====???????????????deviceToken???-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.debugInfo("sgm", "??????====???????????????-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        PlatformConfig.setWeixin("wx7b991e43bc9b5814", "e182f7a8ebb31ddf44632ce3404c0467");
        PlatformConfig.setQQZone("101813872", "8358b3268e4a3153181b2d35f1acafc8");
        PlatformConfig.setSinaWeibo("3416535971", "f66ee46acae3d312dabc33637b5b1304", "http://sns.whalecloud.com");

        mPushAgent.setMessageHandler(messageHandler);
    }

    UmengMessageHandler messageHandler = new UmengMessageHandler() {

        @Override
        public Notification getNotification(Context context, UMessage msg) {
            Map<String, String> extra = msg.extra;
            String test = extra.get("");
            LogUtils.debugInfo("====?????????" + extra.toString());
            if (!TextUtils.isEmpty(test)) {

                try {
                    JSONObject userJson = JSONObject.parseObject(test);
                    PushBean pushBean = JSON.toJavaObject(userJson, PushBean.class);
                    if (TextUtils.equals(pushBean.type, "")) {
                        EventBus.getDefault().post(new FirstEvent(pushBean, TUISONG));
                    } else if (TextUtils.equals(pushBean.type, "")) {
                        EventBus.getDefault().post(new FirstEvent(pushBean, KBXTUISONG));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.getNotification(context, msg);
        }
    };


    /**
     * ????????????
     */
    private UserInfo findUserId(String userId) {
        LogUtils.debugInfo("====???????????????");

        UserInfo userInfo;

        String userListStr = (String) SharedPreferencesUtils.getParam(MainActivity.this, "rim_info", "");

        Map<String, Object> userMap = new HashMap<>();

        if (!TextUtils.isEmpty(userListStr)) {//?????????????????????

            userListStr = new String(EncodeUtils.base64Decode(userListStr));//?????????

            LogUtils.debugInfo("====?????????????????????????????????" + userListStr);

            userMap = FastJsonUtils.json2Map(userListStr);

            int m = 0;

            for (String key : userMap.keySet()) {

                if (TextUtils.equals(userId, key)) {

                    String userJson = (String) userMap.get(key);

                    if (!TextUtils.isEmpty(userJson)) {
                        m = 2;

                        UserBean userBean = JSON.parseObject(userJson, UserBean.class);

                        userInfo = new UserInfo(userBean.getData().getId() + "",
                                userBean.getData().getNickname(),
                                Uri.parse(userBean.getData().getHeadimgurl()));

                        LogUtils.debugInfo("====?????????????????????????????????" + userJson);

                        return userInfo;

                    }
                    break;
                }
            }

            if (m == 0) {//???????????????
                getOtherUser(userId, userMap);
            }

        } else {
            getOtherUser(userId, userMap);
        }


//        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId.getUserId() + "",
//                userId.getNickname(),
//                Uri.parse(userId.getHeadimgurl())));
        return null;
    }

    private void getOtherUser(String userId, Map<String, Object> userMap) {

        RxUtils.loading(commonModel.get_user_info(userId), this)
                .subscribe(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(UserBean userBean) {

                        Log.e("?????????????????????======", userBean.getData().getNickname());
                        UserInfo userInfo = new UserInfo(userBean.getData().getId() + "",
                                userBean.getData().getNickname(),
                                Uri.parse(userBean.getData().getHeadimgurl()));

                        String userStrs = JSON.toJSONString(userBean);//????????????

                        userMap.put(userId, userStrs);

                        String mapsJsonStr = FastJsonUtils.map2Json(userMap);

                        String baseStr = EncodeUtils.base64Encode2String(mapsJsonStr.getBytes());//??????

                        LogUtils.debugInfo("??????????????????" + mapsJsonStr);

                        SharedPreferencesUtils.setParam(MainActivity.this, "rim_info", baseStr);

                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("???????????????????????????======", "0000");
                    }
                });
    }

    /**
     * ??????
     */
    private void initDanmu() {
        BarrageView.Options options = new BarrageView.Options()
                .setGravity(BarrageView.GRAVITY_TOP) // ?????????????????????
                .setInterval(600)  // ???????????????????????????
                .setSpeed(200, 29) // ????????????????????????
                .setModel(BarrageView.MODEL_COLLISION_DETECTION)     // ????????????????????????
                .setRepeat(1)// ???????????? ?????????1??? -1 ???????????????
                .setClick(true);// ??????????????????????????????
        barrageView.setOptions(options);

        barrageView.setAdapter(mAdapter = new BarrageAdapter<PushBean>(null, this) {
            @Override
            public BarrageViewHolder<PushBean> onCreateViewHolder(View root, int type) {
                return new DanMuViewHolder(root, MainActivity.this);// ?????????????????????ViewHolder
            }

            @Override
            public int getItemLayout(PushBean barrageData) {
                return R.layout.danmu;// ?????????????????????????????????
            }
        });

        // ???????????????
        mAdapter.setAdapterListener(new AdapterListener<PushBean>() {
            @Override
            public void onItemClick(BarrageAdapter.BarrageViewHolder<PushBean> holder, PushBean item) {
                if (item != null) {
                    if ("gift".equals(item.type)) {
                        enterData(item.getData().getUid() + "", "", commonModel, 1, "0");
                    }
                }
            }
        });

//        final PushBean pushBean = new PushBean();
//        PushBean.DataBean dataBean = new PushBean.DataBean();
//        dataBean.setUid(1100001);
//        dataBean.setImg("http://tp5_test.miniyuyin.cn/upload/box/39bebf5a604494481be991f44e0bb04c.png");
//        dataBean.setUser_name("zhongguo");
//        dataBean.setFrom_name("??????");
//        dataBean.setGift_name("??????");
//        dataBean.setNum(30);
//
//        pushBean.setData(dataBean);
//        pushBean.setType("gift");
//        EventBus.getDefault().post(new FirstEvent(pushBean,TUISONG));
//
//        final PushBean pushBean4 = new PushBean();
//        PushBean.DataBean dataBean4 = new PushBean.DataBean();
//        dataBean4.setUid(1100009);
//        dataBean4.setUser_name("??????");
//        dataBean4.setFrom_name("?????????");
//        dataBean4.setGift_name("??????");
//        dataBean4.setNum(30);
//
//        pushBean4.setData(dataBean4);
//        pushBean4.setType("gift");
//        EventBus.getDefault().post(new FirstEvent(pushBean4,TUISONG));
////        barrageView.postDelayed(() -> EventBus.getDefault().post(new FirstEvent(pushBean,KBXTUISONG)),1000);
////        barrageView.postDelayed(() -> mAdapter.add(pushBean), 1000);
//
//        final PushBean pushBean1 = new PushBean();
//        dataBean = new PushBean.DataBean();
//        dataBean.setUid(1100008);
//        dataBean.setUser_name("zhongguo");
//        dataBean.setBoxclass("????????????");
//        dataBean.setFrom_name("??????");
//        dataBean.setGift_name("??????X66 ????????????X99 ????????????X55 ????????????X999");
//        dataBean.setNum(30);
//
//        pushBean1.setData(dataBean);
//        pushBean1.setType("award");
//        EventBus.getDefault().post(new FirstEvent(pushBean1,KBXTUISONG));
//        final PushBean pushBean5 = new PushBean();
//        dataBean = new PushBean.DataBean();
//        dataBean.setUid(1100004);
//        dataBean.setUser_name("zhongguo");
//        dataBean.setBoxclass("????????????");
//        dataBean.setFrom_name("??????");
//        dataBean.setGift_name("??????X66 ?????????X99 ??????X55 ??????X999");
//        dataBean.setNum(30);
//
//        pushBean5.setData(dataBean);
//        pushBean5.setType("award");
//        EventBus.getDefault().post(new FirstEvent(pushBean5,KBXTUISONG));
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_home:
                ActivityUtils.addOrShowFragmentToActivity(getSupportFragmentManager(),
                        mainHomeFragment, R.id.frameLayout_main);
                break;
            case R.id.radio_finder:
                ActivityUtils.addOrShowFragmentToActivity(getSupportFragmentManager(),
                        mainFindFragment, R.id.frameLayout_main);
                break;
            case R.id.radio_shequ:
                ActivityUtils.addOrShowFragmentToActivity(getSupportFragmentManager(),
                        mainHomeRankFragmentNew, R.id.frameLayout_main);
                break;
            case R.id.radio_message:
                LogUtils.debugInfo("sgm", "====????????????");
                EventBus.getDefault().post(new FirstEvent("????????????", Constant.SHUAXINPENGYOU));
                ActivityUtils.addOrShowFragmentToActivity(getSupportFragmentManager(),
                        mainMessageFragment, R.id.frameLayout_main);
                break;
            case R.id.radio_center:
                ActivityUtils.addOrShowFragmentToActivity(getSupportFragmentManager(),
                        mainCenterFragment, R.id.frameLayout_main);
                break;
            default:
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //???????????????
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barrageView != null) {
            barrageView.destroy();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        LogUtils.debugInfo("====onRestart");
        if (AdminHomeActivity.isStart && AdminHomeActivity.isTop) {
            startActivity(new Intent(this, AdminHomeActivity.class));
        }
    }

    boolean mIsPushRuning = false;

    CountDownTimer mPushTimer = new CountDownTimer(3 * 1000, 500) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            barrageView.postDelayed(() -> mAdapter.addList(mPushBeanList), 500);
            mIsPushRuning = false;
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
//        String msg = event.getMsg();
        if (LOGOUT.equals(tag)) {
            finish();
        } else if (FANHUIZHUYE.equals(tag)) {//???????????????
            EnterRoom enterRoom = event.getEnterRoom();
            showFlow(enterRoom.getRoom_info().get(0).getRoom_cover());
        } else if (XUANFUYINCANG.equals(tag)) {//???????????????
            FloatingView.get().remove();
        } else if (TUISONG.equals(tag)) {//???????????????????????????
            PushBean pushBean = event.getPushBean();
            if (!mIsPushRuning) {//????????????
                mIsPushRuning = true;
                mPushBeanList.clear();
                mPushTimer.start();
                mPushBeanList.add(pushBean);
            } else {
                mPushBeanList.add(pushBean);
            }
            LogUtils.debugInfo("???????????????", "===" + JSON.toJSONString(pushBean));
            barrageView.postDelayed(() -> mAdapter.add(pushBean), 1000);
        } else if (KBXTUISONG.equals(tag)) { //????????????????????????????????????
            PushBean pushBean = event.getPushBean();
            if (!mIsPushRuning) {//????????????
                mIsPushRuning = true;
                mPushBeanList.clear();
                mPushTimer.start();
                mPushBeanList.add(pushBean);
            } else {
                mPushBeanList.add(pushBean);
            }
            LogUtils.debugInfo("???????????????", "===" + JSON.toJSONString(pushBean));
            barrageView.postDelayed(() -> mAdapter.add(pushBean), 1000);
        }
    }

    public void showFlow(String msg) {
        FloatingView.get().add();
        EnFloatingView view = FloatingView.get().getView();
        if(!msg.contains("http")){
            msg = APP_DOMAIN +msg;
        }
        imgHeader = view.findViewById(R.id.imgHeader);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img1.setSelected(true);
        loadImage(imgHeader, msg, R.mipmap.gender_zhuce_boy);
        img1.setOnClickListener(v -> {
            if (AdminHomeActivity.isStart) {
                if (img1.isSelected()) {
                    img1.setSelected(false);
                    AdminHomeActivity.mContext.stopTing(false);
                } else {
                    img1.setSelected(true);
                    AdminHomeActivity.mContext.stopTing(true);
                }
            }
        });
        img2.setOnClickListener(v -> {
            if (AdminHomeActivity.isStart) {
                AdminHomeActivity.isStart = false;
                AdminHomeActivity.mContext.finish();
            }
        });
        FloatingView.get().listener(new MagnetViewListener() {
            @Override
            public void onRemove(FloatingMagnetView magnetView) {
                Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(FloatingMagnetView magnetView) {
                startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
            }
        });

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        //?????????????????????????????????????????????
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        imgHeader.startAnimation(rotateAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingView.get().attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        FloatingView.get().detach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        LogUtils.debugInfo("====onResume");
        if (AdminHomeActivity.isStart && AdminHomeActivity.isTop) {
            startActivity(new Intent(this, AdminHomeActivity.class));
        }

//        checkApkVersion();
    }

    private void checkApkVersion() {
        RxUtils.loading(commonModel.checkVersion("android", DeviceUtils.getVersionName(this)))
                .subscribe(new ErrorHandleSubscriber<UpdateApkBean>(mErrorHandler) {
                    @Override
                    public void onNext(UpdateApkBean bean) {
                        com.konglianyuyin.mx.utils.LogUtils.e(TAG,bean.toString());
                        com.konglianyuyin.mx.utils.LogUtils.e(TAG,"bean.getDown() ="+bean.getDown());
                        if(bean.getDown().equals("1")){
                            goWeb(bean.getUrl());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable t) {
                        super.onError(t);
                        com.konglianyuyin.mx.utils.LogUtils.e(TAG,"t ="+t.getMessage());
                    }
                });
    }

    private void goWeb(String url) {
        WarningDialog cleardialog=new WarningDialog(MainActivity.this,"???????????????????????????????????????","??????","?????????","??????");
        cleardialog.setClicklistener(new WarningDialog.ClickListenerInterface() {
            @Override
            public void doCancel() {
                System.exit(0);
            }

            @Override
            public void doConfirm() {
                //????????????????????????
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                System.exit(0);
            }
        });
        cleardialog.show();
    }

    private MaterialDialog progress;

    private void initUpdate() {
        new PgyUpdateManager.Builder()
                .register();
        /** ????????? **/
        new PgyUpdateManager.Builder()
                .setForced(true)                //??????????????????????????????,?????????????????????????????????????????????
                .setUserCanRetry(false)         //?????????????????????????????????????????????????????? apk ?????????????????????
                .setDeleteHistroyApk(false)     // ??????????????????????????????????????? Apk??? ?????????true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //??????????????????????????????
                        Log.d("pgyer", "there is no new version");
                    }

                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //????????????????????????
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());
                        //?????????????????????DownloadFileListener ????????????
                        //?????????????????????????????????????????????????????????DownloadFileListener
                        new MaterialDialog.Builder(MainActivity.this)
                                .title("?????????????????????~")
                                .content("???????????????????????????????????????????????????????????????")
                                .positiveText("????????????")
                                .negativeText("??????")
                                .onPositive((dialog, which) -> {
                                    progress = new MaterialDialog.Builder(MainActivity.this)
                                            .title("????????????")
                                            .content("???????????????")
                                            .canceledOnTouchOutside(false)
                                            .progress(true, 0)
                                            .show();
                                    PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                })
                                .show();
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //????????????????????????
                        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        Log.e("pgyer", "check update failed ", e);
                    }
                })
                //?????? ???
                //?????????????????? PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); ??????????????????
                //???????????????????????????????????????????????????????????? UI ???????????????
                //?????????????????????????????????????????????UI?????????????????????
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //????????????
                        progress.dismiss();
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        progress.dismiss();
                        // ???????????????????????????????????????????????? ??????apk
                        PgyUpdateManager.installApk(uri);
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers);
                    }
                })
                .register();
    }


//    // ???????????????????????????????????????????????????ViewHolder
////    class ViewHolder extends BarrageAdapter.BarrageViewHolder<PushBean> {
////
//////        private ImageView imgGift;
//////        private TextView textUSer1, textUSer2, textNumber, textGiftName, box, OkBtn, wuYongOne;
////
////        public ViewHolder(View itemView) {
////            super(itemView);
////            imgGift = itemView.findViewById(R.id.imgGift);
////            textUSer1 = itemView.findViewById(R.id.textUSer1);
////            textUSer2 = itemView.findViewById(R.id.textUSer2);
////            textNumber = itemView.findViewById(R.id.textNumber);
////            textGiftName = itemView.findViewById(R.id.textGiftName);
////            box = itemView.findViewById(R.id.box);
////            OkBtn = itemView.findViewById(R.id.ok_btn);
////            wuYongOne = itemView.findViewById(R.id.wuyong_one);
////        }
////
////        @Override
////        protected void onBind(PushBean pushBean) {
////            if (pushBean != null) {
////                if ("gift".equals(pushBean.type)) {
////                    wuYongOne.setText("????????????~");
////                    textUSer1.setText(pushBean.getData().getFrom_name());
////                    textUSer2.setText(pushBean.getData().getUser_name());
//////                    textNumber.setText(pushBean.getData().getNum() + "???");
////                    box.setText("?????????");
////                    textGiftName.setText(pushBean.getData().getGift_name()+"x"+pushBean.getData().getNum());
////                    GlideArms
////                            .with(MainActivity.this)
////                            .load(pushBean.getData().getImg())
////                            .into(imgGift);
////                    OkBtn.setVisibility(View.VISIBLE);
////                } else if ("award".equals(pushBean.type)) {
//////                    String text = "?????????"+pushBean.getData().getUser_name()+"???"+pushBean.getData().getBoxclass()+"?????????"+pushBean.getData().getGift_name()+"????????????????????????";
////                    wuYongOne.setText("??????~");
////                    textUSer1.setText(pushBean.getData().getUser_name());
////                    box.setText("???" + pushBean.getData().getBoxclass() + "?????????");
////                    textUSer2.setText(pushBean.getData().getGift_name());
////                    textNumber.setText("");
//////                    textNumber.setText("????????????????????????");
////                    GlideArms
////                            .with(MainActivity.this)
////                            .load(pushBean.getData().getImg())
////                            .into(imgGift);
////                    OkBtn.setVisibility(View.GONE);
////
////                    //????????????????????????
//////                    MessageEvent messageEvent = new MessageEvent();
//////                    messageEvent.setStateMessage(StateMessage.PEOPLE_OPEN_GEMSTONE);
//////                    messageEvent.setObject(text);
//////                    EventBus.getDefault().post(messageEvent);
////
////                }
////            }
////        }
////    }

    /**
     * ??????????????????
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
     * ??????????????????
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
                UIMessage userName = data;

                data.getMessage().getContent().getUserInfo();
//                LogUtils.debugInfo("???????????????"+userName);
            }
        }
    }
}
