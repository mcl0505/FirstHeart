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
 * 房间主人动态
 * //    messageType     1：正常的 聊天消息  2：进入房间  3： 刷新麦序列表  4 ：礼物消息  5 ：表型消息
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
    private static String uid;      // 房间的uid
    private String room_pass = "";  // 房间密码
    private BarrageAdapter<PushBean> mBarrageViewAdapter;//弹幕
    List<PushBean> mPushBeanList = new Vector<>();
    // 声网im跟声网语音
    private RtcEngine mRtcEngine;
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;

    private List<Microphone.DataBean.MicrophoneBean> mMicrophone = new ArrayList<>();   // 麦序列表数据
    private EnterRoom enterRoom;
    private int user_type;

    private RoomMessageAdapter roomMessageAdapter;  // 房间消息适配器
    List<MessageBean> listMessage = new ArrayList<>();//存消息的数据集合
    public boolean isEditBimai;//是否被管理员闭麦
    public static boolean isStart;//是否启动
    public static boolean isTop;//是否在顶部被启动
    public static AdminHomeActivity mContext;
    private int flag;//跳转来源，1.MainActivity
    private int musicPosition = 0;//当前音乐播放的位置
    private int randomMusic = 0;//是否是随机播放
    private Timer timer;
    private TimerTask timerTask;
    private List<RequestUpMicrophoneBean.DataBean> requestUpMicrophoneData; // 申请上麦的列表数据

    //    private EmojiFragment myFragment1;
    //private int selfPosition = 0;//自己麦位的位置
    //int a = 0;
    private int feiLeft;//飞礼物的位置
    private int feiTop;

    private VipBean vipBean = new VipBean();//vip信息

    private List<LocalMusicInfo> listLocal;

    boolean mHasCPAtRoom = false;

    String mStringGongGao = "";//公告，记住当前的公告，当公告修改后，公屏显示一下最新的公告

    GiftFlyDialog1 mGiftFlyDialog;
    //是否展示宝箱
    public boolean is_show_egg;
    //等级信息
    private DengJiBean dengJiBean;
    private int userLevel = 0;

    private static final int SDK_PAY_FLAG = 101;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://更新播放进度
                    try {
                        int audioMixingDuration = mRtcEngine.getAudioMixingDuration();
                        int audioMixingCurrentPosition = mRtcEngine.getAudioMixingCurrentPosition();
//                    LogUtils.debugInfo("====当前位置" + audioMixingCurrentPosition * 100 / audioMixingDuration);
                        if (audioMixingCurrentPosition * 100 / audioMixingDuration == 99) {
                            if (randomMusic == 0) {
                                // 循环(不随机)
                                if (musicPosition == listLocal.size() - 1) {
                                    // 循环播放第1条
                                    musicPosition = 0;
                                    seekBar.setProgress(0);
                                    textMusicName.setText(listLocal.get(musicPosition).name);
                                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                } else {
                                    // 播放下一条
                                    musicPosition = musicPosition + 1;
                                    seekBar.setProgress(0);
                                    textMusicName.setText(listLocal.get(musicPosition).name);
                                    mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                }
                            } else {
                                // 根据musicPosition位置播放
                                seekBar.setProgress(0);
                                musicPosition = BaseUtils.getRandom(listLocal.size());
                                textMusicName.setText(listLocal.get(musicPosition).name);
                                mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                            }
                        } else {
                            // 更新进度条位置
                            seekBar.setProgress(audioMixingCurrentPosition * 100 / audioMixingDuration);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // 拖动播放
                    try {
                        int progress = seekBar.getProgress();
                        int allDuration = mRtcEngine.getAudioMixingDuration();
                        int currentDuration = allDuration * progress / 100;//拖动的时长
                        LogUtils.debugInfo("====拖动的时长" + currentDuration);
                        mRtcEngine.setAudioMixingPosition(currentDuration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SDK_PAY_FLAG:
                    // 支付成功
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                    } else {
                        // 失败。
                        showToast("支付失败,请重试");
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

        isStart = true;//代表启动了
        isTop = true;//在顶部
        mContext = this;
        ivLottery.setVisibility(ExtConfig.isShowLottery?View.VISIBLE:View.GONE);
        initRoomData(); // 先从上一个界面带过来的房间信息填充
        loadVipData();  // 获取vip徽章，成功后刷新聊天屏幕信息，同时登录声网im
        loadVedioList();    // 初始化麦序列表，各个卡槽模块布局

        // initLive();//初始化直播
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        initLive();//初始化直播
                    }
                });

//        diffuseViewRoom.start();
        imgFei.post(() -> {
            int[] location = new int[2];
            imgFei.getLocationOnScreen(location);
            feiLeft = location[0];
            feiTop = location[1];
            imgFei.setVisibility(View.GONE);
            LogUtils.debugInfo("====飞1：" + location[0] + "====飞1" + location[1]);
        });
        //弹幕
        initDanmu();

        findViewById(R.id.tv_clear_cp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxUtils.loading(commonModel.delete_cp(UserManager.getUser().getToken()), AdminHomeActivity.this)
                        .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                            @Override
                            public void onNext(BaseBean baseBean) {
                                toast("成功：" + baseBean.getMessage());
                            }
                        });
            }
        });
        loadData();
    }

    /**
     * 初始化房间数据
     */
    private void initRoomData() {
        // 根据房间来源 和 用户ID来获取房间初化信息：跳转来源，1.MainActivity
        uid = getIntent().getStringExtra("uid");
        flag = getIntent().getIntExtra("flag", 1);
        // 房间参数，从外部带入
        enterRoom = (EnterRoom) getIntent().getSerializableExtra("enterRoom");
        LogUtils.debugInfo(own + "initRoomData: " + uid + "_" + flag);
        LogUtils.debugInfo(own + "enterRoom: " + enterRoom.getRoom_info().get(0).getRoom_name());
        LogUtils.debugInfo(own + "enterRoom: " + enterRoom.getRoom_info().get(0).getRoom_name());

        if (enterRoom != null) {

            is_show_egg = (enterRoom.getRoom_info().get(0).getConsume()>=ExtConfig.showEggMoney);

            // 房间背景图
            loadImage(imgBg, enterRoom.getRoom_info().get(0).back_img, R.mipmap.room_bg);
            // 房间标题栏
            textName.setText(enterRoom.getRoom_info().get(0).getRoom_name());
            textId.setText("ID:" + enterRoom.getRoom_info().get(0).getUid());
            // bright_num影响ID的展示
            /*if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getBright_num())) {
                textId.setText("ID:" + enterRoom.getRoom_info().get(0).getBright_num());
                textId.setTextColor(getResources().getColor(R.color.colorAccent));
                Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                textId.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                textId.setCompoundDrawablePadding(dip2px(4));
            }*/
            // 房间收藏状态
            imgCollection.setSelected(enterRoom.getRoom_info().get(0).getIs_mykeep() == 1);

            // 被隐藏
            textType.setText(enterRoom.getRoom_info().get(0).getRoom_class_name()); // 类型
            textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));   // 活跃度
            if (enterRoom.getRoom_info().get(0).getIs_afk() == 1)
                textLayout.setVisibility(View.GONE);  // “离开”显示隐藏
            else textLayout.setVisibility(View.VISIBLE);

            // 房主位置
            // 魅力值:  暂时显示活力值
            meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
            //meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
            // 房主昵称
            textRoom.setText(enterRoom.getRoom_info().get(0).getNickname());
            // 房主的语音图标
            imgRoomVedio.setSelected(enterRoom.getRoom_info().get(0).getIs_sound() == 1);
            // 房主头像选中状态
            imgRoom.setSelected(enterRoom.getRoom_info().get(0).getSex() == 1);
            // 房主头像的边框
            if (enterRoom.getRoom_info().get(0).getSex() == 1) {
                imgRoom.setBorderColor(getResources().getColor(R.color.bottom_text_ok));
            } else {
                imgRoom.setBorderColor(getResources().getColor(R.color.bottom_text_ok));
            }
            // 房主头像图是否上锁
            loadImage(imgRoom, enterRoom.getRoom_info().get(0).getHeadimgurl(), R.mipmap.room_kazuo_suo);
            // 房主头像框
            if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).txk)) {//房主的头像框
                mImgTxkZhu.setVisibility(View.VISIBLE);
                loadImage(mImgTxkZhu, enterRoom.getRoom_info().get(0).txk, 0);
            } else {
                mImgTxkZhu.setVisibility(View.GONE);
            }

            // 我针对于该房间的用户类型
            user_type = enterRoom.getRoom_info().get(0).getUser_type();
            LogUtils.debugInfo(own + "user_type：" + user_type);
            // 1.房主 2.管理员 3.禁言 4.评委 5.普通用户
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

            // 房间聊天列表适配器
            roomMessageAdapter = new RoomMessageAdapter(this);
            View headerMessage = ArmsUtils.inflate(this, R.layout.message_header);
            TextView textNameXitong = headerMessage.findViewById(R.id.textNameXitong);
            textNameXitong.setText("系统通知：" + enterRoom.getRoom_info().get(0).getRoom_welcome());
            TextView textMessage2 = headerMessage.findViewById(R.id.textMessage2);
            textMessage2.setVisibility(View.VISIBLE);
            String text = "欢迎来到我的房间~,希望你玩的开心~";
            if (!TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getRoom_intro())) {
                text = enterRoom.getRoom_info().get(0).getRoom_intro();
                mStringGongGao = enterRoom.getRoom_info().get(0).getRoom_intro();
            }
            textMessage2.setText(text);
            roomMessageAdapter.addHeaderView(headerMessage);
            // 点击个人发言的名称
            roomMessageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                MessageBean itemBean = roomMessageAdapter.getData().get(position);
                if (itemBean == null) return;
                String type = itemBean.getMessageType();
                if (TextUtils.equals("8", type) || TextUtils.equals("9", type) || TextUtils.equals("10", type) || TextUtils.equals("11", type)) {//这个几个都是一个textview设置不同点击事件
                    return;
                } else {
                    if (view.getId() == R.id.textName || view.getId() == R.id.textName2 || view.getId() == R.id.textNameGift1) {
                        setFirstNameClick(position);
                    } else if (view.getId() == R.id.textNameGift2) {//接受礼物人的名字被点击
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
     * 初始化房间数据
     */
    private void refreshEgg() {
        if (is_show_egg) {
            baoxiang.setVisibility(View.VISIBLE);

        } else {
            baoxiang.setVisibility(View.INVISIBLE);
        }
    }

    private void getLevelBean() {

        //设置宝箱图片
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
     * vip数据
     */
    private void loadVipData() {
        //vip徽章
        RxUtils.loading(commonModel.get_user_vip(uid + "", UserManager.getUser().getToken()), this)
                .subscribe(new ErrorHandleSubscriber<VipBean>(mErrorHandler) {
                    @Override
                    public void onNext(VipBean baseBean) {
                        vipBean = baseBean;
                        //刷新自己的公屏
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (vipBean != null && vipBean.getData() != null) {
                                    List<VipBean.DataBean.CpUsersBean> cp_user_list = vipBean.getData().getCp_users();//在房间的cp列表
                                    LoginData loginData = UserManager.getUser();
                                    if (cp_user_list != null && cp_user_list.size() > 0) {
                                        // 看CP是否在房间里面
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
                                            newMessage.cp_tx = cpUsersBean.getCp_tx();//CP特效
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
                                            newMessage.cp_tx = cpUsersBean.getCp_tx();//CP特效
                                            newMessage.setMessageType("8");
                                            roomMessageAdapter.getData().add(newMessage);
                                        }
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    } else {
                                        //没有CP，则更新自己到公屏
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
     * 麦序列表
     */
    private void loadVedioList() {
        RxUtils.loading(commonModel.microphone_status(uid), this)
                .subscribe(new ErrorHandleSubscriber<Microphone>(mErrorHandler) {
                    @Override
                    public void onNext(Microphone enterRoom) {
                        List<Microphone.DataBean.MicrophoneBean> microphone = enterRoom.getData().getMicrophone();
                        mMicrophone = microphone;
                        List<Integer> listKong = new ArrayList<>();
                        for (Microphone.DataBean.MicrophoneBean list : microphone) {//判断是否满位
                            int status = list.getStatus();
                            if (status == 1) listKong.add(status);  // 1表示无人
                        }
                        if (listKong.size() == 0) {//证明麦上位置满了，显示排麦
                            imgPaimai.setVisibility(View.VISIBLE);
                            imgShangmai.setVisibility(View.GONE);
                        } else {
                            imgPaimai.setVisibility(View.GONE);
                            if (user_type != 1) {   // 不是房主才走下面代码
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
     * 设置卡座的各个状态
     */
    private void setKazuo(RoundedImageView imageView, TextView textView, ImageView imgTxk, int position,
                          List<Microphone.DataBean.MicrophoneBean> microphone, ImageView imgVedio,
                          TextView textNumber, TextView textMeili) {
        int status = microphone.get(position).getStatus();
        //右下角开麦，闭麦的状态
        int is_sound = microphone.get(position).getIs_sound();
        switch (status) {
            case 1://无人
                loadImage(imageView, "", R.drawable.shf_img2);
                //textView.setText("");
                textView.setText((position + 1) + "号麦");
                textView.setTextColor(getResources().getColor(R.color.white));
                imgVedio.setVisibility(View.GONE);
                imgTxk.setVisibility(View.GONE);//头像框
//                hideQuan(position);
                break;
            case 2://麦序有人
                loadImage(imageView, microphone.get(position).getHeadimgurl(), R.drawable.shf_img2);
                textView.setText(microphone.get(position).getNickname());
                textView.setTextColor(getResources().getColor(R.color.white));
                if (!TextUtils.isEmpty(microphone.get(position).getTxk())) {//头像框
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
                //麦序有人，判断是否是自己
                if (microphone.get(position).getUser_id().equals(String.valueOf(UserManager.getUser().getUserId()))) {
                    // 自己在麦上，自己就多了一些权限
                    // selfPosition = position;//自己的麦位位置
                    imgShangmai.setSelected(true);
                    imgShangmai.setVisibility(View.VISIBLE);
                    imgPaimai.setVisibility(View.GONE);
                    imgBimai.setVisibility(View.VISIBLE);
                    imgMusic.setVisibility(View.VISIBLE);
                    imgBiaoqing.setVisibility(View.VISIBLE);
                    mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
                }
                break;
            case 3://被锁
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
     * 1.初始化直播间音频
     */
    private void initLive() {
        try {
            String myId = UserManager.getUser().getUserId() + "";
            mRtcEngine = RtcEngine.create(this, Api.AGORA_KEY, new IRtcEngineEventHandler() {
                @Override
                public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                    super.onJoinChannelSuccess(channel, uid, elapsed);
                    LogUtils.debugInfo("sgm", "====加入音频直播成功！");
                    LogUtil.d(own + "加入音频直播成功!");
                }

                @Override
                public void onError(int err) {
                    super.onError(err);
                    LogUtils.debugInfo("sgm", "====加入失败！" + err);
                    LogUtils.debugInfo(own + "加入音频直播失败: " + err);
                }

                @Override
                public void onLeaveChannel(RtcStats stats) {
                    super.onLeaveChannel(stats);
                    LogUtils.debugInfo("sgm", "====离开！");
                    LogUtils.debugInfo(own + "离开!");
                }

                @Override
                public void onUserMuteAudio(int uid, boolean muted) {
                    super.onUserMuteAudio(uid, muted);
                    LogUtils.debugInfo("sgm", "====onUserMuteAudio" + muted);
                    LogUtil.d(own + "onUserMuteAudio：" + muted);
                }

                @Override
                public void onConnectionLost() {
                    super.onConnectionLost();
                    LogUtils.debugInfo("sgm", "====网络链接丢失");
                    LogUtils.debugInfo(own + "网络链接丢失");
                }

                @Override
                public void onAudioMixingStateChanged(int state, int errorCode) {//播放状态改变
                    super.onAudioMixingStateChanged(state, errorCode);
                    LogUtils.debugInfo("====状态" + state);
                    LogUtil.d(own + "播放状态改变: " + state);
                    switch (state) {
                        case 710://正常
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
                        case 711://暂停
                            runOnUiThread(() -> {
                                if (timer != null && timerTask != null) {
                                    timer.cancel();
                                    timerTask.cancel();
                                }
                            });
                            break;
                        case 713://停止
                            runOnUiThread(() -> {
                                try {
                                    if (listLocal != null && listLocal.size() > 0) {
                                        if (randomMusic == 0) {//顺序播放
                                            if (musicPosition == listLocal.size() - 1) {
//                                                        toast("已经是最后一个了！");
                                            } else {
                                                musicPosition = musicPosition + 1;
                                                seekBar.setProgress(0);
                                                textMusicName.setText(listLocal.get(musicPosition).name);
                                                mRtcEngine.startAudioMixing(listLocal.get(musicPosition).songUrl, false, false, 1);
                                            }
                                        } else {//随机播放
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
                            // 谁在说话监听
                            if (speakers.length > 0) {
                                List<AudioVolumeInfo> listShuohua = new ArrayList<>();
                                for (AudioVolumeInfo list : speakers) {
                                    if (list.uid != 0) {
                                        listShuohua.add(list);
                                    }
                                }

                                int size = mMicrophone.size();  // 当前麦位的总数
                                for (AudioVolumeInfo audioVolumeInfo : listShuohua) {
                                    for (int i = 0; i < size; i++) {
                                        if (String.valueOf(audioVolumeInfo.uid).equals(mMicrophone.get(i).getUser_id())) {
                                            int colors = 0;
                                            String color = mMicrophone.get(i).getMic_color();
                                            if (!TextUtils.isEmpty(color)) {
                                                colors = Color.parseColor(color);
                                            }
                                            // 匹配上后，给说话人加动效（麦位光圈）
                                            showQuan(i, audioVolumeInfo.volume, colors);
                                        } else if (String.valueOf(audioVolumeInfo.uid).equals(uid)) {
                                            int colorOne = 0;
                                            String colorStr = mMicrophone.get(i).getMic_color();
                                            if (!TextUtils.isEmpty(colorStr)) {
                                                colorOne = Color.parseColor(colorStr);
                                            }
                                            // 给房主麦位加光圈
                                            showQuan(8, audioVolumeInfo.volume, colorOne);
                                        }
                                    }
                                }

                                if (listShuohua.size() == 0) {
                                    //没人说话，除了自己之外的所有人的麦上光圈清空
                                    for (int i = 0; i < size; i++) {
                                        Microphone.DataBean.MicrophoneBean itemPhone = mMicrophone.get(i);
                                        if (TextUtils.equals(itemPhone.getUser_id(), myId)) {
                                            // 是自己的，什么都不做
                                        } else {
                                            stopQuan(i);    // 关闭其他人的麦上光圈
                                        }
                                    }
                                } else {
                                    for (AudioVolumeInfo audioVolumeInfo : listShuohua) {
                                        for (int i = 0; i < size; i++) {
                                            // 说话人的uid不能识别的情况？？？
                                            if (!String.valueOf(audioVolumeInfo.uid).equals(mMicrophone.get(i).getUser_id())
                                                    && !String.valueOf(audioVolumeInfo.uid).equals(uid)) {
                                                stopQuan(i);
                                            }
                                        }
                                    }
                                }

                                //自己说话的处理
                                for (AudioVolumeInfo list : speakers) {
//                                            Log.e("====说话人：" + list.uid, "====音量" + list.volume);
                                    if (list.uid == 0 && list.volume > 20) {//判断自己是否说话显示光圈
                                        if (user_type == 1) {   // 自己是房主
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
                                                // 如果说是自己
                                                if (TextUtils.equals(listPhone.getUser_id(), myId)) {
                                                    int i = mMicrophone.indexOf(listPhone);
                                                    int colors = 0;
//                                                            if (enterRoom != null && enterRoom.getRoom_info() != null && enterRoom.getRoom_info().size() > 0) {
                                                    String color = mMicrophone.get(i).getMic_color();
                                                    if (!TextUtils.isEmpty(color)) {
                                                        colors = Color.parseColor(color);
                                                    }
//                                                            }
                                                    showQuan(i, list.volume, colors);  // 给自己位置加声音光圈
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                }
                            } else {
                                // 没有任何人说话，统统关闭
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
        if (user_type == 1) mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);//1是主播，
        else mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);//2是观众
        /*mRtcEngine.setDefaultAudioRoutetoSpeakerphone(false);//直播模式下默认启用扬声器播放
        mRtcEngine.setAudioProfile(5,1);
        mRtcEngine.adjustPlaybackSignalVolume(400);*/
        mRtcEngine.joinChannel("", uid, "OpenVCall", UserManager.getUser().getUserId());
        mRtcEngine.enableAudioVolumeIndication(1000, 3);//监听远端说话
        mRtcEngine.adjustAudioMixingPlayoutVolume(10);// 设置混音音量，设置本地用户听到的音乐文件音量
        mRtcEngine.adjustPlaybackSignalVolume(150);// 设置播放信号音量,调节音量的参数值范围是 0 - 400，默认值 100 表示原始音量，即不对信号做缩放，400 表示原始音量的 4 倍
        mRtcEngine.adjustRecordingSignalVolume(100);// 设置录音信号音量,调节音量的参数值范围是 0 - 400，默认值 100 表示原始音量，即不对信号做缩放，400 表示原始音量的 4 倍
        mRtcEngine.adjustAudioMixingPublishVolume(10);// 设置混音音量，设置远端用户听到的音乐文件音量，混音是指播放本地或者在线音乐文件，同时让频道内的其他人听到此音乐，调节混音音量的参数值范围是 0 - 100，默认值 100 表示原始文件音量，即不对信号做缩放
    }

    /**
     * 弹幕
     */
    private void initDanmu() {
        BarrageView.Options options = new BarrageView.Options()
                .setGravity(BarrageView.GRAVITY_TOP) // 设置弹幕的位置
                .setInterval(600)  // 设置弹幕的发送间隔
                .setSpeed(200, 29) // 设置速度和波动值
                .setModel(BarrageView.MODEL_COLLISION_DETECTION)     // 设置弹幕生成模式
                .setRepeat(1)// 循环播放 默认为1次 -1 为无限循环
                .setClick(true);// 设置弹幕是否可以点击
        mBarrageView.setOptions(options);

        mBarrageView.setAdapter(mBarrageViewAdapter = new BarrageAdapter<PushBean>(null, this) {
            @Override
            public BarrageViewHolder<PushBean> onCreateViewHolder(View root, int type) {
                return new DanMuViewHolder(root, AdminHomeActivity.this);// 返回自己创建的ViewHolder
            }

            @Override
            public int getItemLayout(PushBean barrageData) {
                return R.layout.danmu;// 返回自己设置的布局文件
            }
        });

        // 设置监听器
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
     * 获取房间在线人数
     */
    private void loadData() {
        RxUtils.loading(commonModel.getRoomUsers(uid), this)
                .subscribe(new ErrorHandleSubscriber<AdminUser>(mErrorHandler) {
                    @Override
                    public void onNext(AdminUser adminUser) {
                        onlinePepole.setText(adminUser.getData().getVisitor().size() + "人");
                    }
                });
    }

    //公屏中最前面的名字被点击（第二个名字为userInfo中的userid）
    public void setFirstNameClick(int position) {
        try {
            if (String.valueOf(UserManager.getUser().getUserId()).equals(roomMessageAdapter.getData().get(position).getUser_id())) {
                // 点击到了自己的名字
                setMyDataDialog(roomMessageAdapter.getData().get(position).getUser_id() + "");
            } else {
                if (user_type == 1 || user_type == 2) {
                    if (mMicrophone != null) {
                        String selectId = roomMessageAdapter.getData().get(position).getUser_id();
                        int m = 0;
                        for (int i = 0; i < mMicrophone.size(); i++) {
                            String userIds = mMicrophone.get(i).getUser_id();
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//是麦上用户
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
                    // 点击到了别人的名字
                    setOtherDataDialog(roomMessageAdapter.getData().get(position).getUser_id());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //公屏中最前面的名字被点击（第二个名字为userInfo中的userid）
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
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//是麦上用户
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

    //公屏中最前面的名字被点击（第二个名字为userInfo中的userid）
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
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//是麦上用户
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

    //公屏中最前面的名字被点击（第二个名字为toUserId）
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
                            if (!TextUtils.isEmpty(userIds) && TextUtils.equals(selectId, userIds)) {//是麦上用户
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
     * 保活
     */
    private void startKeepLiveService() {
        // 保活前，先杀掉之前的保活
        stopkeepLiveService();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            startForegroundService(new Intent(AdminHomeActivity.this, RoomPlayService.class));
        } else {
            startService(new Intent(AdminHomeActivity.this, RoomPlayService.class));
        }
    }

    /**
     * 停止保活
     */
    private void stopkeepLiveService() {
        // service是否正在运行
        boolean isStartService = MyUtil.isServiceExisted(this, "com.konglianyuyin.mx.app.service.RoomPlayService");
        if (isStartService) {
            Intent stopIntent = new Intent(this, RoomPlayService.class);
            stopService(stopIntent);
        }
    }

    /**
     * 显示麦位说话的光圈
     */
    private void showQuan(int position, int volume, int color) {
        if (mWaveView1 == null || mWaveView2 == null || mWaveView3 == null || mWaveView4 == null || mWaveView5 == null
                || mWaveView6 == null || mWaveView7 == null || mWaveView8 == null || mWaveViewZhu == null) {
            return;
        }
        if (color == 0) color = getResources().getColor(R.color.translant);
        float radius = (float) Math.log10(Math.max(1, volume)) * QMUIDisplayHelper.dpToPx(25);
        LogUtils.debugInfo("voiceDb==" + volume + "radius ==" + radius + "半径等于=" + QMUIDisplayHelper.dpToPx(25));
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
//                LogUtils.debugInfo("voiceDb=="+mVoice1+"radius =="+radius+"半径等于="+QMUIDisplayHelper.dpToPx(50));
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
            case 8://房主
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
     * 隐藏麦位说话的光圈
     *
     * @param position
     */
    private void stopQuan(int position) {
        if (mWaveView1 == null || mWaveView2 == null || mWaveView3 == null || mWaveView4 == null || mWaveView5 == null
                || mWaveView6 == null || mWaveView7 == null || mWaveView8 == null || mWaveViewZhu == null) {
            return;
        }
        int color = getResources().getColor(R.color.translant);
//        LogUtils.debugInfo("停止了===============");
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
     * 2.初始化声网im消息
     */
    private void initMessage() {
        try {
            // 1.声网sdk初始化实例化
            mRtmClient = RtmClient.createInstance(this, Api.AGORA_KEY, new RtmClientListener() {
                @Override
                public void onConnectionStateChanged(int state, int reason) {
                    Log.d(TAG, "on connection state changed to " + state + " reason: " + reason);
                    LogUtil.d(own + "connection state: " + state + " reason: " + reason);
                }

                @Override
                public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
                    //创建接收消息监听
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
                    if (msg.equals(nfgk184grdgdfggunaliyuanxiamai)) {//被管理员下麦
                        runOnUiThread(() -> {
                            loadVedioList();
                            mRtcEngine.stopAudioMixing();
                            forcedDownVedio();
                        });
                    } else if (msg.equals(nfgk184grdgdfggunaliyuanbimai)) {//被管理员闭麦
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

                    } else if (msg.equals(nfgk184grdgdfggunaliyuantichu)) {//被踢出房间
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast("您已经被踢出房间!");
                                mRtcEngine.stopAudioMixing();
                                isStart = false;
                                finish();
                            }
                        });
                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkBeiJinYan)) {//被禁言
                        runOnUiThread(() -> {
                            toast("您已经被禁言3分钟!");
                            mRtcEngine.stopAudioMixing();
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkkaimai)) {//解禁麦
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRtcEngine.enableLocalAudio(true);
                                imgBimai.setSelected(false);
                                isEditBimai = false;
                            }
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkShezhiguanliyuan)) {//设置管理
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgAdd.setVisibility(View.VISIBLE);
//                                        imgMusic.setVisibility(View.VISIBLE);
                                user_type = 2;
                            }
                        });

                    } else if (msg.equals(nfgk184grdgdfggdfghfhrthmkQuxiaoiguanliyuan)) {//取消管理
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgAdd.setVisibility(View.GONE);
                                imgMusic.setVisibility(View.GONE);
                                user_type = 5;
                            }
                        });
                    } else {//结为CP消息,第一次发送，不是第一次的话会走频道消息
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject object = new JSONObject(msg);
                                    if (object != null) {
                                        String msgType = object.getString("messageType");
                                        if (!TextUtils.isEmpty(msgType)) {
                                            if (TextUtils.equals("2", msgType)) {
                                                // 第一次收到结为CP消息，不是第一次的话会走频道消息
                                                String nickName = object.getString("nickName");
                                                String user_id = object.getString("user_id");
                                                String headimgurlss = object.getString("headimgurl");
                                                String nick_color = object.getString("nick_color");
                                                LoginData localUser = UserManager.getUser();

                                                RequestCPDialog requestCPDialog = new RequestCPDialog(AdminHomeActivity.this, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        switch (view.getId()) {
                                                            case R.id.tv_left://拒绝，发送点对点消息
                                                                RxUtils.loading(commonModel.handle_cp(localUser.getToken(), localUser.getUserId() + "",
                                                                                "2"), AdminHomeActivity.this)
                                                                        .subscribe(new ErrorHandleSubscriber<AgreeCpResult>(mErrorHandler) {
                                                                            @Override
                                                                            public void onNext(AgreeCpResult agreeCpResult) {
                                                                                toast("很遗憾，结为守护CP失败");
                                                                                String myUserName = localUser.getNickname();
                                                                                String messageType = "1";
                                                                                String cpType = "2";
                                                                                JsonObject rootObj = new JsonObject();
                                                                                rootObj.addProperty("nickName", myUserName);
                                                                                rootObj.addProperty("messageType", messageType);
                                                                                rootObj.addProperty("cpType", cpType);
                                                                                String str = rootObj.toString();
                                                                                LogUtil.d(own + "发送拒绝CP消息: " + str);
                                                                                sendPeerMessage(user_id, str);
                                                                            }
                                                                        });
                                                                break;
                                                            case R.id.tv_right://同意,发送点对点消息，频道消息
                                                                RxUtils.loading(commonModel.handle_cp(localUser.getToken(), localUser.getUserId() + "",
                                                                                "1"), AdminHomeActivity.this)
                                                                        .subscribe(new ErrorHandleSubscriber<AgreeCpResult>(mErrorHandler) {
                                                                            @Override
                                                                            public void onNext(AgreeCpResult agreeCpResult) {
                                                                                if (agreeCpResult != null && agreeCpResult.getData() != null) {
                                                                                    toast("哇哦，你与" + nickName + "结为守护CP啦");
                                                                                    String myUserNames = localUser.getNickname();
                                                                                    String messageTypes = "1";
                                                                                    String cpTypes = "1";
                                                                                    JsonObject rootObjs = new JsonObject();
                                                                                    rootObjs.addProperty("nickName", myUserNames);
                                                                                    rootObjs.addProperty("messageType", messageTypes);
                                                                                    rootObjs.addProperty("cpType", cpTypes);
                                                                                    String strs = rootObjs.toString();
                                                                                    //发送点对点消息，通知对方结为CP了
                                                                                    LogUtil.d(own + "发送结为CP点对点消息: " + strs);
                                                                                    sendPeerMessage(user_id, strs);

                                                                                    MessageBean messageBean = new MessageBean();
                                                                                    messageBean.setMessageType("11");
                                                                                    messageBean.setNickName(myUserNames);
                                                                                    messageBean.nick_color = vipBean.getData().getNick_color();//CP颜色
                                                                                    messageBean.setUser_id(localUser.getUserId() + "");
                                                                                    messageBean.headimgurl = localUser.getHeadimgurl();
                                                                                    messageBean.toUser_id = user_id + "";
                                                                                    messageBean.toNickName = nickName;
                                                                                    messageBean.toNick_color = nick_color;//CP颜色
                                                                                    messageBean.toheadimgurl = headimgurlss;
                                                                                    String jsons = JSON.toJSONString(messageBean);

                                                                                    //更新自己的公屏
                                                                                    roomMessageAdapter.getData().add(messageBean);
                                                                                    roomMessageAdapter.notifyDataSetChanged();
                                                                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                                                                    //发送结为CP的频道消息
                                                                                    LogUtil.d(own + "发送结为CP频道消息: " + jsons);
                                                                                    sendChannelMessage(jsons);

                                                                                    //发送结为CP的频道消息
//                                                                        sendChannelMessage(BaseUtils.getJson("11", nickName+"与"+myUserNames+"结为守护CP",
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
                                                //对方结为CP的回应
                                                String cType = object.getString("cpType");
                                                if (TextUtils.equals("2", cType)) {
                                                    toast("很遗憾，结为守护CP失败");  // 拒绝了CP
                                                } else {
                                                    String nickName = object.getString("nickName");
                                                    toast("哇哦，你与" + nickName + "结为守护CP啦");
                                                }
                                            } else if (TextUtils.equals("8", msgType)) {
                                                // 收到了CP进入房间的消息
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

            //2 声网sdk登录
            mRtmClient.login(null, String.valueOf(UserManager.getUser().getUserId()), new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtils.debugInfo("====登录消息成功");
                    LogUtil.d(own + "im login success!");
                    joinChanalMessage();
                    joinChanalMessage2();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    LogUtils.debugInfo("====登录消息失败");
                    LogUtils.debugInfo(own + "im login err " + errorInfo.getErrorCode() + "_" + errorInfo.getErrorDescription());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("You need to check the RTM init process.");
        }
    }

    /**
     * 根据礼物地址播放后台礼物
     *
     * @param parser
     * @param giftUrl
     * @param svgaImageView
     */
    public void showServerSVG(SVGAParser parser, String giftUrl, SVGAImageView svgaImageView) {
        boolean closeGif = (boolean) SharedPreferencesUtils.getParam(this, "SHOWGIF", false);
        if (closeGif) return;
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
     * 3.创建消息频道
     */
    private void joinChanalMessage() {
        LogUtil.d(own + "创建消息频道 joinChanalMessage");
        // 3创建频道消息，
        try {
            mRtmChannel = mRtmClient.createChannel(uid, new RtmChannelListener() {
                @Override
                public void onMessageReceived(RtmMessage message, RtmChannelMember fromMember) {
                    LogUtil.d(own + "onMessageReceived: user - " + fromMember.getUserId());
                    LogUtil.d(own + "onMessageReceived: msg - " + message.getText());
                    //TODO  1：正常的 聊天消息  2：进入房间  3： 刷新麦序列表
                    // 4 ：礼物消息  5 ：表情消息 6：清空消息 7房间设置
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String account = fromMember.getUserId();
                            String msg = message.getText();
                            LogUtils.debugInfo("====接收的id" + account + "接收的消息： = " + msg);
                            MessageBean messageBean = BaseUtils.getMessageBean(msg);
                            if (TextUtils.equals(messageBean.getMessageType(), "3")) {
                                // 3麦序的操作
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "2")) {
                                // 2进入房间
                                if (mHasCPAtRoom) {
                                    // 有CP在房间，就过滤掉自己进入房间的消息
                                    return;
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {
                                    // 有VIP特效
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {
                                    // 房主进来
                                    textLayout.setVisibility(View.GONE);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "1")) {
                                // 1正常消息
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "6")) {
                                // 6管理员清空消息
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "7")) {
                                // 7房间修改设置
                                loadEnterRoom();    // 加载一次进入的房间信息
                                String gongGao = messageBean.getRoom_intro();
                                if (!TextUtils.equals(mStringGongGao, gongGao)) {//公告变了
                                    LogUtils.debugInfo("公告变了");
                                    mStringGongGao = gongGao;
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    LogUtils.debugInfo("公告没变");
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "5")) {
                                // 5表情消息
                                String emoji = messageBean.getEmoji();
                                if (TextUtils.equals(uid, account)) {
                                    imgRoomGif.setVisibility(View.VISIBLE);
                                    loadOneTimeGif(AdminHomeActivity.this, imgRoomGif, emoji, ()
                                            -> imgRoomGif.setVisibility(View.GONE));
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {
                                        // 代表表情有结果
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                } else {
                                    int maiPosition = 0;//麦序位置
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
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//代表表情有结果
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "401")) {
                                enterRoomRefrash();
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "4")) {
                                // 礼物消息
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
                                // 播放动画特效
                                if (TextUtils.equals(messageBean.type, "2")) {
                                    //全屏动画特效
                                    try {
                                        SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
                                        showServerSVG(parser, messageBean.show_gif_img, svgImage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    // 广播关闭礼物弹窗
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setStateMessage(StateMessage.CLOSE_GIFT_WINDOW);
                                    EventBus.getDefault().post(messageEvent);
                                } else if (TextUtils.equals(messageBean.type, "1")) {
                                    // 头像动画
                                    loadAniData(messageBean.userInfo, messageBean.show_img);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "11")) {
                                // 11同意结为CP，在聊天区域提示XXX与XX结为守护CP；
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "8")) {
                                // 8CP同时在房间
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {
                                    // 房主进来
                                    textLayout.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(messageBean.cp_tx)) {
                                    // 有同房特效
                                    playCpTongFangTX(messageBean.cp_tx, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {
                                    // 有VIP特效
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "12")) {
                                // 12联手上麦
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (!TextUtils.isEmpty(messageBean.cp_xssm)) {
                                    // 有上麦特效
                                    playCpTX(messageBean.cp_xssm, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "13")) {
                                if (is_show_egg){
                                    // 13开宝箱
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
                                // 15收到请求上麦消息
                                if (user_type == 1) {//自己是房主,添加申请上麦列表更新的红点，刷新申请上麦列表数据
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
                            LogUtils.debugInfo("====成员加入消息");
                            LogUtil.d(own + "成员加入消息");
                        }
                    });
                }

                @Override
                public void onMemberLeft(RtmChannelMember rtmChannelMember) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String userId = rtmChannelMember.getUserId();
                            LogUtil.d(own + "成员离开消息");
                            //只要有成员离开了，就需要判断是否其他人刷新麦序列表
                            try {
                                for (Microphone.DataBean.MicrophoneBean list : mMicrophone) {
                                    if (list != null) {
                                        if (TextUtils.equals(list.getUser_id(), userId)) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    loadVedioList();    // 刷新坐席位
                                                }
                                            }, 1000);
                                            break;
                                        }
                                    }
                                }
                                if (userId.equals(uid)) {
                                    // 房主离开，显示离开标记
                                    textLayout.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {

                            }
                        }
                    });
                }
            });
        } catch (RuntimeException e) {
            LogUtils.debugInfo("====创建消息频道失败");
            LogUtils.debugInfo(own + "创建消息频道失败: " + e.getMessage());
        }
        // 4加入
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                LogUtils.debugInfo("====加入频道消息成功");
                LogUtil.d("====加入频道消息成功");
                //进入房间的通知
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (vipBean != null && vipBean.getData() != null) {
                            List<VipBean.DataBean.CpUsersBean> cp_user_list = vipBean.getData().getCp_users();//在房间的cp列表
                            LoginData loginData = UserManager.getUser();
                            if (!TextUtils.isEmpty(vipBean.getData().getVip_tx())) {//vip进场特效
                                playVIPTX(vipBean.getData().getVip_tx(), loginData.getNickname());
                            }

                            if (cp_user_list != null && cp_user_list.size() > 0) {
                                // 看CP是否在房间里面
                                for (VipBean.DataBean.CpUsersBean cpUsersBean : cp_user_list) {
//                                                            mHasCPAtRoom = true;
                                    //先刷新自己的公屏
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
                                    newMessage.cp_tx = cpUsersBean.getCp_tx();//CP特效

                                    if (!TextUtils.isEmpty(cpUsersBean.getCp_tx())) {//播放CP同房特效
                                        playCpTongFangTX(cpUsersBean.getCp_tx(), loginData.getNickname(), loginData.getHeadimgurl(), newMessage.toNickName, newMessage.toheadimgurl);
                                    }

//                                    newMessage.setMessageType("10");

//                                    roomMessageAdapter.getData().add(newMessage);

                                    //发送给CP同伴
                                    newMessage.setMessageType("8");
                                    String jsonStr = JSON.toJSONString(newMessage);
                                    Log.e("进入房间通知CP", jsonStr);
                                    LogUtil.d(own + "进入房间通知CP: " + jsonStr);
                                    sendPeerMessage(cpUsersBean.getId(), jsonStr);

                                    //发送频道消息8（播放同房特效,公屏显示守护CP %@ 和 %@ 同在房间）
                                    sendChannelMessage(jsonStr);
                                }
//                                roomMessageAdapter.notifyDataSetChanged();
//                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else {
                                //更新自己的公屏
//                                MessageBean messageBean = new MessageBean();
//                                messageBean.setMessageType("2");
//                                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
//                                messageBean.setNickName(loginData.getNickname());
//                                messageBean.nick_color = vipBean.getData().getNick_color();
//                                listMessage.add(messageBean);
//                                roomMessageAdapter.setNewData(listMessage);
                                sendEnterRoom();
                            }

                            //判断一下是否是房主，是否去除离开的标识
                            if (TextUtils.equals(UserManager.getUser().getUserId() + "", uid)) {
                                //离开的标志
                                textLayout.setVisibility(View.GONE);
                            }
                        } else {
                            sendChannelMessage(BaseUtils.getJson("2", "进入直播间",
                                    UserManager.getUser().getNickname(),
                                    String.valueOf(UserManager.getUser().getUserId()), "", ""));
                        }
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.debugInfo("====加入频道消息失败");
                LogUtils.debugInfo(own + "加入频道消息失败: " + errorInfo.getErrorDescription());
            }
        });
    }

    // 房主身份，刷新申请上麦的列表数据
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
                            LogUtils.debugInfo("====接收的id" + account + "接收的消息： = " + msg);
                            MessageBean messageBean = BaseUtils.getMessageBean(msg);
                            if (TextUtils.equals(messageBean.getMessageType(), "3")) {//麦序的操作
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "2")) {//进入房间
                                if (mHasCPAtRoom) {//有CP在房间，就过滤掉自己进入房间的消息
                                    return;
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {//有VIP特效
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
//                                roomMessageAdapter.
                                if (uid.equals(account)) {//房主进来
                                    textLayout.setVisibility(View.GONE);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "1")) {//正常消息
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                            } else if (TextUtils.equals(messageBean.getMessageType(), "6")) {//管理员清空消息
                                roomMessageAdapter.getData().clear();
                                roomMessageAdapter.notifyDataSetChanged();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "7")) {//房间修改设置
                                loadEnterRoom();
                                String gongGao = messageBean.getRoom_intro();
                                if (!TextUtils.equals(mStringGongGao, gongGao)) {//公告变了
                                    LogUtils.debugInfo("公告变了");
                                    mStringGongGao = gongGao;
                                    roomMessageAdapter.getData().add(messageBean);
                                    roomMessageAdapter.notifyDataSetChanged();
                                    recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                } else {
                                    LogUtils.debugInfo("公告没变");
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "5")) {//表情消息
                                String emoji = messageBean.getEmoji();
                                if (TextUtils.equals(uid, account)) {
                                    imgRoomGif.setVisibility(View.VISIBLE);
                                    loadOneTimeGif(AdminHomeActivity.this, imgRoomGif, emoji, ()
                                            -> imgRoomGif.setVisibility(View.GONE));
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//代表表情有结果
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                } else {
                                    int maiPosition = 0;//麦序位置
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
                                    if (!TextUtils.equals(messageBean.getIs_answer(), "0")) {//代表表情有结果
                                        roomMessageAdapter.getData().add(messageBean);
                                        roomMessageAdapter.notifyDataSetChanged();
                                        recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                    }
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "401")) {
                                enterRoomRefrash();
                                loadVedioList();
                            } else if (TextUtils.equals(messageBean.getMessageType(), "4")) {//礼物消息
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
                                //播放动画特效
                                if (TextUtils.equals(messageBean.type, "2")) {//全屏
                                    try {
                                        SVGAParser parser = new SVGAParser(AdminHomeActivity.this);
                                        showServerSVG(parser, messageBean.show_gif_img, svgImage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //广播关闭礼物弹窗
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setStateMessage(StateMessage.CLOSE_GIFT_WINDOW);
                                    EventBus.getDefault().post(messageEvent);
                                } else if (TextUtils.equals(messageBean.type, "1")) {//头像动画
                                    loadAniData(messageBean.userInfo, messageBean.show_img);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "11")) {//同意结为CP，在聊天区域提示XXX与XX结为守护CP；
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());

                            } else if (TextUtils.equals(messageBean.getMessageType(), "8")) {//CP同时在房间
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                if (uid.equals(account)) {//房主进来
                                    textLayout.setVisibility(View.GONE);
                                }
                                //播放同房特效
                                if (!TextUtils.isEmpty(messageBean.cp_tx)) {
                                    playCpTongFangTX(messageBean.cp_tx, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                                if (!TextUtils.isEmpty(messageBean.vip_tx)) {//有VIP特效
                                    playVIPTX(messageBean.vip_tx, messageBean.getNickName());
                                }

                            } else if (TextUtils.equals(messageBean.getMessageType(), "12")) {//联手上麦
                                roomMessageAdapter.getData().add(messageBean);
                                roomMessageAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                                //播放上麦特效
                                if (!TextUtils.isEmpty(messageBean.cp_xssm)) {
                                    playCpTX(messageBean.cp_xssm, messageBean.getNickName(), messageBean.headimgurl, messageBean.toNickName, messageBean.toheadimgurl);
                                }
                            } else if (TextUtils.equals(messageBean.getMessageType(), "13")) {//开宝箱
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
     * 播放cp同房特效
     *
     * @param cptx
     */
    private void playCpTongFangTX(String cptx, String nickName, String headUrl, String toNickName, String toHeadUrl) {
        if (mLayoutCpAllIn.getVisibility() == View.VISIBLE) mLayoutCpAllIn.setVisibility(View.GONE);
        mTvCpAllIn.setText("守护" + nickName + "与守护" + toNickName + "同在房间");
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
     * 播放cp携手上麦特效
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
     * 播放VIP进场特效
     *
     * @param txStr
     */
    private void playVIPTX(String txStr, String userName) {
        if (mLayoutVipEnter.getVisibility() == View.VISIBLE)
            mLayoutVipEnter.setVisibility(View.GONE);
        mTvVipEnter.setText(userName + "进入房间");
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTvVipEnter.measure(spec, spec);
        int measuredWidthTicketNum = mTvVipEnter.getMeasuredWidth();
        LogUtils.debugInfo("TextView宽度=======", measuredWidthTicketNum + "00000");
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
        sendChannelMessage(BaseUtils.getJson("2", "进入直播间", loginData.getNickname(), loginData.getUserId() + "", vipBean.getData()));
//        sendChannelMessage(BaseUtils.getJson("2", "进入直播间",
//                UserManager.getUser().getNickname(),
//                String.valueOf(UserManager.getUser().getUserId()),
//                vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color()));
    }

    /**
     * 设置播放礼物期间不能点击页面上的其它按钮
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
                        enterRoom = menterRoom;
                        // 刷新魅力值
                        meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
                        // 刷新活力值
                        textNum.setText(String.valueOf(enterRoom.getRoom_info().get(0).hot));
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        LogUtils.debugInfo("====onDestroy销毁了直播间");
        LogUtil.d(own + "声音房销毁了");
        //selfPosition = -1;
        isStart = false;
        layoutRoom();
        try {
            if (mRtcEngine != null) {
                mRtcEngine.stopAudioMixing();
                mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);//2是观众
            }
            //调用下麦的接口
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
                        Log.d("AA", "成功");
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
            //告诉首页隐藏悬浮窗
            EventBus.getDefault().post(new FirstEvent(enterRoom.getRoom_info().get(0).getRoom_cover(), Constant.XUANFUYINCANG));
            stopkeepLiveService();//停止service
        } catch (Exception e) {
            Log.d("AA", "有代码报错");
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
                // 点击查看申请上麦列表的弹窗
                popWantSpeakWindow();
                break;
            case R.id.imgPaimai:
                // 排麦
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
                // 加载礼物列表弹窗
                HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, Object info) {
                        GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                        GiftListBeanNew giftListBean = new GiftListBeanNew();
                        giftListBean.setData(bean);

                        if (mMicrophone != null) {
                            // 如果席位不为null
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
                    EventBus.getDefault().post(new FirstEvent("指定发送", Constant.FANHUIZHUYE, enterRoom));
                    moveTaskToBack(true);
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                    isTop = false;
                }
                break;
            case R.id.imgBiaoqing://表情
                loadEmoji();
                break;
            case R.id.imgRoom://厅主头像
                setRoomHeader();
                break;
            case R.id.viewTop://隐藏音乐
                llMusic.setVisibility(View.GONE);
                break;
            case R.id.viewEnmojiTop://隐藏表情
                rlEmoji.setVisibility(View.GONE);
                break;
            case R.id.imgRight:
                RoomGaoWindow roomGaoWindow = new RoomGaoWindow(this);
                roomGaoWindow.showAsDropDown(textId);
                if (TextUtils.isEmpty(enterRoom.getRoom_info().get(0).getRoom_intro())) {
                    roomGaoWindow.getTextDec().setText("暂无公告");
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
            case R.id.imgShangmai://上麦
                if (imgShangmai.isSelected()) {
                    goDownVedio(String.valueOf(UserManager.getUser().getUserId()));
                } else {//上麦
                    for (int i = 0; i < mMicrophone.size(); i++) {
                        if (mMicrophone.get(i).getStatus() == 1) {
                            requestUpMicrophone();
                            return;
                        }
                    }
                    ToastUtil.showToast(AdminHomeActivity.this, "无空余麦位");
                }
                break;
            case R.id.imgTing://开关声音
                //TODO enableLocalAudio	开关本地音频采集  muteAllRemoteAudioStreams--停止/恢复接收所有音频流
                if (imgTing.isSelected()) {
                    mRtcEngine.muteAllRemoteAudioStreams(false);
                    imgTing.setSelected(false);
                } else {
                    mRtcEngine.muteAllRemoteAudioStreams(true);
                    imgTing.setSelected(true);
                }
                break;
            case R.id.imgBimai://闭麦
                if (!isEditBimai) {
                    if (imgBimai.isSelected()) {
                        mRtcEngine.enableLocalAudio(true);
                        imgBimai.setSelected(false);
                    } else {
                        mRtcEngine.enableLocalAudio(false);
                        imgBimai.setSelected(true);
                    }
                } else {
                    toast("已经被管理员闭麦！");
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
                                // 清空全部魅力值
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
            case R.id.imgMessage://点击发消息
                sendUserData();
                break;
            case R.id.imgPaihang://排行
                Intent intent = new Intent(AdminHomeActivity.this, RoomRankActivityNew.class);
                intent.putExtra("id", enterRoom.getRoom_info().get(0).getUid());
                intent.putExtra("image", enterRoom.getRoom_info().get(0).back_img);
                intent.putExtra("is_show_num", user_type == 1 || user_type == 2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.imgCollection://收藏
                if (imgCollection.isSelected()) {
                    RxUtils.loading(commonModel.remove_mykeep(uid,
                                    String.valueOf(UserManager.getUser().getUserId())), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    toast("取消收藏");
                                    imgCollection.setSelected(false);
                                }
                            });
                } else {
                    RxUtils.loading(commonModel.room_mykeep(uid,
                                    String.valueOf(UserManager.getUser().getUserId())), this)
                            .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    toast("收藏成功");
                                    imgCollection.setSelected(true);
                                }
                            });
                }
                break;
            case R.id.textRight://更多
                RoomTopWindow roomTopWindow = new RoomTopWindow(this);
                roomTopWindow.showAsDropDown(textRight);
                roomTopWindow.getLlClose().setOnClickListener(v -> {
                    roomTopWindow.dismiss();
                    mRtcEngine.stopAudioMixing();
                    isStart = false;
                    finish();
                });
                roomTopWindow.getLlJubao().setOnClickListener(v -> {//举报
                    roomTopWindow.dismiss();
                    ReportWindow reportWindow = new ReportWindow(AdminHomeActivity.this);
                    reportWindow.showAtLocation(imgMessage, Gravity.BOTTOM, 0, 0);
                });
                roomTopWindow.getLlTeXiao().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean closeGif = (boolean) SharedPreferencesUtils.getParam(AdminHomeActivity.this, "SHOWGIF", false);
                        if (closeGif) {
                            SharedPreferencesUtils.setParam(AdminHomeActivity.this, "SHOWGIF", false);
                        } else {
                            SharedPreferencesUtils.setParam(AdminHomeActivity.this, "SHOWGIF", true);
                        }

                        roomTopWindow.dismiss();
                    }
                });
                roomTopWindow.getLlShare().setOnClickListener(v -> {
                    roomTopWindow.dismiss();
                    UMWeb web = new UMWeb("http://app.suiliao888.cn/bc8m");
                    web.setTitle("大大");//标题
                    web.setDescription("快来加入"+getResources().getString(R.string.app_name)+"直播啦！");//描述

                    ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
                    config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);//设置位置
//                    config.setMenuItemBackgroundShape(getResources().getColor(R.drawable.shape_home_round));
                    config.setCancelButtonVisibility(true);
                    config.setTitleText("分享至");
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
                GemStoneDialogNew gemStoneDialog = new GemStoneDialogNew(AdminHomeActivity.this, commonModel, mErrorHandler);
                gemStoneDialog.show();
//                MessageDialog.show(AdminHomeActivity.this,"","1、开宝箱需要优先购买钥匙，钥匙10红钻/ 把;\n2、可以单次开锁宝箱，也可以10次、100 次;\n3、宝箱分为两种普通宝箱和守护宝箱，守护 宝箱每天定时定点开启;\n4、宝箱中的神秘礼物定期会更新，赠送和收 到神秘礼物的双方若已经开通守护点数，则会 提升两人的守护值;\n5、宝箱中开到的礼物会自动加入背包列表， 可以随时使用;\n6、可以在中奖记录中查看最近一周的开奖记 录;\n7、如有问题，请截图保存并与Mini官方客服 联系;",null);
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
     * 清空全部魅力值
     */
    private void clearAllMlZ() {
        RxUtils.loading(commonModel.clearAllMlZ(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", uid), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        if (microphone.getCode() == 1) {
                            // 清除全部魅力值成功，刷新房间座位列表
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
        // 点击查看申请上麦的弹窗列表
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
            ToastUtil.showToast(this, "当前无人申请上麦");
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
                                if (mMicrophone.get(i).getStatus() == 1) {//可以上的麦序
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
     * 聊天发消息
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
                                showToast("请输入内容！");
                            }
                        });
                    }
                });
    }

    /**
     * 管理员点击麦位的操作
     */
    private void setEditMaiwei(int position) {
        switch (mMicrophone.get(position).getStatus()) {
            case 1://可以上麦
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
                textBaoren.setOnClickListener(v -> {//操作用户上下麦
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

                                            List<MicUserBean> micUserBeanList = dataBean.getMic_user();//麦上

                                            List<MicUserBean> roomUserBeanList = dataBean.getRoom_user();//麦下

                                            List<MicUserBean> seaUserBeanList = dataBean.getSea_user();//搜索


                                            if (micUserBeanList == null) {
                                                micUserBeanList = new ArrayList<>();
                                            }
                                            if (roomUserBeanList == null) {
                                                roomUserBeanList = new ArrayList<>();
                                            }

                                            int micUpUsersLine = micUserBeanList.size();//麦上用户在线数目

                                            int micUpUsers = 8;//麦上用户数目

                                            int micDownUsers = roomUserBeanList.size();

                                            //麦上title
                                            RoomMultipleItem roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.TITLE_MIC_UP, new MicUserBean());

                                            roomMultipleItemList.add(roomMultipleItem);

                                            //麦上
                                            MicUserBean micUserBean;

                                            for (int i = 0; i < micUserBeanList.size(); i++) {

                                                micUserBean = micUserBeanList.get(i);

                                                roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.MIC_UP, micUserBean);

                                                roomMultipleItemList.add(roomMultipleItem);


                                            }

                                            //麦下的title
                                            roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.TITLE_MIC_DOWN, new MicUserBean());

                                            roomMultipleItemList.add(roomMultipleItem);


                                            //麦下
                                            for (int i = 0; i < roomUserBeanList.size(); i++) {

                                                micUserBean = roomUserBeanList.get(i);

                                                roomMultipleItem = new RoomMultipleItem(RoomMultipleItem.MIC_DOWN, micUserBean);

                                                roomMultipleItemList.add(roomMultipleItem);

                                            }

                                            SelectPeopleUpVideoDialog selectPeopleUpVideoDialog = new SelectPeopleUpVideoDialog(AdminHomeActivity.this, position, new SelectPeopleUpVideoDialog.OnOperationMicListener() {
                                                @Override
                                                public void toUpMic(int position, String userId) {//管理员操作上麦
                                                    upEditVedio(position, userId);
                                                }

                                                @Override
                                                public void toDownMic(String userId) {//管理员操作下麦
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
//                            .title("抱麦")
//                            .content("输入用户id")
//                            .inputType(InputType.TYPE_CLASS_TEXT)
//                            .input("输入用户id", null, new MaterialDialog.InputCallback() {
//                                @Override
//                                public void onInput(MaterialDialog dialog, CharSequence input) {
//                                    String trim = input.toString().trim();
//                                    if (!TextUtils.isEmpty(trim)) {
//                                        upEditVedio(position, trim);
//                                    } else {
//                                        showToast("请输入用户id");
//                                    }
//                                }
//                            })
//                            .positiveText("确定")
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
            case 2://麦上有人
                if ((UserManager.getUser().getUserId() + "").
                        equals(mMicrophone.get(position).getUser_id())) {//点击自己
                    setMyDataDialog(mMicrophone.get(position).getUser_id());
                    return;
                }
                setVedioDialog(position);
                break;
            case 3:
                new MaterialDialog.Builder(this)
                        .title("开放当前麦位")
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
                        .positiveText("确认")
                        .negativeText("取消")
                        .show();
                break;
            default:
        }
    }

    /**
     * cp联手上麦
     */
    private void sendCPAtVideo(UpVideoResult upVideoResult) {
        if (upVideoResult.getData() != null && upVideoResult.getData().getUser() != null && upVideoResult.getData().getCp() != null && upVideoResult.getData().getCp().size() > 0) {//有CP
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
                newMessage.cp_xssm = itemBean.getCp_xssm();//CP特效
                newMessage.setMessageType("12");

                //刷新本地列表
                roomMessageAdapter.getData().add(newMessage);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());

                if (!TextUtils.isEmpty(itemBean.getCp_xssm())) {//播放CP特效
                    //播放上麦特效
                    playCpTX(itemBean.getCp_xssm(), userBean.getNickname(), userBean.getHeadimgurl(), itemBean.getNickname(), itemBean.getHeadimgurl());
                }

                String strs = JSON.toJSONString(newMessage);
                //发送频道消息
                sendChannelMessage(strs);

            }


        }

    }

    /**
     * 自己上麦操作
     */
    private void upDownVedio(int position) {
        switch (mMicrophone.get(position).getStatus()) {
            case 1://可以上麦
                if (ExtConfig.isMicrophoneRequest) {
                    //发送上麦请求
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
                showToast("该麦序已锁定！");
                break;
            default:
        }
    }

    /**
     * 自己下麦的操作
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
     * 被迫下麦
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
     * 退出房间的下麦操作
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
     * 管理员操作用户上麦
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
     * 管理员操作用户下麦
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
     * 自己点自己头像的弹窗,包括消息列表点击自己名字
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

                        //添加自己给自己清魅力，自己给自己送礼
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
     * 点击厅主头像
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

                            //添加自己给自己清魅力，自己给自己送礼
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
     * 普通用户点击普通用户的弹窗显示
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

                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "已关注" : "关注");
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

                        //转赠
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
                            if (TextUtils.equals("关注", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("关注成功");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("取消关注成功");
                            }
                        });
                    }
                });
    }

    /**
     * 管理员点击厅主的弹窗显示
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
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "已关注" : "关注");
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
                            if (TextUtils.equals("关注", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("关注成功");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("取消关注成功");
                            }
                        });
                    }
                });
    }

    /**
     * 管理员点击发言用户的弹窗显示
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
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "已关注" : "关注");
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
                                    .title("确定要把Ta踢出房间么？")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("确认")
                                    .negativeText("取消")
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
                            if (TextUtils.equals("关注", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("关注成功");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("取消关注成功");
                            }
                        });
                    }
                });
    }

    /**
     * 管理员点击麦上用户弹窗
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
                        textDialogBimai.setText(otherUser.getData().get(0).getIs_sound() == 1 ? "闭麦" : "开麦");
                        //TODO 关注的状态
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "已关注" : "关注");
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
                            if (TextUtils.equals(textDialogBimai.getText(), "闭麦")) {
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
                                    .title("确定要把Ta踢出房间么？")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("确认")
                                    .negativeText("取消")
                                    .show();
                        });
                        textDialogGuanzhu.setOnClickListener(v -> {
                            String text = textDialogGuanzhu.getText().toString();
                            if (TextUtils.equals("关注", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("关注成功");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("取消关注成功");
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
     * 管理员点击麦上用户弹窗
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
                        textDialogBimai.setText(otherUser.getData().get(0).getIs_sound() == 1 ? "闭麦" : "开麦");
                        //TODO 关注的状态
                        textDialogGuanzhu.setText(otherUser.getData().get(0).is_follows == 1 ? "已关注" : "关注");
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
                            if (TextUtils.equals(textDialogBimai.getText(), "闭麦")) {
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
                                    .title("确定要把Ta踢出房间么？")
                                    .content("")
                                    .onPositive((dialog1, which) -> {
                                        editTichu(otherUser.getData().get(0).getId() + "");
                                        roomDialog.dismiss();
                                    })
                                    .positiveText("确认")
                                    .negativeText("取消")
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
                            if (TextUtils.equals("关注", text)) {
                                fllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("关注成功");
                            } else {
                                cancelFllow(String.valueOf(otherUser.getData().get(0).getId()), textDialogGuanzhu);
                                roomDialog.dismiss();
                                toast("取消关注成功");
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
     * 点击按钮送礼
     */
    private void loadSongLi(String fromUserId, String nickName, String headerUrl) {
        HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, Object info) {
                GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                GiftListBeanNew giftListBean = new GiftListBeanNew();
                giftListBean.setData(bean);
                LogUtils.debugInfo("显示礼物弹框");
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
     * 管理员踢出房间 --- 走下麦接口，踢出
     */
    private void editTichu(String user_id) {
        RxUtils.loading(commonModel.out_room(uid, user_id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                        sendPeerMessage(user_id, nfgk184grdgdfggunaliyuantichu);
                        loadVedioList();
                    }
                });
    }

    /**
     * 管理员禁言
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
     * 管理员闭麦麦上用户
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
     * 管理员解禁麦麦上用户
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
     * 管理员抱人上麦
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
     * 个人离开房间，只个人走
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
     * 关注
     */
    private void fllow(String id, TextView textView) {
        RxUtils.loading(commonModel.follow(String.valueOf(UserManager.getUser().getUserId()), id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        textView.setText("已关注");
                        EventBus.getDefault().post(new FirstEvent("指定发送", Constant.SHUAXINGUANZHU));
                    }
                });
    }

    /**
     * 取消关注
     */
    private void cancelFllow(String id, TextView textView) {
        RxUtils.loading(commonModel.cancel_follow(String.valueOf(UserManager.getUser().getUserId()), id), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean microphone) {
                        textView.setText("关注");
                        EventBus.getDefault().post(new FirstEvent("指定发送", Constant.SHUAXINGUANZHU));
                    }
                });
    }

    /**
     * 发送频道消息
     */
    public void sendChannelMessage(String msg) {
        try {
            RtmMessage message = mRtmClient.createMessage();
            message.setText(msg);
            mRtmChannel.sendMessage(message, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtils.debugInfo("====发送消息成功");
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
                    LogUtils.debugInfo("====发送消息成功");
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
     * 点对点消息
     * 传userid
     */
    public void sendPeerMessage(String dst, String message) {
        RtmMessage msg = mRtmClient.createMessage();
        msg.setText(message);

        mRtmClient.sendMessageToPeer(dst, msg, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtils.debugInfo("====发送点对点消息成功");
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
     * 加载音乐
     */
    private void loadMusic() {
        //加载音效
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
        imgXunhuan.setOnClickListener(v -> {//循环
            if (imgXunhuan.isSelected()) {
                imgXunhuan.setSelected(false);
                randomMusic = 0;//0代表顺序播放
                toast("当前是顺序播放");
            } else {
                imgXunhuan.setSelected(true);
                randomMusic = 1;
                toast("当前是随机播放");
            }
        });
        imgFront.setOnClickListener(v -> {//上一个
            if (listLocal.size() > 0) {
                if (randomMusic == 0) {
                    if (musicPosition == 0) {
                        toast("已经是第一个了！");
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
                toast("请去音乐库添加至我的音乐！");
            }
        });
        imgStop.setOnClickListener(v -> {//暂停
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
                    toast("请去音乐库添加至我的音乐！");
                }
            }
        });
        imgNext.setOnClickListener(v -> {//下一个
            if (listLocal.size() > 0) {
                if (randomMusic == 0) {
                    if (musicPosition == listLocal.size() - 1) {
                        toast("已经是最后一个了！");
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
                toast("请去音乐库添加至我的音乐！");
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
                handler.sendEmptyMessage(2);//拖动进度条结束
            }
        });
    }

    /**
     * 加载音效
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
     * 表情
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
                            int pageSize = 10;//每页10个
                            int size = emojiList.size();
                            int tem = size % pageSize;
                            int page = size / pageSize;
                            int pageIndex = page;

                            if (page == 0) {//小于1页
                                pageIndex = 1;
                            } else {//大于1页
                                if (tem != 0) {//不能被10出尽
                                    pageIndex = pageIndex + 1;
                                }
                            }
                            //添加fragment
                            EmojiFragment emojiFragment;
                            for (int i = 0; i < pageIndex; i++) {
                                int length = i * pageSize + pageSize;
                                //将总list拆分成页
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
                        toast("请重新请求数据");
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
     * 主页悬浮窗关闭yinpin
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
                EventBus.getDefault().post(new FirstEvent("指定发送",
                        Constant.FANHUIZHUYE, enterRoom));
                ArmsUtils.startActivity(MainActivity.class);
                moveTaskToBack(true);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                llMusic.setVisibility(View.GONE);
                rlEmoji.setVisibility(View.GONE);
                isTop = false;
            } else {
                EventBus.getDefault().post(new FirstEvent("指定发送",
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
                Log.d("=====", "断开连接222");
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
                    //message就是接收到的消息
                    AdminHomeActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            //message就是接收到的消息
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
                            Log.d("=====", "onClose：断开连接");
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
                            Log.d("=====", "onError：断开连接");
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
     * 外界获取，做对比
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
     * 设置编辑房间成功的回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (FANGJIANSHEZHI.equals(tag)) {//发广播，刷新
//            loadEnterRoom();
            RxUtils.loading(commonModel.enter_room(uid, room_pass,
                            String.valueOf(UserManager.getUser().getUserId())), this)
                    .subscribe(new ErrorHandleSubscriber<EnterRoom>(mErrorHandler) {
                        @Override
                        public void onNext(EnterRoom menterRoom) {
                            enterRoom = menterRoom;
                            meili11.setText(String.valueOf(enterRoom.getRoom_info().get(0).getMeili()));
                            textName.setText(enterRoom.getRoom_info().get(0).getRoom_name());
                            textId.setText("ID:" + enterRoom.getRoom_info().get(0).getUid());
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

                            if (!TextUtils.equals(mStringGongGao, currentGongGao)) {//公告变了
                                mStringGongGao = currentGongGao;
                                //刷新公屏显示公告
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

                            //发送广播通知用户设置
                            sendChannelMessage(BaseUtils.getJson("7", "",
                                    "", "", enterRoom.getRoom_info().get(0).getRoom_name(),
                                    enterRoom.getRoom_info().get(0).getRoom_type(),
                                    enterRoom.getRoom_info().get(0).back_img,
                                    enterRoom.getRoom_info().get(0).getRoom_intro()));
                        }
                    });
        } else if (SHEZHIGUANLI.equals(tag)) {//设置管理
            String userId = event.getMsg();
            sendPeerMessage(userId, nfgk184grdgdfggdfghfhrthmkShezhiguanliyuan);
        } else if (QuxiaoGUANLI.equals(tag)) {//取消管理
            String userId = event.getMsg();
            sendPeerMessage(userId, nfgk184grdgdfggdfghfhrthmkQuxiaoiguanliyuan);
        } else if (YINYUEZANTING.equals(tag)) {//暂停音乐
            String msg = event.getMsg();
            musicPosition = Integer.valueOf(msg);
            imgStop.setSelected(false);
            mRtcEngine.pauseAudioMixing();
        } else if (YINYUEBOFANG.equals(tag)) {//播放音乐
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
                toast("请去音乐库添加至我的音乐！");
            }
        } else if (DIANJIBIAOQING.equals(tag)) {//点击表情
            String msg = event.getMsg();
            rlEmoji.setVisibility(View.GONE);
            loadGifEmoji(msg);
        } else if (FASONGMAIXULIWU.equals(tag)) {//发送礼物
            enterRoomRefrash();
            loadVedioList();
//            GiftFlyDialog rmDialog = new GiftFlyDialog(AdminHomeActivity.this, R.layout.pop_gift_fly);
////            roomDialog.showAsDropooDown(imgMessage);

            MessageBean messageBean = event.getMessageBean();
            messageBean.nick_color = vipBean.getData().getNick_color();
            LogUtils.debugInfo("====发送礼物消息：" + JSON.toJSONString(messageBean));
            sendChannelMessage(JSON.toJSONString(messageBean));

            FirstEvent firstEvent = new FirstEvent("send_gift", "send_gift");//通知个人中心刷新数据
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
            if (TextUtils.equals(messageBean.type, "2")) {//全屏
                SVGAParser parser = new SVGAParser(this);
                showServerSVG(parser, messageBean.show_gif_img, svgImage);
            } else if (TextUtils.equals(messageBean.type, "1")) {//头像动画
                setFlyAnimate(messageBean);
//                loadAniData(messageBean.userInfo, messageBean.show_img);
            }
        } else if (XIUGAIGERENZILIAOCHENGGONG.equals(tag)) {//修改个人资料刷新名称信息
            if (user_type == 1) {
                loadEnterRoom();
            } else {
                sendChannelMessage(BaseUtils.getJson("3", "", "", ""));
                loadVedioList();
            }
        } else if (TUISONG.equals(tag)) {//推送消息，显示布局
            PushBean pushBean = event.getPushBean();
            if (!mIsPushRuning) {//没有运行
                mIsPushRuning = true;
                mPushBeanList.clear();
                mPushTimer.start();
                mPushBeanList.add(pushBean);
            } else {
                mPushBeanList.add(pushBean);
            }
            mBarrageView.postDelayed(() -> mBarrageViewAdapter.add(pushBean), 1000);
        } else if (KBXTUISONG.equals(tag)) { //开宝箱推送消息，显示布局
            PushBean pushBean = event.getPushBean();
            if (!mIsPushRuning) {//没有运行
                mIsPushRuning = true;
                mPushBeanList.clear();
                mPushTimer.start();
                mPushBeanList.add(pushBean);
            } else {
                mPushBeanList.add(pushBean);
            }
            mBarrageView.postDelayed(() -> mBarrageViewAdapter.add(pushBean), 1000);
        } else if (RECHARGE_FROM_HOME.equals(tag)) {
            popRechargeWindow();    // 充值
        }
    }

    private void popRechargeWindow() {
//        ToastUtil.showToast(mContext,"暂时无法充值");
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
                        // 将该app注册到微信
                        mWxApi.registerApp("wx7b991e43bc9b5814");
                        // 判断是否安装客户端
                        if (!mWxApi.isWXAppInstalled()) {
                            toast("请您先安装微信客户端！");
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
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(MessageEvent event) {
        StateMessage stateMessage = event.getStateMessage();
        if (stateMessage.getState() == StateMessage.SEND_GEMSTONE.getState()) { //发送宝石
            enterRoomRefrash();
            loadVedioList();
            Object[] objects = (Object[]) event.getObject();
            MessageBean messageBean = (MessageBean) objects[0];
            SendGemResult sendGemResult = (SendGemResult) objects[1];

            //根据返回的数据，判断是有不是第一次 送宝石的，只有第一次送宝石对方才会显示结为CP的对话框
            List<SendGemResult.DataBean> sendDataList = sendGemResult.getData();
            for (SendGemResult.DataBean dataItem : sendDataList) {
                if (dataItem.getIs_first() == 1) {//第一次发送
                    JsonObject rootObj = new JsonObject();
                    LoginData loginData = UserManager.getUser();
                    rootObj.addProperty("nickName", loginData.getNickname());
                    rootObj.addProperty("user_id", loginData.getUserId());
                    rootObj.addProperty("nick_color", vipBean.getData().getNick_color());
                    rootObj.addProperty("messageType", "2");
                    rootObj.addProperty("headimgurl", loginData.getHeadimgurl());
                    String str = rootObj.toString();
                    Log.e("第一次发送宝石==", str);
                    sendPeerMessage(dataItem.getUserId(), str);
                } else {//不是第一次发送宝石，就发送频道消息
                    messageBean.nick_color = vipBean.getData().getNick_color();
                    sendChannelMessage(JSON.toJSONString(messageBean));
                }
            }

            LogUtils.debugInfo("====发送宝石消息：" + JSON.toJSONString(messageBean));
//            sendChannelMessage(JSON.toJSONString(messageBean));

            FirstEvent firstEvent = new FirstEvent("send_gift", "send_gift");//通知个人中心刷新数据
            EventBus.getDefault().post(firstEvent);

            List<MessageBean.Data> userInfo = messageBean.userInfo;
            if (userInfo.size() == 1) {
                LogUtils.debugInfo("====单个人:" + userInfo.get(0).nickname);
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
            if (TextUtils.equals(messageBean.type, "2")) {//全屏
                SVGAParser parser = new SVGAParser(this);
                showServerSVG(parser, messageBean.show_gif_img, svgImage);
            } else if (TextUtils.equals(messageBean.type, "1")) {//头像动画
//                setFlyAnimate(messageBean);
                loadAniData(messageBean.userInfo, messageBean.show_img);
            }
        } else if (stateMessage.getState() == StateMessage.PEOPLE_OPEN_GEMSTONE.getState()) { //开宝箱的消息，类型13

            MessageBean messageBean = (MessageBean) event.getObject();

            if (!TextUtils.isEmpty(messageBean.getMessage()) || (messageBean.awardList != null && messageBean.awardList.size() > 0)) {
                roomMessageAdapter.getData().add(messageBean);
                roomMessageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(roomMessageAdapter.getData().size());
                //发送频道消息
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
            //发送频道消息
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
     * 发送礼物飞的动画
     */
    private void setFlyAnimate(MessageBean messageBean) {
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
            if (TextUtils.equals(list.userId, uid)) {//厅主
                imgRoom.post(() -> {

                    mGiftFlyDialog.startImageFly(imgRoom, imgUrl);

                });
            } else {
                for (Microphone.DataBean.MicrophoneBean listPhone : mMicrophone) {
                    if (TextUtils.equals(list.userId, listPhone.getUser_id())) {
                        LogUtils.debugInfo("====sgm礼物位置：" + list.userId);
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
     * 计算飞的距离
     */
    private void loadAniData(List<MessageBean.Data> userInfo, String imgUrl) {
        for (MessageBean.Data list : userInfo) {
            if (TextUtils.equals(list.userId, uid)) {//厅主
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
                    LogUtils.debugInfo("====飞2：" + location[0] + "====飞2" + location[1]);
                    startAnimotion(location[0] - feiLeft + 70, location[1] - feiTop + 150, imageView);
                });
            } else {
                for (Microphone.DataBean.MicrophoneBean listPhone : mMicrophone) {
                    if (TextUtils.equals(list.userId, listPhone.getUser_id())) {
                        LogUtils.debugInfo("====sgm礼物位置：" + list.userId);
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
     * 设置飞用户头像
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
     * 飞的动画
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
//                        0, 0);// 改变位置,这里是左右不变，上下平移height高度
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
                            //再改位置
//                            RelativeLayout.LayoutParams params2 =
//                                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                            RelativeLayout.LayoutParams.WRAP_CONTENT);
//                            params2.setMargins(left, top,
//                                    0, 0);// 改变位置,这里是左右不变，上下平移height高度
//                            imgVisiFei.setLayoutParams(params2);
                        }
                    }
                }, 1000);//延时1秒后隐藏

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //开始动画
        imgVisiFei.startAnimation(translateAnimation);
        translateAnimation.start();
    }

    /**
     * 动态表情
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
                                    "使用道具结果：" + gifBean.getData().get(0).getName(),
                                    loginData.getNickname(), loginData.getUserId() + "",
                                    gifBean.getData().get(0).getIs_answer(),
                                    gifBean.getData().get(0).getEmoji(), gifBean.getData().get(0).getT_length(),
                                    vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color()));
                        } else {
                            sendChannelMessage(BaseUtils.getJson("5",
                                    "使用道具结果：" + gifBean.getData().get(0).getName(),
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
                            int maiPosition = 0;//麦序位置
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
                        if (!TextUtils.equals(gifBean.getData().get(0).getIs_answer(), "0")) {//代表表情有结果
                            String json = "";
                            if (vipBean != null && vipBean.getData() != null) {
                                json = BaseUtils.getJson("5", "使用道具结果：" + gifBean.getData().get(0).getName(),
                                        loginData.getNickname(), loginData.getUserId() + "",
                                        gifBean.getData().get(0).getIs_answer(),
                                        gifBean.getData().get(0).getEmoji(),
                                        gifBean.getData().get(0).getT_length(),
                                        vipBean.getData().getVip_img(), vipBean.getData().getHz_img(), vipBean.getData().getNick_color());
                            } else {
                                json = BaseUtils.getJson("5", "使用道具结果：" + gifBean.getData().get(0).getName(),
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
     * 展示表情git效果
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
     * 广播用户,厅主修改房间设置
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
                        textId.setText("ID:" + enterRoom.getRoom_info().get(0).getUid());
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
            Toast.makeText(AdminHomeActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AdminHomeActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AdminHomeActivity.this, "取消了", Toast.LENGTH_LONG).show();

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