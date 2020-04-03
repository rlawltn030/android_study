package com.example.chatting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset; //
    private String myNickName;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView_nickname;
        public TextView textView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            textView_nickname = v.findViewById(R.id.textView_nickname);
            textView_msg = v.findViewById(R.id.textView_msg);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatData> myDataset, Context context, String myNickName) {
        mDataset = myDataset;
        this.myNickName = myNickName;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 원본데이터의 크기만큼 반복한다.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //데이터 셋팅
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatData chat = mDataset.get(position);

        holder.textView_nickname.setText(chat.getNickname());
        holder.textView_msg.setText(chat.getMsg());

        if(chat.getNickname().equals(this.myNickName)){
            holder.textView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.textView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }else{
            holder.textView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.textView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public ChatData getchat(int position){
        return mDataset == null ? null : mDataset.get(position);
    }

    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); // 갱신
    }
}
