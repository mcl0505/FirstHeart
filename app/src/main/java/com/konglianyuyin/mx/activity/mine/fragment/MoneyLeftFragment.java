package com.konglianyuyin.mx.activity.mine.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.ChargeActivity;
import com.konglianyuyin.mx.activity.mine.MyGiveMainActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.JIEBANGALI;

public class MoneyLeftFragment extends MyBaseArmFragment {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.textZuanNum)
    TextView textZuanNum;
    @BindView(R.id.imgCHongzhi)
    TextView imgCHongzhi;
    Unbinder unbinder;
    @BindView(R.id.imgZhuanZeng)
    TextView imgZhuanZeng;
    private int mCanZs; // 0不能，1能

    public static MoneyLeftFragment newInstance(int canZs) {
        Bundle arguments = new Bundle();
        arguments.putInt("CanZs", canZs);
        MoneyLeftFragment fl = new MoneyLeftFragment();
        fl.setArguments(arguments);
        return fl;
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_left, container, false);
        return view;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    private int page = 1;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loadData();
        mCanZs = getArguments().getInt("CanZs");
        if(mCanZs == 1) imgZhuanZeng.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (Constant.CHONGZHISHUAXIN.equals(tag)) {
            loadData();
        } else if (JIEBANGALI.equals(tag)) {
            loadData();
        }
    }

    private void loadData() {
        RxUtils.loading(commonModel.my_store(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        textZuanNum.setText(moneyBean.getData().get(0).getMizuan());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.imgZhuanZeng, R.id.imgCHongzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgZhuanZeng:
                ArmsUtils.startActivity(MyGiveMainActivity.class);
                break;
            case R.id.imgCHongzhi:
                ArmsUtils.startActivity(ChargeActivity.class);
                break;
        }
    }
}
