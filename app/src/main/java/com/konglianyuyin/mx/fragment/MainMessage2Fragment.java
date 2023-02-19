package com.konglianyuyin.mx.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.message.MessageOfficeActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.MiniOfficBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imlib.model.Conversation;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 作者:sgm
 * 描述:消息
 */
public class MainMessage2Fragment extends MyBaseArmFragment  implements ImmersionOwner {

    @BindView(R.id.ci_head)
    ImageView ciHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.textTime)
    TextView textTime;
    @BindView(R.id.textNum)
    TextView textNum;
    @BindView(R.id.llRight)
    LinearLayout llRight;
    @BindView(R.id.relativeLayout_main)
    LinearLayout relativeLayoutMain;
    @BindView(R.id.llTop)
    LinearLayout llTop;

    @Inject
    CommonModel commonModel;
    private MessageHeaderFrament mConversationListFragment;

    MyReceiver mMyReceiver;

    private int tag = 0;
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ArmsUtils.inflate(getActivity(), R.layout.fragment_message2);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mMyReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("has_read_office");
        mContext.registerReceiver(mMyReceiver, filter);
        loadData();
    }

    private void loadData() {
        RxUtils.loading(commonModel.mini_official(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MiniOfficBean>(mErrorHandler) {
                    @Override
                    public void onNext(MiniOfficBean miniOfficBean) {
                        initHeaderData(miniOfficBean);
                        mConversationListFragment = new MessageHeaderFrament();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("miniOfficBean",miniOfficBean);
//                        mConversationListFragment.setArguments(bundle);
                        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                                .appendPath("conversationlist")
                                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
//                                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                                .build();
                        mConversationListFragment.setUri(uri);
                        FragmentManager manager = getChildFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.relativeLayout_main, mConversationListFragment);
                        transaction.commitAllowingStateLoss();

                    }
                });
    }

    private void initHeaderData(MiniOfficBean miniOfficBean) {
//        GlideArms.with(getActivity())
//                .load(miniOfficBean.getData().getImg())
//                .placeholder(R.mipmap.icon_msg_default)
//                .error(R.mipmap.icon_msg_default)
//                .circleCrop()
//                .into(ciHead);
//        tvTitle.setText(miniOfficBean.getData().getName());
        tvUserid.setText(miniOfficBean.getData().getTitle());
        int unread = miniOfficBean.getData().getUnread();
        if (unread > 0) {
            llRight.setVisibility(View.VISIBLE);
            textTime.setText(miniOfficBean.getData().getCreated_at());
            textNum.setText(miniOfficBean.getData().getUnread() + "");
        } else {
            llRight.setVisibility(View.GONE);
        }
        llTop.setOnClickListener(v -> {
            ArmsUtils.startActivity(MessageOfficeActivity.class);
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    public void hasRead() {
        RxUtils.loading(commonModel.mini_official(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MiniOfficBean>(mErrorHandler) {
                    @Override
                    public void onNext(MiniOfficBean miniOfficBean) {
//                            mConversationListFragment.refresh(miniOfficBean);
                        initHeaderData(miniOfficBean);
                    }
                });
    }


    //已读了官方通知
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.debugInfo("收到广播了=======");
            hasRead();
        }
    }





    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);
    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void initImmersionBar() {
//
//        ImmersionBar.with(this)
//                .statusBarColor(R.color.mainbg)
//                .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//                .init();//设置状态栏白色

    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean immersionBarEnabled() {//是否用沉浸式
        return true;
    }

}
