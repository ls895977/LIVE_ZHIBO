package com.kiwi.phonelive.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.adapter.MyFriendAdapter;
import com.kiwi.phonelive.bean.MyFriendBean;
import com.kiwi.phonelive.bean.SearchUserBean;
import com.kiwi.phonelive.event.FriendCountEvent;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.utils.WordUtil;
import com.kiwi.phonelive.views.LoadDialog;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FriendListActivity extends AbsActivity {

    private RecyclerView rvMyFriend;
    private MyFriendAdapter adapter;
    private SearchUserBean mSearchUserBean;
    private LoadDialog dialog;
    private ImageView iv_no_data;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_list;
    }

    @Override
    protected void main() {
        setTitle(WordUtil.getString(R.string.friend));
        rvMyFriend = findViewById(R.id.rv_my_friend);
        iv_no_data = findViewById(R.id.iv_no_data);
        dialog = new LoadDialog(mContext);
        dialog.show();
        initRecyView();
        initHttp();
    }

    private void initHttp() {
        EventBus.getDefault().post(new FriendCountEvent());
        HttpUtil.getFriendList(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    dialog.hide();
                    JSONObject jsonObject = JSON.parseObject(info[0]);
                    JSONArray friend_num = jsonObject.getJSONArray("friend_list");
                    List<MyFriendBean.FriendListBean> myFriendBeans = JSON.parseArray(friend_num.toJSONString(), MyFriendBean.FriendListBean.class);
                    if (myFriendBeans.size() > 0) {
                        adapter.setList(myFriendBeans);
                        iv_no_data.setVisibility(View.GONE);
                    } else {
                        iv_no_data.setVisibility(View.VISIBLE);
                    }
                } else {
                    dialog.hide();
                }
            }
        });
    }

    private void initRecyView() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyFriend.setLayoutManager(lm);
        adapter = new MyFriendAdapter(mContext);
        rvMyFriend.setAdapter(adapter);
        adapter.setOnMyFriendClick(new MyFriendAdapter.onMyFriendClickListener() {
            @Override
            public void onChatClick(MyFriendBean.FriendListBean bean) {
                HttpUtil.getUserHome(bean.getId(), new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0 && info.length > 0) {
                            JSONObject obj = JSON.parseObject(info[0]);
                            SearchUserBean userBean = JSON.toJavaObject(obj, SearchUserBean.class);
                            mSearchUserBean = userBean;
                            ChatRoomActivity.forward(mContext, mSearchUserBean, false);
                        }
                    }
                });
            }

            @Override
            public void onDeleteClick(MyFriendBean.FriendListBean bean, final SwipeMenuLayout swipeMenuLayout) {
                dialog.show();
                HttpUtil.friendDel(bean.getId(), new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0) {
                            dialog.hide();
                            swipeMenuLayout.smoothClose();
                            initHttp();
                            EventBus.getDefault().post(new FriendCountEvent());
                        } else {
                            dialog.hide();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
