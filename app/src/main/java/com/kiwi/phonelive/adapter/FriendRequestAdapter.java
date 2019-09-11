package com.kiwi.phonelive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kiwi.phonelive.R;
import com.kiwi.phonelive.bean.FriendRequestBean;
import com.kiwi.phonelive.glide.ImgLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<FriendRequestBean.RequestListBean> list = new ArrayList<>();

    public FriendRequestAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendRequestHolder(mInflater.inflate(R.layout.friend_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FriendRequestHolder holders = (FriendRequestHolder) holder;
        if (list.get(position).getUserinfo() != null) {
            if (TextUtils.isEmpty(list.get(position).getUserinfo().getAvatar())) {
                ImgLoader.displayAvatar(list.get(position).getUserinfo().getAvatar(), holders.iv_friend_request_photo);
            } else {
                ImgLoader.displayAvatar(list.get(position).getUserinfo().getAvatar(), holders.iv_friend_request_photo);
            }
            holders.tv_friend_request_nickname.setText(list.get(position).getUserinfo().getUser_nicename());
        }

        holders.iv_friend_request_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAgreeClick(list.get(position), position);
                }
            }
        });

        holders.iv_friend_request_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRefuseClick(list.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FriendRequestHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_friend_request_photo;
        RoundedImageView iv_friend_request_true;
        RoundedImageView iv_friend_request_false;
        TextView tv_friend_request_nickname;

        public FriendRequestHolder(View itemView) {
            super(itemView);
            iv_friend_request_photo = itemView.findViewById(R.id.iv_friend_request_photo);
            iv_friend_request_true = itemView.findViewById(R.id.iv_friend_request_true);
            iv_friend_request_false = itemView.findViewById(R.id.iv_friend_request_false);
            tv_friend_request_nickname = itemView.findViewById(R.id.tv_friend_request_nickname);
        }
    }

    public void setData(List<FriendRequestBean.RequestListBean> lists) {
        this.list = lists;
        notifyDataSetChanged();
    }

    public interface OnFriendRequestClickListener {
        void onAgreeClick(FriendRequestBean.RequestListBean bean, int positon);

        void onRefuseClick(FriendRequestBean.RequestListBean bean, int positon);
    }

    private OnFriendRequestClickListener listener;

    public void setOnFriendRequestClickListener(OnFriendRequestClickListener listener) {
        this.listener = listener;
    }
}
