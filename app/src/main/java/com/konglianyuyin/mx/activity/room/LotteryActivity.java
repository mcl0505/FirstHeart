package com.konglianyuyin.mx.activity.room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.LotteryBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.LogUtils;
import com.konglianyuyin.mx.utils.SpannableStringUtils;
import com.konglianyuyin.mx.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class LotteryActivity extends MyBaseArmActivity {

    @Inject
    CommonModel commonModel;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_lottery_rule)
    TextView tvLotteryRule;
    @BindView(R.id.iv_card_one)
    ImageView ivCardOne;
    @BindView(R.id.cl_card_one)
    ConstraintLayout clCardOne;
    @BindView(R.id.iv_card_two)
    ImageView ivCardTwo;
    @BindView(R.id.cl_card_two)
    ConstraintLayout clCardTwo;
    @BindView(R.id.iv_card_three)
    ImageView ivCardThree;
    @BindView(R.id.cl_card_three)
    ConstraintLayout clCardThree;
    @BindView(R.id.iv_card_four)
    ImageView ivCardFour;
    @BindView(R.id.cl_card_four)
    ConstraintLayout clCardFour;
    @BindView(R.id.iv_card_five)
    ImageView ivCardFive;
    @BindView(R.id.cl_card_five)
    ConstraintLayout clCardFive;
    @BindView(R.id.iv_card_ten)
    ImageView ivCardTen;
    @BindView(R.id.cl_card_ten)
    ConstraintLayout clCardTen;
    @BindView(R.id.iv_card_nine)
    ImageView ivCardNine;
    @BindView(R.id.cl_card_nine)
    ConstraintLayout clCardNine;
    @BindView(R.id.iv_card_eight)
    ImageView ivCardEight;
    @BindView(R.id.cl_card_eight)
    ConstraintLayout clCardEight;
    @BindView(R.id.iv_card_seven)
    ImageView ivCardSeven;
    @BindView(R.id.cl_card_seven)
    ConstraintLayout clCardSeven;
    @BindView(R.id.iv_card_six)
    ImageView ivCardSix;
    @BindView(R.id.cl_card_six)
    ConstraintLayout clCardSix;
    @BindView(R.id.iv_center_yellow_bg)
    ImageView ivCenterYellowBg;
    @BindView(R.id.tv_price_ten)
    TextView tvPriceTen;
    @BindView(R.id.tv_price_bai)
    TextView tvPriceBai;
    @BindView(R.id.tv_price_qian)
    TextView tvPriceQian;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_lottery_text)
    TextView tvLotteryText;
    @BindView(R.id.iv_lottery_bg_one)
    ImageView ivLotteryBgOne;
    @BindView(R.id.iv_gift_one)
    ImageView ivGiftOne;
    @BindView(R.id.tv_lottery_status_one)
    TextView tvLotteryStatusOne;
    @BindView(R.id.tv_lottery_money_one)
    TextView tvLotteryMoneyOne;
    @BindView(R.id.ll_lottery_one)
    LinearLayout llLotteryOne;
    @BindView(R.id.iv_lottery_bg_two)
    ImageView ivLotteryBgTwo;
    @BindView(R.id.iv_gift_two)
    ImageView ivGiftTwo;
    @BindView(R.id.tv_lottery_status_two)
    TextView tvLotteryStatusTwo;
    @BindView(R.id.tv_lottery_money_two)
    TextView tvLotteryMoneyTwo;
    @BindView(R.id.ll_lottery_two)
    LinearLayout llLotteryTwo;
    @BindView(R.id.iv_lottery_bg_three)
    ImageView ivLotteryBgThree;
    @BindView(R.id.iv_gift_three)
    ImageView ivGiftThree;
    @BindView(R.id.tv_lottery_status_three)
    TextView tvLotteryStatusThree;
    @BindView(R.id.tv_lottery_money_three)
    TextView tvLotteryMoneyThree;
    @BindView(R.id.ll_lottery_three)
    LinearLayout llLotteryThree;
    @BindView(R.id.iv_lottery_bg_four)
    ImageView ivLotteryBgFour;
    @BindView(R.id.iv_gift_four)
    ImageView ivGiftFour;
    @BindView(R.id.tv_lottery_status_four)
    TextView tvLotteryStatusFour;
    @BindView(R.id.tv_lottery_money_four)
    TextView tvLotteryMoneyFour;
    @BindView(R.id.ll_lottery_four)
    LinearLayout llLotteryFour;
    @BindView(R.id.iv_lottery_bg_five)
    ImageView ivLotteryBgFive;
    @BindView(R.id.iv_gift_five)
    ImageView ivGiftFive;
    @BindView(R.id.tv_lottery_status_five)
    TextView tvLotteryStatusFive;
    @BindView(R.id.tv_lottery_money_five)
    TextView tvLotteryMoneyFive;
    @BindView(R.id.ll_lottery_five)
    LinearLayout llLotteryFive;
    @BindView(R.id.iv_lottery_bg_ten)
    ImageView ivLotteryBgTen;
    @BindView(R.id.iv_gift_ten)
    ImageView ivGiftTen;
    @BindView(R.id.tv_lottery_status_ten)
    TextView tvLotteryStatusTen;
    @BindView(R.id.tv_lottery_money_ten)
    TextView tvLotteryMoneyTen;
    @BindView(R.id.ll_lottery_ten)
    LinearLayout llLotteryTen;
    @BindView(R.id.iv_lottery_bg_nine)
    ImageView ivLotteryBgNine;
    @BindView(R.id.iv_gift_nine)
    ImageView ivGiftNine;
    @BindView(R.id.tv_lottery_status_nine)
    TextView tvLotteryStatusNine;
    @BindView(R.id.tv_lottery_money_nine)
    TextView tvLotteryMoneyNine;
    @BindView(R.id.ll_lottery_nine)
    LinearLayout llLotteryNine;
    @BindView(R.id.iv_lottery_bg_eight)
    ImageView ivLotteryBgEight;
    @BindView(R.id.iv_gift_eight)
    ImageView ivGiftEight;
    @BindView(R.id.tv_lottery_status_eight)
    TextView tvLotteryStatusEight;
    @BindView(R.id.tv_lottery_money_eight)
    TextView tvLotteryMoneyEight;
    @BindView(R.id.ll_lottery_eight)
    LinearLayout llLotteryEight;
    @BindView(R.id.iv_lottery_bg_seven)
    ImageView ivLotteryBgSeven;
    @BindView(R.id.iv_gift_seven)
    ImageView ivGiftSeven;
    @BindView(R.id.tv_lottery_status_seven)
    TextView tvLotteryStatusSeven;
    @BindView(R.id.tv_lottery_money_seven)
    TextView tvLotteryMoneySeven;
    @BindView(R.id.ll_lottery_seven)
    LinearLayout llLotterySeven;
    @BindView(R.id.iv_lottery_bg_six)
    ImageView ivLotteryBgSix;
    @BindView(R.id.iv_gift_six)
    ImageView ivGiftSix;
    @BindView(R.id.tv_lottery_status_six)
    TextView tvLotteryStatusSix;
    @BindView(R.id.tv_lottery_money_six)
    TextView tvLotteryMoneySix;
    @BindView(R.id.ll_lottery_six)
    LinearLayout llLotterySix;
    @BindView(R.id.tv_lottery_history)
    TextView tvLotteryHistory;

    private List<String> selectedCard;
    private String price = "10";
    private boolean isInLottery;

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
        return R.layout.activity_lottery;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        selectedCard = new ArrayList<>();
    }

    @OnClick({R.id.iv_back, R.id.tv_lottery_rule, R.id.cl_card_one, R.id.cl_card_two, R.id.cl_card_three, R.id.cl_card_four,
            R.id.cl_card_five, R.id.cl_card_ten, R.id.cl_card_nine, R.id.cl_card_eight, R.id.cl_card_seven, R.id.cl_card_six,
            R.id.tv_price_ten, R.id.tv_price_bai, R.id.tv_price_qian, R.id.tv_start,R.id.tv_lottery_history})
    public void onClick(View view) {
        if(isInLottery){
            toast("正在开奖中，请稍后");
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_lottery_rule:
                Intent intent = new Intent(LotteryActivity.this, BaseWebActivity.class);
                intent.putExtra("url", Api.APP_DOMAIN+"/index/index/show?id=12");
                intent.putExtra("title", "塔罗牌活动规则");
                startActivity(intent);
                break;
            case R.id.tv_lottery_history:
                ArmsUtils.startActivity(LotteryHistoryActivity.class);
                break;
            case R.id.cl_card_one:
                addCard("0");
                break;
            case R.id.cl_card_two:
                addCard("1");
                break;
            case R.id.cl_card_three:
                addCard("2");
                break;
            case R.id.cl_card_four:
                addCard("3");
                break;
            case R.id.cl_card_five:
                addCard("4");
                break;
            case R.id.cl_card_ten:
                addCard("9");
                break;
            case R.id.cl_card_nine:
                addCard("8");
                break;
            case R.id.cl_card_eight:
                addCard("7");
                break;
            case R.id.cl_card_seven:
                addCard("6");
                break;
            case R.id.cl_card_six:
                addCard("5");
                break;
            case R.id.tv_price_ten:
                changeCheckedPrice("10");
                break;
            case R.id.tv_price_bai:
                changeCheckedPrice("100");
                break;
            case R.id.tv_price_qian:
                changeCheckedPrice("1000");
                break;
            case R.id.tv_start:
                startLottery();
                break;
        }
    }

    private void startLottery() {
        showDialogLoding();
        if (selectedCard == null || selectedCard.size() == 0) {
            ToastUtil.showToast(this, "未选择塔罗牌");
            disDialogLoding();
            return;
        }

        isInLottery = true;

        StringBuffer sb = new StringBuffer();
        for (String id : selectedCard) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        LogUtils.e("TAG", "token" + UserManager.getUser().getNewtoken());
        RxUtils.loading(commonModel.lottery(UserManager.getUser().getNewtoken(), UserManager.getUser().getUserId() + "", sb.toString(), price), this)
                .subscribe(new ErrorHandleSubscriber<LotteryBean>(mErrorHandler) {
                    @Override
                    public void onNext(LotteryBean lotteryBean) {
                        disDialogLoding();
                        if (lotteryBean.getCode() == 1) {
                            lotteryCradShow(lotteryBean);
                        } else {
                            ToastUtil.showToast(LotteryActivity.this, lotteryBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                        toast(t.getMessage());
                    }
                });
    }

    private void lotteryCradShow(LotteryBean lotteryBean) {
        if(lotteryBean.getData().getState() == 1){
            tvLotteryText.setText("恭喜获赠价值" + lotteryBean.getData().getZjprice() + "灵石的 海洋之心！");
        }else{
            tvLotteryText.setText("");
        }

        for (int i = 0;i<selectedCard.size();i++){
            LogUtils.e("TAG","select ="+selectedCard.get(i));
            if(lotteryBean.getData().getPk().equals(selectedCard.get(i))){
                setCardView(selectedCard.get(i),3,lotteryBean.getData().getZjprice());
            }else{
                setCardView(selectedCard.get(i),2,"");
            }
            setCardBg(selectedCard.get(i),false);
        }
        mHandler.sendEmptyMessageDelayed(0, 5000);
    }

    private void setCardView(String cardId, int type,String money) {
        switch (cardId){
            case "0":
                cardViewOneChange(type,money);
                break;
            case "1":
                cardViewTwoChange(type,money);
                break;
            case "2":
                cardViewThreeChange(type,money);
                break;
            case "3":
                cardViewFourChange(type,money);
                break;
            case "4":
                cardViewFiveChange(type,money);
                break;
            case "5":
                cardViewsixChange(type,money);
                break;
            case "6":
                cardViewSevenChange(type,money);
                break;
            case "7":
                cardViewEightChange(type,money);
                break;
            case "8":
                cardViewNineChange(type,money);
                break;
            case "9":
                cardViewTenChange(type,money);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initCardView();
                    isInLottery = false;
                    break;
            }
        }
    };

    private void initCardView() {
        for(int i = 0;i<10;i++){
            setCardView(i+"",1,"");
        }
        selectedCard.clear();
    }

    private void changeCheckedPrice(String checkedPrice) {
        if (price.equals(checkedPrice)) {
            return;
        }
        price = checkedPrice;
        switch (checkedPrice) {
            case "10":
                tvPriceTen.setBackgroundResource(R.mipmap.icon_bg_button_blue);
                tvPriceBai.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                tvPriceQian.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                break;
            case "100":
                tvPriceTen.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                tvPriceBai.setBackgroundResource(R.mipmap.icon_bg_button_blue);
                tvPriceQian.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                break;
            case "1000":
                tvPriceTen.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                tvPriceBai.setBackgroundResource(R.mipmap.icon_bg_button_gray);
                tvPriceQian.setBackgroundResource(R.mipmap.icon_bg_button_blue);
                break;
        }
    }

    private void addCard(String cardId) {
        if (selectedCard.contains(cardId)) {
            selectedCard.remove(cardId);
            setCardBg(cardId,false);
        } else {
            if (selectedCard.size() < 9) {
                selectedCard.add(cardId);
                setCardBg(cardId,true);
            } else {
                ToastUtil.showToast(this, "最多不超过9张塔罗牌");
            }
        }
    }

    private void setCardBg(String cardId, boolean isAdd) {
        switch (cardId){
            case "0":
                if(isAdd){
                    clCardOne.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardOne.setBackgroundResource(0);
                }
                break;
            case "1":
                if(isAdd){
                    clCardTwo.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardTwo.setBackgroundResource(0);
                }
                break;
            case "2":
                if(isAdd){
                    clCardThree.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardThree.setBackgroundResource(0);
                }
                break;
            case "3":
                if(isAdd){
                    clCardFour.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardFour.setBackgroundResource(0);
                }
                break;
            case "4":
                if(isAdd){
                    clCardFive.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardFive.setBackgroundResource(0);
                }
                break;
            case "5":
                if(isAdd){
                    clCardSix.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardSix.setBackgroundResource(0);
                }
                break;
            case "6":
                if(isAdd){
                    clCardSeven.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardSeven.setBackgroundResource(0);
                }
                break;
            case "7":
                if(isAdd){
                    clCardEight.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardEight.setBackgroundResource(0);
                }
                break;
            case "8":
                if(isAdd){
                    clCardNine.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardNine.setBackgroundResource(0);
                }
                break;
            case "9":
                if(isAdd){
                    clCardTen.setBackgroundResource(R.drawable.shape_bule_stroke);
                }else{
                    clCardTen.setBackgroundResource(0);
                }
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖状态  3开奖中奖
    private void cardViewOneChange(int type,String money){
        LogUtils.e("TAG","cardViewOneChange  type ="+type);
        switch (type){
            case 1:
                ivCardOne.setVisibility(View.VISIBLE);
                ivLotteryBgOne.setImageResource(R.mipmap.bg_not_lottery1);
                ivLotteryBgOne.setVisibility(View.GONE);
                llLotteryOne.setVisibility(View.GONE);
                break;
            case 2:
                ivCardOne.setVisibility(View.GONE);
                ivLotteryBgOne.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardOne.setVisibility(View.GONE);
                ivLotteryBgOne.setVisibility(View.VISIBLE);
                ivLotteryBgOne.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryOne.setVisibility(View.VISIBLE);
                ivGiftOne.setVisibility(View.VISIBLE);
                tvLotteryMoneyOne.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewTwoChange(int type,String money){
        LogUtils.e("TAG","cardViewTwoChange  type ="+type);
        switch (type){
            case 1:
                ivCardTwo.setVisibility(View.VISIBLE);
                ivLotteryBgTwo.setImageResource(R.mipmap.bg_not_lottery2);
                ivLotteryBgTwo.setVisibility(View.GONE);
                llLotteryTwo.setVisibility(View.GONE);
                break;
            case 2:
                ivCardTwo.setVisibility(View.GONE);
                ivLotteryBgTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardTwo.setVisibility(View.GONE);
                ivLotteryBgTwo.setVisibility(View.VISIBLE);
                ivLotteryBgTwo.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryTwo.setVisibility(View.VISIBLE);
                ivGiftTwo.setVisibility(View.VISIBLE);
                tvLotteryMoneyTwo.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewThreeChange(int type,String money){
        LogUtils.e("TAG","cardViewThreeChange  type ="+type);
        switch (type){
            case 1:
                ivCardThree.setVisibility(View.VISIBLE);
                ivLotteryBgThree.setImageResource(R.mipmap.bg_not_lottery3);
                ivLotteryBgThree.setVisibility(View.GONE);
                llLotteryThree.setVisibility(View.GONE);
                break;
            case 2:
                ivCardThree.setVisibility(View.GONE);
                ivLotteryBgThree.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardThree.setVisibility(View.GONE);
                ivLotteryBgThree.setVisibility(View.VISIBLE);
                ivLotteryBgThree.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryThree.setVisibility(View.VISIBLE);
                ivGiftThree.setVisibility(View.VISIBLE);
                tvLotteryMoneyThree.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewFourChange(int type,String money){
        LogUtils.e("TAG","cardViewFourChange  type ="+type);
        switch (type){
            case 1:
                ivCardFour.setVisibility(View.VISIBLE);
                ivLotteryBgFour.setImageResource(R.mipmap.bg_not_lottery4);
                ivLotteryBgFour.setVisibility(View.GONE);
                llLotteryFour.setVisibility(View.GONE);
                break;
            case 2:
                ivCardFour.setVisibility(View.GONE);
                ivLotteryBgFour.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardFour.setVisibility(View.GONE);
                ivLotteryBgFour.setVisibility(View.VISIBLE);
                ivLotteryBgFour.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryFour.setVisibility(View.VISIBLE);
                ivGiftFour.setVisibility(View.VISIBLE);
                tvLotteryMoneyFour.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewFiveChange(int type,String money){
        LogUtils.e("TAG","cardViewFiveChange  type ="+type);
        switch (type){
            case 1:
                ivCardFive.setVisibility(View.VISIBLE);
                ivLotteryBgFive.setImageResource(R.mipmap.bg_not_lottery5);
                ivLotteryBgFive.setVisibility(View.GONE);
                llLotteryFive.setVisibility(View.GONE);
                break;
            case 2:
                ivCardFive.setVisibility(View.GONE);
                ivLotteryBgFive.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardFive.setVisibility(View.GONE);
                ivLotteryBgFive.setVisibility(View.VISIBLE);
                ivLotteryBgFive.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryFive.setVisibility(View.VISIBLE);
                ivGiftFive.setVisibility(View.VISIBLE);
                tvLotteryMoneyFive.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewsixChange(int type,String money){
        LogUtils.e("TAG","cardViewsixChange  type ="+type);
        switch (type){
            case 1:
                ivCardSix.setVisibility(View.VISIBLE);
                ivLotteryBgSix.setImageResource(R.mipmap.bg_not_lottery6);
                ivLotteryBgSix.setVisibility(View.GONE);
                llLotterySix.setVisibility(View.GONE);
                break;
            case 2:
                ivCardSix.setVisibility(View.GONE);
                ivLotteryBgSix.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardSix.setVisibility(View.GONE);
                ivLotteryBgSix.setVisibility(View.VISIBLE);
                ivLotteryBgSix.setImageResource(R.mipmap.bg_not_lottery);
                llLotterySix.setVisibility(View.VISIBLE);
                ivGiftSix.setVisibility(View.VISIBLE);
                tvLotteryMoneySix.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewSevenChange(int type,String money){
        LogUtils.e("TAG","cardViewSevenChange  type ="+type);
        switch (type){
            case 1:
                ivCardSeven.setVisibility(View.VISIBLE);
                ivLotteryBgSeven.setImageResource(R.mipmap.bg_not_lottery7);
                ivLotteryBgSeven.setVisibility(View.GONE);
                llLotterySeven.setVisibility(View.GONE);
                break;
            case 2:
                ivCardSeven.setVisibility(View.GONE);
                ivLotteryBgSeven.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardSeven.setVisibility(View.GONE);
                ivLotteryBgSeven.setVisibility(View.VISIBLE);
                ivLotteryBgSeven.setImageResource(R.mipmap.bg_not_lottery);
                llLotterySeven.setVisibility(View.VISIBLE);
                ivGiftSeven.setVisibility(View.VISIBLE);
                tvLotteryMoneySeven.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewEightChange(int type,String money){
        LogUtils.e("TAG","cardViewEightChange  type ="+type);
        switch (type){
            case 1:
                ivCardEight.setVisibility(View.VISIBLE);
                ivLotteryBgEight.setImageResource(R.mipmap.bg_not_lottery8);
                ivLotteryBgEight.setVisibility(View.GONE);
                llLotteryEight.setVisibility(View.GONE);
                break;
            case 2:
                ivCardEight.setVisibility(View.GONE);
                ivLotteryBgEight.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardEight.setVisibility(View.GONE);
                ivLotteryBgEight.setVisibility(View.VISIBLE);
                ivLotteryBgEight.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryEight.setVisibility(View.VISIBLE);
                ivGiftEight.setVisibility(View.VISIBLE);
                tvLotteryMoneyEight.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewNineChange(int type,String money){
        LogUtils.e("TAG","cardViewNineChange  type ="+type);
        switch (type){
            case 1:
                ivCardNine.setVisibility(View.VISIBLE);
                ivLotteryBgNine.setImageResource(R.mipmap.bg_not_lottery9);
                ivLotteryBgNine.setVisibility(View.GONE);
                llLotteryNine.setVisibility(View.GONE);
                break;
            case 2:
                ivCardNine.setVisibility(View.GONE);
                ivLotteryBgNine.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardNine.setVisibility(View.GONE);
                ivLotteryBgNine.setVisibility(View.VISIBLE);
                ivLotteryBgNine.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryNine.setVisibility(View.VISIBLE);
                ivGiftNine.setVisibility(View.VISIBLE);
                tvLotteryMoneyNine.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

    //type  1初始化到抽奖状态  2开奖没中奖周状态  3开奖中奖
    private void cardViewTenChange(int type,String money){
        LogUtils.e("TAG","cardViewTenChange  type ="+type);
        switch (type){
            case 1:
                ivCardTen.setVisibility(View.VISIBLE);
                ivLotteryBgTen.setImageResource(R.mipmap.bg_not_lottery10);
                ivLotteryBgTen.setVisibility(View.GONE);
                llLotteryTen.setVisibility(View.GONE);
                break;
            case 2:
                ivCardTen.setVisibility(View.GONE);
                ivLotteryBgTen.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivCardTen.setVisibility(View.GONE);
                ivLotteryBgTen.setVisibility(View.VISIBLE);
                ivLotteryBgTen.setImageResource(R.mipmap.bg_not_lottery);
                llLotteryTen.setVisibility(View.VISIBLE);
                ivGiftTen.setVisibility(View.VISIBLE);
                tvLotteryStatusTen.setText("海洋之心");
                tvLotteryMoneyTen.setText(SpannableStringUtils.getInstance().getSizeChangePrice(money));
                break;
        }
    }

}