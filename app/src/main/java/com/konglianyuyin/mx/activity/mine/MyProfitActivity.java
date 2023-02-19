package com.konglianyuyin.mx.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.IncomeBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ShapeTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 我的收益
 */
public class MyProfitActivity extends MyBaseArmActivity {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.ll2)
    LinearLayout ll2;

    @Inject
    CommonModel commonModel;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rightTitle)
    TextView rightTitle;
    @BindView(R.id.rightConfirm)
    ShapeTextView rightConfirm;
    @BindView(R.id.toolbar_right)
    RelativeLayout toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv22)
    TextView tv22;

    private String is_card;
    private IncomeBean mIncomeBean;

    int mCurrentPosition = 0;

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
        return R.layout.activity_my_profit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        loaData();

        ll1.setOnClickListener(v -> {
            if (mCurrentPosition == 0) {
                return;
            }
            mCurrentPosition = 0;
//            line1.setVisibility(View.VISIBLE);
//            line2.setVisibility(View.GONE);
            tv11.setTextSize(18);
            tv11.setTextColor(getResources().getColor(R.color.white));
            tv22.setTextSize(14);
            tv22.setTextColor(getResources().getColor(R.color.color_F9F9F9));
            loaData();
//            if (mIncomeBean != null) {
//                text1.setText(mIncomeBean.getData().getGift_income().getYue());
//                text2.setText(mIncomeBean.getData().getGift_income().getDay_sum());
//                text3.setText(mIncomeBean.getData().getGift_income().getWeek_sum());
//                text4.setText(mIncomeBean.getData().getGift_income().getMon_sum());
//                text5.setText(mIncomeBean.getData().getGift_income().getLast_mon_sum());
//            }
        });
        ll2.setOnClickListener(v -> {
            if (mCurrentPosition == 1) {
                return;
            }
            mCurrentPosition = 1;
            tv22.setTextSize(18);
            tv22.setTextColor(getResources().getColor(R.color.white));
            tv11.setTextSize(14);
            tv11.setTextColor(getResources().getColor(R.color.color_F9F9F9));
//            line1.setVisibility(View.GONE);
//            line2.setVisibility(View.VISIBLE);
            loaData();
//            if (mIncomeBean != null) {
//                text1.setText(mIncomeBean.getData().getRoom_income().getYue());
//                text2.setText(mIncomeBean.getData().getRoom_income().getDay_sum());
//                text3.setText(mIncomeBean.getData().getRoom_income().getWeek_sum());
//                text4.setText(mIncomeBean.getData().getRoom_income().getMon_sum());
//                text5.setText(mIncomeBean.getData().getRoom_income().getLast_mon_sum());
//            }
        });
    }

    private void loaData() {
        showDialogLoding();
        RxUtils.loading(commonModel.user_income(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<IncomeBean>(mErrorHandler) {
                    @Override
                    public void onNext(IncomeBean incomeBean) {
                        disDialogLoding();
                        mIncomeBean = incomeBean;
                        if (incomeBean.getData().getGift_income().getIs_leader() == 1) {
                            ll2.setVisibility(View.VISIBLE);

                        } else {
                            ll2.setVisibility(View.GONE);

                        }

                        if (mCurrentPosition == 0) {
                            text1.setText(String.valueOf(incomeBean.getData().getGift_income().getYue()));
                            text2.setText(String.valueOf(incomeBean.getData().getGift_income().getDay_sum()));
                            text3.setText(String.valueOf(incomeBean.getData().getGift_income().getWeek_sum()));
                            text4.setText(String.valueOf(incomeBean.getData().getGift_income().getMon_sum()));
                            text5.setText(String.valueOf(incomeBean.getData().getGift_income().getLast_mon_sum()));
                        } else {

                            if (incomeBean.getData() != null) {

                                IncomeBean.DataBean.RoomIncomeBean room_income = incomeBean.getData().getRoom_income();

                                if (room_income != null) {
                                    text1.setText(String.valueOf(room_income.getYue()));
                                    text2.setText(String.valueOf(room_income.getDay_sum()));
                                    text3.setText(String.valueOf(room_income.getWeek_sum()));
                                    text4.setText(String.valueOf(room_income.getMon_sum()));
                                    text5.setText(String.valueOf(room_income.getLast_mon_sum()));
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
