package com.kiwi.phonelive.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.bean.RechargeRecordBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyCoinRechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordBean.DataBean> {
    public MyCoinRechargeRecordAdapter(List<RechargeRecordBean.DataBean> data) {
        super(R.layout.recharge_record_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RechargeRecordBean.DataBean bean) {
        if (bean.getStatus().equals("1")) { //成功
            baseViewHolder.setText(R.id.tv_recharge_time, getDate2String(bean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            baseViewHolder.setText(R.id.recharge_coin_item_number, "+" + bean.getDiamond_num());
            baseViewHolder.getView(R.id.iv_recharge_coin_item).setVisibility(View.VISIBLE);
        } else if (bean.getStatus().equals("0")) { //失败
            baseViewHolder.setText(R.id.tv_recharge_time, getDate2String(bean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            baseViewHolder.setText(R.id.recharge_coin_item_number, mContext.getResources().getString(R.string.fail));
            baseViewHolder.getView(R.id.iv_recharge_coin_item).setVisibility(View.GONE);
        }else if(bean.getStatus().equals("2")){//审核中
            baseViewHolder.setText(R.id.tv_recharge_time, getDate2String(bean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
            baseViewHolder.setText(R.id.recharge_coin_item_number,  mContext.getResources().getString(R.string.in_the_review));
            baseViewHolder.getView(R.id.iv_recharge_coin_item).setVisibility(View.GONE);
        }
    }

    public static String getDate2String(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

}
