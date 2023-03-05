package com.konglianyuyin.mx.popup;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.BaoshiPagerAdapter;
import com.konglianyuyin.mx.adapter.BeibaoPagerAdapter;
import com.konglianyuyin.mx.adapter.GiftPagerAdapter;
import com.konglianyuyin.mx.adapter.GiftUserAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.GiftListBean;
import com.konglianyuyin.mx.bean.GiftListBeanNew;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.MessageEvent;
import com.konglianyuyin.mx.bean.Microphone;
import com.konglianyuyin.mx.bean.SendGemResult;
import com.konglianyuyin.mx.bean.StateMessage;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.http.HttpCallback;
import com.konglianyuyin.mx.http.HttpUtil;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Arith;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.DpUtil;
import com.konglianyuyin.mx.utils.FastJsonUtils;
import com.konglianyuyin.mx.utils.ScreenDimenUtil;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.konglianyuyin.mx.view.MyViewPager3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.khrystal.library.widget.CircularHorizontalMode;
import me.khrystal.library.widget.ItemViewMode;

import static com.konglianyuyin.mx.activity.room.AdminHomeActivity.mContext;


/**
 * 礼物弹窗,popupwindow和dialog不能嵌套viewpager使用，此处是一个巨坑，草特大爷
 */
@SuppressLint("ValidFragment")
public class GiftWindow extends PopupWindow implements GiftPagerAdapter.ActionListener, BaoshiPagerAdapter.ActionListener, BeibaoPagerAdapter.ActionListener {

    @BindView(R.id.user_recyclerview)
    RecyclerView userRecyclerview;
    @BindView(R.id.quanmai)
    ImageView quanmai;
    @BindView(R.id.toubu_one)
    LinearLayout toubuOne;
    @BindView(R.id.liwu)
    TextView liwu;
    @BindView(R.id.baoshi)
    TextView baoshi;
    @BindView(R.id.beibao)
    TextView beibao;
    @BindView(R.id.viewPager)
    MyViewPager3 viewPager;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.mizuan)
    TextView mizuan;
    @BindView(R.id.liwushuliang)
    TextView liwushuliang;
    @BindView(R.id.img_next)
    ImageView imgNext;
    @BindView(R.id.zengsong)
    TextView zengsong;
    @BindView(R.id.sendGiftAll)
    TextView sendGiftAll;
    @BindView(R.id.allPrice)
    TextView allPrice;

    private String mId = "";//礼物id

    public TextView getTextNum() {
        return liwushuliang;
    }

    private AdminHomeActivity context;
    private CommonModel commonModel;
    private ItemViewMode mItemViewMode;
    private LinearLayoutManager mLayoutManager;
    private List<Microphone.DataBean.MicrophoneBean> mMicrophone;   // 上麦用户列表数据
    private GiftListBeanNew giftListBean;
    private Microphone.DataBean.MicrophoneBean microphoneBean;      // 房主麦位
    private GiftUserAdapter giftUserAdapter;
    private int mPosition = -1;
    private ImageView imgGift;

    //    GiftAdapter mGiftAdapter;
    GiftPagerAdapter mGiftPagerAdapter;

    //    StoneGiftAdapter mStoneGiftAdapter;
    BaoshiPagerAdapter mStoneGiftAdapter;

    //    PackageGiftAdapter mPackageGiftAdapter;
    BeibaoPagerAdapter mPackageGiftAdapter;

    private int mCurrentGiftType = 0;
    private String fromUserId;

    private String mSelectGiftId = "";
    private String mSelectBaoShiId = "";
    private String mSelectBeiBaoId = "";

    public GiftWindow(AdminHomeActivity context,
                      List<Microphone.DataBean.MicrophoneBean> mMicrophone,
                      CommonModel commonModel, GiftListBeanNew giftListBean,
                      Microphone.DataBean.MicrophoneBean microphoneBean, ImageView imgGift) {
        super(context);
        this.context = context;
        this.mMicrophone = mMicrophone;
        this.commonModel = commonModel;
        this.giftListBean = giftListBean;
        this.microphoneBean = microphoneBean;
        this.imgGift = imgGift;
        initView();
    }

    public GiftWindow(AdminHomeActivity context,
                      List<Microphone.DataBean.MicrophoneBean> mMicrophone,
                      CommonModel commonModel, GiftListBeanNew giftListBean,
                      Microphone.DataBean.MicrophoneBean microphoneBean, ImageView imgGift, String fromUserId) {
        super(context);
        this.context = context;
        this.mMicrophone = mMicrophone;
        this.commonModel = commonModel;
        this.giftListBean = giftListBean;
        this.microphoneBean = microphoneBean;
        this.imgGift = imgGift;
        this.fromUserId = fromUserId;
        initView();
    }

    private void initUser(AdminHomeActivity context) {

        mItemViewMode = new CircularHorizontalMode();
        mLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        userRecyclerview.setLayoutManager(mLayoutManager);
//        userRecyclerview.setViewMode(mItemViewMode);
//        userRecyclerview.setNeedCenterForce(false);
//        userRecyclerview.setNeedLoop(false);
        List<Microphone.DataBean.MicrophoneBean> listNew = new ArrayList<>();

        if (fromUserId != null)
            microphoneBean.isSelect = fromUserId.equals(microphoneBean.getUser_id());
        else
            microphoneBean.isSelect = false;
        listNew.add(microphoneBean);
        //listNew.add(mMicrophone.get(mMicrophone.size() - 1));

        for (int i = 0; i < mMicrophone.size() - 1; i++) {
            if (fromUserId != null)
                mMicrophone.get(i).isSelect = fromUserId.equals(mMicrophone.get(i).getUser_id());
            else
                mMicrophone.get(i).isSelect = false;
            listNew.add(mMicrophone.get(i));
        }
        giftUserAdapter = new GiftUserAdapter(context, userRecyclerview,
                listNew);
        userRecyclerview.setAdapter(giftUserAdapter);
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.gift_layout_pop, null);
        ButterKnife.bind(this, view);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //设置透明
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        context.getWindow().setAttributes(params);

        EventBus.getDefault().register(this);

//
//        List<GiftListBeanNew.DataBean.MyWaresBean> myWaresBeanList = new ArrayList<>();

//        GiftListBeanNew.DataBean.MyWaresBean myWaresBean = new GiftListBeanNew.DataBean.MyWaresBean();
//        myWaresBean.setId("6");
//        myWaresBean.setName("iiiiii");
//        myWaresBean.setPrice("133");
//        myWaresBean.setImg("http://47.92.85.75/upload/gifts/png/gift_list_sgpp02@2x.png");
//        myWaresBean.setType(2);
//        myWaresBean.setShow_img("http://47.92.85.75/upload/gifts/png/gift_sgpp@2x.png");
//        myWaresBean.setShow_img2("http://47.92.85.75/upload/gifts/png/gift_sgpp@2x.png");
//        myWaresBean.setWares_type(2);
//        myWaresBean.setPrice_004(20);
//        myWaresBeanList.add(myWaresBean);
//        myWaresBeanList.add(myWaresBean);
//        myWaresBeanList.add(myWaresBean);
//        myWaresBeanList.add(myWaresBean);
//        this.giftListBean.getData().setMy_wares(myWaresBeanList);

        initUser(context);
        initBottomRecycler(context);
        giftNumberWindow = new GiftNumber2Window(context);
    }

    private void initBottomRecycler(AdminHomeActivity context) {
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (radioGroup != null) {
                    ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mizuan.setText(giftListBean.getData().getMizuan());
        mItemViewMode = new CircularHorizontalMode();
        mLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);


        liwu.setSelected(true);
        showGiftList();


    }


    private void showGiftList() {
        if (giftListBean.getData().getGifts() == null) {
            giftListBean.getData().setGifts(new ArrayList<>());
        }
        List<GiftListBeanNew.DataBean.GiftsBean> gifts = giftListBean.getData().getGifts();
        for (GiftListBeanNew.DataBean.GiftsBean gift : gifts) {
            gift.setChecked(false);
        }
        radioGroup.removeAllViews();
        mGiftPagerAdapter = new GiftPagerAdapter(mContext, gifts);
        mGiftPagerAdapter.setActionListener(this);
        viewPager.setAdapter(mGiftPagerAdapter);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0, size = mGiftPagerAdapter.getCount(); i < size; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_gift_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
    }

    private void showBaoshiList() {
        if (giftListBean.getData().getBaoshi() == null) {
            giftListBean.getData().setBaoshi(new ArrayList<>());
        }
        List<GiftListBeanNew.DataBean.BaoshiBean> gifts = giftListBean.getData().getBaoshi();
        for (GiftListBeanNew.DataBean.BaoshiBean gift : gifts) {
            gift.setChecked(false);
        }
        radioGroup.removeAllViews();
        mStoneGiftAdapter = new BaoshiPagerAdapter(mContext, gifts);
        mStoneGiftAdapter.setActionListener(this);
        viewPager.setAdapter(mStoneGiftAdapter);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0, size = mStoneGiftAdapter.getCount(); i < size; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_gift_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
    }

    private void showBeibaoList() {
        if (giftListBean.getData().getMy_wares() == null) {
            giftListBean.getData().setMy_wares(new ArrayList<>());
        }
        List<GiftListBeanNew.DataBean.MyWaresBean> gifts = giftListBean.getData().getMy_wares();
        int price = 0;
        for (GiftListBeanNew.DataBean.MyWaresBean gift : gifts) {
            int p = gift.getNum()*gift.getPrice_004();
            price = p + price;
        }
        allPrice.setText("总计："+price);

        for (int i = 0; i < gifts.size(); i++) {
            gifts.get(i).setChecked(mId.equals(gifts.get(i).getId()));
        }
        radioGroup.removeAllViews();
        mPackageGiftAdapter = new BeibaoPagerAdapter(mContext, gifts);
        mPackageGiftAdapter.setActionListener(this);
        viewPager.setAdapter(mPackageGiftAdapter);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0, size = mPackageGiftAdapter.getCount(); i < size; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_gift_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
    }

    private void ss() {
        mPosition = -1;
        HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, Object info) {
                GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                GiftListBeanNew giftListBean2 = new GiftListBeanNew();
                giftListBean2.setData(bean);
                giftListBean.getData().setMy_wares(giftListBean2.getData().getMy_wares());
                showBeibaoList();
            }
        });
    }

    private void updateBeiBaoData(String beiBaoId) {
        mId = beiBaoId;
        HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, Object info) {
                GiftListBeanNew.DataBean bean = JSON.parseObject(((com.alibaba.fastjson.JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                GiftListBeanNew giftListBean2 = new GiftListBeanNew();
                giftListBean2.setData(bean);
                giftListBean.getData().setMy_wares(giftListBean2.getData().getMy_wares());
                showBeibaoList();
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = (MyBaseArmActivity) context;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
        EventBus.getDefault().unregister(this);
    }

    //获取礼物列表
    private void getGiftList() {

        HttpUtil.getGiftList(String.valueOf(UserManager.getUser().getUserId()), new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, Object info) {
                GiftListBeanNew.DataBean bean = JSON.parseObject(((JSONObject) info).toJSONString(), GiftListBeanNew.DataBean.class);
                if (bean != null) {
                    mizuan.setText(bean.getMizuan());
                }

            }
        });
    }

    /**
     * 发送礼物
     *
     * @param substring
     * @param listNew
     */
    private void sendGift(String substring, List<Microphone.DataBean.MicrophoneBean> listNew, boolean isMyPackage, String type) {

//        if (isMyPackage) {
//
//        }
        String numbers = "";
        if (!liwushuliang.getText().toString().equals("全部")) {
            numbers = liwushuliang.getText().toString().replace("x", "").trim();
        } else {
            if (isMyPackage) {
                numbers = giftListBean.getData().getMy_wares().get(mPosition).getNum() + "";
            }
        }

        String finalNumbers = numbers;
        RxUtils.loading(commonModel.gift_queue(
                mId, microphoneBean.getUser_id(),
                String.valueOf(UserManager.getUser().getUserId()),
                substring, type, numbers
        )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {
            @Override
            public void onNext(SendGemResult baseBean) {
//                dismiss();
                if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }
//                ToastUtil.showToast(context, "发送成功");

                LoginData loginData = UserManager.getUser();

                MessageBean messageBean = new MessageBean();
//                imgQuan.getLocationOnScreen(messageBean.location);
                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                messageBean.setNickName(loginData.getNickname());
                if (!isMyPackage) {
                    GiftListBeanNew.DataBean.GiftsBean giftsBean = giftListBean.getData().getGifts().get(mPosition);
                    giftsBean.getView().getLocationOnScreen(messageBean.location);
                    messageBean.show_img = giftsBean.getShow_img();
                    messageBean.show_gif_img = giftsBean.getShow_img2();
                    messageBean.type = giftsBean.getType();
                    messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
                    messageBean.giftPrice = giftsBean.getPrice();
                    messageBean.e_name = giftsBean.e_name;
                    if (!TextUtils.equals("2", giftsBean.getType())) {//没有全屏特效情况下，更新红钻,有特效已经关闭弹框了，不用更新
                        getGiftList();
                    }
                } else {

                    if (giftListBean.getData().getMy_wares().size() == 0) {
                        dismiss();
                        return;
                    }
                    if (mPosition < 0)
                        return;
                    if (mPosition >= giftListBean.getData().getMy_wares().size())
                        return;
                    GiftListBeanNew.DataBean.MyWaresBean myWaresBean = giftListBean.getData().getMy_wares().get(mPosition);
                    messageBean.show_img = myWaresBean.getShow_img();
                    messageBean.show_gif_img = myWaresBean.getShow_img2();
                    messageBean.type = myWaresBean.getType() + "";
                    messageBean.giftNum = finalNumbers;
                    messageBean.giftPrice = myWaresBean.getPrice();

                    //if (myWaresBean.getType() != 2) {//我的背包里面的礼物数量要减少，没有全屏特效情况下
//                    if (true) {
//                        int number = myWaresBean.getNum();
//
//                        String[] sendUserIds = substring.split(",");//送给了几个人，人数*数量
//
//                        if (sendUserIds.length > 0) {
//
//                            int total = sendUserIds.length * Arith.strToInt(finalNumbers);
//
//                            number -= total;
//
//                            if (number <= 0) {
//                                giftListBean.getData().getMy_wares().remove(mPosition);
//                                mPosition = -1;
//                                if (giftListBean.getData().getMy_wares().size() == 0) {
//                                    dismiss();
//                                } else {
//                                    // TODO 待处理
////                                    showBeibaoList();
////                                    mPackageGiftAdapter.notifyDataSetChanged();
//                                }
////                                dismiss();
//                            }
//                            myWaresBean.setNum(number);
//                            myWaresBean.setPrice("x" + number);
//                            // TODO 待处理
//                            showBeibaoList();
////                            mPackageGiftAdapter.notifyDataSetChanged();
//
//                        }
//
//                    }
//                    messageBean.e_name = giftListBean.getData().getMy_wares().get(mPosition).gete;
                    String allPrice = String.valueOf(Integer.parseInt(finalNumbers) * Integer.parseInt(giftListBean.getData().getMy_wares().get(mPosition).getPrice().replace("x", "").trim()));
//                    LogUtils.debugInfo(JSON.toJSONString(myWaresBean));
                    if (ExtConfig.isSendGiftNeedAddToMessage) {
                        StringBuffer msg = new StringBuffer();
                        msg.append(myWaresBean.getName());
                        msg.append("礼物价值");
                        msg.append("(" + myWaresBean.getPrice().replace("x", "").trim() + ")");
                        msg.append("*");
                        msg.append(finalNumbers);
                        msg.append("总计：");
                        msg.append(allPrice);

                        TextMessage myTextMessage = TextMessage.obtain(msg.toString());


                        for (Microphone.DataBean.MicrophoneBean microphoneBean : listNew) {
                            Message myMessage = Message.obtain(microphoneBean.getUser_id(), Conversation.ConversationType.PRIVATE, myTextMessage);
                            RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                @Override
                                public void onAttached(Message message) {
                                    LogUtils.debugInfo("onAttached  " + JSON.toJSONString(message));
                                }

                                @Override
                                public void onSuccess(Message message) {
                                    LogUtils.debugInfo("onSuccess  " + JSON.toJSONString(message));
                                }

                                @Override
                                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                    LogUtils.debugInfo("onError  " + JSON.toJSONString(message));
                                }
                            });
                        }

                    }
                    updateBeiBaoData(myWaresBean.getId());
                }
                messageBean.setMessageType("4");

                List<SendGemResult.DataBean> sendDataList = baseBean.getData();
                if (sendDataList.size() != listNew.size()) {
                    return;
                }

                List<MessageBean.Data> dataList = new ArrayList<>();
                for (int i = 0; i < listNew.size(); i++) {
                    Microphone.DataBean.MicrophoneBean items = listNew.get(i);
                    MessageBean.Data data = new MessageBean.Data();
                    data.nickname = items.getNickname();
                    data.userId = items.getUser_id();
                    data.toNick_color = sendDataList.get(i).getNick_color();
                    dataList.add(data);

                }
                messageBean.userInfo = dataList;
                EventBus.getDefault().post(new FirstEvent(messageBean, Constant.FASONGMAIXULIWU));
                LogUtils.debugInfo("发送广播了============-=-=-=-=-=-=-=-=-=-=-=-=");
//                if (isMyPackage && liwushuliang.getText().toString().equals("全部")) {
//                    zengsong.setEnabled(false);
//
//                }
//                ss();

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
//                            ApiIOException apiIOException = (ApiIOException) t;
//                            String code = apiIOException.code;
//                dismiss();
            }
        });
    }

    int finalPrice = 0;

    private void sendALLGift(String substring, List<Microphone.DataBean.MicrophoneBean> listNew, String user_id) {
        List<GiftListBeanNew.DataBean.MyWaresBean> My_wares = giftListBean.getData().getMy_wares();
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < My_wares.size(); i++) {
            GiftListBeanNew.DataBean.MyWaresBean gift = My_wares.get(i);
            String finalNumbers = gift.getNum() + "";
            String type = "pack";
            String mId = gift.getId() + "";
            LogUtils.debugInfo("json: " + gift.toString());
            int finalI = i;

            int price = gift.getPrice_004();
            int allPrice = Integer.parseInt(finalNumbers) * price;

            stringBuffer.append(String.format("%s礼物价值(%s)*%s总计：%s\n",
                    gift.getName(),
                    price,
                    finalNumbers,
                    allPrice));
            finalPrice = finalPrice + allPrice;
            RxUtils.loading(commonModel.gift_queue_all(
                    mId, microphoneBean.getUser_id(),
                    String.valueOf(UserManager.getUser().getUserId()),
                    substring, type, finalNumbers
            )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {

                @Override
                public void onNext(SendGemResult baseBean) {

                    if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                        return;
                    }
                    ToastUtil.showToast(context, "赠送成功");

                    LoginData loginData = UserManager.getUser();
                    MessageBean messageBean = new MessageBean();
//                imgQuan.getLocationOnScreen(messageBean.location);
                    messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                    messageBean.setNickName(loginData.getNickname());

                    GiftListBeanNew.DataBean.MyWaresBean myWaresBean = My_wares.get(finalI);
                    messageBean.show_img = myWaresBean.getShow_img();
                    messageBean.show_gif_img = myWaresBean.getShow_img2();
                    messageBean.type = myWaresBean.getType() + "";
                    messageBean.giftNum = finalNumbers;
                    messageBean.setMessageType("4");


                    LogUtils.debugInfo("消息发送内容=>  " + stringBuffer);

                    List<SendGemResult.DataBean> sendDataList = baseBean.getData();
                    if (sendDataList.size() != listNew.size()) {
                        return;
                    }

                    List<MessageBean.Data> dataList = new ArrayList<>();
                    for (int i = 0; i < listNew.size(); i++) {
                        Microphone.DataBean.MicrophoneBean items = listNew.get(i);
                        MessageBean.Data data = new MessageBean.Data();
                        data.nickname = items.getNickname();
                        data.userId = items.getUser_id();
                        data.toNick_color = sendDataList.get(i).getNick_color();
                        dataList.add(data);
                    }
                    messageBean.userInfo = dataList;
                    EventBus.getDefault().post(new FirstEvent(messageBean, Constant.FASONGMAIXULIWU));
                    //My_wares.remove(gift);
                    if (My_wares.size() == 0) {

                        myWaresBean.setNum(0);
                        myWaresBean.setPrice("x" + 0);
                        My_wares.clear();
//                        mSelectBeiBaoId = "";
//                        showBeibaoList();
                        updateBeiBaoData("");
                        sendGiftAll.setVisibility(View.GONE);
                        return;


                    }

                    if (finalI == My_wares.size() - 1) {
                        if (ExtConfig.isSendGiftNeedAddToMessage) {

                            stringBuffer.append("总计：" + finalPrice);

                            LogUtils.debugInfo("消息发送内容=>  " + stringBuffer);
                            TextMessage myTextMessage = TextMessage.obtain(stringBuffer.toString());
                            Message myMessage = Message.obtain(user_id, Conversation.ConversationType.PRIVATE, myTextMessage);
                            RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                @Override
                                public void onAttached(Message message) {
                                    LogUtils.debugInfo("onAttached  " + JSON.toJSONString(message));
                                }

                                @Override
                                public void onSuccess(Message message) {
                                    LogUtils.debugInfo("onSuccess  " + JSON.toJSONString(message));
                                }

                                @Override
                                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                    LogUtils.debugInfo("onError  " + JSON.toJSONString(message));
                                }
                            });

                        }
                    }

                }

                //发送消息结束
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
//                            ApiIOException apiIOException = (ApiIOException) t;
//                            String code = apiIOException.code;
//                dismiss();
                }
            });
        }

        dismiss();//不显示礼物界面
        //
        //ss();


    }

    /**
     * 发送宝石,我的背包
     *
     * @param substring
     * @param listNew
     */
    private void sendGemStonePackage(String substring, List<Microphone.DataBean.MicrophoneBean> listNew) {

        RxUtils.loading(commonModel.send_baoshi(
                mId, microphoneBean.getUser_id(),
                String.valueOf(UserManager.getUser().getToken()),
                substring, liwushuliang.getText().toString().replace("x", "").trim()
        )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {
            @Override
            public void onNext(SendGemResult baseBean) {
//                dismiss();
//                ToastUtil.showToast(context, "发送成功");
                if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }

                LoginData loginData = UserManager.getUser();

                MessageBean messageBean = new MessageBean();
//                imgQuan.getLocationOnScreen(messageBean.location);
                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                messageBean.setNickName(loginData.getNickname());
                messageBean.show_img = giftListBean.getData().getMy_wares().get(mPosition).getShow_img();
//                messageBean.show_gif_img = giftListBean.getData().getBaoshi().get(mPosition).getShow_img2();
                messageBean.type = giftListBean.getData().getMy_wares().get(mPosition).getType() + "";
                messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
//                messageBean.e_name = giftListBean.getData().getBaoshi().get(mPosition).e_name;
                messageBean.setMessageType("4");
                List<MessageBean.Data> dataList = new ArrayList<>();

                List<SendGemResult.DataBean> sendDataList = baseBean.getData();
                if (sendDataList.size() != listNew.size()) {
                    return;
                }
                for (int i = 0; i < listNew.size(); i++) {
                    Microphone.DataBean.MicrophoneBean items = listNew.get(i);
                    MessageBean.Data data = new MessageBean.Data();
                    data.nickname = items.getNickname();
                    data.userId = items.getUser_id();
                    data.toNick_color = sendDataList.get(i).getNick_color();
                    dataList.add(data);
                }
                messageBean.userInfo = dataList;

                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setStateMessage(StateMessage.SEND_GEMSTONE);
                Object[] obj = new Object[2];
                obj[0] = messageBean;
                obj[1] = baseBean;
                messageEvent.setObject(obj);
                EventBus.getDefault().post(messageEvent);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                t.printStackTrace();
//                            ApiIOException apiIOException = (ApiIOException) t;
//                            String code = apiIOException.code;
//                dismiss();
            }
        });
    }

    /**
     * 发送宝石
     *
     * @param substring
     * @param listNew
     */
    private void sendGemStone(String substring, List<Microphone.DataBean.MicrophoneBean> listNew) {

        RxUtils.loading(commonModel.send_baoshi(
                mId, microphoneBean.getUser_id(),
                String.valueOf(UserManager.getUser().getToken()),
                substring, liwushuliang.getText().toString().replace("x", "").trim()
        )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {
            @Override
            public void onNext(SendGemResult baseBean) {
//                dismiss();
//                ToastUtil.showToast(context, "发送成功");
                if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }

                LoginData loginData = UserManager.getUser();

                MessageBean messageBean = new MessageBean();
//                imgQuan.getLocationOnScreen(messageBean.location);
                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                messageBean.setNickName(loginData.getNickname());
                messageBean.show_img = giftListBean.getData().getBaoshi().get(mPosition).getShow_img();
//                messageBean.show_gif_img = giftListBean.getData().getBaoshi().get(mPosition).getShow_img2();
                messageBean.type = giftListBean.getData().getBaoshi().get(mPosition).getType() + "";
                messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
//                messageBean.e_name = giftListBean.getData().getBaoshi().get(mPosition).e_name;
                messageBean.setMessageType("4");

                List<MessageBean.Data> dataList = new ArrayList<>();

                List<SendGemResult.DataBean> sendDataList = baseBean.getData();
                if (sendDataList.size() != listNew.size()) {
                    return;
                }
                for (int i = 0; i < listNew.size(); i++) {
                    Microphone.DataBean.MicrophoneBean items = listNew.get(i);
                    MessageBean.Data data = new MessageBean.Data();
                    data.nickname = items.getNickname();
                    data.userId = items.getUser_id();
                    data.toNick_color = sendDataList.get(i).getNick_color();
                    dataList.add(data);
                }
                messageBean.userInfo = dataList;
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setStateMessage(StateMessage.SEND_GEMSTONE);
                Object[] obj = new Object[2];
                obj[0] = messageBean;
                obj[1] = baseBean;
                messageEvent.setObject(obj);
                EventBus.getDefault().post(messageEvent);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                t.printStackTrace();
//                            ApiIOException apiIOException = (ApiIOException) t;
//                            String code = apiIOException.code;
//                dismiss();
            }
        });
    }

    /**
     * 发送爆音卡
     *
     * @param substring
     * @param listNew
     */
    private void sendByk(String substring, List<Microphone.DataBean.MicrophoneBean> listNew) {

        RxUtils.loading(commonModel.send_byk(
                microphoneBean.getUser_id(),
                String.valueOf(UserManager.getUser().getToken()),
                substring, liwushuliang.getText().toString().replace("x", "").trim()
        )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {
            @Override
            public void onNext(SendGemResult baseBean) {
//                dismiss();
//                ToastUtil.showToast(context, "发送成功");
                if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }

                LoginData loginData = UserManager.getUser();

                MessageBean messageBean = new MessageBean();
                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                messageBean.setNickName(loginData.getNickname());
                messageBean.show_img = giftListBean.getData().getMy_wares().get(mPosition).getShow_img();
//                messageBean.show_gif_img = giftListBean.getData().getBaoshi().get(mPosition).getShow_img2();
                messageBean.type = giftListBean.getData().getMy_wares().get(mPosition).getType() + "";
                messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
//                messageBean.e_name = giftListBean.getData().getBaoshi().get(mPosition).e_name;
                messageBean.setMessageType("4");

                List<MessageBean.Data> dataList = new ArrayList<>();

                List<SendGemResult.DataBean> sendDataList = baseBean.getData();
                if (sendDataList.size() != listNew.size()) {
                    return;
                }
                for (int i = 0; i < listNew.size(); i++) {
                    Microphone.DataBean.MicrophoneBean items = listNew.get(i);
                    MessageBean.Data data = new MessageBean.Data();
                    data.nickname = items.getNickname();
                    data.userId = items.getUser_id();
                    data.toNick_color = sendDataList.get(i).getNick_color();
                    dataList.add(data);
                }
                messageBean.userInfo = dataList;

                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setStateMessage(StateMessage.SEND_GEMSTONE);
                Object[] obj = new Object[2];
                obj[0] = messageBean;
                obj[1] = baseBean;
                messageEvent.setObject(obj);
                EventBus.getDefault().post(messageEvent);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                t.printStackTrace();
//                            ApiIOException apiIOException = (ApiIOException) t;
//                            String code = apiIOException.code;
//                dismiss();
            }
        });
    }

    GiftNumber2Window giftNumberWindow;

    @OnClick({R.id.zengsong, R.id.quanmai, R.id.mizuan, R.id.liwushuliang, R.id.liwu, R.id.baoshi, R.id.beibao, R.id.sendGiftAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zengsong:

                List<Microphone.DataBean.MicrophoneBean> data2 = giftUserAdapter.getData();
                List<Microphone.DataBean.MicrophoneBean> listNew = new ArrayList<>();
                StringBuffer sb = new StringBuffer();
                for (Microphone.DataBean.MicrophoneBean list : data2) {
                    if (!TextUtils.isEmpty(list.getHeadimgurl()) && list.isSelect) {
                        sb.append(list.getUser_id() + ",");
                        listNew.add(list);
                    }
                }
                if (TextUtils.isEmpty(sb.toString())) {
                    ToastUtil.showToast(context, "请选择要送的麦位或者用户！");
                    return;
                }

                String substring = sb.toString().substring(0, sb.toString().length() - 1);

                switch (mCurrentGiftType) {
                    case 0://礼物
                        if (mPosition >= giftListBean.getData().getGifts().size()) {
                            return;
                        }
                        sendGift(substring, listNew, false, "");
                        GiftListBeanNew.DataBean.GiftsBean giftsBean = giftListBean.getData().getGifts().get(mPosition);
                        if (TextUtils.equals("2", giftsBean.getType())) {//有全屏特效
//                            dismiss();
                        }
                        break;
                    case 1://宝石
                        sendGemStone(substring, listNew);
                        dismiss();
                        break;
                    case 2://我的
                        if (mPosition == -1) {
                            ToastUtil.showToast(context, "请先选择要送的礼物！");
                            return;
                        }
                        if (mPosition >= giftListBean.getData().getMy_wares().size()) {
                            return;
                        }

                        int type = giftListBean.getData().getMy_wares().get(mPosition).getWares_type();

                        if (type == 1) {//1宝石 2礼物 3 卡片
                            sendGemStonePackage(substring, listNew);
                            dismiss();
                        } else if (type == 2) {
                            GiftListBeanNew.DataBean.MyWaresBean giftsBean1 = giftListBean.getData().getMy_wares().get(mPosition);
                            /*if (listNew.size() > 1) {
                                ToastUtil.showToast(context, "全部送出礼物只能选择一个人！");
                                return;
                            }*/

                            String numbers = "";
                            if (!liwushuliang.getText().toString().equals("全部")) {
                                numbers = liwushuliang.getText().toString().replace("x", "").trim();
                            } else {
                                numbers = giftListBean.getData().getMy_wares().get(mPosition).getNum() + "";
                            }
                            try {
                                if (Integer.valueOf(numbers) > giftsBean1.getNum()) {
                                    ToastUtil.showToast(context, "当前背包礼物数量不够！");

                                    mPosition = -1;
                                    updateBeiBaoData("");
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                ToastUtil.showToast(context, "当前背包礼物数量不够！");

                                mPosition = -1;
//                                showBeibaoList();
                                updateBeiBaoData("");
                                return;
                            }
                            sendGift(substring, listNew, true, "pack");
                            if (giftsBean1.getType() == 2) {//有全屏特效
//                                mPosition = -1;
//                                showBeibaoList();
                                updateBeiBaoData("");
                            }
                        } else if (type == 3) {
                            sendByk(substring, listNew);
                            dismiss();
                        }
                        break;
                }
//                dismiss();

                break;
            case R.id.quanmai:
                List<Microphone.DataBean.MicrophoneBean> data = giftUserAdapter.getData();
                if (quanmai.isSelected()) {
                    quanmai.setSelected(false);
                    for (Microphone.DataBean.MicrophoneBean list : data) {
                        list.isSelect = false;
                        giftUserAdapter.notifyDataSetChanged();
                    }
                } else {
                    quanmai.setSelected(true);
                    for (Microphone.DataBean.MicrophoneBean list : data) {
                        if (!TextUtils.isEmpty(list.getHeadimgurl())) {
                            if (list.getUser_id()
                                    .equals(UserManager.getUser().getUserId() + "")) {
                                list.isSelect = false;
                            } else {
                                list.isSelect = true;
                            }
                        }
                        giftUserAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.mizuan:
//                Intent intent = new Intent(context, ChargeActivity.class);
//                intent.putExtra("type", 1);
//                ArmsUtils.startActivity(intent);
//                if (context instanceof AdminHomeActivity) {
//                    context.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                }
                EventBus.getDefault().post(new FirstEvent("", Constant.RECHARGE_FROM_HOME));
                dismiss();
                break;
            case R.id.liwushuliang:
                if (giftNumberWindow == null) return;
                giftNumberWindow.getmMenuView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth = giftNumberWindow.getmMenuView().getMeasuredWidth();
                int popupHeight = giftNumberWindow.getmMenuView().getMeasuredHeight();
                int[] location = new int[2];
                imgGift.getLocationOnScreen(location);

                giftNumberWindow.showAtLocation(imgGift, Gravity.TOP | Gravity.LEFT,
                        ScreenDimenUtil.getInstance().getScreenWdith() - (DpUtil.dp2px(87) + popupWidth / 2), ScreenDimenUtil.getInstance().getScreenHeight() - (DpUtil.dp2px(35) + popupHeight));
                giftNumberWindow.getMyGrid().setOnItemClickListener((parent, view1, position, id) -> {
                    giftNumberWindow.dismiss();
                    liwushuliang.setText(giftNumberWindow.getGiftDiaAdapter().getList_adapter().get(position));
                });
                break;

            case R.id.liwu:
                if (mCurrentGiftType == 0) {
                    return;
                }
                mId = "";
                allPrice.setVisibility(View.GONE);
                sendGiftAll.setVisibility(View.GONE);
                resetLiwuSelect();
                liwu.setSelected(true);
                mCurrentGiftType = 0;
                zengsong.setEnabled(false);
                showGiftList();
                giftNumberWindow.resetData(false);
                liwushuliang.setText("x1");
                break;
            case R.id.baoshi:
                allPrice.setVisibility(View.GONE);
                sendGiftAll.setVisibility(View.GONE);
                if (giftListBean == null || giftListBean.getData() == null || giftListBean.getData().getBaoshi() == null || giftListBean.getData().getBaoshi().size() == 0) {
                    ToastUtil.showToast(context, "暂无宝石");
                    return;
                }
                if (mCurrentGiftType == 1) {
                    return;
                }
                mId = "";
                resetLiwuSelect();
                baoshi.setSelected(true);
                mCurrentGiftType = 1;
                zengsong.setEnabled(false);
                showBaoshiList();
                giftNumberWindow.resetData(false);
                liwushuliang.setText("x1");
                break;
            case R.id.beibao:
                if (giftListBean == null || giftListBean.getData() == null || giftListBean.getData().getMy_wares() == null || giftListBean.getData().getMy_wares().size() == 0) {
                    ToastUtil.showToast(context, "我的背包空空如也~");
                    allPrice.setVisibility(View.GONE);
                    sendGiftAll.setVisibility(View.GONE);
                    return;
                }
                if (mCurrentGiftType == 2) {
                    return;
                }
                mPosition = -1;
                mId = "";
                allPrice.setVisibility(View.VISIBLE);
                if (ExtConfig.isSendAllGift ){
                    sendGiftAll.setVisibility(View.VISIBLE);
                }

                resetLiwuSelect();
                zengsong.setEnabled(false);
                beibao.setSelected(true);
                mCurrentGiftType = 2;
                updateBeiBaoData("");
                giftNumberWindow.resetData(true);
                break;
            case R.id.sendGiftAll:
                String name = null;
                List<Microphone.DataBean.MicrophoneBean> data3 = giftUserAdapter.getData();
                List<Microphone.DataBean.MicrophoneBean> listNew1 = new ArrayList<>();
                StringBuffer sb1 = new StringBuffer();
                Uri uri1 = null;
                for (Microphone.DataBean.MicrophoneBean list : data3) {
                    if (!TextUtils.isEmpty(list.getHeadimgurl()) && list.isSelect) {
                        uri1 = Uri.parse(list.getHeadimgurl());
                        name = list.getNickname();
                        sb1.append(list.getUser_id() + ",");
                        listNew1.add(list);
                    }
                }
                if (TextUtils.isEmpty(sb1.toString())) {
                    ToastUtil.showToast(context, "请选择要送的麦位或者用户！");
                    return;
                }
                if (listNew1.size() > 1) {
                    ToastUtil.showToast(context, "全部送出礼物只能选择一个人！");
                    return;
                }


                String user_id = listNew1.get(0).getUser_id();
//                pupWindow.show(listNew1,gift,commonModel,microphoneBean.getUser_id(),String.valueOf(UserManager.getUser().getUserId()),user_id,mount+"",name,uri1);
//                dismiss();
                String substring1 = sb1.toString().substring(0, sb1.toString().length() - 1);
                sendALLGift(substring1, listNew1, user_id);
                break;
        }
    }


    private void resetLiwuSelect() {
        liwu.setSelected(false);
        baoshi.setSelected(false);
        beibao.setSelected(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(MessageEvent event) {

        StateMessage stateMessage = event.getStateMessage();

        if (stateMessage.getState() == StateMessage.CLOSE_GIFT_WINDOW.getState()) {//关闭
//            dismiss();
        }

    }

    @Override
    public void onItemChecked(GiftListBeanNew.DataBean.GiftsBean bean, int position) {
        mPosition = position;
        mId = bean.getId();
        zengsong.setEnabled(true);
    }

    @Override
    public void onItemChecked(GiftListBeanNew.DataBean.BaoshiBean bean, int position) {
        mPosition = position;
        mId = bean.getId();
        zengsong.setEnabled(true);
    }

    @Override
    public void onItemChecked(GiftListBeanNew.DataBean.MyWaresBean bean, int position) {
        mPosition = position;
        mId = bean.getId();
        showBeibaoList();

        zengsong.setEnabled(true);
    }
}
