package com.kiwi.phonelive.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.bean.SearchUserBean;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.utils.ToastUtil;

public class CustomServcieActivity extends AbsActivity implements View.OnClickListener {

    private ImageView btn_back;
    private LinearLayout ll_custom_service_1;
    private LinearLayout ll_custom_service_2;
    private LinearLayout ll_custom_service_3;
    private String userid;
    private SearchUserBean mSearchUserBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customservcie;
    }

    @Override
    protected void main() {
        btn_back = findViewById(R.id.btn_back);
        ll_custom_service_1 = findViewById(R.id.ll_custom_service_1);
        ll_custom_service_2 = findViewById(R.id.ll_custom_service_2);
        ll_custom_service_3 = findViewById(R.id.ll_custom_service_3);
        HttpUtil.getCusServiceUser(new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    JSONObject obj = JSON.parseObject(info[0]);
                    userid = obj.getString("userid");
                } else {
                    ToastUtil.show(R.string.no_kefu);
                    finish();
                }
            }
        });
        btn_back.setOnClickListener(this);
        ll_custom_service_1.setOnClickListener(this);
        ll_custom_service_2.setOnClickListener(this);
        ll_custom_service_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_custom_service_1:
                HttpUtil.getUserHome(userid, new HttpCallback() {
                    @Override
                    public void onSuccess(int code, String msg, String[] info) {
                        if (code == 0 && info.length > 0) {
                            JSONObject obj = JSON.parseObject(info[0]);
                            SearchUserBean userBean = JSON.toJavaObject(obj, SearchUserBean.class);
                            mSearchUserBean = userBean;
                            ChatRoomActivity.forward(mContext, mSearchUserBean, true);
                        }else {
                            ToastUtil.show(R.string.no_kefu);
                            finish();
                        }
                    }
                });
                break;
            case R.id.ll_custom_service_2:
                break;
            case R.id.ll_custom_service_3:
                break;
        }
    }
}
