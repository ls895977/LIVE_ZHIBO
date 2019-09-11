package com.kiwi.phonelive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kiwi.phonelive.R;
import com.kiwi.phonelive.bean.MyFriendBean;
import com.kiwi.phonelive.glide.ImgLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

public class MyFriendAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MyFriendBean.FriendListBean> list = new ArrayList<>();

    public MyFriendAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFriendHolder(inflater.inflate(R.layout.my_friend_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyFriendHolder holders = (MyFriendHolder) holder;
        ImgLoader.display(list.get(position).getAvatar(), holders.iv_friend_photo);
        holders.tv_friend_nikename.setText(list.get(position).getUser_nicename());
        holders.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onChatClick(list.get(position));
                }
            }
        });

        holders.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onDeleteClick(list.get(position),holders.swipe_layout);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyFriendHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_friend_photo;
        TextView tv_friend_nikename;
        TextView btn_delete;
        RelativeLayout rl_my_friend_parent;
        RelativeLayout rl;
        SwipeMenuLayout swipe_layout;

        public MyFriendHolder(View itemView) {
            super(itemView);
            iv_friend_photo = itemView.findViewById(R.id.iv_friend_photo);
            tv_friend_nikename = itemView.findViewById(R.id.tv_friend_nikename);
            rl_my_friend_parent = itemView.findViewById(R.id.rl_my_friend_parent);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            swipe_layout = itemView.findViewById(R.id.swipe_layout);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    public void setList(List<MyFriendBean.FriendListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface onMyFriendClickListener {
        void onChatClick(MyFriendBean.FriendListBean bean);
        void onDeleteClick(MyFriendBean.FriendListBean bean,SwipeMenuLayout swipeMenuLayout);
    }

    private onMyFriendClickListener listener;

    public void setOnMyFriendClick(onMyFriendClickListener listener) {
        this.listener = listener;
    }
}
