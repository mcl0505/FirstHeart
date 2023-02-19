package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.RoomRankBean;
import com.konglianyuyin.mx.ext.ExtConfig;

import java.util.ArrayList;

import static com.konglianyuyin.mx.app.Api.APP_DOMAIN;
import static io.rong.imkit.utilities.RongUtils.dip2px;

/**
 * 排行榜
 */
@ActivityScope
public class RoomRankAdapterNew extends BaseQuickAdapter<RoomRankBean.DataBean.OtherBean, BaseViewHolder> {
    private int type;
    private Context context;
    boolean mIsFirstTransion = true;
    BaseViewHolder mHelper;
    boolean is_show_num;

    public RoomRankAdapterNew(int type, boolean is_show_num, Context context) {
        super(R.layout.item_room_rank_new, new ArrayList<>());
        this.type = type;
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoomRankBean.DataBean.OtherBean item) {
        mHelper = helper;
        helper.addOnClickListener(R.id.ci_head);
        ImageView sex = helper.getView(R.id.sex);
        if (item.getSex() == 1) {
            sex.setImageResource(R.mipmap.gender_boy);
        } else if (item.getSex() == 2) {
            sex.setImageResource(R.mipmap.gender_girl);
        }
        TextView roomRankId1 = helper.getView(R.id.room_rank_id);
        roomRankId1.setText("ID:" + item.getId());
        if (!TextUtils.isEmpty(item.getBright_num())) {
            roomRankId1.setText("ID:" + item.getBright_num());
            roomRankId1.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            Drawable left = context.getResources().getDrawable(R.mipmap.jianhao);
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());  // left, top, right, bottom
            roomRankId1.setCompoundDrawables(left, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        } else {
            roomRankId1.setTextColor(mContext.getResources().getColor(R.color.black));
            roomRankId1.setCompoundDrawables(null, null, null, null);  // left, top, right, bottom
            roomRankId1.setCompoundDrawablePadding(dip2px(4));
        }

        String ml = "";
        if (is_show_num){
            ml = item.getMizuan();
        }else {
            if (ExtConfig.isOpenRoomRankLookMyself){
                if (UserManager.getUser().getUserId() == Integer.parseInt(item.getId())){
                    ml = item.getMizuan();
                }
            }else {
                ml = item.getMizuan();
            }
        }

        helper.setText(R.id.text1, String.valueOf(helper.getPosition() + 4))
                .setText(R.id.tv_title, item.getName())
                .setText(R.id.textNum,ml);
//                .setText(R.id.textNum, NumberUtil.getBigDecimal(String.valueOf(item.getMizuan())));


        if (!TextUtils.isEmpty(item.getImg())) {
            String headUrl = item.getImg();
            if(!headUrl.contains("http")){
                headUrl = APP_DOMAIN+headUrl;
            }
            ArmsUtils.obtainAppComponentFromContext(mContext)
                    .imageLoader()
                    .loadImage(mContext, ImageConfigImpl
                            .builder()
                            .url(headUrl)
                            .placeholder(R.mipmap.no_tou)
                            .imageView(helper.getView(R.id.ci_head))
                            .errorPic(R.mipmap.no_tou)
                            .build());
        }
    }

    public void setFirstItemBg(boolean isTransion) {
        LogUtils.debugInfo("方法进来了==3==");
        if (mHelper != null) {
            if (mHelper.getPosition() == 1) {
                if (isTransion) {
                    if (mIsFirstTransion) {
                        mIsFirstTransion = false;
                        mHelper.setBackgroundRes(R.id.item_back, R.drawable.transion_corner_shape);
                        LogUtils.debugInfo("变成透明了");
                        notifyItemChanged(1);
                    }
                } else {
                    if (!mIsFirstTransion) {
                        mIsFirstTransion = true;
                        mHelper.setBackgroundRes(R.id.item_back, R.drawable.white_corner_shape);
                        LogUtils.debugInfo("变成白色了");
                        notifyItemChanged(1);
                    }
                }


            } else {
                mHelper.setBackgroundRes(R.id.item_back, R.drawable.white_corner_shape_two);
            }
        }
    }


//    public static class ViewHolder {
//        TextView tv_title, textNum, text1;
//        CircularImage ci_head;
//
//        public ViewHolder(View convertView) {
//
//            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//            textNum = (TextView) convertView.findViewById(R.id.textNum);
//            ci_head = convertView.findViewById(R.id.ci_head);
//        }
//    }

}