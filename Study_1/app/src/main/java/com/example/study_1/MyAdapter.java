package com.example.study_1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.study_1.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset; // 뉴스 데이터가 담길 List
    private static View.OnClickListener onClickListener; // 뉴스 클릭리스너

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView_title;
        public TextView textView_description;
        public SimpleDraweeView imageView_title;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            textView_title = v.findViewById(R.id.textView_title);
            textView_description = v.findViewById(R.id.textView_description);
            imageView_title = v.findViewById(R.id.imageView_title);
            rootView = v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsData> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 원본데이터의 크기만큼 반복한다.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //데이터 셋팅
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NewsData news = mDataset.get(position);

        holder.textView_title.setText(news.getTitle());
        String description = news.getDescription();
        if(description != "null") {
            holder.textView_description.setText(description);
        }else{
            holder.textView_description.setText("-");
        }

        Uri uri = Uri.parse(news.getUrlToImage());
        holder.imageView_title.setImageURI(uri);

        //tag- label
        holder.rootView.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public NewsData getNews(int position){
        return mDataset == null ? null : mDataset.get(position);
    }
}