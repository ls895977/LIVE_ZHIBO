package com.kiwi.phonelive.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.activity.AbsActivity;
import com.kiwi.phonelive.activity.CustomServcieActivity;
import com.kiwi.phonelive.activity.FriendRequestActivity;
import com.kiwi.phonelive.activity.SearchActivity;
import com.kiwi.phonelive.activity.SystemMessageActivity;
import com.kiwi.phonelive.bean.SystemMessageBean;
import com.kiwi.phonelive.dialog.SystemMessageDialogFragment;
import com.kiwi.phonelive.event.AgreeEvent;
import com.kiwi.phonelive.event.FollowEvent;
import com.kiwi.phonelive.event.RequestEvent;
import com.kiwi.phonelive.event.RequestUnReadEvent;
import com.kiwi.phonelive.event.SystemMsgEvent;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpConsts;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.im.ImListAdapter;
import com.kiwi.phonelive.im.ImMessageBean;
import com.kiwi.phonelive.im.ImMessageUtil;
import com.kiwi.phonelive.im.ImUserBean;
import com.kiwi.phonelive.im.ImUserMsgEvent;
import com.kiwi.phonelive.utils.SpUtil;
import com.kiwi.phonelive.utils.ToastUtil;
import com.kiwi.phonelive.utils.WordUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by cxf on 2018/10/24.
 */

public class ChatListViewHolder extends AbsViewHolder implements View.OnClickListener, ImListAdapter.ActionListener {

    public static final int TYPE_ACTIVITY = 0;
    public static final int TYPE_DIALOG = 1;
    public static final int TYPE_MAIN = 2;
    private int mType;
    //private View mNoData;
    private View mBtnSystemMsg;
    private RecyclerView mRecyclerView;
    private ImListAdapter mAdapter;
    private ActionListener mActionListener;
    private View mSystemMsgRedPoint;//系统消息的红点
    private TextView mSystemMsgContent;
    private TextView mSystemTime;
    private HttpCallback mSystemMsgCallback;
    private View mBtnBack;
    private String mLiveUid;//主播的uid
    private View top;
    private LinearLayout ll_gone;
    private View view;
    private RelativeLayout mBtnArtificial;
    private RelativeLayout mBtnCheckMsg;
    private ImageView btn_search;
    private TextView btn_ignore;
    private TextView check_red_point;

    public ChatListViewHolder(Context context, ViewGroup parentView, int mType) {
        super(context, parentView, mType);
    }

    @Override
    protected void processArguments(Object... args) {
        mType = (int) args[0];
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_chat_list;
    }

    @Override
    public void init() {
        //mNoData = findViewById(R.id.no_data);
        EventBus.getDefault().register(ChatListViewHolder.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_search = (ImageView) findViewById(R.id.btn_search);
        mBtnSystemMsg = findViewById(R.id.btn_system_msg);
        mBtnArtificial = (RelativeLayout) findViewById(R.id.btn_artificial_msg);
        mBtnCheckMsg = (RelativeLayout) findViewById(R.id.btn_check_msg);
        btn_ignore = (TextView) findViewById(R.id.btn_ignore);
        check_red_point = (TextView) findViewById(R.id.check_red_point);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ImListAdapter(mContext);
        mAdapter.setActionListener(this);
        mRecyclerView.setAdapter(mAdapter);
//        friendRequestNum(AppConfig.getInstance().getUid());
        mBtnBack = findViewById(R.id.btn_back);
        top = findViewById(R.id.top);
        if (mType == TYPE_ACTIVITY) {
            mBtnBack.setOnClickListener(this);
        } else if (mType == TYPE_MAIN) {
            mBtnBack.setVisibility(View.GONE);
            btn_ignore.setVisibility(View.GONE);
            btn_search.setVisibility(View.VISIBLE);
        } else {
            mBtnCheckMsg.setVisibility(View.GONE);
            mBtnArtificial.setVisibility(View.GONE);
            mBtnBack.setVisibility(View.INVISIBLE);
            top.setBackgroundColor(0xfff9fafb);
        }

        btn_ignore.setOnClickListener(this);
        mBtnSystemMsg.setOnClickListener(this);
        mBtnArtificial.setOnClickListener(this);
        mBtnCheckMsg.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        mAdapter.setContactView(mBtnSystemMsg);
        mSystemMsgRedPoint = findViewById(R.id.red_point);
        mSystemMsgContent = (TextView) findViewById(R.id.msg);
        mSystemTime = (TextView) findViewById(R.id.time);
        mSystemMsgCallback = new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0 && info.length > 0) {
                    SystemMessageBean bean = JSON.parseObject(info[0], SystemMessageBean.class);
                    if (mSystemMsgContent != null) {
                        mSystemMsgContent.setText(bean.getContent());
                    }
                    if (mSystemTime != null) {
                        mSystemTime.setText(bean.getAddtime());
                    }
                    if (SpUtil.getInstance().getBooleanValue(SpUtil.HAS_SYSTEM_MSG)) {
                        if (mSystemMsgRedPoint != null && mSystemMsgRedPoint.getVisibility() != View.VISIBLE) {
                            mSystemMsgRedPoint.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        };
        if (AppConfig.SYSTEM_MSG_APP_ICON) {
            ImageView avatar = (ImageView) findViewById(R.id.avatar);
            avatar.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public void release() {
        EventBus.getDefault().unregister(this);
        mActionListener = null;
        HttpUtil.cancel(HttpConsts.GET_SYSTEM_MESSAGE_LIST);
        HttpUtil.cancel(HttpConsts.GET_IM_USER_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestEvent(RequestEvent e) {
        friendRequestNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RequestUnReadEvent(RequestUnReadEvent e) {
        if (e.isRead()) {
            check_red_point.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAgreeEvent(AgreeEvent e) {
        ImMessageBean msg = ImMessageUtil.getInstance().createTextMessage(e.getTouid(), mContext.getResources().getString(R.string.friend_request_pass));
        ImMessageUtil.getInstance().sendMessage(msg);
        mAdapter.notifyDataSetChanged();
    }

    private void friendRequestNum() {
        HttpUtil.friedNum(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    JSONObject obj = JSON.parseObject(info[0]);
                    String request_num = obj.getString("request_num");
                    if (check_red_point != null) {
                        if (request_num.equals("0")) {
                            check_red_point.setVisibility(View.GONE);
                        } else {
                            check_red_point.setVisibility(View.VISIBLE);
                            check_red_point.setText(request_num);
                            RequestUnReadEvent requestUnReadEvent = new RequestUnReadEvent();
                            requestUnReadEvent.setRequestCount(request_num);
                            requestUnReadEvent.setRead(false);
                            EventBus.getDefault().post(requestUnReadEvent);
                        }
                    }
                }
            }
        });
    }


    public void setLiveUid(String liveUid) {
        mLiveUid = liveUid;
    }

    public void loadData() {
        getSystemMessageList();
        final boolean needAnchorItem = mType == TYPE_DIALOG
                && !TextUtils.isEmpty(mLiveUid) && !mLiveUid.equals(AppConfig.getInstance().getUid());
        String uids = ImMessageUtil.getInstance().getConversationUids();
        if (TextUtils.isEmpty(uids)) {
            if (needAnchorItem) {
                uids = mLiveUid;
            } else {
                return;
            }
        } else {
            if (needAnchorItem) {
                if (!uids.contains(mLiveUid)) {
                    uids = mLiveUid + "," + uids;
                }
            }
        }
        HttpUtil.getImUserInfo(uids, new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    List<ImUserBean> list = JSON.parseArray(Arrays.toString(info), ImUserBean.class);
                    list = ImMessageUtil.getInstance().getLastMsgInfoList(list);
                    if (mRecyclerView != null && mAdapter != null && list != null) {
                        if (needAnchorItem) {
                            int anchorItemPosition = -1;
                            for (int i = 0, size = list.size(); i < size; i++) {
                                ImUserBean bean = list.get(i);
                                if (bean != null) {
                                    if (mLiveUid.equals(bean.getId())) {
                                        anchorItemPosition = i;
                                        bean.setAnchorItem(true);
                                        if (!bean.isHasConversation()) {
                                            bean.setLastMessage(WordUtil.getString(R.string.im_live_anchor_msg));
                                        }
                                        break;
                                    }
                                }
                            }
                            if (anchorItemPosition > 0) {//把主播的会话排在最前面
                                Collections.sort(list, new Comparator<ImUserBean>() {
                                    @Override
                                    public int compare(ImUserBean bean1, ImUserBean bean2) {
                                        if (mLiveUid.equals(bean1.getId())) {
                                            return -1;
                                        } else if (mLiveUid.equals(bean2.getId())) {
                                            return 1;
                                        }
                                        return 0;
                                    }
                                });
                            }
                        }
                        mAdapter.setList(list);
                    }
                }
            }
        });
//        else {
//            if (mNoData != null && mNoData.getVisibility() != View.VISIBLE) {
//                mNoData.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (mActionListener != null) {
                    mActionListener.onCloseClick();
                }
                break;
            case R.id.btn_ignore:
                ignoreUnReadCount();
                break;
            case R.id.btn_system_msg:
                forwardSystemMessage();
                break;
            case R.id.btn_artificial_msg:
                mContext.startActivity(new Intent(mContext, CustomServcieActivity.class));
                break;
            case R.id.btn_check_msg:
                mContext.startActivity(new Intent(mContext, FriendRequestActivity.class));
                break;
            case R.id.btn_search:
                SearchActivity.forward(mContext);
                break;
        }
    }

    /**
     * 前往系统消息
     */
    private void forwardSystemMessage() {
        SpUtil.getInstance().setBooleanValue(SpUtil.HAS_SYSTEM_MSG, false);
        if (mSystemMsgRedPoint != null && mSystemMsgRedPoint.getVisibility() == View.VISIBLE) {
            mSystemMsgRedPoint.setVisibility(View.INVISIBLE);
        }
        if (mType == TYPE_ACTIVITY || mType == TYPE_MAIN) {
            SystemMessageActivity.forward(mContext);
        } else {
            SystemMessageDialogFragment fragment = new SystemMessageDialogFragment();
            fragment.show(((AbsActivity) mContext).getSupportFragmentManager(), "SystemMessageDialogFragment");
        }
    }

    @Override
    public void onItemClick(ImUserBean bean) {
        if (bean != null) {
            boolean res = ImMessageUtil.getInstance().markAllMessagesAsRead(bean.getId());
            if (res) {
                ImMessageUtil.getInstance().refreshAllUnReadMsgCount();
            }
            if (mActionListener != null) {
                mActionListener.onItemClick(bean);
            }
        }
    }

    @Override
    public void onItemDelete(ImUserBean bean, int size) {
        ImMessageUtil.getInstance().removeConversation(bean.getId());
//        if (size == 0) {
//            if (mNoData != null && mNoData.getVisibility() != View.VISIBLE) {
//                mNoData.setVisibility(View.VISIBLE);
//            }
//        }
    }

    public interface ActionListener {
        void onCloseClick();

        void onItemClick(ImUserBean bean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFollowEvent(FollowEvent e) {
        if (e != null) {
            if (mAdapter != null) {
                mAdapter.setFollow(e.getToUid(), e.getIsAttention());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemMsgEvent(SystemMsgEvent e) {
        getSystemMessageList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImUserMsgEvent(final ImUserMsgEvent e) {
        if (e != null && mRecyclerView != null && mAdapter != null) {
            int position = mAdapter.getPosition(e.getUid());
            loadData();
            if (position < 0) {
                HttpUtil.getImUserInfo(e.getUid(), new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0 && info.length > 0) {
                            ImUserBean bean = JSON.parseObject(info[0], ImUserBean.class);
                            bean.setLastMessage(e.getLastMessage());
                            bean.setUnReadCount(e.getUnReadCount());
                            bean.setLastTime(e.getLastTime());
                            mAdapter.insertItem(bean);
//                            if (mNoData != null && mNoData.getVisibility() == View.VISIBLE) {
//                                mNoData.setVisibility(View.INVISIBLE);
//                            }
                        }
                    }
                });
            } else {
                mAdapter.updateItem(e.getLastMessage(), e.getLastTime(), e.getUnReadCount(), position);
            }
        }
    }

    /**
     * 忽略未读
     */
    private void ignoreUnReadCount() {
        SpUtil.getInstance().setBooleanValue(SpUtil.HAS_SYSTEM_MSG, false);
        if (mSystemMsgRedPoint != null && mSystemMsgRedPoint.getVisibility() == View.VISIBLE) {
            mSystemMsgRedPoint.setVisibility(View.INVISIBLE);
        }
        ImMessageUtil.getInstance().markAllConversationAsRead();
        if (mAdapter != null) {
            mAdapter.resetAllUnReadCount();
        }
        ToastUtil.show(R.string.im_msg_ignore_unread_2);
    }

    /**
     * 获取系统消息
     */
    private void getSystemMessageList() {
        HttpUtil.getSystemMessageList(1, mSystemMsgCallback);
    }

}
