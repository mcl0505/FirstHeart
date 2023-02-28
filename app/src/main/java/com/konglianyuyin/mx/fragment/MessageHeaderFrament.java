package com.konglianyuyin.mx.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konglianyuyin.mx.bean.MiniOfficBean;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * 作者:sgm
 * 描述:为消息列表添加头布局
 */
public class MessageHeaderFrament extends ConversationListFragment {
    private MiniOfficBean miniOfficBean;
    private TextView textNum;
    private TextView textTime;
    private TextView tv_userid;
    private TextView tv_title;
    private ImageView ci_head;
    private LinearLayout llRight;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        miniOfficBean = (MiniOfficBean) getArguments().getSerializable("miniOfficBean");



        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }
        });
    }



    //    @Override
//    protected List<View> onAddHeaderView() {
//        List<View> headerViews = super.onAddHeaderView();
//        View inflate = ArmsUtils.inflate(getActivity(), R.layout.item_message_header);
//        ci_head = inflate.findViewById(R.id.ci_head);
//        tv_title = inflate.findViewById(R.id.tv_title);
//        tv_userid = inflate.findViewById(R.id.tv_userid);
//        textTime = inflate.findViewById(R.id.textTime);
//        textNum = inflate.findViewById(R.id.textNum);
//        llRight = inflate.findViewById(R.id.llRight);
//        GlideArms.with(getActivity())
//                .load(miniOfficBean.getData().getImg())
//                .placeholder(R.mipmap.default_home)
//                .error(R.mipmap.default_home)
//                .circleCrop()
//                .into(ci_head);
//        tv_title.setText(miniOfficBean.getData().getName());
//        tv_userid.setText(miniOfficBean.getData().getTitle());
//        int unread = miniOfficBean.getData().getUnread();
//        if(unread > 0){
//            llRight.setVisibility(View.VISIBLE);
//            textTime.setText(miniOfficBean.getData().getCreated_at());
//            textNum.setText(miniOfficBean.getData().getUnread()+ "");
//        }else {
//            llRight.setVisibility(View.GONE);
//        }
//        inflate.setOnClickListener(v -> {
//            ArmsUtils.startActivity(MessageOfficeActivity.class);
//        });
//        headerViews.add(inflate);
//        return headerViews;
//    }

//    public void refresh(MiniOfficBean miniOfficBean) {
//        int unread = miniOfficBean.getData().getUnread();
//        if(unread > 0){
//            llRight.setVisibility(View.VISIBLE);
//            textTime.setText(miniOfficBean.getData().getCreated_at());
//            textNum.setText(miniOfficBean.getData().getUnread()+ "");
//        }else {
//            llRight.setVisibility(View.GONE);
//        }
//    }
}
