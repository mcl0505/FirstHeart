package com.konglianyuyin.mx.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.Api;
import com.konglianyuyin.mx.bean.ZengSongHisBean;
import com.konglianyuyin.mx.utils.TimeUtil;

import java.util.ArrayList;

import static io.rong.imkit.utilities.RongUtils.dip2px;

/**
 * 排麦
 */
@ActivityScope
public class ZengSongHisAdapter extends BaseQuickAdapter<ZengSongHisBean.DataBean, BaseViewHolder> {


    public ZengSongHisAdapter() {
        super(R.layout.item_zengsong_his, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, ZengSongHisBean.DataBean item) {

        try {
            helper.setText(R.id.tv_name, item.getFromUserInfo().getNickname())
                    .setText(R.id.tv_id, "ID:" + item.getFrom_uid())
                    .setText(R.id.tv_num, item.getMoney())
                    .setText(R.id.age_text, item.getFromUserInfo().getAge() + "")
                    .setText(R.id.tv_time, TimeUtil.toDateYmd(Long.valueOf(item.getAddtime()) * 1000));

            if (!TextUtils.isEmpty(item.getFromUserInfo().getBright_num())) {
                ((TextView) helper.getView(R.id.tv_id)).setText("ID:" + item.getFromUserInfo().getBright_num());

                ((TextView) helper.getView(R.id.tv_id)).setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                Drawable left = mContext.getResources().getDrawable(R.mipmap.jianhao);
                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
                ((TextView) helper.getView(R.id.tv_id)).setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
                ((TextView) helper.getView(R.id.tv_id)).setCompoundDrawablePadding(dip2px(4));
            }else {
                ((TextView) helper.getView(R.id.tv_id)).setCompoundDrawables(null, null, null, null);  // left, top, right, bottom
            }
            ImageView imgSex = helper.getView(R.id.sex_image);
            LinearLayout llSex = helper.getView(R.id.ll_sex);
            llSex.setBackgroundDrawable(item.getFromUserInfo().getSex() == 1 ? mContext.getDrawable(R.drawable.jianbian_gerenzhongxin) : mContext.getDrawable(R.drawable.jianbian_gerenzhongxin2));
            imgSex.setImageDrawable(item.getFromUserInfo().getSex() == 1 ? mContext.getDrawable(R.mipmap.sec_nan) : mContext.getDrawable(R.mipmap.sex_nv));
            ImageView imgUser = helper.getView(R.id.iv_cover);
            GlideArms.with(mContext)
                    .load(Api.APP_DOMAIN + item.getFromUserInfo().getHeadimgurl())
                    .placeholder(R.mipmap.no_tou)
                    .error(R.mipmap.no_tou)
                    .circleCrop()
                    .into(imgUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
