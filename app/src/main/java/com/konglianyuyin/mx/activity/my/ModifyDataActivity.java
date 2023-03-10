package com.konglianyuyin.mx.activity.my;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.utils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.ConstellationBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.bean.JsonBean;
import com.konglianyuyin.mx.bean.LoginData;
import com.konglianyuyin.mx.bean.OtherBean;
import com.konglianyuyin.mx.bean.UserBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.BaseUtils;
import com.konglianyuyin.mx.utils.GetJsonDataUtil;
import com.konglianyuyin.mx.utils.PhotoWindow;
import com.konglianyuyin.mx.utils.SdcardTools;
import com.konglianyuyin.mx.utils.TextNumUtil;
import com.konglianyuyin.mx.utils.TimeUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.XIUGAIGERENZILIAOCHENGGONG;
import static io.rong.imkit.utilities.RongUtils.dip2px;

public class ModifyDataActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;

    @BindView(R.id.head_image)
    CircularImage headImage;
    @BindView(R.id.textSend)
    EditText textSend;
    @BindView(R.id.nick_num)
    TextView nickNum;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.birthday_btn)
    RelativeLayout birthdayBtn;
    @BindView(R.id.constellation)
    TextView constellation;
    @BindView(R.id.consteation_btn)
    RelativeLayout consteationBtn;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.region_btn)
    RelativeLayout regionBtn;
    @BindView(R.id.tv_baocun)
    TextView tvBaocun;

    private String nowDate;
    private String sexStr;

    //??????
    public static final int REQUEST_CODE_SELECT = 100;
    private ArrayList<ImageItem> selImageList = new ArrayList<>();
    private String imgString;

    //?????????????????????
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    private Calendar startDate, endDate, seleteDate;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//????????????????????????????????????????????????

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // ?????????????????????????????????
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    toast("??????????????????????????????????????????????????????????????????????????????");
                    break;
            }
        }
    };

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_modify_data;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TextNumUtil.initTextNum(ModifyDataActivity.this, textSend, nickNum);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        getPerInfo();
    }


    @OnClick({R.id.birthday_btn, R.id.head_image, R.id.region_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birthday_btn:
                String string = birthday.getText().toString();
                String timeYmd = TimeUtil.getTimeYmd();
                String[] split1 = string.split("-");
                String[] split = timeYmd.split("-");
                seleteDate = Calendar.getInstance();
                startDate = Calendar.getInstance();
                endDate = Calendar.getInstance();
                startDate.set(1900, 0, 1);
                endDate.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]));
                seleteDate.set(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]) - 1, Integer.parseInt(split1[2]));
                TimePickerView pvTime = new TimePickerBuilder(ModifyDataActivity.this, (date, v) -> {
                    nowDate = BaseUtils.getNowDate(date);
                    birthday.setText(nowDate);
                    getConstellation(nowDate);
                })
                        .setRangDate(startDate, endDate)
                        .setDate(seleteDate)
                        .build();
                pvTime.show();
                break;
            case R.id.head_image:
                showPop();
                break;
            case R.id.region_btn:
                //???????????????
                if (isLoaded) {
                    showPickerView();
                }
                break;
        }
    }

    // ???????????????
    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //?????????????????????????????????????????????
//                String opt1tx = options1Items.size() > 0 ?
//                        options1Items.get(options1).getPickerViewText() : "";
//
                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

//                String opt3tx = options2Items.size() > 0
//                        && options3Items.get(options1).size() > 0
//                        && options3Items.get(options1).get(options2).size() > 0 ?
//                        options3Items.get(options1).get(options2).get(options3) : "";

                region.setText(opt2tx);
            }
        })

                .setTitleText("????????????")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //???????????????????????????
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//???????????????
        pvOptions.setPicker(options1Items, options2Items);//???????????????*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//???????????????
        pvOptions.show();
    }

    //????????????
    private void initJsonData() {

        /**
         * ?????????assets ????????????Json??????????????????????????????????????????????????????
         * ???????????????????????????
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//??????assets????????????json????????????

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//???Gson ????????????

        /**
         * ??????????????????
         *
         * ???????????????????????????JavaBean????????????????????????????????? IPickerViewData ?????????
         * PickerView?????????getPickerViewText????????????????????????????????????
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//????????????
            ArrayList<String> cityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//??????????????????????????????
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//????????????
                ArrayList<String> city_AreaList = new ArrayList<>();//??????????????????????????????

                //??????????????????????????????????????????????????????????????????null ?????????????????????????????????????????????
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//??????????????????????????????
            }

            /**
             * ??????????????????
             */
            options2Items.add(cityList);

            /**
             * ??????????????????
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    //Gson ??????
    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    //???????????????
    private void showPop() {
        PhotoWindow photoWindow = new PhotoWindow(this);
        photoWindow.showAtLocation(headImage, Gravity.BOTTOM, 0, 0);
        photoWindow.getTake_photo().setOnClickListener(v -> {
            photoWindow.dismiss();
            if (SdcardTools.sdcard()) {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions
                        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                //??????
                                Intent intent = new Intent(ModifyDataActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // ???????????????????????????
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                            }
                        });
            } else {
                Toast.makeText(this, "sd????????????", Toast.LENGTH_SHORT).show();
            }
        });
        photoWindow.getChose_pic().setOnClickListener(v -> {
            photoWindow.dismiss();
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            // ???????????????
                            ImagePicker.getInstance().setSelectLimit(1);
                            ImagePicker.getInstance().setMultiMode(false);
                            Intent intent = new Intent(ModifyDataActivity.this, ImageGridActivity.class);
                            //?????????????????????
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                        }
                    });
        });
        photoWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 1f;
            getWindow().setAttributes(params);
        });
    }

    /*-----------------------??????????????????------------------------------*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ArrayList<ImageItem> tempList;
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //??????????????????
            if (requestCode == REQUEST_CODE_SELECT) {
                tempList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (tempList == null) {
                    return;
                }
                selImageList.clear();
                selImageList.addAll(tempList);

                if (selImageList.size() > 0) {
                    GlideArms.with(ModifyDataActivity.this)
                            .load(selImageList.get(0).path)
                            .placeholder(R.drawable.ic_default_image)
                            .error(R.drawable.ic_default_image)
                            .circleCrop()
                            .into(headImage);
                }
            }
        }
    }

    //??????????????????
    private void getPerInfo() {
        RxUtils.loading(commonModel.get_user_info(UserManager.getUser().getUserId() + ""), this)
                .subscribe(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(UserBean userBean) {
                        //??????
                        ArmsUtils.obtainAppComponentFromContext(ModifyDataActivity.this)
                                .imageLoader()
                                .loadImage(ModifyDataActivity.this, ImageConfigImpl
                                        .builder()
                                        .url(userBean.getData().getHeadimgurl())
                                        .placeholder(R.mipmap.no_tou)
                                        .imageView(headImage)
                                        .errorPic(R.mipmap.no_tou)
                                        .build());
                        //??????
                        textSend.setText(userBean.getData().getNickname());
                        //ID???
                        if(TextUtils.isEmpty(userBean.getData().getBright_num())){//????????????????????????????????????id
                            LogUtils.e(TAG,"userBean.getData().getId()"+userBean.getData().getId());
                            id.setText(userBean.getData().getId() + "");
                        }else{
                            LogUtils.e(TAG,"userBean.getData().getBright_num()"+userBean.getData().getBright_num());
                            id.setText(userBean.getData().getBright_num() + "");
                            Drawable left = getResources().getDrawable(R.mipmap.jianhao);
                            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                            id.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                            id.setCompoundDrawablePadding(dip2px(4));
                        }

                        //??????
                        sexStr = String.valueOf(userBean.getData().getSex());
                        if (userBean.getData().getSex() == 1) {
                            sex.setText("???");
                        } else {
                            sex.setText("???");
                        }
                        //????????????
                        birthday.setText(userBean.getData().getBirthday());
                        //??????
                        constellation.setText(userBean.getData().getConstellation());
                        //??????
                        region.setText(userBean.getData().getCity());
                    }
                });
    }

    //????????????
    private void getConstellation(String birthday) {
        RxUtils.loading(commonModel.getConstellation(birthday), this)
                .subscribe(new ErrorHandleSubscriber<ConstellationBean>(mErrorHandler) {
                    @Override
                    public void onNext(ConstellationBean constellationBean) {
                        constellation.setText(constellationBean.getData().getConstellation());
                    }
                });
    }

    //????????????
    private void setUserInfo(String img, String nickname, String sex, String birthday, String constellation, String city) {
        showDialogLoding();
        RxUtils.loading(commonModel.setUserInfo(String.valueOf(UserManager.getUser().getUserId()), img, nickname, sex, birthday, constellation, city), this)
                .subscribe(new ErrorHandleSubscriber<OtherBean>(mErrorHandler) {
                    @Override
                    public void onNext(OtherBean commentBean) {

                        disDialogLoding();

                        toast("????????????");

                        if (commentBean.getCode() == 1) {
                            String token = UserManager.getUser().getToken();
                            //???????????????????????????
                            LoginData loginData = new LoginData();
                            loginData.setHeadimgurl(commentBean.getData().getHeadimgurl());
                            loginData.setNickname(commentBean.getData().getNickname());
                            loginData.setUserId(commentBean.getData().getId());
                            loginData.setPhone(commentBean.getData().getPhone());
                            loginData.setRy_token(commentBean.getData().getRy_token());
                            loginData.setToken(token);
                            if(commentBean.getData().getNewtoken() != null && commentBean.getData().getNewtoken().length() > 0){
                                loginData.setNewtoken(commentBean.getData().getNewtoken());
                            }

//                                    if(TextUtils.isEmpty(userInfo.getData().getProvince())){
//                                        loginData.setPr(userInfo.getData().getProvince());
//                                    }
//                                    if(TextUtils.isEmpty(userInfo.getData().getCountry())){
//                                        loginData.set(userInfo.getData().getCountry());
//                                    }
                            LitePal.deleteAll(LoginData.class);
                            loginData.save();//litepal???????????????????????????LoginData??????
                            UserManager.initData();//?????????????????????
                            finish();
                            EventBus.getDefault().post(new FirstEvent("????????????", XIUGAIGERENZILIAOCHENGGONG));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                        LogUtils.e("====????????????????????????", t.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle("????????????", true);
        tvBaocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(textSend.getText())) {
                    ArmsUtils.snackbarText("????????????");
                } else if (TextUtils.isEmpty(birthday.getText())) {
                    ArmsUtils.snackbarText("????????????");
                } else if (TextUtils.isEmpty(constellation.getText())) {
                    ArmsUtils.snackbarText("????????????");
                } else if (TextUtils.isEmpty(region.getText())) {
                    ArmsUtils.snackbarText("????????????????????????");
                } else {
                    if (selImageList.size() == 0) {
                        imgString = "";
                    } else {
                        imgString = "data:image/png;base64," + BaseUtils.file2Base64(selImageList.get(0).path);
                    }
                    setUserInfo(imgString, textSend.getText().toString().replace(" ", ""), sexStr, birthday.getText().toString().replace(" ", ""), constellation.getText().toString().replace(" ", ""), region.getText().toString().replace(" ", ""));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
