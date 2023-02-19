package com.konglianyuyin.mx.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.konglianyuyin.mx.utils.LogUtils;
import com.konglianyuyin.mx.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String TAG = WXPayEntryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay);
        api = WXAPIFactory.createWXAPI(this, "wx7b991e43bc9b5814");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.e(TAG, "WX code:------>" + baseResp.errCode);
        LogUtils.e(TAG, "WX code:------>" + baseResp.errStr);

        switch (baseResp.errCode) {
            case 0:
                //成功
                LogUtils.e("TAG","111111");
                ToastUtil.showToast(WXPayEntryActivity.this,"支付成功");
                break;
            case -1:
                //失败
                LogUtils.e("TAG","2222222");
                ToastUtil.showToast(WXPayEntryActivity.this,"支付失败");
                break;
            case -2:
                //用户取消
                LogUtils.e("TAG","33333333");
                ToastUtil.showToast(WXPayEntryActivity.this,"支付取消");
                break;
        }
        finish();
    }
}