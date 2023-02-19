package com.konglianyuyin.mx.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.activity.login.LoginActivity;
import com.konglianyuyin.mx.base.UserManager;

public class LunchActivity extends AppCompatActivity {
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);//恢复原有的样式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setStatusBar();

        setContentView(R.layout.activity_launcher);
        mContext = this;

        /*ImageView imageView = (ImageView) findViewById(R.id.img);
        GlideArms.with(this)
                .load(R.drawable.splash_bg)
                .into(imageView);*/

        /*if (false) {
            SharedPreferencesUtils.setParam(mContext, "isFirstOpen", false);
            ArmsUtils.startActivity(GuildActivity.class);
            LunchActivity.this.finish();

        } else {
            if (UserManager.IS_LOGIN) {
                ArmsUtils.startActivity(MainActivity.class);
                finish();
            }else{
                ArmsUtils.startActivity(LoginActivity.class);
                LunchActivity.this.finish();
            }
        }*/

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //boolean isFirstIn = (boolean) SharedPreferencesUtils.getParam(mContext, "isFirstOpen", true);
                if (false) {
                    SharedPreferencesUtils.setParam(mContext, "isFirstOpen", false);
                    ArmsUtils.startActivity(GuildActivity.class);
                    LunchActivity.this.finish();

                } else {
                    if (UserManager.IS_LOGIN) {
                        ArmsUtils.startActivity(MainActivity.class);
                        finish();
                    }else{
                        ArmsUtils.startActivity(LoginActivity.class);
                        LunchActivity.this.finish();
                    }
                }
            }
        }, 800);*/


        View view = findViewById(Window.ID_ANDROID_CONTENT);
        view.post(new Runnable() {
            @Override
            public void run() {
                // 渐变展示启动屏
                AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
                aa.setDuration(600);
                aa.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (UserManager.IS_LOGIN) {
                            ArmsUtils.startActivity(MainActivity.class);
                            finish();
                        }else{
                            ArmsUtils.startActivity(LoginActivity.class);
                            LunchActivity.this.finish();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                view.startAnimation(aa);
            }
        });
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0);
        }
    }
}
