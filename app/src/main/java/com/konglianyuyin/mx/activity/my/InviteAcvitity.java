package com.konglianyuyin.mx.activity.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.BaseWebActivity;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.ShareBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class InviteAcvitity extends MyBaseArmActivity {

    @BindView(R.id.tv_invite_rule)
    TextView tvInviteRule;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R.id.tv_copy_url)
    TextView tvCopyUrl;
    @BindView(R.id.iv_invite)
    TextView ivInvite;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.cl_parent)
    ConstraintLayout clParent;

    @Inject
    CommonModel commonModel;

    private String shareUrl;

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
        return R.layout.activity_invite_acvitity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getQrCode();
    }

    private void getQrCode() {
        showDialogLoding();
        Log.e("TAG","token"+UserManager.getUser().getNewtoken());
        RxUtils.loading(commonModel.getQrCode(UserManager.getUser().getNewtoken(),UserManager.getUser().getUserId()+""), this)
                .subscribe(new ErrorHandleSubscriber<ShareBean>(mErrorHandler) {
                    @Override
                    public void onNext(ShareBean userBean) {
                        disDialogLoding();
                        ArmsUtils.obtainAppComponentFromContext(InviteAcvitity.this)
                                .imageLoader()
                                .loadImage(InviteAcvitity.this, ImageConfigImpl
                                        .builder()
                                        .url(userBean.getData().getShareimg())
                                        .placeholder(R.mipmap.default_home)
                                        .imageView(ivQrCode)
                                        .errorPic(R.mipmap.default_home)
                                        .build());

                        tvInviteCode.setText("我的推荐码："+userBean.getData().getSharecode());
                        shareUrl = userBean.getData().getShareurl();
                    }

                    @Override
                    public void onError( Throwable t) {
                        super.onError(t);
                        disDialogLoding();
                        toast(t.getMessage());
                    }
                });
    }

    @OnClick({R.id.tv_invite_rule, R.id.tv_copy_url, R.id.iv_invite,R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite_rule:
                Intent intent = new Intent(InviteAcvitity.this, BaseWebActivity.class);
                intent.putExtra("url", Api.APP_DOMAIN+"/index/index/show?id=11");
                intent.putExtra("title", "邀请规则");
                startActivity(intent);
                break;
            case R.id.tv_copy_url:
                if(shareUrl != null && shareUrl.length() > 0){
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", shareUrl);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(this,"已复制链接到剪切板",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_invite://整个页面截图保存到相册
                saveBitmapToSystemPhotoAlbum(convertBitmap());
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    private void saveBitmapToSystemPhotoAlbum(Bitmap bitmap) {
        if(bitmap == null){
            Log.e(TAG,"bitmap is null");
            return;
        }
        //检查有没有存储权限
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "请至权限中心打开应用权限", Toast.LENGTH_SHORT).show();
        } else {
            // 新建目录appDir，并把图片存到其下
            File appDir = new File(getExternalFilesDir(null).getPath()+ "BarcodeBitmap");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 把file里面的图片插入到系统相册中
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "已保存到系统相册", Toast.LENGTH_LONG).show();

            // 通知相册更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }
    }

    private Bitmap convertBitmap(){
        Bitmap bitmap  = Bitmap.createBitmap(clParent.getWidth(), clParent.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        clParent.draw(c);

        return bitmap;
    }
}