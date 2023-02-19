package com.konglianyuyin.mx;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jess.arms.utils.LogUtils;
import com.konglianyuyin.mx.app.Api;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;

public class SendMessageService extends Service {


    public List<SendMessageServiceBean> dataList = new ArrayList();
    private RtmClient mRtmClient;
    private RtmChannel mRtmChannel;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //    public void setDataList(List<String> dataList2222) {
//        this.dataList.addAll(dataList2222);
//        if (dataList.size() == 0) {
//            joinChanalMessage();
//        }
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        for (int i = 0; i < 1000; i++) {
//            try {
//                //  Thread.currentThread().sleep(10);
//                LogUtils.debugInfo("===" + i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        pos = 0;
        if (pos == 0) {
            dataList = (List<SendMessageServiceBean>) intent.getSerializableExtra("list");
            initMessage();
        } else {
            dataList.addAll((List<SendMessageServiceBean>) intent.getSerializableExtra("list"));
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private int pos = 0;

    /**
     * 2.初始化消息
     */
    private void initMessage() {
        //1.实例化
        try {
            LogUtils.debugInfo("===实例化开始");
            mRtmClient = RtmClient.createInstance(this, Api.AGORA_KEY,
                    new RtmClientListener() {
                        @Override
                        public void onConnectionStateChanged(int state, int reason) {

                        }

                        @Override
                        public void onMessageReceived(RtmMessage rtmMessage, String peerId) {

                        }

                        @Override
                        public void onTokenExpired() {

                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("You need to check the RTM init process.");
        }
        //2登录
//        mRtmClient.login(null, String.valueOf(UserManager.getUser().getUserId()), new ResultCallback<Void>() {
        mRtmClient.login(null, System.currentTimeMillis() + "", new ResultCallback<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                LogUtils.debugInfo("====登录消息成功");
                joinChanalMessage();
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.debugInfo("====登录消息失败");
            }
        });

    }

    public void joinChanalMessage() {
        LogUtils.debugInfo("====第" + pos + "个");
        //3创建频道消息，
        try {
            if (pos >= dataList.size()) {
                pos = 0;
                return;
            }
            if (dataList == null) {
                pos++;
                initMessage();
                return;
            }
            if (dataList.get(pos) == null) {
                pos++;
                initMessage();
                return;
            }
            if (dataList.get(pos).getRoomID() == null) {
                pos++;
                initMessage();
                return;
            }
            if (dataList.get(pos).getMessage() == null) {
                pos++;
                initMessage();
                return;
            }
//            if (mRtmChannel != null) mRtmChannel.release();
            mRtmChannel = mRtmClient.createChannel(dataList.get(pos).getRoomID(), new RtmChannelListener() {
                @Override
                public void onMessageReceived(RtmMessage rtmMessage, RtmChannelMember rtmChannelMember) {
                    LogUtils.debugInfo("====第二个");
                }

                @Override
                public void onMemberJoined(RtmChannelMember rtmChannelMember) {
                    LogUtils.debugInfo("====第二个");
                }

                @Override
                public void onMemberLeft(RtmChannelMember rtmChannelMember) {
                    LogUtils.debugInfo("====第二个");
                }

            });
        } catch (RuntimeException e) {
            LogUtils.debugInfo("====创建消息频道失败    " + dataList.get(pos).getRoomID() + "====" + e.getLocalizedMessage());
        }
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LogUtils.debugInfo("====加入频道消息成功");
                RtmMessage message = mRtmClient.createMessage();
                message.setText(dataList.get(pos).getMessage());
                mRtmChannel.sendMessage(message, null);
                mRtmChannel.leave(new ResultCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pos++;
                        LogUtils.debugInfo("====离开成功");
                        initMessage();
                    }

                    @Override
                    public void onFailure(ErrorInfo errorInfo) {
                        LogUtils.debugInfo("====离开失败");
                        pos++;
                        initMessage();
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                LogUtils.debugInfo("====加入频道消息失败");
                pos++;
                initMessage();
            }
        });
    }
}
