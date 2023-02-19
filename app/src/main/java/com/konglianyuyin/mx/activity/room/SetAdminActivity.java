package com.konglianyuyin.mx.activity.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.AdminUserAdapter;
import com.konglianyuyin.mx.adapter.AdminUserAdapter2;
import com.konglianyuyin.mx.adapter.AdminUserAdapter3;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.bean.AdminUser;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.SearchAdmin;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;
import com.konglianyuyin.mx.view.ClearEditText;
import com.konglianyuyin.mx.view.MyListView;
import com.konglianyuyin.mx.view.ShapeTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 设置房间管理员
 */
public class SetAdminActivity extends MyBaseArmActivity {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rightTitle)
    TextView rightTitle;
    @BindView(R.id.rightConfirm)
    ShapeTextView rightConfirm;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_login_pass)
    ClearEditText edtLoginPass;
    @BindView(R.id.btn_ok)
    ShapeTextView btnOk;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.myList1)
    MyListView myList1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.myList2)
    MyListView myList2;

    @Inject
    CommonModel commonModel;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.myList3)
    MyListView myList3;
    private String uid;

    private AdminUserAdapter adminUserAdapter;
    private AdminUserAdapter2 adminUserAdapter2;
    private AdminUserAdapter3 adminUserAdapter3;

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
        return R.layout.activity_set_admin;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        uid = getIntent().getStringExtra("uid");
        adminUserAdapter = new AdminUserAdapter(this);
        myList1.setAdapter(adminUserAdapter);
        adminUserAdapter2 = new AdminUserAdapter2(this);
        myList2.setAdapter(adminUserAdapter2);
        adminUserAdapter3 = new AdminUserAdapter3(this);
        myList3.setAdapter(adminUserAdapter3);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminHomeActivity.class));
            finish();
        });
    }


    private void loadData() {
        RxUtils.loading(commonModel.getRoomUsers(uid), this)
                .subscribe(new ErrorHandleSubscriber<AdminUser>(mErrorHandler) {
                    @Override
                    public void onNext(AdminUser adminUser) {
                        text1.setText("管理员" + adminUser.getData().getAdmin().size());
                        text2.setText("房间在线" + adminUser.getData().getVisitor().size() + "人");

                        List<AdminUser.DataBean.AdminBean> adminList = adminUser.getData().getAdmin();
                        adminUserAdapter.getList_adapter().clear();
                        adminUserAdapter.getList_adapter().addAll(adminList);
                        adminUserAdapter.notifyDataSetChanged();

                        adminUserAdapter2.getList_adapter().clear();
                        adminUserAdapter2.getList_adapter().addAll(adminUser.getData().getVisitor());
                        adminUserAdapter2.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, AdminHomeActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @OnClick(R.id.btn_ok)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                loadSearch();
                break;
        }
    }

    /**
     * 搜索
     */
    private void loadSearch() {
        String s = edtLoginPass.getText().toString();
        if (TextUtils.isEmpty(s)) {
            scroll.setVisibility(View.VISIBLE);
            myList3.setVisibility(View.GONE);
        } else {
            RxUtils.loading(commonModel.search_user(uid, s), this)
                    .subscribe(new ErrorHandleSubscriber<SearchAdmin>(mErrorHandler) {
                        @Override
                        public void onNext(SearchAdmin searchAdmin) {
                            if (searchAdmin != null && searchAdmin.getData().size() > 0) {
                                scroll.setVisibility(View.GONE);
                                myList3.setVisibility(View.VISIBLE);
                                adminUserAdapter3.getList_adapter().clear();
                                adminUserAdapter3.getList_adapter().addAll(searchAdmin.getData());
                                adminUserAdapter3.notifyDataSetChanged();
                            }else {
                                toast(searchAdmin.getMessage());
                            }
                        }
                    });
        }
    }

    /**
     * 取消管理员
     * @param userId
     */
    public void remove_admin(String userId,int type){
        RxUtils.loading(commonModel.remove_admin(uid, userId), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        EventBus.getDefault().post(new FirstEvent(userId,Constant.QuxiaoGUANLI));
                        if(type == 2){
                            loadSearch();
                        }else {
                            loadData();
                        }
                    }
                });
    }

    /**
     * 设置管理员
     * @param userId
     */
    public void is_admin(String userId,int type){
        RxUtils.loading(commonModel.is_admin(uid, userId), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        EventBus.getDefault().post(new FirstEvent(userId,Constant.SHEZHIGUANLI));
                        if(type == 2){
                            loadSearch();
                        }else {
                            loadData();
                        }
                    }
                });
    }
}
