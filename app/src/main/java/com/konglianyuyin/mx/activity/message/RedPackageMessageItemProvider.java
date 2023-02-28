package com.konglianyuyin.mx.activity.message;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.utils.LogUtils;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.ILinkClickListener;
import io.rong.imkit.widget.LinkTextViewMovementMethod;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imkit.widget.provider.IContainerItemProvider.MessageProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = RedPackageMessage.class)
public class RedPackageMessageItemProvider extends MessageProvider<RedPackageMessage>{

    @Override
    public void bindView(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {
        LogUtils.d("MSG", JSON.toJSONString(uiMessage));
        RedPackageMessageItemProvider.ViewHolder holder = (RedPackageMessageItemProvider.ViewHolder)view.getTag();
        holder.message.setText(((RedPackageMessage)uiMessage.getContent()).getContent());
    }

    @Override
    public Spannable getContentSummary(RedPackageMessage redPackageMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, RedPackageMessage redPackageMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_msg_red_package, (ViewGroup)null);
        RedPackageMessageItemProvider.ViewHolder holder = new RedPackageMessageItemProvider.ViewHolder();
        holder.message = (TextView)view.findViewById(R.id.redNumber);
        view.setTag(holder);
        return view;
    }

    private static class ViewHolder {
        TextView message;
        private ViewHolder() {
        }
    }
}
