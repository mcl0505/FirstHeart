package com.konglianyuyin.mx.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.HelpActivity;
import com.konglianyuyin.mx.activity.MainActivity;
import com.konglianyuyin.mx.activity.SetActivity;
import com.konglianyuyin.mx.activity.login.LoginActivity;
import com.konglianyuyin.mx.activity.mine.MoneyActivityNew;
import com.konglianyuyin.mx.activity.mine.MyProfitActivity;
import com.konglianyuyin.mx.activity.mine.RealNameActivity;
import com.konglianyuyin.mx.activity.my.GradeCenterActivity;
import com.konglianyuyin.mx.activity.my.InviteAcvitity;
import com.konglianyuyin.mx.activity.my.MemberCoreActivity;
import com.konglianyuyin.mx.activity.my.ModifyDataActivity;
import com.konglianyuyin.mx.activity.my.MyFollowActivity;
import com.konglianyuyin.mx.activity.my.MyPackageActivity;
import com.konglianyuyin.mx.activity.my.MyPersonalCenterActivity;
import com.konglianyuyin.mx.activity.room.CollectionRoomListActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.view.CustomDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.CHONGZHISHUAXIN;
import static com.konglianyuyin.mx.utils.Constant.FANHUIZHUYE;
import static com.konglianyuyin.mx.utils.Constant.LOGIN;
import static com.konglianyuyin.mx.utils.Constant.PACKFANHUI;
import static com.konglianyuyin.mx.utils.Constant.XIUGAIGERENZILIAOCHENGGONG;
import static com.konglianyuyin.mx.utils.Constant.XUANFUYINCANG;
import static io.rong.imkit.utilities.RongUtils.dip2px;

/**
 * 作者:sgm
 * 描述: 我的
 */
public class MainCenterFragment extends MyBaseArmFragment implements ImmersionOwner {
    @BindView(R.id.ll_sex)
    LinearLayout ll_sex;
    @BindView(R.id.iv_sex)
    ImageView iv_sex;
    @BindView(R.id.tv_age)
    TextView tv_age;

    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.iv_username)
    TextView ivUsername;
    @BindView(R.id.im_myhome)
    ImageView imMyhome;
    @BindView(R.id.myhome)
    TextView myhome;
    @BindView(R.id.im_mywallet)
    ImageView imMywallet;
    @BindView(R.id.mywallet)
    TextView mywallet;
    @BindView(R.id.im_myshouyi)
    ImageView imMyshouyi;
    @BindView(R.id.myshouyi)
    TextView myshouyi;
    @BindView(R.id.im_mydengji)
    ImageView imMydengji;
    @BindView(R.id.mydengji)
    TextView mydengji;
    @BindView(R.id.im_myhelp)
    ImageView imMyhelp;
    @BindView(R.id.myhelp)
    TextView myhelp;
    @BindView(R.id.im_myset)
    ImageView imMyset;
    @BindView(R.id.myset)
    TextView myset;
    Unbinder unbinder;
    @BindView(R.id.text_vip)
    TextView textVip;
    @BindView(R.id.rlSet)
    RelativeLayout rlSet;
    CustomDialog mDialog;
    //    @BindView(R.id.iv_modifyusermsg)
//    ImageView ivModifyusermsg;
    @BindView(R.id.rl_myhome)
    RelativeLayout rlMyhome;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;

    @BindView(R.id.textCollection)
    TextView textCollection;
    @BindView(R.id.textFans)
    TextView textFans;
    @BindView(R.id.rlMoney)
    RelativeLayout rlMoney;
    @BindView(R.id.rlShouyi)
    RelativeLayout rlShouyi;
    @BindView(R.id.dengji)
    RelativeLayout dengji;
    @BindView(R.id.huiyuan)
    RelativeLayout huiyuan;
    @BindView(R.id.my_lv)
    TextView myLv;
    @BindView(R.id.mizuan)
    TextView mizuanNum;
    @BindView(R.id.layout_head_title)
    RelativeLayout layoutHeadTitle;
    @BindView(R.id.imgSetting)
    LinearLayout imgSetting;
    @BindView(R.id.imgJinrui)
    ImageView imgJinrui;
    @BindView(R.id.imgXingrui)
    ImageView imgXingrui;
    @BindView(R.id.llCollection)
    LinearLayout llCollection;
    @BindView(R.id.llFans)
    LinearLayout llFans;

    Unbinder unbinder1;
    private String old_id = "";

    @Inject
    CommonModel commonModel;

    private UserBean mUserBean;

    private MainActivity mActivity;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_center1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 0, null);

        textVip.setVisibility(View.VISIBLE);
        ivUsername.setText(UserManager.getUser().getNickname());

        loadUserData();
    }


    /**
     * 用户信息
     */
    private void loadUserData() {
        RxUtils.loading(commonModel.get_user_info(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(UserBean userBean) {
                        mUserBean = userBean;
                        loadImage(ivHead, userBean.getData().getHeadimgurl(),
                                R.mipmap.no_tou);
                        ivUsername.setText(userBean.getData().getNickname());
                        textVip.setText("ID:" + userBean.getData().getId());
                        int sex = userBean.getData().getSex();
                        // 1男 2女
                        if(sex == 1) {
                            ll_sex.setBackgroundResource(R.drawable.bg_sex_jb_boy);
                            iv_sex.setImageResource(R.mipmap.sex_2);
                        } else {
                            ll_sex.setBackgroundResource(R.drawable.bg_sex_jb_girl);
                            iv_sex.setImageResource(R.mipmap.sex_1);
                        }
                        tv_age.setText("" + userBean.getData().getAge());

                        if (!TextUtils.isEmpty(userBean.getData().getBright_num())) {
                            textVip.setTextColor(getResources().getColor(R.color.colorAccent));
                            textVip.setText("ID:" + userBean.getData().getBright_num());
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textVip.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textVip.setCompoundDrawablePadding(dip2px(4));
                        }
                        textCollection.setText(userBean.getData().getFollows_num() + "");
                        textFans.setText(userBean.getData().getFans_num() + "");
                        myLv.setText("Lv. " + userBean.getData().getVip_level());
                        String mizuan = userBean.getData().getMizuan();
                        if (!TextUtils.isEmpty(mizuan)) {
                            if (mizuan.contains(".")) {
                                int index = mizuan.indexOf(".");
                                mizuan = mizuan.substring(0, index);
                            }
                        }
                        mizuanNum.setText(mizuan);
//                        imgJinrui.setVisibility(TextUtils.isEmpty(userBean.getData().getGold_img()) ? View.GONE : View.VISIBLE);
//                        imgXingrui.setVisibility(TextUtils.isEmpty(userBean.getData().getStar_img()) ? View.GONE : View.VISIBLE);
//                        loadImage(imgJinrui, userBean.getData().getGold_img(), R.mipmap.huizhang);
//                        loadImage(imgXingrui, userBean.getData().getStar_img(), R.mipmap.huizhang);

                        //用户信息存入数据库
//                        LoginData loginData = UserManager.getUser();
//                        loginData.setNickname(userBean.getData().getNickname());
//                        LitePal.deleteAll(LoginData.class);
//                        loginData.save();//litepal数据库，不能随便改LoginData数据
//                        UserManager.initData();//存储完，初始化
                    }
                });
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @OnClick({R.id.iv_head, R.id.rlShouyi, R.id.rlMoney, R.id.rl_help, R.id.iv_username, R.id.rlSet, R.id.rl_myhome, R.id.llCollection,
            R.id.llFans, R.id.dengji, R.id.huiyuan, R.id.rl_my_package, R.id.rl_myshouchang, R.id.imgSetting,R.id.rlInvite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_myhome:
                if (mUserBean == null) {
                    return;
                }

                enterData(String.valueOf(UserManager.getUser().getUserId()), "", commonModel, 1, mUserBean.getData().getHeadimgurl());

                break;
//            case R.id.iv_modifyusermsg:
////                    ArmsUtils.startActivity(ChangeUserActivity.class);
////                    ArmsUtils.startActivity(UserMineStateActivity.class);
////                    ArmsUtils.startActivity(AdminHomeActivity.class);
//                mDialog = new CustomDialog(getActivity(), "您已被踢出房间", "60分钟后可重新进入该房间", "确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//                        Toast.makeText(getActivity(), "退出了--伤心", Toast.LENGTH_LONG).show();
//                    }
//                });
//                mDialog.setCanotBackPress();
//                mDialog.setCanceledOnTouchOutside(true);
//                mDialog.show();
//                break;
            case R.id.rl_help:
                ArmsUtils.startActivity(HelpActivity.class); //帮助和反馈
                break;
            case R.id.rl_myshouchang:
                ArmsUtils.startActivity(CollectionRoomListActivity.class);
                break;
            case R.id.iv_head:
                Intent intent = new Intent(getActivity(), MyPersonalCenterActivity.class);
                intent.putExtra("sign", 0);
                intent.putExtra("id", "");
                ArmsUtils.startActivity(intent);
                break;
            case R.id.iv_username:
                ArmsUtils.startActivity(LoginActivity.class);
                break;
            case R.id.rlSet: //设置
                ArmsUtils.startActivity(SetActivity.class);
                break;
            case R.id.imgSetting: //设置
                ArmsUtils.startActivity(ModifyDataActivity.class);
                break;
            case R.id.llFans: // 我的粉丝
                ArmsUtils.startActivity(MyFollowActivity.class);
                break;
            case R.id.rlShouyi:
                Intent intent2 = new Intent(getActivity(), MyProfitActivity.class);
                ArmsUtils.startActivity(intent2);
                break;
            case R.id.rlMoney:  //我的钱包
                Intent moneyIntent = new Intent(getActivity(), MoneyActivityNew.class);
                // 传递是否能赠送的标记值
                moneyIntent.putExtra("CanZs", mUserBean.getData().getIs_zengsong());
                ArmsUtils.startActivity(moneyIntent);
                break;
            case R.id.llCollection:  //我的关注
                ArmsUtils.startActivity(MyFollowActivity.class);
                break;
            case R.id.dengji: //我的等级
                ArmsUtils.startActivity(GradeCenterActivity.class);
                break;
            case R.id.huiyuan:  //会员等级
                ArmsUtils.startActivity(MemberCoreActivity.class);
                break;
            case R.id.rl_my_package:  //我的背包
                if (mUserBean == null) {
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MyPackageActivity.class);
                intent1.putExtra("url", mUserBean.getData().getHeadimgurl());
                ArmsUtils.startActivity(intent1);
                break;
            case R.id.rlInvite://邀请好友
                ArmsUtils.startActivity(InviteAcvitity.class);
                break;
        }
    }

    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {

////        ImmersionBar.with(this).getTag("MainActivity").reset();
//
//        ImmersionBar.with(this)
////                .transparentStatusBar()//透明状态栏，不写默认透明色
//                .init();
//
//        ImmersionBar.with(this)
//                .statusBarColor(R.color.mainbg)
//                .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//                .init();//设置状态栏白色

    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
        if (!hidden && getActivity() != null) {
            loadUserData();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean immersionBarEnabled() {//是否用沉浸式
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (LOGIN.equals(tag)) {
            textVip.setVisibility(View.VISIBLE);
            //做请求个人信息的操作
        } else if (FANHUIZHUYE.equals(tag)) {//显示悬浮窗
            EnterRoom enterRoom = event.getEnterRoom();
            old_id = String.valueOf(enterRoom.getRoom_info().get(0).getUid());
        } else if (XUANFUYINCANG.equals(tag)) {//显示悬浮窗
            old_id = "";
        } else if (Constant.RENZHENGCHENGGONG.equals(tag)) {
            showToast("认证成功！");
            mUserBean.getData().setIs_idcard(1);
        } else if ("send_gift".equals(tag)) {
            loadUserData();
        } else if (XIUGAIGERENZILIAOCHENGGONG.equals(tag)) {
            loadUserData();
        } else if (CHONGZHISHUAXIN.equals(tag)) {
            loadUserData();
        } else if (PACKFANHUI.equals(tag)) {
            loadUserData();
        }
    }
}
