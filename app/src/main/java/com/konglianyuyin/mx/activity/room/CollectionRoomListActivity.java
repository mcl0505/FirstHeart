package com.konglianyuyin.mx.activity.room;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.konglianyuyin.mx.R;
import com.konglianyuyin.mx.adapter.MySaveOffAdapter;
import com.konglianyuyin.mx.adapter.MySaveOnAdapter;
import com.konglianyuyin.mx.adapter.OnItemClickListener;
import com.konglianyuyin.mx.app.utils.RxUtils;
import com.konglianyuyin.mx.base.MyBaseArmActivity;
import com.konglianyuyin.mx.base.UserManager;
import com.konglianyuyin.mx.bean.CollectionRoomListBean;
import com.konglianyuyin.mx.di.CommonModule;
import com.konglianyuyin.mx.di.DaggerCommonComponent;
import com.konglianyuyin.mx.service.CommonModel;
import com.konglianyuyin.mx.view.ItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class CollectionRoomListActivity extends MyBaseArmActivity {
    @Inject
    CommonModel commonModel;

    /*@BindView(R.id.liveing_list)
    MyListView liveingList;
    @BindView(R.id.no_liveing_list)
    MyListView noLiveingList;
    @BindView(R.id.sss)
    ScrollView sss;*/

    @BindView(R.id.btn_state1)
    LinearLayout btn_state1;
    @BindView(R.id.tv_state1)
    TextView tv_state1;
    @BindView(R.id.view_state1)
    View view_state1;
    @BindView(R.id.btn_state2)
    LinearLayout btn_state2;
    @BindView(R.id.tv_state2)
    TextView tv_state2;
    @BindView(R.id.view_state2)
    View view_state2;
    @BindView(R.id.onRecyclerView)
    RecyclerView onRecyclerView;
    @BindView(R.id.offRecyclerView)
    RecyclerView offRecyclerView;
    @BindView(R.id.no_data)
    LinearLayout noData;

    private MySaveOnAdapter mySaveOnAdapter;
    private MySaveOffAdapter mySaveOffAdapter;

    //private CollectionRoomListOnAdapter onAdapter; //正在直播的适配器
    //private CollectionRoomListOffAdapter offAdapter; //暂未开播的适配器

    private PopupWindow popupWindow, popupWindow1;
    private ImageView imageView, imageView1;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_save;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mySaveOnAdapter = new MySaveOnAdapter(this);
        onRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //onRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(this, 0x00000000, 8, 8);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        onRecyclerView.addItemDecoration(decoration);
        mySaveOnAdapter.setOnItemClickListener(new OnItemClickListener<CollectionRoomListBean.DataBean.OnBean>() {
            @Override
            public void onItemClick(CollectionRoomListBean.DataBean.OnBean bean, int position) {
                enterData(String.valueOf(bean.getUid()), "", commonModel, 1, bean.getRoom_cover());
            }
        });
        onRecyclerView.setAdapter(mySaveOnAdapter);

        mySaveOffAdapter = new MySaveOffAdapter(this);
        offRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //offRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration2 = new ItemDecoration(this, 0x00000000, 8, 8);
        decoration2.setOnlySetItemOffsetsButNoDraw(true);
        offRecyclerView.addItemDecoration(decoration2);
        mySaveOffAdapter.setOnItemClickListener(new OnItemClickListener<CollectionRoomListBean.DataBean.OffBean>() {
            @Override
            public void onItemClick(CollectionRoomListBean.DataBean.OffBean bean, int position) {
                enterData(String.valueOf(bean.getUid()), "", commonModel, 1, bean.getRoom_cover());
            }
        });
        offRecyclerView.setAdapter(mySaveOffAdapter);

        getCollectionRoomList();
        btn_state1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState(1);
            }
        });
        btn_state2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState(2);
            }
        });

        /*liveingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindow == null) {
                    View view1 = getLayoutInflater().inflate(R.layout.cancel_collection_item, null);
                    popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView = view1.findViewById(R.id.cancel_collection);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(view, 0, -20);
                imageView.setOnClickListener(v -> {
                    int uid = onAdapter.getList_adapter().get(position).getUid();
                    setCelceCollection(String.valueOf(uid), 1, position);
                });

                return true;
            }
        });
        noLiveingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindow1 == null) {
                    View view1 = getLayoutInflater().inflate(R.layout.cancel_collection_item, null);
                    popupWindow1 = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView1 = view1.findViewById(R.id.cancel_collection);
                    popupWindow1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popupWindow1.setFocusable(true);
                }
                popupWindow1.showAsDropDown(view, 0, -20);
                imageView1.setOnClickListener(v -> {
                    int uid = offAdapter.getList_adapter().get(position).getUid();
                    setCelceCollection(String.valueOf(uid), 2, position);
                });
                return true;
            }
        });
        liveingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterData(String.valueOf(onAdapter.getList_adapter().get(position).getUid()), "", commonModel, 1, onAdapter.getList_adapter().get(position).getRoom_cover());
            }
        });
        noLiveingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterData(String.valueOf(offAdapter.getList_adapter().get(position).getUid()), "", commonModel, 1, offAdapter.getList_adapter().get(position).getRoom_cover());
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle("我的收藏", true);
    }

    //获取收藏的房间列表
    private void getCollectionRoomList() {
        RxUtils.loading(commonModel.getCollectionRoomList(String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<CollectionRoomListBean>(mErrorHandler) {
                    @Override
                    public void onNext(CollectionRoomListBean collectionRoomListBean) {
                        if (collectionRoomListBean.getData().getOn().size() == 0) {
                            mySaveOnAdapter.clearData();
                            //onRecyclerView.setVisibility(View.GONE);
                        } else {
                            mySaveOnAdapter.setList(collectionRoomListBean.getData().getOn());
                            //mySaveOnAdapter.getList().addAll(collectionRoomListBean.getData().getOn());
                            mySaveOnAdapter.notifyDataSetChanged();
                        }

                        if (collectionRoomListBean.getData().getOff().size() == 0) {
                            mySaveOffAdapter.clearData();
                            //offRecyclerView.setVisibility(View.GONE);
                        } else {
                            mySaveOffAdapter.setList(collectionRoomListBean.getData().getOff());
                            //mySaveOffAdapter.getList().addAll(collectionRoomListBean.getData().getOff());
                            mySaveOffAdapter.notifyDataSetChanged();
                        }

                        /*if (collectionRoomListBean.getData().getOn().size() == 0 && collectionRoomListBean.getData().getOff().size() == 0) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }*/

                        tv_state1.setTextColor(getResources().getColor(R.color.bottom_text_ok));
                        tv_state1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        view_state1.setVisibility(View.VISIBLE);
                        tv_state2.setTextColor(getResources().getColor(R.color.color_222222));
                        tv_state2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        view_state2.setVisibility(View.INVISIBLE);
                        mType = 1;
                        offRecyclerView.setVisibility(View.GONE);
                        if (mySaveOnAdapter.getList().size() == 0) {
                            onRecyclerView.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            onRecyclerView.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void setCelceCollection(String uid, int a, int pos) {
        /*RxUtils.loading(commonModel.remove_mykeep(uid,
                String.valueOf(UserManager.getUser().getUserId())), this)
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        toast(baseBean.getMessage());

                        if (a == 1) {
                            onAdapter.getList_adapter().remove(pos);
                            onAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                        } else if (a == 2) {
                            offAdapter.getList_adapter().remove(pos);
                            offAdapter.notifyDataSetChanged();
                            popupWindow1.dismiss();
                        }
                    }
                });*/
    }

    private int mType = 1;

    private void changeState(int type) {
        switch (type) {
            case 1:
                if (mType != 1) {
                    tv_state1.setTextColor(getResources().getColor(R.color.bottom_text_ok));
                    tv_state1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    view_state1.setVisibility(View.VISIBLE);
                    tv_state2.setTextColor(getResources().getColor(R.color.color_222222));
                    tv_state2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    view_state2.setVisibility(View.INVISIBLE);
                    mType = 1;
                    offRecyclerView.setVisibility(View.GONE);
                    if (mySaveOnAdapter.getList().size() == 0) {
                        onRecyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    } else {
                        onRecyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    }
                }
                break;
            case 2:
                if (mType != 2) {
                    tv_state1.setTextColor(getResources().getColor(R.color.color_222222));
                    tv_state1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    view_state1.setVisibility(View.INVISIBLE);
                    tv_state2.setTextColor(getResources().getColor(R.color.bottom_text_ok));
                    tv_state2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    view_state2.setVisibility(View.VISIBLE);
                    mType = 2;
                    onRecyclerView.setVisibility(View.GONE);
                    if (mySaveOffAdapter.getList().size() == 0) {
                        offRecyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    } else {
                        offRecyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        LogUtils.debugInfo("====onRestart");
        if (AdminHomeActivity.isStart && AdminHomeActivity.isTop) {
            startActivity(new Intent(this, AdminHomeActivity.class));
        }
    }

}
