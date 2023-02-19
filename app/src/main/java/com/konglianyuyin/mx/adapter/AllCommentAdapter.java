package com.konglianyuyin.mx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.app.view.CircularImage;
import com.konglianyuyin.mx.base.MyBaseAdapter;
import com.konglianyuyin.mx.bean.DynamicDetailsBean;
import com.konglianyuyin.mx.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCommentAdapter extends MyBaseAdapter<DynamicDetailsBean.DataBean.CommentsBean> {
    private Context context;

    public AllCommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllCommentHolder CH;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hot_comment_item, parent, false);
            CH = new AllCommentHolder(convertView);
            convertView.setTag(CH);
        } else {
            CH = (AllCommentHolder) convertView.getTag();
        }
        //头像
        if (!TextUtils.isEmpty(list_adapter.get(position).getHeadimgurl())) {
            ArmsUtils.obtainAppComponentFromContext(context)
                    .imageLoader()
                    .loadImage(context, ImageConfigImpl
                            .builder()
                            .url(list_adapter.get(position).getHeadimgurl())
                            .placeholder(R.mipmap.no_tou)
                            .imageView(CH.headImage)
                            .errorPic(R.mipmap.no_tou)
                            .build());
        }
        //昵称以及评论内容
        CH.nameText.setText(list_adapter.get(position).getNickname());
        if (TextUtils.isEmpty(list_adapter.get(position).getReply())) {
            CH.replyText.setVisibility(View.GONE);
            CH.replyNameText.setVisibility(View.GONE);
            CH.contentText.setText(list_adapter.get(position).getContent());
        } else {
            CH.replyText.setVisibility(View.VISIBLE);
            CH.replyNameText.setVisibility(View.VISIBLE);
            CH.replyNameText.setText(list_adapter.get(position).getReply());
            CH.contentText.setText("：" + list_adapter.get(position).getContent());
        }
        //时间
        CH.timeText.setText(TimeUtil.chatTime(list_adapter.get(position).getCreated_at()));
        //点赞
        CH.fabulous.setText(list_adapter.get(position).getPraise() + "");
        if (list_adapter.get(position).getIs_praise() == 1) {
            CH.fabulous.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dongtai_hudong_yidianzan, 0, 0, 0);
        } else {
            CH.fabulous.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dongtai_hudong_dianzan, 0, 0, 0);
        }
        CH.fabulous.setOnClickListener(v -> {
            if (onThreeClick != null) {
                onThreeClick.threeClick(position);
            }
        });

        CH.neiRong.setOnClickListener(v -> {
            if (onOneClick != null) {
                onOneClick.oneClick(position);
            }
        });
        CH.headImage.setOnClickListener(v -> {
            if (onOneClick != null) {
                onOneClick.onHeadClick(position);
            }
        });
        return convertView;
    }

    static class AllCommentHolder {
        @BindView(R.id.head_image)
        CircularImage headImage;
        @BindView(R.id.name_text)
        TextView nameText;
        @BindView(R.id.grade_image)
        ImageView gradeImage;
        @BindView(R.id.time_text)
        TextView timeText;
        @BindView(R.id.reply_text)
        TextView replyText;
        @BindView(R.id.reply_name_text)
        TextView replyNameText;
        @BindView(R.id.content_text)
        TextView contentText;
        @BindView(R.id.fabulous)
        TextView fabulous;
        @BindView(R.id.neirong)
        LinearLayout neiRong;

        public AllCommentHolder(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnThreeClick {
        void threeClick(int pos);
    }

    OnThreeClick onThreeClick;

    public void setOnThreeClick(OnThreeClick onThreeClick) {
        this.onThreeClick = onThreeClick;
    }

    public void setOnOneClick(OnOneClick onOneClick) {
        this.onOneClick = onOneClick;
    }

    public interface OnOneClick {
        void oneClick(int pos);
        void onHeadClick(int pos);
    }

    OnOneClick onOneClick;
}
