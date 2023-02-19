package com.konglianyuyin.mx.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.AgreementBean;
import com.konglianyuyin.mx.bean.DengJiBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.JudgeImageUtil;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 我的等级页面
 * 老王
 */
public class GradeCenterActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.head_image)
    CircularImage headImage;
    @BindView(R.id.nack_name)
    TextView nackName;
    @BindView(R.id.now_image)
    ImageView nowImage;
    @BindView(R.id.next_image)
    ImageView nextImage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.jinrui)
    TextView jinrui;
    @BindView(R.id.xingrui_now_image)
    ImageView xingruiNowImage;
    @BindView(R.id.xingrui_next_image)
    ImageView xingruiNextImage;
    @BindView(R.id.progress_bar2)
    ProgressBar progressBar2;
    @BindView(R.id.xingrui)
    TextView xingrui;

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
        return R.layout.activity_grade_center;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getLevelCenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle("我的等级", true);

        /*setToolbarRightText("等级说明", v -> {
            Intent intent = new Intent(this, DengJiShuoMingActivity.class);
            intent.putExtra("tag", "1");
            ArmsUtils.startActivity(intent);
        }, R.color.white);*/
    }

    //获取数据
    private void getLevelCenter() {
        RxUtils.loading(commonModel.getLevelCenter(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<DengJiBean>(mErrorHandler) {
                    @Override
                    public void onNext(DengJiBean dengJiBean) {
                        ArmsUtils.obtainAppComponentFromContext(GradeCenterActivity.this)
                                .imageLoader()
                                .loadImage(GradeCenterActivity.this, ImageConfigImpl
                                        .builder()
                                        .url(dengJiBean.getData().get(0).getHeadimgurl())
                                        .placeholder(R.mipmap.no_tou)
                                        .imageView(headImage)
                                        .errorPic(R.mipmap.no_tou)
                                        .build());
                        nackName.setText(dengJiBean.getData().get(0).getNickname());

                        JudgeImageUtil.JinRui(dengJiBean.getData().get(0).getGold_level(), nowImage);
                        JudgeImageUtil.JinRui(dengJiBean.getData().get(0).getNext_gold_level(), nextImage);
                        int goldNum = (int) Double.parseDouble(dengJiBean.getData().get(0).getGold_num());
                        int next_gold_num = dengJiBean.getData().get(0).getNext_gold_num();
                        jinrui.setText("贡献值 " + goldNum + "/" + next_gold_num);
                        progressBar.setMax(next_gold_num);
                        progressBar.setProgress(goldNum);

                        JudgeImageUtil.XingRui(dengJiBean.getData().get(0).getStar_level(), xingruiNowImage);
                        JudgeImageUtil.XingRui(dengJiBean.getData().get(0).getNext_star_level(), xingruiNextImage);
                        int starNum = (int) Double.parseDouble(dengJiBean.getData().get(0).getStar_num());
                        int next_star_num = dengJiBean.getData().get(0).getNext_star_num();
                        xingrui.setText("魅力值 " + starNum + "/" + next_star_num);
                        progressBar2.setMax(next_star_num);
                        progressBar2.setProgress(starNum);
                    }
                });
    }

    private void getAgr() {
        RxUtils.loading(commonModel.getAgreement(String.valueOf(1)), this)
                .subscribe(new ErrorHandleSubscriber<AgreementBean>(mErrorHandler) {
                    @Override
                    public void onNext(AgreementBean agreementBean) {
                        Intent intent = new Intent(GradeCenterActivity.this, BaseWebActivity.class);
                        intent.putExtra("url", agreementBean.getData().get(0).getUrl());
                        intent.putExtra("title", agreementBean.getData().get(0).getName());
                        ArmsUtils.startActivity(intent);
                    }
                });
    }
}
