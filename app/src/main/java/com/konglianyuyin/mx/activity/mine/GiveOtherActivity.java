package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.message.RedPackageMessage;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.bean.ZengSongBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.ext.ExtConfig;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ClearEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.NativeObject;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class GiveOtherActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.edt_num)
    ClearEditText edtNum;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_sure)
    TextView tvSure;

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
        return R.layout.activity_give_other;
    }

    private void loadData() {
        RxUtils.loading(commonModel.my_store(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        tvAllNum.setText(Html.fromHtml("余额<font color='#8245FF'>" + moneyBean.getData().get(0).getMizuan() + "</font>钻石"));
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbarTitle.setText("转赠");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            Glide.with(this).load(getIntent().getStringExtra("img")).into(ivCover);
            tvUserName.setText(getIntent().getStringExtra("name"));
            tvUserId.setText(getIntent().getStringExtra("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData();
        edtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    tvSure.setBackgroundDrawable(getDrawable(R.drawable.shape_my_zhuanzeng_no));
                } else {
                    tvSure.setBackgroundDrawable(getDrawable(R.drawable.shape_my_zhuanzeng_yes));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvSure.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtNum.getText().toString())) {
                showMessage("数量不能为空");
                return;
            }

            RxUtils.loading(commonModel.zengsong(getIntent().getStringExtra("id"), edtNum.getText().toString(),String.valueOf(UserManager.getUser().getToken())))
                    .subscribe(new ErrorHandleSubscriber<ZengSongBean>(mErrorHandler) {
                        @Override
                        public void onNext(ZengSongBean zengSongBean) {
                            if (ExtConfig.isTransfer){

                                if (ExtConfig.isRegisterMsg){
                                    // 构造 RedPackageMessage 实例
                                    RedPackageMessage myTextMessage = RedPackageMessage.obtain( edtNum.getText().toString()+"金币");
                                    Message myMessage = Message.obtain(getIntent().getStringExtra("id"), Conversation.ConversationType.PRIVATE, myTextMessage);
                                    RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                        @Override
                                        public void onAttached(Message message) {
                                            LogUtils.debugInfo("消息本地数据库存储成功的回调");
                                            LogUtils.debugInfo( JSON.toJSONString(message));
                                        }

                                        @Override
                                        public void onSuccess(Message message) {
                                            LogUtils.debugInfo("消息通过网络发送成功的回调");
                                            LogUtils.debugInfo(JSON.toJSONString(message));
                                            showMessage("消息发送成功");
                                        }

                                        @Override
                                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                            LogUtils.debugInfo("消息发送失败的回调");
                                            LogUtils.debugInfo(JSON.toJSONString(message));
                                            showMessage(message.getExtra());
                                        }
                                    });
                                }else {
                                    // 构造 TextMessage 实例
                                    TextMessage myTextMessage = TextMessage.obtain( edtNum.getText().toString()+"金币\n金币转赠");
                                    Message myMessage = Message.obtain(getIntent().getStringExtra("id"), Conversation.ConversationType.PRIVATE, myTextMessage);
                                    RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                        @Override
                                        public void onAttached(Message message) {
                                            LogUtils.debugInfo("消息本地数据库存储成功的回调");
                                            LogUtils.debugInfo( JSON.toJSONString(message));
                                        }

                                        @Override
                                        public void onSuccess(Message message) {
                                            LogUtils.debugInfo("消息通过网络发送成功的回调");
                                            LogUtils.debugInfo(JSON.toJSONString(message));
                                            showMessage("消息发送成功");
                                        }

                                        @Override
                                        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                            LogUtils.debugInfo("消息发送失败的回调");
                                            LogUtils.debugInfo(JSON.toJSONString(message));
                                            showMessage(message.getExtra());
                                        }
                                    });
                                }

                            }
                            showMessage("转赠成功");
                            finish();
                        }
                    });
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
