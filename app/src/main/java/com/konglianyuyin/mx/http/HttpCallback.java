package com.konglianyuyin.mx.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Created by cxf on 2017/8/7.
 */

public abstract class HttpCallback extends AbsCallback<JsonBean> {


    @Override
    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
        return JSON.parseObject(response.body().string(), JsonBean.class);
    }

    @Override
    public void onSuccess(Response<JsonBean> response) {
        JsonBean bean = response.body();
        if (bean != null) {
            if (1 == bean.getCode()) {
                onSuccess(bean.getCode(), bean.getMessage(), bean.getData());
            } else {
                Log.e("HttpCallback", "服务器返回值异常--->ret: " + bean.getCode() + " msg: " + bean.getMessage());
            }
        } else {
            Log.e("HttpCallback", "服务器返回值异常--->ret: " + bean.getCode() + " msg: " + bean.getMessage());
        }

    }

    @Override
    public void onError(Response<JsonBean> response) {
        Throwable t = response.getException();
        Log.e("HttpCallback", "网络请求错误---->" + t.getClass() + " : " + t.getMessage());

        onError();
    }

    public void onError() {

    }


    public abstract void onSuccess(int code, String msg, Object info);

    @Override
    public void onStart(Request<JsonBean, ? extends Request> request) {
        onStart();
    }

    public void onStart() {
    }

    @Override
    public void onFinish() {

    }



}
