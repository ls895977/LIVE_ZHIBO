package com.kiwi.phonelive.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.Constants;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.activity.EditProfileActivity;
import com.kiwi.phonelive.activity.FansActivity;
import com.kiwi.phonelive.activity.FollowActivity;
import com.kiwi.phonelive.activity.FriendListActivity;
import com.kiwi.phonelive.activity.MyCoinActivity;
import com.kiwi.phonelive.activity.MyProfitActivity;
import com.kiwi.phonelive.activity.MyVideoActivity;
import com.kiwi.phonelive.activity.RankActivity;
import com.kiwi.phonelive.activity.SettingActivity;
import com.kiwi.phonelive.activity.WebViewActivity;
import com.kiwi.phonelive.adapter.MainMeAdapter;
import com.kiwi.phonelive.bean.LevelBean;
import com.kiwi.phonelive.bean.UserBean;
import com.kiwi.phonelive.bean.UserItemBean;
import com.kiwi.phonelive.event.FriendCountEvent;
import com.kiwi.phonelive.event.UserInfoEvent;
import com.kiwi.phonelive.glide.ImgLoader;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpConsts;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.interfaces.CommonCallback;
import com.kiwi.phonelive.interfaces.LifeCycleAdapter;
import com.kiwi.phonelive.interfaces.MainAppBarLayoutListener;
import com.kiwi.phonelive.interfaces.OnItemClickListener;
import com.kiwi.phonelive.utils.IconUtil;
import com.kiwi.phonelive.utils.L;
import com.kiwi.phonelive.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by cxf on 2018/9/22.
 * 我的
 */

public class MainMeViewHolder extends AbsMainChildViewHolder implements OnItemClickListener<UserItemBean>, View.OnClickListener {
    private TextView mTtileView;
    private ImageView mAvatar;
    private TextView mName;
    private ImageView mSex;
    private ImageView mLevelAnchor;
    private ImageView mLevel;
    private TextView mID;
    private TextView friend_count;
    private TextView mFollow;
    private TextView mFans;
    private boolean mPaused;
    private RecyclerView mRecyclerView;
    private MainMeAdapter mAdapter;

    public MainMeViewHolder(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_main_me;
    }

    @Override
    public void init() {
        super.init();
        EventBus.getDefault().register(MainMeViewHolder.this);
        mTtileView = (TextView) findViewById(R.id.titleView);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mSex = (ImageView) findViewById(R.id.sex);
        mLevelAnchor = (ImageView) findViewById(R.id.level_anchor);
        mLevel = (ImageView) findViewById(R.id.level);
        mID = (TextView) findViewById(R.id.id_val);
        friend_count = (TextView) findViewById(R.id.friend_count);
        mFollow = (TextView) findViewById(R.id.follow);
        mFans = (TextView) findViewById(R.id.fans);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_friend).setOnClickListener(this);
        findViewById(R.id.btn_follow).setOnClickListener(this);
        findViewById(R.id.btn_fans).setOnClickListener(this);
        getFriendCount();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLifeCycleListener = new LifeCycleAdapter() {

            @Override
            public void onResume() {
                if (mPaused && mShowed) {
                    loadData();
                }
                mPaused = false;
            }

            @Override
            public void onPause() {
                mPaused = true;
            }

            @Override
            public void onDestroy() {
                L.e("main----MainMeViewHolder-------LifeCycle---->onDestroy");
                HttpUtil.cancel(HttpConsts.GET_BASE_INFO);
            }
        };
        mAppBarLayoutListener = new MainAppBarLayoutListener() {
            @Override
            public void onOffsetChanged(float rate) {
                mTtileView.setAlpha(rate);
            }
        };
        mNeedDispatch = true;
    }

    @Override
    public void setAppBarLayoutListener(MainAppBarLayoutListener appBarLayoutListener) {
    }


    @Override
    public void loadData() {
        if (isFirstLoadData()) {
            AppConfig appConfig = AppConfig.getInstance();
            UserBean u = appConfig.getUserBean();
            List<UserItemBean> list = appConfig.getUserItemList();
            if (u != null && list != null) {
                showData(u, list);
            }
        }
        HttpUtil.getBaseInfo(mCallback);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFrirendCountEvent(FriendCountEvent event) {
        getFriendCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
            AppConfig appConfig = AppConfig.getInstance();
            UserBean u = appConfig.getUserBean();
            List<UserItemBean> list = appConfig.getUserItemList();
            if (u != null && list != null) {
                showData(u, list);
            }
    }

    /*
     * 好友数
     * */
    private void getFriendCount() {
        HttpUtil.getFriendNum(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    JSONObject jsonObject = JSON.parseObject(info[0]);
                    String friend_num = jsonObject.getString("friend_num");
                    friend_count.setText(friend_num);
                }
            }
        });
    }

    private CommonCallback<UserBean> mCallback = new CommonCallback<UserBean>() {
        @Override
        public void callback(UserBean bean) {
            List<UserItemBean> list = AppConfig.getInstance().getUserItemList();
            if (bean != null) {
                showData(bean, list);
            }
        }
    };

    private void showData(UserBean u, List<UserItemBean> list) {
        ImgLoader.displayAvatar(u.getAvatar(), mAvatar);
        mTtileView.setText(u.getUserNiceName());
        mName.setText(u.getUserNiceName());
        mSex.setImageResource(IconUtil.getSexIcon(u.getSex()));
        AppConfig appConfig = AppConfig.getInstance();
        LevelBean anchorLevelBean = appConfig.getAnchorLevel(u.getLevelAnchor());
        if (anchorLevelBean != null) {
            ImgLoader.display(anchorLevelBean.getThumb(), mLevelAnchor);
        }
        LevelBean levelBean = appConfig.getLevel(u.getLevel());
        if (levelBean != null) {
            ImgLoader.display(levelBean.getThumb(), mLevel);
        }
        mID.setText(u.getLiangNameTip());
        mFollow.setText(StringUtil.toWan(u.getFollows()));
        mFans.setText(StringUtil.toWan(u.getFans()));
        if (list != null && list.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new MainMeAdapter(mContext, list);
                mAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setList(list);
            }
        }
    }

    @Override
    public void onItemClick(UserItemBean bean, int position) {
        String url = bean.getHref();
        if (TextUtils.isEmpty(url)) {
            switch (bean.getId()) {
                case 1:
                    forwardProfit();
                    break;
                case 2:
                    forwardCoin();
                    break;
                case 13:
                    forwardSetting();
                    break;
                case 19:
                    forwardMyVideo();
                    break;
                case 21:
                    rankList();
                    break;
            }
        } else {
            WebViewActivity.forward(mContext, url);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                forwardEditProfile();
                break;
            case R.id.btn_friend:
                forwardFriendList();
                break;
            case R.id.btn_follow:
                forwardFollow();
                break;
            case R.id.btn_fans:
                forwardFans();
                break;
        }
    }

    /*
     * 礼物排行
     * */
    private void rankList() {
        mContext.startActivity(new Intent(mContext, RankActivity.class));
    }

    /**
     * 编辑个人资料
     */
    private void forwardEditProfile() {
        mContext.startActivity(new Intent(mContext, EditProfileActivity.class));
    }

    /**
     * 我的关注
     */
    private void forwardFollow() {
        Intent intent = new Intent(mContext, FollowActivity.class);
        intent.putExtra(Constants.TO_UID, AppConfig.getInstance().getUid());
        mContext.startActivity(intent);
    }

    /**
     * 我的粉丝
     */
    private void forwardFans() {
        Intent intent = new Intent(mContext, FansActivity.class);
        intent.putExtra(Constants.TO_UID, AppConfig.getInstance().getUid());
        mContext.startActivity(intent);
    }

    /**
     * 好友列表
     */
    private void forwardFriendList() {
        mContext.startActivity(new Intent(mContext, FriendListActivity.class));
    }

    /**
     * 我的收益
     */
    private void forwardProfit() {
        mContext.startActivity(new Intent(mContext, MyProfitActivity.class));
    }

    /**
     * 我的钻石
     */
    private void forwardCoin() {
        mContext.startActivity(new Intent(mContext, MyCoinActivity.class));
    }

    /**
     * 设置
     */
    private void forwardSetting() {
        mContext.startActivity(new Intent(mContext, SettingActivity.class));
    }

    /**
     * 我的视频
     */
    private void forwardMyVideo() {
        mContext.startActivity(new Intent(mContext, MyVideoActivity.class));
    }
}
