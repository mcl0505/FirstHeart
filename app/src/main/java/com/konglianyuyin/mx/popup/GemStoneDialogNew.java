package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.ChargeActivity;
import com.konglianyuyin.mx.activity.room.AdminHomeActivity;
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
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.konglianyuyin.mx.utils.BToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.konglianyuyin.mx.utils.Constant.DUIHUAN;
import static com.konglianyuyin.mx.utils.Constant.GOUMAIYAOSHI;
import static com.konglianyuyin.mx.utils.Constant.XIANSHIWANBI;
import static com.konglianyuyin.mx.utils.DpUtil.dp2px;


public class GemStoneDialogNew extends Dialog {

    @BindView(R.id.tv_jilu)
    TextView tvJilu;
    @BindView(R.id.tv_jiangchi)
    TextView tvJiangchi;
    @BindView(R.id.tv_shuoming)
    TextView tvShuoming;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_za1)
    RadioButton tvZa1;
    @BindView(R.id.tv_za10)
    RadioButton tvZa10;
    @BindView(R.id.tv_za100)
    RadioButton tvZa100;
    @BindView(R.id.fl_open)
    FrameLayout fl_open;
    @BindView(R.id.layout_baoxiao)
    LinearLayout layoutBaoxiao;
    @BindView(R.id.view_empty)
    View viewEmpty;
    @BindView(R.id.tv_cut_down_time)
    TextView mTvCutDownTime;
    @BindView(R.id.tv_qbi)
    TextView tvQbi;
    @BindView(R.id.tv_chongzhi)
    TextView tvChongzhi;
    @BindView(R.id.iv_cuizi)
    ImageView ivCuizi;
    @BindView(R.id.fl_main)
    RelativeLayout flMain;
    //    @BindView(R.id.iv_cuizixiao)
//    ImageView ivCuizixiao;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tv_num_text)
    TextView tvNumText;
    @BindView(R.id.common_tab_layout)
    CommonTabLayout commonTabLayout;

    private AdminHomeActivity mContext;
    private CommonModel commonModel;
    private RxErrorHandler mErrorHandler;
    private BaoXiangBean.DataBean mDataBean;
    String tag = "0";//0=普通  1=高级

    public GemStoneDialogNew(@NonNull Activity context, CommonModel commonModel, RxErrorHandler errorHandler) {
        super(context, R.style.myChooseDialog);
        mContext = (AdminHomeActivity) context;
        this.commonModel = commonModel;
        this.mErrorHandler = errorHandler;
    }

    Animation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_open_gemstone_new);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
//        rotate = AnimationUtils.loadAnimation(mContext, R.anim.zuizi_anim);
        //匀速插值器 解决卡顿问题
//        rotate.setInterpolator(new LinearInterpolator());
        boo(false);
        setWidows();

        getBaoXiang();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (commonTabLayout.getCurrentTab() == 0) {
                if (tvZa1.isChecked()) tvNumText.setText("20红钻/次，连开几率更高");
                if (tvZa10.isChecked()) tvNumText.setText("200红钻/次，连开几率更高");
                if (tvZa100.isChecked()) tvNumText.setText("1000红钻/次，连开几率更高");
            } else {
                if (tvZa1.isChecked()) tvNumText.setText("200红钻/次，连开几率更高");
                if (tvZa10.isChecked()) tvNumText.setText("2000红钻/次，连开几率更高");
                if (tvZa100.isChecked()) tvNumText.setText("10000红钻/次，连开几率更高");
            }
        });

        ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合
        List<CustomTabEntity> dataTab = new ArrayList<>();                 //CommonTabLayout 所需数据集合
        //标题资源
        titleRes.add("普通金蛋");
        titleRes.add("高级金蛋");

        for (int i = 0; i < titleRes.size(); i++) {
            final int index = i;
            dataTab.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return titleRes.get(index);
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        //设置数据
        commonTabLayout.setTabData((ArrayList<CustomTabEntity>) dataTab);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0)
                    ivCuizi.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.putonghongb));
                if (position == 1)
                    ivCuizi.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.gaojihonghbao));
                if (commonTabLayout.getCurrentTab() == 0) {
                    if (tvZa1.isChecked()) tvNumText.setText("20红钻/次，连开几率更高");
                    if (tvZa10.isChecked()) tvNumText.setText("200红钻/次，连开几率更高");
                    if (tvZa100.isChecked()) tvNumText.setText("1000红钻/次，连开几率更高");
                } else {
                    if (tvZa1.isChecked()) tvNumText.setText("200红钻/次，连开几率更高");
                    if (tvZa10.isChecked()) tvNumText.setText("2000红钻/次，连开几率更高");
                    if (tvZa100.isChecked()) tvNumText.setText("10000红钻/次，连开几率更高");
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void setWidows() {

        Display display = getWindow().getWindowManager().getDefaultDisplay();

        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.width = display.getWidth();

        lp.alpha = 1.0f;

        lp.gravity = Gravity.BOTTOM;

        getWindow().setAttributes(lp);

        this.setCanceledOnTouchOutside(true);

    }

    @Override
    public void dismiss() {
        handler.removeCallbacks(runnable);
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.dismiss();
    }


    boolean isCanPost = true;

    @OnClick({R.id.tv_jilu, R.id.tv_shuoming, R.id.fl_open, R.id.tv_jiangchi, R.id.tv_chongzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jilu:
                BoxOpenRecordWindow boxOpenRecordWindow = new BoxOpenRecordWindow(mContext, layoutBaoxiao, commonModel, mErrorHandler);
                boxOpenRecordWindow.showAtLocation(layoutBaoxiao, Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_jiangchi:
                BoxJiangChiWindow boxJiangChiWindow = new BoxJiangChiWindow(mContext, layoutBaoxiao, commonModel, mErrorHandler,commonTabLayout.getCurrentTab());
                boxJiangChiWindow.showAtLocation(layoutBaoxiao, Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_shuoming:
                BoxTitleWindow boxTitleWindow = new BoxTitleWindow(mContext, layoutBaoxiao, commonModel, mErrorHandler,commonTabLayout.getCurrentTab()+"");
                boxTitleWindow.showAtLocation(layoutBaoxiao, Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_chongzhi:
                ArmsUtils.startActivity(ChargeActivity.class);
                if (isShowing())
                    dismiss();
                break;
            case R.id.fl_open:
//                setCanceledOnTouchOutside(false);
                int num = 0;
                if (tvZa1.isChecked()) num = 1;
                if (tvZa10.isChecked()) num = 10;
                if (tvZa100.isChecked()) num = 50;
                if (isCanPost) {
                    getAwardList(num);
                }
                boo(false);
//                ivCuizixiao.clearAnimation();
//                if (rotate != null) {
//                    ivCuizixiao.startAnimation(rotate);
//                }
//                if (gifLoadFinish) {
//                    gifLoadFinish = false;
//                    loadOneTimeGif(mContext, ivCuizi, mContext.getResources().getDrawable(R.mipmap.jindandan_gif), new GifListener() {
//                        @Override
//                        public void gifPlayComplete() {
//                            gifLoadFinish = true;
//                        }
//                    });
//                }
                break;
        }
    }

    boolean gifLoadFinish = true;

    /**
     * 加载一次gif
     */
    public static void loadOneTimeGif(Context context, ImageView imageView, Drawable url, GifListener gifListener) {
        GlideArms
                .with(context)
                .asGif()
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            Field gifStateField = GifDrawable.class.getDeclaredField("state");
                            gifStateField.setAccessible(true);
                            Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                            Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                            gifFrameLoaderField.setAccessible(true);
                            Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                            Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                            gifDecoderField.setAccessible(true);
                            Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                            Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                            Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                            getDelayMethod.setAccessible(true);
                            //设置只播放一次
                            resource.setLoopCount(1);
                            //获得总帧数
                            int count = resource.getFrameCount();
                            int delay = 0;
                            for (int i = 0; i < count; i++) {
                                //计算每一帧所需要的时间进行累加
                                delay += (int) getDelayMethod.invoke(gifDecoder, i);
                            }
                            imageView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (gifListener != null) {
                                        gifListener.gifPlayComplete();
                                    }
                                }
                            }, delay);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                }).into(imageView);
    }

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }


    /**
     * 宝箱动画完毕之后
     */
    private void setSvgImgClickble(SVGAImageView svgImage, OpenBoxBean openBoxBean) {
        LogUtils.debugInfo("====打不出来哇", "哦哦哦哦哦哦哦");
        svgImage.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
            }

            @Override
            public void onFinished() {
                svgImage.setCallback(null);
//                svgImage.setClickable(false);
                LogUtils.debugInfo("====一直结束");
//                if (touMingDialog != null) {
//                    touMingDialog.dismiss();
//                }
                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
                boxGiftActivity.show();
//                touMingDialog = new TouMingDialog(mContext);
//                touMingDialog.show();
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {
            }
        });

    }

    //获取宝箱信息
    private void getBaoXiang() {
        RxUtils.loading(commonModel.getBoxInfo("xx"), mContext)
                .subscribe(new ErrorHandleSubscriber<BaoXiangBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaoXiangBean baoXiangBean) {
                        if (baoXiangBean.getCode() == 1) {
                            boo(true);
                            mDataBean = baoXiangBean.getData();
                            tvQbi.setText(mDataBean.getMizuan());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        LogUtils.debugInfo("====数据请求错误", t.getMessage());
                    }
                });
    }

    List<OpenBoxBean.DataBean.AwardListBean> aniList = new ArrayList<>();

    Handler handler = new Handler();
    Runnable runnable;

    private void donghua() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (aniList == null || aniList.size() == 0) return;
                ImageView imageView = new ImageView(getContext());
                //创建图片的长宽
                LinearLayout.LayoutParams lp22 = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(dp2px(30), dp2px(30)));
                Glide.with(getContext()).load(aniList.get(0).getShow_img()).apply(new RequestOptions().circleCrop()).into(imageView);
                imageView.setLayoutParams(lp22);  //设置图片的大小

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                lp.leftMargin = 30;

                lp.topMargin = 30;

                imageView.setId(1);//设置这个View 的id

                imageView.setLayoutParams(lp22);//设置布局参数

                flMain.addView(imageView);

                AnimationSet set = new AnimationSet(true);
                ScaleAnimation scale = new ScaleAnimation(
                        0.1f, 1,//X轴从满屏缩小到无
                        0.1f, 1,//Y轴从满屏缩小到无
                        Animation.RELATIVE_TO_SELF, 0.5f,//以自身0.5宽度为轴缩放
                        Animation.RELATIVE_TO_SELF, 0.5f);//以自身0.5宽度为轴缩放
                scale.setDuration(2000);//三秒完成动画
                set.addAnimation(scale);//增加动画
                TranslateAnimation trans = new TranslateAnimation(0.0f, new Random().nextInt(dp2px(240)) - dp2px(120),//以自身0.5宽度为轴
                        0.0f, (new Random().nextInt(dp2px(40)) - dp2px(80)));//以y轴原点进行计算
                trans.setDuration(2000);//三秒完成动画
                set.addAnimation(trans);//增加动画

                //Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale1);//获取平移缩放动画资源
                set.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.clearAnimation();
                                    flMain.removeViews(0, 1);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(set);//动画开始
                if (aniList == null || aniList.size() == 0) {
                } else {
                    handler.postDelayed(this, 300);
                    aniList.remove(0);
                }
            }
        };
        handler.postDelayed(runnable, 1);
    }


    //开启宝箱
    private void getAwardList(int keyNum) {
        RxUtils.loading(commonModel.getAwardList(keyNum, commonTabLayout.getCurrentTab() + ""), mContext)
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
                            if (aniList.size() == 0) {
                                aniList.addAll(openBoxBean.getData().getAwardList());
//                                donghua();
                            } else {
                                aniList.clear();
                                aniList.addAll(openBoxBean.getData().getAwardList());
                            }
//                            if (isShowing()){
//                                dismiss();
//                            }
                            WonPopupWindow wonPopupWindow = new WonPopupWindow(mContext, layoutBaoxiao, commonModel, mErrorHandler,aniList);
                            wonPopupWindow.showAtLocation(layoutBaoxiao, Gravity.CENTER, 0, 0);
//                            for (int i = 0; i < openBoxBean.getData().getAwardList().size(); i++) {
//                                ImageView imageView = new ImageView(getContext());
//                                //创建图片的长宽
//                                LinearLayout.LayoutParams lp22 = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(50, 50));
//                                imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.jianhao));
//                                Glide.with(getContext()).load(openBoxBean.getData().getAwardList().get(i).getShow_img()).into(imageView);
//                                imageView.setLayoutParams(lp22);  //设置图片的大小
//
//                                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//
//                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//                                lp.leftMargin = 30;
//
//                                lp.topMargin = 30;
//
//                                imageView.setId(1);//设置这个View 的id
//
//                                imageView.setLayoutParams(lp22);//设置布局参数
//
//                                flMain.addView(imageView);
//
//                                AnimationSet set = new AnimationSet(true);
//                                ScaleAnimation scale = new ScaleAnimation(
//                                        0.0f, 1,//X轴从满屏缩小到无
//                                        0.0f, 1,//Y轴从满屏缩小到无
//                                        Animation.RELATIVE_TO_SELF, 0.5f,//以自身0.5宽度为轴缩放
//                                        Animation.RELATIVE_TO_SELF, 0.5f);//以自身0.5宽度为轴缩放
//                                scale.setDuration(4000);//三秒完成动画
//                                set.addAnimation(scale);//增加动画
//                                TranslateAnimation trans = new TranslateAnimation(0.0f, new Random().nextInt(600) - 300,//以自身0.5宽度为轴
//                                        0.0f, new Random().nextInt(200) - 300);//以y轴原点进行计算
//                                trans.setDuration(4000);//三秒完成动画
//                                set.addAnimation(trans);//增加动画
//
//                                //Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale1);//获取平移缩放动画资源
//                                set.setAnimationListener(new Animation.AnimationListener() {
//                                    @Override
//                                    public void onAnimationStart(Animation animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animation animation) {
//                                        try {
//                                            new Handler().post(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    imageView.clearAnimation();
//                                                    flMain.removeViews(0, 1);
//                                                }
//                                            });
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animation animation) {
//
//                                    }
//                                });
//                                imageView.startAnimation(set);//动画开始
//                            }
//
//                            EventBus.getDefault().post(new FirstEvent("显示完毕", XIANSHIWANBI));
//                            EventBus.getDefault().post(new FirstEvent("动画完成", "donghuawancheng"));
                            if (mDataBean.getBoxclass() == 1) {
//                                recyclerview.setVisibility(View.VISIBLE);
//                                BXAdapter mAdapter = new BXAdapter();
//                                GridLayoutManager GM = new GridLayoutManager(mContext, 4);
//                                recyclerview.setLayoutManager(GM);
//                                recyclerview.setAdapter(mAdapter);
//                                mAdapter.setNewData(openBoxBean.getData().getAwardList());
                                EventBus.getDefault().post(new FirstEvent("显示完毕", XIANSHIWANBI));
                                EventBus.getDefault().post(new FirstEvent("动画完成", "donghuawancheng"));
//                                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
//                                boxGiftActivity.show();
//                                touMingDialog = new TouMingDialog(mContext);
//                                touMingDialog.show();
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showServerSVG(parser, "putong_kai.svga", mSVGAImageView, openBoxBean);
                            } else if (mDataBean.getBoxclass() == 2) {
//                                recyclerview.setVisibility(View.VISIBLE);
//                                BXAdapter mAdapter = new BXAdapter();
//                                GridLayoutManager GM = new GridLayoutManager(mContext, 4);
//                                recyclerview.setLayoutManager(GM);
//                                recyclerview.setAdapter(mAdapter);
//                                mAdapter.setNewData(openBoxBean.getData().getAwardList());
                                EventBus.getDefault().post(new FirstEvent("显示完毕", XIANSHIWANBI));
                                EventBus.getDefault().post(new FirstEvent("动画完成", "donghuawancheng"));
//                                BoxGiftActivity boxGiftActivity = new BoxGiftActivity(mContext, openBoxBean);
//                                boxGiftActivity.show();
//                                touMingDialog = new TouMingDialog(mContext);
//                                touMingDialog.show();
//                                SVGAParser parser = new SVGAParser(mContext);
//                                showServerSVG(parser, "shouhu_kai.svga", mSVGAImageView, openBoxBean);
                            }

//                            BXAdapter mAdapter = new BXAdapter();
//                            GridLayoutManager GM = new GridLayoutManager(mContext, 4);
//                            recyclerview.setLayoutManager(GM);
//                            recyclerview.setAdapter(mAdapter);
//                            mAdapter.setNewData(openBoxBean.getData().getAwardList());

                            //更新房间公屏消息
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

//                                StringBuilder builder = new StringBuilder();
//                                builder.append("哇哦，");
//                                builder.append(UserManager.getUser().getNickname());
//                                builder.append("在");
//                                if (openBoxBean.getData().getBox_class() == 1) {
//                                    builder.append("普通宝箱");
//                                } else {
//                                    builder.append("守护宝箱");
//                                }
//                                builder.append("开出了");
//                                for (OpenBoxBean.DataBean.AwardListBean awardListBean : awardList) {
//                                    String number = "x" + awardListBean.getNum();
//                                    String name = awardListBean.getName();
//                                    builder.append(name);
//                                    builder.append(number);
//                                    builder.append(",");
//                                }
//                                builder.append("真是太优秀了！");

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
                        boo(true);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(FirstEvent event) {
        String tag = event.getTag();
        if (DUIHUAN.equals(tag)) {
            getBaoXiang();
        } else if (GOUMAIYAOSHI.equals(tag)) {
            getBaoXiang();
        } else if (XIANSHIWANBI.equals(tag)) {
            getBaoXiang();
        } else if ("donghuawancheng".equals(tag)) {
//            if (touMingDialog != null) {
//                touMingDialog.dismiss();
//            }
            this.setCanceledOnTouchOutside(true);
            boo(true);
        }
    }

    private void boo(boolean booo) {
        isCanPost = booo;
//        fl_open.setEnabled(booo);
    }
}
