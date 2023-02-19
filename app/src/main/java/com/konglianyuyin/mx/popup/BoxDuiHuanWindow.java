package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.BoxDuiHuanAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.BoxExchangeBean;
import com.konglianyuyin.mx.bean.CommentBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.AToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.DUIHUAN;

//开奖记录
public class BoxDuiHuanWindow extends PopupWindow {
    private AdminHomeActivity mContext;
    private RecyclerView recyclerView;
    private SmartRefreshLayout mSm;
    private CommonModel commonModel;
    private RxErrorHandler mRxErrorHandler;
    private BoxDuiHuanAdapter mAdapter;
    private List<BoxExchangeBean.DataBean> mDataList;

    public BoxDuiHuanWindow(Activity context, ConstraintLayout view, CommonModel commonModel, RxErrorHandler rxErrorHandler, String money) {
        super(context);
        this.mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mRxErrorHandler = rxErrorHandler;
        View view1 = LayoutInflater.from(context).inflate(R.layout.box_duihuan_window, null);
        recyclerView = view1.findViewById(R.id.recyclerview);
        ImageView iv_close = view1.findViewById(R.id.iv_close);
        TextView okBtn = view1.findViewById(R.id.tv_duihuan);
        TextView tv_jifen = view1.findViewById(R.id.tv_jifen);
        tv_jifen.setText("我的积分：" + money);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(view1);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //设置透明
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 0.7f;
        this.setWidth(view.getMeasuredWidth());
//        this.setHeight(view.getMeasuredHeight() - 100);
        context.getWindow().setAttributes(params);

        mAdapter = new BoxDuiHuanAdapter(R.layout.box_duihuan_item, mDataList);
        GridLayoutManager llm = new GridLayoutManager(mContext, 4);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setSelectPos(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        setData();
        okBtn.setOnClickListener(v -> {
            MessageDialog.build(mContext)
                    .setStyle(DialogSettings.STYLE.STYLE_IOS)
                    .setTheme(DialogSettings.THEME.LIGHT)
                    .setTitle("")
                    .setMessage("兑换后将扣除 " + String.valueOf(mAdapter.getData().get(mAdapter.getSelectPos()).getScore() + " 积分，是否兑换"))
                    .setOkButton("确定", new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            baseDialog.doDismiss();
                            actionAwardExchange(String.valueOf(mAdapter.getData().get(mAdapter.getSelectPos()).getId()));
                            return false;
                        }

                    })
                    .setCancelButton("取消", new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            baseDialog.doDismiss();
                            return false;
                        }
                    })
                    .show();
        });
    }


    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = mContext;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
    }

    //获取礼物列表
    private void setData() {
        RxUtils.loading(commonModel.getAwardWaresList("xx"), mContext)
                .subscribe(new ErrorHandleSubscriber<BoxExchangeBean>(mContext.mErrorHandler) {
                    @Override
                    public void onNext(BoxExchangeBean boxExchangeBean) {
                        if (boxExchangeBean.getCode() == 1) {
                            mDataList = boxExchangeBean.getData();
                            mAdapter.setNewData(mDataList);
                        }
                    }
                });
    }

    //兑换
    private void actionAwardExchange(String waresId) {
        RxUtils.loading(commonModel.actionAwardExchange(waresId), mContext)
                .subscribe(new ErrorHandleSubscriber<CommentBean>(mContext.mErrorHandler) {
                    @Override
                    public void onNext(CommentBean commentBean) {
                        AToast.showText(mContext, commentBean.getMessage());
                        if (commentBean.getCode() == 1) {
                            dismiss();
                            EventBus.getDefault().post(new FirstEvent("兑换成功", DUIHUAN));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        AToast.showText(mContext, t.getMessage());
                    }
                });
    }
}
