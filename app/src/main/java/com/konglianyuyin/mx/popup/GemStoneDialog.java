package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.BXAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BaoXiangBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.MessageEvent;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.bean.StateMessage;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Arith;
import com.konglianyuyin.mx.utils.BToast;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.DUIHUAN;
import static com.konglianyuyin.mx.utils.Constant.GOUMAIYAOSHI;
import static com.konglianyuyin.mx.utils.Constant.XIANSHIWANBI;


public class GemStoneDialog extends Dialog {

    @BindView(R.id.tv_gemstone_integral)
    TextView mTvGemstoneIntegral;
    @BindView(R.id.img_winning_record)
    ImageView mImgWinningRecord;
    @BindView(R.id.img_gemstone_intro)
    ImageView mImgGemstoneIntro;
    @BindView(R.id.img_gemstone_exchange)
    ImageView mImgGemstoneExchange;
    @BindView(R.id.tv_key_count)
    TextView mTvKeyCount;
    @BindView(R.id.img_add_key)
    ImageView mImgAddKey;
    @BindView(R.id.img_ten_open)
    ImageView mImgTenOpen;
    @BindView(R.id.img_open_one)
    ImageView mImgOpenOne;
    //    @BindView(R.id.img_gemstone_logo)
//    ImageView mImgGemstoneLogo;
    @BindView(R.id.img_open_hundred)
    ImageView mImgOpenHundred;
    @BindView(R.id.layout_baoxiao)
    ConstraintLayout mConstraintLayoutBaoxiao;
    //    @BindView(R.id.img_svg_open)
//    SVGAImageView mSVGAImageView;
    @BindView(R.id.tv_cut_down_time)
    TextView mTvCutDownTime;
    @BindView(R.id.view_empty)
    View mViewEmpty;

    private AdminHomeActivity mContext;
    private CommonModel commonModel;
    private RxErrorHandler mErrorHandler;
    private BaoXiangBean.DataBean mDataBean;
//    private TouMingDialog touMingDialog;
//    private OpenBoxBean mOpenBoxBean;
//    private BoxGiftActivity boxGiftActivity;
//    private int anInt = 0;

    boolean mHasThreeMinate = true;//??????????????????????????????3???????????????

    boolean mIsGuardOpen = true;//??????????????????????????????????????????????????????

    long mHasGoneThreeMinateTime = 0;//3??????????????????????????????????????????

    long mHasGoneGuardOpenTime = 0;//30??????????????????????????????????????????


    long mLimitGuardTime = 0;//?????????????????????????????????????????????

    long mLimitThreeMinateTime = 0;//???????????????3??????????????????????????????

    Handler mHandlerThreeMinate = new Handler();

    Handler mHandlerGuardOpen = new Handler();


    Runnable mThreeMinateRunnable = new Runnable() {//??????????????????????????????3???????????????
        @Override
        public void run() {

//            long limite = 3 * 60 * 1000;//????????????

            mHasGoneThreeMinateTime += 1000;

            if (mHasGoneThreeMinateTime >= mLimitThreeMinateTime) {
                mHasGoneThreeMinateTime = mLimitThreeMinateTime;
            }

            long second = (mLimitThreeMinateTime - mHasGoneThreeMinateTime) / 1000;//???????????????

            String timeStr = "";

            if (second == 0) {

                timeStr = "0:00";

            } else {

                if (second >= 60) {//??????????????????60???

                    int minate = (int) (second / 60);

                    timeStr = "" + minate;

                    int sec = (int) (second % 60);

                    if (sec >= 10) {
                        timeStr = timeStr + ":" + sec;
                    } else {
                        timeStr = timeStr + ":0" + sec;
                    }

                } else {//??????????????????60???

                    if (second >= 10) {
                        timeStr = "0:" + second;
                    } else {
                        timeStr = "0:0" + second;
                    }

                }

            }
            mTvCutDownTime.setText("????????????????????????" + timeStr + "??????");

            if (timeStr.equals("0:00")) {
                getBaoXiang();
                return;
            } else {
                LogUtils.debugInfo("??????????????????====" + timeStr);
                mHandlerThreeMinate.postDelayed(this, 1000);
            }

        }
    };

    Runnable mGuardCloseRunnable = new Runnable() {//???????????????????????????????????????????????????
        @Override
        public void run() {

//            long limite = 30 * 60 * 1000;//????????????

            mHasGoneGuardOpenTime += 1000;

            if (mHasGoneGuardOpenTime >= mLimitGuardTime) {
                mHasGoneGuardOpenTime = mLimitGuardTime;
            }

            long second = (mLimitGuardTime - mHasGoneGuardOpenTime) / 1000;//???????????????

            String timeStr = "";

            if (second == 0) {

                timeStr = "0:00";

            } else {

                if (second >= 60) {//??????????????????60???

                    int minate = (int) (second / 60);

                    if (minate >= 10) {
                        timeStr = "" + minate;
                    } else {

                        timeStr = "0" + minate;
                    }


                    int sec = (int) (second % 60);

                    if (sec >= 10) {
                        timeStr = timeStr + ":" + sec;
                    } else {
                        timeStr = timeStr + ":0" + sec;
                    }

                } else {//??????????????????60???

                    if (second >= 10) {
                        timeStr = "0:" + second;
                    } else {
                        timeStr = "0:0" + second;
                    }

                }

            }
            mTvCutDownTime.setText("????????????????????????" + timeStr + "??????");

            if (timeStr.equals("0:00")) {
                getBaoXiang();
                return;
            } else {
                mHandlerGuardOpen.postDelayed(this, 1000);
            }

        }
    };

    public GemStoneDialog(@NonNull Activity context, CommonModel commonModel, RxErrorHandler errorHandler) {
        super(context, R.style.myChooseDialog);
        mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mErrorHandler = errorHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_open_gemstone);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        boo(false);
        setWidows();

//        initTime();

        getBaoXiang();

    }

    private void initTime() {
        mHandlerThreeMinate.removeCallbacks(mThreeMinateRunnable);
        mHandlerGuardOpen.removeCallbacks(mGuardCloseRunnable);
        LogUtils.debugInfo("???????????????" + System.currentTimeMillis());

        long currentTime = System.currentTimeMillis() / 1000;

        if (mDataBean != null && !TextUtils.isEmpty(mDataBean.getBoxstartdate()) && !TextUtils.isEmpty(mDataBean.getBoxenddate())) {

            long startTime = Arith.strToLong(mDataBean.getBoxstartdate());

            long endTime = Arith.strToLong(mDataBean.getBoxenddate());

//            startTime=currentTime+2*60;
//            endTime+=currentTime+32*60;

            LogUtils.debugInfo("???????????????" + startTime);
            LogUtils.debugInfo("???????????????" + endTime);

            if (currentTime >= startTime && currentTime < endTime) {//?????????????????????????????????????????????????????????

                LogUtils.debugInfo("?????????????????????????????????????????????????????????");

                mLimitGuardTime = endTime - currentTime;//??????????????????

                mLimitGuardTime = mLimitGuardTime * 1000;//????????????

                mTvCutDownTime.setText("");

                mTvCutDownTime.setVisibility(View.VISIBLE);

                mHasGoneGuardOpenTime = 0;

                mHandlerGuardOpen.postDelayed(mGuardCloseRunnable, 1000);

                updateViewHeight();

            } else if (currentTime > endTime) {//????????????

                LogUtils.debugInfo("????????????");

                mTvCutDownTime.setVisibility(View.GONE);

                updateViewHeight();

            } else if (currentTime < startTime && startTime - currentTime > 3 * 60) {//??????????????????3??????

                LogUtils.debugInfo("??????????????????3??????");

                mTvCutDownTime.setVisibility(View.GONE);

                updateViewHeight();

            } else if (currentTime < startTime && startTime - currentTime <= 3 * 60) {//?????????????????????3???????????????

                LogUtils.debugInfo("?????????????????????3???????????????");

                mLimitThreeMinateTime = startTime - currentTime;//??????????????????

                mLimitThreeMinateTime = mLimitThreeMinateTime * 1000;//????????????

                mTvCutDownTime.setText("");

                mTvCutDownTime.setVisibility(View.VISIBLE);

                mHasGoneThreeMinateTime = 0;

                mHandlerThreeMinate.postDelayed(mThreeMinateRunnable, 1000);

            }


        }

//        if(mHasThreeMinate){//??????3????????????????????????????????????
//
//            mTvCutDownTime.setVisibility(View.VISIBLE);
//
//            mHandlerThreeMinate.postDelayed(mThreeMinateRunnable, 1000);
//        }
//        if (mIsGuardOpen) {
//
//            mTvCutDownTime.setText("????????????????????????30:00??????");
//
//            mTvCutDownTime.setVisibility(View.VISIBLE);
//
//            mHandlerGuardOpen.postDelayed(mGuardCloseRunnable, 1000);
//
//            updateViewHeight();
//        }

    }

    /**
     * ??????dialog??????????????????
     */
    private void updateViewHeight() {

        if (mTvCutDownTime.getVisibility() == View.GONE) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mViewEmpty.getLayoutParams();
            params.height = QMUIDisplayHelper.dpToPx(30);
            mViewEmpty.setLayoutParams(params);
        } else {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mViewEmpty.getLayoutParams();
            params.height = QMUIDisplayHelper.dpToPx(10);
            mViewEmpty.setLayoutParams(params);
        }
    }

    private void setWidows() {

        Display display = getWindow().getWindowManager().getDefaultDisplay();

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.width = display.getWidth();

        lp.alpha = 1.0f;

        lp.gravity = Gravity.CENTER;

        getWindow().setAttributes(lp);

        this.setCanceledOnTouchOutside(true);

    }

    @Override
    public void dismiss() {
        if (recyclerview.getVisibility() == View.VISIBLE) {
            recyclerview.setVisibility(View.GONE);
        } else {
            super.dismiss();
            mHandlerThreeMinate.removeCallbacks(mThreeMinateRunnable);
            mHandlerGuardOpen.removeCallbacks(mGuardCloseRunnable);
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.img_winning_record, R.id.img_gemstone_intro, R.id.img_gemstone_exchange, R.id.img_add_key, R.id.img_ten_open, R.id.img_open_one, R.id.img_open_hundred, R.id.img_baoxiang_jiangchi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_winning_record:
                BoxOpenRecordWindow boxOpenRecordWindow = new BoxOpenRecordWindow(mContext, mConstraintLayoutBaoxiao, commonModel, mErrorHandler);
                boxOpenRecordWindow.showAtLocation(mTvGemstoneIntegral, Gravity.CENTER, 0, 0);
                break;
            case R.id.img_baoxiang_jiangchi:
                BoxJiangChiWindow boxJiangChiWindow = new BoxJiangChiWindow(mContext, mConstraintLayoutBaoxiao, commonModel, mErrorHandler, 0);
                boxJiangChiWindow.showAtLocation(mTvGemstoneIntegral, Gravity.CENTER, 0, 0);
                break;
            case R.id.img_gemstone_intro:
                BoxTitleWindow boxTitleWindow = new BoxTitleWindow(mContext, mConstraintLayoutBaoxiao, commonModel, mErrorHandler,"0");
                boxTitleWindow.showAtLocation(mTvGemstoneIntegral, Gravity.CENTER, 0, 0);
                break;
            case R.id.img_gemstone_exchange:
                BoxDuiHuanWindow boxDuiHuanWindow = new BoxDuiHuanWindow(mContext, mConstraintLayoutBaoxiao, commonModel, mErrorHandler, mDataBean.getPoints());
                boxDuiHuanWindow.showAtLocation(mTvGemstoneIntegral, Gravity.CENTER, 0, 0);
                break;
            case R.id.img_add_key://????????????
                BuyKeyDialog buyKeyDialog = new BuyKeyDialog(mContext, commonModel, mErrorHandler);
                buyKeyDialog.show();
                break;
            case R.id.img_ten_open:
//                touMingDialog = new TouMingDialog(mContext);
//                touMingDialog.show();
                boo(false);
                setCanceledOnTouchOutside(false);
                getAwardList(10);
                break;
            case R.id.img_open_one:
//                touMingDialog = new TouMingDialog(mContext);
//                touMingDialog.show();
                boo(false);
                setCanceledOnTouchOutside(false);
                getAwardList(1);
                break;
            case R.id.img_open_hundred:
//                touMingDialog = new TouMingDialog(mContext);
//                touMingDialog.show();
                boo(false);
                setCanceledOnTouchOutside(false);
                getAwardList(100);
                break;
        }
    }

    //????????????
    public void showWeiSVG(SVGAParser parser, String giftUrl, SVGAImageView svgaImageView) {
        try {
            parser.decodeFromAssets(giftUrl, new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    svgaImageView.setVideoItem(svgaVideoEntity);
                    svgaImageView.setLoops(0);
                    svgaImageView.stepToFrame(1, true);
                }

                @Override
                public void onError() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debugInfo("====SVAG??????", e.getMessage());
        }
    }

    //????????????
    public void showServerSVG(SVGAParser parser, String giftUrl, SVGAImageView svgaImageView, OpenBoxBean openBoxBean) {
        try {
            parser.decodeFromAssets(giftUrl, new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    svgaImageView.setVideoItem(svgaVideoEntity);
                    svgaImageView.setLoops(1);
                    svgaImageView.stepToFrame(1, true);
//                    svgaImageView.startAnimation();
//                    svgaImageView.setClearsAfterStop(false);
                    setSvgImgClickble(svgaImageView, openBoxBean);
                }

                @Override
                public void onError() {
                    LogUtils.debugInfo("====?????????");
                }
            });
//            setSvgImgClickble(svgaImageView, openBoxBean);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debugInfo("====?????????????????????");
            LogUtils.debugInfo("====????????????", e.getLocalizedMessage());
            LogUtils.debugInfo("====?????????????", e.getMessage());
            LogUtils.debugInfo("====????????????", e.toString());
        }
    }

    /**
     * ????????????????????????
     */
    private void setSvgImgClickble(SVGAImageView svgImage, OpenBoxBean openBoxBean) {
        LogUtils.debugInfo("====???????????????", "?????????????????????");
        svgImage.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
            }

            @Override
            public void onFinished() {
                svgImage.setCallback(null);
//                svgImage.setClickable(false);
                LogUtils.debugInfo("====????????????");
//                if (touMingDialog != null) {
//                    touMingDialog.dismiss();
//                }
                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
                boxGiftActivity.show();
//                touMingDialog = new TouMingDialog(mContext);
//                touMingDialog.show();
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {
            }
        });

    }

    //??????????????????
    private void getBaoXiang() {
        RxUtils.loading(commonModel.getBoxInfo("xx"), mContext)
                .subscribe(new ErrorHandleSubscriber<BaoXiangBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaoXiangBean baoXiangBean) {
                        if (baoXiangBean.getCode() == 1) {
                            boo(true);
                            mDataBean = baoXiangBean.getData();
                            mTvGemstoneIntegral.setText("??????????????? " + baoXiangBean.getData().getPoints());
                            mTvKeyCount.setText(String.valueOf(baoXiangBean.getData().getKeys_num()));
                            initTime();
//                            if (baoXiangBean.getData().getBoxclass() == 1) {
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showWeiSVG(parser, "putong_weikai.svga", mSVGAImageView);
//                            } else if (baoXiangBean.getData().getBoxclass() == 2) {
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showWeiSVG(parser, "shouhu_weikai.svga", mSVGAImageView);
//                            }
//                           int points= Integer.parseInt(baoXiangBean.getData().getPoints());

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        LogUtils.debugInfo("====??????????????????", t.getMessage());
                    }
                });
    }

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    //????????????
    private void getAwardList(int keyNum) {
        RxUtils.loading(commonModel.getAwardList(keyNum, "0"), mContext)
                .subscribe(new ErrorHandleSubscriber<OpenBoxBean>(mErrorHandler) {
                    @Override
                    public void onNext(OpenBoxBean openBoxBean) {
                        if (openBoxBean.getCode() == 1) {
                            if (mDataBean == null) return;
                            if (mDataBean.getBoxclass() == 1) {
                                recyclerview.setVisibility(View.VISIBLE);
                                BXAdapter mAdapter = new BXAdapter();
                                GridLayoutManager GM = new GridLayoutManager(mContext, 4);
                                recyclerview.setLayoutManager(GM);
                                recyclerview.setAdapter(mAdapter);
                                mAdapter.setNewData(openBoxBean.getData().getAwardList());
                                EventBus.getDefault().post(new FirstEvent("????????????", XIANSHIWANBI));
                                EventBus.getDefault().post(new FirstEvent("????????????", "donghuawancheng"));
//                                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
//                                boxGiftActivity.show();
//                                touMingDialog = new TouMingDialog(mContext);
//                                touMingDialog.show();
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showServerSVG(parser, "putong_kai.svga", mSVGAImageView, openBoxBean);
                            } else if (mDataBean.getBoxclass() == 2) {
                                recyclerview.setVisibility(View.VISIBLE);
                                BXAdapter mAdapter = new BXAdapter();
                                GridLayoutManager GM = new GridLayoutManager(mContext, 4);
                                recyclerview.setLayoutManager(GM);
                                recyclerview.setAdapter(mAdapter);
                                mAdapter.setNewData(openBoxBean.getData().getAwardList());
                                EventBus.getDefault().post(new FirstEvent("????????????", XIANSHIWANBI));
                                EventBus.getDefault().post(new FirstEvent("????????????", "donghuawancheng"));
//                                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
//                                boxGiftActivity.show();
//                                touMingDialog = new TouMingDialog(mContext);
//                                touMingDialog.show();
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showServerSVG(parser, "shouhu_kai.svga", mSVGAImageView, openBoxBean);
                            }

//                            BXAdapter mAdapter = new BXAdapter();
//                            GridLayoutManager GM = new GridLayoutManager(mContext, 4);
//                            recyclerview.setLayoutManager(GM);
//                            recyclerview.setAdapter(mAdapter);
//                            mAdapter.setNewData(openBoxBean.getData().getAwardList());

                            //????????????????????????
                            List<OpenBoxBean.DataBean.AwardListBean> awardList = openBoxBean.getData().getAwardList();
                            if (awardList != null && awardList.size() > 0) {

                                List<OpenBoxBean.DataBean.AwardListBean> newAwardList = new ArrayList<>();
                                //???????????????????????????20???????????????????????????
                                for (OpenBoxBean.DataBean.AwardListBean itemBean : awardList) {

                                    //double price = Arith.strToDouble(itemBean.getPrice());

                                    if (itemBean.getIs_public_play() == 1) {
                                        newAwardList.add(itemBean);
                                    }

                                }

//                                StringBuilder builder = new StringBuilder();
//                                builder.append("?????????");
//                                builder.append(UserManager.getUser().getNickname());
//                                builder.append("???");
//                                if (openBoxBean.getData().getBox_class() == 1) {
//                                    builder.append("????????????");
//                                } else {
//                                    builder.append("????????????");
//                                }
//                                builder.append("?????????");
//                                for (OpenBoxBean.DataBean.AwardListBean awardListBean : awardList) {
//                                    String number = "x" + awardListBean.getNum();
//                                    String name = awardListBean.getName();
//                                    builder.append(name);
//                                    builder.append(number);
//                                    builder.append(",");
//                                }
//                                builder.append("?????????????????????");

                                //????????????????????????20??????
                                if (newAwardList.size() > 0 || !TextUtils.isEmpty(openBoxBean.getData().getAward_tips())) {

                                    LoginData loginData = UserManager.getUser();
                                    MessageBean messageBean = new MessageBean();
                                    messageBean.setNickName(loginData.getNickname());
                                    messageBean.setUser_id(loginData.getUserId() + "");
                                    messageBean.box_class = String.valueOf(openBoxBean.getData().getBox_class());
                                    messageBean.awardList = newAwardList;
                                    messageBean.setMessage(openBoxBean.getData().getAward_tips());
//                                    messageBean.award_tips = openBoxBean.getData().getAward_tips();
                                    messageBean.setMessageType("13");

                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setStateMessage(StateMessage.PEOPLE_OPEN_GEMSTONE);
                                    messageEvent.setObject(messageBean);
                                    EventBus.getDefault().post(messageEvent);

                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        BToast.showText(mContext, t.getMessage());
//                        touMingDialog.dismiss();
                        setCanceledOnTouchOutside(true);
                        boo(true);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (DUIHUAN.equals(tag)) {
            getBaoXiang();
        } else if (GOUMAIYAOSHI.equals(tag)) {
            getBaoXiang();
        } else if (XIANSHIWANBI.equals(tag)) {
            getBaoXiang();
        } else if ("donghuawancheng".equals(tag)) {
//            if (touMingDialog != null) {
//                touMingDialog.dismiss();
//            }
            this.setCanceledOnTouchOutside(true);
            boo(true);
        }
    }

    private void boo(boolean booo) {
        mImgTenOpen.setEnabled(booo);
        mImgOpenOne.setEnabled(booo);
        mImgOpenHundred.setEnabled(booo);
    }
}
