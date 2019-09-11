package com.kiwi.phonelive.activity;

import android.view.ViewGroup;

import com.kiwi.phonelive.R;
import com.kiwi.phonelive.event.FinshEvent;
import com.kiwi.phonelive.views.MainListViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RankActivity extends AbsActivity {
    private MainListViewHolder mainListViewHolder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void main() {
        EventBus.getDefault().register(RankActivity.this);
        mainListViewHolder = new MainListViewHolder(mContext, (ViewGroup) findViewById(R.id.root));
        mainListViewHolder.addToParent();
        mainListViewHolder.loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinshEvent(FinshEvent e) {
        finish();
    }
}
