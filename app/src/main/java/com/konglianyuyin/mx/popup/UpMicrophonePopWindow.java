package com.konglianyuyin.mx.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.RequestUpMicrophoneAdapter;
import com.konglianyuyin.mx.bean.RequestUpMicrophoneBean;
import com.konglianyuyin.mx.utils.ScreenDimenUtil;

import java.util.List;

public class UpMicrophonePopWindow extends PopupWindow {

    private Activity mContext;
    private RecyclerView rvUpMicrophone;
    private List<RequestUpMicrophoneBean.DataBean> requestUpMicrophoneData;
    private RequestUpMicrophoneAdapter adapter;

    public UpMicrophonePopWindow(Activity context, List<RequestUpMicrophoneBean.DataBean> requestUpMicrophoneData) {
        super(context);
        this.mContext = context;
        this.requestUpMicrophoneData = requestUpMicrophoneData;
        init();
    }

    private void init(){
        View mView = View.inflate(mContext, R.layout.layout_pop_up_microphone, null);

        rvUpMicrophone = mView.findViewById(R.id.rv_up_microphone);
        getUpMicrophoneList();

        setFocusable(true);
        // 导入布局
        this.setContentView(mView);
        setWidth(ScreenDimenUtil.getInstance().getScreenWdith());
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);
        setAlpha(0.7f);
    }

    private void getUpMicrophoneList() {
        rvUpMicrophone.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RequestUpMicrophoneAdapter();
        rvUpMicrophone.setLayoutManager(new LinearLayoutManager(mContext));
        rvUpMicrophone.setAdapter(adapter);
        adapter.setNewData(requestUpMicrophoneData);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(listener == null){
                    return;
                }
                switch (view.getId()){
                    case R.id.tv_agree:
                        listener.agree(position);
                        dismiss();
                        break;
                    case R.id.tv_refuse:
                        listener.refuse(position);
                        dismiss();
                        break;
                }
            }
        });
    }

    private void setAlpha(float alpha){
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = alpha;
        mContext.getWindow().setAttributes(params);
    }

    @Override
    public void dismiss() {
        setAlpha(1);
        super.dismiss();
    }

    private AgreeOrRefuseUpMicrophoneListener listener;

    public void setListener(AgreeOrRefuseUpMicrophoneListener listener){
        this.listener = listener;
    }

    public interface AgreeOrRefuseUpMicrophoneListener{
        void agree(int position);
        void refuse(int position);
    }
}
