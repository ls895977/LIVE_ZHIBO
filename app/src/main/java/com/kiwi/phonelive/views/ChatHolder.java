package com.kiwi.phonelive.views;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.kiwi.phonelive.R;
import com.kiwi.phonelive.activity.ChatRoomActivity;
import com.kiwi.phonelive.event.SendCompleteEvent;
import com.kiwi.phonelive.im.ImUserBean;
import com.kiwi.phonelive.im.ImUserMsgEvent;
import com.kiwi.phonelive.interfaces.LifeCycleAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatHolder extends AbsMainViewHolder {
    private ChatListViewHolder mChatListViewHolder;

    public ChatHolder(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(ChatHolder.this);
        mChatListViewHolder = new ChatListViewHolder(mContext, (ViewGroup) findViewById(R.id.root), ChatListViewHolder.TYPE_MAIN);
        mChatListViewHolder.setActionListener(new ChatListViewHolder.ActionListener() {
            @Override
            public void onCloseClick() {
//                onBackPressed();
            }

            @Override
            public void onItemClick(ImUserBean bean) {
                ChatRoomActivity.forward(mContext, bean, bean.getAttent() == 1);
            }
        });
        mLifeCycleListener = new LifeCycleAdapter() {
            @Override
            public void onCreate() {
            }

            @Override
            public void onDestroy() {
                if (mChatListViewHolder != null) {
                    mChatListViewHolder.release();
                }
            }
        };
        mChatListViewHolder.addToParent();
        mChatListViewHolder.loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImUserMsgEvent(final ImUserMsgEvent e) {
        mChatListViewHolder.loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendCompleteEvent(final SendCompleteEvent e) {
        mChatListViewHolder.loadData();
    }
}
