package com.konglianyuyin.mx.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.my.CPActivity;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CommentBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.MyPersonalCebterBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.popup.PuTongWindow;
import com.konglianyuyin.mx.service.CommonModel;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.KAIQICPWEI;


/**
 * 第一个CP页面
 * 老王
 */
public class CPAFragment extends MyBaseArmFragment {


    @BindView(R.id.cp_time)
    TextView cpTime;
    @BindView(R.id.cp_rank)
    TextView cpRank;
    @BindView(R.id.cp_one_head)
    RoundedImageView cpOneHead;
    @BindView(R.id.cp_one_name)
    TextView cpOneName;
    @BindView(R.id.one_cp)
    LinearLayout oneCp;
    @BindView(R.id.cp_two_head)
    RoundedImageView cpTwoHead;
    @BindView(R.id.cp_two_name)
    TextView cpTwoName;
    @BindView(R.id.two_cp)
    LinearLayout twoCp;
    @BindView(R.id.cp_xindiantu_one)
    ImageView cpXindiantuOne;
    @BindView(R.id.cp_xindiantu_two)
    ImageView cpXindiantuTwo;
    @BindView(R.id.have_cp)
    RelativeLayout haveCp;
    @BindView(R.id.cp_no)
    LinearLayout cpNo;
    @BindView(R.id.cp_kq)
    ImageView cpKq;
    @BindView(R.id.cp_unopened)
    LinearLayout cpUnopened;
    @BindView(R.id.cp_bag)
    ImageView cpBag;
    @BindView(R.id.cp_da_bj)
    ImageView cpDaBj;

    @Inject
    CommonModel commonModel;

    private ArrayList<MyPersonalCebterBean.DataBean.CplistBean> cpbeanone;
    private PuTongWindow puTongWindow;
    private String fromId;

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cp, container, false);
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

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            cpbeanone = arguments.getParcelableArrayList("cpbeanone");
            fromId = arguments.getString("idd");
            LogUtils.debugInfo("====有木有", fromId);
            cpTime.setText("守护" + cpbeanone.get(0).getDays() + "天");
            if (cpbeanone.get(0).getCp_type() == 1) {  //有cp
                oneCp.setVisibility(View.VISIBLE);
                twoCp.setVisibility(View.VISIBLE);
                haveCp.setVisibility(View.VISIBLE);
                cpRank.setVisibility(View.VISIBLE);
                cpOneName.setVisibility(View.VISIBLE);
                cpTwoName.setVisibility(View.VISIBLE);
                //cp等级
                cpRank.setText("Lv" + cpbeanone.get(0).getCp_level());
                //第一个人的头像
                GlideArms.with(getActivity())
                        .load(cpbeanone.get(0).getUser_head())
                        .placeholder(R.mipmap.gender_zhuce_girl)
                        .error(R.mipmap.gender_zhuce_girl)
                        .circleCrop()
                        .into(cpOneHead);
                //第一个人的昵称
                cpOneName.setText(cpbeanone.get(0).getUser_nick());
                //第二个人的头像
                GlideArms.with(getActivity())
                        .load(cpbeanone.get(0).getFrom_head())
                        .placeholder(R.mipmap.gender_zhuce_girl)
                        .error(R.mipmap.gender_zhuce_girl)
                        .circleCrop()
                        .into(cpTwoHead);
                //第二个人的头像
                cpTwoName.setText(cpbeanone.get(0).getFrom_nick());
                //两个闪电中间的图片
                loadImage(cpBag, cpbeanone.get(0).getBs_img(), 0);
            } else if (cpbeanone.get(0).getCp_type() == 2) { //没有cp
                cpTime.setText(cpbeanone.get(0).getDays());
                oneCp.setVisibility(View.GONE);
                twoCp.setVisibility(View.GONE);
                haveCp.setVisibility(View.GONE);
                cpRank.setVisibility(View.GONE);
                cpUnopened.setVisibility(View.GONE);
                cpOneName.setVisibility(View.GONE);
                cpTwoName.setVisibility(View.GONE);
                cpNo.setVisibility(View.VISIBLE);
            } else if (cpbeanone.get(0).getCp_type() == 3) { //cp栏未开启
                cpTime.setText(cpbeanone.get(0).getDays());
                cpUnopened.setVisibility(View.VISIBLE);
            }
            cpDaBj.setOnClickListener(v -> {
                if (String.valueOf(UserManager.getUser().getUserId()).equals(fromId)) {
                    if (cpbeanone.get(0).getCp_type() == 1) {
                        Intent intent = new Intent(getActivity(), CPActivity.class);
                        intent.putExtra("id", String.valueOf(cpbeanone.get(0).getId()));
                        intent.putExtra("type", "1");
                        ArmsUtils.startActivity(intent);
                    }
                }
            });

            if (String.valueOf(UserManager.getUser().getUserId()).equals(fromId)) {
                cpKq.setOnClickListener(v -> {
                    puTongWindow = new PuTongWindow(getActivity());
                    puTongWindow.showAtLocation(cpDaBj, Gravity.CENTER, 0, 0);
                    puTongWindow.getTitText().setText("是否确认开启CP位");
                    puTongWindow.getCancel().setOnClickListener(v1 -> {
                        puTongWindow.dismiss();
                    });
                    puTongWindow.getSure().setOnClickListener(v1 -> {
                        puTongWindow.dismiss();
                        setCpKq();
                    });
                });
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void setCpKq() {
        RxUtils.loading(commonModel.openCPCard("xx"), this)
                .subscribe(new ErrorHandleSubscriber<CommentBean>(mErrorHandler) {
                    @Override
                    public void onNext(CommentBean commentBean) {
                        showToast(commentBean.getMessage());
                        if (commentBean.getCode() == 1) {
                            EventBus.getDefault().post(new FirstEvent("开启CP位成功", KAIQICPWEI));
                        }
                    }
                });
    }

}
