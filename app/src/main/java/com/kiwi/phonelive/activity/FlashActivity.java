package com.kiwi.phonelive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kiwi.phonelive.Constants;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.bean.NewAdvertBean;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.http.MyHttpCallBack;

import java.util.Timer;
import java.util.TimerTask;

public class FlashActivity extends AbsActivity implements View.OnClickListener, MyHttpCallBack {

    private ImageView ivFlash;
    private TextView tvTime;
    private int recLen = 6;//跳过倒计时提示5秒
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    private String jumpurl;
    private String name;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    protected void main() {
        ivFlash = findViewById(R.id.iv_flash);
        tvTime = findViewById(R.id.tv_jump_flash);
        tvTime.setOnClickListener(this);//跳过监听
        ivFlash.setOnClickListener(this);
        HttpUtil.getAdvert(mContext, "3", 1, this);

        timer.schedule(task, 900, 900);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                forwardMainActivity();
            }
        }, 5500);//延迟5S后发送handler信息
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tvTime.setText(recLen + " " + mContext.getResources().getString(R.string.skip));
                    if (recLen < 0) {
                        timer.cancel();
                        tvTime.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_flash:
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                HttpUtil.getAdvertClick(mContext, "3", id, 2, this);
                forwardMainActivity();
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Constants.URL, jumpurl);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_jump_flash:
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                forwardMainActivity();
                break;
        }
    }


    /**
     * 跳转到首页
     */
    private void forwardMainActivity() {
        MainActivity.forward(mContext);
        finish();
    }

    @Override
    public void onHttpSuccess(int flag, String message) {
        if (flag == 1) {
            NewAdvertBean newAdvertBean = new Gson().fromJson(message, NewAdvertBean.class);
            if (newAdvertBean != null) {
                jumpurl = newAdvertBean.getData().get(0).getLink();
                name = newAdvertBean.getData().get(0).getName();
                id = newAdvertBean.getData().get(0).getId();
                String upload_url = newAdvertBean.getData().get(0).getUpload_url();
                Glide.with(mContext).load(upload_url).into(ivFlash);
                tvTime.setVisibility(View.VISIBLE);
            } else {
                ivFlash.setImageResource(R.mipmap.screen);
            }
        } else if (flag == 2) {

        }
    }

    @Override
    public void onHttpFail(int flag, String error_message) {
        if (flag == 1) {
            forwardMainActivity();
        }
    }
}
