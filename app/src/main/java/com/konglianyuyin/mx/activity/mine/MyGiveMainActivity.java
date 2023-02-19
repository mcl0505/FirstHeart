package com.konglianyuyin.mx.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.SearchHisActivity;
import com.konglianyuyin.mx.adapter.ZengSongHisAdapter;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.MoneyBean;
import com.konglianyuyin.mx.bean.ZengSongHisBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ShapeTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class MyGiveMainActivity extends MyBaseArmActivity {
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
    @BindView(R.id.sousuo)
    LinearLayout sousuo;
    @Inject
    CommonModel commonModel;
    @BindView(R.id.tvAllNum)
    TextView tvAllNum;

    ZengSongHisAdapter zengSongHisAdapter;
    List<ZengSongHisBean.DataBean> dataList = new ArrayList<>();
    @BindView(R.id.listView)
    RecyclerView listView;

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
        return R.layout.activity_give_my_main;
    }

    private void loadData() {
        RxUtils.loading(commonModel.my_store(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<MoneyBean>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        tvAllNum.setText(TextUtils.isEmpty(moneyBean.getData().get(0).getMizuan()) ?
                                "0" :
                                Html.fromHtml(moneyBean.getData().get(0).getMizuan()));
                    }
                });
    }

    private void initRecyview() {
        if (zengSongHisAdapter == null)
            zengSongHisAdapter = new ZengSongHisAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        zengSongHisAdapter.setNewData(dataList);
        listView.setAdapter(zengSongHisAdapter);
    }

    private void loadDataList() {
        dataList.clear();
        RxUtils.loading(commonModel.zhengsonglog("1"))
                .subscribe(new ErrorHandleSubscriber<ZengSongHisBean>(mErrorHandler) {
                    @Override
                    public void onNext(ZengSongHisBean zengSongHisBean) {
                        dataList.addAll(zengSongHisBean.getData());
                        if (dataList != null && dataList.size() > 4)
                            dataList = dataList.subList(0, 4);
                        zengSongHisAdapter.setNewData(dataList);
                        zengSongHisAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbarTitle.setText("我的钱包");
        loadData();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyview();
        loadDataList();
        setToolbarRightText("赠送记录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyGiveMainActivity.this, MyGiveHistoryActivity.class);
                ArmsUtils.startActivity(intent);
            }
        }, R.color.font_333333);
        sousuo.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MyGiveMainActivity.this, SearchHisActivity.class);
            intent.putExtra("source", "give");
            ArmsUtils.startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
