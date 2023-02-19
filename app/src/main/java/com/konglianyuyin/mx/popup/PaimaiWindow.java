package com.konglianyuyin.mx.popup;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
import com.konglianyuyin.mx.adapter.PaimaiAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.WaitList;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ShapeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * 排麦,popupwindow和dialog不能嵌套viewpager使用，此处是一个巨坑，草特大爷
 */
@SuppressLint("ValidFragment")
public class PaimaiWindow extends PopupWindow {


    @BindView(R.id.textNum)
    TextView textNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;
    private String id = "";//礼物id
    private AdminHomeActivity context;
    private CommonModel commonModel;
    private String uid;
    private PaimaiAdapter paimaiAdapter;
    public PaimaiWindow(AdminHomeActivity context,String uid,
                        CommonModel commonModel) {
        super(context);
        this.context = context;
        this.commonModel = commonModel;
        this.uid = uid;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_paimai, null);
        ButterKnife.bind(this, view);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
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
        context.getWindow().setAttributes(params);
        paimaiAdapter = new PaimaiAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(paimaiAdapter);
        loadData();
    }

    private void loadData() {
        RxUtils.loading(commonModel.waitList(uid,String.valueOf(UserManager.getUser().getUserId())))
                .subscribe(new ErrorHandleSubscriber<WaitList>(context.mErrorHandler) {
                    @Override
                    public void onNext(WaitList waitList) {
                        paimaiAdapter.setNewData(waitList.getData().getData());
                        textNum.setText("当前麦序" + waitList.getData().getTotal() + "人");
                    }
                });
    }


    @Override
    public void dismiss() {
        super.dismiss();
        MyBaseArmActivity myBaseArmActivity = (MyBaseArmActivity) context;
        WindowManager.LayoutParams params = myBaseArmActivity.getWindow().getAttributes();
        params.alpha = 1f;
        myBaseArmActivity.getWindow().setAttributes(params);
    }

    @OnClick({R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                RxUtils.loading(commonModel.delWait(String.valueOf(UserManager.getUser().getUserId())))
                        .subscribe(new ErrorHandleSubscriber<BaseBean>(context.mErrorHandler) {
                            @Override
                            public void onNext(BaseBean giftListBean) {
                                dismiss();
                            }
                        });
                break;
        }
    }

}
