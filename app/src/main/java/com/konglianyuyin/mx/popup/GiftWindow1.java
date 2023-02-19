package com.konglianyuyin.mx.popup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.ChargeActivity;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.GiftPagerAdapter;
import com.konglianyuyin.mx.adapter.GiftUserAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.GiftListBeanNew;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.MessageEvent;
import com.konglianyuyin.mx.bean.Microphone;
import com.konglianyuyin.mx.bean.SendGemResult;
import com.konglianyuyin.mx.bean.StateMessage;
import com.konglianyuyin.mx.http.HttpCallback;
import com.konglianyuyin.mx.http.HttpUtil;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Arith;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.konglianyuyin.mx.view.MyViewPager2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.khrystal.library.widget.CircularHorizontalMode;
import me.khrystal.library.widget.ItemViewMode;

import static com.konglianyuyin.mx.activity.room.AdminHomeActivity.mContext;


/**
 * 礼物弹窗,popupwindow和dialog不能嵌套viewpager使用，此处是一个巨坑，草特大爷
 */
@SuppressLint("ValidFragment")
public class GiftWindow1 extends PopupWindow implements GiftPagerAdapter.ActionListener{

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
    MyViewPager2 viewPager;
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


    private String mId = "";//礼物id

    public TextView getTextNum() {
        return liwushuliang;
    }

    private AdminHomeActivity context;
    private CommonModel commonModel;
    private ItemViewMode mItemViewMode;
    private LinearLayoutManager mLayoutManager;
    private List<Microphone.DataBean.MicrophoneBean> mMicrophone;
    private GiftListBeanNew giftListBean;
    private Microphone.DataBean.MicrophoneBean microphoneBean;
    private GiftUserAdapter giftUserAdapter;
    private int mPosition;
    private ImageView imgGift;

//    GiftAdapter mGiftAdapter;
    GiftPagerAdapter mGiftPagerAdapter;

//    StoneGiftAdapter mStoneGiftAdapter;

//    PackageGiftAdapter mPackageGiftAdapter;

    private int mCurrentGiftType = 0;

    public GiftWindow1(AdminHomeActivity context,
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

//        List<GiftListBeanNew.DataBean.GiftsBean> giftsBeans = giftListBean.getData().getGifts();
//
        List<GiftListBeanNew.DataBean.MyWaresBean> myWaresBeanList = new ArrayList<>();

        GiftListBeanNew.DataBean.MyWaresBean myWaresBean = new GiftListBeanNew.DataBean.MyWaresBean();
        myWaresBean.setId("6");
        myWaresBean.setName("iiiiii");
        myWaresBean.setPrice("133");
        myWaresBean.setImg("http:\\/\\/47.92.85.75\\/upload\\/gifts\\/png\\/gift_list_sgpp02@2x.png");
        myWaresBean.setType(2);
        myWaresBean.setShow_img("http:\\/\\/47.92.85.75\\/upload\\/gifts\\/png\\/gift_sgpp@2x.png");
        myWaresBean.setShow_img2("http:\\/\\/47.92.85.75\\/upload\\/gifts\\/png\\/gift_sgpp@2x.png");
        myWaresBeanList.add(myWaresBean);
        myWaresBeanList.add(myWaresBean);
        myWaresBeanList.add(myWaresBean);
        myWaresBeanList.add(myWaresBean);
        this.giftListBean.getData().setMy_wares(myWaresBeanList);

        initUser(context);
        initBottomRecycler(context);
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
        listNew.add(microphoneBean);
        listNew.add(mMicrophone.get(mMicrophone.size() - 1));

        for (int i = 0; i < mMicrophone.size() - 1; i++) {
            mMicrophone.get(i).isSelect = false;
            listNew.add(mMicrophone.get(i));
        }
        giftUserAdapter = new GiftUserAdapter(context, userRecyclerview,
                listNew);
        userRecyclerview.setAdapter(giftUserAdapter);
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

        initAdapters();

        showGiftList();


    }


    private void showGiftList() {
        if (giftListBean.getData().getGifts() == null) {
            giftListBean.getData().setGifts(new ArrayList<>());
        }
        radioGroup.removeAllViews();
        mGiftPagerAdapter = new GiftPagerAdapter(mContext, giftListBean.getData().getGifts());
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

    }

    private void showBeibaoList() {

    }




    private void initAdapters() {

        if (giftListBean.getData().getGifts() == null) {
            giftListBean.getData().setGifts(new ArrayList<>());
        }
//        mGiftAdapter = new GiftAdapter(context, circleRecycler, giftListBean.getData().getGifts(), giftListBean.getData().getGifts().size() <= 4 ? false : true);

        if (giftListBean.getData().getBaoshi() == null) {
            giftListBean.getData().setBaoshi(new ArrayList<>());
        }
//        mStoneGiftAdapter = new StoneGiftAdapter(context, circleRecycler, giftListBean.getData().getBaoshi(), giftListBean.getData().getBaoshi().size() <= 4 ? false : true);

        boolean needLoop = true;

        if (giftListBean.getData().getMy_wares() == null) {
            giftListBean.getData().setMy_wares(new ArrayList<>());
        }

        if (giftListBean.getData().getMy_wares().size() <= 4) {
            needLoop = false;
        }

//        mPackageGiftAdapter = new PackageGiftAdapter(context, circleRecycler, giftListBean.getData().getMy_wares(), needLoop);

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
                GiftListBeanNew.DataBean bean = JSON.parseObject(((JSONObject)info).toJSONString(), GiftListBeanNew.DataBean.class);
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
    private void sendGift(String substring, List<Microphone.DataBean.MicrophoneBean> listNew, boolean isMyPackage,String type) {

        String numbers = liwushuliang.getText().toString().replace("x", "").trim();
        RxUtils.loading(commonModel.gift_queue(
                mId, microphoneBean.getUser_id(),
                String.valueOf(UserManager.getUser().getUserId()),
                substring, type,numbers
        )).subscribe(new ErrorHandleSubscriber<SendGemResult>(context.mErrorHandler) {
            @Override
            public void onNext(SendGemResult baseBean) {
//                dismiss();
                if (baseBean == null || baseBean.getData() == null || baseBean.getData().size() == 0) {
                    return;
                }
                ToastUtil.showToast(context, "发送成功");

                LoginData loginData = UserManager.getUser();

                MessageBean messageBean = new MessageBean();
//                imgQuan.getLocationOnScreen(messageBean.location);
                messageBean.setUser_id(String.valueOf(loginData.getUserId()));
                messageBean.setNickName(loginData.getNickname());
                if (!isMyPackage) {
                    GiftListBeanNew.DataBean.GiftsBean giftsBean = giftListBean.getData().getGifts().get(mPosition);
                    messageBean.show_img = giftsBean.getShow_img();
                    messageBean.show_gif_img = giftsBean.getShow_img2();
                    messageBean.type = giftsBean.getType();
                    messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
                    messageBean.e_name = giftsBean.e_name;
                    if (!TextUtils.equals("2", giftsBean.getType())) {//没有全屏特效情况下，更新红钻,有特效已经关闭弹框了，不用更新

                        getGiftList();

                    }
                } else {

                    if (giftListBean.getData().getMy_wares().size() == 0) {
                        dismiss();
                        return;
                    }
                    GiftListBeanNew.DataBean.MyWaresBean myWaresBean = giftListBean.getData().getMy_wares().get(mPosition);
                    messageBean.show_img = myWaresBean.getShow_img();
                    messageBean.show_gif_img = myWaresBean.getShow_img2();
                    messageBean.type = myWaresBean.getType() + "";
                    messageBean.giftNum = liwushuliang.getText().toString().replace("x", "").trim();
                    if (myWaresBean.getType() != 2) {//我的背包里面的礼物数量要减少，没有全屏特效情况下
                        int number = myWaresBean.getNum();

                        String[] sendUserIds = substring.split(",");//送给了几个人，人数*数量

                        if (sendUserIds.length > 0) {

                            int total = sendUserIds.length * Arith.strToInt(numbers);

                            number -= total;

                            if (number <= 0) {
                                giftListBean.getData().getMy_wares().remove(mPosition);
                                if (giftListBean.getData().getMy_wares().size() == 0) {
                                    dismiss();
                                } else {
                                    // TODO 待处理
//                                    mPackageGiftAdapter.notifyDataSetChanged();
                                }
//                                dismiss();
                            }
                            myWaresBean.setNum(number);
                            myWaresBean.setPrice("x" + number);
                            // TODO 待处理
//                            mPackageGiftAdapter.notifyDataSetChanged();

                        }

                    }
//                    messageBean.e_name = giftListBean.getData().getMy_wares().get(mPosition).gete;
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
                ToastUtil.showToast(context, "发送成功");
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
                ToastUtil.showToast(context, "发送成功");
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
                ToastUtil.showToast(context, "发送成功");
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

    @OnClick({R.id.zengsong, R.id.quanmai, R.id.mizuan, R.id.liwushuliang, R.id.liwu, R.id.baoshi, R.id.beibao})
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
                        sendGift(substring, listNew, false,"");
                        GiftListBeanNew.DataBean.GiftsBean giftsBean = giftListBean.getData().getGifts().get(mPosition);
                        if (TextUtils.equals("2", giftsBean.getType())) {//有全屏特效
                            dismiss();
                        }
                        break;
                    case 1://宝石
                        sendGemStone(substring, listNew);
                        dismiss();
                        break;
                    case 2://我的
                        if (mPosition >= giftListBean.getData().getMy_wares().size()) {
                            return;
                        }
                        int type = giftListBean.getData().getMy_wares().get(mPosition).getWares_type();

                        if (type == 1) {//1宝石 2礼物 3 卡片
                            sendGemStonePackage(substring, listNew);
                            dismiss();
                        } else if (type == 2) {
                            sendGift(substring, listNew, true,"pack");
                            GiftListBeanNew.DataBean.MyWaresBean giftsBean1 = giftListBean.getData().getMy_wares().get(mPosition);
                            if (giftsBean1.getType() == 2) {//有全屏特效
//                                dismiss();
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
                Intent intent = new Intent(context, ChargeActivity.class);
                intent.putExtra("type", 1);
                ArmsUtils.startActivity(intent);
                if (context instanceof AdminHomeActivity) {
                    context.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                }
                break;
            case R.id.liwushuliang:
                GiftNumber2Window giftNumberWindow = new GiftNumber2Window(context);
                giftNumberWindow.getmMenuView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int popupWidth = giftNumberWindow.getmMenuView().getMeasuredWidth();
                int popupHeight = giftNumberWindow.getmMenuView().getMeasuredHeight();
                int[] location = new int[2];
                imgGift.getLocationOnScreen(location);
                giftNumberWindow.showAtLocation(imgGift, Gravity.NO_GRAVITY,
                        (location[0] + imgGift.getWidth() / 2) - popupWidth / 2 + 140, location[1] - popupHeight);
                giftNumberWindow.getMyGrid().setOnItemClickListener((parent, view1, position, id) -> {
                    giftNumberWindow.dismiss();
                    liwushuliang.setText(giftNumberWindow.getGiftDiaAdapter().getList_adapter().get(position));
                });
                break;

            case R.id.liwu:
                if (mCurrentGiftType == 0) {
                    return;
                }
                mCurrentGiftType = 0;
                showGiftList();

                break;
            case R.id.baoshi:
                if (giftListBean == null || giftListBean.getData() == null || giftListBean.getData().getBaoshi() == null || giftListBean.getData().getBaoshi().size() == 0) {
                    ToastUtil.showToast(context, "暂无宝石");
                    return;
                }
                if (mCurrentGiftType == 1) {
                    return;
                }
                mCurrentGiftType = 1;
                giftListBean.getData().getBaoshi().size();
                showGiftList();

                break;
            case R.id.beibao:
                if (giftListBean == null || giftListBean.getData() == null || giftListBean.getData().getMy_wares() == null || giftListBean.getData().getMy_wares().size() == 0) {
                    ToastUtil.showToast(context, "我的背包空空如也~");
                    return;
                }
                if (mCurrentGiftType == 2) {
                    return;
                }
                mCurrentGiftType = 2;


                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(MessageEvent event) {

        StateMessage stateMessage = event.getStateMessage();

        if (stateMessage.getState() == StateMessage.CLOSE_GIFT_WINDOW.getState()) {//关闭
            dismiss();
        }

    }

    @Override
    public void onItemChecked(GiftListBeanNew.DataBean.GiftsBean bean, int position) {
        mId = bean.getId();

    }
}
