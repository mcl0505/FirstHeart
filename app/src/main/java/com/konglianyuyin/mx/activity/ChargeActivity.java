package com.konglianyuyin.mx.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.ChargeAdapter;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.GoodsListBean;
import com.konglianyuyin.mx.bean.PayBean;
import com.konglianyuyin.mx.bean.WxEndBean;
import com.konglianyuyin.mx.bean.Wxmodel;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.utils.PayResult;
import com.konglianyuyin.mx.view.MyGridView;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import anet.channel.util.StringUtils;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * ??????
 *
 * mizuan = ??????
 * mibi = ??????
 *
 */
public class ChargeActivity extends MyBaseArmActivity {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rightTitle)
    TextView rightTitle;
    @BindView(R.id.rightConfirm)
    ShapeTextView rightConfirm;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textNum)
    TextView textNum;
    @BindView(R.id.myGrid)
    MyGridView myGrid;
    @BindView(R.id.textVx)
    TextView textVx;
    @BindView(R.id.textZfb)
    TextView textZfb;
    @BindView(R.id.textZfbH5)
    TextView textZfbH5;
    @BindView(R.id.btn_chongzhi)
    ShapeTextView btnChongzhi;
    @BindView(R.id.yonghu)
    TextView xieyiText;

    private int type;

    private String payType;
    private String price;

    @Inject
    CommonModel commonModel;

    private ChargeAdapter chargeAdapter;

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
        return R.layout.activity_charge;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        chargeAdapter = new ChargeAdapter(this);
        myGrid.setAdapter(chargeAdapter);
        RxUtils.loading(commonModel.getGoodsList(UserManager.getUser().getNewtoken(),String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<GoodsListBean>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsListBean goodsList) {
                        // cnylingshi ????????????
                        textNum.setText(TextUtils.isEmpty(goodsList.getData().getBalance()) ? "0" : goodsList.getData().getBalance());
                        chargeAdapter.getList_adapter().clear();
                        chargeAdapter.getList_adapter().addAll(goodsList.getData().getGoodslist());
                        chargeAdapter.notifyDataSetChanged();
                    }
                });
        myGrid.setOnItemClickListener((parent, view, position, id) -> {
            List<GoodsListBean.DataBean.GoodslistBean> list_adapter = chargeAdapter.getList_adapter();
            if (list_adapter.get(position).isSelect()) {

            } else {
                for (GoodsListBean.DataBean.GoodslistBean list : list_adapter) {
                    list.setSelect(false);
                }
                list_adapter.get(position).setSelect(true);
                chargeAdapter.notifyDataSetChanged();
            }

            price = list_adapter.get(position).getPrice();
        });
    }

    @OnClick({R.id.textVx, R.id.textZfb, R.id.textZfbH5, R.id.btn_chongzhi,R.id.yonghu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textVx:
                textVx.setSelected(true);
                textZfb.setSelected(false);
                textZfbH5.setSelected(false);
                payType = "2";
                break;
            case R.id.textZfb:
                textVx.setSelected(false);
                textZfb.setSelected(true);
                textZfbH5.setSelected(false);
                payType = "1";
                break;
            case R.id.textZfbH5:
                textVx.setSelected(false);
                textZfb.setSelected(false);
                textZfbH5.setSelected(true);
                break;
            case R.id.btn_chongzhi:
                List<GoodsListBean.DataBean.GoodslistBean> list_adapter = chargeAdapter.getList_adapter();
                List<GoodsListBean.DataBean.GoodslistBean> listNew = new ArrayList<>();
                for (GoodsListBean.DataBean.GoodslistBean list : list_adapter) {
                    if (list.isSelect()) {
                        listNew.add(list);
                    }
                }
                if (listNew.size() > 0) {
                    if (textVx.isSelected()) {
                        loadWxData(String.valueOf(listNew.get(0).getId()));
                    } else if (textZfb.isSelected()) {
                        loadZfbData(String.valueOf(listNew.get(0).getId()));
                    } else if (textZfbH5.isSelected()) {
                        loadZfbH5Data(String.valueOf(listNew.get(0).getId()));
                    } else if (!textVx.isSelected() && !textZfb.isSelected() && !textZfbH5.isSelected()) {
                        showToast("?????????????????????");
                    }
                } else {
                    showToast("?????????????????????");
                }
                break;
            case R.id.yonghu:
                Intent intent = new Intent(ChargeActivity.this, BaseWebActivity.class);
                intent.putExtra("url", Api.APP_DOMAIN_NoApi + "/index/index/show_content?id=6");
                intent.putExtra("title", "????????????");
                startActivity(intent);
                break;
        }
    }

    /**
     * ?????????????????????
     * payType  1?????????   2??????
     */
    private void loadZfbData(String goods_id) {
        RxUtils.loading(commonModel.getRechargeOrder(payType,price,goods_id,String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<PayBean>(mErrorHandler) {
                    @Override
                    public void onNext(PayBean payBean) {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(ChargeActivity.this);
                                Map<String, String> result = alipay.payV2(payBean.getData(),
                                        true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // ??????????????????
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }
 /**
     * ???????????????H5??????
     */
    private void loadZfbH5Data(String goods_id) {
        RxUtils.loading(commonModel.rechargePay(String.valueOf(UserManager.getUser().getUserId()),
                goods_id, "3"), this)
                .subscribe(new ErrorHandleSubscriber<PayBean>(mErrorHandler) {
                    @Override
                    public void onNext(PayBean payBean) {
                        if (!StringUtils.isBlank(payBean.getData())){
                            Intent intent = new Intent(ChargeActivity.this, BaseWebActivity.class);
                            intent.putExtra("url", payBean.getData());
                            intent.putExtra("title", "?????????H5??????");
                            startActivity(intent);
                        }
                    }
                });
    }

    /**
     * ??????????????????
     */
    private void loadWxData(String goods_id) {
        RxUtils.loading(commonModel.getRechargeWxOrder(payType+"",price,goods_id,String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<Wxmodel>(mErrorHandler) {
                    @Override
                    public void onNext(Wxmodel wxmodel) {
                        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(ChargeActivity.this,
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
                        request.packageValue ="Sign=WXPay";
                        request.nonceStr = wxmodel.getData().getNoncestr();
                        request.timeStamp =wxmodel.getData().getTimestamp();
                        request.sign = wxmodel.getData().getSign();
                        request.checkArgs();
                        boolean send = mWxApi.sendReq(request);
                    }
                });
    }


    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            PayResult payResult2 = new PayResult((Map<String, String>) msg.obj);
//            LogUtils.e("sgm","====????????????" + payResult2.getResult());
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
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
                        EventBus.getDefault().post(new FirstEvent("????????????", Constant.CHONGZHISHUAXIN));
                        loadYue();
                    } else {
                        // ?????????
                        showToast("????????????,?????????");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * ????????????
     */
    private void loadYue() {
        RxUtils.loading(commonModel.getGoodsList(UserManager.getUser().getNewtoken(),String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<GoodsListBean>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsListBean goodsList) {
                        textNum.setText(TextUtils.isEmpty(goodsList.getData().getBalance()) ? "0" : goodsList.getData().getBalance());
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type == 1) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                startActivity(new Intent(this, AdminHomeActivity.class));
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarBack.setOnClickListener(v -> {
            if (type == 1) {
                startActivity(new Intent(this, AdminHomeActivity.class));
                finish();
            } else {
                finish();
            }
        });

        //?????????h5?????????????????????????????????
        EventBus.getDefault().post(new FirstEvent("????????????", Constant.CHONGZHISHUAXIN));
        loadYue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        System.out.println(event.getMsg());
        String tag = event.getTag();
        String msg = event.getMsg();
        if (Constant.WEIXINZHIFU.equals(tag)) {
            WxEndBean baseResp = JSON.parseObject(msg, WxEndBean.class);
            if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                if (TextUtils.equals(baseResp.getErrCode(), "0")) {
                    toast("????????????");
                    loadYue();
                    EventBus.getDefault().post(new FirstEvent("????????????", Constant.CHONGZHISHUAXIN));
                } else if (TextUtils.equals(baseResp.getErrCode(), "-2")) {
                    toast("????????????");
                } else {
                    // ?????????
                    showToast("????????????,?????????");
                }
            } else {
                // ?????????
                showToast("????????????,?????????");
            }
        }
    }
}
