package com.konglianyuyin.mx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.EmojiAdapter;
import com.konglianyuyin.mx.base.MyBaseArmFragment;
import com.konglianyuyin.mx.bean.EmojiBean;
import com.konglianyuyin.mx.bean.FirstEvent;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 作者:sgm
 * 描述:表情
 */

public class EmojiFragment extends MyBaseArmFragment {


    @BindView(R.id.myGrid)
    GridView myGrid;
//    private int id;

    @Inject
    CommonModel commonModel;

    private EmojiAdapter emojiAdapter;
//    private EmojiBean mEmojiBean;

    List<EmojiBean.DataBean> mDataBeanList;

//    public EmojiBean getmEmojiBean() {
//        return mEmojiBean;
//    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        id = getArguments().getInt("id");
        mDataBeanList = (List<EmojiBean.DataBean>) getArguments().getSerializable("data_list");

        emojiAdapter = new EmojiAdapter(getActivity());

        myGrid.setAdapter(emojiAdapter);

        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new FirstEvent(emojiAdapter.getList_adapter().get(position).getId() + "",
                        Constant.DIANJIBIAOQING));
            }
        });

        if(mDataBeanList != null && mDataBeanList.size()>0){

            loadData();
        }

    }

    private void loadData() {

        emojiAdapter.getList_adapter().clear();

        emojiAdapter.list_adapter.addAll(mDataBeanList);

//        RxUtils.loading(commonModel.emoji_list(""), this)
//                .subscribe(new ErrorHandleSubscriber<EmojiBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(EmojiBean emojiBean) {
//                        mEmojiBean = emojiBean;
//                        if (id == 0) {
//                            emojiAdapter.getList_adapter().clear();
//                            List<EmojiBean.DataBean> data = emojiBean.getData();
//                            for (int i = 0; i < 10; i++) {
//                                emojiAdapter.getList_adapter().add(data.get(i));
//                            }
//                            emojiAdapter.notifyDataSetChanged();
//                        } else {
//                            emojiAdapter.getList_adapter().clear();
//                            List<EmojiBean.DataBean> data = emojiBean.getData();
//                            for (int i = 10; i < data.size(); i++) {
//                                emojiAdapter.getList_adapter().add(data.get(i));
//                            }
//                            emojiAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public static EmojiFragment getInstance(List<EmojiBean.DataBean> list) {
        EmojiFragment fragment = new EmojiFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_list", (Serializable) list);
        fragment.setArguments(bundle);
        return fragment;
    }


}
