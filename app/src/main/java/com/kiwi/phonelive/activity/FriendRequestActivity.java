package com.kiwi.phonelive.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.adapter.FriendRequestAdapter;
import com.kiwi.phonelive.bean.FriendRequestBean;
import com.kiwi.phonelive.event.AgreeEvent;
import com.kiwi.phonelive.event.FriendCountEvent;
import com.kiwi.phonelive.event.RequestUnReadEvent;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.utils.WordUtil;
import com.kiwi.phonelive.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

public class FriendRequestActivity extends AbsActivity {

    private RecyclerView rv_friend_request;
    private FriendRequestAdapter adapter;
    private LoadDialog dialog;
    private ImageView iv_no_request;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected void main() {
        setTitle(WordUtil.getString(R.string.friend_request));
        rv_friend_request = findViewById(R.id.rv_friend_request);
        iv_no_request = findViewById(R.id.iv_no_request);
        dialog = new LoadDialog(mContext);
        dialog.show();
        initRecyView();
        initHttp();
    }

    private void initHttp() {
        /*
         * 请求消息置空
         * */
        RequestUnReadEvent requestUnReadEvent = new RequestUnReadEvent();
        requestUnReadEvent.setRead(true);
        EventBus.getDefault().post(requestUnReadEvent);

        /*
         * 请求列表
         * */
        HttpUtil.friendRequestList(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    dialog.hide();
                    JSONObject obj = JSON.parseObject(info[0]);
                    JSONArray request_list = obj.getJSONArray("request_list");
                    List<FriendRequestBean.RequestListBean> list = JSON.parseArray(request_list.toJSONString(), FriendRequestBean.RequestListBean.class);
                    if (list.size() > 0) {
                        iv_no_request.setVisibility(View.GONE);
                        adapter.setData(list);
                    } else {
                        iv_no_request.setVisibility(View.VISIBLE);
                    }
                } else {
                    dialog.hide();
                }
            }
        });
    }

    private void initRecyView() {
        adapter = new FriendRequestAdapter(mContext);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_friend_request.setLayoutManager(lm);
        rv_friend_request.setAdapter(adapter);
        adapter.setOnFriendRequestClickListener(new FriendRequestAdapter.OnFriendRequestClickListener() {
            @Override
            public void onAgreeClick(final FriendRequestBean.RequestListBean bean, int positon) {
                /*
                 * 同意添加好友
                 * */
                dialog.show();
                HttpUtil.friendVerify(bean.getUid(), "1", new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0) {
                            dialog.hide();
                            AgreeEvent agreeEvent = new AgreeEvent();
                            agreeEvent.setTouid(bean.getUid());
                            EventBus.getDefault().post(agreeEvent);
                            EventBus.getDefault().post(new FriendCountEvent());
                            finish();
                        } else {
                            dialog.hide();
                        }
                    }
                });
            }

            @Override
            public void onRefuseClick(FriendRequestBean.RequestListBean bean, int positon) {
                /*
                 * 拒绝加好友
                 * */
                dialog.show();
                HttpUtil.friendVerify(bean.getUid(), "2", new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0) {
                            dialog.hide();
                            HttpUtil.friendRequestList(new HttpCallback() {
                                @Override
                                public void onSuccess(int code, String msg, String[] info) {
                                    if (code == 0) {
                                        String s = Arrays.toString(info);
                                        JSONObject obj = JSON.parseObject(info[0]);
                                        JSONArray request_list = obj.getJSONArray("request_list");
                                        List<FriendRequestBean.RequestListBean> list = JSON.parseArray(request_list.toJSONString(), FriendRequestBean.RequestListBean.class);
                                        adapter.setData(list);
                                        iv_no_request.setVisibility(View.INVISIBLE);
                                    } else {
                                        iv_no_request.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
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
