package com.konglianyuyin.mx.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.BaseBean;
import com.konglianyuyin.mx.bean.GuanFangLianXiBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.BaseUtils;
import com.konglianyuyin.mx.utils.TextNumUtil;
import com.konglianyuyin.mx.view.ShapeTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class HelpActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;
    @BindView(R.id.et_question_des)
    EditText etQuestionDes;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.num_text)
    TextView numText;
    @BindView(R.id.btn_submit)
    ShapeTextView btnSubmit;
    @BindView(R.id.qqmobile)
    TextView qq;
    @BindView(R.id.guanfanmobile)
    TextView guanfang;
    @BindView(R.id.guanfandy)
    TextView guanfan;
    @BindView(R.id.one)
    TextView One;
    @BindView(R.id.two)
    TextView Two;
    @BindView(R.id.three)
    TextView Three;
    @BindView(R.id.iv_qqmobile)
    ImageView qqImg;
    @BindView(R.id.iv_guanfanmobile)
    ImageView guanImg;
    @BindView(R.id.iv_guanfandy)
    ImageView fanImg;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.dingfu)
    TextView dingfu;
    @BindView(R.id.phonefu)
    TextView phonefu;
    @BindView(R.id.qqfu)
    TextView qqfu;

    public static final int REQUEST_CODE_SELECT = 100;
    private ArrayList<ImageItem> selImageList = new ArrayList<>();

    private String imageString;

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
        return R.layout.activity_help;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TextNumUtil.initTextNum(HelpActivity.this, etQuestionDes, numText);

        getGuanFang();
    }

    private void getGuanFang() {
        //获取官方联系方式
        RxUtils.loading(commonModel.guanfang("xx"), this)
                .subscribe(new ErrorHandleSubscriber<GuanFangLianXiBean>(mErrorHandler) {
                    @Override
                    public void onNext(GuanFangLianXiBean guanFangLianXiBean) {
                        List<GuanFangLianXiBean.DataBean> data = guanFangLianXiBean.getData();
                        if(data != null && data.size() > 0){
                            loadImage(qqImg, guanFangLianXiBean.getData().get(0).getImg(), R.mipmap.help_qq);
                            One.setText(guanFangLianXiBean.getData().get(0).getType() + "：");
                            qq.setText(guanFangLianXiBean.getData().get(0).getContent());
                            qqfu.setVisibility(View.VISIBLE);
                            if(data.size() > 1){
                                loadImage(guanImg, guanFangLianXiBean.getData().get(1).getImg(), R.mipmap.help_qq);
                                Two.setText(guanFangLianXiBean.getData().get(1).getType() + "：");
                                guanfang.setText(guanFangLianXiBean.getData().get(1).getContent());
                                phonefu.setVisibility(View.VISIBLE);
                            }

                            if(data.size() > 2){
                                loadImage(fanImg, guanFangLianXiBean.getData().get(2).getImg(), R.mipmap.help_qq);
                                Three.setText(guanFangLianXiBean.getData().get(2).getType() + "：");
                                guanfan.setText(guanFangLianXiBean.getData().get(2).getContent());
                                dingfu.setVisibility(View.VISIBLE);
                            }

                        }else{
                            llBottom.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle("帮助与反馈", true);
    }

    @OnClick({R.id.image, R.id.btn_submit, R.id.qqfu, R.id.phonefu, R.id.dingfu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // 跳转到相册
                                ImagePicker.getInstance().setSelectLimit(1);
                                ImagePicker.getInstance().setMultiMode(false);
                                Intent intent = new Intent(HelpActivity.this, ImageGridActivity.class);
                                //显示选中的图片
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                            }
                        });
                break;
            case R.id.btn_submit:
                if (selImageList.size() == 0) {
                    imageString = "";
                } else {
                    imageString = "data:image/png;base64," + BaseUtils.file2Base64(selImageList.get(0).path);
                }
                feedBack(imageString);
                break;
            case R.id.qqfu:
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(qq.getText().toString());
                toast("复制成功");
                break;
            case R.id.phonefu:
                ClipboardManager cmb1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb1.setText(guanfang.getText().toString());
                toast("复制成功");
                break;
            case R.id.dingfu:
                ClipboardManager cmb2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb2.setText(guanfan.getText().toString());
                toast("复制成功");
                break;
        }
    }

    private void feedBack(String string) {

        if (TextUtils.isEmpty(etQuestionDes.getText().toString()) && selImageList.size() == 0) {
            toast("请填写您的反馈建议");
        } else {
            RxUtils.loading(commonModel.feedBack(String.valueOf(UserManager.getUser().getUserId()), etQuestionDes.getText().toString(), string), this)
                    .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                        @Override
                        public void onNext(BaseBean baseBean) {
                            toast(baseBean.getMessage());
                            finish();
                        }
                    });
        }
    }


    /*-----------------------图片选择回调------------------------------*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ArrayList<ImageItem> tempList;
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (requestCode == REQUEST_CODE_SELECT) {
                tempList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (tempList == null) {
                    return;
                }
                selImageList.clear();
                selImageList.addAll(tempList);
                if (selImageList.size() > 0) {
                    loadImage(image, selImageList.get(0).path, R.mipmap.help_picture);
                }
            }
        }
    }
}
