package com.konglianyuyin.mx.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.RequestUpMicrophoneBean;

public class RequestUpMicrophoneAdapter extends BaseQuickAdapter<RequestUpMicrophoneBean.DataBean, BaseViewHolder> {

    public RequestUpMicrophoneAdapter() {
        super(R.layout.item_request_up_microphone);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RequestUpMicrophoneBean.DataBean item) {
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .url(item.getHeadimg())
                        .placeholder(R.mipmap.no_tu)
                        .imageView(helper.getView(R.id.iv_head))
                        .errorPic(R.mipmap.no_tu)
                        .build());

        helper.setText(R.id.tv_name,item.getNickname());

        helper.addOnClickListener(R.id.tv_agree)
                .addOnClickListener(R.id.tv_refuse);
    }
}
