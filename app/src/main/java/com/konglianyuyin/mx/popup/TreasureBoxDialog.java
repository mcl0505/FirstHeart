package com.konglianyuyin.mx.popup;

import static com.konglianyuyin.mx.utils.Constant.DUIHUAN;
import static com.konglianyuyin.mx.utils.Constant.GOUMAIYAOSHI;
import static com.konglianyuyin.mx.utils.Constant.XIANSHIWANBI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.ChargeActivity;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.TreasureBoxAdapter;
import com.konglianyuyin.mx.adapter.WonPopupAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BaoXiangBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.MessageBean;
import com.konglianyuyin.mx.bean.MessageEvent2;
import com.konglianyuyin.mx.bean.OpenBoxBean;
import com.konglianyuyin.mx.bean.StateMessage;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.BToast;
import com.konglianyuyin.mx.utils.DpUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class TreasureBoxDialog extends Dialog {
    @BindView(R.id.rootView)
    ConstraintLayout rootView;
    @BindView(R.id.boxBg)
    RoundedImageView boxBg;
    @BindView(R.id.boxClose)
    ImageView boxClose;
    @BindView(R.id.tv_qbi)
    TextView tv_qbi;
    @BindView(R.id.tv_chongzhi)
    TextView tv_chongzhi;
    @BindView(R.id.boxTab1)
    TextView boxTab1;
    @BindView(R.id.boxTab2)
    TextView boxTab2;
    @BindView(R.id.boxTab3)
    TextView boxTab3;
    @BindView(R.id.boxBtn1)
    LinearLayout boxBtn1;
    @BindView(R.id.boxBtnNumber1)
    TextView boxBtnNumber1;
    @BindView(R.id.boxBtn2)
    LinearLayout boxBtn2;
    @BindView(R.id.boxBtnNumber2)
    TextView boxBtnNumber2;
    @BindView(R.id.boxBtn3)
    LinearLayout boxBtn3;
    @BindView(R.id.boxBtnNumber3)
    TextView boxBtnNumber3;
    @BindView(R.id.boxDes1)
    TextView boxDes1;
    @BindView(R.id.boxDes2)
    TextView boxDes2;
    @BindView(R.id.boxDes3)
    TextView boxDes3;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    private AdminHomeActivity mContext;
    private CommonModel commonModel;
    private RxErrorHandler mErrorHandler;
    private BaoXiangBean.DataBean mDataBean;
    String tag = "0";//1=普通  2=中级  3=高级
    private String mRoomId;
    List<OpenBoxBean.DataBean.AwardListBean> aniList = new ArrayList<>();
    private TreasureBoxAdapter mAdapter;


    public TreasureBoxDialog(@NonNull Context context ,CommonModel commonModel, RxErrorHandler errorHandler, String roomId) {
        super(context, R.style.myChooseDialog);
        mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mErrorHandler = errorHandler;
        this.mRoomId = roomId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_treasure_box);
        ButterKnife.bind(this);
        setWidows();
        intiRecyclerView();
        initClick();
        getBaoXiang();
        updateDialog(boxTab1);
    }
    private void setWidows() {

        Display display = getWindow().getWindowManager().getDefaultDisplay();

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.width = display.getWidth();

        lp.alpha = 1.0f;

        lp.gravity = Gravity.CENTER;

        getWindow().setAttributes(lp);

        this.setCanceledOnTouchOutside(true);

    }

    private void initClick(){
        boxTab1.setOnClickListener(view -> {
            updateDialog(boxTab1);
        });
        boxTab2.setOnClickListener(view -> {
            updateDialog(boxTab2);
        });
        boxTab3.setOnClickListener(view -> {
            updateDialog(boxTab3);
        });
        boxBtn1.setOnClickListener(view -> {
            getAwardList(1);
        });
        boxBtn2.setOnClickListener(view -> {
            getAwardList(10);
        });
        boxBtn3.setOnClickListener(view -> {
            getAwardList(100);
        });
        //手气榜
        boxDes1.setOnClickListener(view -> {
            BoxOpenRecordWindow boxOpenRecordWindow = new BoxOpenRecordWindow(mContext, rootView, commonModel, mErrorHandler);
            boxOpenRecordWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        });
        //奖池
        boxDes2.setOnClickListener(view -> {
            BoxJiangChiWindow boxJiangChiWindow = new BoxJiangChiWindow(mContext, rootView, commonModel, mErrorHandler,Integer.parseInt(tag));
            boxJiangChiWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        });
        //规则
        boxDes3.setOnClickListener(view -> {
            BoxTitleWindow boxTitleWindow = new BoxTitleWindow(mContext, rootView, commonModel, mErrorHandler,tag);
            boxTitleWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        });
        //充值
        tv_chongzhi.setOnClickListener(view -> {
            ArmsUtils.startActivity(ChargeActivity.class);
            if (isShowing())
                dismiss();
        });
        boxClose.setOnClickListener(view -> dismiss());
    }

    private void intiRecyclerView(){
        mAdapter = new TreasureBoxAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateDialog(TextView mTv){
        int defauleW = ArmsUtils.dip2px(mContext,80);
        int defauleH = ArmsUtils.dip2px(mContext,28);
        int defauleS = 14;
        int selectW = ArmsUtils.dip2px(mContext,100);
        int selectH = ArmsUtils.dip2px(mContext,30);
        int selectS = 16;

        mAdapter.setNewData(new ArrayList<>());


        ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams)boxTab1.getLayoutParams();
        lp1.width = defauleW;
        lp1.height = defauleH;
        boxTab1.setLayoutParams(lp1);
        boxTab1.setTextSize(defauleS);

        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams)boxTab2.getLayoutParams();
        lp2.width = defauleW;
        lp2.height = defauleH;
        boxTab2.setLayoutParams(lp2);
        boxTab2.setTextSize(defauleS);

        ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams)boxTab3.getLayoutParams();
        lp3.width = defauleW;
        lp3.height = defauleH;
        boxTab3.setLayoutParams(lp3);
        boxTab3.setTextSize(defauleS);

        //选中的样式
        ConstraintLayout.LayoutParams lpSelect = (ConstraintLayout.LayoutParams)mTv.getLayoutParams();
        lpSelect.width = selectW;
        lpSelect.height = selectH;
        mTv.setLayoutParams(lpSelect);
        mTv.setTextSize(selectS);


        int mLevel_1 = 10;//初级第一次
        int mLevel_2 = 50;//中级第一次
        int mLevel_3 = 100;//高级第一次
        tag = String.valueOf(mTv.getTag());
        switch (Integer.parseInt(String.valueOf(mTv.getTag()))){
            case 1://初级
                boxBg.setImageResource(R.mipmap.icon_box_img_1);
                boxBtnNumber1.setText((mLevel_1*Integer.parseInt(String.valueOf(boxBtn1.getTag())))+"");
                boxBtnNumber2.setText((mLevel_1*Integer.parseInt(String.valueOf(boxBtn2.getTag())))+"");
                boxBtnNumber3.setText((mLevel_1*Integer.parseInt(String.valueOf(boxBtn3.getTag())))+"");
                break;
            case 2://中级
                boxBg.setImageResource(R.mipmap.icon_box_img_2);
                boxBtnNumber1.setText((mLevel_2*Integer.parseInt(String.valueOf(boxBtn1.getTag())))+"");
                boxBtnNumber2.setText((mLevel_2*Integer.parseInt(String.valueOf(boxBtn2.getTag())))+"");
                boxBtnNumber3.setText((mLevel_2*Integer.parseInt(String.valueOf(boxBtn3.getTag())))+"");
                break;
            case 3://高级
                boxBg.setImageResource(R.mipmap.icon_box_img_3);
                boxBtnNumber1.setText((mLevel_3*Integer.parseInt(String.valueOf(boxBtn1.getTag())))+"");
                boxBtnNumber2.setText((mLevel_3*Integer.parseInt(String.valueOf(boxBtn2.getTag())))+"");
                boxBtnNumber3.setText((mLevel_3*Integer.parseInt(String.valueOf(boxBtn3.getTag())))+"");
                break;
        }
    }

    //获取宝箱信息
    private void getBaoXiang() {
        RxUtils.loading(commonModel.getBoxInfo("xx"), mContext)
                .subscribe(new ErrorHandleSubscriber<BaoXiangBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaoXiangBean baoXiangBean) {
                        if (baoXiangBean.getCode() == 1) {
                            mDataBean = baoXiangBean.getData();
                            tv_qbi.setText(mDataBean.getMizuan());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        LogUtils.debugInfo("====数据请求错误", t.getMessage());
                    }
                });
    }

    //开启宝箱
    private void getAwardList(int keyNum) {
        RxUtils.loading(commonModel.getAwardList(keyNum, tag), mContext)
                .subscribe(new ErrorHandleSubscriber<OpenBoxBean>(mErrorHandler) {
                    @Override
                    public void onNext(OpenBoxBean openBoxBean) {
                        try {
//                            tvQbi.setText(openBoxBean.getData().getMizuan());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (openBoxBean.getCode() == 1) {
                            if (mDataBean == null) return;
                            aniList.clear();
                            aniList.addAll(openBoxBean.getData().getAwardList());

                            mAdapter.setNewData(aniList);
                            getBaoXiang();
                            List<OpenBoxBean.DataBean.AwardListBean> awardList = openBoxBean.getData().getAwardList();
                            if (awardList != null && awardList.size() > 0) {

                                List<OpenBoxBean.DataBean.AwardListBean> newAwardList = new ArrayList<>();
                                //单个礼物的价值大于20红钻，才发频道消息
                                for (OpenBoxBean.DataBean.AwardListBean itemBean : awardList) {

                                    //double price = Arith.strToDouble(itemBean.getPrice());

                                    if (itemBean.getIs_public_play() == 1) {
                                        newAwardList.add(itemBean);
                                    }

                                }

                                //有礼物的价值大于20红钻
                                if (newAwardList.size() > 0 || !TextUtils.isEmpty(openBoxBean.getData().getAward_tips())) {

                                    LoginData loginData = UserManager.getUser();
                                    MessageBean messageBean = new MessageBean();
                                    messageBean.setNickName(loginData.getNickname());
                                    messageBean.setUser_id(loginData.getUserId() + "");
                                    messageBean.box_class = String.valueOf(openBoxBean.getData().getBox_class());
                                    messageBean.awardList = newAwardList;
                                    messageBean.setMessage(openBoxBean.getData().getAward_tips());
//                                    messageBean.award_tips = openBoxBean.getData().getAward_tips();
                                    messageBean.setMessageType("13");
                                    messageBean.setRoomId222(mRoomId);

                                    MessageEvent2 messageEvent = new MessageEvent2();
                                    messageEvent.setStateMessage(StateMessage.PEOPLE_OPEN_GEMSTONE);
                                    messageEvent.setObject(messageBean);
                                    EventBus.getDefault().post(messageEvent);

                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (!TextUtils.isEmpty(t.getMessage()))
                            BToast.showText(mContext, t.getMessage());
//                        touMingDialog.dismiss();
                        setCanceledOnTouchOutside(true);

                    }
                });
    }

}
