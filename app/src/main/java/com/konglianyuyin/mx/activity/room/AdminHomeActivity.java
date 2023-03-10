package com.konglianyuyin.mx.activity.room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.activity.mine.GiveOtherActivity;
import com.konglianyuyin.mx.bean.DengJiBean;
import com.konglianyuyin.mx.bean.GiftListBean;
import com.konglianyuyin.mx.bean.MessageEvent2;
import com.konglianyuyin.mx.bean.PayBean;
import com.konglianyuyin.mx.bean.RequestUpMicrophoneBean;
import com.konglianyuyin.mx.bean.Wxmodel;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.popup.RechargePopWindow;
import com.konglianyuyin.mx.popup.TreasureBoxDialog;
import com.konglianyuyin.mx.popup.UpMicrophonePopWindow;
import com.konglianyuyin.mx.utils.LogUtil;
import com.konglianyuyin.mx.utils.PayResult;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.orient.tea.barragephoto.adapter.AdapterListener;
import com.orient.tea.barragephoto.adapter.BarrageAdapter;
import com.orient.tea.barragephoto.ui.BarrageView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.konglianyuyin.mx.JWebSocketClient;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.SendMessageService;
import com.konglianyuyin.mx.SendMessageServiceBean;
import com.konglianyuyin.mx.activity.MainActivity;
import com.konglianyuyin.mx.activity.my.MyPersonalCenterActivity;
import com.konglianyuyin.mx.adapter.DanMuViewHolder;
import com.konglianyuyin.mx.adapter.FragmentAdapter;
import com.konglianyuyin.mx.adapter.PagerAdapter;
import com.konglianyuyin.mx.adapter.RoomMessageAdapter;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.service.RoomPlayService;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.AdminUser;
import com.konglianyuyin.mx.bean.AgreeCpResult;
import com.konglianyuyin.mx.bean.AllRoomBean;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.EmojiBean;
import com.konglianyuyin.mx.bean.EnterRoom;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.GifBean;
import com.konglianyuyin.mx.bean.GiftListBeanNew;
import com.konglianyuyin.mx.bean.GiftSocketBean;
import com.konglianyuyin.mx.bean.GiftSocketBean2;
import com.konglianyuyin.mx.bean.JinSheng;
import com.konglianyuyin.mx.bean.LocalMusicInfo;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.MessageEvent;
import com.konglianyuyin.mx.bean.MicUserBean;
import com.konglianyuyin.mx.bean.Microphone;
import com.konglianyuyin.mx.bean.MusicYinxiao;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.bean.OtherUser;
import com.konglianyuyin.mx.bean.PushBean;
import com.konglianyuyin.mx.bean.RoomMultipleItem;
import com.konglianyuyin.mx.bean.RoomUsersBean;
import com.konglianyuyin.mx.bean.SendGemResult;
import com.konglianyuyin.mx.bean.StateMessage;
import com.konglianyuyin.mx.bean.UpVideoResult;
import com.konglianyuyin.mx.bean.VipBean;
import com.konglianyuyin.mx.bean.WaitList;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.fragment.EmojiFragment;
import com.konglianyuyin.mx.fragment.YinxiaoFragment;
import com.konglianyuyin.mx.http.HttpCallback;
import com.konglianyuyin.mx.http.HttpUtil;
import com.konglianyuyin.mx.popup.GemStoneDialogNew;
import com.konglianyuyin.mx.popup.GiftFlyDialog1;
import com.konglianyuyin.mx.popup.GiftWindow;
import com.konglianyuyin.mx.popup.KeybordWindow;
import com.konglianyuyin.mx.popup.PaimaiWindow;
import com.konglianyuyin.mx.popup.ReportWindow;
import com.konglianyuyin.mx.popup.RequestCPDialog;
import com.konglianyuyin.mx.popup.RoomDialog;
import com.konglianyuyin.mx.popup.RoomGaoWindow;
import com.konglianyuyin.mx.popup.RoomSetWindow1;
import com.konglianyuyin.mx.popup.RoomSetWindow2;
import com.konglianyuyin.mx.popup.RoomTopWindow;
import com.konglianyuyin.mx.popup.SelectPeopleUpVideoDialog;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.BaseUtils;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.MyUtil;
import com.konglianyuyin.mx.utils.SharedPreferencesUtils;
import com.konglianyuyin.mx.view.RippleView;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.relex.circleindicator.CircleIndicator;

import static com.konglianyuyin.mx.app.Api.APP_DOMAIN3;
import static com.konglianyuyin.mx.utils.Constant.DIANJIBIAOQING;
import static com.konglianyuyin.mx.utils.Constant.FANGJIANSHEZHI;
import static com.konglianyuyin.mx.utils.Constant.FASONGMAIXULIWU;
import static com.konglianyuyin.mx.utils.Constant.KBXTUISONG;
import static com.konglianyuyin.mx.utils.Constant.QuxiaoGUANLI;
import static com.konglianyuyin.mx.utils.Constant.RECHARGE_FROM_HOME;
import static com.konglianyuyin.mx.utils.Constant.SHEZHIGUANLI;
import static com.konglianyuyin.mx.utils.Constant.TUISONG;
import static com.konglianyuyin.mx.utils.Constant.XIUGAIGERENZILIAOCHENGGONG;
import static com.konglianyuyin.mx.utils.Constant.YINYUEBOFANG;
import static com.konglianyuyin.mx.utils.Constant.YINYUEZANTING;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggdfghfhrthmkBeiJinYan;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggdfghfhrthmkQuxiaoiguanliyuan;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggdfghfhrthmkShezhiguanliyuan;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggdfghfhrthmkkaimai;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggunaliyuanbimai;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggunaliyuantichu;
import static com.konglianyuyin.mx.utils.Constant.nfgk184grdgdfggunaliyuanxiamai;
import static io.rong.imkit.utilities.RongUtils.dip2px;


/**
 * ??????????????????
 * //    messageType     1???????????? ????????????  2???????????????  3??? ??????????????????  4 ???????????????  5 ???????????????
 */
public class AdminHomeActivity extends MyBaseArmActivity {
    private String own = " - AdminHomeActivity - ";

    @BindView(R.id.fl_mng_apply)
    RelativeLayout fl_mng_apply;
    @BindView(R.id.imgBg)
    ImageView imgBg;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textType)
    ShapeTextView textType;
    @BindView(R.id.textId)
    TextView textId;
    @BindView(R.id.textNum)
    TextView textNum;
    @BindView(R.id.imgRight)
    LinearLayout imgRight;
    @BindView(R.id.imgCollection)
    ImageView imgCollection;
    @BindView(R.id.textRight)
    ImageView textRight;
    @BindView(R.id.imgQuanZhu)
    SVGAImageView imgQuanZhu;
    @BindView(R.id.wave_zhu)
    RippleView mWaveViewZhu;
    @BindView(R.id.imgRoom)
    RoundedImageView imgRoom;
    @BindView(R.id.img_txk_zhu)
    ImageView mImgTxkZhu;
    @BindView(R.id.imgRoomVedio)
    ImageView imgRoomVedio;
    @BindView(R.id.textLayout)
    ShapeTextView textLayout;
    @BindView(R.id.imgRoomGif)
    ImageView imgRoomGif;
    @BindView(R.id.textRoom)
    TextView textRoom;
    @BindView(R.id.imgQuan1)
    SVGAImageView imgQuan1;
    @BindView(R.id.wave_1)
    RippleView mWaveView1;
    @BindView(R.id.img1)
    RoundedImageView img1;
    @BindView(R.id.img_txk_1)
    ImageView mImgTxk1;
    @BindView(R.id.imgVedio1)
    ImageView imgVedio1;
    @BindView(R.id.textNum1)
    TextView textNum1;
    @BindView(R.id.imgGif1)
    ImageView imgGif1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.imgQuan2)
    SVGAImageView imgQuan2;
    @BindView(R.id.wave_2)
    RippleView mWaveView2;
    @BindView(R.id.img2)
    RoundedImageView img2;
    @BindView(R.id.img_txk_2)
    ImageView mImgTxk2;
    @BindView(R.id.imgVedio2)
    ImageView imgVedio2;
    @BindView(R.id.textNum2)
    TextView textNum2;
    @BindView(R.id.imgGif2)
    ImageView imgGif2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.imgQuan3)
    SVGAImageView imgQuan3;
    @BindView(R.id.wave_3)
    RippleView mWaveView3;
    @BindView(R.id.img3)
    RoundedImageView img3;
    @BindView(R.id.img_txk_3)
    ImageView mImgTxk3;
    @BindView(R.id.imgVedio3)
    ImageView imgVedio3;
    @BindView(R.id.textNum3)
    TextView textNum3;
    @BindView(R.id.imgGif3)
    ImageView imgGif3;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.imgQuan4)
    SVGAImageView imgQuan4;
    @BindView(R.id.wave_4)
    RippleView mWaveView4;
    @BindView(R.id.img4)
    RoundedImageView img4;
    @BindView(R.id.img_txk_4)
    ImageView mImgTxk4;
    @BindView(R.id.imgVedio4)
    ImageView imgVedio4;
    @BindView(R.id.textNum4)
    TextView textNum4;
    @BindView(R.id.imgGif4)
    ImageView imgGif4;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.imgQuan5)
    SVGAImageView imgQuan5;
    @BindView(R.id.wave_5)
    RippleView mWaveView5;
    @BindView(R.id.img5)
    RoundedImageView img5;
    @BindView(R.id.img_txk_5)
    ImageView mImgTxk5;
    @BindView(R.id.imgVedio5)
    ImageView imgVedio5;
    @BindView(R.id.textNum5)
    TextView textNum5;
    @BindView(R.id.imgGif5)
    ImageView imgGif5;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.imgQuan6)
    SVGAImageView imgQuan6;
    @BindView(R.id.wave_6)
    RippleView mWaveView6;
    @BindView(R.id.img6)
    RoundedImageView img6;
    @BindView(R.id.img_txk_6)
    ImageView mImgTxk6;
    @BindView(R.id.imgVedio6)
    ImageView imgVedio6;
    @BindView(R.id.textNum6)
    TextView textNum6;
    @BindView(R.id.imgGif6)
    ImageView imgGif6;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.imgQuan7)
    SVGAImageView imgQuan7;
    @BindView(R.id.wave_7)
    RippleView mWaveView7;
    @BindView(R.id.img7)
    RoundedImageView img7;
    @BindView(R.id.img_txk_7)
    ImageView mImgTxk7;
    @BindView(R.id.imgVedio7)
    ImageView imgVedio7;
    @BindView(R.id.textNum7)
    TextView textNum7;
    @BindView(R.id.imgGif7)
    ImageView imgGif7;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.imgQuan8)
    SVGAImageView imgQuan8;
    @BindView(R.id.wave_8)
    RippleView mWaveView8;
    @BindView(R.id.img8)
    RoundedImageView img8;
    @BindView(R.id.img_txk_8)
    ImageView mImgTxk8;
    @BindView(R.id.imgVedio8)
    ImageView imgVedio8;
    @BindView(R.id.textNum8)
    TextView textNum8;
    @BindView(R.id.imgGif8)
    ImageView imgGif8;
    @BindView(R.id.text8)
    TextView text8;
    @BindView(R.id.imgPaimai)
    ImageView imgPaimai;
    @BindView(R.id.imgShangmai)
    ImageView imgShangmai;
    @BindView(R.id.imgBimai)
    CircularImage imgBimai;
    @BindView(R.id.imgTing)
    CircularImage imgTing;
    @BindView(R.id.imgMusic)
    ImageView imgMusic;
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.imgMessage)
    LinearLayout imgMessage;
    @BindView(R.id.imgBiaoqing)
    CircularImage imgBiaoqing;
    @BindView(R.id.ll_bootombar)
    LinearLayout llBootombar;
    @BindView(R.id.imgGift)
    FrameLayout imgGift;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.baoxiang)
    ImageView baoxiang;
    @BindView(R.id.iv_lottery)
    ImageView ivLottery;
    @BindView(R.id.img_vip_enter_bg)
    ImageView mImgVipEnterBg;
    @BindView(R.id.tv_vip_enter)
    TextView mTvVipEnter;
    @BindView(R.id.layout_vip_enter)
    RelativeLayout mLayoutVipEnter;
    @BindView(R.id.recLayout)
    RelativeLayout recLayout;
    @BindView(R.id.view_need_offset)
    LinearLayout viewNeedOffset;
    @BindView(R.id.viewTop)
    View viewTop;
    @BindView(R.id.imgXunhuan)
    ImageView imgXunhuan;
    @BindView(R.id.imgLiebiao)
    ImageView imgLiebiao;
    @BindView(R.id.textMusicName)
    TextView textMusicName;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.imgFront)
    ImageView imgFront;
    @BindView(R.id.imgStop)
    ImageView imgStop;
    @BindView(R.id.imgNext)
    ImageView imgNext;
    @BindView(R.id.myGrid)
    GridView myGrid;
    @BindView(R.id.recyclerMusic)
    ViewPager recyclerMusic;
    @BindView(R.id.llMusic)
    LinearLayout llMusic;
    @BindView(R.id.viewEnmojiTop)
    View viewEnmojiTop;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.rlEmoji)
    LinearLayout rlEmoji;
    @BindView(R.id.img_cp_all_in)
    ImageView mImgCpALlIn;
    @BindView(R.id.img_cp_left_all_in)
    CircularImage mImgCpLeftAllIn;
    @BindView(R.id.img_cp_right_all_in)
    CircularImage mImgCpRightAllIn;
    @BindView(R.id.tv_cp_all_in)
    TextView mTvCpAllIn;
    @BindView(R.id.layout_cp_all_in)
    RelativeLayout mLayoutCpAllIn;
    @BindView(R.id.img_cp_tongfang)
    ImageView mImgCpTongFang;
    @BindView(R.id.img_cp_left)
    CircularImage mImgCpLeft;
    @BindView(R.id.tv_cp_left)
    TextView mTvCpLeft;
    @BindView(R.id.img_cp_right)
    CircularImage mImgCpRight;
    @BindView(R.id.tv_cp_right)
    TextView mTvCpRight;
    @BindView(R.id.layout_cp_tongfang)
    RelativeLayout mLayoutCpTongFang;
    @BindView(R.id.svgImage)
    SVGAImageView svgImage;
    @BindView(R.id.imgPopup)
    ImageView imgPopup;
    @BindView(R.id.imgFei)
    ImageView imgFei;
    @BindView(R.id.imgFei1)
    ImageView imgFei1;
    @BindView(R.id.imgFei2)
    ImageView imgFei2;
    @BindView(R.id.imgFei3)
    ImageView imgFei3;
    @BindView(R.id.imgFei4)
    ImageView imgFei4;
    @BindView(R.id.imgFei5)
    ImageView imgFei5;
    @BindView(R.id.imgFei6)
    ImageView imgFei6;
    @BindView(R.id.imgFei7)
    ImageView imgFei7;
    @BindView(R.id.imgFei8)
    ImageView imgFei8;
    @BindView(R.id.tv_clear_cp)
    TextView tvClearCp;
    @BindView(R.id.barrage_view)
    BarrageView mBarrageView;
    @BindView(R.id.room)
    RelativeLayout layoutRoot;
    @BindView(R.id.meili_1)
    TextView meili11;
    @BindView(R.id.meili1)
    TextView meili1;
    @BindView(R.id.meili2)
    TextView meili2;
    @BindView(R.id.meili3)
    TextView meili3;
    @BindView(R.id.meili4)
    TextView meili4;
    @BindView(R.id.meili5)
    TextView meili5;
    @BindView(R.id.meili6)
    TextView meili6;
    @BindView(R.id.meili7)
    TextView meili7;
    @BindView(R.id.meili8)
    TextView meili8;
    @BindView(R.id.online_pepole)
    TextView onlinePepole;
    @BindView(R.id.iv_want_speak_list)
    ImageView ivWantSpeakList;
    @BindView(R.id.tv_new_up_microphone)
    TextView tvNewUpMicrophone;

    @Inject
    CommonModel commonModel;
    private static String uid;      // ?????????uid
    private String room_pass = "";  // ????????????
    private BarrageAdapter<PushBean> mBarrageViewAdapter;//??????
    List<PushBean> mPushBeanList = new Vector<>();
    // ??????im???????????????
    private RtcEngine mRtcEngine;
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;

    private List<Microphone.DataBean.MicrophoneBean> mMicrophone = new ArrayList<>();   // ??????????????????
    private EnterRoom enterRoom;
    private int user_type;

    private RoomMessageAdapter roomMessageAdapter;  // ?????????????????????
    List<MessageBean> listMessage = new ArrayList<>();//????????????????????????
    public boolean isEditBimai;//????????????????????????
    public static boolean isStart;//????????????
    public static boolean isTop;//????????????????????????
    public static AdminHomeActivity mContext;
    private int flag;//???????????????1.MainActivity
    private int musicPosition = 0;//???????????????????????????
    private int randomMusic = 0;//?????????????????????
    private Timer timer;
    private TimerTask timerTask;
    private List<RequestUpMicrophoneBean.DataBean> requestUpMicrophoneData; // ???????????????????????????

    //    private EmojiFragment myFragment1;
    //private int selfPosition = 0;//?????????????????????
    //int a = 0;
    private int feiLeft;//??????????????????
    private int feiTop;

    private VipBean vipBean = new VipBean();//vip??????

    private List<LocalMusicInfo> listLocal;

    boolean mHasCPAtRoom = false;

    String mStringGongGao = "";//???????????????????????????????????????????????????????????????????????????????????????

    GiftFlyDialog1 mGiftFlyDialog;
    //??????????????????
    public boolean is_show_egg;
    //????????????
    private DengJiBean dengJiBean;
    private int userLevel = 0;

    AdminUser mAdminUser;

    private static final int SDK_PAY_FLAG = 101;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://??????????????????
                    try {
                        int audioMixingDuration = mRtcEngine.getAudioMixingDuration();
                        int audioMixingCurrentPosition = mRtcEngine.getAudioMixingCurrentPosition();
//                    LogUtils.debugInfo("====????????????" + audioMixingCurrentPosition * 100 / audioMixingDuration);
                        if (audioMixingCurrentPosition * 100 / audioMixingDuration == 99) {
                            if (randomMusic == 0) {
                                // ??????(?????????)
                                if (musicPosition == listLocal.size() - 1) {
                                    // ???????????????1???
                                    musicPosition = 0;
                                    seekBar.setProgress(0);
                                    textMusicName.setText(listLocal.get(musicPosition).name);
                                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                } else {
                                    // ???????????????
                                    musicPosition = musicPosition + 1;
                                    seekBar.setProgress(0);
                                    textMusicName.setText(listLocal.get(musicPosition).name);
                                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                }
                            } else {
                                // ??????musicPosition????????????
                                seekBar.setProgress(0);
                                musicPosition = BaseUtils.getRandom(listLocal.size());
                                textMusicName.setText(listLocal.get(musicPosition).name);
                                mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                            }
                        } else {
                            // ?????????????????????
                            seekBar.setProgress(audioMixingCurrentPosition * 100 / audioMixingDuration);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // ????????????
                    try {
                        int progress = seekBar.getProgress();
                        int allDuration = mRtcEngine.getAudioMixingDuration();
                        int currentDuration = allDuration * progress / 100;//???????????????
                        LogUtils.debugInfo("====???????????????" + currentDuration);
                        mRtcEngine.setAudioMixingPosition(currentDuration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SDK_PAY_FLAG:
                    // ????????????
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        showToast("????????????");
                    } else {
                        // ?????????
                        showToast("????????????,?????????");
                    }
                    break;
                default:
                    break;
            }
        }
    };

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
        return R.layout.activity_admin_home_1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getLevelBean();
        StatusBarUtil.setTranslucentForImageView(this, 0, viewNeedOffset);
//        mCurrentRenderer = new TimerCircleRippleRenderer(PaintHelper.getDefaultRipplePaint(this, R.color.translant), PaintHelper.getDefaultRippleBackgroundPaint(this, R.color.translant), 10000.0, 0.0);
//        if (mCurrentRenderer instanceof TimerCircleRippleRenderer) {
//            ((TimerCircleRippleRenderer) mCurrentRenderer).setStrokeWidth(20);
//        }
//        setWaveViewListener();
//        setWaveViewAttr();
//        mWaveView1.setRippleSampleRate(Rate.LOW);
//        mWaveView1.setRippleDecayRate(Rate.HIGH);
//        mWaveView1.setBackgroundRippleRatio(1.4);

        isStart = true;//???????????????
        isTop = true;//?????????
        mContext = this;
        ivLottery.setVisibility(ExtConfig.isShowLottery?View.VISIBLE:View.GONE);
        initRoomData(); // ???????????????????????????????????????????????????
        loadVipData();  // ??????vip???????????????????????????????????????????????????????????????im
        loadVedioList();    // ????????????????????????????????????????????????

        // initLive();//???????????????
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        initLive();//???????????????
                    }
                });

//        diffuseViewRoom.start();
        imgFei.post(() -> {
            int[] location = new int[2];
            imgFei.getLocationOnScreen(location);
            feiLeft = location[0];
            feiTop = location[1];
            imgFei.setVisibility(View.GONE);
            LogUtils.debugInfo("====???1???" + location[0] + "====???1" + location[1]);
        });
        //??????
        initDanmu();

        findViewById(R.id.tv_clear_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxUtils.loading(commonModel.delete_cp(UserManager.getUser().getToken()), AdminHomeActivity.this)
                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                            @Override
                            public void onNext(BaseBean baseBean) {
                                toast("?????????" + baseBean.getMessage());
                            }
                        });
            }
        });
        loadData();
    }

    /**
     * ?????????????????????
     */
    private void initRoomData() {
        // ?????????????????? ??? ??????ID?????????????????????????????????????????????1.MainActivity
        uid = getIntent().getStringExtra("uid");
        flag = getIntent().getIntExtra("flag", 1);
        // ??????????????????????????????
        enterRoom = (EnterRoom) getIntent().getSerializableExtra("enterRoom");
        LogUtils.debugInfo(own + "initRoomData: " + uid + "_" + flag);
        LogUtils.debugInfo(own + "enterRoom: " + enterRoom.getRoom_info().get(0).getRoom_name());
        LogUtils.debugInfo(own + "enterRoom: " + enterRoom.getRoom_info().get(0).getRoom_name());

        if (enterRoom != null) {

            is_show_egg = (enterRoom.getRoom_info().get(0).getConsume()>=ExtConfig.showEggMoney);

            // ???????????????
            loadImage(imgBg, enterRoom.getRoom_info().get(0).back_img, R.mipmap.room_bg);
            // ???????????????
            textName.setText(enterRoom.getRoom_info().get(0).getRoom_name());
            textId.setText("ID:" + enterRoom.getRoom_info().get(0).getNumid());
            // bright_num??????ID?????????
            /*if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getBright_num())) {
                textId.setText("ID:" + enterRoom.getRoom_info().get(0).getBright_num());
                textId.setTextColor(getResources().getColor(R.color.colorAccent));
                Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                textId.setCompoundDrawablePadding(dip2px(4));
            }*/
            // ??????????????????
            imgCollection.setSelected(enterRoom.getRoom_info().get(0).getIs_mykeep() == 1);

            // ?????????
            textType.setText(enterRoom.getRoom_info().get(0).getRoom_class_name()); // ??????
            textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));   // ?????????
            if (enterRoom.getRoom_info().get(0).getIs_afk() == 1)
                textLayout.setVisibility(View.GONE);  // ????????????????????????
            else textLayout.setVisibility(View.VISIBLE);

            // ????????????
            // ?????????:  ?????????????????????
            meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
            //meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
            // ????????????
            textRoom.setText(enterRoom.getRoom_info().get(0).getNickname());
            // ?????????????????????
            imgRoomVedio.setSelected(enterRoom.getRoom_info().get(0).getIs_sound() == 1);
            // ????????????????????????
            imgRoom.setSelected(enterRoom.getRoom_info().get(0).getSex() == 1);
            // ?????????????????????
            if (enterRoom.getRoom_info().get(0).getSex() == 1) {
                imgRoom.setBorderColor(getResources().getColor(R.color.bottom_text_ok));
            } else {
                imgRoom.setBorderColor(getResources().getColor(R.color.bottom_text_ok));
            }
            // ???????????????????????????
            loadImage(imgRoom, enterRoom.getRoom_info().get(0).getHeadimgurl(), R.mipmap.room_kazuo_suo);
            // ???????????????
            if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).txk)) {//??????????????????
                mImgTxkZhu.setVisibility(View.VISIBLE);
                loadImage(mImgTxkZhu, enterRoom.getRoom_info().get(0).txk, 0);
            } else {
                mImgTxkZhu.setVisibility(View.GONE);
            }

            // ????????????????????????????????????
            user_type = enterRoom.getRoom_info().get(0).getUser_type();
            LogUtils.debugInfo(own + "user_type???" + user_type);
            // 1.?????? 2.????????? 3.?????? 4.?????? 5.????????????
            if (user_type == 1) {
                imgShangmai.setVisibility(View.GONE);
                imgAdd.setVisibility(View.VISIBLE);
                imgBimai.setVisibility(View.VISIBLE);
                imgMusic.setVisibility(View.VISIBLE);
                imgBiaoqing.setVisibility(View.VISIBLE);
                textLayout.setVisibility(View.GONE);
                fl_mng_apply.setVisibility(View.VISIBLE);

                ivWantSpeakList.setVisibility(ExtConfig.isMicrophoneRequest?View.VISIBLE:View.INVISIBLE);
            } else if (user_type == 2) {
                imgAdd.setVisibility(View.VISIBLE);
                imgBimai.setVisibility(View.GONE);
                imgMusic.setVisibility(View.GONE);
                imgBiaoqing.setVisibility(View.GONE);
                fl_mng_apply.setVisibility(View.INVISIBLE);
                ivWantSpeakList.setVisibility(View.GONE);
            } else {
                imgAdd.setVisibility(View.GONE);
                imgBimai.setVisibility(View.GONE);
                imgMusic.setVisibility(View.GONE);
                imgBiaoqing.setVisibility(View.GONE);
                fl_mng_apply.setVisibility(View.INVISIBLE);
                ivWantSpeakList.setVisibility(View.GONE);
            }

            // ???????????????????????????
            roomMessageAdapter = new RoomMessageAdapter(this,uid);
            View headerMessage = ArmsUtils.inflate(this, R.layout.message_header);
            TextView textNameXitong = headerMessage.findViewById(R.id.textNameXitong);
            textNameXitong.setText("???????????????" + enterRoom.getRoom_info().get(0).getRoom_welcome());
            TextView textMessage2 = headerMessage.findViewById(R.id.textMessage2);
            textMessage2.setVisibility(View.VISIBLE);
            String text = "????????????????????????~,?????????????????????~";
            if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getRoom_intro())) {
                text = enterRoom.getRoom_info().get(0).getRoom_intro();
                mStringGongGao = enterRoom.getRoom_info().get(0).getRoom_intro();
            }
            textMessage2.setText(text);
            roomMessageAdapter.addHeaderView(headerMessage);
            // ???????????????????????????
            roomMessageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                MessageBean itemBean = roomMessageAdapter.getData().get(position);
                if (itemBean == null) return;
//                //????????????????????????????????????
//                if (itemBean.getRoomId222().equals(uid)){
//
//                }

                String type = itemBean.getMessageType();
                if (TextUtils.equals("8", type) || TextUtils.equals("9", type) || TextUtils.equals("10", type) || TextUtils.equals("11", type)) {//????????????????????????textview????????????????????????
                    return;
                } else {
                    if (view.getId() == R.id.textName || view.getId() == R.id.textName2 || view.getId() == R.id.textNameGift1) {
                        setFirstNameClick(position);
                    } else if (view.getId() == R.id.textNameGift2) {//?????????????????????????????????
                        setSecondNameClick(position);
                    }
                }


            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(roomMessageAdapter);

            startKeepLiveService();
        }
    }

    /**
     * ?????????????????????
     */
    private void refreshEgg() {
        if (is_show_egg) {
            baoxiang.setVisibility(View.VISIBLE);

        } else {
            baoxiang.setVisibility(View.INVISIBLE);
        }
    }

    private void getLevelBean() {

        //??????????????????
        baoxiang.setImageResource(ExtConfig.baoxiangImg);

        RxUtils.loading(commonModel.getLevelCenter(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<DengJiBean>(mErrorHandler) {
                    @Override
                    public void onNext(DengJiBean dengJi) {
                        LogUtils.debugInfo(dengJi.toString());
                        dengJiBean = dengJi;
                        if (dengJiBean.getData() ==null || dengJiBean.getData().size() == 0){
                            userLevel = 0;
                        }else userLevel = dengJiBean.getData().get(0).getGold_level();

                        if (Double.parseDouble(dengJiBean.getData().get(0).getGold_num()) >= ExtConfig.showEggMoney){
                            is_show_egg = true;
                            refreshEgg();
                        }else {
                            is_show_egg = false;
                            refreshEgg();
                        }
                    }
                });
    }

    /**
     * vip??????
     */
    private void loadVipData() {
        //vip??????
        RxUtils.loading(commonModel.get_user_vip(uid + "", UserManager.getUser().getToken()), this)
                .subscribe(new ErrorHandleSubscriber<VipBean>(mErrorHandler) {
                    @Override
                    public void onNext(VipBean baseBean) {
                        vipBean = baseBean;
                        //?????????????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (vipBean != null && vipBean.getData() != null) {
                                    List<VipBean.DataBean.CpUsersBean> cp_user_list = vipBean.getData().getCp_users();//????????????cp??????
                                    LoginData loginData = UserManager.getUser();
                                    if (cp_user_list != null && cp_user_list.size() > 0) {
                                        // ???CP?????????????????????
                                        for (VipBean.DataBean.CpUsersBean cpUsersBean : cp_user_list) {
                                            MessageBean newMessage = new MessageBean();
                                            newMessage.hz_img = vipBean.getData().getHz_img();
                                            newMessage.vip_tx = vipBean.getData().getVip_tx();
                                            newMessage.vip_img = vipBean.getData().getVip_img();
                                            newMessage.setNickName(loginData.getNickname());
                                            newMessage.setUser_id(loginData.getUserId() + "");
                                            newMessage.nick_color = vipBean.getData().getNick_color();
                                            newMessage.toNickName = cpUsersBean.getNickname();
                                            newMessage.toNick_color = cpUsersBean.getNick_color();
                                            newMessage.toUser_id = cpUsersBean.getId();
                                            newMessage.cp_tx = cpUsersBean.getCp_tx();//CP??????
                                            newMessage.setMessageType("10");
                                            roomMessageAdapter.getData().add(newMessage);

                                            newMessage = new MessageBean();
                                            newMessage.hz_img = vipBean.getData().getHz_img();
                                            newMessage.vip_tx = vipBean.getData().getVip_tx();
                                            newMessage.vip_img = vipBean.getData().getVip_img();
                                            newMessage.setNickName(loginData.getNickname());
                                            newMessage.setUser_id(loginData.getUserId() + "");
                                            newMessage.nick_color = vipBean.getData().getNick_color();
                                            newMessage.toNickName = cpUsersBean.getNickname();
                                            newMessage.toNick_color = cpUsersBean.getNick_color();
                                            newMessage.toUser_id = cpUsersBean.getId();
                                            newMessage.cp_tx = cpUsersBean.getCp_tx();//CP??????
                                            newMessage.setMessageType("8");
                                            roomMessageAdapter.getData().add(newMessage);
                                        }
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    } else {
                                        //??????CP???????????????????????????
                                        MessageBean messageBean = new MessageBean();
                                        messageBean.setMessageType("2");
                                        messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                                        messageBean.setNickName(loginData.getNickname());
                                        messageBean.nick_color = vipBean.getData().getNick_color();
                                        listMessage.add(messageBean);
                                        roomMessageAdapter.setNewData(listMessage);
                                    }
                                    initMessage();
                                }
                            }
                        });
                    }
                });
    }

    /**
     * ????????????
     */
    private void loadVedioList() {
        RxUtils.loading(commonModel.microphone_status(uid), this)
                .subscribe(new ErrorHandleSubscriber<Microphone>(mErrorHandler) {
                    @Override
                    public void onNext(Microphone enterRoom) {
                        List<Microphone.DataBean.MicrophoneBean> microphone = enterRoom.getData().getMicrophone();
                        mMicrophone = microphone;
                        List<Integer> listKong = new ArrayList<>();
                        for (Microphone.DataBean.MicrophoneBean list : microphone) {//??????????????????
                            int status = list.getStatus();
                            if (status == 1) listKong.add(status);  // 1????????????
                        }
                        if (listKong.size() == 0) {//???????????????????????????????????????
                            imgPaimai.setVisibility(View.VISIBLE);
                            imgShangmai.setVisibility(View.GONE);
                        } else {
                            imgPaimai.setVisibility(View.GONE);
                            if (user_type != 1) {   // ??????????????????????????????
                                imgShangmai.setVisibility(View.VISIBLE);
                            }
                        }
                        setKazuo(img1, text1, mImgTxk1, 0, microphone, imgVedio1, textNum1, meili1);
                        setKazuo(img2, text2, mImgTxk2, 1, microphone, imgVedio2, textNum2, meili2);
                        setKazuo(img3, text3, mImgTxk3, 2, microphone, imgVedio3, textNum3, meili3);
                        setKazuo(img4, text4, mImgTxk4, 3, microphone, imgVedio4, textNum4, meili4);
                        setKazuo(img5, text5, mImgTxk5, 4, microphone, imgVedio5, textNum5, meili5);
                        setKazuo(img6, text6, mImgTxk6, 5, microphone, imgVedio6, textNum6, meili6);
                        setKazuo(img7, text7, mImgTxk7, 6, microphone, imgVedio7, textNum7, meili7);
                        setKazuo(img8, text8, mImgTxk8, 7, microphone, imgVedio8, textNum8, meili8);
                    }
                });
    }

    /**
     * ???????????????????????????
     */
    private void setKazuo(RoundedImageView imageView, TextView textView, ImageView imgTxk, int position,
                          List<Microphone.DataBean.MicrophoneBean> microphone, ImageView imgVedio,
                          TextView textNumber, TextView textMeili) {
        int status = microphone.get(position).getStatus();
        //?????????????????????????????????
        int is_sound = microphone.get(position).getIs_sound();
        switch (status) {
            case 1://??????
                loadImage(imageView, "", R.drawable.shf_img2);
                //textView.setText("");
                textView.setText((position + 1) + "??????");
                textView.setTextColor(getResources().getColor(R.color.white));
                imgVedio.setVisibility(View.GONE);
                imgTxk.setVisibility(View.GONE);//?????????
//                hideQuan(position);
                break;
            case 2://????????????
                loadImage(imageView, microphone.get(position).getHeadimgurl(), R.drawable.shf_img2);
                textView.setText(microphone.get(position).getNickname());
                textView.setTextColor(getResources().getColor(R.color.white));
                if (!TextUtils.isEmpty(microphone.get(position).getTxk())) {//?????????
                    imgTxk.setVisibility(View.VISIBLE);
                    loadImage(imgTxk, microphone.get(position).getTxk(), 0);
                } else {
                    imgTxk.setVisibility(View.GONE);
                }
                imgVedio.setVisibility(View.GONE);
                if (is_sound == 1) {
                    imgVedio.setVisibility(View.GONE);
                    imgVedio.setSelected(true);
                } else {
                    imgVedio.setVisibility(View.VISIBLE);
                    imgVedio.setSelected(false);
                }
                //????????????????????????????????????
                if (microphone.get(position).getUser_id().equals(String.valueOf(UserManager.getUser().getUserId()))) {
                    // ?????????????????????????????????????????????
                    // selfPosition = position;//?????????????????????
                    imgShangmai.setSelected(true);
                    imgShangmai.setVisibility(View.VISIBLE);
                    imgPaimai.setVisibility(View.GONE);
                    imgBimai.setVisibility(View.VISIBLE);
                    imgMusic.setVisibility(View.VISIBLE);
                    imgBiaoqing.setVisibility(View.VISIBLE);
                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
                }
                break;
            case 3://??????
                loadImage(imageView, "", R.drawable.bimai_img2);
                //textView.setText("");
                imgVedio.setVisibility(View.GONE);
                break;
            default:
        }

        int sex = microphone.get(position).getSex();
        switch (sex) {
            case 1:
                imageView.setBorderColor(getResources().getColor(R.color.font_89E0FB));
//                textNumber.setSelected(true);
                textNumber.setBackground(getResources().getDrawable(R.mipmap.room_xuhao_boy));
                break;
            case 2:
                imageView.setBorderColor(getResources().getColor(R.color.colorAccent));
//                textNumber.setSelected(false);
                textNumber.setBackground(getResources().getDrawable(R.mipmap.room_xuhao_girl));
                break;
            default:
                imageView.setBorderColor(getResources().getColor(R.color.translant));
                textNumber.setBackground(getResources().getDrawable(R.mipmap.room_xuhao_weizhi));
        }
        textMeili.setText(String.valueOf(microphone.get(position).getMeili()));
    }

    /**
     * 1.????????????????????????
     */
    private void initLive() {
        try {
            String myId = UserManager.getUser().getUserId() + "";
            mRtcEngine = RtcEngine.create(this, Api.AGORA_KEY, new IRtcEngineEventHandler() {
                @Override
                public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                    super.onJoinChannelSuccess(channel, uid, elapsed);
                    LogUtils.debugInfo("sgm", "====???????????????????????????");
                    LogUtil.d(own + "????????????????????????!");
                }

                @Override
                public void onError(int err) {
                    super.onError(err);
                    LogUtils.debugInfo("sgm", "====???????????????" + err);
                    LogUtils.debugInfo(own + "????????????????????????: " + err);
                }

                @Override
                public void onLeaveChannel(RtcStats stats) {
                    super.onLeaveChannel(stats);
                    LogUtils.debugInfo("sgm", "====?????????");
                    LogUtils.debugInfo(own + "??????!");
                }

                @Override
                public void onUserMuteAudio(int uid, boolean muted) {
                    super.onUserMuteAudio(uid, muted);
                    LogUtils.debugInfo("sgm", "====onUserMuteAudio" + muted);
                    LogUtil.d(own + "onUserMuteAudio???" + muted);
                }

                @Override
                public void onConnectionLost() {
                    super.onConnectionLost();
                    LogUtils.debugInfo("sgm", "====??????????????????");
                    LogUtils.debugInfo(own + "??????????????????");
                }

                @Override
                public void onAudioMixingStateChanged(int state, int errorCode) {//??????????????????
                    super.onAudioMixingStateChanged(state, errorCode);
                    LogUtils.debugInfo("====??????" + state);
                    LogUtil.d(own + "??????????????????: " + state);
                    switch (state) {
                        case 710://??????
                            runOnUiThread(() -> {
                                timer = new Timer();
                                timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(1);
                                    }
                                };
                                timer.schedule(timerTask, 100, 200);
                            });
                            break;
                        case 711://??????
                            runOnUiThread(() -> {
                                if (timer != null && timerTask != null) {
                                    timer.cancel();
                                    timerTask.cancel();
                                }
                            });
                            break;
                        case 713://??????
                            runOnUiThread(() -> {
                                try {
                                    if (listLocal != null && listLocal.size() > 0) {
                                        if (randomMusic == 0) {//????????????
                                            if (musicPosition == listLocal.size() - 1) {
//                                                        toast("???????????????????????????");
                                            } else {
                                                musicPosition = musicPosition + 1;
                                                seekBar.setProgress(0);
                                                textMusicName.setText(listLocal.get(musicPosition).name);
                                                mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                            }
                                        } else {//????????????
                                            seekBar.setProgress(0);
                                            musicPosition = BaseUtils.getRandom(listLocal.size());
                                            textMusicName.setText(listLocal.get(musicPosition).name);
                                            mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                    }
                }

                @Override
                public void onAudioVolumeIndication(AudioVolumeInfo[] speakers, int totalVolume) {
                    super.onAudioVolumeIndication(speakers, totalVolume);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // ??????????????????
                            if (speakers.length > 0) {
                                List<AudioVolumeInfo> listShuohua = new ArrayList<>();
                                for (AudioVolumeInfo list : speakers) {
                                    if (list.uid != 0) {
                                        listShuohua.add(list);
                                    }
                                }

                                int size = mMicrophone.size();  // ?????????????????????
                                for (AudioVolumeInfo audioVolumeInfo : listShuohua) {
                                    for (int i = 0; i < size; i++) {
                                        if (String.valueOf(audioVolumeInfo.uid).equals(mMicrophone.get(i).getUser_id())) {
                                            int colors = 0;
                                            String color = mMicrophone.get(i).getMic_color();
                                            if (!TextUtils.isEmpty(color)) {
                                                colors = Color.parseColor(color);
                                            }
                                            // ??????????????????????????????????????????????????????
                                            showQuan(i, audioVolumeInfo.volume, colors);
                                        } else if (String.valueOf(audioVolumeInfo.uid).equals(uid)) {
                                            int colorOne = 0;
                                            String colorStr = mMicrophone.get(i).getMic_color();
                                            if (!TextUtils.isEmpty(colorStr)) {
                                                colorOne = Color.parseColor(colorStr);
                                            }
                                            // ????????????????????????
                                            showQuan(8, audioVolumeInfo.volume, colorOne);
                                        }
                                    }
                                }

                                if (listShuohua.size() == 0) {
                                    //??????????????????????????????????????????????????????????????????
                                    for (int i = 0; i < size; i++) {
                                        Microphone.DataBean.MicrophoneBean itemPhone = mMicrophone.get(i);
                                        if (TextUtils.equals(itemPhone.getUser_id(), myId)) {
                                            // ??????????????????????????????
                                        } else {
                                            stopQuan(i);    // ??????????????????????????????
                                        }
                                    }
                                } else {
                                    for (AudioVolumeInfo audioVolumeInfo : listShuohua) {
                                        for (int i = 0; i < size; i++) {
                                            // ????????????uid??????????????????????????????
                                            if (!String.valueOf(audioVolumeInfo.uid).equals(mMicrophone.get(i).getUser_id())
                                                    && !String.valueOf(audioVolumeInfo.uid).equals(uid)) {
                                                stopQuan(i);
                                            }
                                        }
                                    }
                                }

                                //?????????????????????
                                for (AudioVolumeInfo list : speakers) {
//                                            Log.e("====????????????" + list.uid, "====??????" + list.volume);
                                    if (list.uid == 0 && list.volume > 20) {//????????????????????????????????????
                                        if (user_type == 1) {   // ???????????????
                                            int colors = 0;
                                            if (enterRoom != null && enterRoom.getRoom_info() != null && enterRoom.getRoom_info().size() > 0) {
                                                String color = enterRoom.getRoom_info().get(0).mic_color;
                                                if (!TextUtils.isEmpty(color)) {
                                                    colors = Color.parseColor(color);
                                                }
                                            }
                                            showQuan(8, list.volume, colors);
                                        } else {
                                            for (Microphone.DataBean.MicrophoneBean listPhone : mMicrophone) {
                                                // ??????????????????
                                                if (TextUtils.equals(listPhone.getUser_id(), myId)) {
                                                    int i = mMicrophone.indexOf(listPhone);
                                                    int colors = 0;
//                                                            if (enterRoom != null && enterRoom.getRoom_info() != null && enterRoom.getRoom_info().size() > 0) {
                                                    String color = mMicrophone.get(i).getMic_color();
                                                    if (!TextUtils.isEmpty(color)) {
                                                        colors = Color.parseColor(color);
                                                    }
//                                                            }
                                                    showQuan(i, list.volume, colors);  // ??????????????????????????????
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                }
                            } else {
                                // ????????????????????????????????????
                                int size = mMicrophone.size();
                                for (int i = 0; i < size; i++) {
                                    stopQuan(i);
                                }
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
        mRtcEngine.setAudioProfile(4, 3);
        if (user_type == 1) mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);//1????????????
        else mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);//2?????????
        /*mRtcEngine.setDefaultAudioRoutetoSpeakerphone(false);//??????????????????????????????????????????
        mRtcEngine.setAudioProfile(5,1);
        mRtcEngine.adjustPlaybackSignalVolume(400);*/
        mRtcEngine.joinChannel("", uid, "OpenVCall", UserManager.getUser().getUserId());
        mRtcEngine.enableAudioVolumeIndication(1000, 3);//??????????????????
        mRtcEngine.adjustAudioMixingPlayoutVolume(10);// ??????????????????????????????????????????????????????????????????
        mRtcEngine.adjustPlaybackSignalVolume(150);// ????????????????????????,????????????????????????????????? 0 - 400???????????? 100 ????????????????????????????????????????????????400 ????????????????????? 4 ???
        mRtcEngine.adjustRecordingSignalVolume(100);// ????????????????????????,????????????????????????????????? 0 - 400???????????? 100 ????????????????????????????????????????????????400 ????????????????????? 4 ???
        mRtcEngine.adjustAudioMixingPublishVolume(10);// ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? 0 - 100???????????? 100 ???????????????????????????????????????????????????
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
        mBarrageView.setOptions(options);

        mBarrageView.setAdapter(mBarrageViewAdapter = new BarrageAdapter<PushBean>(null, this) {
            @Override
            public BarrageViewHolder<PushBean> onCreateViewHolder(View root, int type) {
                return new DanMuViewHolder(root, AdminHomeActivity.this);// ?????????????????????ViewHolder
            }

            @Override
            public int getItemLayout(PushBean barrageData) {
                return R.layout.danmu;// ?????????????????????????????????
            }
        });

        // ???????????????
        mBarrageViewAdapter.setAdapterListener(new AdapterListener<PushBean>() {
            @Override
            public void onItemClick(BarrageAdapter.BarrageViewHolder<PushBean> holder, PushBean item) {
                if (item != null) {
                    if ("gift".equals(item.type)) {
                        enterData(item.getData().getUid() + "", "", commonModel, 1, "0");
                    }
                }
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void loadData() {
        RxUtils.loading(commonModel.getRoomUsers(uid), this)
                .subscribe(new ErrorHandleSubscriber<AdminUser>(mErrorHandler) {
                    @Override
                    public void onNext(AdminUser adminUser) {
                        mAdminUser = adminUser;
                        onlinePepole.setText(adminUser.getData().getVisitor().size() + "???");
                    }
                });
    }

    //?????????????????????????????????????????????????????????userInfo??????userid???
    public void setFirstNameClick(int position) {
        try {
            if (String.valueOf(UserManager.getUser().getUserId()).equals(roomMessageAdapter.getData().get(position).getUser_id())) {
                // ???????????????????????????
                setMyDataDialog(roomMessageAdapter.getData().get(position).getUser_id() + "");
            } else {
                if (user_type == 1 || user_type == 2) {
                    if (mMicrophone != null) {
                        String selectId = roomMessageAdapter.getData().get(position).getUser_id();
                        int m = 0;
                        for (int i = 0; i < mMicrophone.size(); i++) {
                            String userIds = mMicrophone.get(i).getUser_id();
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//???????????????
                                setVedioDialog(selectId);
                                break;
                            } else {
                                m++;
                            }
                        }
                        if (m == mMicrophone.size() && m != 0) {
                            setEditOtherDataDialog(selectId);
                        }
                    } else {

                        setEditOtherDataDialog(roomMessageAdapter.getData().get(position).getUser_id());
                    }
                } else {
                    // ???????????????????????????


                    setOtherDataDialog(roomMessageAdapter.getData().get(position).getUser_id());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //?????????????????????????????????????????????????????????userInfo??????userid???
    public void setSecondNameClick(int position) {
        try {
            if (String.valueOf(UserManager.getUser().getUserId()).equals(roomMessageAdapter.getData().get(position).userInfo.get(0).userId)) {
                setMyDataDialog(roomMessageAdapter.getData().get(position).userInfo.get(0).userId);
            } else {
                if (user_type == 1 || user_type == 2) {
                    if (mMicrophone != null) {
                        String selectId = roomMessageAdapter.getData().get(position).userInfo.get(0).userId;
                        int m = 0;
                        for (int i = 0; i < mMicrophone.size(); i++) {
                            String userIds = mMicrophone.get(i).getUser_id();
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//???????????????
                                setVedioDialog(selectId);
                                break;
                            } else {
                                m++;
                            }
                        }
                        if (m == mMicrophone.size() && m != 0) {
                            setEditOtherDataDialog(selectId);
                        }
                    } else {
                        setEditOtherDataDialog(roomMessageAdapter.getData().get(position).userInfo.get(0).userId);
                    }
                } else {
                    setOtherDataDialog(roomMessageAdapter.getData().get(position).userInfo.get(0).userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //?????????????????????????????????????????????????????????userInfo??????userid???
    public void setFirstNameClickNew(int position) {
        try {
            if (String.valueOf(UserManager.getUser().getUserId()).equals(roomMessageAdapter.getData().get(position).toUser_id)) {
                setMyDataDialog(roomMessageAdapter.getData().get(position).toUser_id);
            } else {
                if (user_type == 1 || user_type == 2) {
                    String selectId = roomMessageAdapter.getData().get(position).toUser_id;
                    if (mMicrophone != null) {
                        int m = 0;
                        for (int i = 0; i < mMicrophone.size(); i++) {
                            String userIds = mMicrophone.get(i).getUser_id();
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//???????????????
                                setVedioDialog(selectId);
                                break;
                            } else {
                                m++;
                            }
                        }
                        if (m == mMicrophone.size() && m != 0) {
                            setEditOtherDataDialog(selectId);
                        }
                    } else {
                        setEditOtherDataDialog(selectId);
                    }
                } else {
                    setOtherDataDialog(roomMessageAdapter.getData().get(position).toUser_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //?????????????????????????????????????????????????????????toUserId???
    public void setSecondNameClickNew(int position) {
        try {
            if (String.valueOf(UserManager.getUser().getUserId()).equals(roomMessageAdapter.getData().get(position).toUser_id)) {
                setMyDataDialog(roomMessageAdapter.getData().get(position).toUser_id);
            } else {
                if (user_type == 1 || user_type == 2) {
                    String selectId = roomMessageAdapter.getData().get(position).toUser_id;
                    if (mMicrophone != null) {
                        int m = 0;
                        for (int i = 0; i < mMicrophone.size(); i++) {
                            String userIds = mMicrophone.get(i).getUser_id();
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//???????????????
                                setVedioDialog(selectId);
                                break;
                            } else {
                                m++;
                            }
                        }
                        if (m == mMicrophone.size() && m != 0) {
                            setEditOtherDataDialog(selectId);
                        }
                    } else {
                        setEditOtherDataDialog(selectId);
                    }
                } else {
                    setOtherDataDialog(roomMessageAdapter.getData().get(position).toUser_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????
     */
    private void startKeepLiveService() {
        // ????????????????????????????????????
        stopkeepLiveService();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0????????????startForegroundService??????service
            startForegroundService(new Intent(AdminHomeActivity.this, RoomPlayService.class));
        } else {
            startService(new Intent(AdminHomeActivity.this, RoomPlayService.class));
        }
    }

    /**
     * ????????????
     */
    private void stopkeepLiveService() {
        // service??????????????????
        boolean isStartService = MyUtil.isServiceExisted(this, "com.konglianyuyin.mx.app.service.RoomPlayService");
        if (isStartService) {
            Intent stopIntent = new Intent(this, RoomPlayService.class);
            stopService(stopIntent);
        }
    }

    /**
     * ???????????????????????????
     */
    private void showQuan(int position, int volume, int color) {
        if (mWaveView1 == null || mWaveView2 == null || mWaveView3 == null || mWaveView4 == null || mWaveView5 == null
                || mWaveView6 == null || mWaveView7 == null || mWaveView8 == null || mWaveViewZhu == null) {
            return;
        }
        if (color == 0) color = getResources().getColor(R.color.translant);
        float radius = (float) Math.log10(Math.max(1, volume)) * QMUIDisplayHelper.dpToPx(25);
        LogUtils.debugInfo("voiceDb==" + volume + "radius ==" + radius + "????????????=" + QMUIDisplayHelper.dpToPx(25));
        switch (position) {
            case 0:
//                loadQuan(imgQuan1);
//                mVoice1 = volume;
                mWaveView1.setColor(color);
                mWaveView1.addCircle(radius);
                break;
            case 1:
                mWaveView2.setColor(color);
                mWaveView2.addCircle(radius);
//                mVoice2 = volume;
//                    mWaveView2.setRippleColor(color);
//                    mWaveView2.startRecording();
                break;
            case 2:
                mWaveView3.setColor(color);
                mWaveView3.addCircle(radius);
//                mVoice3 = volume;
//                    mWaveView3.setRippleColor(color);
//                    mWaveView3.startRecording();
                break;
            case 3:
                mWaveView4.setColor(color);
                mWaveView4.addCircle(radius);
//                loadQuan(imgQuan4);
//                mVoice4 = volume;
//                    mWaveView4.setRippleColor(color);
//                    mWaveView4.startRecording();
                break;
            case 4:
                mWaveView5.setColor(color);
                mWaveView5.addCircle(radius);
//                loadQuan(imgQuan5);
//                mVoice5 = volume;
//                    mWaveView5.setRippleColor(color);
//                    mWaveView5.startRecording();
                break;
            case 5:
                mWaveView6.setColor(color);
                mWaveView6.addCircle(radius);
//                loadQuan(imgQuan6);
//                mVoice6 = volume;
//                    mWaveView6.setRippleColor(color);
//                    mWaveView6.startRecording();
                break;
            case 6:
                radius = (float) Math.log10(Math.max(1, volume)) * QMUIDisplayHelper.dpToPx(40);
//                LogUtils.debugInfo("voiceDb=="+mVoice1+"radius =="+radius+"????????????="+QMUIDisplayHelper.dpToPx(50));
                mWaveView7.setColor(color);
                mWaveView7.addCircle(radius);
//                loadQuan(imgQuan7);
//                mVoice7 = volume;
//                    mWaveView7.setRippleColor(color);
//                    mWaveView7.startRecording();
                break;
            case 7:
                mWaveView8.setColor(color);
                mWaveView8.addCircle(radius);
//                loadQuan(imgQuan8);
//                mVoice8 = volume;
//                    mWaveView8.setRippleColor(color);
//                    mWaveView8.startRecording();
                break;
            case 8://??????
                mWaveViewZhu.setColor(color);
                mWaveViewZhu.addCircle(radius);
//                loadQuan(imgQuanZhu);
//                mVoiceZhu = volume;
//                    mWaveViewZhu.setRippleColor(color);
//                    mWaveViewZhu.startRecording();
                break;
        }
    }

    /**
     * ???????????????????????????
     *
     * @param position
     */
    private void stopQuan(int position) {
        if (mWaveView1 == null || mWaveView2 == null || mWaveView3 == null || mWaveView4 == null || mWaveView5 == null
                || mWaveView6 == null || mWaveView7 == null || mWaveView8 == null || mWaveViewZhu == null) {
            return;
        }
        int color = getResources().getColor(R.color.translant);
//        LogUtils.debugInfo("?????????===============");
        switch (position) {
            case 0:
//                if (mWaveView1.isRecording()) {
//                    mVoice1 = 0;
//                    mWaveView1.setRippleColor(color);
//                    mWaveView1.stopRecording();
//                    mWaveView1.reset();
//                }
                break;
            case 1:
//                    mVoice2 = 0;
//                    mWaveView2.setRippleColor(color);
//                    mWaveView2.stopRecording();
                break;
            case 2:
//                    mVoice3 = 0;
//                    mWaveView3.setRippleColor(color);
//                    mWaveView3.stopRecording();
                break;
            case 3:
//                    mVoice4 = 0;
//                    mWaveView4.setRippleColor(color);
//                    mWaveView4.stopRecording();
                break;
            case 4:
//                    mVoice5 = 0;
//                    mWaveView5.setRippleColor(color);
//                    mWaveView5.stopRecording();
                break;
            case 5:
//                    mVoice6 = 0;
//                    mWaveView6.setRippleColor(color);
//                    mWaveView6.stopRecording();
                break;
            case 6:
//                    mVoice7 = 0;
//                    mWaveView7.setRippleColor(color);
//                    mWaveView7.stopRecording();
                break;
            case 7:
//                    mVoice8 = 0;
//                    mWaveView8.setRippleColor(color);
//                    mWaveView8.stopRecording();
                break;
            case 8:
//                    mVoiceZhu = 0;
//                    mWaveViewZhu.setRippleColor(color);
//                    mWaveViewZhu.stopRecording();
                break;
        }

    }

    /**
     * 2.???????????????im??????
     */
    private void initMessage() {
        try {
            // 1.??????sdk??????????????????
            mRtmClient = RtmClient.createInstance(this, Api.AGORA_KEY, new RtmClientListener() {
                @Override
                public void onConnectionStateChanged(int state, int reason) {
                    Log.d(TAG, "on connection state changed to " + state + " reason: " + reason);
                    LogUtil.d(own + "connection state: " + state + " reason: " + reason);
                }

                @Override
                public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
                    //????????????????????????
                    String msg = rtmMessage.getText();
                    Log.d(TAG, "Receives message: " + msg + " from " + peerId);
                    LogUtil.d(own + "Receives message: " + msg + " from " + peerId);

                    if (is_show_egg == false){
                        getLevelBean();
                    }

//                            MessageBean messageBean = null;
//                            if(!TextUtils.isEmpty(msg)){
//                                try{
//                                    messageBean = BaseUtils.getMessageBean(msg);
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
                    if (msg.equals(nfgk184grdgdfggunaliyuanxiamai)) {//??????????????????
                        runOnUiThread(() -> {
                            loadVedioList();
                            mRtcEngine.stopAudioMixing();
                            forcedDownVedio();
                        });
                    } else if (msg.equals(nfgk184grdgdfggunaliyuanbimai)) {//??????????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadVedioList();
                                mRtcEngine.stopAudioMixing();
                                mRtcEngine.enableLocalAudio(false);
                                imgBimai.setSelected(true);
                                isEditBimai = true;
                            }
                        });

                    } else if (msg.equals(nfgk184grdgdfggunaliyuantichu)) {//???????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast("????????????????????????!");
                                mRtcEngine.stopAudioMixing();
                                isStart = false;
                                finish();
                            }
                        });
                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkBeiJinYan)) {//?????????
                        runOnUiThread(() -> {
                            toast("??????????????????3??????!");
                            mRtcEngine.stopAudioMixing();
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkkaimai)) {//?????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRtcEngine.enableLocalAudio(true);
                                imgBimai.setSelected(false);
                                isEditBimai = false;
                            }
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkShezhiguanliyuan)) {//????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgAdd.setVisibility(View.VISIBLE);
//                                        imgMusic.setVisibility(View.VISIBLE);
                                user_type = 2;
                            }
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkQuxiaoiguanliyuan)) {//????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgAdd.setVisibility(View.GONE);
                                imgMusic.setVisibility(View.GONE);
                                user_type = 5;
                            }
                        });
                    } else {//??????CP??????,?????????????????????????????????????????????????????????
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject object = new JSONObject(msg);
                                    if (object != null) {
                                        String msgType = object.getString("messageType");
                                        if (!TextUtils.isEmpty(msgType)) {
                                            if (TextUtils.equals("2", msgType)) {
                                                // ?????????????????????CP????????????????????????????????????????????????
                                                String nickName = object.getString("nickName");
                                                String user_id = object.getString("user_id");
                                                String headimgurlss = object.getString("headimgurl");
                                                String nick_color = object.getString("nick_color");
                                                LoginData localUser = UserManager.getUser();

                                                RequestCPDialog requestCPDialog = new RequestCPDialog(AdminHomeActivity.this, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        switch (view.getId()) {
                                                            case R.id.tv_left://??????????????????????????????
                                                                RxUtils.loading(commonModel.handle_cp(localUser.getToken(), localUser.getUserId() + "",
                                                                                "2"), AdminHomeActivity.this)
                                                                        .subscribe(new ErrorHandleSubscriber<AgreeCpResult>(mErrorHandler) {
                                                                            @Override
                                                                            public void onNext(AgreeCpResult agreeCpResult) {
                                                                                toast("????????????????????????CP??????");
                                                                                String myUserName = localUser.getNickname();
                                                                                String messageType = "1";
                                                                                String cpType = "2";
                                                                                JsonObject rootObj = new JsonObject();
                                                                                rootObj.addProperty("nickName", myUserName);
                                                                                rootObj.addProperty("messageType", messageType);
                                                                                rootObj.addProperty("cpType", cpType);
                                                                                String str = rootObj.toString();
                                                                                LogUtil.d(own + "????????????CP??????: " + str);
                                                                                sendPeerMessage(user_id, str);
                                                                            }
                                                                        });
                                                                break;
                                                            case R.id.tv_right://??????,????????????????????????????????????
                                                                RxUtils.loading(commonModel.handle_cp(localUser.getToken(), localUser.getUserId() + "",
                                                                                "1"), AdminHomeActivity.this)
                                                                        .subscribe(new ErrorHandleSubscriber<AgreeCpResult>(mErrorHandler) {
                                                                            @Override
                                                                            public void onNext(AgreeCpResult agreeCpResult) {
                                                                                if (agreeCpResult != null && agreeCpResult.getData() != null) {
                                                                                    toast("???????????????" + nickName + "????????????CP???");
                                                                                    String myUserNames = localUser.getNickname();
                                                                                    String messageTypes = "1";
                                                                                    String cpTypes = "1";
                                                                                    JsonObject rootObjs = new JsonObject();
                                                                                    rootObjs.addProperty("nickName", myUserNames);
                                                                                    rootObjs.addProperty("messageType", messageTypes);
                                                                                    rootObjs.addProperty("cpType", cpTypes);
                                                                                    String strs = rootObjs.toString();
                                                                                    //??????????????????????????????????????????CP???
                                                                                    LogUtil.d(own + "????????????CP???????????????: " + strs);
                                                                                    sendPeerMessage(user_id, strs);

                                                                                    MessageBean messageBean = new MessageBean();
                                                                                    messageBean.setMessageType("11");
                                                                                    messageBean.setNickName(myUserNames);
                                                                                    messageBean.nick_color = vipBean.getData().getNick_color();//CP??????
                                                                                    messageBean.setUser_id(localUser.getUserId() + "");
                                                                                    messageBean.headimgurl = localUser.getHeadimgurl();
                                                                                    messageBean.toUser_id = user_id + "";
                                                                                    messageBean.toNickName = nickName;
                                                                                    messageBean.toNick_color = nick_color;//CP??????
                                                                                    messageBean.toheadimgurl = headimgurlss;
                                                                                    String jsons = JSON.toJSONString(messageBean);

                                                                                    //?????????????????????
                                                                                    roomMessageAdapter.getData().add(messageBean);
                                                                                    roomMessageAdapter.notifyDataSetChanged();
                                                                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                                                                    //????????????CP???????????????
                                                                                    LogUtil.d(own + "????????????CP????????????: " + jsons);
                                                                                    sendChannelMessage(jsons);

                                                                                    //????????????CP???????????????
//                                                                        sendChannelMessage(BaseUtils.getJson("11", nickName+"???"+myUserNames+"????????????CP",
//                                                                                data.getNickname(),
//                                                                                String.valueOf(data.getUserId()), "", ""));
                                                                                }
                                                                            }
                                                                        });
                                                                break;
                                                        }

                                                    }
                                                }, user_id, nickName, headimgurlss);
                                                requestCPDialog.show();
                                            } else if (TextUtils.equals("1", msgType)) {
                                                //????????????CP?????????
                                                String cType = object.getString("cpType");
                                                if (TextUtils.equals("2", cType)) {
                                                    toast("????????????????????????CP??????");  // ?????????CP
                                                } else {
                                                    String nickName = object.getString("nickName");
                                                    toast("???????????????" + nickName + "????????????CP???");
                                                }
                                            } else if (TextUtils.equals("8", msgType)) {
                                                // ?????????CP?????????????????????
                                                MessageBean newMessage = BaseUtils.getMessageBean(msg);
                                                if (newMessage != null) {
                                                    newMessage.setMessageType("9");
                                                    roomMessageAdapter.getData().add(newMessage);
                                                    roomMessageAdapter.notifyDataSetChanged();
                                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onTokenExpired() {
                }
            });

            //2 ??????sdk??????
            mRtmClient.login(null, String.valueOf(UserManager.getUser().getUserId()), new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtils.debugInfo("====??????????????????");
                    LogUtil.d(own + "im login success!");
                    joinChanalMessage();
                    joinChanalMessage2();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    LogUtils.debugInfo("====??????????????????");
                    LogUtils.debugInfo(own + "im login err " + errorInfo.getErrorCode() + "_" + errorInfo.getErrorDescription());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("You need to check the RTM init process.");
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param parser
     * @param giftUrl
     * @param svgaImageView
     */
    public void showServerSVG(SVGAParser parser, String giftUrl, SVGAImageView svgaImageView) {
        if (!ExtConfig.isSendAllGiftShow){
            return;
        }

//        boolean closeGif = (boolean) SharedPreferencesUtils.getParam(this, "SHOWGIF", false);
//        if (closeGif) return;
        try {
            parser.decodeFromURL(new URL(giftUrl), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    svgaImageView.setVideoItem(svgaVideoEntity);
                    svgaImageView.setLoops(1);
                    svgaImageView.stepToFrame(1, true);
                    setSvgImgClickble();
                }

                @Override
                public void onError() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3.??????????????????
     */
    private void joinChanalMessage() {
        LogUtil.d(own + "?????????????????? joinChanalMessage");
        // 3?????????????????????
        try {
            mRtmChannel = mRtmClient.createChannel(uid, new RtmChannelListener() {
                @Override
                public void onMessageReceived(RtmMessage message, RtmChannelMember fromMember) {
                    LogUtil.d(own + "onMessageReceived: user - " + fromMember.getUserId());
                    LogUtil.d(own + "onMessageReceived: msg - " + message.getText());
                    //TODO  1???????????? ????????????  2???????????????  3??? ??????????????????
                    // 4 ???????????????  5 ??????????????? 6??????????????? 7????????????
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String account = fromMember.getUserId();
                            String msg = message.getText();
                            LogUtils.debugInfo("====?????????id" + account + "?????????????????? = " + msg);
                            MessageBean messageBean = BaseUtils.getMessageBean(msg);
                            if (TextUtils.equals(messageBean.getMessageType(), "3")) {
                                // 3???????????????
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "2")) {
                                // 2????????????
                                if (mHasCPAtRoom) {
                                    // ???CP???????????????????????????????????????????????????
                                    return;
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {
                                    // ???VIP??????
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {
                                    // ????????????
                                    textLayout.setVisibility(View.GONE);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "1")) {
                                // 1????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "6")) {
                                // 6?????????????????????
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "7")) {
                                // 7??????????????????
                                loadEnterRoom();    // ?????????????????????????????????
                                String gongGao = messageBean.getRoom_intro();
                                if (!TextUtils.equals(mStringGongGao, gongGao)) {//????????????
                                    LogUtils.debugInfo("????????????");
                                    mStringGongGao = gongGao;
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    LogUtils.debugInfo("????????????");
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "5")) {
                                // 5????????????
                                String emoji = messageBean.getEmoji();
                                if (TextUtils.equals(uid, account)) {
                                    imgRoomGif.setVisibility(View.VISIBLE);
                                    loadOneTimeGif(AdminHomeActivity.this, imgRoomGif, emoji, ()
                                            -> imgRoomGif.setVisibility(View.GONE));
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {
                                        // ?????????????????????
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                } else {
                                    int maiPosition = 0;//????????????
                                    for (Microphone.DataBean.MicrophoneBean list : mMicrophone) {
                                        if (TextUtils.equals(list.getUser_id(), account)) {
                                            maiPosition = mMicrophone.indexOf(list);
                                        }
                                    }
                                    maiPosition = maiPosition + 1;
                                    switch (maiPosition) {
                                        case 1:
                                            loadGifShow(imgGif1, emoji);
                                            break;
                                        case 2:
                                            loadGifShow(imgGif2, emoji);
                                            break;
                                        case 3:
                                            loadGifShow(imgGif3, emoji);
                                            break;
                                        case 4:
                                            loadGifShow(imgGif4, emoji);
                                            break;
                                        case 5:
                                            loadGifShow(imgGif5, emoji);
                                            break;
                                        case 6:
                                            loadGifShow(imgGif6, emoji);
                                            break;
                                        case 7:
                                            loadGifShow(imgGif7, emoji);
                                            break;
                                        case 8:
                                            loadGifShow(imgGif8, emoji);
                                            break;
                                    }
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//?????????????????????
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "401")) {
                                enterRoomRefrash();
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "4")) {
                                // ????????????
                                enterRoomRefrash();
                                loadVedioList();
                                List<MessageBean.Data> userInfo = messageBean.userInfo;
                                if (userInfo.size() == 1) {
//                                    messageBean.nick_color = vipBean.getData().getNick_color();
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    for (MessageBean.Data list : userInfo) {
                                        MessageBean newMessage = new MessageBean();
                                        newMessage.setUser_id(messageBean.getNickName());
                                        newMessage.setNickName(messageBean.getNickName());
                                        newMessage.nick_color = messageBean.nick_color;
                                        newMessage.show_img = messageBean.show_img;
                                        newMessage.show_gif_img = messageBean.show_gif_img;
                                        newMessage.type = messageBean.type;
                                        newMessage.giftNum = messageBean.giftNum;
                                        newMessage.e_name = messageBean.e_name;
                                        newMessage.setMessageType("4");
                                        List<MessageBean.Data> dataList = new ArrayList<>();
                                        dataList.add(list);
                                        newMessage.userInfo = dataList;
                                        roomMessageAdapter.getData().add(newMessage);
                                    }
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                }
                                // ??????????????????
                                if (TextUtils.equals(messageBean.type, "2")) {
                                    //??????????????????
                                    try {
                                        SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
                                        showServerSVG(parser, messageBean.show_gif_img, svgImage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // ????????????????????????
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setStateMessage(StateMessage.CLOSE_GIFT_WINDOW);
                                    EventBus.getDefault().post(messageEvent);
                                } else if (TextUtils.equals(messageBean.type, "1")) {
                                    // ????????????
                                    loadAniData(messageBean.userInfo, messageBean.show_img);
                                }


                            } else if (TextUtils.equals(messageBean.getMessageType(), "11")) {
                                // 11????????????CP????????????????????????XXX???XX????????????CP???
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "8")) {
                                // 8CP???????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {
                                    // ????????????
                                    textLayout.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(messageBean.cp_tx)) {
                                    // ???????????????
                                    playCpTongFangTX(messageBean.cp_tx, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {
                                    // ???VIP??????
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "12")) {
                                // 12????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (!TextUtils.isEmpty(messageBean.cp_xssm)) {
                                    // ???????????????
                                    playCpTX(messageBean.cp_xssm, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "13")) {
                                if (is_show_egg){
                                    // 13?????????
                                    if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }

                            } else if (TextUtils.equals(messageBean.getMessageType(), "6666")) {
                                if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "15")) {
                                // 15????????????????????????
                                if (user_type == 1) {//???????????????,????????????????????????????????????????????????????????????????????????
                                    tvNewUpMicrophone.setVisibility(View.VISIBLE);
                                    updateRequestUpMicrophoneList();
                                }
                            }
                        }
                    });
                }

                @Override
                public void onMemberJoined(RtmChannelMember rtmChannelMember) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                            LogUtils.debugInfo("====??????????????????");
                            LogUtil.d(own + "??????????????????");
                        }
                    });
                }

                @Override
                public void onMemberLeft(RtmChannelMember rtmChannelMember) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                            String userId = rtmChannelMember.getUserId();
                            LogUtil.d(own + "??????????????????");
                            //???????????????????????????????????????????????????????????????????????????
                            try {
                                for (Microphone.DataBean.MicrophoneBean list : mMicrophone) {
                                    if (list != null) {
                                        if (TextUtils.equals(list.getUser_id(), userId)) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadVedioList();    // ???????????????
                                                }
                                            }, 1000);
                                            break;
                                        }
                                    }
                                }
                                if (userId.equals(uid)) {
                                    // ?????????????????????????????????
                                    textLayout.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {

                            }
                        }
                    });
                }
            });
        } catch (RuntimeException e) {
            LogUtils.debugInfo("====????????????????????????");
            LogUtils.debugInfo(own + "????????????????????????: " + e.getMessage());
        }
        // 4??????
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                LogUtils.debugInfo("====????????????????????????");
                LogUtil.d("====????????????????????????");
                //?????????????????????
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (vipBean != null && vipBean.getData() != null) {
                            List<VipBean.DataBean.CpUsersBean> cp_user_list = vipBean.getData().getCp_users();//????????????cp??????
                            LoginData loginData = UserManager.getUser();
                            if (!TextUtils.isEmpty(vipBean.getData().getVip_tx())) {//vip????????????
                                playVIPTX(vipBean.getData().getVip_tx(), loginData.getNickname());
                            }

                            if (cp_user_list != null && cp_user_list.size() > 0) {
                                // ???CP?????????????????????
                                for (VipBean.DataBean.CpUsersBean cpUsersBean : cp_user_list) {
//                                                            mHasCPAtRoom = true;
                                    //????????????????????????
                                    MessageBean newMessage = new MessageBean();
                                    newMessage.hz_img = vipBean.getData().getHz_img();
                                    newMessage.vip_tx = vipBean.getData().getVip_tx();
                                    newMessage.vip_img = vipBean.getData().getVip_img();

                                    newMessage.setNickName(loginData.getNickname());
                                    newMessage.setUser_id(loginData.getUserId() + "");
                                    newMessage.nick_color = vipBean.getData().getNick_color();
                                    newMessage.headimgurl = loginData.getHeadimgurl();

//                                                            MessageBean.Data toUser = new MessageBean.Data();

                                    newMessage.toNickName = cpUsersBean.getNickname();
                                    newMessage.toNick_color = cpUsersBean.getNick_color();
                                    newMessage.toheadimgurl = cpUsersBean.getHeadimgurl();
                                    newMessage.toUser_id = cpUsersBean.getId();
                                    newMessage.cp_tx = cpUsersBean.getCp_tx();//CP??????

                                    if (!TextUtils.isEmpty(cpUsersBean.getCp_tx())) {//??????CP????????????
                                        playCpTongFangTX(cpUsersBean.getCp_tx(), loginData.getNickname(), loginData.getHeadimgurl(), newMessage.toNickName, newMessage.toheadimgurl);
                                    }

//                                    newMessage.setMessageType("10");

//                                    roomMessageAdapter.getData().add(newMessage);

                                    //?????????CP??????
                                    newMessage.setMessageType("8");
                                    String jsonStr = JSON.toJSONString(newMessage);
                                    Log.e("??????????????????CP", jsonStr);
                                    LogUtil.d(own + "??????????????????CP: " + jsonStr);
                                    sendPeerMessage(cpUsersBean.getId(), jsonStr);

                                    //??????????????????8?????????????????????,??????????????????CP %@ ??? %@ ???????????????
                                    sendChannelMessage(jsonStr);
                                }
//                                roomMessageAdapter.notifyDataSetChanged();
//                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else {
                                //?????????????????????
//                                MessageBean messageBean = new MessageBean();
//                                messageBean.setMessageType("2");
//                                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
//                                messageBean.setNickName(loginData.getNickname());
//                                messageBean.nick_color = vipBean.getData().getNick_color();
//                                listMessage.add(messageBean);
//                                roomMessageAdapter.setNewData(listMessage);
                                sendEnterRoom();
                            }

                            //?????????????????????????????????????????????????????????
                            if (TextUtils.equals(UserManager.getUser().getUserId() + "", uid)) {
                                //???????????????
                                textLayout.setVisibility(View.GONE);
                            }
                        } else {
                            sendChannelMessage(BaseUtils.getJson("2", "???????????????",
                                    UserManager.getUser().getNickname(),
                                    String.valueOf(UserManager.getUser().getUserId()), "", ""));
                        }
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.debugInfo("====????????????????????????");
                LogUtils.debugInfo(own + "????????????????????????: " + errorInfo.getErrorDescription());
            }
        });
    }

    // ????????????????????????????????????????????????
    private void updateRequestUpMicrophoneList() {
        RxUtils.loading(commonModel.requestUpMicrophoneList(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", uid), this)
                .subscribe(new ErrorHandleSubscriber<RequestUpMicrophoneBean>(mErrorHandler) {
                    @Override
                    public void onNext(RequestUpMicrophoneBean microphone) {
                        if (microphone.getCode() == 1) {
                            requestUpMicrophoneData = microphone.getData();
                        }
                    }
                });
    }

    RtmChannel mRtmChannel2;

    private void joinChanalMessage2() {
        try {
            mRtmChannel2 = mRtmClient.createChannel("897432975", new RtmChannelListener() {
                @Override
                public void onMessageReceived(RtmMessage message, RtmChannelMember fromMember) {
                    Log.i("chuyibo", "onMessageReceived");
                    LogUtil.d(own + "897432975 - onMessageReceived: " + fromMember.getUserId() + "" + message.getText());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String account = fromMember.getUserId();
                            String msg = message.getText();
                            LogUtils.debugInfo("====?????????id" + account + "?????????????????? = " + msg);
                            MessageBean messageBean = BaseUtils.getMessageBean(msg);
                            if (TextUtils.equals(messageBean.getMessageType(), "3")) {//???????????????
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "2")) {//????????????
                                if (mHasCPAtRoom) {//???CP???????????????????????????????????????????????????
                                    return;
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {//???VIP??????
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
//                                roomMessageAdapter.
                                if (uid.equals(account)) {//????????????
                                    textLayout.setVisibility(View.GONE);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "1")) {//????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "6")) {//?????????????????????
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "7")) {//??????????????????
                                loadEnterRoom();
                                String gongGao = messageBean.getRoom_intro();
                                if (!TextUtils.equals(mStringGongGao, gongGao)) {//????????????
                                    LogUtils.debugInfo("????????????");
                                    mStringGongGao = gongGao;
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    LogUtils.debugInfo("????????????");
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "5")) {//????????????
                                String emoji = messageBean.getEmoji();
                                if (TextUtils.equals(uid, account)) {
                                    imgRoomGif.setVisibility(View.VISIBLE);
                                    loadOneTimeGif(AdminHomeActivity.this, imgRoomGif, emoji, ()
                                            -> imgRoomGif.setVisibility(View.GONE));
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//?????????????????????
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                } else {
                                    int maiPosition = 0;//????????????
                                    for (Microphone.DataBean.MicrophoneBean list : mMicrophone) {
                                        if (TextUtils.equals(list.getUser_id(), account)) {
                                            maiPosition = mMicrophone.indexOf(list);
                                        }
                                    }
                                    maiPosition = maiPosition + 1;
                                    switch (maiPosition) {
                                        case 1:
                                            loadGifShow(imgGif1, emoji);
                                            break;
                                        case 2:
                                            loadGifShow(imgGif2, emoji);
                                            break;
                                        case 3:
                                            loadGifShow(imgGif3, emoji);
                                            break;
                                        case 4:
                                            loadGifShow(imgGif4, emoji);
                                            break;
                                        case 5:
                                            loadGifShow(imgGif5, emoji);
                                            break;
                                        case 6:
                                            loadGifShow(imgGif6, emoji);
                                            break;
                                        case 7:
                                            loadGifShow(imgGif7, emoji);
                                            break;
                                        case 8:
                                            loadGifShow(imgGif8, emoji);
                                            break;
                                    }
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//?????????????????????
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "401")) {
                                enterRoomRefrash();
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "4")) {//????????????
                                enterRoomRefrash();
                                loadVedioList();
                                List<MessageBean.Data> userInfo = messageBean.userInfo;
                                if (userInfo.size() == 1) {
//                                    messageBean.nick_color = vipBean.getData().getNick_color();
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    for (MessageBean.Data list : userInfo) {
                                        MessageBean newMessage = new MessageBean();
                                        newMessage.setUser_id(messageBean.getNickName());
                                        newMessage.setNickName(messageBean.getNickName());
                                        newMessage.nick_color = messageBean.nick_color;
                                        newMessage.show_img = messageBean.show_img;
                                        newMessage.show_gif_img = messageBean.show_gif_img;
                                        newMessage.type = messageBean.type;
                                        newMessage.giftNum = messageBean.giftNum;
                                        newMessage.e_name = messageBean.e_name;
                                        newMessage.setMessageType("4");
                                        List<MessageBean.Data> dataList = new ArrayList<>();
                                        dataList.add(list);
                                        newMessage.userInfo = dataList;
                                        roomMessageAdapter.getData().add(newMessage);
                                    }
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                }
                                //??????????????????
                                if (TextUtils.equals(messageBean.type, "2")) {//??????
                                    try {
                                        SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
                                        showServerSVG(parser, messageBean.show_gif_img, svgImage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //????????????????????????
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setStateMessage(StateMessage.CLOSE_GIFT_WINDOW);
                                    EventBus.getDefault().post(messageEvent);
                                } else if (TextUtils.equals(messageBean.type, "1")) {//????????????
                                    loadAniData(messageBean.userInfo, messageBean.show_img);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "11")) {//????????????CP????????????????????????XXX???XX????????????CP???
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());

                            } else if (TextUtils.equals(messageBean.getMessageType(), "8")) {//CP???????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {//????????????
                                    textLayout.setVisibility(View.GONE);
                                }
                                //??????????????????
                                if (!TextUtils.isEmpty(messageBean.cp_tx)) {
                                    playCpTongFangTX(messageBean.cp_tx, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {//???VIP??????
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }

                            } else if (TextUtils.equals(messageBean.getMessageType(), "12")) {//????????????
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                //??????????????????
                                if (!TextUtils.isEmpty(messageBean.cp_xssm)) {
                                    playCpTX(messageBean.cp_xssm, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "13")) {//?????????
                                if (is_show_egg){
                                    if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }

                            } else if (TextUtils.equals(messageBean.getMessageType(), "6666")) {
                                if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                }
                            }
                        }
                    });
                }

                @Override
                public void onMemberJoined(RtmChannelMember rtmChannelMember) {
                    Log.i("chuyibo", "onMemberJoined");
                    LogUtil.d(own + "897432975 - onMemberJoined");
                }

                @Override
                public void onMemberLeft(RtmChannelMember rtmChannelMember) {
                    Log.i("chuyibo", "onMemberLeft");
                    LogUtil.d(own + "897432975 - onMemberLeft");
                }
            });
        } catch (RuntimeException e) {
            Log.e("chuyibo", "Fails to create channel. Maybe the channel ID is invalid," +
                    " or already in use. See the API Reference for more information.");
        }

        mRtmChannel2.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.d("chuyibo", "Successfully joins the channel!");
                LogUtil.d(own + "897432975 - join success");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.d("chuyibo", "join channel failure! errorCode = " + errorInfo.getErrorCode());
                LogUtils.debugInfo(own + "897432975 - join failure");
            }
        });
    }

    /**
     * ??????cp????????????
     *
     * @param cptx
     */
    private void playCpTongFangTX(String cptx, String nickName, String headUrl, String toNickName, String toHeadUrl) {
        if (mLayoutCpAllIn.getVisibility() == View.VISIBLE) mLayoutCpAllIn.setVisibility(View.GONE);
        mTvCpAllIn.setText("??????" + nickName + "?????????" + toNickName + "????????????");
        loadImage(mImgCpLeftAllIn, headUrl, R.mipmap.no_tou);
        loadImage(mImgCpRightAllIn, toHeadUrl, R.mipmap.no_tou);

        Glide.with(mContext)
                .asBitmap()
                .load(cptx)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
//                        mLayoutVipEnter.setBackground(drawable);
                        mImgCpALlIn.setImageDrawable(drawable);
                        mLayoutCpAllIn.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(AdminHomeActivity.this, R.anim.slide_right_in);
                        animation.setDuration(1000);
                        animation.setInterpolator(new DecelerateInterpolator());
                        mLayoutCpAllIn.startAnimation(animation);

                        mLayoutCpAllIn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animation animation = AnimationUtils.loadAnimation(AdminHomeActivity.this, R.anim.slide_left_out);
                                mLayoutCpAllIn.startAnimation(animation);
                                animation.setDuration(2000);
                                animation.setInterpolator(new AccelerateInterpolator());
                                mLayoutCpAllIn.setVisibility(View.GONE);
                            }
                        }, 2000);

                    }

                });
//        try {
//            SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
//            showServerSVG(parser, cptx, svgImage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * ??????cp??????????????????
     *
     * @param cptx
     */
    private void playCpTX(String cptx, String nickName, String headUrl, String toNickName, String toHeadUrl) {
        if (mLayoutCpTongFang.getVisibility() == View.VISIBLE)
            mLayoutCpTongFang.setVisibility(View.GONE);
        mTvCpLeft.setText(nickName);
        mTvCpRight.setText(toNickName);
        loadImage(mImgCpLeft, headUrl, R.mipmap.no_tou);
        loadImage(mImgCpRight, toHeadUrl, R.mipmap.no_tou);

        Glide.with(mContext)
                .asBitmap()
                .load(cptx)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
//                        mLayoutVipEnter.setBackground(drawable);
                        mImgCpTongFang.setImageDrawable(drawable);
                        mLayoutCpTongFang.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(AdminHomeActivity.this, R.anim.alpha_in);
                        mLayoutCpTongFang.startAnimation(animation);
                        mLayoutCpTongFang.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animation animation = AnimationUtils.loadAnimation(AdminHomeActivity.this, R.anim.alpha_out);
                                mLayoutCpTongFang.startAnimation(animation);
                                mLayoutCpTongFang.setVisibility(View.GONE);
                            }
                        }, 2000);

                    }

                });

//        try {
//            SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
//            showServerSVG(parser, cptx, svgImage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * ??????VIP????????????
     *
     * @param txStr
     */
    private void playVIPTX(String txStr, String userName) {
        if (mLayoutVipEnter.getVisibility() == View.VISIBLE)
            mLayoutVipEnter.setVisibility(View.GONE);
        mTvVipEnter.setText(userName + "????????????");
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTvVipEnter.measure(spec, spec);
        int measuredWidthTicketNum = mTvVipEnter.getMeasuredWidth();
        LogUtils.debugInfo("TextView??????=======", measuredWidthTicketNum + "00000");
        Glide.with(mContext)
                .asBitmap()
                .load(txStr)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
//                        mLayoutVipEnter.setBackground(drawable);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImgVipEnterBg.getLayoutParams();
                        params.width = measuredWidthTicketNum;
                        mImgVipEnterBg.setLayoutParams(params);

                        mImgVipEnterBg.setImageDrawable(drawable);
                        mLayoutVipEnter.setVisibility(View.VISIBLE);
                        mLayoutVipEnter.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    mLayoutVipEnter.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 2000);
                    }

                });
    }

    private void sendEnterRoom() {
        LoginData loginData = UserManager.getUser();
        sendChannelMessage(BaseUtils.getJson("2", "???????????????", loginData.getNickname(), loginData.getUserId() + "", vipBean.getData()));
//        sendChannelMessage(BaseUtils.getJson("2", "???????????????",
//                UserManager.getUser().getNickname(),
//                String.valueOf(UserManager.getUser().getUserId()),
//                vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color()));
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    private void setSvgImgClickble() {
        if (svgImage != null)
            svgImage.setClickable(true);
        if (svgImage != null)
            svgImage.setCallback(new SVGACallback() {
                @Override
                public void onPause() {

                }

                @Override
                public void onFinished() {
                    if (svgImage != null)
                        svgImage.setClickable(false);
                }

                @Override
                public void onRepeat() {

                }

                @Override
                public void onStep(int i, double v) {

                }
            });
    }

    private void enterRoomRefrash() {
        RxUtils.loading(commonModel.enter_room(uid, room_pass,
                        String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<EnterRoom>(mErrorHandler) {
                    @Override
                    public void onNext(EnterRoom menterRoom) {
                        textId.setText("ID:" + menterRoom.getRoom_info().get(0).getNumid());
                        enterRoom = menterRoom;
                        // ???????????????
                        meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
                        // ???????????????
                        textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        LogUtils.debugInfo("====onDestroy??????????????????");
        LogUtil.d(own + "??????????????????");
        //selfPosition = -1;
        isStart = false;
        layoutRoom();
        try {
            if (mRtcEngine != null) {
                mRtcEngine.stopAudioMixing();
                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);//2?????????
            }
            //?????????????????????
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (mRtmChannel != null) {
                mRtcEngine.stopAudioMixing();
                mRtcEngine.leaveChannel();
                mRtcEngine = null;
            }
            if (mRtmChannel != null) {
                mRtmChannel.leave(new ResultCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("AA", "??????");
                    }

                    @Override
                    public void onFailure(ErrorInfo errorInfo) {

                    }
                });
                mRtmChannel.release();
                mRtmChannel = null;
            }
            if (mRtmClient != null) {
                mRtmClient.logout(null);
                mRtmClient.release();
            }
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            //???????????????????????????
            EventBus.getDefault().post(new FirstEvent(enterRoom.getRoom_info().get(0).getRoom_cover(), Constant.XUANFUYINCANG));
            stopkeepLiveService();//??????service
        } catch (Exception e) {
            Log.d("AA", "???????????????");
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img1, R.id.img6, R.id.img7, R.id.img2, R.id.img8,
            R.id.img5, R.id.img3, R.id.img4,
            R.id.imgBack, R.id.imgRight,
            R.id.imgShangmai, R.id.imgTing, R.id.imgBimai, R.id.imgPaimai,
            R.id.imgMusic, R.id.imgAdd, R.id.imgGift, R.id.viewEnmojiTop,
            R.id.viewTop, R.id.imgBiaoqing, R.id.imgMessage, R.id.imgRoom, R.id.iv_want_speak_list,
            R.id.imgCollection, R.id.imgPaihang, R.id.textRight, R.id.baoxiang, R.id.iv_lottery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_want_speak_list:
                // ???????????????????????????????????????
                popWantSpeakWindow();
                break;
            case R.id.imgPaimai:
                // ??????
                RxUtils.loading(commonModel.addWaid(uid, String.valueOf(UserManager.getUser().getUserId())))
                        .subscribe(new ErrorHandleSubscriber<WaitList>(mErrorHandler) {
                            @Override
                            public void onNext(WaitList giftListBean) {
                                PaimaiWindow paimaiWindow = new PaimaiWindow(AdminHomeActivity.this, uid, commonModel);
                                paimaiWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                            }
                        });
                break;
            case R.id.imgGift:
                // ????????????????????????
                HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, Object info) {
                        GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                        GiftListBeanNew giftListBean = new GiftListBeanNew();
                        giftListBean.setData(bean);

                        if (mMicrophone != null) {
                            // ??????????????????null
                            Microphone.DataBean.MicrophoneBean microphoneBean =
                                    new Microphone.DataBean.MicrophoneBean();
                            microphoneBean.setUser_id(String.valueOf(enterRoom.getRoom_info().get(0).getUid()));
                            microphoneBean.setHeadimgurl(enterRoom.getRoom_info().get(0).getHeadimgurl());
                            microphoneBean.setSex(enterRoom.getRoom_info().get(0).getSex());
                            microphoneBean.setNickname(enterRoom.getRoom_info().get(0).getNickname());
                            GiftWindow giftWindow = new GiftWindow(AdminHomeActivity.this,
                                    mMicrophone, commonModel, giftListBean, microphoneBean, imgPopup);
                            giftWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        }
                    }
                });

//                RxUtils.loading(commonModel.gift_list(String.valueOf(UserManager.getUser().getUserId())))
//                        .subscribe(new ErrorHandleSubscriber<GiftListBean>(mErrorHandler) {
//                            @Override
//                            public void onNext(GiftListBean giftListBean) {
//                                if (mMicrophone != null) {
//                                    Microphone.DataBean.MicrophoneBean microphoneBean =
//                                            new Microphone.DataBean.MicrophoneBean();
//                                    microphoneBean.setUser_id(String.valueOf(enterRoom.getRoom_info().get(0).getUid()));
//                                    microphoneBean.setHeadimgurl(enterRoom.getRoom_info().get(0).getHeadimgurl());
//                                    microphoneBean.setSex(enterRoom.getRoom_info().get(0).getSex());
//                                    microphoneBean.setNickname(enterRoom.getRoom_info().get(0).getNickname());
//                                    GiftWindow giftWindow = new GiftWindow(AdminHomeActivity.this,
//                                            mMicrophone, commonModel, giftListBean, microphoneBean, imgPopup);
//                                    giftWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
//                                }
//                            }
//                        });

                break;
            case R.id.imgBack:
                if (flag == 1) {
//                    EventBus.getDefault().post(new FirstEvent(enterRoom.getRoom_info().get(0).getRoom_cover()
//                            , Constant.FANHUIZHUYE));
                    EventBus.getDefault().post(new FirstEvent("????????????", Constant.FANHUIZHUYE, enterRoom));
                    moveTaskToBack(true);
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                    isTop = false;
                }
                break;
            case R.id.imgBiaoqing://??????
                loadEmoji();
                break;
            case R.id.imgRoom://????????????
                setRoomHeader();
                break;
            case R.id.viewTop://????????????
                llMusic.setVisibility(View.GONE);
                break;
            case R.id.viewEnmojiTop://????????????
                rlEmoji.setVisibility(View.GONE);
                break;
            case R.id.imgRight:
                RoomGaoWindow roomGaoWindow = new RoomGaoWindow(this);
                roomGaoWindow.showAsDropDown(textId);
                if (TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getRoom_intro())) {
                    roomGaoWindow.getTextDec().setText("????????????");
                } else {
                    roomGaoWindow.getTextDec().setText(enterRoom.getRoom_info().get(0).getRoom_intro());
                }
                break;
            case R.id.img1:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(0);
                    } else {
                        upDownVedio(0);
                    }
                }
                break;
            case R.id.img6:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(5);
                    } else {
                        upDownVedio(5);
                    }
                }
                break;
            case R.id.img7:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(6);
                    } else {
                        upDownVedio(6);
                    }
                }
                break;
            case R.id.img8:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(7);
                    } else {
                        upDownVedio(7);
                    }
                }
                break;
            case R.id.img2:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(1);
                    } else {
                        upDownVedio(1);
                    }
                }
                break;
            case R.id.img5:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(4);
                    } else {
                        upDownVedio(4);
                    }
                }
                break;
            case R.id.img3:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(2);
                    } else {
                        upDownVedio(2);
                    }
                }
                break;
            case R.id.img4:
                if (mMicrophone.size() > 0) {
                    if (user_type == 1 || user_type == 2) {
                        setEditMaiwei(3);
                    } else {
                        upDownVedio(3);
                    }
                }
                break;
            case R.id.imgShangmai://??????
                if (imgShangmai.isSelected()) {
                    goDownVedio(String.valueOf(UserManager.getUser().getUserId()));
                } else {//??????
                    for (int i = 0; i < mMicrophone.size(); i++) {
                        if (mMicrophone.get(i).getStatus() == 1) {
                            requestUpMicrophone();
                            return;
                        }
                    }
                    ToastUtil.showToast(AdminHomeActivity.this, "???????????????");
                }
                break;
            case R.id.imgTing://????????????
                //TODO enableLocalAudio	????????????????????????  muteAllRemoteAudioStreams--??????/???????????????????????????
                if (imgTing.isSelected()) {
                    mRtcEngine.muteAllRemoteAudioStreams(false);
                    imgTing.setSelected(false);
                } else {
                    mRtcEngine.muteAllRemoteAudioStreams(true);
                    imgTing.setSelected(true);
                }
                break;
            case R.id.imgBimai://??????
                if (!isEditBimai) {
                    if (imgBimai.isSelected()) {
                        mRtcEngine.enableLocalAudio(true);
                        imgBimai.setSelected(false);
                    } else {
                        mRtcEngine.enableLocalAudio(false);
                        imgBimai.setSelected(true);
                    }
                } else {
                    toast("???????????????????????????");
                }
                break;
            case R.id.imgMusic:
                loadMusic();
                break;
            case R.id.imgAdd:
                if (user_type == 1) {
                    RoomSetWindow1 roomSetWindow1 = new RoomSetWindow1(AdminHomeActivity.this);
                    roomSetWindow1.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                    roomSetWindow1.setOnDismissListener(() -> {
                        WindowManager.LayoutParams params = getWindow().getAttributes();
                        params.alpha = 1f;
                        getWindow().setAttributes(params);
                    });
                    roomSetWindow1.getMyGrid().setOnItemClickListener((parent, view1, position, id) -> {
                        roomSetWindow1.dismiss();
                        switch (position) {
                            case 0:
                                if (enterRoom == null) {
                                    return;
                                }
                                Intent intent2 = new Intent(AdminHomeActivity.this, RoomSettingActivity.class);
                                intent2.putExtra("isHome", uid);
                                intent2.putExtra("enterRoom", enterRoom);
                                ArmsUtils.startActivity(intent2);
                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                break;
                            case 1:
                                Intent intent = new Intent(AdminHomeActivity.this, SetAdminActivity.class);
                                intent.putExtra("uid", uid);
                                ArmsUtils.startActivity(intent);
                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                break;
                            case 2:
                                String json = BaseUtils.getJson("6", "",
                                        UserManager.getUser().getNickname(),
                                        String.valueOf(UserManager.getUser().getUserId()));
                                sendChannelMessage(json);
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                break;
                            case 3:
                                // ?????????????????????
                                clearAllMlZ();
                                break;
                        }
                    });
                } else if (user_type == 2) {
                    RoomSetWindow2 roomSetWindow2 = new RoomSetWindow2(AdminHomeActivity.this);
                    roomSetWindow2.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                    roomSetWindow2.setOnDismissListener(() -> {
                        WindowManager.LayoutParams params = getWindow().getAttributes();
                        params.alpha = 1f;
                        getWindow().setAttributes(params);
                    });
                    roomSetWindow2.getMyGrid().setOnItemClickListener((parent, view1, position, id) -> {
                        roomSetWindow2.dismiss();
                        switch (position) {
                            case 0:
                                if (enterRoom == null) {
                                    return;
                                }
                                Intent intent2 = new Intent(AdminHomeActivity.this, RoomSettingActivity.class);
                                intent2.putExtra("isHome", uid);
                                intent2.putExtra("enterRoom", enterRoom);
                                ArmsUtils.startActivity(intent2);
                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                break;
                            case 1:
                                String json = BaseUtils.getJson("6", "",
                                        UserManager.getUser().getNickname(),
                                        String.valueOf(UserManager.getUser().getUserId()));
                                sendChannelMessage(json);
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                                break;
                        }
                    });
                }
                break;
            case R.id.imgMessage://???????????????
                sendUserData();
                break;
            case R.id.imgPaihang://??????
                Intent intent = new Intent(AdminHomeActivity.this, RoomRankActivityNew.class);
                intent.putExtra("id", enterRoom.getRoom_info().get(0).getUid());
                intent.putExtra("image", enterRoom.getRoom_info().get(0).back_img);
                intent.putExtra("is_show_num", user_type == 1 || user_type == 2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.imgCollection://??????
                if (imgCollection.isSelected()) {
                    RxUtils.loading(commonModel.remove_mykeep(uid,
                                    String.valueOf(UserManager.getUser().getUserId())), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    toast("????????????");
                                    imgCollection.setSelected(false);
                                }
                            });
                } else {
                    RxUtils.loading(commonModel.room_mykeep(uid,
                                    String.valueOf(UserManager.getUser().getUserId())), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    toast("????????????");
                                    imgCollection.setSelected(true);
                                }
                            });
                }
                break;
            case R.id.textRight://??????
                RoomTopWindow roomTopWindow = new RoomTopWindow(this);
                roomTopWindow.showAsDropDown(textRight);
                roomTopWindow.getLlClose().setOnClickListener(v -> {
                    roomTopWindow.dismiss();
                    mRtcEngine.stopAudioMixing();
                    isStart = false;
                    finish();
                });
                roomTopWindow.getLlJubao().setOnClickListener(v -> {//??????
                    roomTopWindow.dismiss();
                    ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                    reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                });
                roomTopWindow.getLlTeXiao().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        boolean closeGif = (boolean) SharedPreferencesUtils.getParam(AdminHomeActivity.this, "SHOWGIF", false);
//                        if (closeGif) {
//                            SharedPreferencesUtils.setParam(AdminHomeActivity.this, "SHOWGIF", false);
//                        } else {
//                            SharedPreferencesUtils.setParam(AdminHomeActivity.this, "SHOWGIF", true);
//                        }


                        ExtConfig.isSendAllGiftShow = !ExtConfig.isSendAllGiftShow;
                        roomTopWindow.dismiss();
                    }
                });
                roomTopWindow.getLlShare().setOnClickListener(v -> {
                    roomTopWindow.dismiss();
                    UMWeb web = new UMWeb("http://app.suiliao888.cn/bc8m");
                    web.setTitle("??????");//??????
                    web.setDescription("????????????"+getResources().getString(R.string.app_name)+"????????????");//??????

                    ShareBoardConfig config = new ShareBoardConfig();//??????ShareBoardConfig
                    config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);//????????????
//                    config.setMenuItemBackgroundShape(getResources().getColor(R.drawable.shape_home_round));
                    config.setCancelButtonVisibility(true);
                    config.setTitleText("?????????");
                    config.setTitleTextColor(getResources().getColor(R.color.font_333333));
                    config.setMenuItemTextColor(getResources().getColor(R.color.font_333333));
                    config.setIndicatorVisibility(false);
                    config.setCancelButtonVisibility(false);
                    config.setShareboardBackgroundColor(getResources().getColor(R.color.white));

                    new ShareAction(AdminHomeActivity.this)
                            .withMedia(web)
                            .setDisplayList(SHARE_MEDIA.WEIXIN,
                                    SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                                    SHARE_MEDIA.SINA)
                            .setCallback(shareListener)
                            .open(config);
                });
                break;
            case R.id.baoxiang:
//                GemStoneDialogNew gemStoneDialog = new GemStoneDialogNew(AdminHomeActivity.this, commonModel, mErrorHandler,uid);
//                gemStoneDialog.show();
                TreasureBoxDialog boxDialog = new TreasureBoxDialog(AdminHomeActivity.this, commonModel, mErrorHandler,uid);
                boxDialog.show();
//                MessageDialog.show(AdminHomeActivity.this,"","1?????????????????????????????????????????????10??????/ ???;\n2???????????????????????????????????????10??????100 ???;\n3????????????????????????????????????????????????????????? ??????????????????????????????;\n4????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????? ????????????????????????;\n5????????????????????????????????????????????????????????? ??????????????????;\n6????????????????????????????????????????????????????????? ???;\n7???????????????????????????????????????Mini???????????? ??????;",null);
//                CustomDialog.show(AdminHomeActivity.this, R.layout.box_rule_popu, new CustomDialog.OnBindView() {
//                    @Override
//                    public void onBind(CustomDialog dialog, View v) {
//
//                    }
//                });

//                if (a == 0) {
//                    BoxTitleWindow boxTitleWindow = new BoxTitleWindow(AdminHomeActivity.this);
//                    boxTitleWindow.showAtLocation(room, Gravity.CENTER, 0, 0);
//                    a = 1;
//                } else if (a == 1) {
//                    BoxFirstOpenWindow boxFirstOpenWindow = new BoxFirstOpenWindow(AdminHomeActivity.this);
//                    boxFirstOpenWindow.showAtLocation(room, Gravity.CENTER, 0, 0);
//                    boxFirstOpenWindow.getOkBtn().setOnClickListener(v -> {
//                        boxFirstOpenWindow.dismiss();
//                    });
//                    a = 2;
//                } else if (a == 2) {
//                    CustomDialog.show(AdminHomeActivity.this, R.layout.box_open_record_window, new CustomDialog.OnBindView() {
//                        @Override
//                        public void onBind(CustomDialog dialog, View v) {
//
//                        }
//                    });
//                    a = 0;
//                }

                break;
            case R.id.iv_lottery:
                if (ExtConfig.isShowLottery){
                    ArmsUtils.startActivity(LotteryActivity.class);
                }

                break;
            default:
        }
    }

    /**
     * ?????????????????????
     */
    private void clearAllMlZ() {
        RxUtils.loading(commonModel.clearAllMlZ(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", uid), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        if (microphone.getCode() == 1) {
                            // ??????????????????????????????????????????????????????
                            sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                            enterRoomRefrash();
                            loadVedioList();
                            //...
                        }
                    }
                });
    }

    private void requestUpMicrophone() {
        RxUtils.loading(commonModel.requestUpMicrophone(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", uid), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        if (microphone.getCode() == 1) {
                            sendChannelMessage(BaseUtils.getJson("15", "", "", ""));
                        }
                        ToastUtil.showToast(AdminHomeActivity.this, microphone.getMessage());
                    }
                });
    }

    private void popWantSpeakWindow() {
        // ???????????????????????????????????????
        if (requestUpMicrophoneData != null && requestUpMicrophoneData.size() > 0) {
            tvNewUpMicrophone.setVisibility(View.GONE);

            UpMicrophonePopWindow popWindow = new UpMicrophonePopWindow(this, requestUpMicrophoneData);
            popWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            popWindow.setAnimationStyle(R.style.popwindow_center_anim_style);
            popWindow.setListener(new UpMicrophonePopWindow.AgreeOrRefuseUpMicrophoneListener() {
                @Override
                public void agree(int position) {
                    agreeOrRefuseUpMicrophone(position, 1);
                }

                @Override
                public void refuse(int position) {
                    agreeOrRefuseUpMicrophone(position, 2);
                }
            });
        } else {
            ToastUtil.showToast(this, "????????????????????????");
        }
    }

    private void agreeOrRefuseUpMicrophone(int position, int type) {
        RxUtils.loading(commonModel.agreeOrRefuseUpMicrophone(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", type, requestUpMicrophoneData.get(position).getId()), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        toast(baseBean.getMessage());
                        updateRequestUpMicrophoneList();
                        if (type == 1) {
                            int canUpMicrophonePosition = -1;
                            for (int i = 0; i < mMicrophone.size(); i++) {
                                if (mMicrophone.get(i).getStatus() == 1) {//??????????????????
                                    canUpMicrophonePosition = i;
                                    break;
                                }
                            }

                            if (canUpMicrophonePosition != -1) {
                                upEditVedio(canUpMicrophonePosition, requestUpMicrophoneData.get(position).getUserid());
                            }
                        }
                    }
                });
    }

    /**
     * ???????????????
     */
    private void sendUserData() {
        RxUtils.loading(commonModel.not_speak_status(uid, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        KeybordWindow payWindow = new KeybordWindow(AdminHomeActivity.this);
                        payWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        payWindow.setOnDismissListener(() -> {
                            WindowManager.LayoutParams params = getWindow().getAttributes();
                            params.alpha = 1f;
                            getWindow().setAttributes(params);
                        });
                        payWindow.getBtn_ok().setOnClickListener(v -> {
                            String str = payWindow.getEditMessage().getText().toString();
                            if (!TextUtils.isEmpty(str)) {
                                String json;
                                if (vipBean != null && vipBean.getData() != null) {
                                    VipBean.DataBean dataBean = vipBean.getData();
                                    json = BaseUtils.getJson("1", str,
                                            UserManager.getUser().getNickname(),
                                            String.valueOf(UserManager.getUser().getUserId()),
                                            vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color(), dataBean);
                                } else {
                                    json = BaseUtils.getJson("1", str,
                                            UserManager.getUser().getNickname(),
                                            String.valueOf(UserManager.getUser().getUserId()),
                                            "", "", "");
                                }
                                sendChannelMessage(json);
                                roomMessageAdapter.getData().add(BaseUtils.getMessageBean(json));
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                payWindow.dismiss();
                            } else {
                                showToast("??????????????????");
                            }
                        });
                    }
                });
    }

    /**
     * ??????????????????????????????
     */
    private void setEditMaiwei(int position) {
        switch (mMicrophone.get(position).getStatus()) {
            case 1://????????????
                MaterialDialog dialog = null;
                if (user_type == 2) {
                    dialog = new MaterialDialog.Builder(this)
                            .customView(R.layout.dialog_room_admin1, true)
                            .show();
                } else {
                    dialog = new MaterialDialog.Builder(this)
                            .customView(R.layout.dialog_room_admin4, true)
                            .show();
                }
                TextView textBaoren = (TextView) dialog.findViewById(R.id.textBaoren);
                TextView textSuomai = (TextView) dialog.findViewById(R.id.textSuomai);
                TextView textCancel = (TextView) dialog.findViewById(R.id.textCancel);
                MaterialDialog finalDialog = dialog;
                textCancel.setOnClickListener(v -> {
                    finalDialog.dismiss();
                });
                textSuomai.setOnClickListener(v -> {
                    RxUtils.loading(commonModel.shut_microphone(uid, position), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean microphone) {
                                    finalDialog.dismiss();
                                    sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                                    loadVedioList();
                                }
                            });
                });
                textBaoren.setOnClickListener(v -> {//?????????????????????
                    finalDialog.dismiss();
//                    String.valueOf(UserManager.getUser().getUserId()
                    showDialogLoding();
                    RxUtils.loading(commonModel.getRoomUsers(uid,
                                    ""), this)
                            .subscribe(new ErrorHandleSubscriber<RoomUsersBean>(mErrorHandler) {
                                @Override
                                public void onNext(RoomUsersBean roomUsersBean) {
                                    disDialogLoding();
                                    if (roomUsersBean != null) {

                                        RoomUsersBean.DataBean dataBean = roomUsersBean.getData();

                                        if (dataBean != null) {

                                            List<RoomMultipleItem> roomMultipleItemList = new ArrayList<>();

                                            List<MicUserBean> micUserBeanList = dataBean.getMic_user();//??????

                                            List<MicUserBean> roomUserBeanList = dataBean.getRoom_user();//??????

                                            List<MicUserBean> seaUserBeanList = dataBean.getSea_user();//??????


                                            if (micUserBeanList == null) {
                                                micUserBeanList = new ArrayList<>();
                                            }
                                            if (roomUserBeanList == null) {
                                                roomUserBeanList = new ArrayList<>();
                                            }

                                            int micUpUsersLine = micUserBeanList.size();//????????????????????????

                                            int micUpUsers = 8;//??????????????????

                                            int micDownUsers = roomUserBeanList.size();

                                            //??????title
                                            RoomMultipleItem roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.TITLE_MIC_UP, new MicUserBean());

                                            roomMultipleItemList.add(roomMultipleItem);

                                            //??????
                                            MicUserBean micUserBean;

                                            for (int i = 0; i < micUserBeanList.size(); i++) {

                                                micUserBean = micUserBeanList.get(i);

                                                roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.MIC_UP, micUserBean);

                                                roomMultipleItemList.add(roomMultipleItem);


                                            }

                                            //?????????title
                                            roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.TITLE_MIC_DOWN, new MicUserBean());

                                            roomMultipleItemList.add(roomMultipleItem);


                                            //??????
                                            for (int i = 0; i < roomUserBeanList.size(); i++) {

                                                micUserBean = roomUserBeanList.get(i);

                                                roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.MIC_DOWN, micUserBean);

                                                roomMultipleItemList.add(roomMultipleItem);

                                            }

                                            SelectPeopleUpVideoDialog selectPeopleUpVideoDialog = new SelectPeopleUpVideoDialog(AdminHomeActivity.this, position, new SelectPeopleUpVideoDialog.OnOperationMicListener() {
                                                @Override
                                                public void toUpMic(int position, String userId) {//?????????????????????
                                                    upEditVedio(position, userId);
                                                }

                                                @Override
                                                public void toDownMic(String userId) {//?????????????????????
                                                    editXiamai(userId);
                                                }
                                            });

                                            selectPeopleUpVideoDialog.setInfo(commonModel, uid, mErrorHandler);

                                            selectPeopleUpVideoDialog.show();

                                            selectPeopleUpVideoDialog.setUserCount(roomMultipleItemList, micUpUsers, micUpUsersLine, micDownUsers);

                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                    disDialogLoding();
                                }
                            });
//                    new MaterialDialog.Builder(this)
//                            .title("??????")
//                            .content("????????????id")
//                            .inputType(InputType.TYPE_CLASS_TEXT)
//                            .input("????????????id", null, new MaterialDialog.InputCallback() {
//                                @Override
//                                public void onInput(MaterialDialog dialog, CharSequence input) {
//                                    String trim = input.toString().trim();
//                                    if (!TextUtils.isEmpty(trim)) {
//                                        upEditVedio(position, trim);
//                                    } else {
//                                        showToast("???????????????id");
//                                    }
//                                }
//                            })
//                            .positiveText("??????")
//                            .show();
                });
                textCancel.setOnClickListener(v -> {
                    RxUtils.loading(commonModel.up_microphone(uid,
                                    String.valueOf(UserManager.getUser().getUserId()), position + ""), this)
                            .subscribe(new ErrorHandleSubscriber<UpVideoResult>(mErrorHandler) {
                                @Override
                                public void onNext(UpVideoResult microphone) {
                                    finalDialog.dismiss();
                                    imgShangmai.setSelected(true);
                                    imgBimai.setVisibility(View.VISIBLE);
                                    imgMusic.setVisibility(View.VISIBLE);
                                    imgBiaoqing.setVisibility(View.VISIBLE);
                                    sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
                                    sendCPAtVideo(microphone);
                                    loadVedioList();
                                }
                            });
                });
                break;
            case 2://????????????
                if ((UserManager.getUser().getUserId() + "").
                        equals(mMicrophone.get(position).getUser_id())) {//????????????
                    setMyDataDialog(mMicrophone.get(position).getUser_id());
                    return;
                }
                setVedioDialog(position);
                break;
            case 3:
                new MaterialDialog.Builder(this)
                        .title("??????????????????")
                        .content("")
                        .onPositive((dialog1, which) -> {
                            RxUtils.loading(commonModel.open_microphone(uid, position), this)
                                    .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                        @Override
                                        public void onNext(BaseBean microphone) {
                                            dialog1.dismiss();
                                            sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                                            loadVedioList();
                                        }
                                    });
                        })
                        .positiveText("??????")
                        .negativeText("??????")
                        .show();
                break;
            default:
        }
    }

    /**
     * cp????????????
     */
    private void sendCPAtVideo(UpVideoResult upVideoResult) {
        if (upVideoResult.getData() != null && upVideoResult.getData().getUser() != null && upVideoResult.getData().getCp() != null && upVideoResult.getData().getCp().size() > 0) {//???CP
            List<UpVideoResult.DataBean.CpBean> cpList = upVideoResult.getData().getCp();
            UpVideoResult.DataBean.UserBean userBean = upVideoResult.getData().getUser();
            for (UpVideoResult.DataBean.CpBean itemBean : cpList) {
                MessageBean newMessage = new MessageBean();
                newMessage.setNickName(userBean.getNickname());
                newMessage.setUser_id(userBean.getId() + "");
                newMessage.nick_color = userBean.getNick_color();
                newMessage.headimgurl = userBean.getHeadimgurl();
                newMessage.toNickName = itemBean.getNickname();
                newMessage.toNick_color = itemBean.getNick_color();
                newMessage.toheadimgurl = itemBean.getHeadimgurl();
                newMessage.toUser_id = itemBean.getId();
                newMessage.cp_xssm = itemBean.getCp_xssm();//CP??????
                newMessage.setMessageType("12");

                //??????????????????
                roomMessageAdapter.getData().add(newMessage);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());

                if (!TextUtils.isEmpty(itemBean.getCp_xssm())) {//??????CP??????
                    //??????????????????
                    playCpTX(itemBean.getCp_xssm(), userBean.getNickname(), userBean.getHeadimgurl(), itemBean.getNickname(), itemBean.getHeadimgurl());
                }

                String strs = JSON.toJSONString(newMessage);
                //??????????????????
                sendChannelMessage(strs);

            }


        }

    }

    /**
     * ??????????????????
     */
    private void upDownVedio(int position) {
        switch (mMicrophone.get(position).getStatus()) {
            case 1://????????????
                if (ExtConfig.isMicrophoneRequest) {
                    //??????????????????
                    requestUpMicrophone();
                }else {
                    RxUtils.loading(commonModel.up_microphone(uid,
                                    String.valueOf(UserManager.getUser().getUserId()), position + ""), this)
                            .subscribe(new ErrorHandleSubscriber<UpVideoResult>(mErrorHandler) {
                                @Override
                                public void onNext(UpVideoResult microphone) {
                                    imgShangmai.setSelected(true);
                                    imgBimai.setVisibility(View.VISIBLE);
                                    imgMusic.setVisibility(View.VISIBLE);
                                    imgBiaoqing.setVisibility(View.VISIBLE);
                                    sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
                                    sendCPAtVideo(microphone);
                                    loadVedioList();
                                }
                            });
                }

                break;
            case 2:
                if (mMicrophone.get(position).getUser_id().equals(UserManager.getUser().getUserId() + "")) {
                    setMyDataDialog(mMicrophone.get(position).getUser_id());
                } else {
                    setOtherDataDialog(mMicrophone.get(position).getUser_id());
                }
                break;
            case 3:
                showToast("?????????????????????");
                break;
            default:
        }
    }

    /**
     * ?????????????????????
     */
    private void goDownVedio(String user_id) {
        RxUtils.loading(commonModel.go_microphone(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        imgShangmai.setSelected(false);
                        imgBimai.setVisibility(View.GONE);
                        imgMusic.setVisibility(View.GONE);
                        imgBiaoqing.setVisibility(View.GONE);
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
                        mRtcEngine.stopAudioMixing();
                        loadVedioList();
                    }
                });
    }

    /**
     * ????????????
     */
    private void forcedDownVedio() {
        imgShangmai.setSelected(false);
        imgBimai.setVisibility(View.GONE);
        imgMusic.setVisibility(View.GONE);
        imgBiaoqing.setVisibility(View.GONE);
        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
//        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
//        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
//        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
//        mRtcEngine.stopAudioMixing();
    }

    /**
     * ???????????????????????????
     *
     * @param
     */
    private void goDownVedioUnBind(String user_id) {
        RxUtils.loading(commonModel.go_microphone(uid, user_id))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                    }
                });
    }

    /**
     * ???????????????????????????
     */
    private void editShangmai(String user_id) {
        for (int i = 0; i < mMicrophone.size(); i++) {
            if (mMicrophone.get(i).getStatus() == 1) {
                RxUtils.loading(commonModel.up_microphone(uid,
                                user_id, i + ""), this)
                        .subscribe(new ErrorHandleSubscriber<UpVideoResult>(mErrorHandler) {
                            @Override
                            public void onNext(UpVideoResult microphone) {
                                imgShangmai.setSelected(true);
                                imgBimai.setVisibility(View.VISIBLE);
                                imgMusic.setVisibility(View.VISIBLE);
                                imgBiaoqing.setVisibility(View.VISIBLE);
                                sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
                                sendCPAtVideo(microphone);
                                loadVedioList();
                            }
                        });
                return;
            }
        }
    }

    /**
     * ???????????????????????????
     */
    private void editXiamai(String user_id) {
        showDialogLoding();
        RxUtils.loading(commonModel.go_microphone(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        disDialogLoding();
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggunaliyuanxiamai);
                        mRtcEngine.stopAudioMixing();
                        loadVedioList();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                    }
                });
    }

    /**
     * ??????????????????????????????,????????????????????????????????????
     */
    private void setMyDataDialog(String user_id) {
        if (TextUtils.isEmpty(user_id)) {
            user_id = String.valueOf(UserManager.getUser().getUserId());
        }
        RxUtils.loading(commonModel.get_other_user(uid,
                        user_id, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin7);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin7, true)
//                                .show();
                        RoundedImageView img1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        loadImage(img1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            intent1.putExtra("sign", 0);
                            intent1.putExtra("id", "");
                            intent1.putExtra("isRoom", true);
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });

                        //??????????????????????????????????????????????????????
                        dialog2.findViewById(R.id.textDialogClearCharm).setOnClickListener(
                                view -> {
                                    roomDialog.dismiss();
                                    RxUtils.loading(commonModel.cleargiftpricecount(otherUser.getData().get(0).getId() + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
                                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                                @Override
                                                public void onNext(BaseBean baseBean) {
                                                    enterRoomRefrash();
                                                    loadVedioList();
                                                }
                                            });
                                }
                        );

                        dialog2.findViewById(R.id.sendMyself).setVisibility(ExtConfig.isSendGiftToMyself?View.VISIBLE:View.GONE);
                        dialog2.findViewById(R.id.sendMyself).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        LogUtils.debugInfo("user_type="+user_type);
                        if (user_type == 1 || user_type == 2) {
                            dialog2.findViewById(R.id.textDialogClearCharm).setVisibility(ExtConfig.isCleanMeiLi?View.VISIBLE:View.GONE);

                        }
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void setRoomHeader() {
        if (user_type == 1) {
            RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                    R.layout.dialog_room_admin7);
            View dialog2 = roomDialog.getmMenuView();
            roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);

//            MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                    .customView(R.layout.dialog_room_admin7, true)
//                    .show();
            RoundedImageView img1 = dialog2.findViewById(R.id.img1);
            ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
            TextView textName = (TextView) dialog2.findViewById(R.id.textName);
            TextView textId = (TextView) dialog2.findViewById(R.id.textId);
            loadImage(img1, enterRoom.getRoom_info().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
            img2.setSelected(enterRoom.getRoom_info().get(0).getSex() == 1);
            textName.setText(enterRoom.getRoom_info().get(0).getNickname());
            textId.setText("ID:" + UserManager.getUser().getUserId());
            /*if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getBright_num())) {
                textId.setText("ID:" + enterRoom.getRoom_info().get(0).getBright_num());
                textId.setTextColor(getResources().getColor(R.color.colorAccent));
                Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                textId.setCompoundDrawablePadding(dip2px(4));
            }*/
            dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                roomDialog.dismiss();
                Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                intent1.putExtra("sign", 0);
                intent1.putExtra("id", "");
                intent1.putExtra("isRoom", true);
                ArmsUtils.startActivity(intent1);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            });
            RxUtils.loading(commonModel.get_other_user(uid, uid,
                            String.valueOf(UserManager.getUser().getUserId())), this)
                    .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                        @Override
                        public void onNext(OtherUser otherUser) {
                            TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                            textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                            dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                            dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                            dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                            loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                            loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang
                            );
                            loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);

                            //??????????????????????????????????????????????????????
                            dialog2.findViewById(R.id.textDialogClearCharm).setOnClickListener(
                                    view -> {
                                        roomDialog.dismiss();
                                        RxUtils.loading(commonModel.cleargiftpricecount(otherUser.getData().get(0).getId() + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
                                                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                                    @Override
                                                    public void onNext(BaseBean baseBean) {
                                                        enterRoomRefrash();
                                                        loadVedioList();
                                                    }
                                                });
                                    }
                            );

                            dialog2.findViewById(R.id.sendMyself).setVisibility(ExtConfig.isSendGiftToMyself?View.VISIBLE:View.GONE);
                            dialog2.findViewById(R.id.sendMyself).setOnClickListener(v -> {
                                roomDialog.dismiss();
                                loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                        , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                            });
                            LogUtils.debugInfo("user_type="+user_type);
                            if (user_type == 1 || user_type == 2) {
                                dialog2.findViewById(R.id.textDialogClearCharm).setVisibility(ExtConfig.isCleanMeiLi?View.VISIBLE:View.GONE);

                            }
                        }
                    });
        } else if (user_type == 2) {
            setEditOtherDataDialog2(uid);
        } else {
            setOtherDataDialog(uid);
        }
    }

    /**
     * ?????????????????????????????????????????????
     */
    private void setOtherDataDialog(String userId) {
        RxUtils.loading(commonModel.get_other_user(uid, userId,
                        String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin6);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin6, true)
//                                .show();
                        RoundedImageView popupimg1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        TextView textDialogGuanzhu = (TextView) dialog2.findViewById(R.id.textDialogGuanzhu);
                        loadImage(popupimg1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }

//                        dialog2.findViewById(R.id.tv_clear_meilizhi).setVisibility((user_type == 1 || user_type == 2) ? View.VISIBLE : View.GONE);
//                        dialog2.findViewById(R.id.tv_clear_meilizhi).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                roomDialog.dismiss();
//                                RxUtils.loading(commonModel.cleargiftpricecount(userId + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
//                                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
//                                            @Override
//                                            public void onNext(BaseBean baseBean) {
//                                                sendChannelMessage(BaseUtils.getJson("401", "", "", "", null));
//                                                enterRoomRefrash();
//                                                loadVedioList();
//                                            }
//                                        });
//                            }
//                        });

                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "?????????" : "??????");
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);
                        dialog2.findViewById(R.id.textDialogSongli).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        dialog2.findViewById(R.id.imgJubao).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                            reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        });
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            if (otherUser.getData().get(0).getId() == UserManager.getUser().getUserId()) {
                                intent1.putExtra("sign", 0);
                                intent1.putExtra("id", "");
                                intent1.putExtra("isRoom", true);
                            } else {
                                intent1.putExtra("sign", 1);
                                intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                                intent1.putExtra("isRoom", true);
                            }
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });

                        //??????
                        dialog2.findViewById(R.id.imgGiveOthe).setVisibility(ExtConfig.isTransfer ? View.VISIBLE : View.GONE);
                        dialog2.findViewById(R.id.imgGiveOthe).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, GiveOtherActivity.class);
                            intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                            intent1.putExtra("name", otherUser.getData().get(0).getNickname() + "");
                            intent1.putExtra("img", otherUser.getData().get(0).getHeadimgurl() + "");
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("??????", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("????????????");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("??????????????????");
                            }
                        });
                    }
                });
    }

    /**
     * ????????????????????????????????????
     */
    private void setEditOtherDataDialog2(String userId) {
        RxUtils.loading(commonModel.get_other_user(uid, userId,
                        String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin5);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin5, true)
//                                .show();
                        RoundedImageView popupimg1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        TextView textDialogGuanzhu = (TextView) dialog2.findViewById(R.id.textDialogGuanzhu);
                        loadImage(popupimg1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "?????????" : "??????");
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);
                        dialog2.findViewById(R.id.textDialogXiamai).setVisibility(View.GONE);
                        dialog2.findViewById(R.id.textDialogJinyan).setVisibility(View.GONE);
                        dialog2.findViewById(R.id.textDialogTichu).setVisibility(View.GONE);
                        dialog2.findViewById(R.id.tv_clear_meilizhi).setVisibility(View.VISIBLE);
                        dialog2.findViewById(R.id.tv_clear_meilizhi).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                roomDialog.dismiss();
                                RxUtils.loading(commonModel.cleargiftpricecount(otherUser.getData().get(0).getId() + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
                                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                            @Override
                                            public void onNext(BaseBean baseBean) {
                                                sendChannelMessage(BaseUtils.getJson("401", "", "", "", null));
                                                enterRoomRefrash();
                                                loadVedioList();
                                            }
                                        });
                            }
                        });
                        dialog2.findViewById(R.id.textDialogSongli).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        dialog2.findViewById(R.id.imgJubao).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                            reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        });
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            if (otherUser.getData().get(0).getId() == UserManager.getUser().getUserId()) {
                                intent1.putExtra("sign", 0);
                                intent1.putExtra("id", "");
                                intent1.putExtra("isRoom", true);
                            } else {
                                intent1.putExtra("sign", 1);
                                intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                                intent1.putExtra("isRoom", true);
                            }
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("??????", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("????????????");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("??????????????????");
                            }
                        });
                    }
                });
    }

    /**
     * ??????????????????????????????????????????
     */
    private void setEditOtherDataDialog(String userId) {
        RxUtils.loading(commonModel.get_other_user(uid, userId,
                        String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin5);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin5, true)
//                                .show();
                        RoundedImageView popupimg1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        TextView textDialogGuanzhu = (TextView) dialog2.findViewById(R.id.textDialogGuanzhu);
                        loadImage(popupimg1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "?????????" : "??????");
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);
                        dialog2.findViewById(R.id.textDialogXiamai).setOnClickListener(v -> {
                            editShangmai(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogJinyan).setOnClickListener(v -> {
                            editJinyan(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogTichu).setOnClickListener(v -> {
                            new MaterialDialog.Builder(AdminHomeActivity.this)
                                    .title("????????????Ta??????????????????")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("??????")
                                    .negativeText("??????")
                                    .show();
                        });
                        dialog2.findViewById(R.id.textDialogSongli).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        dialog2.findViewById(R.id.imgJubao).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                            reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        });
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            if (otherUser.getData().get(0).getId() == UserManager.getUser().getUserId()) {
                                intent1.putExtra("sign", 0);
                                intent1.putExtra("id", "");
                                intent1.putExtra("isRoom", true);
                            } else {
                                intent1.putExtra("sign", 1);
                                intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                                intent1.putExtra("isRoom", true);
                            }
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("??????", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("????????????");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("??????????????????");
                            }
                        });

                        for (AdminUser.DataBean.VisitorBean  bean:mAdminUser.getData().getVisitor()){
                            if (bean.getId() == Integer.parseInt(userId)){
                                dialog2.findViewById(R.id.textDialogTichu).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    private void setVedioDialog(String userId) {
        RxUtils.loading(commonModel.get_other_user(uid,
                        userId, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin2);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin2, true)
//                                .show();
                        RoundedImageView popupimg1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textDialogGuanzhu = (TextView) dialog2.findViewById(R.id.textDialogGuanzhu);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        TextView textDialogBimai = (TextView) dialog2.findViewById(R.id.textDialogBimai);
                        loadImage(popupimg1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }
                        textDialogBimai.setText(otherUser.getData().get(0).getIs_sound() == 1 ? "??????" : "??????");
                        //TODO ???????????????
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "?????????" : "??????");
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);

                        dialog2.findViewById(R.id.tv_clear_meilizhi).setVisibility((user_type == 1 || user_type == 2) ? View.VISIBLE : View.GONE);
                        dialog2.findViewById(R.id.tv_clear_meilizhi).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                roomDialog.dismiss();
                                RxUtils.loading(commonModel.cleargiftpricecount(otherUser.getData().get(0).getId() + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
                                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                            @Override
                                            public void onNext(BaseBean baseBean) {
                                                sendChannelMessage(BaseUtils.getJson("401", "", "", "", null));
                                                enterRoomRefrash();
                                                loadVedioList();
                                            }
                                        });
                            }
                        });
                        dialog2.findViewById(R.id.textDialogXiamai).setOnClickListener(v -> {
                            editXiamai(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        textDialogBimai.setOnClickListener(v -> {
                            if (TextUtils.equals(textDialogBimai.getText(), "??????")) {
                                editBimai(String.valueOf(otherUser.getData().get(0).getId()));
                            } else {
                                editKaimai(String.valueOf(otherUser.getData().get(0).getId()));
                            }
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogJinyan).setOnClickListener(v -> {
                            editJinyan(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogTichu).setOnClickListener(v -> {
                            new MaterialDialog.Builder(AdminHomeActivity.this)
                                    .title("????????????Ta??????????????????")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("??????")
                                    .negativeText("??????")
                                    .show();
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("??????", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("????????????");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("??????????????????");
                            }
                        });
                        dialog2.findViewById(R.id.textDialogSongli).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        dialog2.findViewById(R.id.imgJubao).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                            reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        });
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            if (otherUser.getData().get(0).getId() == UserManager.getUser().getUserId()) {
                                intent1.putExtra("sign", 0);
                                intent1.putExtra("id", "");
                                intent1.putExtra("isRoom", true);
                            } else {
                                intent1.putExtra("sign", 1);
                                intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                                intent1.putExtra("isRoom", true);
                            }
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    private void setVedioDialog(int position) {
        LogUtil.d(own + "");
        RxUtils.loading(commonModel.get_other_user(uid,
                        mMicrophone.get(position).getUser_id(), String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<OtherUser>(mErrorHandler) {
                    @Override
                    public void onNext(OtherUser otherUser) {
                        RoomDialog roomDialog = new RoomDialog(AdminHomeActivity.this,
                                R.layout.dialog_room_admin2);
                        View dialog2 = roomDialog.getmMenuView();
                        roomDialog.showAtLocation(img1, Gravity.BOTTOM, 0, 0);
//                        MaterialDialog dialog2 = new MaterialDialog.Builder(AdminHomeActivity.this)
//                                .customView(R.layout.dialog_room_admin2, true)
//                                .show();
                        RoundedImageView popupimg1 = dialog2.findViewById(R.id.img1);
                        ImageView img2 = (ImageView) dialog2.findViewById(R.id.img2);
                        TextView textName = (TextView) dialog2.findViewById(R.id.textName);
                        TextView textDialogGuanzhu = (TextView) dialog2.findViewById(R.id.textDialogGuanzhu);
                        TextView textId = (TextView) dialog2.findViewById(R.id.textId);
                        TextView textDialogBimai = (TextView) dialog2.findViewById(R.id.textDialogBimai);
                        loadImage(popupimg1, otherUser.getData().get(0).getHeadimgurl(), R.mipmap.touxiang_ziliao_girl);
                        img2.setSelected(otherUser.getData().get(0).getSex() == 1);
                        textName.setText(otherUser.getData().get(0).getNickname());
                        textId.setText("ID:" + otherUser.getData().get(0).getId());
                        if (!TextUtils.isEmpty(otherUser.getData().get(0).getBright_num())) {
                            textId.setText("ID:" + otherUser.getData().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }
                        textDialogBimai.setText(otherUser.getData().get(0).getIs_sound() == 1 ? "??????" : "??????");
                        //TODO ???????????????
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "?????????" : "??????");
                        TextView textFenNumber = (TextView) dialog2.findViewById(R.id.textFenNumber);
                        textFenNumber.setText(otherUser.getData().get(0).getAge() + "");
//                        dialog2.findViewById(R.id.imgVip).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).vip_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgJinrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).gold_img) ? View.GONE : View.VISIBLE);
                        dialog2.findViewById(R.id.imgXingrui).setVisibility(TextUtils.isEmpty(otherUser.getData().get(0).star_img) ? View.GONE : View.VISIBLE);
//                        loadImage(dialog2.findViewById(R.id.imgVip), otherUser.getData().get(0).vip_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgJinrui), otherUser.getData().get(0).gold_img, R.mipmap.huizhang);
                        loadImage(dialog2.findViewById(R.id.imgXingrui), otherUser.getData().get(0).star_img, R.mipmap.huizhang);
                        dialog2.findViewById(R.id.textDialogXiamai).setOnClickListener(v -> {
                            editXiamai(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        textDialogBimai.setOnClickListener(v -> {
                            if (TextUtils.equals(textDialogBimai.getText(), "??????")) {
                                editBimai(String.valueOf(otherUser.getData().get(0).getId()));
                            } else {
                                editKaimai(String.valueOf(otherUser.getData().get(0).getId()));
                            }
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogJinyan).setOnClickListener(v -> {
                            editJinyan(String.valueOf(otherUser.getData().get(0).getId()));
                            roomDialog.dismiss();
                        });
                        dialog2.findViewById(R.id.textDialogTichu).setOnClickListener(v -> {
                            new MaterialDialog.Builder(AdminHomeActivity.this)
                                    .title("????????????Ta??????????????????")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("??????")
                                    .negativeText("??????")
                                    .show();
                        });
                        dialog2.findViewById(R.id.tv_clear_meilizhi).setVisibility((user_type == 1 || user_type == 2) ? View.VISIBLE : View.GONE);
                        dialog2.findViewById(R.id.tv_clear_meilizhi).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                roomDialog.dismiss();
                                RxUtils.loading(commonModel.cleargiftpricecount(otherUser.getData().get(0).getId() + "", enterRoom.getRoom_info().get(0).getUid() + ""), AdminHomeActivity.this)
                                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                            @Override
                                            public void onNext(BaseBean baseBean) {
                                                enterRoomRefrash();
                                                loadVedioList();
                                            }
                                        });
                            }
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("??????", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("????????????");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("??????????????????");
                            }
                        });
                        dialog2.findViewById(R.id.textDialogSongli).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            loadSongLi(String.valueOf(otherUser.getData().get(0).getId())
                                    , otherUser.getData().get(0).getNickname(), otherUser.getData().get(0).getHeadimgurl());
                        });
                        dialog2.findViewById(R.id.imgJubao).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                            reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                        });
                        dialog2.findViewById(R.id.imgHome).setOnClickListener(v -> {
                            roomDialog.dismiss();
                            Intent intent1 = new Intent(AdminHomeActivity.this, MyPersonalCenterActivity.class);
                            if (otherUser.getData().get(0).getId() == UserManager.getUser().getUserId()) {
                                intent1.putExtra("sign", 0);
                                intent1.putExtra("id", "");
                                intent1.putExtra("isRoom", true);
                            } else {
                                intent1.putExtra("sign", 1);
                                intent1.putExtra("id", otherUser.getData().get(0).getId() + "");
                                intent1.putExtra("isRoom", true);
                            }
                            ArmsUtils.startActivity(intent1);
                            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                        });
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void loadSongLi(String fromUserId, String nickName, String headerUrl) {
        HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, Object info) {
                GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                GiftListBeanNew giftListBean = new GiftListBeanNew();
                giftListBean.setData(bean);
                LogUtils.debugInfo("??????????????????");
                if (mMicrophone != null) {

                    Microphone.DataBean.MicrophoneBean microphoneBean =
                            new Microphone.DataBean.MicrophoneBean();
                    microphoneBean.setUser_id(String.valueOf(enterRoom.getRoom_info().get(0).getUid()));
                    microphoneBean.setHeadimgurl(enterRoom.getRoom_info().get(0).getHeadimgurl());
                    microphoneBean.setSex(enterRoom.getRoom_info().get(0).getSex());
                    microphoneBean.setNickname(enterRoom.getRoom_info().get(0).getNickname());
                    GiftWindow giftWindow = new GiftWindow(AdminHomeActivity.this,
                            mMicrophone, commonModel, giftListBean, microphoneBean, imgPopup, fromUserId);
                    giftWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                }

            }
        });

//        RxUtils.loading(commonModel.gift_list(String.valueOf(UserManager.getUser().getUserId())))
//                .subscribe(new ErrorHandleSubscriber<GiftListBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(GiftListBean giftListBean) {
//                        Microphone.DataBean.MicrophoneBean microphoneBean =
//                                new Microphone.DataBean.MicrophoneBean();
//                        microphoneBean.setUser_id(String.valueOf(enterRoom.getRoom_info().get(0).getUid()));
//                        microphoneBean.setHeadimgurl(enterRoom.getRoom_info().get(0).getHeadimgurl());
//                        microphoneBean.setSex(enterRoom.getRoom_info().get(0).getSex());
//                        microphoneBean.setNickname(enterRoom.getRoom_info().get(0).getNickname());
//                        GiftNoUserWindow giftWindow = new GiftNoUserWindow(AdminHomeActivity.this,
//                                fromUserId, nickName, commonModel,
//                                giftListBean, microphoneBean, imgPopup, headerUrl);
//                        giftWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
//                    }
//                });
    }

    /**
     * ????????????????????? --- ????????????????????????
     */
    private void editTichu(String user_id) {
        RxUtils.loading(commonModel.out_room(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggunaliyuantichu);
                        loadVedioList();
                        loadData();
                    }
                });
    }

    /**
     * ???????????????
     */
    private void editJinyan(String user_id) {
        RxUtils.loading(commonModel.is_black(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<JinSheng>(mErrorHandler) {
                    @Override
                    public void onNext(JinSheng microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggdfghfhrthmkBeiJinYan);
                        loadVedioList();
                    }
                });
    }

    /**
     * ???????????????????????????
     */
    private void editBimai(String user_id) {
        RxUtils.loading(commonModel.is_sound(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<JinSheng>(mErrorHandler) {
                    @Override
                    public void onNext(JinSheng microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggunaliyuanbimai);
                        loadVedioList();
                    }
                });
    }

    /**
     * ??????????????????????????????
     */
    private void editKaimai(String user_id) {
        RxUtils.loading(commonModel.remove_sound(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<JinSheng>(mErrorHandler) {
                    @Override
                    public void onNext(JinSheng microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggdfghfhrthmkkaimai);
                        loadVedioList();
                    }
                });
    }

    /**
     * ?????????????????????
     */
    private void upEditVedio(int position, String id) {
        showDialogLoding();
        RxUtils.loading(commonModel.up_microphone(uid, id, position + ""), this)
                .subscribe(new ErrorHandleSubscriber<UpVideoResult>(mErrorHandler) {
                    @Override
                    public void onNext(UpVideoResult microphone) {
                        disDialogLoding();
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendCPAtVideo(microphone);
                        loadVedioList();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                    }
                });
    }

    /**
     * ?????????????????????????????????
     */
    private void layoutRoom() {
        RxUtils.loading(commonModel.quit_room(uid, String.valueOf(UserManager.getUser().getUserId())))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                    }
                });
    }

    /**
     * ??????
     */
    private void fllow(String id, TextView textView) {
        RxUtils.loading(commonModel.follow(String.valueOf(UserManager.getUser().getUserId()), id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        textView.setText("?????????");
                        EventBus.getDefault().post(new FirstEvent("????????????", Constant.SHUAXINGUANZHU));
                    }
                });
    }

    /**
     * ????????????
     */
    private void cancelFllow(String id, TextView textView) {
        RxUtils.loading(commonModel.cancel_follow(String.valueOf(UserManager.getUser().getUserId()), id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        textView.setText("??????");
                        EventBus.getDefault().post(new FirstEvent("????????????", Constant.SHUAXINGUANZHU));
                    }
                });
    }

    /**
     * ??????????????????
     */
    public void sendChannelMessage(String msg) {
        try {
            RtmMessage message = mRtmClient.createMessage();
            message.setText(msg);
            mRtmChannel.sendMessage(message, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtils.debugInfo("====??????????????????");
                    LogUtil.d(own + "sendChannelMessage onSuccess");
                    if (is_show_egg == false){
                        getLevelBean();
                    }
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    if (is_show_egg == false){
                        getLevelBean();
                    }
                }
            });
//            SendMessageService sendMessageService = new SendMessageService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendChannelMessage2(String msg) {
        try {
            RtmMessage message = mRtmClient.createMessage();
            message.setText(msg);
            mRtmChannel2.sendMessage(message, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtils.debugInfo("====??????????????????");
                    LogUtil.d(own + "sendChannelMessage2 onSuccess");
                    if (is_show_egg == false){
                        getLevelBean();
                    }
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    if (is_show_egg == false){
                        getLevelBean();
                    }

                }
            });
//            SendMessageService sendMessageService = new SendMessageService();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????
     * ???userid
     */
    public void sendPeerMessage(String dst, String message) {
        RtmMessage msg = mRtmClient.createMessage();
        msg.setText(message);

        mRtmClient.sendMessageToPeer(dst, msg, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtils.debugInfo("====???????????????????????????");
                LogUtil.d(own + "sendMessageToPeer onSuccess");
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                final int errorCode = errorInfo.getErrorCode();
                Log.d(TAG, "Fails to send the message to the peer, errorCode = "
                        + errorCode);
            }
        });
    }

    /**
     * ????????????
     */
    private void loadMusic() {
        //????????????
        loadYinxiao();
        llMusic.setVisibility(View.VISIBLE);
        try {
            listLocal = LitePal.findAll(LocalMusicInfo.class);
            textMusicName.setText(listLocal.get(musicPosition).name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgLiebiao.setOnClickListener(v -> {
            llMusic.setVisibility(View.GONE);
            ArmsUtils.startActivity(MusicActivity.class);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        });
        imgXunhuan.setOnClickListener(v -> {//??????
            if (imgXunhuan.isSelected()) {
                imgXunhuan.setSelected(false);
                randomMusic = 0;//0??????????????????
                toast("?????????????????????");
            } else {
                imgXunhuan.setSelected(true);
                randomMusic = 1;
                toast("?????????????????????");
            }
        });
        imgFront.setOnClickListener(v -> {//?????????
            if (listLocal.size() > 0) {
                if (randomMusic == 0) {
                    if (musicPosition == 0) {
                        toast("????????????????????????");
                    } else {
                        seekBar.setProgress(0);
                        imgStop.setSelected(true);
                        musicPosition = musicPosition - 1;
                        textMusicName.setText(listLocal.get(musicPosition).name);
                        mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                    }
                } else {
                    seekBar.setProgress(0);
                    imgStop.setSelected(true);
                    musicPosition = BaseUtils.getRandom(listLocal.size());
                    textMusicName.setText(listLocal.get(musicPosition).name);
                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                }
            } else {
                toast("???????????????????????????????????????");
            }
        });
        imgStop.setOnClickListener(v -> {//??????
            if (imgStop.isSelected()) {
                imgStop.setSelected(false);
                mRtcEngine.pauseAudioMixing();
//                mRtcEngine.getAudioEffectManager().stopAllEffects();
            } else {
                if (listLocal.size() > 0) {
                    int audioMixingCurrentPosition = mRtcEngine.getAudioMixingCurrentPosition();
                    if (audioMixingCurrentPosition != 0) {
                        mRtcEngine.resumeAudioMixing();
                    } else {
                        mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                    }
                    imgStop.setSelected(true);
                } else {
                    toast("???????????????????????????????????????");
                }
            }
        });
        imgNext.setOnClickListener(v -> {//?????????
            if (listLocal.size() > 0) {
                if (randomMusic == 0) {
                    if (musicPosition == listLocal.size() - 1) {
                        toast("???????????????????????????");
                    } else {
                        imgStop.setSelected(true);
                        musicPosition = musicPosition + 1;
                        seekBar.setProgress(0);
                        textMusicName.setText(listLocal.get(musicPosition).name);
                        mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                    }
                } else {
                    imgStop.setSelected(true);
                    seekBar.setProgress(0);
                    musicPosition = BaseUtils.getRandom(listLocal.size());
                    textMusicName.setText(listLocal.get(musicPosition).name);
                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                }
            } else {
                toast("???????????????????????????????????????");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.sendEmptyMessage(2);//?????????????????????
            }
        });
    }

    /**
     * ????????????
     */
//    private YinxiaoAdapter yinxiaoAdapter;
    private void loadYinxiao() {
        RxUtils.loading(commonModel.get_sound("", UserManager.getUser().getUserId() + ""), this)
                .subscribe(new ErrorHandleSubscriber<MusicYinxiao>(mErrorHandler) {
                    @Override
                    public void onNext(MusicYinxiao yinxiao) {
//                        yinxiaoAdapter = new YinxiaoAdapter(AdminHomeActivity.this, mRtcEngine);
//                        myGrid.setAdapter(yinxiaoAdapter);
//                        yinxiaoAdapter.getList_adapter().clear();
//                        yinxiaoAdapter.getList_adapter().addAll(yinxiao.getData().getYinxiao());
//                        yinxiaoAdapter.notifyDataSetChanged();
                        List<android.support.v4.app.Fragment> mFragments = new ArrayList<>();
                        List<MusicYinxiao.DataBean.YinxiaoBean> yinxiaoList = yinxiao.getData().getYinxiao();
                        if (yinxiaoList.size() > 6) {
                            YinxiaoFragment yinxiaoFragment1 = YinxiaoFragment.getInstance(0, yinxiao);
                            YinxiaoFragment yinxiaoFragment2 = YinxiaoFragment.getInstance(1, yinxiao);
                            mFragments.add(yinxiaoFragment1);
                            mFragments.add(yinxiaoFragment2);
                            yinxiaoFragment1.setRt(mRtcEngine);
                            yinxiaoFragment2.setRt(mRtcEngine);
                        } else {
                            YinxiaoFragment yinxiaoFragment1 = YinxiaoFragment.getInstance(0, yinxiao);
                            mFragments.add(yinxiaoFragment1);
                            yinxiaoFragment1.setRt(mRtcEngine);
                        }
                        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
                        recyclerMusic.setAdapter(adapter);
                    }
                });
    }

    /**
     * ??????
     */
    private void loadEmoji() {
        RxUtils.loading(commonModel.emoji_list(""), this)
                .subscribe(new ErrorHandleSubscriber<EmojiBean>(mErrorHandler) {
                    @Override
                    public void onNext(EmojiBean emojiBean) {
                        List<EmojiBean.DataBean> emojiList = emojiBean.getData();
                        if (emojiBean != null && emojiList != null && emojiList.size() > 0) {
                            rlEmoji.setVisibility(View.VISIBLE);
                            List<Fragment> listFragment = new ArrayList<>();
                            int pageSize = 10;//??????10???
                            int size = emojiList.size();
                            int tem = size % pageSize;
                            int page = size / pageSize;
                            int pageIndex = page;

                            if (page == 0) {//??????1???
                                pageIndex = 1;
                            } else {//??????1???
                                if (tem != 0) {//?????????10??????
                                    pageIndex = pageIndex + 1;
                                }
                            }
                            //??????fragment
                            EmojiFragment emojiFragment;
                            for (int i = 0; i < pageIndex; i++) {
                                int length = i * pageSize + pageSize;
                                //??????list????????????
                                List<EmojiBean.DataBean> childList = new ArrayList<>();
                                for (int j = i * pageSize; j < (length > size ? size : length); j++) {
                                    childList.add(emojiList.get(j));
                                }
                                emojiFragment = EmojiFragment.getInstance(childList);
                                listFragment.add(emojiFragment);
                            }

                            PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), listFragment);
                            viewPager.setAdapter(pagerAdapter);
                            indicator.setViewPager(viewPager);

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast("?????????????????????");
                    }
                });

//        myFragment1 = EmojiFragment.getInstance(0);
//        EmojiFragment myFragment2 = EmojiFragment.getInstance(1);
//        list.add(myFragment1);
//        list.add(myFragment2);
//        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), list);
//        viewPager.setAdapter(pagerAdapter);
//        indicator.setViewPager(viewPager);
    }

    /**
     * ?????????????????????yinpin
     */
    public void stopTing(boolean isStop) {
        if (isStop) {
            mRtcEngine.muteAllRemoteAudioStreams(false);
            imgTing.setSelected(false);
        } else {
            mRtcEngine.muteAllRemoteAudioStreams(true);
            imgTing.setSelected(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //flag 1.mainactivity 2.
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (flag == 1) {
                EventBus.getDefault().post(new FirstEvent("????????????",
                        Constant.FANHUIZHUYE, enterRoom));
                ArmsUtils.startActivity(MainActivity.class);
                moveTaskToBack(true);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                llMusic.setVisibility(View.GONE);
                rlEmoji.setVisibility(View.GONE);
                isTop = false;
            } else {
                EventBus.getDefault().post(new FirstEvent("????????????",
                        Constant.FANHUIZHUYE, enterRoom));
                moveTaskToBack(true);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                llMusic.setVisibility(View.GONE);
                rlEmoji.setVisibility(View.GONE);
                isTop = false;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    @Override
//    protected void onStop() {
//        super.onStop();
//        if(mMicrophone !=null && mMicrophone.size()>0){
//            for(int i = 0;i<mMicrophone.size();i++){
//                stopQuan(i);
//            }
//        }
//    }
//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*try {
            if (null != client) {
                client.close();
                Log.d("=====", "????????????222");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*try {
            if (client != null) return;
            URI uri = URI.create("ws://" + APP_DOMAIN3 + ":9090/ws");
            client = new JWebSocketClient(uri) {
                @Override
                public void onMessage(String message) {
                    //message????????????????????????
                    AdminHomeActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            //message????????????????????????
//                            Log.e("=======", message);
                            GiftSocketBean2 giftSocketBean2 = JSON.parseObject(message, GiftSocketBean2.class);
                            if (!giftSocketBean2.getCode().equals("202")) return;
                            MessageBean messageBean = BaseUtils.getMessageBean(giftSocketBean2.getData());
                            Log.i("123456789",""+messageBean.getMessageType());
                            if (messageBean == null) return;
                            if (messageBean.getRoomId222() != null && !messageBean.getRoomId222().equals(uid)) {
                                if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.smoothScrollToPosition(roomMessageAdapter.getData().size());
                                }
                            }
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    super.onClose(code, reason, remote);
                    try {
                        if (null != client) {
                            client.close();
                            Log.d("=====", "onClose???????????????");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        client = null;
                    }
                }

                @Override
                public void onError(Exception ex) {
                    super.onError(ex);
                    try {
                        if (null != client) {
                            client.close();
                            Log.d("=====", "onError???????????????");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        client = null;
                    }
                }
            };
            if (null != client) {
                client.connectBlocking();
//                client.sendPing();
                GiftSocketBean giftSocketBean = new GiftSocketBean();
                giftSocketBean.setAction("reg");
                giftSocketBean.setHomeid(uid);
                giftSocketBean.setContent("1");
                client.send(JSON.toJSONString(giftSocketBean));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        LogUtils.debugInfo("onResume=============");
//        if(mMicrophone !=null && mMicrophone.size()>0){
//            for(int i = 0;i<mMicrophone.size();i++){
//                stopQuan(i);
//            }
//        }
    }

    /**
     * ????????????????????????
     */
    public String getUid() {
        return uid;
    }

    boolean mIsPushRuning = false;
    CountDownTimer mPushTimer = new CountDownTimer(3 * 1000, 500) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            mBarrageView.postDelayed(() -> mBarrageViewAdapter.addList(mPushBeanList), 500);
            mIsPushRuning = false;
        }
    };

    /**
     * ?????????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (FANGJIANSHEZHI.equals(tag)) {//??????????????????
//            loadEnterRoom();
            RxUtils.loading(commonModel.enter_room(uid, room_pass,
                            String.valueOf(UserManager.getUser().getUserId())), this)
                    .subscribe(new ErrorHandleSubscriber<EnterRoom>(mErrorHandler) {
                        @Override
                        public void onNext(EnterRoom menterRoom) {
                            enterRoom = menterRoom;
                            meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
                            textName.setText(enterRoom.getRoom_info().get(0).getRoom_name());
                            textId.setText("ID:" + enterRoom.getRoom_info().get(0).getNumid());
                            /*if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getBright_num())) {
                                textId.setText("ID:" + enterRoom.getRoom_info().get(0).getBright_num());
                                textId.setTextColor(getResources().getColor(R.color.colorAccent));
                                Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                                textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                                textId.setCompoundDrawablePadding(dip2px(4));
                            }*/
                            textType.setText(enterRoom.getRoom_info().get(0).getRoom_class_name());
                            textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
                            textRoom.setText(enterRoom.getRoom_info().get(0).getNickname());
                            loadImage(imgRoom, enterRoom.getRoom_info().get(0).getHeadimgurl(), R.mipmap.room_kazuo_suo);
                            loadImage(imgBg, enterRoom.getRoom_info().get(0).back_img, R.mipmap.room_bg);
                            if (enterRoom.getRoom_info().get(0).getIs_afk() == 1) {
                                textLayout.setVisibility(View.GONE);
                            } else {
                                textLayout.setVisibility(View.VISIBLE);
                            }

                            String currentGongGao = enterRoom.getRoom_info().get(0).getRoom_intro();

                            if (!TextUtils.equals(mStringGongGao, currentGongGao)) {//????????????
                                mStringGongGao = currentGongGao;
                                //????????????????????????
                                MessageBean msg = new MessageBean();
                                msg.setMessageType("7");
                                msg.setMessage("");
                                msg.setNickName("");
                                msg.setUser_id("");
                                msg.setRoom_name(enterRoom.getRoom_info().get(0).getRoom_name());
                                msg.setRoom_type(enterRoom.getRoom_info().get(0).getRoom_type());
                                msg.setRoom_background(enterRoom.getRoom_info().get(0).back_img);
                                msg.setRoom_intro(enterRoom.getRoom_info().get(0).getRoom_intro());
                                roomMessageAdapter.getData().add(msg);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            }

                            //??????????????????????????????
                            sendChannelMessage(BaseUtils.getJson("7", "",
                                    "", "", enterRoom.getRoom_info().get(0).getRoom_name(),
                                    enterRoom.getRoom_info().get(0).getRoom_type(),
                                    enterRoom.getRoom_info().get(0).back_img,
                                    enterRoom.getRoom_info().get(0).getRoom_intro()));
                        }
                    });
        } else if (SHEZHIGUANLI.equals(tag)) {//????????????
            String userId = event.getMsg();
            sendPeerMessage(userId, nfgk184grdgdfggdfghfhrthmkShezhiguanliyuan);
        } else if (QuxiaoGUANLI.equals(tag)) {//????????????
            String userId = event.getMsg();
            sendPeerMessage(userId, nfgk184grdgdfggdfghfhrthmkQuxiaoiguanliyuan);
        } else if (YINYUEZANTING.equals(tag)) {//????????????
            String msg = event.getMsg();
            musicPosition = Integer.valueOf(msg);
            imgStop.setSelected(false);
            mRtcEngine.pauseAudioMixing();
        } else if (YINYUEBOFANG.equals(tag)) {//????????????
            String msg = event.getMsg();
//            musicPosition = Integer.valueOf(msg);
            try {
                listLocal = LitePal.findAll(LocalMusicInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listLocal.size() > 0) {
                int audioMixingCurrentPosition = mRtcEngine.getAudioMixingCurrentPosition();
                if (audioMixingCurrentPosition != 0) {
                    if (musicPosition == Integer.valueOf(msg)) {
                        mRtcEngine.resumeAudioMixing();
                    } else {
                        musicPosition = Integer.valueOf(msg);
                        textMusicName.setText(listLocal.get(musicPosition).name);
                        mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                    }
                } else {
                    textMusicName.setText(listLocal.get(musicPosition).name);
                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                }
                imgStop.setSelected(true);
            } else {
                toast("???????????????????????????????????????");
            }
        } else if (DIANJIBIAOQING.equals(tag)) {//????????????
            String msg = event.getMsg();
            rlEmoji.setVisibility(View.GONE);
            loadGifEmoji(msg);
        } else if (FASONGMAIXULIWU.equals(tag)) {//????????????
            enterRoomRefrash();
            loadVedioList();
//            GiftFlyDialog rmDialog = new GiftFlyDialog(AdminHomeActivity.this, R.layout.pop_gift_fly);
////            roomDialog.showAsDropooDown(imgMessage);

            MessageBean messageBean = event.getMessageBean();
            messageBean.nick_color = vipBean.getData().getNick_color();
            LogUtils.debugInfo("====?????????????????????" + JSON.toJSONString(messageBean));
            sendChannelMessage(JSON.toJSONString(messageBean));

            FirstEvent firstEvent = new FirstEvent("send_gift", "send_gift");//??????????????????????????????
            EventBus.getDefault().post(firstEvent);

            List<MessageBean.Data> receiveUserList = messageBean.userInfo;
            if (receiveUserList.size() == 1) {
                LogUtils.debugInfo("====mingcheng:" + receiveUserList.get(0).nickname);
                messageBean.nick_color = vipBean.getData().getNick_color();
                roomMessageAdapter.getData().add(messageBean);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
            } else {
                LoginData loginData = UserManager.getUser();
                String userId = loginData.getUserId() + "";
                String userNick = loginData.getNickname();
                String nickColor = vipBean.getData().getNick_color();
                for (MessageBean.Data itemUser : receiveUserList) {
                    MessageBean newMessage = new MessageBean();
                    newMessage.setUser_id(userId);
                    newMessage.setNickName(userNick);
                    newMessage.nick_color = nickColor;
                    newMessage.show_img = messageBean.show_img;
                    newMessage.show_gif_img = messageBean.show_gif_img;
                    newMessage.type = messageBean.type;
                    newMessage.giftNum = messageBean.giftNum;
                    newMessage.e_name = messageBean.e_name;
                    newMessage.setMessageType("4");
                    List<MessageBean.Data> dataList = new ArrayList<>();
                    dataList.add(itemUser);
                    newMessage.userInfo = dataList;
                    roomMessageAdapter.getData().add(newMessage);

                }
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
            }
            if (TextUtils.equals(messageBean.type, "2")) {//??????
                SVGAParser parser = new SVGAParser(this);
                showServerSVG(parser, messageBean.show_gif_img, svgImage);
            } else if (TextUtils.equals(messageBean.type, "1")) {//????????????
                setFlyAnimate(messageBean);
//                loadAniData(messageBean.userInfo, messageBean.show_img);
            }
        } else if (XIUGAIGERENZILIAOCHENGGONG.equals(tag)) {//????????????????????????????????????
            if (user_type == 1) {
                loadEnterRoom();
            } else {
                sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                loadVedioList();
            }
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
            mBarrageView.postDelayed(() -> mBarrageViewAdapter.add(pushBean), 1000);
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
            mBarrageView.postDelayed(() -> mBarrageViewAdapter.add(pushBean), 1000);
        } else if (RECHARGE_FROM_HOME.equals(tag)) {
            popRechargeWindow();    // ??????
        }
    }

    private void popRechargeWindow() {
//        ToastUtil.showToast(mContext,"??????????????????");
        RechargePopWindow popWindow = new RechargePopWindow(AdminHomeActivity.this, commonModel);
        popWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
        popWindow.setRechargePopWindowListener(new RechargePopWindow.RechargePopWindowListener() {
            @Override
            public void recharge(String goodId, String price, int payType) {
                if (payType == 1) {
                    getOrder(goodId, price, payType);
                } else {
                    getWxOrder(goodId, price, payType);
                }
            }
        });
    }

    private void getWxOrder(String goodId, String price, int payType) {
        RxUtils.loading(commonModel.getRechargeWxOrder(payType + "", price, goodId, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<Wxmodel>(mErrorHandler) {
                    @Override
                    public void onNext(Wxmodel wxmodel) {
                        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(AdminHomeActivity.this,
                                "wx7b991e43bc9b5814", true);
                        // ??????app???????????????
                        mWxApi.registerApp("wx7b991e43bc9b5814");
                        // ???????????????????????????
                        if (!mWxApi.isWXAppInstalled()) {
                            toast("?????????????????????????????????");
                            return;
                        }

                        PayReq request = new PayReq();
                        request.appId = "wx7b991e43bc9b5814";
                        request.partnerId = wxmodel.getData().getPartnerid();
                        request.prepayId = wxmodel.getData().getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = wxmodel.getData().getNoncestr();
                        request.timeStamp = wxmodel.getData().getTimestamp() + "";
                        request.sign = wxmodel.getData().getSign();
                        request.checkArgs();
                        boolean send = mWxApi.sendReq(request);
                    }
                });
    }

    private void getOrder(String goodId, String price, int payType) {
        RxUtils.loading(commonModel.getRechargeOrder(payType + "", price, goodId, String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<PayBean>(mErrorHandler) {
                    @Override
                    public void onNext(PayBean payBean) {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(AdminHomeActivity.this);
                                Map<String, String> result = alipay.payV2(payBean.getData(),
                                        true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }
                        };
                        // ??????????????????
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(MessageEvent event) {
        StateMessage stateMessage = event.getStateMessage();
        if (stateMessage.getState() == StateMessage.SEND_GEMSTONE.getState()) { //????????????
            enterRoomRefrash();
            loadVedioList();
            Object[] objects = (Object[]) event.getObject();
            MessageBean messageBean = (MessageBean) objects[0];
            SendGemResult sendGemResult = (SendGemResult) objects[1];

            //??????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????CP????????????
            List<SendGemResult.DataBean> sendDataList = sendGemResult.getData();
            for (SendGemResult.DataBean dataItem : sendDataList) {
                if (dataItem.getIs_first() == 1) {//???????????????
                    JsonObject rootObj = new JsonObject();
                    LoginData loginData = UserManager.getUser();
                    rootObj.addProperty("nickName", loginData.getNickname());
                    rootObj.addProperty("user_id", loginData.getUserId());
                    rootObj.addProperty("nick_color", vipBean.getData().getNick_color());
                    rootObj.addProperty("messageType", "2");
                    rootObj.addProperty("headimgurl", loginData.getHeadimgurl());
                    String str = rootObj.toString();
                    Log.e("?????????????????????==", str);
                    sendPeerMessage(dataItem.getUserId(), str);
                } else {//???????????????????????????????????????????????????
                    messageBean.nick_color = vipBean.getData().getNick_color();
                    sendChannelMessage(JSON.toJSONString(messageBean));
                }
            }

            LogUtils.debugInfo("====?????????????????????" + JSON.toJSONString(messageBean));
//            sendChannelMessage(JSON.toJSONString(messageBean));

            FirstEvent firstEvent = new FirstEvent("send_gift", "send_gift");//??????????????????????????????
            EventBus.getDefault().post(firstEvent);

            List<MessageBean.Data> userInfo = messageBean.userInfo;
            if (userInfo.size() == 1) {
                LogUtils.debugInfo("====?????????:" + userInfo.get(0).nickname);
                messageBean.nick_color = vipBean.getData().getNick_color();
                roomMessageAdapter.getData().add(messageBean);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
            } else {
                LoginData loginData = UserManager.getUser();
                String nickColor = vipBean.getData().getNick_color();
                String nickName = loginData.getNickname();
                String userId = String.valueOf(loginData.getUserId());
                for (MessageBean.Data list : userInfo) {
                    MessageBean newMessage = new MessageBean();
                    newMessage.setUser_id(userId);
                    newMessage.setNickName(nickName);
                    newMessage.nick_color = nickColor;
                    newMessage.show_img = messageBean.show_img;
                    newMessage.show_gif_img = messageBean.show_gif_img;
                    newMessage.type = messageBean.type;
                    newMessage.giftNum = messageBean.giftNum;
                    newMessage.e_name = messageBean.e_name;
                    newMessage.setMessageType("4");
                    List<MessageBean.Data> dataList = new ArrayList<>();
                    dataList.add(list);
                    newMessage.userInfo = dataList;
                    roomMessageAdapter.getData().add(newMessage);
                }
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
            }
            if (TextUtils.equals(messageBean.type, "2")) {//??????
                SVGAParser parser = new SVGAParser(this);
                showServerSVG(parser, messageBean.show_gif_img, svgImage);
            } else if (TextUtils.equals(messageBean.type, "1")) {//????????????
//                setFlyAnimate(messageBean);
                loadAniData(messageBean.userInfo, messageBean.show_img);
            }
        } else if (stateMessage.getState() == StateMessage.PEOPLE_OPEN_GEMSTONE.getState()) { //???????????????????????????13

            MessageBean messageBean = (MessageBean) event.getObject();

            if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                roomMessageAdapter.getData().add(messageBean);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                //??????????????????
                sendChannelMessage(JSON.toJSONString(messageBean));
                /*try {
                    if (client != null && client.isOpen()) {
                        String str = new Gson().toJson(messageBean);
                        MessageBean m22 = new Gson().fromJson(str, MessageBean.class);
                        List<OpenBoxBean.DataBean.AwardListBean> dataL = new ArrayList<>();
                        for (int i = 0; i < m22.awardList.size(); i++) {
                            if (m22.awardList.get(i).getIs_play() != 0) {
                                //                                        messageBean.awardList.remove(i);
                                dataL.add(m22.awardList.get(i));
                            }
                        }
                        if (dataL == null || dataL.size() == 0) return;
                        m22.awardList = dataL;
                        m22.setMessageType("6666");
                        m22.setRoomId222(uid);
                        GiftSocketBean giftSocketBean = new GiftSocketBean();
                        giftSocketBean.setAction("sendallmessage");
                        giftSocketBean.setHomeid(uid);
                        giftSocketBean.setContent(JSON.toJSONString(m22));
                        Log.d("=========", JSON.toJSONString(giftSocketBean));
                        client.send(JSON.toJSONString(giftSocketBean));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                //postAll(messageBean);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg2(MessageEvent2 event) {
        MessageBean messageBean = (MessageBean) event.getObject();
        if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
            roomMessageAdapter.getData().add(messageBean);
            roomMessageAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
            //??????????????????
            sendChannelMessage2(JSON.toJSONString(messageBean));
            /*try {
                if (client != null && client.isOpen()) {
                    String str = new Gson().toJson(messageBean);
                    MessageBean m22 = new Gson().fromJson(str, MessageBean.class);
                    List<OpenBoxBean.DataBean.AwardListBean> dataL = new ArrayList<>();
                    for (int i = 0; i < m22.awardList.size(); i++) {
                        if (m22.awardList.get(i).getIs_play() != 0) {
                            //                                        messageBean.awardList.remove(i);
                            dataL.add(m22.awardList.get(i));
                        }
                    }
                    if (dataL == null || dataL.size() == 0) return;
                    m22.awardList = dataL;
                    m22.setMessageType("6666");
                    m22.setRoomId222(uid);
                    GiftSocketBean giftSocketBean = new GiftSocketBean();
                    giftSocketBean.setAction("sendallmessage");
                    giftSocketBean.setHomeid(uid);
                    giftSocketBean.setContent(JSON.toJSONString(m22));
                    Log.d("=========", JSON.toJSONString(giftSocketBean));
                    client.send(JSON.toJSONString(giftSocketBean));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //postAll(messageBean);
        }
    }

    public void postAll(MessageBean messageBean2) {
        String str = new Gson().toJson(messageBean2);
        MessageBean m22 = new Gson().fromJson(str, MessageBean.class);
        RxUtils.loading(commonModel.getAllRoom())
                .subscribe(new ErrorHandleSubscriber<AllRoomBean>(mErrorHandler) {
                    @Override
                    public void onNext(AllRoomBean baseBean) {
                        Log.d("===========", new Gson().toJson(m22.awardList));
                        List<OpenBoxBean.DataBean.AwardListBean> dataL = new ArrayList<>();
                        for (int i = 0; i < m22.awardList.size(); i++) {
                            if (m22.awardList.get(i).getIs_play() != 0) {
//                                        messageBean.awardList.remove(i);
                                dataL.add(m22.awardList.get(i));
                            }
                        }
                        if (dataL == null || dataL.size() == 0) return;
                        m22.awardList = dataL;
                        m22.setMessageType("6666");
                        List<SendMessageServiceBean> ddd = new ArrayList<>();
                        for (int i = 0; i < baseBean.getData().size(); i++) {
                            if (!baseBean.getData().get(i).getNumid().equals(uid)) {
                                ddd.add(new SendMessageServiceBean(baseBean.getData().get(i).getNumid(), JSON.toJSONString(m22)));
                            }
                        }
                        Intent it = new Intent(AdminHomeActivity.this, SendMessageService.class);
                        it.putExtra("list", (Serializable) ddd);
                        startService(it);
                    }
                });
    }

    /**
     * ????????????????????????
     */
    private void setFlyAnimate(MessageBean messageBean) {

        if (!ExtConfig.isSendAllGiftShow){
            return;
        }

        int[] location = messageBean.location;
//        if (mGiftFlyDialog == null) {
        mGiftFlyDialog = new GiftFlyDialog1(this, R.layout.pop_gift_fly, feiLeft, feiTop, location);
//        }
        if (!mGiftFlyDialog.isShowing()) {
            mGiftFlyDialog.showAsDropDown(imgMessage);
        }

        List<MessageBean.Data> userInfo = messageBean.userInfo;

        String imgUrl = messageBean.show_img;

        for (MessageBean.Data list : userInfo) {
            if (TextUtils.equals(list.userId, uid)) {//??????
                imgRoom.post(() -> {

                    mGiftFlyDialog.startImageFly(imgRoom, imgUrl);

                });
            } else {
                for (Microphone.DataBean.MicrophoneBean listPhone : mMicrophone) {
                    if (TextUtils.equals(list.userId, listPhone.getUser_id())) {
                        LogUtils.debugInfo("====sgm???????????????" + list.userId);
                        int i = mMicrophone.indexOf(listPhone);
                        switch (i) {
                            case 0:
                                mGiftFlyDialog.startImageFly(img1, imgUrl);
                                break;
                            case 1:
                                mGiftFlyDialog.startImageFly(img2, imgUrl);
                                break;
                            case 2:
                                mGiftFlyDialog.startImageFly(img3, imgUrl);
                                break;
                            case 3:
                                mGiftFlyDialog.startImageFly(img4, imgUrl);
                                break;
                            case 4:
                                mGiftFlyDialog.startImageFly(img5, imgUrl);
                                break;
                            case 5:
                                mGiftFlyDialog.startImageFly(img6, imgUrl);
                                break;
                            case 6:
//                                mGiftFlyDialog.startImageFly(img7, imgUrl, true);
                                mGiftFlyDialog.startImageFly(img7, imgUrl);
                                break;
                            case 7:
                                mGiftFlyDialog.startImageFly(img8, imgUrl);
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * ??????????????????
     */
    private void loadAniData(List<MessageBean.Data> userInfo, String imgUrl) {
        if (!ExtConfig.isSendAllGiftShow){
            return;
        }


        for (MessageBean.Data list : userInfo) {
            if (TextUtils.equals(list.userId, uid)) {//??????
                imgRoom.post(() -> {

                    RelativeLayout.LayoutParams paramsImgFei = (RelativeLayout.LayoutParams) imgFei.getLayoutParams();
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(paramsImgFei);
                    layoutRoot.addView(imageView);

                    GlideArms.with(mContext)
                            .load(imgUrl)
                            .placeholder(R.drawable.shf_img2)
                            .error(R.drawable.shf_img2)
                            .circleCrop()
                            .into(imageView);
                    int[] location = new int[2];
                    imgRoom.getLocationOnScreen(location);
                    LogUtils.debugInfo("====???2???" + location[0] + "====???2" + location[1]);
                    startAnimotion(location[0] - feiLeft + 70, location[1] - feiTop + 150, imageView);
                });
            } else {
                for (Microphone.DataBean.MicrophoneBean listPhone : mMicrophone) {
                    if (TextUtils.equals(list.userId, listPhone.getUser_id())) {
                        LogUtils.debugInfo("====sgm???????????????" + list.userId);
                        int i = mMicrophone.indexOf(listPhone);
                        switch (i) {
                            case 0:
                                setImageFei(img1, imgFei1, imgUrl, false);
                                break;
                            case 1:
                                setImageFei(img2, imgFei2, imgUrl, false);
                                break;
                            case 2:
                                setImageFei(img3, imgFei3, imgUrl, false);
                                break;
                            case 3:
                                setImageFei(img4, imgFei4, imgUrl, false);
                                break;
                            case 4:
                                setImageFei(img5, imgFei5, imgUrl, false);
                                break;
                            case 5:
                                setImageFei(img6, imgFei6, imgUrl, false);
                                break;
                            case 6:
                                setImageFei(img7, imgFei7, imgUrl, true);
                                break;
                            case 7:
                                setImageFei(img8, imgFei8, imgUrl, false);
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void setImageFei(ImageView imgVedio, ImageView imgVisiFei, String imgUrl, boolean isSeven) {
        RelativeLayout.LayoutParams paramsImgFei = (RelativeLayout.LayoutParams) imgVisiFei.getLayoutParams();
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(paramsImgFei);
        layoutRoot.addView(imageView);

        GlideArms.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.shf_img2)
                .error(R.drawable.shf_img2)
                .circleCrop()
                .into(imageView);

        int[] location = new int[2];
        imgVedio.getLocationOnScreen(location);
//        startAnimotion(location[0] - feiLeft + 70, location[1] - feiTop + 150, imgVisiFei);
        if (isSeven) {
            startAnimotion(location[0] - feiLeft + 130, location[1] - feiTop + 210, imageView);
        } else {
            startAnimotion(location[0] - feiLeft + 70, location[1] - feiTop + 150, imageView);
        }
    }

    /**
     * ????????????
     */
    private void startAnimotion(int width, int height, ImageView imgVisiFei) {
        imgVisiFei.setVisibility(View.VISIBLE);

        final TranslateAnimation translateAnimation = new TranslateAnimation(0,
                width, 0, height);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillBefore(false);
        translateAnimation.setRepeatMode(ScaleAnimation.RESTART);
        translateAnimation.setRepeatCount(0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                imgVisiFei.clearAnimation();
//                translateAnimation.cancel();
//                RelativeLayout.LayoutParams params =
//                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                RelativeLayout.LayoutParams.WRAP_CONTENT);
//                int left = imgFei.getLeft();
//                int top = imgFei.getTop();
//                params.setMargins(imgFei.getLeft() + width, imgFei.getTop() + height,
//                        0, 0);// ????????????,????????????????????????????????????height??????
//                imgVisiFei.setLayoutParams(params);
                imgVisiFei.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (imgVisiFei != null) {
                            imgVisiFei.clearAnimation();
                            translateAnimation.cancel();
                            imgVisiFei.setVisibility(View.GONE);
                            RelativeLayout layoutParent = (RelativeLayout) imgVisiFei.getParent();
                            layoutParent.removeView(imgVisiFei);
                            //????????????
//                            RelativeLayout.LayoutParams params2 =
//                                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                            RelativeLayout.LayoutParams.WRAP_CONTENT);
//                            params2.setMargins(left, top,
//                                    0, 0);// ????????????,????????????????????????????????????height??????
//                            imgVisiFei.setLayoutParams(params2);
                        }
                    }
                }, 1000);//??????1????????????

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //????????????
        imgVisiFei.startAnimation(translateAnimation);
        translateAnimation.start();
    }

    /**
     * ????????????
     */
    private void loadGifEmoji(String id) {
        RxUtils.loading(commonModel.get_emoji(id), this)
                .subscribe(new ErrorHandleSubscriber<GifBean>(mErrorHandler) {
                    @Override
                    public void onNext(GifBean gifBean) {
                        String emoji = gifBean.getData().get(0).getEmoji();
                        LoginData loginData = UserManager.getUser();
                        if (vipBean != null && vipBean.getData() != null) {
                            sendChannelMessage(BaseUtils.getJson("5",
                                    "?????????????????????" + gifBean.getData().get(0).getName(),
                                    loginData.getNickname(), loginData.getUserId() + "",
                                    gifBean.getData().get(0).getIs_answer(),
                                    gifBean.getData().get(0).getEmoji(), gifBean.getData().get(0).getT_length(),
                                    vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color()));
                        } else {
                            sendChannelMessage(BaseUtils.getJson("5",
                                    "?????????????????????" + gifBean.getData().get(0).getName(),
                                    loginData.getNickname(), loginData.getUserId() + "",
                                    gifBean.getData().get(0).getIs_answer(),
                                    gifBean.getData().get(0).getEmoji(), gifBean.getData().get(0).getT_length(),
                                    "", "", ""));
                        }
                        if (user_type == 1) {
                            imgRoomGif.setVisibility(View.VISIBLE);
                            loadOneTimeGif(AdminHomeActivity.this, imgRoomGif, emoji, new GifListener() {
                                @Override
                                public void gifPlayComplete() {
                                    imgRoomGif.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            int maiPosition = 0;//????????????
                            for (Microphone.DataBean.MicrophoneBean list : mMicrophone) {
                                if (TextUtils.equals(list.getUser_id(),
                                        String.valueOf(loginData.getUserId()))) {
                                    maiPosition = mMicrophone.indexOf(list);
                                }
                            }
                            maiPosition = maiPosition + 1;
                            switch (maiPosition) {
                                case 1:
                                    loadGifShow(imgGif1, emoji);
                                    break;
                                case 2:
                                    loadGifShow(imgGif2, emoji);
                                    break;
                                case 3:
                                    loadGifShow(imgGif3, emoji);
                                    break;
                                case 4:
                                    loadGifShow(imgGif4, emoji);
                                    break;
                                case 5:
                                    loadGifShow(imgGif5, emoji);
                                    break;
                                case 6:
                                    loadGifShow(imgGif6, emoji);
                                    break;
                                case 7:
                                    loadGifShow(imgGif7, emoji);
                                    break;
                                case 8:
                                    loadGifShow(imgGif8, emoji);
                                    break;
                            }

                        }
                        if (!TextUtils.equals(gifBean.getData().get(0).getIs_answer(), "0")) {//?????????????????????
                            String json = "";
                            if (vipBean != null && vipBean.getData() != null) {
                                json = BaseUtils.getJson("5", "?????????????????????" + gifBean.getData().get(0).getName(),
                                        loginData.getNickname(), loginData.getUserId() + "",
                                        gifBean.getData().get(0).getIs_answer(),
                                        gifBean.getData().get(0).getEmoji(),
                                        gifBean.getData().get(0).getT_length(),
                                        vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color());
                            } else {
                                json = BaseUtils.getJson("5", "?????????????????????" + gifBean.getData().get(0).getName(),
                                        loginData.getNickname(), loginData.getUserId() + "",
                                        gifBean.getData().get(0).getIs_answer(),
                                        gifBean.getData().get(0).getEmoji(),
                                        gifBean.getData().get(0).getT_length(),
                                        "", "", "");
                            }
                            roomMessageAdapter.getData().add(BaseUtils.getMessageBean(json));
                            roomMessageAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                        }
                    }
                });
    }

    /**
     * ????????????git??????
     */
    private void loadGifShow(ImageView imgGif, String url) {
        imgGif.setVisibility(View.VISIBLE);
        loadOneTimeGif(AdminHomeActivity.this, imgGif, url, new GifListener() {
            @Override
            public void gifPlayComplete() {
                imgGif.setVisibility(View.GONE);
            }
        });
    }

    /**
     * ????????????,????????????????????????
     */
    private void loadEnterRoom() {
        RxUtils.loading(commonModel.enter_room(uid, room_pass,
                        String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<EnterRoom>(mErrorHandler) {
                    @Override
                    public void onNext(EnterRoom menterRoom) {
                        enterRoom = menterRoom;
                        meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
                        textName.setText(enterRoom.getRoom_info().get(0).getRoom_name());
                        textId.setText("ID:" + enterRoom.getRoom_info().get(0).getNumid());
                        /*if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getBright_num())) {
                            textId.setText("ID:" + enterRoom.getRoom_info().get(0).getBright_num());
                            textId.setTextColor(getResources().getColor(R.color.colorAccent));
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            textId.setCompoundDrawablePadding(dip2px(4));
                        }*/
                        textType.setText(enterRoom.getRoom_info().get(0).getRoom_class_name());
                        textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
                        textRoom.setText(enterRoom.getRoom_info().get(0).getNickname());
                        loadImage(imgRoom, enterRoom.getRoom_info().get(0).getHeadimgurl(), R.mipmap.room_kazuo_suo);
                        loadImage(imgBg, enterRoom.getRoom_info().get(0).back_img, R.mipmap.room_bg);
                        if (enterRoom.getRoom_info().get(0).getIs_afk() == 1) {
                            textLayout.setVisibility(View.GONE);
                        } else {
                            textLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(AdminHomeActivity.this, "?????????", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AdminHomeActivity.this, "??????" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AdminHomeActivity.this, "?????????", Toast.LENGTH_LONG).show();

        }
    };

    //private JWebSocketClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}