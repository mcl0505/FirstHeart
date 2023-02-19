package com.konglianyuyin.mx.Interface;

import com.konglianyuyin.mx.bean.MyPackBean;
import com.konglianyuyin.mx.bean.Rank;

import java.util.List;

public class MyPackBaoShiInter {
    public interface onListener {
        void OnListener(MyPackBean.DataBean bean);
    }


    public interface onListenerTwo {
        void OnListener(MyPackBean.DataBean bean, int tag, int type);
    }

    public interface onRankInter {
        void OnRankInter(int distance);
    }

    public interface NewData{
        void NewData(List<Rank.DataBean.TopBean> topBean ,int sign);
    }
}
