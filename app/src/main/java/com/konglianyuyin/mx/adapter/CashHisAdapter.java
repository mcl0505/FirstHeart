package com.konglianyuyin.mx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.scope.ActivityScope;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.bean.CashHis;

import java.util.ArrayList;

/**
 * 粉丝，关注
 */
@ActivityScope
public class CashHisAdapter extends BaseQuickAdapter<CashHis.DataBean, BaseViewHolder> {

    private int tag;
    public CashHisAdapter(int tag) {
        super(R.layout.item_cash_his, new ArrayList<>());
        this.tag = tag;
    }

    @Override
    protected void convert(BaseViewHolder helper, CashHis.DataBean item) {


        if(tag == 0){
            helper
                    .setText(R.id.tv_title, "钻石兑换零食")
                    .setText(R.id.tv_userid,  item.getAddtime())
                    .setText(R.id.btn_ok, "-" + item.mibi + "钻石");
        }else {
            helper
                    .setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_userid,  item.getAddtime())
                    .setText(R.id.btn_ok, "-" + item.getMoney() + "钻石");
        }

    }
}
