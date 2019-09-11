package com.kiwi.phonelive.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.adapter.MyCoinRechargeRecordAdapter;
import com.kiwi.phonelive.bean.RechargeRecordBean;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCoinRechargeRecordActivity extends AbsActivity implements View.OnClickListener {

    private RecyclerView recycle;
    private ImageView btn_back;
    private List<RechargeRecordBean.DataBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_recharge_record;
    }

    @Override
    protected void main() {
        btn_back = findViewById(R.id.btn_back);
        recycle = findViewById(R.id.recycle);
        initRecycle();
        btn_back.setOnClickListener(this);
    }

    private void initRecycle() {
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.activity_recharge_empty, new RelativeLayout(mContext), false);
        final MyCoinRechargeRecordAdapter adapter = new MyCoinRechargeRecordAdapter(list);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        recycle.setLayoutManager(lm);
        adapter.setEmptyView(emptyView);
        recycle.setAdapter(adapter);
        HttpUtil.coinRechargeRecord(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    List<RechargeRecordBean.DataBean> list = JSON.parseArray(Arrays.toString(info), RechargeRecordBean.DataBean.class);
                    adapter.setNewData(list);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
