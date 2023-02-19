package com.konglianyuyin.mx.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.konglianyuyin.mx.R;


/**
 * 警告dialog
 */
public class WarningDialog extends Dialog {
    private String content, left, right,title;
    private TextView tvTitle;
    private ClickListenerInterface clickListenerInterface;

    public WarningDialog(Context context, String content, String left, String right, String title) {
        super(context, R.style.custom_dialog_style);
        this.content = content;
        this.left = left;
        this.right = right;
        this.title=title;
    }

    public interface ClickListenerInterface {
        void doCancel();

        void doConfirm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置不显示键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.dialog_common);
        TextView reason_tv = findViewById(R.id.reason_tv);
        TextView cancel_tv = findViewById(R.id.cancel_tv);
        TextView conform_tv = findViewById(R.id.conform_tv);
         tvTitle=findViewById(R.id.tvTitle);
        reason_tv.setText(content);
        cancel_tv.setText(left);
        conform_tv.setText(right);
        tvTitle.setText(title);
        if( title!=null&&!title.equals("")){
           tvTitle.setVisibility(View.VISIBLE);
        }
        cancel_tv.setOnClickListener(new clickListener());
        conform_tv.setOnClickListener(new clickListener());
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }


    public void setClicklistener(ClickListenerInterface clickListener) {
        clickListenerInterface = clickListener;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_tv:
                    clickListenerInterface.doCancel();
                    dismiss();
                    break;
                case R.id.conform_tv:
                    dismiss();
                    clickListenerInterface.doConfirm();
                    break;
            }
        }
    }
}
